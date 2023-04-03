package ru.skypro.shelterforanimals.handler;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import ru.skypro.shelterforanimals.handler.button_answers.CatButtonAnswers;
import ru.skypro.shelterforanimals.handler.button_answers.DogButtonAnswers;
import ru.skypro.shelterforanimals.handler.button_answers.VolunteerButtonAnswers;

import static ru.skypro.shelterforanimals.constants.BotButtonEnum.*;
import static ru.skypro.shelterforanimals.constants.BotButtonEnum.BUTTON_INSTRUCTION_CAT;


@Slf4j
@RequiredArgsConstructor
@Service
public class DataCheck {
    private final DogButtonAnswers dogButtonAnswers;
    private final CatButtonAnswers catButtonAnswers;
    private final VolunteerButtonAnswers volunteerButtonAnswers;

    /**
     * processes responses from the dog shelter menu buttons
     *
     * @param update
     */
    public void checkDogButtonAnswer(Update update) {
        log.info("checkDogButtonAnswer - DataCheck");
        if (update.callbackQuery().data().equals(COMMAND_INFORMATION_ABOUT_SHELTER_DOG.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_INSTRUCTION_DOG.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_RECORD.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_PET_PHOTO.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_HELP.getMessage())) {
            dogButtonAnswers.checkButtonAnswerDogs(update);
        } else if (update.callbackQuery().data().equals(COMMAND_INFORMATION_ABOUT_SHELTER.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_DRIVING_DIRECTIONS.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_ADDRESS_SHELTER.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_SAFETY_SHELTER.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_CALL_VOLUNTEER.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_WORK_SCHEDULE_SHELTER.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_LEAVE_DATA_FOR_COMMUNICATION.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_MEET_PET.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_DOC.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_TRANSPORTATION.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_PUPPY.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_ARRANGEMENT_DOG.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_ARRANGEMENT_INVALID.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_CYNOLOGIST.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_GOOD_CYNOLOGIST.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_REJECT.getMessage())) {
            dogButtonAnswers.sendResponseForFirstAndSecondMenuDogs(update);
        }
    }

    /**
     * processes responses from the cat shelter menu buttons
     *
     * @param update
     */
    public void checkCatButtonAnswer(Update update) {
        log.info("checkCatButtonAnswer - DataCheck");
        if (update.callbackQuery().data().equals(COMMAND_INFORMATION_ABOUT_SHELTER.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_INSTRUCTION_CAT.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_RECORD.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_PET_PHOTO.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_HELP.getMessage())) {
            catButtonAnswers.checkButtonAnswerCats(update);
        } else if (update.callbackQuery().data().equals(COMMAND_INFORMATION_ABOUT_SHELTER_CAT.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_DRIVING_DIRECTIONS.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_ADDRESS_SHELTER.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_SECURITY_CONTACT_CAT.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_SAFETY_SHELTER.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_CALL_VOLUNTEER.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_WORK_SCHEDULE_SHELTER.getMessage()) ||
                update.callbackQuery().data().equals(COMMAND_LEAVE_DATA_FOR_COMMUNICATION.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_MEET_PET.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_DOC.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_TRANSPORTATION.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_KITTY.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_ARRANGEMENT_CAT.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_ARRANGEMENT_INVALID.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_REJECT.getMessage())) {
            catButtonAnswers.sendResponseForFirstAndSecondMenuCats(update);
        }
    }

}
