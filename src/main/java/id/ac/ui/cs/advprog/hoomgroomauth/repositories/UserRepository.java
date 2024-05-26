package id.ac.ui.cs.advprog.hoomgroomauth.repositories;

import id.ac.ui.cs.advprog.hoomgroomauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
