package depromeet.ohgzoo.iam.search.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@EnableScheduling
@RequiredArgsConstructor
@RestController
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job collectJob;

    @GetMapping("/api/v1/search/batch")
    public void launch() {
        launchJob();
    }

//    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 10)
    public void runJob() {
        launchJob();
    }

    private void launchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("date", new Date())
                    .toJobParameters();
            jobLauncher.run(collectJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
