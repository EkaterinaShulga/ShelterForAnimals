package ru.skypro.shelterforanimals.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


/**
 * the Cat is entered into the database
 */

@Getter
@Setter
@Entity
@Table(name = "Cats")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String age;

    private String color;

    private long chatId;

    private String catPhoto;

    public Cat(){}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return id == cat.id && chatId == cat.chatId && Objects.equals(name, cat.name) && Objects.equals(age, cat.age) && Objects.equals(color, cat.color) && Objects.equals(catPhoto, cat.catPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, color, chatId, catPhoto);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", color='" + color + '\'' +
                ", chatId=" + chatId +
                ", catPhoto='" + catPhoto + '\'' +
                '}';
    }
}
