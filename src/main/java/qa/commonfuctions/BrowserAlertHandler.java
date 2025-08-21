package qa.commonfuctions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.PageFactory;
import qa.base.BaseTest;
import qa.utils.ReportUtil;

public class BrowserAlertHandler extends BaseTest {

    public BrowserAlertHandler() {
        PageFactory.initElements(driver, this);
    }

    public static void click_Ok () throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- Browser_Alert_Click_Ok function ", "");

        try{
            // Switch to alert
            Alert alert = driver.switchTo().alert();
            // ==== To Accept alert (click OK) ====
            alert.accept();
            System.out.println("Clicked on Ok button ");
            ReportUtil.report(true, "PASS", "Clicked on Ok button", "");

        }catch (Exception e) {

            System.out.println("Browser_Alert_Click_Ok: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Browser_Alert_Click_Ok, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- Browser_Alert_Click_Ok function ", "");

    }

    public static void click_Dismiss () throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- Browser_Alert_Click_Dismiss function ", "");

        try{
            // Switch to alert
            Alert alert = driver.switchTo().alert();
            // ==== To dismiss alert (click cancel) ====
            alert.dismiss();
            System.out.println("Clicked on Dismiss button ");
            ReportUtil.report(true, "PASS", "Clicked on Dismiss button", "");

        }catch (Exception e) {

            System.out.println("Browser_Alert_Click_Dismiss: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Browser_Alert_Click_Dismiss, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- Browser_Alert_Click_Dismiss function ", "");

    }

    public static String get_Alert_Text() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- Browser_Alert_Get_Text function ", "");

        String message = "";

        try{
            // Switch to alert
            Alert alert = driver.switchTo().alert();
            // ==== get text ====
            message = alert.getText();
            System.out.println("Browser_Alert_Get_Text : "+ message);
            ReportUtil.report(true, "PASS", "Browser_Alert_Get_Text : ", message);

        }catch (Exception e) {

            System.out.println("Browser_Alert_Get_Text: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Browser_Alert_Get_Text, ",  e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- Browser_Alert_Get_Text function ", "");

        return message;
    }

}
