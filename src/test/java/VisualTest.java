import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class VisualTest {
    @Test
    public void takeScreenshot() throws IOException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-extensions");
        WebDriver driver = new ChromeDriver(options);
        driver.get("http://ignite:81/startbootstrap/login.html");
        Screenshot screenshot = new AShot()
//                .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                .shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(1.25f), 1000))
                .takeScreenshot(driver, driver.findElement(By.xpath("//div[@class='container']")));
        ImageIO.write(screenshot.getImage(), "PNG", new File("./img/" + driver.getTitle() + ".png"));
        assertThat(driver.getTitle()).contains("Login");
        driver.close();
        driver.quit();
    }
}
