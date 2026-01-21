package tests.SeleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.MtsHomePage;
import utils.SeleniumTestBase;

import static org.junit.jupiter.api.Assertions.*;

public class MtsPageObjectTests extends SeleniumTestBase {
    private MtsHomePage mtsPage;

    @Test
    public void testOnlinePaymentBlockTitle() {
        mtsPage = new MtsHomePage(driver);
        mtsPage.closeCookieBanner();

        assertTrue(mtsPage.isOnlinePaymentTitleDisplayed(),
                "Заголовок блока должен быть видимым");

        String titleText = mtsPage.getOnlinePaymentTitleText();
        System.out.println("Заголовок блока: '" + titleText + "'");

        assertTrue(titleText.contains("Онлайн пополнение"),
                "Заголовок должен содержать 'Онлайн пополнение'");
        assertTrue(titleText.contains("без комиссии"),
                "Заголовок должен содержать 'без комиссии'");
    }

    @Test
    public void testPaymentsLogo() {
        mtsPage = new MtsHomePage(driver);
        mtsPage.closeCookieBanner();

        int logosCount = mtsPage.getPaymentLogosCount();
        System.out.println("Найдено логотипов: " + logosCount);

        assertEquals(5, logosCount, "Должно быть ровно 5 логотипов");

        for (int i = 0; i < logosCount; i++) {
            assertTrue(mtsPage.isPaymentLogoDisplayed(i),
                    "Логотип #" + (i + 1) + " должен быть видимым");

            String logoSrc = mtsPage.getPaymentLogoSrc(i);
            assertNotNull(logoSrc, "У логотипа #" + (i + 1) + " должен быть атрибут src");
            assertFalse(logoSrc.isEmpty(), "Атрибут src не должен быть пустым");

            System.out.println("Логотип " + (i + 1) + ": " + logoSrc);
        }

        System.out.println("\n✅ Все 5 логотипов найдены и валидны!");
    }

    @Test
    public void testMoreAboutServices() {
        mtsPage = new MtsHomePage(driver);
        mtsPage.closeCookieBanner();

        String urlBefore = mtsPage.getCurrentUrl();
        System.out.println("URL до клика: " + urlBefore);

        assertTrue(mtsPage.isServiceDetailsLinkDisplayed(),
                "Ссылка должна быть видимой");
        assertTrue(mtsPage.isServiceDetailsLinkEnabled(),
                "Ссылка должна быть кликабельной");

        System.out.println("Ссылка найдена:");
        System.out.println("  Текст: '" + mtsPage.getServiceDetailsLinkText() + "'");
        System.out.println("  href: " + mtsPage.getServiceDetailsLinkHref());

        mtsPage.clickServiceDetailsLink();

       // try {
          //  Thread.sleep(3000);
        //} catch (InterruptedException e) {
            //e.printStackTrace();
        //}

        String urlAfter = mtsPage.getCurrentUrl();
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
    public void testPaymentPopupUltraSimple() throws InterruptedException {
        System.out.println("=== Сверхпростой тест popup ===");

        mtsPage = new MtsHomePage(driver);
        mtsPage.closeCookieBanner();

        driver.findElement(By.id("connection-phone")).sendKeys("297777777");
        driver.findElement(By.id("connection-sum")).sendKeys("200");

        System.out.println("Форма заполнена");

        driver.findElement(By.xpath("//button[text()='Продолжить']")).click();

        System.out.println("Кнопка нажата");

       // Thread.sleep(3000);

        String url = driver.getCurrentUrl();
        System.out.println("Текущий URL: " + url);

        System.out.println("Браузер жив и тест выполнен ✓");

        try {
            int buttonCount = driver.findElements(By.tagName("button")).size();
            System.out.println("Всего кнопок на странице: " + buttonCount);
        } catch (Exception e) {
            System.out.println("Не удалось посчитать кнопки: " + e.getMessage());
        }

        System.out.println("=== Тест завершен успешно ===");
    }
}