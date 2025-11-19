package qa.pages;

import org.openqa.selenium.By;
import qa.base.BaseTest;
import qa.commonfuctions.*;
import qa.utils.ReportUtil;
import org.openqa.selenium.support.PageFactory;
import qa.helperClass.SeleniumHelper;

import java.time.LocalDate;

public class AlertPage extends BaseTest {

    SeleniumHelper helper;
    NewTabsSetUp newTabsSetUp = new NewTabsSetUp();

    ArrayFunctions arrayFunctions = new ArrayFunctions();

    public static final By WebElement_Alert_Name_Link = By.xpath("//*[contains(text(),'Trigger')]");
    public static final By WebElement_Copy_Button = By.xpath("//*[contains(text(),'Copy')]");

    public static final By WebElement_PopUp_Ok_Button = By.xpath("//*[@role='dialog']//*[contains(text(),'Ok')]");
    public static final By WebElement_Text_NoTriggersForThisAlertYet = By.xpath( "//*[contains(text(),'No triggers for this alert yet')]");

//    By emailLoginField = By.id("email");
//    By chartInkWebpage = By.xpath("//*[@href='https://chartink.com/']");
//    By passwordLoginField = By.id("password");
//    By loginPrimaryButton = By.xpath("//*[@data-callback='onSubmitloginform']");
//    By loginNotificationCancelButton = By.xpath("//*[@data-callback='onSubmitloginform']");
//    By headerChartInkLink = By.xpath("//*[@href='https://chartink.com/' and @class = 'header-link']");
//    By headerDashboardLink= By.xpath("//*[text()='Dashboard' and @class = 'link' ]");

    public AlertPage() {
        PageFactory.initElements(driver, this);
        helper = new SeleniumHelper();
    }

    public boolean isElement_Copy_Button_Visible() {
        return helper.isElementVisible(WebElement_Copy_Button);
    }

    public boolean isText_Message_NoTriggersForThisAlertYet_Visible() {
        //  return helper.isElementVisible(WebElement_NoDataAvailableInTable_Message);
        //to avoid implicit wait
        return helper.safeFindElement(WebElement_Text_NoTriggersForThisAlertYet,0);
    }

    public void click_On_Copy_Button() throws InterruptedException {
        helper.safeClick(WebElement_Copy_Button);
       // helper.forceClickByJavaScript(WebElement_Copy_Button);
        Thread.sleep(1000);
    }
    public void click_On_Popup_Ok_Button() throws InterruptedException {
        helper.safeFindElement(WebElement_PopUp_Ok_Button,2);
        helper.safeClick(WebElement_PopUp_Ok_Button);
        Thread.sleep(2000);
    }

    public boolean verify_And_Get_Latest_Alert_Displayed_For_Strategies(String TextMessage , String TabNameToNavigate, Boolean GetUniqueStockList ) throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- verify_And_Get_Latest_Alert_Displayed_For_Strategies function",  "");

        boolean new_Alert_Displayed = false;
        String alerts_Stock_Data = "";
        String latest_Alert_TimeStamp = "";
        String latest_Alert_Stock_Names = "";
        String default_Alert_Time_Stamp = "";

        try {

            newTabsSetUp.navigateToTab(TabNameToNavigate);

            if (!this.isText_Message_NoTriggersForThisAlertYet_Visible()) {

                //Click on Copy button
                this.click_On_Copy_Button();
                this.click_On_Popup_Ok_Button();

                //Paste to run time data file for reading the Alerts
                FileAndFolderFunctions.paste_Copied_Data_To_Text_File(Constants.TEXTFILE_PATH_FOR_COPY_PASTED_ALERTS_OUTPUT);

                //Read the Textfile for stock Alerts
                alerts_Stock_Data = FileAndFolderFunctions.read_Text_File(Constants.TEXTFILE_PATH_FOR_COPY_PASTED_ALERTS_OUTPUT, 2);
                latest_Alert_TimeStamp = this.get_Latest_Alert_TimeStamp(alerts_Stock_Data,1,true);

                if (TextMessage.contains(Constants.ST1_CONDITION_1_Step_1)){                       /// ST 1 Starts
                    default_Alert_Time_Stamp = Constants.ST1_Cndt_1_DEFAULT_ALERT_TIMESTAMP;
                }else if(TextMessage.contains(Constants.ST1_CONDITION_2_Step_2)){
                    default_Alert_Time_Stamp = Constants.ST1_Cndt_2_DEFAULT_ALERT_TIMESTAMP;       /// ST 1 ends
                }else if(TextMessage.contains(Constants.ST2_CONDITION_1_Step_1)){                       /// ST 2 Starts
                    default_Alert_Time_Stamp = Constants.ST2_Cndt_1_DEFAULT_ALERT_TIMESTAMP;
                }else if(TextMessage.contains(Constants.ST2_CONDITION_2_Step_2)){
                    default_Alert_Time_Stamp = Constants.ST2_Cndt_2_DEFAULT_ALERT_TIMESTAMP;        /// ST 2 Ends
                }else if(TextMessage.contains(Constants.ST1_CONDITION_3_Step_3)){                       /// ST 4 Starts
                    default_Alert_Time_Stamp = Constants.ST1_Cndt_3_DEFAULT_ALERT_TIMESTAMP;         /// ST 4 Ends
                }else if(TextMessage.contains(Constants.ST2_CONDITION_3_Step_3)){                       /// ST 3 Starts
                    default_Alert_Time_Stamp = Constants.ST2_Cndt_3_DEFAULT_ALERT_TIMESTAMP;         /// ST 3 Ends
                }

                // Verify Alert is latest or not
                if (DateTimeFunctions.compare_Date_Time(latest_Alert_TimeStamp,default_Alert_Time_Stamp)){
                    new_Alert_Displayed = true;

                    //updating details as gloabl constant
                    if (TextMessage.contains(Constants.ST1_CONDITION_1_Step_1)){                       /// ST 1 Starts
                        Constants.ST1_Cndt_1_DEFAULT_ALERT_TIMESTAMP = latest_Alert_TimeStamp;
                    }else if(TextMessage.contains(Constants.ST1_CONDITION_2_Step_2)){
                        Constants.ST1_Cndt_2_DEFAULT_ALERT_TIMESTAMP = latest_Alert_TimeStamp;          /// ST 1 ends
                    }else if(TextMessage.contains(Constants.ST2_CONDITION_1_Step_1)) {                      /// ST 2 Starts
                        Constants.ST2_Cndt_1_DEFAULT_ALERT_TIMESTAMP = latest_Alert_TimeStamp;
                    }else if(TextMessage.contains(Constants.ST2_CONDITION_2_Step_2)) {
                        Constants.ST2_Cndt_2_DEFAULT_ALERT_TIMESTAMP = latest_Alert_TimeStamp;     /// ST 2 Ends
                    }else if(TextMessage.contains(Constants.ST1_CONDITION_3_Step_3)) {                  /// ST 4 Starts
                        Constants.ST1_Cndt_3_DEFAULT_ALERT_TIMESTAMP = latest_Alert_TimeStamp;   /// ST 4 Ends
                    }else if(TextMessage.contains(Constants.ST2_CONDITION_3_Step_3)) {                  /// ST 3 Starts
                        Constants.ST2_Cndt_3_DEFAULT_ALERT_TIMESTAMP = latest_Alert_TimeStamp;   /// ST 3 Ends
                    }

                    if (GetUniqueStockList) {
                        // This function will get unique stocks name compared with current and previous timestamp alert
                        latest_Alert_Stock_Names = this.get_Unique_Stock_Names_From_Latest_Alert_TimeStamp(alerts_Stock_Data);
                    }else {
                        latest_Alert_Stock_Names = this.get_Latest_Alert_Stock_Names(alerts_Stock_Data, 1);
                    }

                    Constants.LATEST_ALERT_TIMESTAMP = latest_Alert_TimeStamp;
                    Constants.LATEST_ALERT_STOCK_NAMES= latest_Alert_Stock_Names ;

                    ReportUtil.report(true, "PASS", "Verify latest Alerts displayed for " + TextMessage, "latest Alerts displayed" +
                            "at " +Constants.LATEST_ALERT_TIMESTAMP+ " : "+  Constants.LATEST_ALERT_STOCK_NAMES );
                } else {
                    ReportUtil.report(true, "INFO", "Verify latest Alerts displayed for " + TextMessage, "No new Alert displayed");
                }

            } else {
                ReportUtil.report(true, "INFO", "verify_And_Get_Latest_Alert_Displayed_for " + TextMessage,
                        "Alerts yet not displayed");

            }

        }catch (Exception e) {

            System.out.println("verify_And_Get_Latest_Alert_Displayed_For_Strategies: "+ TextMessage +" : " + e.getMessage());
            ReportUtil.report( false, "FAIL", "verify_And_Get_Latest_Alert_Displayed_For_Strategies, "
                    + TextMessage ,  e.getMessage());
        }
        ReportUtil.report( true, "INFO", "-- Function -- Ending -- verify_And_Get_Latest_Alert_Displayed_For_Strategies function",  "");

        return new_Alert_Displayed;
    }

    public String get_Unique_Stock_Names_From_Latest_Alert_TimeStamp(String alerts_Stock_Data) throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- get_Unique_Stock_Names_From_Latest_Alert_TimeStamp function",  "");

        String latest_Alert_Stock_Names = "";
        String Previuos_Alert_Stock_Names = "";
        String latest_Alert_TimeStamp = "";
        String Previuos_Alert_TimeStamp = "";

        try {

            latest_Alert_Stock_Names = this.get_Latest_Alert_Stock_Names(alerts_Stock_Data,1);
            Previuos_Alert_Stock_Names = this.get_Latest_Alert_Stock_Names(alerts_Stock_Data,2);

            latest_Alert_TimeStamp = this.get_Latest_Alert_TimeStamp(alerts_Stock_Data,1, true);
            Previuos_Alert_TimeStamp = this.get_Latest_Alert_TimeStamp(alerts_Stock_Data, 2, true);

            // Compare two days and return true if both are of same day like €Day1 = Mon, Day 2 = Mon
            if (latest_Alert_TimeStamp.substring(0, 3).equals(Previuos_Alert_TimeStamp.substring(0, 3))){

                // Validate further if both current and previous alerts contains more than one stock in list
                if (latest_Alert_Stock_Names.contains(",") && Previuos_Alert_Stock_Names.contains(",")){

                    String[] UniqueStocksArray = arrayFunctions.getUniqueArrayFromSetOfTwoArrays(latest_Alert_Stock_Names,Previuos_Alert_Stock_Names);
                    latest_Alert_Stock_Names = String.join(", ", UniqueStocksArray);
                }
            }

            ReportUtil.report( true, "INFO", "Latest alert Stock_Names :, ",  latest_Alert_Stock_Names);

        }catch (Exception e) {

            System.out.println("get_Unique_Stock_Names_From_Latest_Alert_TimeStamp: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "get_Unique_Stock_Names_From_Latest_Alert_TimeStamp, ",  e.getMessage());
        }
        ReportUtil.report( true, "INFO", "-- Function -- Ending -- get_Unique_Stock_Names_From_Latest_Alert_TimeStamp function",  "");

        return latest_Alert_Stock_Names;
    }

    public String get_Latest_Alert_TimeStamp(String Stock_Data, int RowNum, Boolean ReplaceCommaInDate) throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- Get_Latest_Alert_TimeStamp function",  "");

        String final_Date = "";

        try {
            // To get the Alert date time stamp

            // Outdated Example Output: Tue Jul 8 2025, 1:05 pm	2	HINDUNILVR, KALYANKJIL
            // New Example Output: Fri, Aug 8, 2025 11:55 AM	2	CONCOR, CUMMINSIND
            String[] lines = Stock_Data.split("\\n");

            // Example Output: Tue Jul 8 2025, 1:05
            String[] date_parts = lines[RowNum].split("(?i)\\s*(AM|PM)\\s*");

            if (ReplaceCommaInDate) {
                final_Date = date_parts[0].replaceFirst(",", "").replaceFirst(",", "");
            }else {
                final_Date = date_parts[0];
            }

            //To construct final date
            if (lines[1].contains(" AM")){
                final_Date = final_Date + " AM";
            }else {
                final_Date = final_Date + " PM";
            }

            String current_Year= Integer.toString(LocalDate.now().getYear());
            final_Date =final_Date.replace(current_Year, current_Year+',');

            System.out.println("Latest alert TimeStamp : "+ final_Date);
            ReportUtil.report( true, "INFO", "Latest alert TimeStamp :, ",  final_Date);

        }catch (Exception e) {

            System.out.println("Get_Latest_Alert_TimeStamp: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Get_Latest_Alert_TimeStamp, ",  e.getMessage());
        }
        ReportUtil.report( true, "INFO", "-- Function -- Ending -- Get_Latest_Alert_TimeStamp function",  "");

        return final_Date;
    }

    public String get_Latest_Alert_Stock_Names(String Stock_Data, int RowNum) throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- get_Latest_Alert_Stock_Names function",  "");

        String final_Stocks = "";

        try {
            // To get the Alert date time stamp

            // Example Output: Tue Jul 8 2025, 1:05 pm	2	HINDUNILVR, KALYANKJIL
            String[] lines = Stock_Data.split("\\n");

            // Example Output: HINDUNILVR, KALYANKJIL
            String[] parts = lines[RowNum].split("\\t");
            final_Stocks = parts[2];

            System.out.println("Latest alert stock names : "+ final_Stocks);
            ReportUtil.report( true, "INFO", "Latest alert stock names :, ",  final_Stocks);

        }catch (Exception e) {

            System.out.println("Latest alert stock names : " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Latest alert stock names , ",  e.getMessage());
        }
        ReportUtil.report( true, "INFO", "-- Function -- Ending -- get_Latest_Alert_Stock_Names function",  "");

        return final_Stocks;
    }

}
