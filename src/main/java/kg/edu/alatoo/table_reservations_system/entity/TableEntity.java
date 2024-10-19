package kg.edu.alatoo.table_reservations_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TABLES")
@ToString(exclude = {"restaurant"})
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
