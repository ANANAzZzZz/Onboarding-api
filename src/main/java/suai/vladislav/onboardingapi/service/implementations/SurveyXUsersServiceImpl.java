package suai.vladislav.onboardingapi.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suai.vladislav.onboardingapi.dto.SecureUserDto;
import suai.vladislav.onboardingapi.dto.SurveyXUserDto;
import suai.vladislav.onboardingapi.model.Survey;
import suai.vladislav.onboardingapi.model.User;
import suai.vladislav.onboardingapi.repository.SurveyRepository;
import suai.vladislav.onboardingapi.service.interfaces.EntityFinderService;
import suai.vladislav.onboardingapi.service.interfaces.SurveyXUserService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyXUsersServiceImpl implements SurveyXUserService {

    private final SurveyRepository surveyRepository;
    private final EntityFinderService entityFinderService;

    @Override
    @Transactional
    public List<SecureUserDto> getUsersForSurvey(Long surveyId) {
        log.info("вызван getUsersForSurvey");

        Survey survey = entityFinderService.getSurveyOrThrow(surveyId);

        return survey.getUsers()
            .stream()
            .map(
                user -> SecureUserDto
                    .builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstname())
                    .lastName(user.getLastname())
                    .role(user.getRole())
                    .build()

            )
            .toList();
    }

    @Override
    @Transactional
    public SurveyXUserDto addUserToSurvey(Long surveyId, Long userId) {
        log.info("вызван addUserToSurvey");

        Survey survey = entityFinderService.getSurveyOrThrow(surveyId);
        User user = entityFinderService.getUserOrThrow(userId);

        survey.assignUser(user);
        surveyRepository.save(survey);

        return SurveyXUserDto
            .builder()
            .userId(user.getId())
            .surveyId(survey.getId())
            .build();
    }

    @Override
    @Transactional
    public void deleteUserFromSurvey(Long surveyId, Long userId) {
        log.info("вызван deleteUserFromSurvey");

        Survey survey = entityFinderService.getSurveyOrThrow(surveyId);
        User user = entityFinderService.getUserOrThrow(userId);

        survey.removeUser(user);
        surveyRepository.save(survey);
    }
}
