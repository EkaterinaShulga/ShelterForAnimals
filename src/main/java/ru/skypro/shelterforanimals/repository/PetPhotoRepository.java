package ru.skypro.shelterforanimals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.shelterforanimals.entity.PetPhoto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PetPhotoRepository extends JpaRepository<PetPhoto, Long> {

    Optional<PetPhoto> findById(Long recordId);

    List<PetPhoto> getAllByChatId(Long chatIdUser);

    List<PetPhoto> getPetPhotosByStatus(int statusVolunteer);

    PetPhoto findPetPhotoByChatIdAndDate(long chatId, LocalDate date);
}

