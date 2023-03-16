package ru.skypro.shelterforanimals.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long chatId;
    private int status;

    public Client() {
    }

    public Client(Long id, long chatId, int status) {
        this.id = id;
        this.chatId = chatId;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return chatId == client.chatId && status == client.status && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, status);
    }

    @Override
    public String
    toString() {
        return "Client{" +
                "userId=" + id +
                ", chatId=" + chatId +
                ", status=" + status +
                '}';
    }
}