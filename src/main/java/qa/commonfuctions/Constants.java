package qa.commonfuctions;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import qa.commonfuctions.DateTimeFunctions;

import static qa.base.BaseTest.prop;


public class Constants {

    // Public static final = Global constant
    public static final String WEBPAGE_LOGIN = "lOGIN";
    public static final String WEBPAGE_WATCHLIST = "WATCHLIST";
    public static final String WEBPAGE_ALERT = "ALERT";

    // <editor-fold desc="SubTabDetails New Statergy">
    public static final String TAB_ALERTPAGE_NAME_ST_1_FIRST_CONDITION = "IntraDay_Sell_ST1_Cndt_1_ADX_Buy_Breakout_With_Sell_Position";
    public static final String TAB_ALERTPAGE_NAME_ST_1_SECOND_CONDITION = "IntraDay_Sell_ST1_Cndt_2_ADX_Sell_Side_Crossover";
    public static final String TAB_ALERTPAGE_NAME_ST_1_THIRD_CONDITION = "ST1_Cndt_3_ADX_Sell_Breakout";

    public static final String TAB_ALERTPAGE_NAME_ST_2_FIRST_CONDITION = "IntraDay_ST2_Cndt_1_ADX_Sell_Breakout_With_-Ve_Supertrend_For_Buy_Position";
    public static final String TAB_ALERTPAGE_NAME_ST_2_SECOND_CONDITION = "IntraDay_ST2_Cndt_2_ADX_Buy_Side_Crossover";

    public static final String TAB_ALERTPAGE_NAME_ST_2_THIRD_CONDITION = "ST2_Cndt_3_ADX_Buy_Breakout";


    // </editor-fold>

    // <editor-fold desc="SubTabDetails">
   // public static final String TAB_ALERTPAGE_NAME_ST_1_FIRST_CONDITION = "Scan_For_*BUY*_Trades_+Ve_Supertrend";
    public static final String TAB_WATCHLISTPAGE_NAME_ST_1_Cndt_2_Watchlist = "1_Buy_Side_Stocks_For_Intraday_From_Positive_Supertrend_Of_Buy_Trades";
//    public static final String TAB_ALERTPAGE_NAME_ST_2_FIRST_CONDITION = "Scan_Sell_Trades_+Ve_Spertrnd_upd for SMA crossover MACD zero line ALL FUTURES";
    public static final String TAB_WATCHLISTPAGE_NAME_ST_2_Cndt_2_Watchlist = "1_Sell_Side_Stocks_For_Intraday_From_Positive_Supertrend_Of_Sell_Trades";
    public static final String TAB_ALERTPAGE_NAME_ST_3_FIRST_CONDITION = "ST2_Cndt3_Scan_For_Sell_Trades_Histo_Top_out_Stocks_5_Mins_-ve_Supertrend";
    public static final String TAB_WATCHLISTPAGE_NAME_ST_3_Cndt_2_Watchlist = "1_Sell_Side_Stocks_For_Intraday_From_Negative_Supertrend_Of_Sell_Trades";
    public static final String TAB_ALERTPAGE_NAME_ST_4_FIRST_CONDITION = "Super_ST2_Cndt3_Scan_For_*BUY*_Trades_-Ve_Supertrend_updated";
    public static final String TAB_WATCHLISTPAGE_NAME_ST_4_Cndt_2_Watchlist = "1_Sell_Side_Stocks_For_Intraday_From_Negative_Supertrend_Of_Buy_Trades";


//    public static final String TAB_ALERTPAGE_NAME_ST_2_SECOND_CONDITION ="ST2_Cndt2_Scan_For_Sell_Trades_Histo_Top_out_Stocks_5_Mins";

    public static final String TAB_DEFAULT_WATCHLIST_PAGE = "Default_Watchlist";
    public static final String TAB_ALERTPAGE_NAME_ST_1_SECOND_CONDITION_NEXTDAY_CARRY_FORWARD ="ST_1_Second_Condition_For_NextDay_Carry_Forward_Scenario";
    // </editor-fold>

    // <editor-fold desc="Folder and Text file Stamp constant  ">
    public static final String TODAYSDATE_YYYY_MM_DD = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    public static final String TODAYSDATE_YYYY_MM_DD_HH_MM_SS = ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd_hh-mm-ss-a_z"));
    public static final String OUTPUT_FOLDER_PATH = System.getProperty("user.dir") + "/src/main/output/";
    public static String FOLDER_MAIN_OUTPUT = "";
    public static String FOLDER_SUB_OUTPUT = "";

    //Text File related details
    public static final String DATA_FOLDER_PATH = System.getProperty("user.dir") + "/src/main/resources/data/";
    public static String TEXTFILE_PATH_ST1_CNDT2_WATCHLIST_UPDATES_FROM_CNDT_1 = "ST1_Cndt2_Watchlist_Updates_From_Cndt_1.txt";
    public static String TEXTFILE_PATH_ST1_CNDT3_WATCHLIST_UPDATES = "ST1_Cndt3_Watchlist_Updates.txt";
    public static String TEXTFILE_PATH_ST2_CNDT2_WATCHLIST_UPDATES_FROM_CNDT_1 = "ST2_Cndt2_Watchlist_Updates_From_Cndt_1.txt";
    public static String TEXTFILE_PATH_ST2_CNDT3_WATCHLIST_UPDATES = "ST2_Cndt3_Watchlist_Updates.txt";
    public static String TEXTFILE_PATH_ST3_LAST_CNDT_WATCHLIST_UPDATES = "ST3_Last_Cndt_Watchlist_Updates_From_ST2_CNDT_3.txt";
    public static String TEXTFILE_PATH_ST4_LAST_CNDT_WATCHLIST_UPDATES = "ST4_Last_Cndt_Watchlist_Updates_From_ST1_CNDT_3.txt";

    public static String TEXTFILE_PATH_ST2_CNDT2_WATCHLIST_UPDATES = "ST2_Cndt2_Watchlist_Updates.txt";
    public static String TEXTFILE_PATH_ST3_CNDT2_WATCHLIST_UPDATES = "ST3_Cndt2_Watchlist_Updates.txt";
    public static String TEXTFILE_PATH_ST4_CNDT2_WATCHLIST_UPDATES = "ST4_Cndt2_Watchlist_Updates.txt";
    public static String TEXTFILE_PATH_FOR_COPY_PASTED_ALERTS_OUTPUT = System.getProperty("user.dir") + "/src/main/resources/data/copy_Pasted_Alerts_Output.txt";

    public static String TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST = System.getProperty("user.dir") + "/src/main/resources/data/runTime_Stocks_for_watchlist.txt";
   public static String TEXTFILE_PATH_FOR_ST1_WATCHLISTS_NAME_AND_URL = System.getProperty("user.dir") + "/src/main/resources/data/ST1_Watchlist_Names.properties";




    public static String TEXTFILE_NAME_ST2_CNDT2_WATCHLIST_UPDATES = "ST2_Cndt2_Watchlist_Updates.txt";
    public static String TEXTFILE_NAME_ST2_CNDT3_WATCHLIST_UPDATES = "ST2_Cndt3_Watchlist_Updates.txt";




    public static String TEXTFILE_NAME_ST2_CNDT3_WATCHLIST_STOCKS_REMOVED_TO_AVOID_DUPLICATE_ALERTS = "ST2_Cndt3_Watchlist_Stocks_Removed_TO_AVOID_DUPLICATE_ALERTS.txt";
    public static String TEXTFILE_NAME_ST2_CNDT3_WATCHLIST_STOCKS_REMOVED_FOR_INVALID_CONDITION = "ST2_Cndt3_Watchlist_Stocks_Removed_FOR_INVALID_CONDITION.txt";
    public static String TEXTFILE_NAME_ST1_CNDT2_ALERTS = "ST1_Cndt2_Alerts.txt";
    public static String TEXTFILE_NAME_ST1_CNDT3_ALERTS = "ST1_Cndt3_Alerts.txt";
    public static String TEXTFILE_NAME_COPY_PASTED_ALERTS_OUTPUT_OF_ST2_CNDT1 = "copy_Pasted_Alerts_Output.txt";
    public static String TEXTFILE_NAME_COPY_PASTED_ALERTS_OUTPUT_OF_ST2_CNDT2 = "copy_Pasted_Alerts_Output_Of_ST2_Cndt2.txt";
    public static String TEXTFILE_NAME_FOR_NEXYDAY_CARRY_FORWARD_STOCKS_OF_ST2_CNDT2 = "runTime_Stocks_for_ST2_Cndt2_NextDay_Carry_Forward.txt";
        public static String FOLDER_LOCATION_WATCHLISTS_TEXTFILE = "";
    public static String FOLDER_LOCATION_ALERT_TEXTFILE = "";

    //Text file path


//    public static String TEXTFILE_PATH_ST2_CNDT3_WATCHLIST_UPDATES = "";
    public static String TEXTFILE_PATH_ST2_CNDT3_WATCHLIST_STOCKS_REMOVED_FOR_INVALID_CONDITION = "";
    public static String TEXTFILE_PATH_ST2_CNDT3_WATCHLIST_STOCKS_REMOVED_TO_AVOID_DUPLICATE_ALERTS = "";
    public static String TEXTFILE_PATH_ST1_CNDT2_ALERTS = "";
    public static String TEXTFILE_PATH_ST1_CNDT3_ALERTS = "";
    public static String TEXTFILE_PATH_ST2_CNDT2_ALERTS_FOR_NEXYDAY_CARRY_FORWARD_SCENARIO = "";
    public static String TEXTFILE_PATH_FOR_COPY_PASTED_ALERTS_OUTPUT_OF_ST2_CNDT1 = "";
    public static String TEXTFILE_PATH_FOR_COPY_PASTED_ALERTS_OUTPUT_OF_ST2_CNDT2 = "";


    // </editor-fold>

    // <editor-fold desc="ST1_CNDT1 variables ">
    // Setting up date for 1st default date time comparison
    public static String ST1_CONDITION_1_PART_A = "ST1_Cndt_1_Part_A";
    public static String ST1_CONDITION_1_PART_B = "ST1_Cndt_1_Part_B";
    public static String ST1_CONDITION_2 = "ST1_Cndt_2";

    public static String ST1_CNDT_1_TIME = "ST1_Cndt_1_Time_";
    public static String ST2_CNDT_2_TIME = "ST2_Cndt_2_Time_";

    public static String ST1_CONDITION_1_Step_1 = "ST1_Cndt_1_Step_1";
    public static String ST1_CONDITION_2_Step_2 = "ST1_Cndt_2_Step_2";
    public static String ST1_CONDITION_3_Step_3 = "ST1_Cndt_3_Step_3";

    public static String ST2_CONDITION_1_Step_1 = "ST2_Cndt_1_Step_1";
    public static String ST2_CONDITION_2_Step_2 = "ST2_Cndt_2_Step_2";
    public static String ST2_CONDITION_3_Step_3 = "ST2_Cndt_3_Step_3";

    public static String ST3_CONDITION_LAST = "ST3_Cndt_LAST";
    public static String ST4_CONDITION_LAST = "ST3_Cndt_LAST";
    public static String ST3_CONDITION_1 = "ST3_Cndt_1";
    public static String ST4_CONDITION_1 = "ST4_Cndt_1";

    public static String ST1_Cndt_1_DEFAULT_ALERT_TIMESTAMP = DateTimeFunctions.get_Now_Date_And_Day_In_Chartink_Time_Format() + ", 09:10 AM";  // Example format: "Tue Jul 8 2025, 9:10 am";
    public static String ST1_Cndt_2_DEFAULT_ALERT_TIMESTAMP = DateTimeFunctions.get_Now_Date_And_Day_In_Chartink_Time_Format() + ", 09:10 AM";  // Example format: "Tue Jul 8 2025, 9:10 am";

    public static String ST1_Cndt_3_DEFAULT_ALERT_TIMESTAMP = DateTimeFunctions.get_Now_Date_And_Day_In_Chartink_Time_Format() + ", 09:10 AM";  // Example format: "Tue Jul 8 2025, 9:10 am";

    public static String ST2_Cndt_1_DEFAULT_ALERT_TIMESTAMP = DateTimeFunctions.get_Now_Date_And_Day_In_Chartink_Time_Format() + ", 09:10 AM";  // Example format: "Tue Jul 8 2025, 9:10 am";
    public static String ST2_Cndt_2_DEFAULT_ALERT_TIMESTAMP = DateTimeFunctions.get_Now_Date_And_Day_In_Chartink_Time_Format() + ", 09:10 AM";  // Example format: "Tue Jul 8 2025, 9:10 am";

    public static String ST2_Cndt_3_DEFAULT_ALERT_TIMESTAMP = DateTimeFunctions.get_Now_Date_And_Day_In_Chartink_Time_Format() + ", 09:10 AM";  // Example format: "Tue Jul 8 2025, 9:10 am";
    public static String ST3_Cndt_Last_DEFAULT_ALERT_TIMESTAMP = DateTimeFunctions.get_Now_Date_And_Day_In_Chartink_Time_Format() + ", 09:10 AM";  // Example format: "Tue Jul 8 2025, 9:10 am";
    public static String ST4_Cndt_Last_DEFAULT_ALERT_TIMESTAMP = DateTimeFunctions.get_Now_Date_And_Day_In_Chartink_Time_Format() + ", 09:10 AM";  // Example format: "Tue Jul 8 2025, 9:10 am";

    public static String LATEST_ALERT_TIMESTAMP = "";
    public static String LATEST_ALERT_STOCK_NAMES = "";

    // FOR ST1_Cndt2 watchlist Name and url of current excution

    public static String ACTION_STOCKS_ADDED = "Stocks added into watchlist";

    public static String ACTION_STOCKS_REMOVED_FOR_INVALID_CONDITION = "Stocks removed from watchlist for invalid condition";

    public static String ACTION_STOCKS_REMOVED_TO_AVOID_DUPLICATE_ALERTS = "Stocks removed from watchlist to avoid duplicate alerts in final scan";

    // </editor-fold>

    // <editor-fold desc="ST1_CNDT2 variables ">
    // Setting up date for 1st default date time comparison
    public static String ST1_CNDT2_DEFAULT_ALERT_TIMESTAMP = DateTimeFunctions.get_Now_Date_And_Day_In_Chartink_Time_Format() + ", 09:10 AM";  // Example format: "Tue Jul 8 2025, 9:10 am";
    public static String ST1_CNDT2_LATEST_ALERT_TIMESTAMP = "";
    public static String ST1_CNDT2_LATEST_ALERT_STOCK_NAMES = "";

    // FOR ST1_Cndt3 watchlist Name and url of current excution
    public static String ST1_CNDT3_CURRENT_RUN_WATCHLIST_NAME = "";
    public static String ST1_CNDT3_CURRENT_RUN_WATCHLIST_URL =  "";

    // </editor-fold>


    // <editor-fold desc="AUtoIt exe file location ">

    public static String AUTOIT_EXE_LOCATION_FOR_ST1_CNDT2_WATCHLIST_STOCKS = "C:/Users/prath/IdeaProjects/ChartInk/AutoIt_File_Uploader_Exe/ST1_Cndt2_Wtachlist_Stocks_FileUpload.EXE";

    // </editor-fold>

    // <editor-fold desc="Stock Watchlist file location ">

    public static String TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_ST2_CNDT2_WATCHLIST = System.getProperty("user.dir") + "/src/main/resources/data/runTime_Stocks_for_watchlist.txt";

    public static String TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_ST2_CNDT3_WATCHLIST = System.getProperty("user.dir") + "/src/main/resources/data/runTime_Stocks_for_ST2_Cndt3.txt";

    // </editor-fold>

    public static String TEXTFILE_PATH_FOR_ST2_CNDT2_WATCHLISTS_NAME_AND_URL = System.getProperty("user.dir") + "/src/main/resources/data/ST1_Watchlist_Names.properties";

    public static String TEXTFILE_PATH_FOR_F_AND_O_STOCKS_DETAILS = System.getProperty("user.dir") + "/src/main/resources/data/FAndO_Stocks_Detail.properties";

    // Private constructor to prevent instantiation
    private Constants() {}
}
