package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    private static int validYear = Integer.parseInt(getCurrentYear()) + 1;
    private static String numberApprovedCard = "4444 4444 4444 4441";
    private static String numberDeclinedCard = "4444 4444 4444 4442";


    public static CardInfo generateDataWithApprovedCard() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithDeclineCard() {

        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberDeclinedCard, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithRandomCardNumber() {
        var randomName = faker.name().fullName();
        var randomCardNumber = faker.number().digits(16);
        var randomCvc = faker.number().digits(3);
        return  new CardInfo(randomCardNumber, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithApprovedCardAndParametrizedMonthAndYear(String month, String year) {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return  new CardInfo(numberApprovedCard, month, year, randomName, randomCvc);

    }

    public static CardInfo generateDataWithParamLengthCardOwnerName(int length) {
        var randomName = faker.lorem().fixedString(length);
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithParamCardOwnerName(String name) {
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), name, randomCvc);
    }

    public static CardInfo generateDataExpiredCardForOneMonth() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        var currentMonth = Integer.parseInt(getCurrentMonth());
        var currentYear = Integer.parseInt(getCurrentYear());
        if (currentMonth == 1) {
            currentMonth = 12;
            currentYear = currentYear - 1;
        } else currentMonth = currentMonth - 1;

        String minusOneFromCurrentMonth = "";
        if (currentMonth < 10) {
            minusOneFromCurrentMonth = "0" + currentMonth;
        }
        return  new CardInfo(numberApprovedCard, minusOneFromCurrentMonth,
                String.valueOf(currentYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithMaxDateMinusOneMonth() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        var currentMonth = Integer.parseInt(getCurrentMonth());
        var preMaxMonth = 0;
        var maxYear = Integer.parseInt(getCurrentYear()) + 5;

        if (currentMonth == 1) {
            preMaxMonth = 12;
            maxYear = maxYear - 1;
        } else preMaxMonth = currentMonth - 1;

        String strPreMaxMonth = "";
        if (preMaxMonth < 10) {
            strPreMaxMonth = "0" + preMaxMonth;
        }
        return  new CardInfo(numberApprovedCard, strPreMaxMonth,
                String.valueOf(maxYear), randomName, randomCvc);
    }

    public static String getCurrentMonth() {
        LocalDate date = LocalDate.now();
        String currentMonth = date.format(DateTimeFormatter.ofPattern("MM"));
        return currentMonth;
    }

    public static String getCurrentYear() {
        LocalDate date = LocalDate.now();
        String currentYear = date.format(DateTimeFormatter.ofPattern("yy"));
        return currentYear;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardInfo {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditCardData {
        private String id;
        private String bank_id; //пока будем брать данные из поля bank_id вместо id
        private String created;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentCardData {
        private String id;
        private String amount;
        private String created;
        private String status;
        private String transaction_id; //пока будем брать данные из поля transaction_id вместо id
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TableOrderEntity {
        private String id;
        private String created;
        private String credit_id;
        private String payment_id;
    }
}
