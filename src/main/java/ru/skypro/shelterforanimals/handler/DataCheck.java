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
     * @param update
     */
    public void checkDogButtonAnswer(Update update) {
        log.info("checkDogButtonAnswer - DataCheck");
        if (update.callbackQuery().data().equals(BUTTON_INFO.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_INSTRUCTION_DOG.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_RECORD.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_PET_PHOTO.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_HELP.getMessage())) {
            dogButtonAnswers.checkButtonAnswerDogs(update);
        } else if (update.callbackQuery().data().equals("info") ||
                update.callbackQuery().data().equals("way") ||
                update.callbackQuery().data().equals("address") ||
                update.callbackQuery().data().equals("safety") ||
                update.callbackQuery().data().equals("volunteer") ||
                update.callbackQuery().data().equals("workTime") ||
                update.callbackQuery().data().equals("contact") ||
                update.callbackQuery().data().equals("rules") ||
                update.callbackQuery().data().equals("docs") ||
                update.callbackQuery().data().equals("transportation") ||
                update.callbackQuery().data().equals("arrangementPuppy") ||
                update.callbackQuery().data().equals("arrangementDog") ||
                update.callbackQuery().data().equals("arrangementDogInvalid") ||
                update.callbackQuery().data().equals("cynologist") ||
                update.callbackQuery().data().equals("goodCynologists") ||
                update.callbackQuery().data().equals("reject")) {
            dogButtonAnswers.sendResponseForFirstAndSecondMenuDogs(update);
        }
    }
    /**
     * processes responses from the cat shelter menu buttons
     * @param update
     */
    public void checkCatButtonAnswer(Update update) {
        log.info("checkCatButtonAnswer - DataCheck");
        if (update.callbackQuery().data().equals(BUTTON_INFO.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_INSTRUCTION_CAT.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_RECORD.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_PET_PHOTO.getMessage()) ||
                update.callbackQuery().data().equals(BUTTON_HELP.getMessage())) {
            catButtonAnswers.checkButtonAnswerCats(update);
        } else if (update.callbackQuery().data().equals("infoCat") ||
                update.callbackQuery().data().equals("wayCat") ||
                update.callbackQuery().data().equals("addressCat") ||
                update.callbackQuery().data().equals("securityContactCat") ||
                update.callbackQuery().data().equals("safetyCat") ||
                update.callbackQuery().data().equals("volunteerCat") ||
                update.callbackQuery().data().equals("workTimeCat") ||
                update.callbackQuery().data().equals("contactCat") ||
                update.callbackQuery().data().equals("rulesCat") ||
                update.callbackQuery().data().equals("docsCat") ||
                update.callbackQuery().data().equals("transportationCat") ||
                update.callbackQuery().data().equals("arrangementKitty") ||
                update.callbackQuery().data().equals("arrangementCat") ||
                update.callbackQuery().data().equals("arrangementCatDisabled") ||
                update.callbackQuery().data().equals("rejectCat")) {
            catButtonAnswers.sendResponseForFirstAndSecondMenuCats(update);
        }
    }

}
