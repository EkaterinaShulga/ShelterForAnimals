package ru.skypro.shelterforanimals.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * linked by foreign key with {@code Client(Entity)}<br>
 * the Contact is entered into the database <br>
 */

@Getter
@Setter
@Entity
@Table(name = "Contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long chatId;
    private String name;
    private String numberPhone;

    private LocalDate date;

    private int clientStatus;


    public Contact() {
    }

    public Contact(int id, long chatId, String name, String numberPhone, LocalDate date, int clientStatus){
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.numberPhone = numberPhone;
        this.date = date;
        this.clientStatus = clientStatus;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id && clientStatus == contact.clientStatus && Objects.equals(name, contact.name) && Objects.equals(numberPhone, contact.numberPhone) && Objects.equals(date, contact.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberPhone, date, clientStatus);
    }

    @Override
    public String toString() {
        return
                name + '\n' +
                        " телефон:  " + numberPhone + '\n' +
                        " chatId:  " + chatId + '\n' +
                        " дата регистр: " + date + '\n' +
                        " статус " + clientStatus + '\n';
    }
}


