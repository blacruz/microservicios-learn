package pe.blacruz.security.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.blacruz.security.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  public User findByUsernameAndPassword(String username, String password);

  public User findByUsername(String username);
}
