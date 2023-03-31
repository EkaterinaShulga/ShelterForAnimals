package ru.skypro.shelterforanimals.entity;


import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;


/**
 * PetPhoto - photo of an animal <br>
 * linked by foreign keys with {@code Record(Entity)}<br>
 * the PetPhoto is entered into the database <br>
 */

@Getter
@Setter
@Entity
@Table(name = "Pet_Photos")
public class PetPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petPhotoId;
    private String filePath;
    private Integer fileSize;

    private LocalDate date;
    private int status;
    private Long chatId;

    @OneToOne
    @JoinColumn(name = "record_id")
    private Record record;

    public PetPhoto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetPhoto petPhoto = (PetPhoto) o;
        return status == petPhoto.status && Objects.equals(petPhotoId, petPhoto.petPhotoId)
                && Objects.equals(filePath, petPhoto.filePath)
                && Objects.equals(fileSize, petPhoto.fileSize)
                && Objects.equals(chatId, petPhoto.chatId)
                && Objects.equals(record, petPhoto.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petPhotoId, filePath, fileSize, status, chatId);
    }

    @Override
    public String toString() {
        return "PetPhoto{" +
                "petPhotoId=" + petPhotoId +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", dateTime=" + date +
                ", status=" + status +
                ", chatId=" + chatId +
                '}';
    }
}
