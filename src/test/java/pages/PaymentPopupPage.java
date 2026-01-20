package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PaymentPopupPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Popup окно - теперь ищем по более общим признакам
    @FindBy(xpath = "//div[@role='dialog' or contains(@class, 'modal') or contains(@class, 'popup')]")
    private List<WebElement> popupContainers;

    // Номер телефона в popup - ищем по тексту
    @FindBy(xpath = "//*[contains(text(), '297777777') or contains(text(), '375297777777') or contains(text(), 'Оплата:')]")
    private List<WebElement> phoneNumberElements;

    // Сумма платежа
    @FindBy(xpath = "//*[contains(text(), '200') and contains(text(), 'BYN')]")
    private List<WebElement> paymentAmountElements;

    // Поле номера карты
    @FindBy(xpath = "//input[contains(@id, 'card') or contains(@name, 'card') or contains(@placeholder, 'карт')]")
    private List<WebElement> cardNumberFields;

    // Кнопка оплаты
    @FindBy(xpath = "//button[contains(text(), 'Оплатить')]")
    private List<WebElement> payButtons;

    // Кнопка закрытия popup (крестик)
    @FindBy(xpath = "//button[@aria-label='Close'] | //*[contains(@class, 'close')] | //*[text()='×']")
    private List<WebElement> closeButtons;

    public PaymentPopupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Проверка, что popup отображается
    public boolean isPopupDisplayed() {
        try {
            // Даем время для появления
            Thread.sleep(1000);

            // Вариант 1: Ищем кнопку "Оплатить" в popup
            List<WebElement> payButtonsInPopup = driver.findElements(
                    By.xpath("//button[contains(text(), 'Оплатить') and contains(text(), '200')]")
            );

            // Вариант 2: Ищем поля карты
            List<WebElement> cardFields = driver.findElements(
                    By.xpath("//input[contains(@placeholder, 'карт') or contains(@placeholder, 'card')]")
            );

            return !payButtonsInPopup.isEmpty() || !cardFields.isEmpty();

        } catch (Exception e) {
            return false;
        }
    }

    // Ожидание появления popup
    public void waitForPopupToAppear() {
        wait.until(driver -> {
            try {
                // Ищем видимую кнопку "Оплатить" с суммой
                List<WebElement> buttons = driver.findElements(
                        By.xpath("//button[contains(text(), 'Оплатить')]")
                );

                for (WebElement button : buttons) {
                    if (button.isDisplayed() && button.getText().contains("200")) {
                        return true;
                    }
                }

                // Или ищем видимые поля карты
                List<WebElement> fields = driver.findElements(
                        By.xpath("//input[contains(@placeholder, 'карт')]")
                );

                for (WebElement field : fields) {
                    if (field.isDisplayed()) {
                        return true;
                    }
                }

                return false;

            } catch (Exception e) {
                return false;
            }
        });
    }

    // Получение текста с номером телефона
    public String getPhoneNumberText() {
        try {
            waitForPopupToAppear();

            // Ищем текст с номером
            List<WebElement> elements = driver.findElements(
                    By.xpath("//*[contains(text(), '297777777') or contains(text(), '375') or contains(text(), 'Оплата:')]")
            );

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return element.getText();
                }
            }
            return "Номер телефона не найден";
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    // Получение суммы платежа
    public String getPaymentAmountText() {
        try {
            // Ищем сумму
            List<WebElement> elements = driver.findElements(
                    By.xpath("//*[contains(text(), '200') and contains(text(), 'BYN')]")
            );

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return element.getText();
                }
            }
            return "Сумма не найдена";
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    // Получение текста на кнопке оплаты
    public String getPayButtonText() {
        try {
            List<WebElement> buttons = driver.findElements(
                    By.xpath("//button[contains(text(), 'Оплатить')]")
            );

            for (WebElement button : buttons) {
                if (button.isDisplayed()) {
                    return button.getText();
                }
            }
            return "Кнопка не найдена";
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    // Проверка, что кнопка оплаты неактивна
    public boolean isPayButtonDisabled() {
        try {
            List<WebElement> buttons = driver.findElements(
                    By.xpath("//button[contains(text(), 'Оплатить')]")
            );

            for (WebElement button : buttons) {
                if (button.isDisplayed()) {
                    String classes = button.getAttribute("class");
                    return classes.contains("disabled") || !button.isEnabled();
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // Получение сообщений об ошибках полей карты
    public String getCardNumberError() {
        return findErrorText("Некорректный номер карты");
    }

    public String getCardExpiryError() {
        return findErrorText("Исправьте срок действия");
    }

    public String getCardCvvError() {
        return findErrorText("Введите CVC-код");
    }

    public String getCardHolderError() {
        return findErrorText("Введите имя и фамилию как указано на карте");
    }

    private String findErrorText(String errorText) {
        try {
            List<WebElement> elements = driver.findElements(
                    By.xpath("//*[contains(text(), '" + errorText + "')]")
            );

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return element.getText();
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    // Закрытие popup
    public void closePopup() {
        try {
            // Ищем крестик или кнопку закрытия
            List<WebElement> closeElements = driver.findElements(
                    By.xpath("//button[@aria-label='Close'] | //*[contains(@class, 'close')] | //*[text()='×'] | //*[contains(@class, 'header__close-icon')]")
            );

            for (WebElement closeBtn : closeElements) {
                if (closeBtn.isDisplayed()) {
                    closeBtn.click();
                    Thread.sleep(500);
                    break;
                }
            }
        } catch (Exception e) {
            // Игнорируем ошибки закрытия
        }
    }
}