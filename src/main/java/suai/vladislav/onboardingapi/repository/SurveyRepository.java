package suai.vladislav.onboardingapi.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suai.vladislav.onboardingapi.model.Survey;

import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @EntityGraph(attributePaths = {"users"})
    @Override
    Optional<Survey> findById(Long id);
}
