import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ImagiumTest {
    WebDriver driver;
    static String url = "http://ignite:81/startbootstrap/"; //test page's base url
    static String testPage = "login_test.html"; //test page
    static String testMode = "Strict"; //Default or Strict

    @Before
    public void initialize() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-extensions");
        driver = new ChromeDriver(options);
    }

    @Test
    public void visualTest() throws IOException {
        driver.get(url + testPage);
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(1.25f), 1000))
                .takeScreenshot(driver, driver.findElement(By.xpath("//div[@class='container']")));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(screenshot.getImage(), "PNG", out);
        byte[] bytes = out.toByteArray();
        String imagebase64 = Base64.encodeBase64String(bytes);

        String projectID = Imagium.getUID("login", "c272bef9-1432-44e6-8fd8-8227e3e451df", testMode);
        String response = Imagium.postRequest("Visual Test", projectID, imagebase64);
        assertThat(response).doesNotContain("Failed");
    }

    @After
    public void endTest() {
        driver.close();
        driver.quit();
    }
}
