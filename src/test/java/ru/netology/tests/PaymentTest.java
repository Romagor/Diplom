package ru.netology.tests;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;


import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {

    MainPage mainPage = open("http://localhost:8080/", MainPage.class);

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card")
    void shouldSuccessTransactionWithPaymentCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }


    @Test
    @DisplayName("Should decline payment card with declined test card")
    void shouldNotSuccessTransactionWithPaymentCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with month by one digit")
    void shouldSuccessTransactionWithMonthWithoutZero() {
        var toPaymentPage = mainPage.paymentPage();
        var validYear = Integer.parseInt(DataHelper.getCurrentYear()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                ("5", String.valueOf(validYear));
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and max date")
    void shouldSuccessTransactionWithMaxAllowedDate() {
        var toPaymentPage = mainPage.paymentPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(maxYear));
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and max date minus 1 month")
    void shouldSuccessTransactionWithPreMaxAllowedDate() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithMaxDateMinusOneMonth();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and min date(current month)")
    void shouldSuccessTransactionWithMinAllowedDate() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                (DataHelper.getCurrentMonth(),DataHelper.getCurrentYear());
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and min date next month)")
    void shouldSuccessTransactionWithPreMinAllowedDate() {
        var toPaymentPage = mainPage.paymentPage();
        var nextMonth = Integer.parseInt(DataHelper.getCurrentMonth()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                (String.valueOf(nextMonth),DataHelper.getCurrentYear());
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and max length card owner's name")
    void shouldSuccessTransactionMaxLengthCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(21);
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and min length card owner's name")
    void shouldSuccessTransactionMinLengthCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(3);
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should decline payment card with random test card")
    void shouldDeclineWithRandomPaymentCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should to show red warning with empty card number field")
    void shouldShowMessWithEmptyCardNumberField() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setNumber("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardNumberField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty month field")
    void shouldShowMessWithEmptyMonthField() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setMonth("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonthField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty year field")
    void shouldShowMessWithEmptyYearField() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setYear("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYearField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty card owner field")
    void shouldShowMessWithEmptyCardOwnerField() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setHolder("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Should to show red warning with empty cvc field")
    void shouldShowMessWithEmptyCvcField() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty all field")
    void shouldShowMessWithEmptyAllField() {
        var toPaymentPage = mainPage.paymentPage();
        toPaymentPage.clickProceedButton();
        toPaymentPage.checkWarningUnderCardNumberField("Неверный формат");
        toPaymentPage.checkWarningUnderMonthField("Неверный формат");
        toPaymentPage.checkWarningUnderYearField("Неверный формат");
        toPaymentPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
        toPaymentPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    @DisplayName("Should not to show red warning with empty all field after filled field")
    void shouldNotShowMessAfterEmptyAllField() {
        var toPaymentPage = mainPage.paymentPage();
        toPaymentPage.clickProceedButton();
        toPaymentPage.checkWarningUnderCardNumberField("Неверный формат");
        toPaymentPage.checkWarningUnderMonthField("Неверный формат");
        toPaymentPage.checkWarningUnderYearField("Неверный формат");
        toPaymentPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
        toPaymentPage.checkWarningUnderCvcField("Неверный формат");

        var cardInfo = DataHelper.generateDataWithApprovedCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
        toPaymentPage.notCheckWarningUnderAllFields();
    }

    @Test
    @DisplayName("Should to show red warning with expired card for year")
    void shouldShowMessWithExpiredCardForYear() {
        var toPaymentPage = mainPage.paymentPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var lastYear = Integer.parseInt(DataHelper.getCurrentYear()) - 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(lastYear));
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYearField("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with expired card for month")
    void shouldShowMessWithExpiredCardForMonth() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataExpiredCardForOneMonth();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with 00 month")
    void shouldShowMessWithZeroZeroMonth() {
        var toPaymentPage = mainPage.paymentPage();
        var validYear = Integer.parseInt(DataHelper.getCurrentYear()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                ("00", String.valueOf(validYear));
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with invalid month data")
    void shouldShowMessWithInvalidMonthData() {
        var toPaymentPage = mainPage.paymentPage();
        var currentYear = DataHelper.getCurrentYear();

        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("78",
                currentYear);
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should o show red warning with more max length card owner's name by one char")
    void shouldShowMessWithMoreMaxLengthCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(22);
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("Имя не должно быть длинее 21 символа");
    }

    @Test
    @DisplayName("Should o show red warning with less min length card owner's name by one char")
    void shouldShowMessWithLessMinLengthCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(2);
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("Имя не должно быть короче 3 символов");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name is written in Cyrillic")
    void shouldShowMessWithCyrillicCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("ИВАНОВ ФЁДОР");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("Неверный формат");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name with numbers")
    void shouldShowMessWithNumbersCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("ИВАН08456 ФЁДОР");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("Неверный формат");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name with special characters")
    void shouldShowMessWithSpecCharactersCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("@#%$^$%&>??<");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("Неверный формат");
    }

}
