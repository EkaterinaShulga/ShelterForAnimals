package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.entity.Client;
import ru.skypro.shelterforanimals.entity.PetPhoto;
import ru.skypro.shelterforanimals.entity.Report;
import ru.skypro.shelterforanimals.entity.Record;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.repository.*;
import ru.skypro.shelterforanimals.service.RecordService;
import ru.skypro.shelterforanimals.service.ReportService;

import java.util.ArrayList;
import java.util.List;

import static ru.skypro.shelterforanimals.constants.BotMessageEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final TelegramBot telegramBot;
    private final RecordRepository recordRepository;
    private final ReportRepository reportRepository;
    private final VolunteerRepository volunteerRepository;
    private final ClientRepository clientRepository;
    private  final PetPhotoRepository petPhotoRepository;
    private final RecordService recordService;


    @Override
    public void saveReport (Update update) {
        log.info("Процесс сохранения отчета");
        long chatId = update.message().chat().id();
        recordService.saveRecord(update);
        Client client = clientRepository.findByChatId(chatId);
        int status = client.getStatus();
        Record record = recordRepository.findRecordByChatId(chatId);
        PetPhoto photo = petPhotoRepository.findPetPhotoByPetPhotoId(chatId);
        // int status = photo.getStatus();
        /*Volunteer volunteer = volunteerRepository.findByChatId(chatId);
        int statusVolunteer = volunteer.getStatus();*/
        Report report = new Report();
        report.setReportId(report.getReportId());
        report.setChatId(chatId);
        report.setStatus(status);
        report.setRecord(record);
        report.setPhoto(photo);
        report.setRecord(record);
        reportRepository.save(report);

        telegramBot.execute(new SendMessage(chatId, SAVE_INFORMATION.getMessage()));
        log.info(" отчет занесен в базу данных");
    }




    @Override
    public void getReportsForVolunteer(Update update){
        log.info("Процесс поиска отчетов - getRecordsForVolunteer");
        Volunteer volunteer = volunteerRepository.findByChatId(update.callbackQuery().message().chat().id());
        int status = volunteer.getStatus();
        telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                "Отчеты об усыновленных питомцах"));
        List<Report> allReports = reportRepository.findAll();
        List<Record> allRecords = new ArrayList<>();
        List<PetPhoto> allPhotos = new ArrayList<>();
        //  List<Report> reports = new ArrayList<>();
        for(Report report: allReports){
            if(report.getStatus() == status){
                allRecords.add(report.getRecord());
                allPhotos.add(report.getPhoto());
                //   reports.add(report);
            }
        }
        if(allRecords.isEmpty()){
            telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                    NOT_FIND_REPORTS.getMessage()));
            log.info("В базе не найдено отчетов");
        }
        else {
            telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                    FIND_REPORTS.getMessage() + allRecords + " " + allPhotos));

            log.info("В базе найдены отчеты");
        }

    }


}
