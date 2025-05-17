package suai.vladislav.onboardingapi.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suai.vladislav.onboardingapi.dto.TrackDto;
import suai.vladislav.onboardingapi.enums.ErrorType;
import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.mapper.TrackMapper;
import suai.vladislav.onboardingapi.model.Track;
import suai.vladislav.onboardingapi.repository.TrackRepository;
import suai.vladislav.onboardingapi.service.interfaces.EntityFinderService;
import suai.vladislav.onboardingapi.service.interfaces.TrackService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    private final TrackMapper trackMapper;

    private final EntityFinderService entityFinderService;

    @Override
    public List<TrackDto> getTracks() {
        log.info("вызван getTracks");

        return trackRepository.findAll().stream()
            .map(trackMapper::toDto)
            .toList();
    }

    @Override
    public TrackDto getTrackById(Long id) {
        log.info("вызван getTrackById id = {}", id);

        return trackMapper.toDto(entityFinderService.getTrackOrThrow(id));
    }

    @Override
    @Transactional
    public TrackDto addTrack(TrackDto trackDto) {
        log.info("вызван addTrack");

        return trackMapper.toDto(
            trackRepository.save(trackMapper.toModel(trackDto))
        );
    }

    @Override
    @Transactional
    public TrackDto updateTrack(TrackDto trackDto) {
        log.info("вызван updateTrack");

        if (trackDto.id() == null) {
            throw new CommonOnboardingApiException(ErrorType.ID_IS_MISSING);
        }

        Track track = entityFinderService.getTrackOrThrow(trackDto.id());

        track.setName(trackDto.name());
        trackRepository.save(track);

        return trackMapper.toDto(track);
    }

    @Override
    @Transactional
    public void deleteTrack(Long id) {
        log.info("вызван deleteTrack");

        Track track = entityFinderService.getTrackOrThrow(id);

        trackRepository.delete(track);
    }
}
