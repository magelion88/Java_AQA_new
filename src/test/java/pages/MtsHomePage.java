package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class MtsHomePage extends BasePage {

    // Конструктор
    public MtsHomePage(WebDriver driver) {
        super(driver);
        driver.get("https://www.mts.by/");
    }

    // 1. Заголовок блока "Онлайн пополнение без комиссии"
    @FindBy(xpath = "//h2[normalize-space(text()) = 'Онлайн пополнение']")
    private WebElement onlinePaymentTitle;

    // 2. Логотипы платёжных систем
    @FindBy(xpath = "//div[@class='pay__partners']/ul/li")
    private List<WebElement> paymentLogos;

    // 3. Ссылка "Подробнее о сервисе"
    @FindBy(xpath = "//a[normalize-space(text())='Подробнее о сервисе']")
    private WebElement serviceDetailsLink;

    // 4. Cookie-баннер
    @FindBy(id = "cookie-agree")
    private WebElement cookieAgreeButton;

    // 5. Форма пополнения - ЭТИ ПОЛЯ ОБЯЗАТЕЛЬНЫ!
    @FindBy(id = "connection-phone")
    private WebElement phoneInput;

    @FindBy(id = "connection-sum")
    private WebElement amountInput;

    @FindBy(xpath = "//button[text()='Продолжить']")
    private WebElement continueButton;

    // Методы для работы с элементами

    public String getOnlinePaymentTitleText() {
        return onlinePaymentTitle.getText();
    }

    public boolean isOnlinePaymentTitleDisplayed() {
        return onlinePaymentTitle.isDisplayed();
    }

    public int getPaymentLogosCount() {
        return paymentLogos.size();
    }

    public List<WebElement> getPaymentLogos() {
        return paymentLogos;
    }

    public boolean isPaymentLogoDisplayed(int index) {
        if (index >= 0 && index < paymentLogos.size()) {
            return paymentLogos.get(index).findElement(org.openqa.selenium.By.tagName("img")).isDisplayed();
        }
        return false;
    }

    public String getPaymentLogoSrc(int index) {
        if (index >= 0 && index < paymentLogos.size()) {
            return paymentLogos.get(index).findElement(org.openqa.selenium.By.tagName("img")).getAttribute("src");
        }
        return null;
    }

    public void clickServiceDetailsLink() {
        serviceDetailsLink.click();
    }

    public boolean isServiceDetailsLinkDisplayed() {
        return serviceDetailsLink.isDisplayed();
    }

    public boolean isServiceDetailsLinkEnabled() {
        return serviceDetailsLink.isEnabled();
    }

    public String getServiceDetailsLinkText() {
        return serviceDetailsLink.getText();
    }

    public String getServiceDetailsLinkHref() {
        return serviceDetailsLink.getAttribute("href");
    }

    public void closeCookieBanner() {
        try {
            if (cookieAgreeButton.isDisplayed()) {
                cookieAgreeButton.click();
            }
        } catch (Exception e) {
            // Игнорируем если баннера нет
        }
    }

    // Метод fillPaymentForm - заполняет phoneInput и amountInput
    public void fillPaymentForm(String phone, String amount) {
        phoneInput.clear();
        phoneInput.sendKeys(phone);

        amountInput.clear();
        amountInput.sendKeys(amount);
    }

    public void clickContinueButton() {
        continueButton.click();
    }

    public boolean isContinueButtonEnabled() {
        return continueButton.isEnabled();
    }

    public String getContinueButtonText() {
        return continueButton.getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Метод для получения страницы онлайн оплаты
    public OnlinePaymentPage getOnlinePaymentPage() {
        return new OnlinePaymentPage(driver);
    }

    // Метод для получения popup страницы
    public PaymentPopupPage getPaymentPopupPage() {
        return new PaymentPopupPage(driver);
    }

    // Алиас для fillPaymentForm для ясности
    public void fillMobileServicesForm(String phoneNumber, String amount) {
        fillPaymentForm(phoneNumber, amount);
    }
}