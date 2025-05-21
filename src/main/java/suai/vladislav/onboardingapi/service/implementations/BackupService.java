package suai.vladislav.onboardingapi.service.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class BackupService {

    private static final Logger log = LoggerFactory.getLogger(BackupService.class);

    @Value("${backup.pg.dump-path}")
    private String pgDumpPath;

    @Value("${backup.path}")
    private String backupPath;

    @Value("${backup.pg.host}")
    private String host;

    @Value("${backup.pg.port}")
    private String port;

    @Value("${backup.pg.dbname}")
    private String dbName;

    @Value("${backup.pg.username}")
    private String username;

    @Value("${backup.pg.password}")
    private String password;

    private static final int RETENTION_DAYS = 7;

    public void backupDatabase() {
        try {
            File backupDir = new File(backupPath);
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String outputFile = backupPath + File.separator + "backup_" + timestamp + ".sql";

            ProcessBuilder pb = new ProcessBuilder(
                    pgDumpPath,
                    "-h", host,
                    "-p", port,
                    "-U", username,
                    "-F", "p",
                    "-f", outputFile,
                    dbName
            );

            Map<String, String> env = pb.environment();
            env.put("PGPASSWORD", password);

            Process process = pb.start();
            int exitCode = process.waitFor();

            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream())
            );
            String line;
            while ((line = errorReader.readLine()) != null) {
                System.err.println("pg_dump error: " + line);
            }

            if (exitCode == 0) {
                System.out.println("Бэкап успешно создан: " + outputFile);
                cleanupOldBackups(backupDir);
            } else {
                System.err.println("Ошибка при создании бэкапа. Код: " + exitCode);
            }
        } catch (Exception e) {
            log.error("Ошибка при создании бэкапа {}", e.getMessage());
        }
    }

    private void cleanupOldBackups(File directory) {
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".sql"));
        if (files == null) return;

        long now = System.currentTimeMillis();
        long cutoff = now - RETENTION_DAYS * 24L * 60 * 60 * 1000;

        for (File file : files) {
            if (file.lastModified() < cutoff) {
                boolean deleted = file.delete();
                if (deleted) {
                    System.out.println("Удалён старый бэкап: " + file.getName());
                } else {
                    System.err.println("Не удалось удалить файл: " + file.getName());
                }
            }
        }
    }
}


