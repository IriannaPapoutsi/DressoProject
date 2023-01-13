package gr.dresso.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
// TODO: Remove unused import
import gr.dresso.rest.entities.User;
import jakarta.persistence.*;
import lombok.*;

// TODO: @Data should not be used with Hibernate / JPA entities (Check warning and look it up)
@Entity
@Data
@Table(name = "UserLogin")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    // TODO: Newline here
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToOne
    @JoinColumn(name = "userID")
    @JsonIgnore
    private User user;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;
    // TODO: Newline here
}
