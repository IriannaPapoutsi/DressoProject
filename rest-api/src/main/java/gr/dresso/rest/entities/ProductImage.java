package gr.dresso.rest.entities;

import gr.dresso.rest.entities.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "ProductImage")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "imageTitle")
    private String imageTitle;

    @Column(name = "imageURL")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;
}
