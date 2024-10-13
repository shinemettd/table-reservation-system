package kg.edu.alatoo.table_reservations_system.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @CreatedDate
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "LAST_EDITED_DATE")
    LocalDateTime editionDate;

    @Column(name = "DELETED_DATE")
    LocalDateTime deletionDate;

}
