package ru.skypro.shelterforanimals.constants;


/**
 * BotMessageVolunteer - contains text for responding to the volunteer's menu buttons
 * and information for users from the volunteer
 */

public enum BotMessageVolunteer {
    MESSAGE_FOR_ADOPTER("Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
            "Пожалуйста подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично " +
            "проверять уловия содеражния питомца."),

    ANSWER_FOR_BUTTON_SAVE_USER("""

            Если вы хотите добавить усыновителя в базу данных, введите информацию строго в соотвествии с шаблоном (кличка животного с большой буквы,
             номер телефона усыновителя, ФИО усыновителя):\s
            Шарик 89061855572 Иванов Иван Иванович
            """),

    ANSWER_FOR_BUTTON_SAVE_PET("""

            Если вы хотите добавить питомца в базу данных, введите информацию строго в соотвествии с шаблоном (кличка животного с большой буквы,
             окрас животного, возраст животного):\s
            Рыжик полосатый четыре месяца
            """),

    ANSWER_FOR_BUTTON_SAVE_PET_ERROR_IN_TEMPLATE("""

            Не могу сохранить данные в базе, введенная информация не соответствует шаблону\s
            Барсик полосатый четыре месяца
            """),
    ANSWER_FOR_BUTTON_SAVE_PET_CAN_NOT_SAVE("\"Такой питомец уже есть в базе данных\""),

    ANSWER_FOR_BUTTON_PROBATION_PERIOD("Уведомление усыновителя об ИС"),
    ANSWER_FOR_BUTTON_PROBATION_PERIOD_SEND_CHAT_ID("""
            Введите чат усыновителя как указано в примере \s
            Пример: chatOk 1300060749
            \s
             и бот отправит ему сообщение о завершении испытательного срока"""),

    ANSWER_FOR_BUTTON_PROBATION_PERIOD_NOT_IS_OVER_SEND_CHAT_ID("""
            Введите чат усыновителя как указано в примере \s
            Пример: chatNotIsOver 1300060749
            \s
             и бот отправит ему сообщение о том, что испытательный срок не пройден"""),

    ANSWER_FOR_BUTTON_EXTRA_TIME("""
            Введите чат усыновителя\s
             как указано в примере \s
            Пример: extraTime 1300060749
            \s
             и бот отправит ему сообщение о том, что испытательный срок продлен"""),

    ANSWER_FOR_BUTTON_BAD_REPORT("""
            Введите чат усыновителя\s
             как указано в примере \s
            Пример: badReport 1300060749
            \s
             и бот отправит ему сообщение о том, что усыновитель плохо составляет отчеты"""),

    ANSWER_FOR_BUTTON_PROBATION_PERIOD_IS_OVER_FOR_USER(" , вы прошли испытательный срок," +
            "поздравляем! Присылать отчеты больше не нужно, но мы будем рады если вы решите сообщить нам о новых " +
            "достижениях вашего питомца!"),

    ANSWER_FOR_BUTTON_PROBATION_PERIOD_NOT_IS_OVER(" , к сожалению вы непрошли испытательный срок," +
            " свяжитесь пожалуйста с волонтером."),
    ANSWER_FOR_BUTTON_EXTRA_TIME_FOR_USER(" , ваш испытательный срок продлен," +
            "связитесь пожалуйста с волонтером");

    BotMessageVolunteer(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage() {
        return message;
    }
}

