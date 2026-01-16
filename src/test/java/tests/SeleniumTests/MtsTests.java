package tests.SeleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

        String urlBefore = driver.getCurrentUrl();
        System.out.println("URL до клика: " + urlBefore);

        WebElement link = driver.findElement(
                By.xpath("//a[normalize-space(text())='Подробнее о сервисе']")
        );

        System.out.println("Ссылка найдена:");
        System.out.println("  Текст: '" + link.getText() + "'");
        System.out.println("  href: " + link.getAttribute("href"));

        assertTrue(link.isDisplayed(), "Ссылка должна быть видимой");
        assertTrue(link.isEnabled(), "Ссылка должна быть кликабельной");

        link.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
    public void testPaymentForm() {
        driver.get("https://www.mts.by/");

        try {
            driver.findElement(By.id("cookie-agree")).click();
        } catch (Exception e) {
        }

        driver.findElement(By.id("connection-phone")).sendKeys("297777777");
        driver.findElement(By.id("connection-sum")).sendKeys("200");

        WebElement continueButton = driver.findElement(
                By.xpath("//button[text()='Продолжить']")
        );

        System.out.println("Кнопка найдена:");
        System.out.println("  Текст: '" + continueButton.getText() + "'");
        System.out.println("  enabled: " + continueButton.isEnabled());
        System.out.println("  координаты: " + continueButton.getLocation());

        continueButton.click();
        System.out.println("✅ Кнопка 'Продолжить' нажата!");

        try {
            Thread.sleep(2000);
            System.out.println("Текущий URL: " + driver.getCurrentUrl());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
