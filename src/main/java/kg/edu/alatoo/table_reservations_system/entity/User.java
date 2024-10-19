package kg.edu.alatoo.table_reservations_system.entity;

import jakarta.persistence.*;
import kg.edu.alatoo.table_reservations_system.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Table(name = "USERS")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SequenceGenerator(name = "USERS_SEQUENCE", sequenceName = "USERS_SEQ")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "USERNAME", nullable = false, unique = true)
    String username;

    @Column(name = "FULL_NAME", nullable = false)
    String fullName;

    @Column(name = "PASSWORD", nullable = false)
    String password;

    @Column(name = "PHONE_NUMBER", nullable = false, unique = true)
    String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.getDeletionDate() == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getDeletionDate() == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.getDeletionDate() == null;
    }

    @Override
    public boolean isEnabled() {
        return this.getDeletionDate() == null;
    }
}
