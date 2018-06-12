package kacirekj.webappstats;

import kacirekj.webappstats.data.*;
import kacirekj.webappstats.repository.KeywordRepo;
import kacirekj.webappstats.repository.KeywordTimelineRepo;
import kacirekj.webappstats.service.GetPagesService;
import kacirekj.webappstats.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@Component
public class WebappStatsApplication implements CommandLineRunner {

    private final int PERIODICAL_ACTION_CHECK_TIME_milis = 60000;
    private final int FEED_UPDATE_DELAY_TIME_milis = 60 * 60 * 1000; // 60 minutes


    @Autowired
    GetPagesService getPagesService;
	@Autowired
    DatabaseLoaderService databaseLoaderService;
	@Autowired
    TimeService timeService;
	@Autowired
    KeywordRepo keywordRepo;
	@Autowired
    KeywordTimelineRepo keywordTimelineRepo;

	public static void main(String[] args) {
		SpringApplication.run(WebappStatsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        databaseLoaderService.loadData();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                    while(true) {
                        try {

                            periodcalAction();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(PERIODICAL_ACTION_CHECK_TIME_milis);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
        });

	    t.start();
	}

	private void periodcalAction() throws IOException {
        long latestMeasurement = keywordTimelineRepo.findTopByOrderByMeasuredTimeDesc().getMeasuredTime();
        System.out.println("Latest measurement: " + latestMeasurement);

        long currentTime = timeService.internetTimeMillis();
        System.out.println("Current time: " + currentTime);

        final long maxDiffTime = this.FEED_UPDATE_DELAY_TIME_milis;
        long diffTime = currentTime - latestMeasurement;
        System.out.println("Diff time: " + diffTime + ", Max difference time: " + maxDiffTime);


        if(diffTime > maxDiffTime) {
            System.out.println("Processing new stats.");
            processNewStats(currentTime);
        }
    }

    private void processNewStats(long currentTime) {
        try {
            List<Keyword> keywords = new ArrayList<>();

            for(Keyword k : keywordRepo.findAll()) {
                keywords.add(k);
            }

            Random rand = new Random();

            // I will pick random keywords to achieve consistent coverage of measurements due to the
            // unpredictable behavior of contiuous http GETs from indeed.com
            while(keywords.size() > 0) {
                int randomPick = rand.nextInt(keywords.size());
                Keyword randomKey = keywords.get(randomPick);

                KeywordTimeline t = getPagesService.processKeyword(randomKey, currentTime);
                keywordTimelineRepo.save(t);

                keywords.remove(randomPick);

                // Wait for a while rather, so indeed.com will not consider us as spammers
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
