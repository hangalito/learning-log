package dev.hangalito.topics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Size(max = 45, message = "First name must not be longer than 45 characters")
    @NotBlank(message = "First name is required")
    @Column(name = "first_name")
    String firstName;
    @Size(max = 45, message = "Last name must not be longer than 45 characters")
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    String lastName;
    @Size(min = 4, max = 15, message = "Username me between 4 and 15 characters long")
    @Column(name = "username", unique = true)
    String username;
    @Size(min = 8, message = "Password should be at least 8 characters long")
    @NotBlank(message = "Password is required")
    String password;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getFirstName(), user.getFirstName()) &&
               Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getUsername(), user.getUsername()) &&
               Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getUsername(), getPassword());
    }

}
