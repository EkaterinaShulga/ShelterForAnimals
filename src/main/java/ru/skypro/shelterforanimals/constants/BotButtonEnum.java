package ru.skypro.shelterforanimals.constants;

/**
 * BotButtonEnum  - contains text for the buttons of all the bot menus
 */

public enum BotButtonEnum {

    BUTTON_GUEST("Пользователь"),
    BUTTON_EMPLOYEE("Волонтер"),
    BUTTON_INSTRUCTION_DOG("Как взять собаку из приюта"),
    BUTTON_INSTRUCTION_CAT("Как взять кошку из приюта"),
    BUTTON_RECORD("record"),
    BUTTON_PET_PHOTO("photo"),
    BUTTON_HELP("Позвать волонтера"),
    BUTTON_CAT_SHELTER("Приют для кошек"),
    BUTTON_DOG_SHELTER("Приют для собак"),

    COMMAND_INFORMATION_ABOUT_SHELTER("Информация о приюте"),

    COMMAND_INFORMATION_ABOUT_SHELTER_DOG("Узнать информацию о приюте"),
    COMMAND_INFORMATION_ABOUT_SHELTER_CAT("Информация о приюте для кошек"),
    COMMAND_WORK_SCHEDULE_SHELTER("Время работы приюта"),
    COMMAND_ADDRESS_SHELTER("Адрес приюта"),
    COMMAND_DRIVING_DIRECTIONS("Схема проезда"),
    COMMAND_SAFETY_SHELTER("Правила поведения в приюте"),
    COMMAND_LEAVE_DATA_FOR_COMMUNICATION("Оставить данные для связи"),

    COMMAND_CALL_VOLUNTEER("Связаться с волонтером"),

    BUTTON_MEET_PET("Правила знакомства с животным"),
    BUTTON_DOC("Список необходимых документов"),
    BUTTON_TRANSPORTATION("Как перевозить питомца"),
    BUTTON_PUPPY("Дом.условия для щенка"),
    BUTTON_KITTY("Дом.условия для котенка"),
    BUTTON_ARRANGEMENT_DOG("Дом.условия для собаки"),
    BUTTON_ARRANGEMENT_CAT("Дом.условия для кошки"),
    BUTTON_ARRANGEMENT_INVALID("Условия для животного-инвалида"),
    BUTTON_CYNOLOGIST("Рекомендации от кинологов"),
    BUTTON_GOOD_CYNOLOGIST("Хорошие кинологи"),
    BUTTON_REJECT("Спиоск причин отказа"),
    BUTTON_CONTACT(" Контактные данные "),
    COMMAND_SECURITY_CONTACT_CAT("Как получить пропуск"),


    BUTTON_CHECK_CONTACT(" Проверить контакты "),

    BUTTON_ADD_USER("Добавить усыновителя"),
    BUTTON_MESSAGE_FOR_USER("Сообщение усыновителю"),

    BUTTON_VOLUNTEER_FOR_CAT_SHELTER(" Меню волонтера приюта для кошек "),
    BUTTON_VOLUNTEER_FOR_DOG_SHELTER(" Меню волонтера приюта для собак "),

    BUTTON_VOLUNTEER_FOR_CAT("Волонтер приюта для кошек"),
    BUTTON_VOLUNTEER_FOR_DOG("Волонтер приюта для собак"),
    BUTTON_VOLUNTEER_ADD_PET("Добавить питомца"),
    BUTTON_VOLUNTEER_CHECK_REPORTS("Проверить отчеты"),

    BUTTON_PASSED_PROBATION_PERIOD("ИС пройден"),
    BUTTON_DID_NOT_PASSED_PROBATION_PERIOD("ИС не пройден"),
    BUTTON_EXTRA_TIME("Доп время"),
    BUTTON_BED_RECORD("Плохой отчет");

    private final String message;

    BotButtonEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


