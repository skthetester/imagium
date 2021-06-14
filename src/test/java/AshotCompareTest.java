import org.junit.Assert;
import org.junit.Test;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AshotCompareTest {
    @Test
    public void visualTest() throws IOException {
        BufferedImage expectedImage = ImageIO.read(new File("./img/Testing Chief - Login.png"));
        BufferedImage actualImage = ImageIO.read(new File("./img/Testing Chief - Login(Testing).png"));
        ImageDiffer imageDiffer = new ImageDiffer();
        ImageDiff imageDiff = imageDiffer.makeDiff(expectedImage, actualImage);
//                .withDiffSizeTrigger(100);

        ImageIO.write(imageDiff.getMarkedImage(), "PNG", new File("./img/" + "Ashot Result - Marked Image" + ".png"));
//        ImageIO.write(imageDiff.getTransparentMarkedImage(), "PNG", new File("./img/" + "Ashot Result - Transparent Marked Image" + ".png"));
        System.out.println(imageDiff.getDiffSize());
        Assert.assertFalse("Images are Different", imageDiff.hasDiff());
    }
}
