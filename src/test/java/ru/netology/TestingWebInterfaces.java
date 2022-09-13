package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestingWebInterfaces {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldFillFormValidValue() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кирилл Гуреев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79125542325");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldFillFormValidValueWithDashInName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кирилл-Кирилл Гуреев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79125542325");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void nameFieldFilledIncorrectly() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Kirill Gureev");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79125542325");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void phoneFieldFilledIncorrectly() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кирилл Гуреев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+3912554232512");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void flagNoSet() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кирилл Гуреев");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79125542325");
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("checkbox__text")).getText();
        Assertions.assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

    @Test
    void formEmpty() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void formEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79046472030");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void formEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Аленин Андрей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }
}