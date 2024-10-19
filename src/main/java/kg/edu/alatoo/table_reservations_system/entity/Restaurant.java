package kg.edu.alatoo.table_reservations_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "RESTAURANT")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SequenceGenerator(name = "RESTAURANT_SEQUENCE", sequenceName = "RESTAURANT_SEQ")
public class Restaurant extends BaseEntity {

    @Column(name = "NAME", nullable = false)
    String name;

    @OneToMany(mappedBy = "restaurant")
    Set<TableEntity> tables;

}
