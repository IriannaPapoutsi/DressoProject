package gr.dresso.rest.repositories;

import gr.dresso.rest.entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Integer> {

    boolean existsUserLoginByUsername(String username);

    boolean existsUserLoginByPassword(String password);
}
