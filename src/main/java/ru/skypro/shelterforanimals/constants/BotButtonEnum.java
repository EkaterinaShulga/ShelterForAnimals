package ru.skypro.shelterforanimals.constants;

public enum BotButtonEnum {

    BUTTON_GUEST("Пользователь"),
    BUTTON_EMPLOYEE("Волонтер"),
    BUTTON_INFO("Узнать информацию о приюте"),
    BUTTON_INSTRUCTION_DOG("Как взять собаку из приюта"),
    BUTTON_INSTRUCTION_CAT("Как взять кошку из приюта"),
    // BUTTON_RECORD("Прислать отчет о питомце"),
    BUTTON_RECORD("record"),

    //  BUTTON_PET_PHOTO("Прислать фото питомца"),
    BUTTON_PET_PHOTO("photo"),
    BUTTON_HELP("Позвать волонтера"),
    BUTTON_CAT_SHELTER("Приют для кошек"),
    BUTTON_DOG_SHELTER("Приют для собак"),


    BUTTON_KNOW (" Правила знакомства с животным "),
    BUTTON_DOC ( " Список необходимых документов "),
    BUTTON_TRANSPORTATION ( " Рекомендация по транспортировке "),
    BUTTON_PUPPY ( " Как подготовить дом для щенка "),
    BUTTON_KITTY( " Как подготовить дом для котенка "),
    BUTTON_ARRANGEMENT_DOG( " Как подготовить дом для взрослой собаки "),
    BUTTON_ARRANGEMENT_CAT( " Как подготовить дом для взрослой кошки "),
    BUTTON_ARRANGEMENT_INVALID( " Как подготовить дом для животного с " +
            " ограниченными возможностями "),
    BUTTON_CYNOLOGIST( " Рекомендации от кинологов "),
    BUTTON_GOOD_CYNOLOGIST( " Хорошие кинологи "),
    BUTTON_REJECT( " Спиоск причин отказа "),
    BUTTON_CONTACT( " Контактные данные "),
    COMMAND_SECURITY_CONTACT_CAT("Контакты охраны для оформления пропуска"),

    BUTTON_CHECK_CONTACT( " Проверить контакты "),

    BUTTON_ADD_USER( " Добавить усыновителя "),
    BUTTON_CHECK_REPORTS( " Проверить отчеты "),
    BUTTON_BED_REPORT( " Сообщить о плохо заполняемом отчете "),
    BUTTON_MAKE_DECISION_ON_PROBATION( " Принять решение об испытательном сроке "),

    BUTTON_VOLUNTEER_FOR_CAT_SHELTER( " Меню волонтера приюта для кошек "),
    BUTTON_VOLUNTEER_FOR_DOG_SHELTER( " Меню волонтера приюта для собак "),

    BUTTON_VOLUNTEER_FOR_CAT( "Волонтер приюта для кошек"),
    BUTTON_VOLUNTEER_FOR_DOG( "Волонтер приюта для собак"),
    BUTTON_VOLUNTEER_ADD_PET( " Добавить питомца "),
    BUTTON_VOLUNTEER_CHECK_REPORTS( " Проверить отчеты ");

    private final String message;

    BotButtonEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


