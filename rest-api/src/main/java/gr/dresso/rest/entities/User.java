package gr.dresso.rest.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "credits")
    private double credits;

    @OneToOne(mappedBy = "user")
    private UserLogin userLogin;

    @OneToMany(mappedBy = "user")
    private Set<FavoriteProduct> favoriteProducts;

    @OneToMany(mappedBy = "user")
    private Set<Cart> cart;
}
