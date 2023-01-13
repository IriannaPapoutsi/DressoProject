package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // TODO: Newline here
    boolean existsUserByEmail(String email);
    // TODO: Newline here
    // TODO: This method already exists on the repository, it does not need to be created
    boolean existsUserById(String id);
    // TODO: This method already exists on the repository, it does not need to be created
    User findUserById(String id);
    // TODO: This method already exists on the repository, it does not need to be created
    void deleteUserById(String id);
    // TODO: Newline here
}
