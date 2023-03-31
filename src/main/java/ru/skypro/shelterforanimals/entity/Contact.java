package ru.skypro.shelterforanimals.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private String name;
    private String numberPhone;

    private LocalDateTime dateTime;

    private int clientStatus;

    @OneToOne()
    @JoinColumn(name = "id_client")
    private Client client;

    public Contact() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id && clientStatus == contact.clientStatus && Objects.equals(name, contact.name) && Objects.equals(numberPhone, contact.numberPhone) && Objects.equals(dateTime, contact.dateTime) && Objects.equals(client, contact.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberPhone, dateTime, clientStatus, client);
    }

    @Override
    public String toString() {
        return
                name + '\n' +
                        " телефон:  " + numberPhone + '\n' +
                        " дата регистр: " + dateTime + '\n' +
                        " статус " + clientStatus + '\n';
    }
}


