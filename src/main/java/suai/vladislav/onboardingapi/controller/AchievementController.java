package suai.vladislav.onboardingapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import suai.vladislav.onboardingapi.dto.AchievementDto;
import suai.vladislav.onboardingapi.dto.AchievementNotificationDto;
import suai.vladislav.onboardingapi.dto.UserActionDto;
import suai.vladislav.onboardingapi.service.interfaces.AchievementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/achievement")
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping
    public List<AchievementDto> getAllAchievements() {
        return achievementService.getAllAchievements();
    }

    @GetMapping("/{achievementId}")
    public AchievementDto getAchievement(@PathVariable("achievementId") Long achievementId) {
        return achievementService.getAchievementById(achievementId);
    }

    @GetMapping("/user/{userId}")
    public List<AchievementDto> getUserAchievements(@PathVariable("userId") Long userId) {
        return achievementService.getUserAchievements(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AchievementDto createAchievement(@Validated @RequestBody AchievementDto achievementDto) {
        return achievementService.createAchievement(achievementDto);
    }

    @PutMapping
    public AchievementDto updateAchievement(@Validated @RequestBody AchievementDto achievementDto) {
        return achievementService.updateAchievement(achievementDto);
    }

    @DeleteMapping("/{achievementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAchievement(@PathVariable("achievementId") Long achievementId) {
        achievementService.deleteAchievement(achievementId);
    }

    @PostMapping("/action")
    public List<AchievementNotificationDto> processUserAction(@Validated @RequestBody UserActionDto userActionDto) {
        return achievementService.processUserAction(userActionDto);
    }
}