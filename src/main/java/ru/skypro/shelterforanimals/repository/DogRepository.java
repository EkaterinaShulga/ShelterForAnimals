package ru.skypro.shelterforanimals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.shelterforanimals.entity.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    Dog findDogByName(String name);
}

