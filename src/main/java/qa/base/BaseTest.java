package qa.base;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;
import org.testng.annotations.*;
import qa.commonfuctions.Constants;
import qa.commonfuctions.NewTabsSetUp;
import qa.commonfuctions.RunTimeDataStore;
import qa.pages.AlertPage;
import qa.pages.LoginPage;
import qa.pages.WatchlistPage;

import java.io.File;
import java.time.Duration;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import qa.commonfuctions.NewTabsSetUp;
import qa.pages.AlertPage;
import qa.pages.LoginPage;
import qa.pages.WatchlistPage;

public class BaseTest {

    // declaring all variables
    public static ChromeOptions options = new ChromeOptions();
    public static WebDriver driver;
    public static Properties prop;
    public static Properties fAndOStocksprop;
    public static InputStream fileInputStream;
    public static InputStream fAndOfileInputStream;

    @BeforeTest
    public void setup() throws IOException {
    // this method will run Before each @Test method we will have

        try {
            //read properties file
            String propFilePath = System.getProperty("user.dir") + "/src/main/resources/config/config.properties";
            System.out.println(propFilePath);
            fileInputStream = new FileInputStream(propFilePath);

            // load properties file in Properties
            prop = new Properties();
            prop.load(fileInputStream);

            //read F&O config  file
            String fAndOStocksFilePath = System.getProperty("user.dir") + "/src/main/resources/data/FAndO_Stocks_Detail.properties";
            System.out.println(fAndOStocksFilePath);
            fAndOfileInputStream = new FileInputStream(fAndOStocksFilePath);

            // load properties file in Properties
            fAndOStocksprop = new Properties();
            fAndOStocksprop.load(fAndOfileInputStream);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (fAndOStocksprop != null) {
                try {
                    fAndOfileInputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

//
        Reporter.log("======Launch Browser======", true);

        options.addArguments("--remote-allow-origins");
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        driver= new ChromeDriver(options);

        // navigate to application and set the pace of script
//        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get(prop.getProperty("url"));
        this.browser_Refresh();

        //Set watchlist data for statergy ST1_Cndt2
        RunTimeDataStore.ST2_Cndt2_Watchlists_Details.update_All_Watchlist_Data(Constants.TEXTFILE_PATH_FOR_ST2_CNDT2_WATCHLISTS_NAME_AND_URL);

        //Set F_AND_O_STOCKS_DETAILS data as complete name and symbol
//        RunTimeDataStore.ST2_Cndt2_Watchlists_Details.update_All_Watchlist_Data(Constants.TEXTFILE_PATH_FOR_F_AND_O_STOCKS_DETAILS);

    }

    public void browser_Refresh() {
            driver.navigate().refresh();
            System.out.println("Browser Refreshed.");
        }

}