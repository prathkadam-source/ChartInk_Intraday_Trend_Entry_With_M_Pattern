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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static qa.utils.ReportUtil.finalizeReport;

public class Sub_Test_Case extends BaseTest {

    LoginPage loginPage = new LoginPage();
    NewTabsSetUp newTabsSetUp = new NewTabsSetUp();

    AlertPage alertPage = new AlertPage();

    WatchlistPage watchlistPage = new WatchlistPage();

    Test_Case_Stratergy_1 test_Case_Stratergy_1 = new Test_Case_Stratergy_1();
    Test_Case_Stratergy_2 test_Case_Stratergy_2 = new Test_Case_Stratergy_2();
    Test_Case_Stratergy_3 test_Case_Stratergy_3 = new Test_Case_Stratergy_3();
    Test_Case_Stratergy_4 test_Case_Stratergy_4 = new Test_Case_Stratergy_4();


    public void Prerequisite_To_Login_And_Set_Sub_Tabs_Urls() throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- Prerequisite_To_Login_And_Set_Sub_Tabs_Urls",  "");

        try {
            loginPage.loginToApplication();
            newTabsSetUp.setUpTabsForParallelExecution();

            // <editor-fold desc="Folder and Text file creation ">
            //To Create a output folder
            Constants.FOLDER_MAIN_OUTPUT = FileAndFolderFunctions.Create_A_Folder(Constants.OUTPUT_FOLDER_PATH, Constants.TODAYSDATE_YYYY_MM_DD);
            Constants.FOLDER_SUB_OUTPUT = FileAndFolderFunctions.Create_A_Folder(Constants.FOLDER_MAIN_OUTPUT + "\\" , Constants.TODAYSDATE_YYYY_MM_DD_HH_MM_SS);

            //To Create a textfile to log details in output folder

            // Textfile to Log updates when ST1_Cndt2-watchlists are updated for alerts received for ST1_Cndt1
            // this file is use to record the out details
            Constants.TEXTFILE_PATH_ST1_CNDT2_WATCHLIST_UPDATES_FROM_CNDT_1 = FileAndFolderFunctions.Create_A_TextFile(
                    Constants.FOLDER_SUB_OUTPUT+ "\\", Constants.TEXTFILE_PATH_ST1_CNDT2_WATCHLIST_UPDATES_FROM_CNDT_1);

            Constants.TEXTFILE_PATH_ST1_CNDT3_WATCHLIST_UPDATES = FileAndFolderFunctions.Create_A_TextFile(
                    Constants.FOLDER_SUB_OUTPUT+ "\\", Constants.TEXTFILE_PATH_ST1_CNDT3_WATCHLIST_UPDATES);

            // Textfile to Log updates when ST2_Cndt2-watchlists are updated for alerts received for ST2_Cndt1
            // this file is use to record the out details
            Constants.TEXTFILE_PATH_ST2_CNDT2_WATCHLIST_UPDATES_FROM_CNDT_1 = FileAndFolderFunctions.Create_A_TextFile(
                    Constants.FOLDER_SUB_OUTPUT+ "\\", Constants.TEXTFILE_PATH_ST2_CNDT2_WATCHLIST_UPDATES_FROM_CNDT_1);

            Constants.TEXTFILE_PATH_ST2_CNDT3_WATCHLIST_UPDATES = FileAndFolderFunctions.Create_A_TextFile(
                    Constants.FOLDER_SUB_OUTPUT+ "\\", Constants.TEXTFILE_PATH_ST2_CNDT3_WATCHLIST_UPDATES);

            //// Textfile to Log updates when ST3_Last_Cndt-watchlists are updated for alerts received for ST2_Cndt3
            Constants.TEXTFILE_PATH_ST3_LAST_CNDT_WATCHLIST_UPDATES = FileAndFolderFunctions.Create_A_TextFile(
                    Constants.FOLDER_SUB_OUTPUT+ "\\", Constants.TEXTFILE_PATH_ST3_LAST_CNDT_WATCHLIST_UPDATES);

            //// Textfile to Log updates when ST4_Last_Cndt-watchlists are updated for alerts received for ST1_Cndt3
            Constants.TEXTFILE_PATH_ST4_LAST_CNDT_WATCHLIST_UPDATES = FileAndFolderFunctions.Create_A_TextFile(
                    Constants.FOLDER_SUB_OUTPUT+ "\\", Constants.TEXTFILE_PATH_ST4_LAST_CNDT_WATCHLIST_UPDATES);

            //----------------------------------------

            // </editor-fold>
            // To empty all watchlist for Strategies
            if ("true".equalsIgnoreCase(prop.getProperty("empty_All_Watchlists_For_Strategies").trim())) {
                watchlistPage.empty_All_Watchlists_For_Strategies(Constants.TEXTFILE_PATH_FOR_ST1_WATCHLISTS_NAME_AND_URL);
            }

            // To collect All failed steps and report them at end of the test case ,
            // it is created to avoid hlt in between test case execution
            finalizeReport();

        }catch (Exception e) {

            System.out.println("Prerequisite_To_Login_And_Set_Sub_Tabs_Urls: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Prerequisite_To_Login_And_Set_Sub_Tabs_Urls, ",  e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Function -- Ending -- Prerequisite_To_Login_And_Set_Sub_Tabs_Urls",  "");

    }
    public void Execute_Strategies() throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- Execute_Strategy_2_All_Conditions",  "");

        // <editor-fold desc="Variables">

        int loop_Count = 75;
        // </editor-fold>

        // <editor-fold desc="Time related setting">
        String Interval = "";
        LocalTime cutoffTime = LocalTime.of(15, 35); // 3:35 PM
        LocalTime endTime = LocalTime.of(10, 30); // 10.35 AM ;  this variable is used for next day carry forward loop to end
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // </editor-fold>

        try {

            // Start loop at specific time i.e 9.15.05 AM
            if ("true".equalsIgnoreCase(prop.getProperty("Start_Loop_At_Specific_Time").trim())) {
                LocalTime start_Time = LocalTime.of(9, 15, 25);
                DateTimeFunctions.start_For_Loop_At_Specific_Time(start_Time);
            }

            // Executing loop for 75 times or till 3.30 PM in interval of 5 mins
            for (int i = 1; i <= loop_Count; i++) {

                Interval = "Executing iteration " + i + " at " + LocalDateTime.now().format(formatter);
                System.out.println(Interval);
                ReportUtil.report(true, "INFO", Interval, "");

                  test_Case_Stratergy_1.Sell_Trades_From_Logic_ADX_Buy_Breakout_With_Sell_Position();
                  test_Case_Stratergy_2.Buy_Trades_From_Logic_ADX_Sell_Breakout_With_Negative_Supertrend_For_Buy_Position();
//                test_Case_Stratergy_3.Sell_Trades_From_Negative_Supertrend_Of_Sell_Trades();
//                test_Case_Stratergy_4.Sell_Trades_From_Negative_Supertrend_Of_Buy_Trades();

                // To ensure for loop starts at the next multiple of 5 minutes 25 seconds,
                // such as 9:25:05, even if the program is started at 9:23:00
                if (i < loop_Count && ("true".equalsIgnoreCase(prop.getProperty("Wait_For_Precise_5min_25Sec_Time_Interval").trim()))) {
                    DateTimeFunctions.loop_At_Precise_Time_Intervals();
                }

                // Break loop if current time is past 3:35 PM
                if ("true".equalsIgnoreCase(prop.getProperty("Break_For_Loop_After_3_35_PM").trim())){
                    LocalTime now = LocalTime.now();

                    if (now.isAfter(cutoffTime)) {
                        System.out.println("Breaking loop as time exceeded 3:35 PM.");
                        break;
                    }
                }

                // it is created to avoid halt in between test case execution, updating all details at final reports
                finalizeReport();

            }

        }catch (Exception e) {

            System.out.println("Execute_Strategy_2_All_Conditions: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Execute_Strategy_2_All_Conditions, ",  e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Function -- Ending -- Execute_Strategy_2_All_Conditions",  "");

    }

}
