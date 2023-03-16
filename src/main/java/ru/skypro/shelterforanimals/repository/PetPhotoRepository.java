package ru.skypro.shelterforanimals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.shelterforanimals.entity.PetPhoto;

import java.util.Optional;

@Repository
public interface PetPhotoRepository extends JpaRepository<PetPhoto, Long> {
    Optional<PetPhoto> findById(Long recordId);

    PetPhoto findPetPhotoByPetPhotoId(Long petPhotoId);

}

