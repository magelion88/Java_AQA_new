package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class OnlinePaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // Выпадающий список
    @FindBy(xpath = "//button[@class='select__header']")
    private WebElement paymentTypeDropdown;

    // Контейнер выпадающего списка
    @FindBy(css = "ul.select__list")
    private WebElement selectListContainer;

    // Все опции в выпадающем списке
    @FindBy(css = "p.select__option")
    private List<WebElement> paymentOptions;

    // Поля для услуг связи
    @FindBy(id = "connection-phone")
    private WebElement connectionPhoneField;

    @FindBy(id = "connection-sum")
    private WebElement connectionSumField;

    @FindBy(id = "connection-email")
    private WebElement connectionEmailField;

    // Поля для домашнего интернета
    @FindBy(id = "internet-phone")
    private WebElement internetPhoneField;

    @FindBy(id = "internet-sum")
    private WebElement internetSumField;

    @FindBy(id = "internet-email")
    private WebElement internetEmailField;

    // Поля для рассрочки
    @FindBy(id = "score-instalment")
    private WebElement scoreInstalmentField;

    @FindBy(id = "instalment-sum")
    private WebElement instalmentSumField;

    @FindBy(id = "instalment-email")
    private WebElement instalmentEmailField;

    // Поля для задолженности
    @FindBy(id = "score-arrears")
    private WebElement scoreArrearsField;

    @FindBy(id = "arrears-sum")
    private WebElement arrearsSumField;

    @FindBy(id = "arrears-email")
    private WebElement arrearsEmailField;

    public OnlinePaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    // Метод для открытия выпадающего списка
    public void openPaymentDropdown() {
        try {
            // Ждем, пока кнопка станет кликабельной
            wait.until(ExpectedConditions.elementToBeClickable(paymentTypeDropdown));

            // Кликаем с помощью Actions для надежности
            actions.moveToElement(paymentTypeDropdown)
                    .pause(Duration.ofMillis(200))
                    .click()
                    .perform();

            // Ждем, пока список станет видимым
            wait.until(ExpectedConditions.visibilityOf(selectListContainer));

            // Даем время для полного отображения опций
            Thread.sleep(500);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Метод для выбора типа оплаты
    public void selectPaymentType(String optionName) {
        // Открываем выпадающий список
        openPaymentDropdown();

        try {
            // Ищем нужную опцию среди видимых элементов
            WebElement targetOption = null;

            // Прокручиваем немного, чтобы все элементы были видны
            actions.moveToElement(paymentTypeDropdown)
                    .scrollByAmount(0, 100)
                    .perform();

            Thread.sleep(300);

            // Ищем опцию по тексту
            for (WebElement option : paymentOptions) {
                if (option.isDisplayed() && option.getText().trim().equals(optionName)) {
                    targetOption = option;
                    break;
                }
            }

            if (targetOption == null) {
                throw new NoSuchElementException("Не найдена опция: " + optionName);
            }

            // Прокручиваем к элементу
            actions.moveToElement(targetOption)
                    .perform();

            // Ждем, пока элемент станет кликабельным
            wait.until(ExpectedConditions.elementToBeClickable(targetOption));

            // Кликаем с паузой для надежности
            actions.moveToElement(targetOption)
                    .pause(Duration.ofMillis(200))
                    .click()
                    .perform();

            // Ждем, пока список закроется
            wait.until(ExpectedConditions.invisibilityOf(selectListContainer));

            // Ждем загрузки формы
            waitForFormToLoad();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Ожидание загрузки формы
    private void waitForFormToLoad() {
        try {
            // Ждем появления хотя бы одного поля ввода
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[id*='-']")
            ));

            // Даем время для применения всех стилей
            Thread.sleep(300);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Проверка видимости выпадающего списка
    public boolean isPaymentTypeDropdownDisplayed() {
        try {
            return paymentTypeDropdown.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Геттеры для placeholder'ов (остаются без изменений)
    public String getConnectionPhonePlaceholder() {
        return wait.until(driver -> connectionPhoneField.getAttribute("placeholder"));
    }

    public String getConnectionSumPlaceholder() {
        return wait.until(driver -> connectionSumField.getAttribute("placeholder"));
    }

    public String getConnectionEmailPlaceholder() {
        return wait.until(driver -> connectionEmailField.getAttribute("placeholder"));
    }

    public String getInternetPhonePlaceholder() {
        return wait.until(driver -> internetPhoneField.getAttribute("placeholder"));
    }

    public String getInternetSumPlaceholder() {
        return wait.until(driver -> internetSumField.getAttribute("placeholder"));
    }

    public String getInternetEmailPlaceholder() {
        return wait.until(driver -> internetEmailField.getAttribute("placeholder"));
    }

    public String getScoreInstalmentPlaceholder() {
        return wait.until(driver -> scoreInstalmentField.getAttribute("placeholder"));
    }

    public String getInstalmentSumPlaceholder() {
        return wait.until(driver -> instalmentSumField.getAttribute("placeholder"));
    }

    public String getInstalmentEmailPlaceholder() {
        return wait.until(driver -> instalmentEmailField.getAttribute("placeholder"));
    }

    public String getScoreArrearsPlaceholder() {
        return wait.until(driver -> scoreArrearsField.getAttribute("placeholder"));
    }

    public String getArrearsSumPlaceholder() {
        return wait.until(driver -> arrearsSumField.getAttribute("placeholder"));
    }

    public String getArrearsEmailPlaceholder() {
        return wait.until(driver -> arrearsEmailField.getAttribute("placeholder"));
    }
}