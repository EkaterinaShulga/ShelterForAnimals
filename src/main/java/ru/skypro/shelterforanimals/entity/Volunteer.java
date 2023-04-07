package ru.skypro.shelterforanimals.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Volunteer - employee of a shelter for cats or dogs <br>
 * the Volunteer is entered into the database <br>
 */

@Getter
@Setter
@Entity
@Table(name = "Volunteers")
public class Volunteer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private int status;
    private long chatId;

    public Volunteer() {
    }

    public Volunteer(long id, String name, int status, long chatId){
        this.id = id;
        this.name = name;
        this.status = status;
        this.chatId = chatId;
    }
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
