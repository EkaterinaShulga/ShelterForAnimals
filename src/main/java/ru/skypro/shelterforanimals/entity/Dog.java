package ru.skypro.shelterforanimals.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "dogs")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String age;

    private String color;

    private long chatId;

    private String dogPhoto;

    public Dog() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return id == dog.id && chatId == dog.chatId && Objects.equals(name, dog.name) && Objects.equals(age, dog.age) && Objects.equals(color, dog.color) && Objects.equals(dogPhoto, dog.dogPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, color, chatId, dogPhoto);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", color='" + color + '\'' +
                ", chatId=" + chatId +
                ", dogPhoto='" + dogPhoto + '\'' +
                '}';
    }
}
