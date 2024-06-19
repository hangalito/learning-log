package dev.hangalito.topics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotBlank(message = "Content is required")
    @Column(name = "content")
    private String content;

    @ManyToOne(targetEntity = Topic.class)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "author")
    private User author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(getId(), subject.getId()) && Objects.equals(getContent(), subject.getContent()) &&
               Objects.equals(getTopic(), subject.getTopic()) && Objects.equals(getAuthor(), subject.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getTopic(), getAuthor());
    }

}
