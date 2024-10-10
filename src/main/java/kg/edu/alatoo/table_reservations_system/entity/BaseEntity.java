package kg.edu.alatoo.table_reservations_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity implements Serializable  {

    @Id
    Long id;

    @CreatedDate
    @Column(name = "CREATION_DATE", updatable = false)
    LocalDateTime creationDate;

    @Column(name = "DELETION_DATE")
    LocalDateTime deletionDate;

}
