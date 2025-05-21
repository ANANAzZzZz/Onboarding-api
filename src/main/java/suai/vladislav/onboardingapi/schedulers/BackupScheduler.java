package suai.vladislav.onboardingapi.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import suai.vladislav.onboardingapi.service.implementations.BackupService;

@Component
public class BackupScheduler {

    private final BackupService backupService;

    public BackupScheduler(BackupService backupService) {
        this.backupService = backupService;
    }

    @Scheduled(cron = "0 00 22 * * ?")
    public void runBackup() {
        backupService.backupDatabase();
    }
}

