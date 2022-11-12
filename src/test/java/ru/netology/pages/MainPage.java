package ru.netology.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;


public class MainPage {

    private SelenideElement heading = Selenide.$x("//h2[text()='Путешествие дня']");
   private SelenideElement paymentBtn = Selenide.$x("//span[text()='Купить']");

    private SelenideElement creditBtn = Selenide.$x("//span[text()='Купить в кредит']");

    public MainPage() {
        heading.shouldBe(visible);
    }



    public PaymentPage paymentPage() {
        paymentBtn.click();
        return new PaymentPage();
    }

    public CreditPage creditPage() {
        creditBtn.click();
        return new CreditPage();
    }
}
