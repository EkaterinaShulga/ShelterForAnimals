package ru.skypro.shelterforanimals.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.Objects;

/**
 * Record - record about animal <br>
 * linked by foreign keys with {@code PetPhoto(Entity)}<br>
 * the Record is entered into the database <br>
 */


@Getter
@Setter
@Entity
@Table(name = "Records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private int status;
    private Long chatId;
    private LocalDate date;
    private String diet;
    private String adaptation;
    private String changeInBehavior;

    @OneToOne(mappedBy = "record")
    private PetPhoto photo;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return status == record.status && Objects.equals(recordId, record.recordId)
                && Objects.equals(chatId, record.chatId)
                && Objects.equals(date, record.date)
                && Objects.equals(diet, record.diet)
                && Objects.equals(adaptation, record.adaptation)
                && Objects.equals(changeInBehavior, record.changeInBehavior)
                && Objects.equals(photo, record.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, status, chatId, date, diet, adaptation, changeInBehavior);
    }

    @Override
    public String toString() {
        return '\n' + "Текстовый отчет" + '\n' +
                " статус " + status + '\n' +
                " чат id " + chatId + '\n' +
                " дата записи: " + date + '\n' +
                " " + diet + '\n' +
                " " + adaptation + '\n' +
                " " + changeInBehavior + '\n';

    }


}