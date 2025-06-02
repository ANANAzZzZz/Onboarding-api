package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.TrackDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с треками.
 */
public interface TrackService {

    /**
     * Возвращает список всех треков.
     *
     * @return список треков
     */
    List<TrackDto> getTracks();

    /**
     * Возвращает трек по его идентификатору.
     *
     * @param id идентификатор трека
     * @return трек
     */
    TrackDto getTrackById(Long id);

    /**
     * Добавляет новый трек.
     *
     * @param trackDto DTO трека
     * @return добавленный трек
     */
    TrackDto addTrack(TrackDto trackDto);

    /**
     * Обновляет существующий трек.
     *
     * @param trackDto DTO трека
     * @return обновленный трек
     */
    TrackDto updateTrack(TrackDto trackDto);

    /**
     * Удаляет трек по его идентификатору.
     *
     * @param id идентификатор трека
     */
    void deleteTrack(Long id);
}
