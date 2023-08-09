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
public class NewCustomerTC {
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
    public void CreateCustomer() {
        // Navigate to the Add Customer page
        driver.navigate().to("https://www.demo.guru99.com/V4/manager/addcustomerpage.php");

        // Create an instance of the NewCustomer class
        NewCustomer newCustomer = new NewCustomer(driver);

        // Call the CreateNewCustomer method with customer details
        newCustomer.CreateNewCustomer("lofaa", "ymetYbN", "08-06-1999",
                "st shoubra el kheima", "cairo", "egypt", "123456", "01282733545", "mahmoudkhallaf277@gmail.com");

        // Locate the registered message element
        WebElement registered = driver.findElement(By.xpath("//*[@id=\"customer\"]/tbody/tr[1]/td/p"));

        // Assert that the registered message matches the expected text
        Assert.assertEquals("Customer Registered Successfully!!!", registered.getText());

        // Locate the Customer ID element
        WebElement Customerid = driver.findElement(By.xpath("//*[@id=\"customer\"]/tbody/tr[4]/td[2]"));

        // Get the text of the Customer ID element
        CustomerID = Customerid.getText();

        // Print the Customer ID
        System.out.println(CustomerID);

        // Take a screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Copy the screenshot file to a specified location and handle exceptions
        try {
            FileUtils.copyFile(screenshot, new File("F://guru99_automation//newCustomer.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    } // End of CreateCustomer method
    @Test(dependsOnMethods = "CreateCustomer" )
    public void AddAccount(){
        NewAccount newAccount = new NewAccount(driver);
        driver.navigate().to("https://www.demo.guru99.com/V4/manager/addAccount.php");
        newAccount.CreateNew("61358","1000000");
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Copy the screenshot file to a location and handle exceptions
        try {
            FileUtils.copyFile(screenshot, new File("F://guru99_automation//NewAccount.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
