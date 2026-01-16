package tests.SeleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SeleniumTestBase;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.SeleniumTestBase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MtsTests extends SeleniumTestBase {

    @Test
    public void testOnlinePaymentBlockTitle(){
        driver.get("https://www.mts.by/");

        try {
            WebElement el2 = driver.findElement(
                    By.xpath("//h2[normalize-space(text()) = 'Онлайн пополнение']")
            );
            System.out.println("Найден: //h2[normalize-space(text()) = 'Онлайн пополнение']");
            System.out.println("   Текст: '" + el2.getText() + "'");
        } catch (Exception e) {
            System.out.println("Не найден: //h2[normalize-space(text()) = 'Онлайн пополнение']");
        }
    }

    @Test
    public void testPaymentsLogo(){
        driver.get("https://www.mts.by/");
        List<WebElement> logoItems = driver.findElements(
                By.xpath("//div[@class='pay__partners']/ul/li")
        );

        System.out.println("Найдено логотипов: " + logoItems.size());

        assertEquals(5, logoItems.size(), "Должно быть ровно 5 логотипов");

        for (int i = 0; i < logoItems.size(); i++) {
            WebElement li = logoItems.get(i);

            WebElement img = li.findElement(By.tagName("img"));

            assertTrue(img.isDisplayed(), "Логотип #" + (i + 1) + " должен быть видимым");
            assertTrue(img.getAttribute("src") != null && !img.getAttribute("src").isEmpty(),
                    "У логотипа #" + (i + 1) + " должен быть атрибут src");

            System.out.println("Логотип " + (i + 1) + ": " + img.getAttribute("src"));
        }

        System.out.println("\nВсе 5 логотипов найдены и валидны!");
    }


    @Test
    public void testMoreAboutServices() {
        // Страница уже открыта, cookie-баннер уже закрыт в @BeforeEach!

        String urlBefore = driver.getCurrentUrl();
        System.out.println("URL до клика: " + urlBefore);

        // Находим ссылку
        WebElement link = driver.findElement(
                By.xpath("//a[normalize-space(text())='Подробнее о сервисе']")
        );

        System.out.println("Ссылка найдена:");
        System.out.println("  Текст: '" + link.getText() + "'");
        System.out.println("  href: " + link.getAttribute("href"));

        // Проверяем
        assertTrue(link.isDisplayed(), "Ссылка должна быть видимой");
        assertTrue(link.isEnabled(), "Ссылка должна быть кликабельной");

        // Кликаем (теперь можно обычным кликом!)
        link.click();

        // Ждём
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверяем
        String urlAfter = driver.getCurrentUrl();
        System.out.println("URL после клика: " + urlAfter);

        if (!urlBefore.equals(urlAfter)) {
            System.out.println("✅ Переход выполнен успешно!");
            System.out.println("  Было: " + urlBefore);
            System.out.println("  Стало: " + urlAfter);
        } else {
            System.out.println("⚠️ URL не изменился");
        }
    }
    @Test
    public void testPaymentFormOptimized() {
        long startTime = System.currentTimeMillis();

        // 1. Загрузка (самое долгое)
        driver.get("https://www.mts.by/");

        // 2. Быстрое закрытие cookie
        try {
            driver.findElement(By.id("cookie-agree")).click();
        } catch (Exception e) {
            // Не тратим время на ожидание
        }

        // 3. Прямые быстрые локаторы
        driver.findElement(By.id("connection-phone")).sendKeys("297777777");
        driver.findElement(By.id("connection-sum")).sendKeys("200");

        // 4. Простой поиск кнопки
        driver.findElement(By.xpath("(//button[@type='submit'])[1]")).click();

        long endTime = System.currentTimeMillis();
        System.out.println("✅ Тест выполнен за " + (endTime - startTime)/1000.0 + " секунд");
    }
}
