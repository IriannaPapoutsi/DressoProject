package gr.dresso.rest.entities;

import gr.dresso.rest.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "UserLogin")
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToOne
    @JoinColumn(name = "userID")
    private User user;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
