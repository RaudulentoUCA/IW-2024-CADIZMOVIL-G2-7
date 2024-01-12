// Generated by Selenium IDE
import es.uca.iw.Application;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.RepositorioCliente;
import es.uca.iw.cliente.ServiciosCliente;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;
import java.util.*;

@SpringBootTest(classes = Application.class)
public class ClienteTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @MockBean
    private final ServiciosCliente servicios;

    @Autowired
    public ClienteTest(ServiciosCliente servicios) {
        this.servicios = servicios;
    }


    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @AfterEach
    public void tearDown() {
        if(driver != null)
            driver.quit();
    }
    @Test
    public void testCliente() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1066, 816));
        driver.findElement(By.cssSelector("vaadin-button:nth-child(2)")).click();
        driver.findElement(By.id("input-vaadin-text-field-25")).click();
        driver.findElement(By.id("input-vaadin-text-field-25")).sendKeys("prueba");
        driver.findElement(By.id("input-vaadin-text-field-25")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("input-vaadin-text-field-26")).click();
        driver.findElement(By.id("input-vaadin-text-field-26")).sendKeys("prueba2");
        driver.findElement(By.id("input-vaadin-text-field-26")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("input-vaadin-date-picker-27")).click();
        driver.findElement(By.id("input-vaadin-date-picker-27")).sendKeys("19.04.1989");
        driver.findElement(By.id("input-vaadin-date-picker-27")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("input-vaadin-text-field-28")).click();
        driver.findElement(By.id("input-vaadin-text-field-28")).sendKeys("123456789");
        driver.findElement(By.id("input-vaadin-text-field-28")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("input-vaadin-email-field-29")).click();
        driver.findElement(By.id("input-vaadin-email-field-29")).sendKeys("prueba@gmail.com");
        driver.findElement(By.id("input-vaadin-email-field-29")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("input-vaadin-text-field-30")).click();
        driver.findElement(By.id("input-vaadin-text-field-30")).sendKeys("11223344S");
        driver.findElement(By.id("input-vaadin-text-field-30")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("input-vaadin-password-field-31")).click();
        driver.findElement(By.id("input-vaadin-password-field-31")).sendKeys("1234");
        driver.findElement(By.id("input-vaadin-password-field-31")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("input-vaadin-password-field-32")).click();
        driver.findElement(By.id("input-vaadin-password-field-32")).sendKeys("1234");
        driver.findElement(By.id("input-vaadin-password-field-32")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector("vaadin-button:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".gap-m:nth-child(5)")).click();
        driver.findElement(By.cssSelector("vaadin-button:nth-child(2)")).click();
        driver.findElement(By.id("input-vaadin-text-field-69")).click();
        driver.findElement(By.id("input-vaadin-text-field-69")).sendKeys("prueba@gmail.com");
        driver.findElement(By.id("input-vaadin-text-field-69")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("input-vaadin-password-field-70")).sendKeys("1234");
        driver.findElement(By.id("input-vaadin-password-field-70")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector("vaadin-icon:nth-child(2)")).click();
        driver.findElement(By.cssSelector("vaadin-menu-bar-list-box > vaadin-menu-bar-item")).click();
    }
}