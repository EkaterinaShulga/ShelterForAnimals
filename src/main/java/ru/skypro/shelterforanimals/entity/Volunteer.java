package ru.skypro.shelterforanimals.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "volunteers")
public class Volunteer {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String name;
    private int status;
    private long chatId;

    public Volunteer(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return status == volunteer.status && chatId == volunteer.chatId && Objects.equals(id, volunteer.id) && Objects.equals(name, volunteer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, chatId);
    }
}
