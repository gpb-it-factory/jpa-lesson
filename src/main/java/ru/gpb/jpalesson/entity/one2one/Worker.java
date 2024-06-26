package ru.gpb.jpalesson.entity.one2one;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "worker")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "worker_seq")
    @SequenceGenerator(name = "worker_seq")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final Worker worker = (Worker) object;
        return id == worker.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
