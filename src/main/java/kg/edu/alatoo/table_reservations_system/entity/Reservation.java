package kg.edu.alatoo.table_reservations_system.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TABLE_RESERVATION")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    User user;

    @OneToOne
    @JoinColumn(name = "TABLE_ID", nullable = false)
    TableEntity table;

    @Column(name = "START_TIME", nullable = false)
    LocalDateTime reservationStartTime;

    @Column(name = "END_TIME")
    LocalDateTime reservationEndTime;

}