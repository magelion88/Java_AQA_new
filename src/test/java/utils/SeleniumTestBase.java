package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By; // ← ДОБАВИТЬ ЭТОТ ИМПОРТ
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class SeleniumTestBase {

    protected WebDriver driver;

    // Метод для закрытия cookie-баннера
    /* protected void closeCookieBanner() {
        try {
            driver.findElement(By.id("cookie-agree")).click(); // ← теперь By импортирован
        } catch (Exception e) {
            // Игнорируем, если нет кнопки или уже закрыт
        }
    } // ← ЗАКРЫВАЮЩАЯ СКОБКА была пропущена!
*/
    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Открываем сайт
        driver.get("https://www.mts.by/");

        // ОБЯЗАТЕЛЬНО закрываем cookie-баннер!
        closeCookieBanner();
    }

    protected void closeCookieBanner() {
        try {
            // Находим и кликаем через JavaScript
            WebElement cookieBtn = driver.findElement(By.id("cookie-agree"));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", cookieBtn);

            // Ждём исчезновения
            Thread.sleep(1000);

        } catch (Exception e) {
            // Если нет баннера - ок
        }
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}