package suai.vladislav.onboardingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suai.vladislav.onboardingapi.enums.Role;
import suai.vladislav.onboardingapi.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);
    List<User> findAllByRoleEquals(Role role);
}
