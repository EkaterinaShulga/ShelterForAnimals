package ru.skypro.shelterforanimals.entity;


import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="petPhotos")
public class PetPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long petPhotoId;
    private String filePath;
    private Integer fileSize;
    private int status;
    private Long chatId;
    @OneToOne(mappedBy = "photo")
    private Report report;

    @OneToOne(mappedBy = "photo")
    private Record record;

public PetPhoto(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetPhoto petPhoto = (PetPhoto) o;
        return status == petPhoto.status && Objects.equals(petPhotoId, petPhoto.petPhotoId) && Objects.equals(filePath, petPhoto.filePath) && Objects.equals(fileSize, petPhoto.fileSize) && Objects.equals(chatId, petPhoto.chatId) && Objects.equals(report, petPhoto.report) && Objects.equals(record, petPhoto.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petPhotoId, filePath, fileSize, status, chatId, report, record);
    }
}
