package gr.dresso.rest.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "FavoriteProduct")
public class FavoriteProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;
}
