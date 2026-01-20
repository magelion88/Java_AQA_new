package pages.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentForm {

    private WebDriver driver;

    @FindBy(id = "connection-phone")
    private WebElement phoneInput;

    @FindBy(id = "connection-sum")
    private WebElement amountInput;

    @FindBy(xpath = "//button[text()='Продолжить']")
    private WebElement continueButton;

    public PaymentForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillForm(String phone, String amount) {
        phoneInput.clear();
        phoneInput.sendKeys(phone);

        amountInput.clear();
        amountInput.sendKeys(amount);
    }

    public void submit() {
        continueButton.click();
    }

    public boolean isSubmitEnabled() {
        return continueButton.isEnabled();
    }
}