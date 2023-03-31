package ru.skypro.shelterforanimals.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Objects;


/**
 * User - a person who took a cat or dog from a shelter <br>
 * the User is entered into the database <br>
 */

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String numberPhone;
    private long chatId;
    private int status;
    private String petName;

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return chatId == user.chatId && status == user.status && Objects.equals(userId, user.userId) && Objects.equals(userName, user.userName) && Objects.equals(numberPhone, user.numberPhone) && Objects.equals(petName, user.petName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, numberPhone, chatId, status, petName);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", chatId=" + chatId +
                ", status=" + status +
                ", petName='" + petName + '\'' +
                '}';
    }
}
