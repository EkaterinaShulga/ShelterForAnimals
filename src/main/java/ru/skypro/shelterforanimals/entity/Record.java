package ru.skypro.shelterforanimals.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recordId;

    private int status;
    private Long chatId;
    private LocalDateTime dateTime;
    private String diet;
    private String adaptation;
    private String changeInBehavior;

    @OneToOne(mappedBy = "record")
    private Report report;

    @OneToOne
    @JoinColumn(name = "pet_Photo_id")
    private PetPhoto photo;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return status == record.status && Objects.equals(recordId, record.recordId) && Objects.equals(chatId, record.chatId) && Objects.equals(dateTime, record.dateTime) && Objects.equals(diet, record.diet) && Objects.equals(adaptation, record.adaptation) && Objects.equals(changeInBehavior, record.changeInBehavior) && Objects.equals(report, record.report) && Objects.equals(photo, record.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, status, chatId, dateTime, diet, adaptation, changeInBehavior, report, photo);
    }

    @Override
    public String toString() {
        return '\n' + "Текстовый отчет" + '\n' +
                " статус " + status + '\n' +
                " чат id " + chatId + '\n' +
                " дата записи: " + dateTime + '\n' +
                " " + diet + '\n' +
                " " + adaptation + '\n' +
                " " + changeInBehavior + '\n';

    }


}