package suai.vladislav.onboardingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suai.vladislav.onboardingapi.enums.ActionType;
import suai.vladislav.onboardingapi.model.Achievement;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findByActionType(ActionType actionType);

    List<Achievement> findByUsersId(Long userId);
}
