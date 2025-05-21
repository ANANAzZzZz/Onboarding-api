package suai.vladislav.onboardingapi.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suai.vladislav.onboardingapi.dto.SurveyDto;
import suai.vladislav.onboardingapi.enums.ErrorType;
import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.mapper.SurveyMapper;
import suai.vladislav.onboardingapi.model.Survey;
import suai.vladislav.onboardingapi.repository.SurveyRepository;
import suai.vladislav.onboardingapi.service.interfaces.EntityFinderService;
import suai.vladislav.onboardingapi.service.interfaces.SurveyService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;
    private final EntityFinderService entityFinderService;

    @Override
    public List<SurveyDto> getSurveys() {
        log.info("вызван getSurveys");

        return surveyRepository.findAll().stream()
            .map(surveyMapper::toDto)
            .toList();
    }

    @Override
    public SurveyDto getSurveyById(Long id) {
        log.info("вызван getSurveyById id = {}", id);

        return surveyMapper.toDto(entityFinderService.getSurveyOrThrow(id));
    }

    @Override
    @Transactional
    public SurveyDto addSurvey(SurveyDto surveyDto) {
        log.info("вызван addSurvey");

        return surveyMapper.toDto(
            surveyRepository.save(surveyMapper.toModel(surveyDto))
        );
    }

    @Override
    @Transactional
    public SurveyDto updateSurvey(SurveyDto surveyDto) {
        log.info("вызван updateSurvey");

        if (surveyDto.id() == null) {
            throw new CommonOnboardingApiException(ErrorType.ID_IS_MISSING);
        }

        Survey survey = entityFinderService.getSurveyOrThrow(surveyDto.id());

        survey.setName(surveyDto.name());
        survey.setContent(surveyDto.content());

        surveyRepository.save(survey);

        return surveyMapper.toDto(survey);
    }

    @Override
    @Transactional
    public void deleteSurvey(Long id) {
        log.info("вызван deleteSurvey");

        Survey survey = entityFinderService.getSurveyOrThrow(id);

        surveyRepository.delete(survey);
    }
}
