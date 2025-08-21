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
    public static final By WebElement_Alert_Name_Link = By.xpath("//*[contains(text(),'Trigger')]");
    public static final By WebElement_Copy_Button = By.xpath("//*[contains(text(),'Copy')]");

    public static final By WebElement_PopUp_Ok_Button = By.xpath("//*[@role='dialog']//*[contains(text(),'Ok')]");

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

    public boolean verify_And_Get_Latest_Alert_Displayed_For_Strategies(String TextMessage , String TabNameToNavigate) throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- verify_And_Get_Latest_Alert_Displayed_For_Strategies function",  "");

        boolean new_Alert_Displayed = false;
        String alerts_Stock_Data = "";
        String latest_Alert_TimeStamp = "";
        String latest_Alert_Stock_Names = "";
        String default_Alert_Time_Stamp = "";

        try {

            newTabsSetUp.navigateToTab(TabNameToNavigate);

            if (this.isElement_Copy_Button_Visible()) {

                //Click on Copy button
                this.click_On_Copy_Button();
                this.click_On_Popup_Ok_Button();

                //Paste to run time data file for reading the Alerts
                FileAndFolderFunctions.paste_Copied_Data_To_Text_File(Constants.TEXTFILE_PATH_FOR_COPY_PASTED_ALERTS_OUTPUT);

                //Read the Textfile for stock Alerts
                alerts_Stock_Data = FileAndFolderFunctions.read_Text_File(Constants.TEXTFILE_PATH_FOR_COPY_PASTED_ALERTS_OUTPUT, 2);
                latest_Alert_TimeStamp = this.get_Latest_Alert_TimeStamp(alerts_Stock_Data);
                latest_Alert_Stock_Names = this.get_Latest_Alert_Stock_Names(alerts_Stock_Data);

                if (TextMessage.contains(Constants.ST1_CONDITION_1)){
                    default_Alert_Time_Stamp = Constants.ST1_DEFAULT_ALERT_TIMESTAMP;
                }else if(TextMessage.contains(Constants.ST2_CONDITION_1)){
                    default_Alert_Time_Stamp = Constants.ST2_DEFAULT_ALERT_TIMESTAMP;
                }

                // Verify Alert is latest or not
                if (DateTimeFunctions.compare_Date_Time(latest_Alert_TimeStamp,default_Alert_Time_Stamp)){
                    new_Alert_Displayed = true;

                    //updating details as gloabl constant
                    if (TextMessage.contains(Constants.ST1_CONDITION_1)){
                        Constants.ST1_DEFAULT_ALERT_TIMESTAMP = latest_Alert_TimeStamp;
                    }else if(TextMessage.contains(Constants.ST2_CONDITION_1)){
                        Constants.ST2_DEFAULT_ALERT_TIMESTAMP = latest_Alert_TimeStamp;
                    }

                    Constants.LATEST_ALERT_TIMESTAMP = latest_Alert_TimeStamp;
                    Constants.LATEST_ALERT_STOCK_NAMES= latest_Alert_Stock_Names ;

                    ReportUtil.report(true, "PASS", "Verify latest Alerts displayed for " + TextMessage, "latest Alerts displayed" +
                            "at " +Constants.LATEST_ALERT_TIMESTAMP+ " : "+  Constants.LATEST_ALERT_STOCK_NAMES );
                } else {
                    ReportUtil.report(true, "INFO", "Verify latest Alerts displayed for " + TextMessage, "No new Alert displayed");
                }

            } else {
                ReportUtil.report(true, "FAIL", "verify_And_Get_Latest_Alert_Displayed_for " + TextMessage,
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

    public String get_Latest_Alert_TimeStamp(String Stock_Data) throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- Get_Latest_Alert_TimeStamp function",  "");

        String final_Date = "";

        try {
            // To get the Alert date time stamp

            // Outdated Example Output: Tue Jul 8 2025, 1:05 pm	2	HINDUNILVR, KALYANKJIL
            // New Example Output: Fri, Aug 8, 2025 11:55 AM	2	CONCOR, CUMMINSIND
            String[] lines = Stock_Data.split("\\n");

            // Example Output: Tue Jul 8 2025, 1:05
            String[] date_parts = lines[1].split("(?i)\\s*(AM|PM)\\s*");

            final_Date = date_parts[0].replaceFirst(",", "").replaceFirst(",", "");

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

    public String get_Latest_Alert_Stock_Names(String Stock_Data) throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- get_Latest_Alert_Stock_Names function",  "");

        String final_Stocks = "";

        try {
            // To get the Alert date time stamp

            // Example Output: Tue Jul 8 2025, 1:05 pm	2	HINDUNILVR, KALYANKJIL
            String[] lines = Stock_Data.split("\\n");

            // Example Output: HINDUNILVR, KALYANKJIL
            String[] parts = lines[1].split("\\t");
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
