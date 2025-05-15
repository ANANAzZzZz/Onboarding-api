package suai.vladislav.onboardingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suai.vladislav.onboardingapi.model.UserProgressInModule;

@Repository
public interface UserProgressInModuleRepository extends JpaRepository<UserProgressInModule, Long> {
}
