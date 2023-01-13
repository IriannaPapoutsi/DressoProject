package gr.dresso.rest.entities;

import jakarta.persistence.*;
import lombok.*;

// TODO: @Data should not be used with Hibernate / JPA entities (Check warning and look it up)
@Entity
@Data
@Table(name = "FavoriteProduct")
public class FavoriteProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;
    // TODO: Newline here
}
