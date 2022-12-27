package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByEmail(String email);

    // TODO: findAll is a method that already exists in JpaRepository and you do not need to create it, so you may remove this
    List<User> findAll();
}
