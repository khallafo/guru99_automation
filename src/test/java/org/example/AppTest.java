package org.example;
import io.qameta.allure.Step;
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

public class AppTest
{
    private WebDriver driver;

    @BeforeClass
    public void setUp() throws Exception{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @Test
    public void testSignInWithValidCredentials(){
        driver.get("https://www.demo.guru99.com/V4/");
        SignIn signIn = new SignIn(driver);
        signIn.clickOnSignIn("mngr520474", "ymetYbU");
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
    @Test
    public void testSignInWithInvalidCredentials(){
        driver.get("https://www.demo.guru99.com/V4/");
        SignIn signIn = new SignIn(driver);
        signIn.clickOnSignIn("mngr520474", "ymetYbU1");
        Alert confirmationAlert = driver.switchTo().alert();
        String alertText = confirmationAlert.getText();
        System.out.println("Alert text is " + alertText);
    }

    @Test(dependsOnMethods = "testSignInWithValidCredentials")
    public void CreateCustomer() {
        // Navigate to the Add Customer page
        driver.navigate().to("https://www.demo.guru99.com/V4/manager/addcustomerpage.php");

        // Create an instance of the NewCustomer class
        NewCustomer newCustomer = new NewCustomer(driver);

        // Call the CreateNewCustomer method with customer details
        newCustomer.CreateNewCustomer("khallafAhmed", "ymetYbU", "08-06-1999",
                "st shoubra el kheima", "cairo", "egypt", "123456", "01282733545", "mngr520478@gmail.com");

        // Locate the registered message element
        WebElement registered = driver.findElement(By.xpath("//*[@id=\"customer\"]/tbody/tr[1]/td/p"));

        // Assert that the registered message matches the expected text
        Assert.assertEquals("Customer Registered Successfully!!!", registered.getText());

        // Take a screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Copy the screenshot file to a location and handle exceptions
        try {
            FileUtils.copyFile(screenshot, new File("F://guru99_automation//newCustomer.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test(dependsOnMethods = "testSignInWithValidCredentials")
    public void EditCustomer1() {
        // Navigate to the Edit Customer page
        driver.navigate().to("https://www.demo.guru99.com/V4/manager/EditCustomer.php");

        // Create an instance of the EditCustomer class
        EditCustomer editCustomer = new EditCustomer(driver);

        // Call the editedCustomer1 method with customer ID
        editCustomer.editedCustomer1("66142");

        // Wait for a certain amount of time
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Call the editCustomerPage method with new customer details
        editCustomer.editCustomerPage("germany");

        // Switch to the alert dialog
        Alert confirmationAlert = driver.switchTo().alert();

        // Get the text from the alert
        String alertText = confirmationAlert.getText();

        // Assert that the alert text is as expected
        Assert.assertEquals("No Changes made to Customer records", alertText);

        // Take a screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Copy the screenshot file to a location and handle exceptions
        try {
            FileUtils.copyFile(screenshot, new File("F://guru99_automation//EditCustomer.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test(dependsOnMethods = "testSignInWithValidCredentials")
    public void DeleteCust() {
        // Navigate to the delete Customer page
        driver.navigate().to("https://www.demo.guru99.com/V4/manager/DeleteCustomerInput.php");
        // Create an instance of the EditCustomer class
        DeleteCustomer  deleteCustomer = new DeleteCustomer(driver);
        deleteCustomer.ValidateDelete("599");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Alert confirmationAlert = driver.switchTo().alert();
        confirmationAlert.accept();
        String alretMess=confirmationAlert.getText();
        System.out.println(alretMess);
        confirmationAlert.dismiss();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Copy the screenshot file to a location and handle exceptions
        try {
            FileUtils.copyFile(screenshot, new File("F://guru99_automation//DeletedCustomer.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Take a screenshot


        //Assert.assertEquals("Customer does not exist!!",alretMess);


    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
