package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.ScoreboardDto;

import java.util.List;

public interface ScoreboardService {

    List<ScoreboardDto> getScoreboard();

    ScoreboardDto getScoreboardById(Long id);

    ScoreboardDto addScoreboard(ScoreboardDto scoreboardDto);

    ScoreboardDto updateScoreboard(ScoreboardDto scoreboardDto);

    void deleteScoreboard(Long id);
}
