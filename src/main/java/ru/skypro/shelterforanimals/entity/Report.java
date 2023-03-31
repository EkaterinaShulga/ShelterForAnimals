package ru.skypro.shelterforanimals.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report {

    @GeneratedValue
    @Id
    private Long reportId;
    private Long chatId;

    private int status;


    @OneToOne
    @JoinColumn(name = "photo_id")
    private PetPhoto photo;
    @OneToOne
    @JoinColumn(name = "record_id")
    private Record record;

    public Report() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return status == report.status && Objects.equals(reportId, report.reportId) && Objects.equals(chatId, report.chatId) && Objects.equals(photo, report.photo) && Objects.equals(record, report.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, chatId, status, photo, record);
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", chatId=" + chatId +
                ", status=" + status +
                ", photo=" + photo +
                ", record=" + record +
                '}';
    }
}
