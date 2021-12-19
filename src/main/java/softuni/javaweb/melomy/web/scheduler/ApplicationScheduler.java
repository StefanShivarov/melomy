package softuni.javaweb.melomy.web.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import softuni.javaweb.melomy.service.RequestCounterService;

@Component
public class ApplicationScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationScheduler.class);
    private final RequestCounterService requestCounterService;

    public ApplicationScheduler(RequestCounterService requestCounterService) {
        this.requestCounterService = requestCounterService;
    }

//    @Scheduled(cron = "0 * * ? * *")//every minute (for testing purpose)
    @Scheduled(cron = "0 0 0 * * ?") //every day at 00:00
    public void resetRequestCounterData(){
        requestCounterService.resetRequestCounterData();
        LOGGER.info("Request counter data is reset!");
    }
}