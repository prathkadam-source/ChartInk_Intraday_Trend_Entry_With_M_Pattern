package qa.testCases;

import qa.base.BaseTest;
import qa.commonfuctions.Constants;
import qa.commonfuctions.DateTimeFunctions;
import qa.commonfuctions.FileAndFolderFunctions;
import qa.commonfuctions.NewTabsSetUp;
import qa.pages.AlertPage;
import qa.pages.LoginPage;
import qa.pages.WatchlistPage;
import qa.utils.ReportUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static qa.utils.ReportUtil.finalizeReport;

public class Test_Case_Stratergy_2 extends BaseTest {

    LoginPage loginPage = new LoginPage();
    NewTabsSetUp newTabsSetUp = new NewTabsSetUp();

    AlertPage alertPage = new AlertPage();

    WatchlistPage watchlistPage = new WatchlistPage();

    // Buy_Trades_From_Logic_iFVG ,
    public void Buy_Trades_From_Logic_W_Pattern() {

        ReportUtil.report( true, "INFO", "-- Test case 2 -- Starting -- Buy_Trades_From_Logic_W_Pattern",  "");

        try {

            Step_1_Checking_ST2_CONDITION_1_Alerts();

            /*ST_2_Cndt_2 :   Scan Criteria :
            ST_2_Cndt_2_Intraday_Buy_Side_IFVG_Statergy_Step_2_Failed_Of_Buy_FVG_Breakout
            [0] 5 minute Close crossed_above [-2] 5 minute Low
            And [0] 5 minute Close > [0] 5 minute Sma ( [0] 5 minute Close , 12 )  of Condition 1
            Steps to perform in code:
            1.Add Stocks to 'ST2_Cndt_3_Watchlist' for further scan
            */
            Step_2_Checking_ST2_CONDITION_2_Alerts();

            /*ST_2_Cndt_3 : Scan Criteria :
            ST_2_Cndt_3_Intraday_Buy_Side_iFVG_Statergy_Step_3_Price_Close_Below_SMA_and_FVG,
            prices closed below sell FVG block
            [0] 5 minute Low < [-2] 5 minute Low
            [0] 5 minute Close < [0] 5 minute Sma ( [0] 5 minute Close , 12 ) of Condition 1
            Steps to perform in code:
            1. Add Stocks to 'ST2_Cndt_4_Watchlist' for further scan
             */
            Step_3_Checking_ST2_CONDITION_3_Alerts();

            /*ST_2_Cndt_4 : Scan Criteria :
            ST_2_Cndt_4_Intraday_Buy_Side_iFVG_Statergy_Step_4_Price_Close_above_SMA_and_FVG
            [0] 5 minute Close > [-2] 5 minute Low
            [0] 5 minute Close > [0] 5 minute Sma ( [0] 5 minute Close , 12 ) of Condition 1
            Steps to perform in code:
            1. Add Stocks to 'ST2_Cndt_5_Watchlist' for further scan

            ******** To be worked on to modify condition to
            [0] 5 minute Close > [=1] 5 minute Low
            instead of
            [0] 5 minute Close > [-2] 5 minute Low
             */
            Step_4_Checking_ST2_CONDITION_4_Alerts();

            /*ST_2_Cndt_5 :Scan Criteria :
            ST_2_Cndt_5_Intraday_Buy_Side_iFVG_Statergy_Step_5_If_Price_Closed_below_Sell_FVG_Then_Remove_Stocks
            [0] 5 minute Close < [=1] 5 minute High of Condition 1
            Steps to perform in code:
            1. Remove Stocks from 'ST2_Cndt_5_Watchlist'
             */
            Step_5_Checking_ST2_CONDITION_5_Alerts();


        }catch (InterruptedException e) {

            System.out.println("Test case 2_ Buy_Trades_From_Logic_W_Pattern: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Test case 2 Buy_Trades_From_Logic_W_Pattern, ",  e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Test case 2 -- Ending -- Buy_Trades_From_Logic_W_Pattern",  "");

    }

    public void Step_1_Checking_ST2_CONDITION_1_Alerts() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Step 1 -- Starting -- Step_1_Checking_ST2_CONDITION_1_Alerts", "");

        // <editor-fold desc="Variables">
        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String Current_Watchlist_Details = "";
        String Current_Watchlist_Name = "";
        String Current_Watchlist_Url = "";
        String Comments = "";

//        String ST2_Cndt_2_Watchlist_Name = prop.getProperty("ST2_Cndt_2_Watchlist_Name");
//        String ST2_Cndt_2_Watchlist_Url = prop.getProperty("ST2_Cndt_2_Watchlist_Url");

        // </editor-fold>

        try {

            // If new alert displayed for strategy : ST2_CONDITION_1 then add it to watchlist of strategy : ST2_Cndt2
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST2_CONDITION_1_Step_1,
                    Constants.TAB_ALERTPAGE_NAME_ST_2_FIRST_CONDITION,false)) {

                Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                String[] stocks;

                // Update Stock Alert to textfile
                FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                // <editor-fold desc=" Step 1 - Sub 1">

                ReportUtil.report(true, "INFO", "Step 1 - Sub 1-- Adding stocks from alert ST2_CONDITION_1 to ST2_Cndt2 watchlist ", "");
                //Add Stocks to watchlist
                if (Alerts_Stock_Names.contains(",")) {
                    stocks = Alerts_Stock_Names.split(",");
                } else {
                    stocks = new String[]{Alerts_Stock_Names};
                }
//
                Current_Watchlist_Details = watchlistPage.get_Watchlist_Url(Constants.ST2_CNDT_1_TIME, latest_Alert_TimeStamp);
                Current_Watchlist_Name = Current_Watchlist_Details.split(",")[0];
                Current_Watchlist_Url = Current_Watchlist_Details.split(",")[1];

                // add Stocks from "ST2_CONDITION_1 to watchlist of ST2_Cndt2
                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        Current_Watchlist_Name, Current_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST2_CONDITION_1_Step_1 + System.lineSeparator() + Constants.ACTION_STOCKS_ADDED ;
                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST2_CNDT2_WATCHLIST_UPDATES_FROM_CNDT_1,
                        Comments, Current_Watchlist_Name,
                        Current_Watchlist_Url, Alerts_Stock_Names);
                // </editor-fold>

            }

        } catch (IOException e) {

            System.out.println("Step 1 Step_1_Checking_ST2_CONDITION_1_Alerts: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "Step 1 Step_1_Checking_ST2_CONDITION_1_Alerts, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Step 1 -- Ending -- Step_1_Checking_ST2_CONDITION_1_Alerts", "");
    }

    public void Step_2_Checking_ST2_CONDITION_2_Alerts() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Step 2 -- Starting -- Step_2_Checking_ST2_CONDITION_2_Alerts", "");

        // <editor-fold desc="Variables">
        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String Comments = "";

        String ST2_Cndt_3_Watchlist_Name = prop.getProperty("ST2_Cndt_3_Watchlist_Name");
        String ST2_Cndt_3_Watchlist_Url = prop.getProperty("ST2_Cndt_3_Watchlist_Url");

        // </editor-fold>

        try {

            // If new alert displayed for strategy : ST2_CONDITION_2 then add it to watchlist of strategy : ST2_Cndt3
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST2_CONDITION_2_Step_2,
                    Constants.TAB_ALERTPAGE_NAME_ST_2_SECOND_CONDITION,false)) {

                Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                String[] stocks;

                // Update Stock Alert to textfile
                FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                //Add Stocks to watchlist
                if (Alerts_Stock_Names.contains(",")) {
                    stocks = Alerts_Stock_Names.split(",");
                } else {
                    stocks = new String[]{Alerts_Stock_Names};
                }

                // <editor-fold desc=" Step 2 - Sub 1 - Adding stocks from alert ST2_CONDITION_2 to ST2_Cndt3 watchlist ">
                ReportUtil.report(true, "INFO", "Step 2 - Sub 1 - Adding stocks from alert ST2_CONDITION_2 to ST2_Cndt3 watchlist ", "");

                // add Stocks from "ST2_CONDITION_2 to watchlist of ST2_Cndt3
                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        ST2_Cndt_3_Watchlist_Name, ST2_Cndt_3_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST2_CONDITION_2_Step_2 + "- Sub 1 -" + System.lineSeparator() + Constants.ACTION_STOCKS_ADDED ;
                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT3_WATCHLIST_UPDATES_FROM_CNDT_2,
                        Comments, ST2_Cndt_3_Watchlist_Name,
                        ST2_Cndt_3_Watchlist_Url, Alerts_Stock_Names);
                // </editor-fold>

            }

        } catch (IOException e) {

            System.out.println("Step_2_Checking_ST2_CONDITION_2_Alerts: " + e.getMessage());
            ReportUtil.report(false, "FAIL", " Step_2_Checking_ST2_CONDITION_2_Alerts, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Step 2 -- Ending -- Checking_ST2_CONDITION_2_Alerts", "");
    }

    public void Step_3_Checking_ST2_CONDITION_3_Alerts() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Starting -- Step_3_Checking_ST2_CONDITION_3_Alerts", "");

        // <editor-fold desc="Variables">
        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String Comments = "";

        String ST2_Cndt_4_Watchlist_Name = prop.getProperty("ST2_Cndt_4_Watchlist_Name");
        String ST2_Cndt_4_Watchlist_Url = prop.getProperty("ST2_Cndt_4_Watchlist_Url");

        // </editor-fold>

        try {

            // If new alert displayed for strategy : ST2_CONDITION_3 then add it to watchlist of strategy : ST2_Cndt4
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST2_CONDITION_3_Step_3,
                    Constants.TAB_ALERTPAGE_NAME_ST_2_THIRD_CONDITION,false)) {

                Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                String[] stocks;

                // Update Stock Alert to textfile
                FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                //Add Stocks to watchlist
                if (Alerts_Stock_Names.contains(",")) {
                    stocks = Alerts_Stock_Names.split(",");
                } else {
                    stocks = new String[]{Alerts_Stock_Names};
                }

                // <editor-fold desc=" Step 2 - Sub 1 - Adding stocks from alert ST2_CONDITION_3 to ST2_Cndt4 watchlist ">
                ReportUtil.report(true, "INFO", "Step 3 - Sub 1 - Adding stocks from alert ST2_CONDITION_3 to ST2_Cndt4 watchlist ", "");

                // add Stocks from "ST2_CONDITION_3 to watchlist of ST2_Cndt4
                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        ST2_Cndt_4_Watchlist_Name, ST2_Cndt_4_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST2_CONDITION_3_Step_3 + "- Sub 1 -" + System.lineSeparator() + Constants.ACTION_STOCKS_ADDED ;
                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT4_WATCHLIST_UPDATES_FROM_CNDT_3,
                        Comments, ST2_Cndt_4_Watchlist_Name,
                        ST2_Cndt_4_Watchlist_Url, Alerts_Stock_Names);
                // </editor-fold>

            }

        } catch (IOException e) {

            System.out.println("Step_3_Checking_ST2_CONDITION_3_Alerts: " + e.getMessage());
            ReportUtil.report(false, "FAIL", " Step_3_Checking_ST2_CONDITION_3_Alerts, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "Ending -- Step_3_Checking_ST2_CONDITION_3_Alerts", "");
    }

    public void Step_4_Checking_ST2_CONDITION_4_Alerts() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Starting -- Step_4_Checking_ST2_CONDITION_4_Alerts", "");

        // <editor-fold desc="Variables">
        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String Comments = "";

        String ST2_Cndt_5_Watchlist_Name = prop.getProperty("ST2_Cndt_5_Watchlist_Name");
        String ST2_Cndt_5_Watchlist_Url = prop.getProperty("ST2_Cndt_5_Watchlist_Url");

        // </editor-fold>

        try {

            // If new alert displayed for strategy : ST2_CONDITION_3 then add it to watchlist of strategy : ST2_Cndt4
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST2_CONDITION_4_Step_4,
                    Constants.TAB_ALERTPAGE_NAME_ST_2_FOURTH_CONDITION,false)) {

                Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                String[] stocks;

                // Update Stock Alert to textfile
                FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                //Add Stocks to watchlist
                if (Alerts_Stock_Names.contains(",")) {
                    stocks = Alerts_Stock_Names.split(",");
                } else {
                    stocks = new String[]{Alerts_Stock_Names};
                }

                // <editor-fold desc=" Step 2 - Sub 1 - Adding stocks from alert ST2_CONDITION_4 to ST2_Cndt5 watchlist ">
                ReportUtil.report(true, "INFO", "Step 4 - Sub 1 - Adding stocks from alert ST2_CONDITION_4 to ST2_Cndt5 watchlist ", "");

                // add Stocks from "ST2_CONDITION_3 to watchlist of ST2_Cndt4
                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        ST2_Cndt_5_Watchlist_Name, ST2_Cndt_5_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST2_CONDITION_4_Step_4 + "- Sub 1 -" + System.lineSeparator() + Constants.ACTION_STOCKS_ADDED ;
                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT5_WATCHLIST_STOCKS_ADDITION_UPDATES_FROM_CNDT_4,
                        Comments, ST2_Cndt_5_Watchlist_Name,
                        ST2_Cndt_5_Watchlist_Url, Alerts_Stock_Names);
                // </editor-fold>

            }

        } catch (IOException e) {

            System.out.println("Step_4_Checking_ST2_CONDITION_4_Alerts: " + e.getMessage());
            ReportUtil.report(false, "FAIL", " Step_4_Checking_ST2_CONDITION_4_Alerts, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "Ending -- Step_4_Checking_ST2_CONDITION_4_Alerts", "");
    }

    public void Step_5_Checking_ST2_CONDITION_5_Alerts() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Starting -- Step_5_Checking_ST2_CONDITION_5_Alerts", "");

        // <editor-fold desc="Variables">
        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String Comments = "";

        String ST2_Cndt_5_Watchlist_Name = prop.getProperty("ST2_Cndt_5_Watchlist_Name");
        String ST2_Cndt_5_Watchlist_Url = prop.getProperty("ST2_Cndt_5_Watchlist_Url");

        // </editor-fold>

        try {

            // If new alert displayed for strategy : ST2_CONDITION_5 then remove stocks from ST2_Cndt_5_Watchlist
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST2_CONDITION_5_Step_5,
                    Constants.TAB_ALERTPAGE_NAME_ST_2_FIFTH_CONDITION,false)) {

                Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                String[] stocks;

                // Update Stock Alert to textfile
                FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                //Add Stocks to watchlist
                if (Alerts_Stock_Names.contains(",")) {
                    stocks = Alerts_Stock_Names.split(",");
                } else {
                    stocks = new String[]{Alerts_Stock_Names};
                }

                // <editor-fold desc=" Step 2 - Sub 3 - Remove filtered stock from watchlist 'ST2_Cndt_5_Watchlist'  ">
                ReportUtil.report(true, "INFO", "Step 5 - Sub 1 - Remove filtered stock from watchlist 'ST2_Cndt_5_Watchlist' ", "");

                watchlistPage.delete_Stock_From_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        ST2_Cndt_5_Watchlist_Name, ST2_Cndt_5_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST2_CONDITION_5_Step_5 + "- Sub 1 -" + System.lineSeparator() + Constants.ACTION_STOCKS_REMOVED ;

                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT5_WATCHLIST_STOCKS_REMOVAL_UPDATES_FROM_CNDT_5,
                        Comments, ST2_Cndt_5_Watchlist_Name,
                        ST2_Cndt_5_Watchlist_Url, Alerts_Stock_Names);
                // </editor-fold>

            }

        } catch (IOException e) {

            System.out.println("Step_5_Checking_ST2_CONDITION_5_Alerts: " + e.getMessage());
            ReportUtil.report(false, "FAIL", " Step_5_Checking_ST2_CONDITION_5_Alerts, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "Ending -- Step_5_Checking_ST2_CONDITION_5_Alerts", "");
    }

}
