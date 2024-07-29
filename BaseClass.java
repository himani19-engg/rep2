package solRetailIHM.Utilities;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;

public class BaseClass {
    static WebDriver driver = null;
    public static ExtentTest logger;
    public void projectSetup() {

        //jenkins Block of Code: Don't delete the commented/Uncommented part
		String projectPath=System.getProperty("user.home");
        System.out.println(projectPath);
		System.setProperty("webdriver.chrome.driver",projectPath+"/drivers/chromedriver.exe");

        //Local Block of code
       /* String projectPath = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", projectPath + "/Drivers/chromedriver.exe");*/
    }
}
