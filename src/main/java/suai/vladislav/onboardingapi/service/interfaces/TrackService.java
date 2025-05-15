package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.TrackDto;

import java.util.List;

public interface TrackService {

    List<TrackDto> getTracks();

    TrackDto getTrackById(Long id);

    TrackDto addTrack(TrackDto trackDto);

    TrackDto updateTrack(TrackDto trackDto);

    void deleteTrack(Long id);
}
