package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.model.KnowledgeBase;
import suai.vladislav.onboardingapi.model.Module;
import suai.vladislav.onboardingapi.model.Page;
import suai.vladislav.onboardingapi.model.Scoreboard;
import suai.vladislav.onboardingapi.model.Survey;
import suai.vladislav.onboardingapi.model.Track;
import suai.vladislav.onboardingapi.model.User;
import suai.vladislav.onboardingapi.model.UserProgressInModule;

public interface EntityFinderService {

    User getUserOrThrow (Long id);

    Module getModuleOrThrow(Long id);

    Page getPageOrThrow(Long id);

    Survey getSurveyOrThrow(Long id);

    Track getTrackOrThrow(Long id);

    Scoreboard getScoreboardOrThrow(Long id);

    UserProgressInModule getUserProgressInModuleOrThrow(Long id);

    KnowledgeBase getKnowledgeBaseOrThrow(Long id);
}
