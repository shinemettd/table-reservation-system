package kg.edu.alatoo.table_reservations_system.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "TABLES")
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@SequenceGenerator(name = "TABLE_SEQUENCE", sequenceName = "TABLE_SEQ")
public class TableEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID", nullable = false)
    Restaurant restaurant;

    @Column(name = "TABLE_NUMBER", nullable = false)
    Long number;

    @Column(name = "CAPACITY", nullable = false)
    Long capacity;

}
