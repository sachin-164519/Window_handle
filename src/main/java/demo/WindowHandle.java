package demo;

import java.io.File;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WindowHandle {
    ChromeDriver driver;
    public WindowHandle()
    {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public static void takeScreenshot(WebDriver driver, String screenShotType, String Description){
        try{
        File theDir = new File("/screenshots");
        if(!theDir.exists()){
            theDir.mkdirs();
        }
        String timeStamp = String.valueOf(java.time.LocalDateTime.now());
        String filename = String.format("screenshot_%s_%s_%s.png", timeStamp,screenShotType,Description);
        TakesScreenshot scrShot = (TakesScreenshot)driver;
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File desFile = new File("screenshots/" +filename);
        FileUtils.copyFile(srcFile, desFile);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    
    public  void testCase01(){
        System.out.println("Start Test case: testCase01");
        //Navigate to url https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_win_open
        driver.get("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_win_open");
        //switch to the frame
        driver.switchTo().frame("iframeResult");
        //Click on the  "Try it" button at the top of the page
        WebElement tryBtn = driver.findElement(By.xpath("//button[contains(text(),'Try it')]"));
        tryBtn.click();
        //switch to new tab
        Set<String> handles = driver.getWindowHandles();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[1]);
        //Grt the URL and title
        System.out.println("URL is :"+driver.getCurrentUrl());
        System.out.println("Ttile of the page :"+driver.getTitle());
        //take screenshot
        takeScreenshot(driver, "W3school", "Screenshot");
        //cloase the child window
        driver.close();
        //switch back to original window
        driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);

    }
}
