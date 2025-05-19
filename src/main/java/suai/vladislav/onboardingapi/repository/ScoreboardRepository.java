package suai.vladislav.onboardingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suai.vladislav.onboardingapi.model.Scoreboard;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreboardRepository extends JpaRepository<Scoreboard, Long> {
    List<Scoreboard> findByUserId(Long userId);

    Optional<Scoreboard> findTopByUserIdOrderByIdDesc(Long userId);
}
