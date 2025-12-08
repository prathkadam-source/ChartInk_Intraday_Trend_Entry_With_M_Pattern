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

public class Test_Case_Stratergy_1 extends BaseTest {

    LoginPage loginPage = new LoginPage();
    NewTabsSetUp newTabsSetUp = new NewTabsSetUp();

    AlertPage alertPage = new AlertPage();

    WatchlistPage watchlistPage = new WatchlistPage();

    // Sell_Trades_From_Logic_iFVG
    public void Sell_Trades_From_Logic_iFVG() {

        ReportUtil.report( true, "INFO", "-- Test case 1 -- Starting -- Sell_Trades_From_Logic_iFVG",  "");

        try {

            Step_1_Checking_ST1_CONDITION_1_Alerts();

           /*ST_1_Cndt_2_Intraday_Sell_Side_IFVG_Statergy_Step_2_Failed_Of_Sell_FVG_Breakout
            1.Add Stocks to 'ST1_Cndt_3_Watchlist' for further scan
            2.Add filtered stock to current alert timestamp watchlist eg Alert is at 10: Am then add stock to ST1_Cndt_2_Time_9_55_AM_S9
            ( This is done to avoid false signal triggering in condition 3 due to same candle of Condition 2 )
            3.Remove filtered stock from watchlist 'ST1_Cndt_2_Header_Default_F_AND_O_Watchlist' to avoid repeated trigger
            */
            Step_2_Checking_ST1_CONDITION_2_Alerts();

             /*ST_1_Cndt_2_B_Intraday_Sell_Side_iFVG_Statergy_Step_3_Failed_Of_Sell_iFVG_Breakout,
            1. Remove the filtered stock from 'ST1_Cndt_3_Watchlist' as it has broken the logic
            2. Add the same stock in 'ST1_Cndt_2_Header_Default_F_AND_O_Watchlist' to continue the further flow
             */
            Step_3_Checking_ST1_CONDITION_2_Part_B_Alerts();

             }catch (InterruptedException e) {

            System.out.println("Test case 1 Sell_Trades_From_Logic_iFVG: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Test case 1 Sell_Trades_From_Logic_iFVG, ",  e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Test case 1 -- Ending -- Sell_Trades_From_Logic_iFVG",  "");

    }

    public void Step_1_Checking_ST1_CONDITION_1_Alerts() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Step 1 -- Starting -- Checking_ST1_CONDITION_1_Alerts", "");

        // <editor-fold desc="Variables">
            String Alerts_Stock_Names = "";
            String latest_Alert_TimeStamp = "";

            String Current_Watchlist_Details = "";
            String Current_Watchlist_Name = "";
            String Current_Watchlist_Url = "";

            String Comments = "";

//            String ST1_Cndt_2_Watchlist_Name = prop.getProperty("ST1_Cndt_2_Watchlist_Name");
//            String ST1_Cndt_2_Watchlist_Url = prop.getProperty("ST1_Cndt_2_Watchlist_Url");

        // </editor-fold>

        try {

            // If new alert displayed for strategy : ST1_CONDITION_1 then add it to watchlist of strategy : ST1_Cndt2
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST1_CONDITION_1_Step_1,
                    Constants.TAB_ALERTPAGE_NAME_ST_1_FIRST_CONDITION, false)) {

                Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                String[] stocks;

                // Update Stock Alert to textfile
                FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                // <editor-fold desc=" Step 1 - Sub 1">

                ReportUtil.report(true, "INFO", "Step 1 - Sub 1-- Adding stocks from ST1_CONDITION_1 alert to ST1_Cndt2 watchlist ", "");
                //Add Stocks to watchlist
                if (Alerts_Stock_Names.contains(",")) {
                    stocks = Alerts_Stock_Names.split(",");
                } else {
                    stocks = new String[]{Alerts_Stock_Names};
                }
//
                Current_Watchlist_Details = watchlistPage.get_Watchlist_Url(Constants.ST1_CNDT_1_TIME, latest_Alert_TimeStamp);
                Current_Watchlist_Name = Current_Watchlist_Details.split(",")[0];
                Current_Watchlist_Url = Current_Watchlist_Details.split(",")[1];

                // add Stocks from "ST1_CONDITION_1" to watchlist of ST1_Cndt2 with time stamp
                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        Current_Watchlist_Name, Current_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST1_CONDITION_1_Step_1 + System.lineSeparator() + Constants.ACTION_STOCKS_ADDED ;

                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT2_WATCHLIST_UPDATES_FROM_CNDT_1,
                                Comments, Current_Watchlist_Name,
                        Current_Watchlist_Url, Alerts_Stock_Names);

                // </editor-fold>

            }

        } catch (IOException e) {

            System.out.println("Step 1 Checking_ST1_CONDITION_1_Alerts: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "Step 1 Checking_ST1_CONDITION_1_Alerts, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Step 1 -- Ending -- Checking_ST1_CONDITION_1_Alerts", "");
    }

    public void Step_2_Checking_ST1_CONDITION_2_Alerts() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Step 2 -- Starting -- Checking_ST1_CONDITION_2_Alerts", "");

        // <editor-fold desc="Variables">
        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String Comments = "";

        String Current_Watchlist_Details = "";
        String Current_Watchlist_Name = "";
        String Current_Watchlist_Url = "";

        String ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Name = prop.getProperty("ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Name");
        String ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Url = prop.getProperty("ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Url");

        String ST1_Cndt_3_Watchlist_Name = prop.getProperty("ST1_Cndt_3_Watchlist_Name");
        String ST1_Cndt_3_Watchlist_Url = prop.getProperty("ST1_Cndt_3_Watchlist_Url");


        // </editor-fold>

        try {

            // If new alert displayed for strategy : ST1_CONDITION_2 then add it to watchlist of strategy : ST1_Cndt3
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST1_CONDITION_2_Step_2,
                    Constants.TAB_ALERTPAGE_NAME_ST_1_SECOND_CONDITION, false)) {

                Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                String[] stocks;

                // Update Stock Alert to textfile
                FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                // <editor-fold desc=" Step 1 - Sub 1">

                ReportUtil.report(true, "INFO", "Step 2 - Sub 1-- Adding stocks from ST1_CONDITION_2 alert to ST1_Cndt3 watchlist ", "");
                //Add Stocks to watchlist
                if (Alerts_Stock_Names.contains(",")) {
                    stocks = Alerts_Stock_Names.split(",");
                } else {
                    stocks = new String[]{Alerts_Stock_Names};
                }

                // <editor-fold desc=" Step 2 - Sub 1 - Adding stocks from alert ST1_CONDITION_2 to ST1_Cndt3 watchlist ">
                ReportUtil.report(true, "INFO", "Step 2 - Sub 1 - Adding stocks from alert ST1_CONDITION_2 to ST1_Cndt3 watchlist ", "");

                // add Stocks from "ST1_CONDITION_2 to watchlist of ST1_Cndt3
                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        ST1_Cndt_3_Watchlist_Name, ST1_Cndt_3_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST1_CONDITION_2_Step_2 + "- Sub 1 -" + System.lineSeparator() + Constants.ACTION_STOCKS_ADDED ;
                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT3_WATCHLIST_UPDATES,
                        Comments, ST1_Cndt_3_Watchlist_Name,
                        ST1_Cndt_3_Watchlist_Url, Alerts_Stock_Names);
                // </editor-fold>

                // <editor-fold desc=" Step 2 - Sub 2 - Adding stocks from alert ST1_CONDITION_2 to ST1_Cndt_2_Current_Time watchlist eg ST2_Cndt_2_Time_9_55_AM_S9 ">
                ReportUtil.report(true, "INFO", "Step 2 - Sub 2 - Adding stocks from alert ST1_CONDITION_2 to ST1_Cndt_2_Current_Time watchlist eg ST1_Cndt_2_Time_9_55_AM_S9 ", "");

                // Get Watchlist Name
                Current_Watchlist_Details = watchlistPage.get_Watchlist_Url(Constants.ST1_CNDT_2_TIME, latest_Alert_TimeStamp);
                Current_Watchlist_Name = Current_Watchlist_Details.split(",")[0];
                Current_Watchlist_Url = Current_Watchlist_Details.split(",")[1];

                // add Stocks from "ST1_CONDITION_2 to ST1_Cndt_2_Current_Time watchlist eg ST1_Cndt_2_Time_9_55_AM_S9
                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        Current_Watchlist_Name, Current_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST1_CONDITION_2_Step_2 + "- Sub 2 -" + System.lineSeparator() + Constants.ACTION_STOCKS_ADDED ;
                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT2_PARTICULAR_TIME_FRAME_WATCHLIST_UPDATES,
                        Comments, Current_Watchlist_Name,
                        Current_Watchlist_Url, Alerts_Stock_Names);

                // </editor-fold>

                // <editor-fold desc=" Step 2 - Sub 3 - Remove filtered stock from watchlist 'ST1_Cndt_2_Header_Default_F_AND_O_Watchlist' to avoid repeated trigger  ">
                ReportUtil.report(true, "INFO", "Step 2 - Sub 3 - Remove filtered stock from watchlist 'ST1_Cndt_2_Header_Default_F_AND_O_Watchlist' to avoid repeated trigger ", "");

                watchlistPage.delete_Stock_From_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Name, ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST1_CONDITION_2_Step_2 + "- Sub 3 -" + System.lineSeparator() + Constants.ACTION_STOCKS_REMOVED ;

                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT2_HEADER_DEFAULT_F_AND_O_WATCHLIST_FOR_REMOVED_STOCKS_UPDATES,
                        Comments, ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Name,
                        ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Url, Alerts_Stock_Names);
                // </editor-fold>

            }

        } catch (IOException e) {

            System.out.println("Step 2 Checking_ST1_CONDITION_2_Alerts: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "Step 2 Checking_ST1_CONDITION_2_Alerts, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Step 2 -- Ending -- Checking_ST1_CONDITION_2_Alerts", "");
    }

    public void Step_3_Checking_ST1_CONDITION_2_Part_B_Alerts() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Step 3 -- Starting -- Checking_ST1_CONDITION_2_Part_B_Alerts", "");

        // <editor-fold desc="Variables">
        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String Comments = "";

        String ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Name = prop.getProperty("ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Name");
        String ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Url = prop.getProperty("ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Url");

        String ST1_Cndt_3_Watchlist_Name = prop.getProperty("ST1_Cndt_3_Watchlist_Name");
        String ST1_Cndt_3_Watchlist_Url = prop.getProperty("ST1_Cndt_3_Watchlist_Url");

        // </editor-fold>

        try {

            // If new alert displayed for strategy : ST2_CONDITION_2_B
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST1_CONDITION_2_B_Step_3,
                    Constants.TAB_ALERTPAGE_NAME_ST_1_SECOND_CONDITION_PART_B,false)) {

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

                // <editor-fold desc=" Step 3 - Sub 1 -  Remove the filtered stock from 'ST1_Cndt_3_Watchlist' as it has broked the logic ">
                ReportUtil.report(true, "INFO", "Step 2 - Sub 1 -  Remove the filtered stock from 'ST1_Cndt_3_Watchlist' as it has broked the logic", "");

                watchlistPage.delete_Stock_From_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        ST1_Cndt_3_Watchlist_Name, ST1_Cndt_3_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST1_CONDITION_2_B_Step_3 + "- Sub 1 -" + System.lineSeparator() + Constants.ACTION_STOCKS_REMOVED ;

                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT2_B_STOCK_ALERTS_UPDATES,
                        Comments, ST1_Cndt_3_Watchlist_Name,
                        ST1_Cndt_3_Watchlist_Url, Alerts_Stock_Names);
                // </editor-fold>

                // <editor-fold desc=" Step 3 - Sub 2 - Add the same stock in 'ST1_Cndt_2_Header_Default_F_AND_O_Watchlist' to continue the further flow">
                ReportUtil.report(true, "INFO", "Step 2 - Sub 2 - Add the same stock in 'ST1_Cndt_2_Header_Default_F_AND_O_Watchlist' to continue the further flow", "");

                // add Stocks from "ST1_CONDITION_2 B to watchlist of ST1_Cndt_2_Header_Default_F_AND_O_Watchlist
                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Name, ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                Comments = Constants.ST1_CONDITION_2_B_Step_3 + "- Sub 2 -" + System.lineSeparator() + Constants.ACTION_STOCKS_ADDED ;
                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT2_B_STOCK_ALERTS_UPDATES,
                        Comments, ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Name,
                        ST1_Cndt_2_Header_Default_F_AND_O_Watchlist_Url, Alerts_Stock_Names);
                // </editor-fold>

            }

        } catch (IOException e) {

            System.out.println("Step_3_Checking_ST1_CONDITION_2_Part_B_Alerts: " + e.getMessage());
            ReportUtil.report(false, "FAIL", " Step_3_Checking_ST1_CONDITION_2_Part_B_Alerts, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Step 3 -- Ending -- Checking_ST1_CONDITION_2_Part_B_Alerts", "");
    }

}
