package gr.dresso.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.dresso.rest.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "UserLogin")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
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
}
