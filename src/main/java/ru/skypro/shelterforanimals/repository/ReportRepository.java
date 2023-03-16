package ru.skypro.shelterforanimals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.shelterforanimals.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {


}
