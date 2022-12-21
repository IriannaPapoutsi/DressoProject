package gr.dresso.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonIgnore
    private UserLogin userLogin;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<FavoriteProduct> favoriteProducts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Cart> cart;
}
