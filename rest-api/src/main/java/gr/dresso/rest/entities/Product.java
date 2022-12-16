package gr.dresso.rest.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "sku")
    private String sku;

    public int getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "colorID")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private Set<FavoriteProduct> favoriteProducts;

    @OneToMany(mappedBy = "product")
    private Set<Cart> cart;
}