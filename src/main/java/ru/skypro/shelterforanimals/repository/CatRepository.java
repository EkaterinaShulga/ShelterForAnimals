package ru.skypro.shelterforanimals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.shelterforanimals.entity.Cat;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {

    Cat findCatByName(String name);
}
