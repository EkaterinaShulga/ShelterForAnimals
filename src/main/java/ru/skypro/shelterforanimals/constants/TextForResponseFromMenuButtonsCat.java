package ru.skypro.shelterforanimals.constants;

/**
 * TextForResponseFromMenuButtonsCat - contains the text of the response to the buttons of the cat menu
 * with information about the shelter for cats
 */

public enum TextForResponseFromMenuButtonsCat {

    INFORMATION_ABOUT_SHELTER_CAT("Муркоша - единственный adoption центр для кошек в России," +
            "который комплексно подходит к вопросу спасения животных. Все сотрудники нашей команды – " +
            "в прошлом волонтеры, за плечами каждого из них десятки спасенных жизней. На собственном опыте," +
            "методом проб и ошибок, мы разработали систему, при которой у любой кошки – будь это возрастной " +
            "питомец или животное-инвалид – появляется шанс стать домашней и счастливой"),

    WORK_TIME_SHELTER_CAT("пн-вс: 09:00 - 21:00"),

    WAY_SHELTER_CAT("Если вы вышли в 1-ый вагон из центра," +
            " то при выходе из метро идите направо до светофора, перейти узкую улочку, далее еще раз переходим дорогу" +
            " и двигаемся налево до второй по ходу движения остановки. 3 остановки на 601 автобусе от остановки \"м." +
            " Медведково\" (Широкая улица, 16А) до остановки \"Погранинститут\". Переходим на другую сторону дороги " +
            "и двигаемся налево 100 метров до проходной (коричневое одноэтажное здание по адресу ул. Осташковская " +
            "14с6)."),
    ADDRESS_SHELTER_CAT("Москва, ул. Осташковская, д. 14, стр. 2"),

    SECURITY_CONTACT_CAT("С проходной позвоните нам со стационарного телефона, мы закажем вам пропуск." +
            "У нас есть бесплатная парковка!"),

    SAFETY_CAT("(!) Обязательно захватите с собой паспорт, на территории действует пропускная система." +
            " Документ, удостоверяющий личность, должен быть у каждого посетителя."),

    RULES_FOR_DATING_CAT("Кошки – независимые животные с чувством собственного достоинства, " +
            "предпочитают быть с человеком на равных. Поэтому знакомиться или здороваться с ней нужно" +
            " на кошачьем языке.При первой встрече, протяните кошке руку, чтобы та её обнюхала," +
            " поняла, что опасности здесь нет. Если всё пройдёт гладко, кошка либо потрётся о вашу " +
            "руку мордочкой, либо повернётся хвостом и можно будет почесать её спинку."),

    LIST_DOCUMENTS_FOR_CAT("Ветеринарный паспорт с отметками о вакцинации"),

    RECOMMENDATIONS_FOR_TRANSPORTATION("Самыми удобными для перемещения в транспорте являются пластиковые боксы." +
            " Они достаточно громоздкие, но отлично подойдут для провоза как одного, " +
            "так и двух пушистых друзей одновременно."),

    RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_KITTEN("Уберите мелкие предметы. " +
            "Подготовьте лоток и место для приема пищи. Приготовьте игрушки для котенка"),

    RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_ADULT_CAT(
            "Подготовьте лоток и место для приема пищи. Чтобы обеспечить комфорт, приобретите спальное место, " +
                    "так как животное большую часть дня проводит во сне. Главное, чтобы место было мягким и теплым," +
                    " тогда питомец будет проводить там больше времени, так как ему будет уютно. Можно купить гамак," +
                    " который крепится к батарее или домик. После того, как спальное место уже куплено," +
                    " не надо насильно сажать животное, дайте время привыкнуть и обнюхаться. " +
                    "Для проявления интереса можно во внутрь положить немного мяты."),

    RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_DISABLED_CAT("Крайне важно играть с кошкой-инвалидом, как с обычным питомцем. " +
            "Можно оборудовать многоярусный домик, по которому животному будет передвигаться и ползать."),

    REASONS_FOR_REFUSAL_CAT("""
            Отказ обеспечить безопасность питомца на новом месте.
            
            Антинаучное мышление.
            
            Маленькие дети в семье.
            
            Аллергия.
            
            Нестабильные отношения в семье.
            
            Наличие дома большого количества животных.
            
            Животное забирают в подарок кому-то.
            
            Претендент — пожилой человек, проживающий один. 
            
            Животное забирают в целях использования его рабочих качеств. 
            
            Отказ приехать познакомиться с животным.
            
            Отсутствие регистрации и собственного жилья или его несоответствие нормам приюта.
            
            Без объяснения причин.
            """);

    private final String message;

    TextForResponseFromMenuButtonsCat(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
