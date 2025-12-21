package qa.pages;

import org.openqa.selenium.By;
import qa.base.BaseTest;
import org.openqa.selenium.support.PageFactory;
import qa.commonfuctions.*;
import qa.helperClass.SeleniumHelper;
import qa.utils.ReportUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class WatchlistPage extends BaseTest {

    SeleniumHelper helper;

    NewTabsSetUp newTabsSetUp = new NewTabsSetUp();

    BrowserAlertHandler browserAlertHandler = new BrowserAlertHandler();

    public static final By WebElement_Watchlist_Name_Link = By.xpath("//*[@aria-current='page']");
    public static final By WebElement_Selected_Watchlist_SubTab = By.xpath("//*[@href='/watchlist_dashboard' and contains(@class,'arctic-pearl')]");
    public static final By WebElement_Choose_File_Button = By.xpath("(//*[ contains(@class,'button') ] [contains (text(), 'Select a file') ] )[1]");
//    public static final By WebElement_Upload_Button = By.xpath("(//*[ contains(@class,'button') ] //*[contains (text(), 'Upload') ] )[1]");
    public static final By WebElement_Upload_Button = By.xpath("(//*[ contains(@class,'cursor-pointer') ] //*[contains (text(), 'Upload') ] )[1]");
    public static final By WebElement_Empty_Watchlist_Button = By.xpath("//*[contains(@class,'cursor-pointer hidden md:flex')]//*[text()= 'Empty watchlist']");
    public static final By WebElement_Success_Message = By.xpath("//*[contains(@class,'success alert')]");
    //public static final By WebElement_NoDataAvailableInTable_Message = By.xpath("//*[contains(@class,'empty') and text()= 'No data available in table']");
    public static final By WebElement_NoStocksOnWatchlist_Message = By.xpath("//*[text()='No stocks on watchlist']");
    public static final By WebElement_Stock_Search_Textbox = By.xpath("//*[@id ='watchlist-stocks']");
    public static final By WebElement_NoMatchingRecordsFoundInTable_Message = By.xpath("//*[contains(@class,'empty') and text()= 'No matching records found']");
   // public static final String Xpath_Stock_Remove_Link= "//*[contains(@onclick,'%s') and text()= 'Remove']";
    public static final String Xpath_Stock_Remove_Link= "//*[text()='%s']//..//..//*[text()='Remove']";
    public static final By WebElement_PopUp_Ok_Button = By.xpath("//*[@role='dialog']//*[contains(text(),'Ok')]");
    public static final By WebElement_Search_Textbox = By.xpath("//*[@id='search']");
    public static final By WebElement_StockAddedToWatchlist_Text = By.xpath("//*[contains(text(),'Stock added to watchlist')]");
    public static final By WebElement_WatchlistIS_Uploaded_Text = By.xpath("//*[contains(text(),'Watchlist is uploaded')]");

    public static final By WebElement_Remove_Stock_Popup_Confirmation_Button = By.xpath("//*[contains(@class,'button')]//*[contains(text(),'Confirm')]");
    public static final By WebElement_Select_A_File_Button = By.xpath("//*[ contains(@class,'button') and contains (text(), ' Select a file ') ] ");
    public static final By WebElement_OS_File_Upload = By.xpath("(//*[ @type='file' and @required = 'required' ])[1] ");

    public static final By WebElement_FileInput  = By.cssSelector("input[type='file']");
    public static final String xpath_For_StockName_of_Add_Stocklist = "//*[contains (@class,'watchlist')]//*[text()=\"%s\"]";
//    public static final String xpath_For_StockName_of_Add_Stocklist = "//*[contains (@class,'watchlist')]//*[contains(text(),\"%s\")]";
    public static final String text_No_Stock_Present = "No Stocks Present..";


    public WatchlistPage() {
        PageFactory.initElements(driver, this);
        helper = new SeleniumHelper();
    }

    public boolean isElement_Success_Message_Visible() {
        return helper.isElementVisible(WebElement_Success_Message);
    }

    public boolean isElement_NoStocksOnWatchlist_Message_Visible() {
        //  return helper.isElementVisible(WebElement_NoDataAvailableInTable_Message);
        //to avoid implicit wait
        return helper.safeFindElement(WebElement_NoStocksOnWatchlist_Message,0);
    }
    public boolean isElement_NoMatchingRecordsFoundInTable_Message_Visible() {
//        return helper.isElementVisible(WebElement_NoMatchingRecordsFoundInTable_Message);
        //to avoid implicit wait
        return helper.safeFindElement(WebElement_NoMatchingRecordsFoundInTable_Message, 0);
    }
    public static By get_WebElement_Of_Stock_Remove_Link(String stock_Name) {
        return By.xpath(String.format(Xpath_Stock_Remove_Link, stock_Name));
    }

    public static By get_WebElement_Of_StockName_Of_Add_StockList(String stock_Name) {
        return By.xpath(String.format(xpath_For_StockName_of_Add_Stocklist, stock_Name));
    }

    // Adding special case for OIL Stock
    public boolean is_WebElement_Of_Stock_Remove_Link_Visible(By webElement) {
        return helper.safeFindElement(webElement, 0);
    }
    public void click_On_Stock_Remove_Link(String stock_Name) {
        helper.safeClick(get_WebElement_Of_Stock_Remove_Link(stock_Name));
    }
    public void click_Choose_File_Button() {
//        helper.safeClick(WebElement_Choose_File_Button);
        helper.forceClickByJavaScript(WebElement_Choose_File_Button);
    }
    public void click_On_Popup_Ok_Button() throws InterruptedException {
        helper.safeFindElement(WebElement_PopUp_Ok_Button,2);
        helper.safeClick(WebElement_PopUp_Ok_Button);
        Thread.sleep(2000);
    }
    public void click_On_Remove_Stock_Popup_Confirmation_Button() throws InterruptedException {
        //helper.safeFindElement(WebElement_Remove_Stock_Popup_Confirmation_Button,2);
        helper.safeClick(WebElement_Remove_Stock_Popup_Confirmation_Button);
        Thread.sleep(500);
    }
    public void click_Empty_Watchlist_Button() {
        helper.safeClick(WebElement_Empty_Watchlist_Button);
    }

    public void click_Upload_Button() {
        helper.safeClick(WebElement_Upload_Button);
    }

    public void send_Text_To_Stock_Search_Textbox(String stock_Name ) {
        helper.sendKeysSafe(WebElement_Stock_Search_Textbox,stock_Name);
    }

    public String get_Watchlist_Url(String Statergy_Time, String latest_Alert_TimeStamp) {

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- get_Watchlist_Url function ", "");

        String Alert_TimeStamp = latest_Alert_TimeStamp.split(", ")[1];
        String Watchlist_Name = "";
        String Watchlist_Url = "";
        String Watchlist_Details = "";
        String updatedTimeStr = "";

        try {

            // Append zero before hour ,if hour is single digit
            String alert_Hour = Alert_TimeStamp.split(":")[0];
            if (alert_Hour.length()== 1) {
                Alert_TimeStamp = Alert_TimeStamp.replace(alert_Hour + ":", "0" + alert_Hour + ":" );
            }

            // To subtract 5 mins from time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
            LocalTime time = LocalTime.parse(Alert_TimeStamp, formatter);

            // Subtract 5 minutes
            LocalTime updatedTime = time.minusMinutes(5);

            // Convert back to string
            updatedTimeStr = updatedTime.format(formatter);
            updatedTimeStr = updatedTimeStr.trim().replace(":", "_").replace(" ", "_");

            //code to remove leading 0 from sting so watchlist name 'ST1_Cndt_1_Time_3_05_PM' can be created properly
            if (updatedTimeStr.startsWith("0")) {
                updatedTimeStr = updatedTimeStr.substring(1);
            }

            // To create watchlist Name string searching string for hash map
            Watchlist_Name = Statergy_Time +updatedTimeStr ;

            // Get actual watchlist name
            Watchlist_Url = RunTimeDataStore.ST1_Watchlists_Details.valueBasedOnParticularKeyText(Watchlist_Name);
            Watchlist_Url = prop.getProperty("Watchlist_Defaut_Url") + Watchlist_Url;

            Watchlist_Details = Watchlist_Name + "," + Watchlist_Url;

            //Updating NAME and Url for future use
//            Constants.ST2_CNDT2_CURRENT_RUN_WATCHLIST_NAME = Watchlist_Name;
//            Constants.ST2_CNDT2_CURRENT_RUN_WATCHLIST_URL = Watchlist_Url;

        }catch (Exception e) {

            System.out.println("Watchlist_Details: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "get_Watchlist_Url, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- get_Watchlist_Url function ", "");

        return Watchlist_Details;
    }

//    public String get_Watchlist_Url_For_ST2_CNDT2_Statergy(String latest_Alert_TimeStamp) {
//
//        ReportUtil.report(true, "INFO", "-- Function -- Starting -- get_WatchlistName_For_ST2_CNDT2_Statergy function ", "");
//
//        String Alert_TimeStamp = latest_Alert_TimeStamp.split(", ")[1];
//        String Watchlist_Name = "";
//        String Watchlist_Url = "";
//        String updatedTimeStr = "";
//
//        try {
//
//            // Append zero before hour ,if hour is single digit
//            String alert_Hour = Alert_TimeStamp.split(":")[0];
//            if (alert_Hour.length()== 1) {
//                Alert_TimeStamp = Alert_TimeStamp.replace(alert_Hour + ":", "0" + alert_Hour + ":" );
//            }
//
//            // To subtract 5 mins from time
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
//            LocalTime time = LocalTime.parse(Alert_TimeStamp, formatter);
//
//            // Subtract 5 minutes
//            LocalTime updatedTime = time.minusMinutes(5);
//
//            // Convert back to string
//            updatedTimeStr = updatedTime.format(formatter);
//            updatedTimeStr = updatedTimeStr.trim().replace(":", "_").replace(" ", "_");
//
//            //code to remove leading 0 from sting so watchlist name 'ST1_Cndt_1_Time_3_05_PM' can be created properly
//            if (updatedTimeStr.startsWith("0")) {
//                updatedTimeStr = updatedTimeStr.substring(1);
//            }
//
//            // To create watchlist Name string searching string for hash map
//            Watchlist_Name = "ST2_Cndt_2_Time_" +updatedTimeStr ;
//
//            // Get actual watchlist name
//            Watchlist_Url = RunTimeDataStore.ST1_Watchlists_Details.valueBasedOnParticularKeyText(Watchlist_Name);
//            Watchlist_Url = prop.getProperty("ST2_Cndt_2_Watchlist_Url") + Watchlist_Url;
//
//            //Updating NAME and Url for future use
////            Constants.ST2_CNDT2_CURRENT_RUN_WATCHLIST_NAME = Watchlist_Name;
////            Constants.ST2_CNDT2_CURRENT_RUN_WATCHLIST_URL = Watchlist_Url;
//
//        }catch (Exception e) {
//
//            System.out.println("get_WatchlistName_For_ST2_CNDT2_Statergy: " + e.getMessage());
//            ReportUtil.report( false, "FAIL", "get_WatchlistName_For_ST2_CNDT2_Statergy, ",  e.getMessage());
//        }
//
//        ReportUtil.report(true, "INFO", "-- Function -- Ending -- get_WatchlistName_For_ST2_CNDT2_Statergy function ", "");
//
//        return Watchlist_Url;
//    }

    public boolean navigate_to_Particular_Watchlist(String Tab_Name, String Watchlist_Url, String Watchlist_Name){

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- navigate_to_Particular_Watchlist function ", "");

        boolean flag= false;

        try {
            newTabsSetUp.navigateToTab(Tab_Name);
            newTabsSetUp.setUrl(Watchlist_Url);
            newTabsSetUp.verifySelectedTab(Constants.WEBPAGE_WATCHLIST, WatchlistPage.WebElement_Watchlist_Name_Link,Watchlist_Name);

        }catch (Exception e) {

            System.out.println("navigate_to_Particular_Watchlist: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "navigate_to_Particular_Watchlist, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- navigate_to_Particular_Watchlist function ", "");

        return flag;
    }

    public boolean upload_Stock_List_TextFile_Using_Robot(String File_Location){

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- upload_Stock_List_TextFile_Using_Robot function ","");

        boolean flag= false;

        try {

            helper.safeClick(WebElement_Choose_File_Button);
            FileUploadWithRobot.uploadFile(File_Location);
//            FileUploadWithRobot.uploadFile("C:\\Users\\prath\\IdeaProjects\\ChartInk_ST1_And_ST2_Combined_Project_New\\src\\main\\resources\\data\\runTime_Stocks_for_watchlist.txt");
            // Wait to ensure file upload completes

            Thread.sleep(1000);

            this.click_Upload_Button();
            Thread.sleep(1000);
            //this.isElement_Success_Message_Visible();

            this.click_On_Popup_Ok_Button();

            ReportUtil.report( true, "PASS", "Text file ",  "'"+File_Location + "' uploaded successfully.");

        }catch (Exception e) {

            System.out.println("upload_Stock_List_TextFile: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "upload_Stock_List_TextFile_Using_Robot, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- upload_Stock_List_TextFile_Using_Robot function ", "");

        return flag;
    }
    public boolean upload_Stock_List_TextFile_Using_Auto_It(String File_Location , String Tab_Name, String Watchlist_Name, String Watchlist_Url, String Alert_Stock_Names){

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- upload_Stock_List_TextFile_Using_Auto_It function ","");

        boolean flag= false;
        String auto_It_File_Path = System.getProperty("user.dir") + "/AutoIt_File_Uploader_Exe/Stocks_FileUpload.exe";
        String Stock_File_Path = System.getProperty("user.dir") + "\\src\\main\\resources\\data\\runTime_Stocks_for_watchlist.txt";

        try {
            this.navigate_to_Particular_Watchlist(Tab_Name, Watchlist_Url, Watchlist_Name);

            // Click button that opens OS dialog
            helper.safeClick(WebElement_Select_A_File_Button);
            Thread.sleep(1000);
            // Execute AutoIT EXE
//            Runtime.getRuntime().exec(
//        "C:\\Users\\prath\\IdeaProjects\\ChartInk_Intraday_Trend_Entry_With_M_Pattern\\AutoIt_File_Uploader_Exe\\Stocks_FileUpload.exe");

            Runtime.getRuntime().exec(
                    auto_It_File_Path + " \"" + Stock_File_Path + "\"");

            // Wait to ensure file upload completes
            if (helper.safeFindElement(WebElement_Upload_Button,4)) {
                Thread.sleep(3000);
                this.click_Upload_Button();
                if (helper.safeFindElement(WebElement_WatchlistIS_Uploaded_Text, 2)) {

                    //browserAlertHandler.click_Ok();
                    Thread.sleep(1000);
                    this.click_On_Popup_Ok_Button();
                    Thread.sleep(1000);

                    System.out.println("Stock : '" + Alert_Stock_Names + "' added in watchlist '" + Watchlist_Name + "'"  + " using Auto_It App");
                    ReportUtil.report(true, "PASS", "Stock : '" + Alert_Stock_Names + "' added in watchlist '" + Watchlist_Name + "'", " using Auto_It App");
                }
            } else {

                System.out.println("Stock '" + Alert_Stock_Names + "' not added to Watchlist '" + Watchlist_Name + "' " + Watchlist_Url +" using Auto_It App");
                ReportUtil.report(false, "FAIL",
                        "Stock '" + Alert_Stock_Names + "' not added to Watchlist '" + Watchlist_Name + "' ", Watchlist_Url +" using Auto_It App");
            }

        }catch (Exception e) {

            System.out.println("upload_Stock_List_TextFile_Using_Auto_It: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "upload_Stock_List_TextFile_Using_Auto_It, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- upload_Stock_List_TextFile_Using_Auto_It function ", "");

        return flag;
    }
    public boolean upload_Stock_List_TextFile_Directly (String File_Location , String Tab_Name, String Watchlist_Name, String Watchlist_Url){

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- upload_Stock_List_TextFile_Directly function ","");

        boolean flag= false;

        try {
            this.navigate_to_Particular_Watchlist(Tab_Name, Watchlist_Url, Watchlist_Name);

//            helper.sendKeysSafe(WebElement_Select_A_File_Button,File_Location);
            helper.makeElementvisibleByJavaScript(WebElement_FileInput);
            helper.sendKeysSafe(WebElement_FileInput,File_Location);
            helper.triggerFrontendStateByJavaScript(WebElement_FileInput);

            // Wait to ensure file upload completes

            Thread.sleep(1000);

            this.click_Upload_Button();

            this.isElement_Success_Message_Visible();

            ReportUtil.report( true, "PASS", "Text file ",  "'"+File_Location + "' uploaded successfully.");

        }catch (Exception e) {

            System.out.println("upload_Stock_List_TextFile: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "upload_Stock_List_TextFile_Directly, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- upload_Stock_List_TextFile_Directly function ", "");

        return flag;
    }
    public boolean upload_Stock_List_TextFile(String File_Location){

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- upload_Stock_List_TextFile function ","");

        boolean flag= false;

        try {

            helper.sendKeysSafe(WebElement_Choose_File_Button,File_Location);

            // Wait to ensure file upload completes

            Thread.sleep(1000);

            this.click_Upload_Button();

            this.isElement_Success_Message_Visible();

            ReportUtil.report( true, "PASS", "Text file ",  "'"+File_Location + "' uploaded successfully.");

        }catch (Exception e) {

            System.out.println("upload_Stock_List_TextFile: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "upload_Stock_List_TextFile, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- upload_Stock_List_TextFile function ", "");

        return flag;
    }

    public boolean empty_All_Watchlists_For_Strategies(String configFilePath) throws IOException {

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- empty_All_Watchlists_For_Strategies function ", "");

        String inputPath = configFilePath;
        String watchlist_Name = "";
        String watchlist_Url = "";

        String Watchlist_Defaut_Url = prop.getProperty("Watchlist_Defaut_Url");

        boolean flag= false;

        try {
            FileInputStream fis = new FileInputStream(inputPath);
            Properties watchlist_prop = new Properties();
            watchlist_prop.load(fis);

            // Get all property keys
            Set<String> keys = watchlist_prop.stringPropertyNames();

            // Get total count of properties
            int count = keys.size();
            System.out.println("Total properties: " + count);

            // Loop through properties and print key-value pairs
            int i = 1;
            for (String key : keys) {

                String value = watchlist_prop.getProperty(key);
                System.out.println("Property " + i + ": " + key + " = " + value);

                watchlist_Name = key;
                watchlist_Url = Watchlist_Defaut_Url + value;

                this.navigate_to_Particular_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE, watchlist_Url, watchlist_Name);

                if (this.isElement_NoStocksOnWatchlist_Message_Visible()) {
                    System.out.println("Watchlist " + i + ": " + watchlist_Name + " is already empty ");
                    ReportUtil.report(true, "INFO", "Watchlist " + i + ": " + watchlist_Name + " is already empty ", "");
                } else {
                    this.click_Empty_Watchlist_Button();
                    browserAlertHandler.click_Ok();
                    Thread.sleep(2000);
                    ReportUtil.report(this.isElement_NoStocksOnWatchlist_Message_Visible(), "PASS", "Watchlist " + i + ": " + watchlist_Name + " is empty now", "");

                }
            }
                i++;

            // Close the file stream
            fis.close();

        } catch (Exception e) {

            System.out.println("empty_All_Watchlists_For_Strategies: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "empty_All_Watchlists_For_Strategies, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- empty_All_Watchlists_For_Strategies function ", "");

        return flag;
    }


    public void delete_Stock_From_Watchlist(String Tab_Name, String Watchlist_Name, String Watchlist_Url , String StockName) throws IOException {

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- delete_Stock_From_Watchlist function :  ",
                "Watchlist Name : '" + Watchlist_Name + "' ,Watchlist_url : '"+ Watchlist_Url + "', Stock name '" + StockName + "'.");

        this.navigate_to_Particular_Watchlist(Tab_Name, Watchlist_Url, Watchlist_Name);
        String stockFullName = "";
        try {
            this.send_Text_To_Stock_Search_Textbox(StockName);
            stockFullName = fAndOStocksprop.getProperty(StockName).trim();

            if (this.isElement_NoStocksOnWatchlist_Message_Visible()) {
                System.out.println("Stock : '" + StockName + "' not found in watchlist '" + Watchlist_Name + "'");
                ReportUtil.report(true, "INFO", "Stock : '" + StockName + "' not found in watchlist '" + Watchlist_Name + "'", "");
            } else {

                Thread.sleep(500);
                this.click_On_Stock_Remove_Link(stockFullName);
                Thread.sleep(500);
                this.click_On_Remove_Stock_Popup_Confirmation_Button();
                //browserAlertHandler.click_Ok();

                System.out.println("Stock '" + StockName + "' removed from Watchlist '" + Watchlist_Name + "' ");
                ReportUtil.report(this.isElement_NoStocksOnWatchlist_Message_Visible(), "PASS",
                        "Stock '" + StockName + "' removed from Watchlist '" + Watchlist_Name + "' ", "");
            }

        } catch (Exception e) {

            System.out.println("delete_Stock_From_Watchlist: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "delete_Stock_From_Watchlist, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- delete_Stock_From_Watchlist function ",
                "Not able to remove Stock '" + StockName + "' from Watchlist '" + Watchlist_Name + "' ");
    }

    public void delete_Stock_From_Watchlist(String Tab_Name, String Watchlist_Name, String Watchlist_Url , String[] StockNames) throws IOException {

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- delete_Stock_From_Watchlist function :  ",
                "Watchlist Name : '" + Watchlist_Name + "' ,Watchlist_url : '"+ Watchlist_Url + "'," +
                        " Stock names '" + String.join(", ", StockNames) + "'.");

        String[] stocks = StockNames;
        String stockFullName = "";

        this.navigate_to_Particular_Watchlist(Tab_Name, Watchlist_Url, Watchlist_Name);

        for (String StockName : stocks) {
            try {
                StockName = StockName.trim();

                //get stock full name
                stockFullName = fAndOStocksprop.getProperty(StockName).trim();

                this.send_Text_To_Stock_Search_Textbox(stockFullName);
                Thread.sleep(100);
                if (this.isElement_NoStocksOnWatchlist_Message_Visible()) {
                    System.out.println("Stock : '" + StockName + "' not found in watchlist '" + Watchlist_Name + "'");
                    ReportUtil.report(true, "INFO", "Stock : '" + StockName + "' not found in watchlist '" + Watchlist_Name + "'", "");
                }  else {

                    this.click_On_Stock_Remove_Link(stockFullName);
                    Thread.sleep(500);
                    this.click_On_Remove_Stock_Popup_Confirmation_Button();
                   // browserAlertHandler.click_Ok();
//                    Thread.sleep(1000);
//                    browserAlertHandler.click_Ok();
                    System.out.println("Stock '" + StockName + "' removed from Watchlist '" + Watchlist_Name + "' ");
                    ReportUtil.report(true, "PASS",
                            "Stock '" + StockName + "' removed from Watchlist '" + Watchlist_Name + "' ", "");
                }


            } catch (Exception e) {

                System.out.println("delete_Stock_From_Watchlist: " + e.getMessage());
                ReportUtil.report( false, "FAIL", "delete_Stock_From_Watchlist, ",  e.getMessage()+
                        ": Not able to remove Stock '" + StockName + "' from Watchlist '" + Watchlist_Name + "'" );
            }
        }
        ReportUtil.report(true, "INFO", "-- Function -- Ending -- delete_Stock_From_Watchlist function ","" );
    }

    public void add_Stocks_To_Watchlist(String Tab_Name, String Watchlist_Name, String Watchlist_Url , String StockName) throws IOException {

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- add_Stocks_To_Watchlist function :  ",
                "Watchlist Name : '" + Watchlist_Name + "' ,Watchlist_url : '"+ Watchlist_Url + "'," +
                        " Stock names '" + StockName + "'.");

        String stock = StockName;
        String stockFullName = "";

        this.navigate_to_Particular_Watchlist(Tab_Name, Watchlist_Url, Watchlist_Name);

        try {
            StockName = StockName.trim();

            //get stock full name
            stockFullName = fAndOStocksprop.getProperty(StockName).trim();

            helper.sendKeysSafe(WebElement_Search_Textbox,stockFullName);
            //Thread.sleep(1000);
            helper.safeFindElement(get_WebElement_Of_StockName_Of_Add_StockList(stockFullName),2);
            Thread.sleep(500);
            //helper.safeClick(get_WebElement_Of_StockName_Of_Add_StockList(stockFullName));
            helper.forceClickByJavaScript(get_WebElement_Of_StockName_Of_Add_StockList(stockFullName));

            if (helper.safeFindElement(WebElement_StockAddedToWatchlist_Text,2)) {

                //browserAlertHandler.click_Ok();
                this.click_On_Popup_Ok_Button();
                Thread.sleep(1000);

                System.out.println("Stock : '" + StockName + "' added in watchlist '" + Watchlist_Name + "'");
                ReportUtil.report(true, "PASS", "Stock : '" + StockName + "' added in watchlist '" + Watchlist_Name + "'", "");
            }  else {

                System.out.println("Stock '" + StockName + "' not added to Watchlist '" + Watchlist_Name + "' ");
                ReportUtil.report(false, "FAIL",
                        "Stock '" + StockName + "' not added to Watchlist '" + Watchlist_Name + "' ", "");
            }

            } catch (Exception e) {

                System.out.println("add_Stocks_To_Watchlist: " + e.getMessage());
                ReportUtil.report( false, "FAIL", "add_Stocks_To_Watchlist, ",  e.getMessage()+
                        ": Not able to add Stock '" + StockName + "' into Watchlist '" + Watchlist_Name + "'" );
            }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- add_Stocks_To_Watchlist function ","" );
    }

    public void add_Stocks_To_Watchlist(String Tab_Name, String Watchlist_Name, String Watchlist_Url , String[] StockNames) throws IOException {

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- add_Stocks_To_Watchlist function :  ",
                "Watchlist Name : '" + Watchlist_Name + "' ,Watchlist_url : '"+ Watchlist_Url + "'," +
                        " Stock names '" + String.join(", ", StockNames) + "'.");

        String[] stocks = StockNames;
        String stockFullName = "";

        this.navigate_to_Particular_Watchlist(Tab_Name, Watchlist_Url, Watchlist_Name);

        for (String StockName : stocks) {
            try {
                StockName = StockName.trim();

                //get stock full name
                stockFullName = fAndOStocksprop.getProperty(StockName).trim();

               helper.sendKeysSafe(WebElement_Search_Textbox,stockFullName);

//                if ((StockName.equals("M&MFIN")) || (StockName.contains("M&M")) || StockName.equals("LT")) {       // For stocks like 	M&MFIN, M&M
//                    helper.sendKeysSafe(WebElement_Search_Textbox,stockFullName);
//                }else {
//                    helper.sendKeysSafe(WebElement_Search_Textbox,StockName);
//                }

                // to handle no stock present condition eventhough correct stock details are ebntered
//                if ( helper.safeFindElement(get_WebElement_Of_StockName_Of_Add_StockList(this.text_No_Stock_Present),2)){
//                    helper.sendKeysSafe(WebElement_Search_Textbox,stockFullName);
//                }
                if (helper.safeFindElement(get_WebElement_Of_StockName_Of_Add_StockList(stockFullName),2) == false){
                    helper.sendKeysSafe(WebElement_Search_Textbox,stockFullName);
                }
                //helper.safeFindElement(get_WebElement_Of_StockName_Of_Add_StockList(stockFullName),2);
                Thread.sleep(500);

                //helper.safeClick(get_WebElement_Of_StockName_Of_Add_StockList(stockFullName));
                helper.forceClickByJavaScript(get_WebElement_Of_StockName_Of_Add_StockList(stockFullName));
                Thread.sleep(500);

                if (helper.safeFindElement(WebElement_StockAddedToWatchlist_Text,2)) {

                    //browserAlertHandler.click_Ok();
                    this.click_On_Popup_Ok_Button();
                    Thread.sleep(1000);

                    System.out.println("Stock : '" + StockName + "' added in watchlist '" + Watchlist_Name + "'");
                    ReportUtil.report(true, "PASS", "Stock : '" + StockName + "' added in watchlist '" + Watchlist_Name + "'", "");
                }  else {

                    System.out.println("Stock '" + StockName + "' not added to Watchlist '" + Watchlist_Name + "' ");
                    ReportUtil.report(true, "FAIL",
                            "Stock '" + StockName + "' not added to Watchlist '" + Watchlist_Name + "' ", "");
                }

            } catch (Exception e) {

                System.out.println("add_Stocks_To_Watchlist: " + e.getMessage());
//                continue;
                ReportUtil.report( true, "FAIL", "add_Stocks_To_Watchlist, ",  e.getMessage()+
                        ": Not able to add Stock '" + StockName + "' into Watchlist '" + Watchlist_Name + "'" );
            }
        }
        ReportUtil.report(true, "INFO", "-- Function -- Ending -- add_Stocks_To_Watchlist function ","" );
    }

//    public boolean empty_All_Watchlists_For_Strategies(String configFilePath) throws IOException {
//
//        ReportUtil.report(true, "INFO", "-- Function -- Starting -- empty_All_Watchlists_For_Strategies function ", "");
//
//        String inputPath = configFilePath;
//        String watchlist_Name = "";
//        String watchlist_Url = "";
//
//        String ST2_Cndt2_Watchlist_Defaut_Url = prop.getProperty("ST2_Cndt_2_Watchlist_Url");
//
//        String ST2_Cndt3_watchlist_Name = prop.getProperty("ST2_Cndt3_Watchlist_Page_Name");
//        String ST2_Cndt3_watchlist_Url = prop.getProperty("ST2_Cndt3_Watchlist_Page_Url");
//
//        // If below string is true then need to clear data for watchlist till 12.55 PM only
//        String To_Consider_Previous_Day_Filtered_Stocks_For_Today =  prop.getProperty("to_Consider_Previous_Day_Filtered_Stocks_For_Today");
//        int Series_Count_of_Watchlist = 0;  // eg if watchlist na s is 'ST1_Cndt_1_Time_2_25_PM_S63' then get S63
//
//        boolean flag= false;
//
//        try {
//            FileInputStream fis = new FileInputStream(inputPath);
//            Properties watchlist_prop = new Properties();
//            watchlist_prop.load(fis);
//
//            // Get all property keys
//            Set<String> keys = watchlist_prop.stringPropertyNames();
//
//            // Get total count of properties
//            int count = keys.size();
//            System.out.println("Total properties: " + count);
//
//            // Loop through properties and print key-value pairs
//            int i = 1;
//            for (String key : keys) {
//                // If below condition is true then need to clear data for ST2_Cndt2 watchlists till 12.55 PM only, mhence avoid series greater than S46
//                if (To_Consider_Previous_Day_Filtered_Stocks_For_Today.equals("true") && Integer.parseInt(key.split("_S")[1]) >= 46) {
//                    // Do nothing
//                    System.out.println("Watchlist " + i + ": " + watchlist_Name + " not deleting the previous day stocks as considering these stocks for today's validation  ");
//                } else {
//                    String value = watchlist_prop.getProperty(key);
//                    System.out.println("Property " + i + ": " + key + " = " + value);
//
//                    watchlist_Name = key;
//                    watchlist_Url = ST2_Cndt2_Watchlist_Defaut_Url + value;
//
//                    this.navigate_to_Particular_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE, watchlist_Url, watchlist_Name);
//
//                    if (this.isElement_NoStocksOnWatchlist_Message_Visible()) {
//                        System.out.println("Watchlist " + i + ": " + watchlist_Name + " is already empty ");
//                        ReportUtil.report(true, "INFO", "Watchlist " + i + ": " + watchlist_Name + " is already empty ", "");
//                    } else {
//                        this.click_Empty_Watchlist_Button();
//                        browserAlertHandler.click_Ok();
//                        Thread.sleep(500);
//                        ReportUtil.report(this.isElement_NoStocksOnWatchlist_Message_Visible(), "PASS", "Watchlist " + i + ": " + watchlist_Name + " is empty now", "");
//
//                    }
//                }
//                i++;
//            }
//
//            // Close the file stream
//            fis.close();
//
//            // Empty ST2_Cndt3 watchlist
//            watchlist_Name = ST2_Cndt3_watchlist_Name;
//            watchlist_Url = ST2_Cndt3_watchlist_Url;
//
//            this.navigate_to_Particular_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE, watchlist_Url, watchlist_Name);
//            if (this.isElement_NoStocksOnWatchlist_Message_Visible()){
//                System.out.println("Watchlist " + i + ": " + watchlist_Name + " is already empty ");
//                ReportUtil.report(true, "INFO", "Watchlist " + i + ": " + watchlist_Name + " is already empty ", "");
//            }else {
//                this.click_Empty_Watchlist_Button();
//                browserAlertHandler.click_Ok();
//                Thread.sleep(500);
//                ReportUtil.report(this.isElement_NoStocksOnWatchlist_Message_Visible(), "PASS", "Watchlist " + i + ": " + watchlist_Name + " is empty now", "");
//
//            }
//
//        } catch (Exception e) {
//
//            System.out.println("empty_All_Watchlists_For_Strategies: " + e.getMessage());
//            ReportUtil.report( false, "FAIL", "empty_All_Watchlists_For_Strategies, ",  e.getMessage());
//        }
//
//        ReportUtil.report(true, "INFO", "-- Function -- Ending -- empty_All_Watchlists_For_Strategies function ", "");
//
//        return flag;
//    }


    //    public boolean file_Upload_With_AutoIt_Exe(String AutoIt_Exe_Location_For_Watchlist_Stocks){
//
//        ReportUtil.report(true, "INFO", "-- Function -- Starting -- file_Upload_With_AutoIt_Exe function ","");
//
//        boolean flag= false;
//
//        try {
//
//            this.click_Choose_File_Button();
//
//            // Run the AutoIt script
//            Runtime.getRuntime().exec(AutoIt_Exe_Location_For_Watchlist_Stocks);
//
//            // Wait to ensure file upload completes
//            Thread.sleep(1000);
//
//            this.click_Upload_Button();
//
//            this.isElement_Success_Message_Visible();
//
//            ReportUtil.report( true, "PASS", "Verify text file upload, ",  "'"+AutoIt_Exe_Location_For_Watchlist_Stocks + "' file upload successfully.");
//
//        }catch (Exception e) {
//
//            System.out.println("file_Upload_With_AutoIt_Exe: " + e.getMessage());
//            ReportUtil.report( false, "FAIL", "file_Upload_With_AutoIt_Exe, ",  e.getMessage());
//        }
//
//        ReportUtil.report(true, "INFO", "-- Function -- Ending -- file_Upload_With_AutoIt_Exe function ", "");
//
//        return flag;
//    }

}
