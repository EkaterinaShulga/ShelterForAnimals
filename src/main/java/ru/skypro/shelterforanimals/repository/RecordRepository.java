package ru.skypro.shelterforanimals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.shelterforanimals.entity.Record;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Optional<Record> findById(Long recordId);

    Record findRecordByChatId(long chatId);

    List<Record> findByChatId(Long chatIdUser);

    Record findRecordByChatIdAndDate(Long chatIdUser, LocalDate date);

    List<Record> getRecordsByStatus(int statusVolunteer);

}
