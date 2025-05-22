package suai.vladislav.onboardingapi.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suai.vladislav.onboardingapi.model.Track;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    @Override
    @EntityGraph(value = "track-with-modules-and-pages")
    List<Track> findAll();

    @EntityGraph(value = "track-with-modules-and-pages")
    @Override
    Optional<Track> findById(Long id);
}
