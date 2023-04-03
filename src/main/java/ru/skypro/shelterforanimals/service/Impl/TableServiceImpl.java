package ru.skypro.shelterforanimals.service.Impl;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.shelterforanimals.service.TableService;

import static ru.skypro.shelterforanimals.constants.BotButtonEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    /**
     * start menu (for dog shelter) for the user
     *
     * @return InlineKeyboardMarkup
     */

    @Override
    public InlineKeyboardMarkup startMenuButtonsForShelterDog() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        var button1Info = new InlineKeyboardButton(COMMAND_INFORMATION_ABOUT_SHELTER_DOG.getMessage());
        var button2Instruction = new InlineKeyboardButton(BUTTON_INSTRUCTION_DOG.getMessage());
        var button3Record = new InlineKeyboardButton(BUTTON_RECORD.getMessage());
        var button4Photo = new InlineKeyboardButton(BUTTON_PET_PHOTO.getMessage());
        var button5Help = new InlineKeyboardButton(BUTTON_HELP.getMessage());

        button1Info.callbackData(COMMAND_INFORMATION_ABOUT_SHELTER_DOG.getMessage());
        button2Instruction.callbackData(BUTTON_INSTRUCTION_DOG.getMessage());
        button2Instruction.callbackData(BUTTON_INSTRUCTION_DOG.getMessage());
        button3Record.callbackData(BUTTON_RECORD.getMessage());
        button4Photo.callbackData(BUTTON_PET_PHOTO.getMessage());
        button5Help.callbackData(BUTTON_HELP.getMessage());

        markup.addRow(button1Info);
        markup.addRow(button2Instruction);
        markup.addRow(button3Record);
        markup.addRow(button4Photo);
        markup.addRow(button5Help);
        log.info("Стартовое меню приюта для собак отправлено");
        return markup;

    }

    /**
     * start menu (for cat shelter) for the user
     *
     * @return InlineKeyboardMarkup
     */
    @Override
    public InlineKeyboardMarkup startMenuButtonsForShelterCat() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        var button1Info = new InlineKeyboardButton(COMMAND_INFORMATION_ABOUT_SHELTER.getMessage());
        var button2Instruction = new InlineKeyboardButton(BUTTON_INSTRUCTION_CAT.getMessage());
        var button3Record = new InlineKeyboardButton(BUTTON_RECORD.getMessage());
        var button4PetPhoto = new InlineKeyboardButton(BUTTON_PET_PHOTO.getMessage());
        var button5Help = new InlineKeyboardButton(BUTTON_HELP.getMessage());

        button1Info.callbackData(COMMAND_INFORMATION_ABOUT_SHELTER.getMessage());
        button2Instruction.callbackData(BUTTON_INSTRUCTION_CAT.getMessage());
        button3Record.callbackData(BUTTON_RECORD.getMessage());
        button4PetPhoto.callbackData(BUTTON_PET_PHOTO.getMessage());
        button5Help.callbackData(BUTTON_HELP.getMessage());

        markup.addRow(button1Info);
        markup.addRow(button2Instruction);
        markup.addRow(button3Record);
        markup.addRow(button4PetPhoto);
        markup.addRow(button5Help);
        log.info("Меню отправлено");
        return markup;
    }

    /**
     * menu with information about the shelter(for dog) for the user
     *
     * @return InlineKeyboardMarkup
     */
    @Override
    public InlineKeyboardMarkup menuButtonsWithInformationAboutShelterForDog() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        var buttonAboutShelter = new InlineKeyboardButton(COMMAND_INFORMATION_ABOUT_SHELTER.getMessage());
        var buttonWorkTime = new InlineKeyboardButton(COMMAND_WORK_SCHEDULE_SHELTER.getMessage());
        var buttonAddress = new InlineKeyboardButton(COMMAND_ADDRESS_SHELTER.getMessage());
        var buttonWay = new InlineKeyboardButton(COMMAND_DRIVING_DIRECTIONS.getMessage());
        var buttonSafety = new InlineKeyboardButton(COMMAND_SAFETY_SHELTER.getMessage());
        var buttonContact = new InlineKeyboardButton(COMMAND_LEAVE_DATA_FOR_COMMUNICATION.getMessage());
        var buttonVolunteer = new InlineKeyboardButton(COMMAND_CALL_VOLUNTEER.getMessage());

        buttonAboutShelter.callbackData(COMMAND_INFORMATION_ABOUT_SHELTER.getMessage());
        buttonWorkTime.callbackData(COMMAND_WORK_SCHEDULE_SHELTER.getMessage());
        buttonAddress.callbackData(COMMAND_ADDRESS_SHELTER.getMessage());
        buttonWay.callbackData(COMMAND_DRIVING_DIRECTIONS.getMessage());
        buttonSafety.callbackData(COMMAND_SAFETY_SHELTER.getMessage());
        buttonContact.callbackData(COMMAND_LEAVE_DATA_FOR_COMMUNICATION.getMessage());
        buttonVolunteer.callbackData(COMMAND_CALL_VOLUNTEER.getMessage());

        markup.addRow(buttonAboutShelter);
        markup.addRow(buttonWorkTime);
        markup.addRow(buttonAddress);
        markup.addRow(buttonWay);
        markup.addRow(buttonSafety);
        markup.addRow(buttonContact);
        markup.addRow(buttonVolunteer);
        return markup;
    }


    /**
     * menu with information about the shelter(for cat) for the user
     *
     * @return InlineKeyboardMarkup
     */
    @Override
    public InlineKeyboardMarkup menuButtonsWithInformationAboutShelterForCat() {
        InlineKeyboardMarkup markupCat = new InlineKeyboardMarkup();
        var buttonAboutShelter = new InlineKeyboardButton(COMMAND_INFORMATION_ABOUT_SHELTER_CAT.getMessage());
        var buttonWorkTime = new InlineKeyboardButton(COMMAND_WORK_SCHEDULE_SHELTER.getMessage());
        var buttonAddress = new InlineKeyboardButton(COMMAND_ADDRESS_SHELTER.getMessage());
        var buttonWay = new InlineKeyboardButton(COMMAND_DRIVING_DIRECTIONS.getMessage());
        var buttonSecurityContact = new InlineKeyboardButton(COMMAND_SECURITY_CONTACT_CAT.getMessage());
        var buttonSafety = new InlineKeyboardButton(COMMAND_SAFETY_SHELTER.getMessage());
        var buttonContact = new InlineKeyboardButton(COMMAND_LEAVE_DATA_FOR_COMMUNICATION.getMessage());
        var buttonVolunteer = new InlineKeyboardButton(COMMAND_CALL_VOLUNTEER.getMessage());

        buttonAboutShelter.callbackData(COMMAND_INFORMATION_ABOUT_SHELTER_CAT.getMessage());
        buttonWorkTime.callbackData(COMMAND_WORK_SCHEDULE_SHELTER.getMessage());
        buttonAddress.callbackData(COMMAND_ADDRESS_SHELTER.getMessage());
        buttonWay.callbackData(COMMAND_DRIVING_DIRECTIONS.getMessage());
        buttonSecurityContact.callbackData(COMMAND_SECURITY_CONTACT_CAT.getMessage());
        buttonSafety.callbackData(COMMAND_SAFETY_SHELTER.getMessage());
        buttonContact.callbackData(COMMAND_LEAVE_DATA_FOR_COMMUNICATION.getMessage());
        buttonVolunteer.callbackData(COMMAND_CALL_VOLUNTEER.getMessage());

        markupCat.addRow(buttonAboutShelter);
        markupCat.addRow(buttonWorkTime);
        markupCat.addRow(buttonAddress);
        markupCat.addRow(buttonWay);
        markupCat.addRow(buttonSecurityContact);
        markupCat.addRow(buttonSafety);
        markupCat.addRow(buttonContact);
        markupCat.addRow(buttonVolunteer);
        return markupCat;
    }

    /**
     * menu with information about the rules of handling a dog for the user
     *
     * @return InlineKeyboardMarkup
     */
    @Override
    public InlineKeyboardMarkup menuButtonsWithInformationAboutGod() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        var button1Rules = new InlineKeyboardButton(BUTTON_MEET_PET.getMessage());
        var button2Docs = new InlineKeyboardButton(BUTTON_DOC.getMessage());
        var button3Transportation = new InlineKeyboardButton(BUTTON_TRANSPORTATION.getMessage());
        var button4ArrangementPuppy = new InlineKeyboardButton(BUTTON_PUPPY.getMessage());
        var button5ArrangementDog = new InlineKeyboardButton(BUTTON_ARRANGEMENT_DOG.getMessage());
        var button6ArrangementDogInvalid = new InlineKeyboardButton(BUTTON_ARRANGEMENT_INVALID.getMessage());
        var button7Cynologist = new InlineKeyboardButton(BUTTON_CYNOLOGIST.getMessage());
        var button8GoodCynologist = new InlineKeyboardButton(BUTTON_GOOD_CYNOLOGIST.getMessage());
        var button9Reject = new InlineKeyboardButton(BUTTON_REJECT.getMessage());
        var button10Contact = new InlineKeyboardButton(COMMAND_LEAVE_DATA_FOR_COMMUNICATION.getMessage());
        var button11Volunteer = new InlineKeyboardButton(BUTTON_HELP.getMessage());

        button1Rules.callbackData(BUTTON_MEET_PET.getMessage());
        button2Docs.callbackData(BUTTON_DOC.getMessage());
        button3Transportation.callbackData(BUTTON_TRANSPORTATION.getMessage());
        button4ArrangementPuppy.callbackData(BUTTON_PUPPY.getMessage());
        button5ArrangementDog.callbackData(BUTTON_ARRANGEMENT_DOG.getMessage());
        button6ArrangementDogInvalid.callbackData(BUTTON_ARRANGEMENT_INVALID.getMessage());
        button7Cynologist.callbackData(BUTTON_CYNOLOGIST.getMessage());
        button8GoodCynologist.callbackData(BUTTON_GOOD_CYNOLOGIST.getMessage());
        button9Reject.callbackData(BUTTON_REJECT.getMessage());
        button10Contact.callbackData(COMMAND_LEAVE_DATA_FOR_COMMUNICATION.getMessage());
        button11Volunteer.callbackData(BUTTON_HELP.getMessage());

        markup.addRow(button1Rules);
        markup.addRow(button2Docs);
        markup.addRow(button3Transportation);
        markup.addRow(button4ArrangementPuppy);
        markup.addRow(button5ArrangementDog);
        markup.addRow(button6ArrangementDogInvalid);
        markup.addRow(button7Cynologist);
        markup.addRow(button8GoodCynologist);
        markup.addRow(button9Reject);
        markup.addRow(button10Contact);
        markup.addRow(button11Volunteer);
        return markup;
    }


    /**
     * menu with information about the rules of handling a cat for the user
     *
     * @return InlineKeyboardMarkup
     */

    @Override
    public InlineKeyboardMarkup menuButtonsWithInformationAboutCat() {
        InlineKeyboardMarkup markupCat = new InlineKeyboardMarkup();

        var button1Rules = new InlineKeyboardButton(BUTTON_MEET_PET.getMessage());
        var button2Docs = new InlineKeyboardButton(BUTTON_DOC.getMessage());
        var button3Transportation = new InlineKeyboardButton(BUTTON_TRANSPORTATION.getMessage());
        var button4ArrangementKitty = new InlineKeyboardButton(BUTTON_KITTY.getMessage());
        var button5ArrangementCat = new InlineKeyboardButton(BUTTON_ARRANGEMENT_CAT.getMessage());
        var button6ArrangementCatInvalid = new InlineKeyboardButton(BUTTON_ARRANGEMENT_INVALID.getMessage());
        var button7Reject = new InlineKeyboardButton(BUTTON_REJECT.getMessage());
        var button8Contact = new InlineKeyboardButton(COMMAND_LEAVE_DATA_FOR_COMMUNICATION.getMessage());
        var button9Volunteer = new InlineKeyboardButton(BUTTON_HELP.getMessage());

        button1Rules.callbackData(BUTTON_MEET_PET.getMessage());
        button2Docs.callbackData(BUTTON_DOC.getMessage());
        button3Transportation.callbackData(BUTTON_TRANSPORTATION.getMessage());
        button4ArrangementKitty.callbackData(BUTTON_KITTY.getMessage());
        button5ArrangementCat.callbackData(BUTTON_ARRANGEMENT_CAT.getMessage());
        button6ArrangementCatInvalid.callbackData(BUTTON_ARRANGEMENT_INVALID.getMessage());
        button7Reject.callbackData(BUTTON_REJECT.getMessage());
        button8Contact.callbackData(COMMAND_LEAVE_DATA_FOR_COMMUNICATION.getMessage());
        button9Volunteer.callbackData(BUTTON_HELP.getMessage());

        markupCat.addRow(button1Rules);
        markupCat.addRow(button2Docs);
        markupCat.addRow(button3Transportation);
        markupCat.addRow(button4ArrangementKitty);
        markupCat.addRow(button5ArrangementCat);
        markupCat.addRow(button6ArrangementCatInvalid);
        markupCat.addRow(button7Reject);
        markupCat.addRow(button8Contact);
        markupCat.addRow(button9Volunteer);
        return markupCat;
    }

    /**
     * menu with two buttons: user/volunteer
     *
     * @return InlineKeyboardMarkup
     */
    @Override
    public InlineKeyboardMarkup userStatusMenuButtons() {
        log.info("Кнопки выбора статуса Пользователь/Волонтер  - tableServiceImpl");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        var buttonGuest = new InlineKeyboardButton(BUTTON_GUEST.getMessage());
        var buttonEmployee = new InlineKeyboardButton(BUTTON_EMPLOYEE.getMessage());

        buttonGuest.callbackData(BUTTON_GUEST.getMessage());
        buttonEmployee.callbackData(BUTTON_EMPLOYEE.getMessage());

        markup.addRow(buttonGuest);
        markup.addRow(buttonEmployee);
        return markup;
    }

    /**
     * menu with two buttons: shelter for cat/shelter for dog - for user
     *
     * @return InlineKeyboardMarkup
     */
    @Override
    public InlineKeyboardMarkup shelterStatusMenuButtons() {
        log.info("Кнопки выбора приюта - volunteerServiceImpl");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        var buttonCatShelter = new InlineKeyboardButton(BUTTON_CAT_SHELTER.getMessage());
        var buttonDogShelter = new InlineKeyboardButton(BUTTON_DOG_SHELTER.getMessage());

        buttonCatShelter.callbackData(BUTTON_CAT_SHELTER.getMessage());
        buttonDogShelter.callbackData(BUTTON_DOG_SHELTER.getMessage());

        markup.addRow(buttonCatShelter);
        markup.addRow(buttonDogShelter);
        return markup;
    }

    /**
     * menu with volunteer functionality
     *
     * @return InlineKeyboardMarkup
     */
    @Override
    public InlineKeyboardMarkup volunteerFunctionality() {
        log.info("volunteerFunctionality - tableServiceImpl");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        var buttonCheckContacts = new InlineKeyboardButton(BUTTON_CHECK_CONTACT.getMessage());
        var buttonAddUser = new InlineKeyboardButton(BUTTON_ADD_USER.getMessage());
        var buttonCheckReports = new InlineKeyboardButton(BUTTON_VOLUNTEER_CHECK_REPORTS.getMessage());
        var buttonMakeDecisionOnProbation = new InlineKeyboardButton(BUTTON_MESSAGE_FOR_USER.getMessage());
        var buttonAddPet = new InlineKeyboardButton(BUTTON_VOLUNTEER_ADD_PET.getMessage());

        buttonCheckContacts.callbackData(BUTTON_CHECK_CONTACT.getMessage());
        buttonAddUser.callbackData(BUTTON_ADD_USER.getMessage());
        buttonCheckReports.callbackData(BUTTON_VOLUNTEER_CHECK_REPORTS.getMessage());
        buttonMakeDecisionOnProbation.callbackData(BUTTON_MESSAGE_FOR_USER.getMessage());
        buttonAddPet.callbackData(BUTTON_VOLUNTEER_ADD_PET.getMessage());

        markup.addRow(buttonCheckContacts);
        markup.addRow(buttonAddUser);
        markup.addRow(buttonCheckReports);
        markup.addRow(buttonMakeDecisionOnProbation);
        markup.addRow(buttonAddPet);

        return markup;
    }


    /**
     * menu with two buttons: volunteer of the shelter for cats/<br>
     * volunteer of the shelter for cats
     *
     * @return InlineKeyboardMarkup
     */

    @Override
    public InlineKeyboardMarkup menuForVolunteer() {
        log.info("menuForVolunteer - volunteerServiceImpl");
        InlineKeyboardMarkup markup2 = new InlineKeyboardMarkup();
        var buttonVolunteerForCat = new InlineKeyboardButton(BUTTON_VOLUNTEER_FOR_CAT.getMessage());
        var buttonVolunteerForDog = new InlineKeyboardButton(BUTTON_VOLUNTEER_FOR_DOG.getMessage());


        buttonVolunteerForCat.callbackData(BUTTON_VOLUNTEER_FOR_CAT.getMessage());
        buttonVolunteerForDog.callbackData(BUTTON_VOLUNTEER_FOR_DOG.getMessage());

        markup2.addRow(buttonVolunteerForCat);
        markup2.addRow(buttonVolunteerForDog);


        return markup2;
    }

    /**
     * menu for the volunteer, a list of messages to notify the user
     *
     * @return InlineKeyboardMarkup
     */
    @Override
    public InlineKeyboardMarkup makeButtonsMessagesToUserAboutProbationPeriod() {
        log.info("makeButtonsMessagesToUserAboutProbationPeriod - volunteerServiceImpl");
        InlineKeyboardMarkup markup3 = new InlineKeyboardMarkup();
        var buttonPassedProbationPeriod = new InlineKeyboardButton(BUTTON_PASSED_PROBATION_PERIOD.getMessage());
        var buttonDidNotPassedProbationPeriod = new InlineKeyboardButton(BUTTON_DID_NOT_PASSED_PROBATION_PERIOD.getMessage());
        var buttonExtraTime = new InlineKeyboardButton(BUTTON_EXTRA_TIME.getMessage());
        var buttonBadRecord = new InlineKeyboardButton(BUTTON_BED_RECORD.getMessage());

        buttonPassedProbationPeriod.callbackData(BUTTON_PASSED_PROBATION_PERIOD.getMessage());
        buttonDidNotPassedProbationPeriod.callbackData(BUTTON_DID_NOT_PASSED_PROBATION_PERIOD.getMessage());
        buttonExtraTime.callbackData(BUTTON_EXTRA_TIME.getMessage());
        buttonBadRecord.callbackData(BUTTON_BED_RECORD.getMessage());

        markup3.addRow(buttonPassedProbationPeriod);
        markup3.addRow(buttonDidNotPassedProbationPeriod);
        markup3.addRow(buttonExtraTime);
        markup3.addRow(buttonBadRecord);


        return markup3;
    }


}




