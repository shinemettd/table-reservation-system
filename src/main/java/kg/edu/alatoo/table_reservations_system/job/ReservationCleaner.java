package kg.edu.alatoo.table_reservations_system.job;

import kg.edu.alatoo.table_reservations_system.service.ReservationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ConditionalOnProperty(value = "job.enabled", havingValue = "true")
public class ReservationCleaner {

    ReservationService reservationService;

    @Scheduled(fixedRate = 60000)
    public void cleanExpiredReservations() {
        log.info("cleaning expired reservations");
        reservationService.deleteExpiredReservations();
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void cleanLongReservationsWithoutEndDate() {
        log.info("cleaning reservations from previous day");
        reservationService.deleteLongReservationsFromPreviousDayWithoutEndDate();
    }

}
