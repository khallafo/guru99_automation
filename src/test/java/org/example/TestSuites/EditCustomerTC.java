package org.example.TestSuites;
import org.example.Pages.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.openqa.selenium.Alert;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
public class EditCustomerTC {
    private WebDriver driver;
    public String CustomerID;
    @BeforeClass
    public void setUp() throws Exception{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        // Maximize the browser window
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @Test
    public void testSignInWithValidCredentials(){
        driver.get("https://www.demo.guru99.com/V4/");
        SignIn signIn = new SignIn(driver);
        signIn.clickOnSignIn("mngr520474", "ymetYbU1@");
        WebElement manageerID = driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[3]/td"));
        Assert.assertEquals("Manger Id : mngr520474",manageerID.getText());
        //Take the screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //Copy the file to a location and use try catch block to handle exception
        try {
            FileUtils.copyFile(screenshot, new File("F://guru99_automation//manageerId.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @AfterClass
    public void tearDown(){
        driver.quit();
    }
    @Test(dependsOnMethods = "testSignInWithValidCredentials")
    public void EditCustomer1() {
        // Navigate to the Edit Customer page
        driver.navigate().to("https://www.demo.guru99.com/V4/manager/EditCustomer.php");

        // Create an instance of the EditCustomer class
        EditCustomer editCustomer = new EditCustomer(driver);

        // Call the editedCustomer1 method with customer ID
        editCustomer.editedCustomer1("10063");

        // Wait for a certain amount of time
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Call the editCustomerPage method with new customer details
        editCustomer.editCustomerPage("germany");

        // Switch to the alert dialog
        Alert confirmationAlert = driver.switchTo().alert();

        // Get the text from the alert
        String alertText = confirmationAlert.getText();
        confirmationAlert.dismiss();
        // Assert that the alert text is as expected
        //Assert.assertEquals("No Changes made to Customer records", alertText);

        // Take a screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Copy the screenshot file to a location and handle exceptions
        try {
            FileUtils.copyFile(screenshot, new File("F://guru99_automation//EditCustomer.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
