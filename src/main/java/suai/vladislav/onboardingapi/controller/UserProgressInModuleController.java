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
import suai.vladislav.onboardingapi.dto.UserProgressInModuleDto;
import suai.vladislav.onboardingapi.service.interfaces.UserProgressInModuleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user-progress-in-module")
public class UserProgressInModuleController {

    private final UserProgressInModuleService userProgressInModuleService;

    @GetMapping
    public List<UserProgressInModuleDto> getUserProgressInModule() {
        return userProgressInModuleService.getUserProgressInModule();
    }

    @GetMapping("/{user-progress-in-module-id}")
    public UserProgressInModuleDto getUserProgressInModuleById(
        @PathVariable("user-progress-in-module-id") Long userProgressInModuleId
    ) {
        return userProgressInModuleService.getUserProgressInModuleById(userProgressInModuleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserProgressInModuleDto addUserProgressInModule(
        @Validated
        @RequestBody UserProgressInModuleDto userProgressInModuleDto
    ) {
        return userProgressInModuleService.addUserProgressInModule(userProgressInModuleDto);
    }

    @PutMapping
    public UserProgressInModuleDto updateUserProgressInModule(
        @Validated
        @RequestBody UserProgressInModuleDto userProgressInModuleDto
    ) {
        return userProgressInModuleService.updateUserProgressInModule(userProgressInModuleDto);
    }

    @DeleteMapping("/{user-progress-in-module-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserProgressInModule(
        @PathVariable("user-progress-in-module-id") Long userProgressInModuleId
    ) {
        userProgressInModuleService.deleteUserProgressInModule(userProgressInModuleId);
    }
}
