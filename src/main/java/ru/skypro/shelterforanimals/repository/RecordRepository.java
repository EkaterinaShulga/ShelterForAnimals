package ru.skypro.shelterforanimals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.shelterforanimals.entity.Record;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Record findByRecordId(long recordId);
    LinkedList<Record> findAllRecordByChatId(long chatId);
    List<Record> findAllRecordsByChatId(long chatId);
    Record findRecordByChatId(long chatId);
    List<Record> findRecordsByChatIdAndDateTime(long chatId, LocalDateTime dateTime);

    List<Record> findRecordsByStatus(int status);
}
