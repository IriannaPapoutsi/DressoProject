package gr.dresso.rest.entities;

import jakarta.persistence.*;
import lombok.*;

// TODO: @Data should not be used with Hibernate / JPA entities (Check warning and look it up)
@Entity
@Data
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "name")
    private String name;
}
