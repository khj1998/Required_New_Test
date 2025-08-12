package required_new_propagation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import required_new_propagation.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
