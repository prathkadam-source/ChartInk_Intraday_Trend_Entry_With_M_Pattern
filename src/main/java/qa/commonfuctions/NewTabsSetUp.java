package qa.commonfuctions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import qa.base.BaseTest;
import qa.helperClass.SeleniumHelper;
import qa.utils.ReportUtil;
import java.util.Properties;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ArrayList;
import qa.helperClass.SeleniumHelper;
import qa.pages.*;
import qa.pages.LoginPage;
import org.openqa.selenium.By;
import qa.commonfuctions.*;

public class NewTabsSetUp extends BaseTest {

    SeleniumHelper helper;
    // Thread-safe, static, and publicly accessible
    public static List<String> tabs ;

//    LoginPage loginPage = new LoginPage();
//    WatchlistPage watchlistPage = new WatchlistPage();
//
//    AlertPage alertPage = new AlertPage();

    public NewTabsSetUp() {
        PageFactory.initElements(driver, this);
        helper = new SeleniumHelper();
    }

    public void openNewTab(){

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- OpenNewTab function",  "");

        try {

            ((JavascriptExecutor) driver).executeScript("window.open()");

        } catch (Exception e) {

            System.out.println("new tab not open: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Verify new Tab opens, ",  "new tab not open: " + e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Function -- Ending -- OpenNewTab function",  "");
    }

    public void getTabsCount (){

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- getNewTabsCount function",  "");

        try {

            tabs = new CopyOnWriteArrayList<>(driver.getWindowHandles());
            ReportUtil.report( true, "PASS", "Get Tabs Count, ",  "Total (" + tabs.stream().count() + ") tabs opened ");

        } catch (Exception e) {

            System.out.println("Error for getTabsCount " + e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Function -- Ending -- getNewTabsCount function",  "");
    }

    public void switchToTab(int tabNumber){

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- switchToTab function",  "");

        try {

            driver.switchTo().window(tabs.get(tabNumber));
            ReportUtil.report( true, "PASS", "Switch To Tab, ",  "Switched to tab number ("+ tabNumber + ")");

        } catch (Exception e) {

            System.out.println("Error for switchToTab: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Verify switchToTab, ",  "Not able to switch to tab: " + e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Function -- Ending -- switchToTab function",  "");
    }

    public void setUrl(String UrlName){

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- setUrl function ",  "");

        try {
            // Load another URL in the new tab
            driver.get(UrlName);
            ReportUtil.report( true, "PASS", "Set Url in Tab, ",  "Url set to " + UrlName);

        } catch (Exception e) {

            System.out.println("Error for setUrl: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Verify setUrl, ",  "New Url not open: " + e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Function -- Ending -- setUrl function",  "");
    }

    public void verifySelectedTab(String tabName, By byElement, String textToBeVerified ){

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- verify Selected Tab function",  "");

        try {
            if (textToBeVerified==""){
                if (helper.isElementVisible(byElement)){
                    ReportUtil.report( true, "PASS", "Verify '" + tabName + "' Webpage Displayed, ",  " "+tabName + " Webpage Displayed");

                } else {
                    ReportUtil.report( false, "FAIL", "Verify '" + tabName + "' Webpage Displayed, ",  " " +tabName + " Webpage NOT Displayed");
                }
            }else {
                if(helper.getTextSafe(byElement).contains(textToBeVerified)){
                    ReportUtil.report( true, "PASS", "Verify " + textToBeVerified + " " +tabName + " Webpage Displayed, ",  textToBeVerified + " " + tabName + " Webpage Displayed");

                } else {
                    ReportUtil.report( false, "FAIL", "Verify " + textToBeVerified + " " + tabName + " Webpage Displayed, ",  textToBeVerified + " " + tabName + " Webpage NOT Displayed");
                }
            }

        }

        catch (Exception e) {

            System.out.println("Error for verifySelectedTab: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "verifySelectedTab, ",  "Error: " + e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Function -- Ending -- verifySelectedTab function",  "");

    }

    public void navigateToTab(String tabName){

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- Navigate To Tab function",  "");

        try {
            switch (tabName) {
                //ST1
                case Constants.TAB_ALERTPAGE_NAME_ST_1_FIRST_CONDITION:
                    this.switchToTab(Integer.parseInt(RunTimeDataStore.TabsName.getGlobal(tabName)));
                    browser_Refresh();
                    Thread.sleep(1000);
                    this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_1_FIRST_CONDITION );
                    break;

                case Constants.TAB_ALERTPAGE_NAME_ST_1_SECOND_CONDITION:
                    this.switchToTab(Integer.parseInt(RunTimeDataStore.TabsName.getGlobal(tabName)));
                    browser_Refresh();
                    Thread.sleep(1000);
                    this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_1_SECOND_CONDITION );
                    break;
                //ST2
                case Constants.TAB_ALERTPAGE_NAME_ST_2_FIRST_CONDITION:
                    this.switchToTab(Integer.parseInt(RunTimeDataStore.TabsName.getGlobal(tabName)));
                    browser_Refresh();
                    Thread.sleep(1000);
                    this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_2_FIRST_CONDITION );
                    break;

                case Constants.TAB_ALERTPAGE_NAME_ST_2_SECOND_CONDITION:
                    this.switchToTab(Integer.parseInt(RunTimeDataStore.TabsName.getGlobal(tabName)));
                    browser_Refresh();
                    Thread.sleep(1000);
                    this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_2_SECOND_CONDITION );
                    break;
                //ST3
                case Constants.TAB_ALERTPAGE_NAME_ST_2_THIRD_CONDITION:
                    this.switchToTab(Integer.parseInt(RunTimeDataStore.TabsName.getGlobal(tabName)));
                    browser_Refresh();
                    Thread.sleep(1000);
                    this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_2_THIRD_CONDITION );
                    break;
                //ST4
                case Constants.TAB_ALERTPAGE_NAME_ST_1_THIRD_CONDITION:
                    this.switchToTab(Integer.parseInt(RunTimeDataStore.TabsName.getGlobal(tabName)));
                    browser_Refresh();
                    Thread.sleep(1000);
                    this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_1_THIRD_CONDITION );
                    break;
                //Default Watchlist tab
                case Constants.TAB_DEFAULT_WATCHLIST_PAGE:
                    this.switchToTab(Integer.parseInt(RunTimeDataStore.TabsName.getGlobal(tabName)));
                    browser_Refresh();
                    Thread.sleep(1000);
                    this.verifySelectedTab(Constants.WEBPAGE_WATCHLIST, WatchlistPage.WebElement_Selected_Watchlist_SubTab,"");
                    break;
            }

        }

        catch (Exception e) {

            System.out.println("Error for Navigate To Tab function : " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Navigate To Tab function, ",  " Error : " + e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Function -- Ending -- Navigate To Tab function",  "");

    }

    public void setUpTabsForParallelExecution (){

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- setUpTabsForParallelExecution function ",  "");

        int TabCount = Integer.parseInt(prop.getProperty("subTabsCount"));

        try {
            // Open new tabs
            for (int i = 1; i <= TabCount; i++) {
                this.openNewTab();
                Thread.sleep(1000);
                System.out.println("New tab Count: " + i);
                ReportUtil.report( true, "INFO", "Opened a new tab " + i,  "");
            }

            //Get sub windows handles
            this.getTabsCount();

            this.switchToTab(1);
            this.setUrl(prop.getProperty("Dashboard_Watchlist_Page_Url"));
            this.verifySelectedTab(Constants.WEBPAGE_WATCHLIST, WatchlistPage.WebElement_Selected_Watchlist_SubTab, "");
            RunTimeDataStore.TabsName.putGlobal(Constants.TAB_DEFAULT_WATCHLIST_PAGE,"1");

            //ST 1

            this.switchToTab(2);
            this.setUrl(prop.getProperty("ST1_Cndt1_Alert_Page_Url"));
            this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_1_FIRST_CONDITION);
            RunTimeDataStore.TabsName.putGlobal(Constants.TAB_ALERTPAGE_NAME_ST_1_FIRST_CONDITION,"2");

            this.switchToTab(3);
            this.setUrl(prop.getProperty("ST1_Cndt2_Alert_Page_Url"));
            this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_1_SECOND_CONDITION);
            RunTimeDataStore.TabsName.putGlobal(Constants.TAB_ALERTPAGE_NAME_ST_1_SECOND_CONDITION,"3");

            // ST2

            this.switchToTab(4);
            this.setUrl(prop.getProperty("ST2_Cndt1_Alert_Page_Url"));
            this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_2_FIRST_CONDITION);
            RunTimeDataStore.TabsName.putGlobal(Constants.TAB_ALERTPAGE_NAME_ST_2_FIRST_CONDITION,"4");

            this.switchToTab(5);
            this.setUrl(prop.getProperty("ST2_Cndt2_Alert_Page_Url"));
            this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_2_SECOND_CONDITION);
            RunTimeDataStore.TabsName.putGlobal(Constants.TAB_ALERTPAGE_NAME_ST_2_SECOND_CONDITION,"5");

            // ST3
            this.switchToTab(6);
            this.setUrl(prop.getProperty("ST2_Cndt3_Alert_Page_Url"));
            this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_2_THIRD_CONDITION);
            RunTimeDataStore.TabsName.putGlobal(Constants.TAB_ALERTPAGE_NAME_ST_2_THIRD_CONDITION,"6");

            // ST4
            this.switchToTab(7);
            this.setUrl(prop.getProperty("ST1_Cndt3_Alert_Page_Url"));
            this.verifySelectedTab(Constants.WEBPAGE_ALERT,AlertPage.WebElement_Alert_Name_Link, Constants.TAB_ALERTPAGE_NAME_ST_1_THIRD_CONDITION);
            RunTimeDataStore.TabsName.putGlobal(Constants.TAB_ALERTPAGE_NAME_ST_1_THIRD_CONDITION,"7");


        } catch (Exception e) {

            System.out.println("Error for setUpTabsForParallelExecution: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Verify setUpTabsForParallelExecution, ",  "Error: " + e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Function -- Ending  -- setUrl function",  "");
    }

}
