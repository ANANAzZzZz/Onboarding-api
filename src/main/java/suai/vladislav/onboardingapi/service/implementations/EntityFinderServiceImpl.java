package suai.vladislav.onboardingapi.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import suai.vladislav.onboardingapi.enums.ErrorType;
import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.model.KnowledgeBase;
import suai.vladislav.onboardingapi.model.Module;
import suai.vladislav.onboardingapi.model.Page;
import suai.vladislav.onboardingapi.model.Scoreboard;
import suai.vladislav.onboardingapi.model.Survey;
import suai.vladislav.onboardingapi.model.Track;
import suai.vladislav.onboardingapi.model.User;
import suai.vladislav.onboardingapi.model.UserProgressInModule;
import suai.vladislav.onboardingapi.repository.KnowledgeBaseRepository;
import suai.vladislav.onboardingapi.repository.ModuleRepository;
import suai.vladislav.onboardingapi.repository.PageRepository;
import suai.vladislav.onboardingapi.repository.ScoreboardRepository;
import suai.vladislav.onboardingapi.repository.SurveyRepository;
import suai.vladislav.onboardingapi.repository.TrackRepository;
import suai.vladislav.onboardingapi.repository.UserProgressInModuleRepository;
import suai.vladislav.onboardingapi.repository.UserRepository;
import suai.vladislav.onboardingapi.service.interfaces.EntityFinderService;

@Service
@RequiredArgsConstructor
public class EntityFinderServiceImpl implements EntityFinderService {

    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final PageRepository pageRepository;
    private final SurveyRepository surveyRepository;
    private final TrackRepository trackRepository;
    private final ScoreboardRepository scoreboardRepository;
    private final UserProgressInModuleRepository userProgressInModuleRepository;
    private final KnowledgeBaseRepository knowledgeBaseRepository;

    @Override
    public User getUserOrThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.USER_NOT_FOUND, id));
    }

    @Override
    public Module getModuleOrThrow(Long id) {
        return moduleRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.MODULE_NOT_FOUND, id));
    }

    @Override
    @Cacheable(value = "findEntityById", key = "'page_' + #id")
    public Page getPageOrThrow(Long id) {
        return pageRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.PAGE_NOT_FOUND, id));
    }

    @Override
    public Survey getSurveyOrThrow(Long id) {
        return surveyRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.SURVEY_NOT_FOUND, id));
    }

    @Override
    public Track getTrackOrThrow(Long id) {
        return trackRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.TRACK_NOT_FOUND, id));
    }

    @Override
    public Scoreboard getScoreboardOrThrow(Long id) {
        return scoreboardRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.SCOREBOARD_NOT_FOUND, id));
    }

    @Override
    public UserProgressInModule getUserProgressInModuleOrThrow(Long id) {
        return userProgressInModuleRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.USER_PROGRESS_IN_MODULE_NOT_FOUND, id));
    }

    @Override
    public KnowledgeBase getKnowledgeBaseOrThrow(Long id) {
        return knowledgeBaseRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.KNOWLEDGE_BASE_NOT_FOUND, id));
    }
}
