import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class TomcatTest {
    static EmbeddedTomcat tomcat = new EmbeddedTomcat();

    static HtmlUnitDriver browser = new HtmlUnitDriver();

    @BeforeClass
    public static void setUp() throws Exception {
        tomcat.start();
        tomcat.deploy();

    }

    @Test
    public void test() {
        browser.get(tomcat.getApplicationUrl("aa"));
        assertEquals("App", browser.findElement(By.id("name")).getText());
    }

    @AfterClass
    public static void tearDown() {
        browser.close();
        tomcat.stop();
    }

}
