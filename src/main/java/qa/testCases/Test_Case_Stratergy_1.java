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

    public void Buy_Trades() throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Test case 1 -- Starting -- Buy_Trades",  "");

        // <editor-fold desc="Variables">

        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String ST1_CNDT2_Watchlist_Name = prop.getProperty("ST1_Cndt2_Watchlist_Page_Name").trim();
        String ST1_CNDT2_Watchlist_Url = prop.getProperty("ST1_Cndt2_Watchlist_Page_Url").trim();

        // </editor-fold>

        try {

                // Step 1: Navigate to  ST1_CNDT1_Alerts tab and get an alerts and update them to ST1_CNDT2 watchlist
                ReportUtil.report(true, "INFO", "-- STEP 1 -- Navigate to ST1_CNDT1_Alerts page and get an alerts and update them to ST1_CNDT2 watchlist", "");

                // If new alert displayed for strategy : ST1_Cndt1 then add it to watchlist of strategy : ST1_Cndt2
                if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST1_CONDITION_1,
                        Constants.TAB_ALERTPAGE_NAME_ST_1_FIRST_CONDITION)) {

                    Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                    latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                    String[] stocks;

                    // Condition 2
                    // Update Stock Alert to textfile
                    FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                    //Add Stocks to watchlist
                   Alerts_Stock_Names = "ZYDUSLIFE";

                   // Create an array to pass to delete_Stock_From_Watchlist function
                    if (Alerts_Stock_Names.contains(",")) {
                        stocks = Alerts_Stock_Names.split(",");
                    }else {
                        stocks = new String[]{Alerts_Stock_Names};
                    }
//
                    // add Stocks from "ST1_Cndt_1_" to watchlist of ST1_Cndt2
                    watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_WATCHLISTPAGE_NAME_ST_1_Cndt_2_Watchlist,
                            ST1_CNDT2_Watchlist_Name,ST1_CNDT2_Watchlist_Url,stocks);

//                    //  Get and update Watchlist_Url_For_ST1_CNDT2_Statergy
//                    watchlistPage.navigate_to_Particular_Watchlist(Constants.TAB_WATCHLISTPAGE_NAME_ST_1_Cndt_2_Watchlist,
//                            ST1_CNDT2_Watchlist_Url,ST1_CNDT2_Watchlist_Name);
//
//                    //Upload Stocks text file
//                    watchlistPage.upload_Stock_List_TextFile_Using_Robot(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST);

                    // Update Stock Alerts to  output textfile for end of the day validation
                    FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST1_CNDT2_WATCHLIST_UPDATES,
                            Constants.ACTION_STOCKS_ADDED, ST1_CNDT2_Watchlist_Name,
                            ST1_CNDT2_Watchlist_Url, Alerts_Stock_Names);

                }

             }catch (IOException e) {

            System.out.println("Test case 1_Buy_Trades: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Test case 1_Buy_Trades, ",  e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Test case 1 -- Ending -- Buy_Trades\"",  "");

    }

}
