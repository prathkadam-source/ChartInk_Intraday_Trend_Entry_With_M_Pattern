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

    // Buy_Trades_From_Logic_Two_Green_Histo_Candles with decreasing SMA of Histo ,
    public void Buy_Trades_From_Logic_Two_Green_Histo_Candles() {

        ReportUtil.report( true, "INFO", "-- Test case 2 -- Starting -- Buy_Trades_From_Logic_Two_Green_Histo_Candles",  "");

        try {

            Step_1_Checking_ST2_CONDITION_1_PART_B_Alerts();

            Step_2_Checking_ST2_CONDITION_1_PART_A_Alerts();

            Step_3_Checking_ST2_CONDITION_2_Alerts();

        }catch (InterruptedException e) {

            System.out.println("Test case 2_Buy_Trades_From_Logic_Two_Green_Histo_Candles: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Test case 2 Buy_Trades_From_Logic_Two_Green_Histo_Candles, ",  e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Test case 2 -- Ending -- Buy_Trades_From_Logic_Two_Green_Histo_Candles",  "");

    }

    public void Step_1_Checking_ST2_CONDITION_1_PART_B_Alerts() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Step 1 -- Starting -- Checking_ST2_CONDITION_1_PART_B_Alerts", "");

        // <editor-fold desc="Variables">
        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String Current_Watchlist_Details = "";
        String Current_Watchlist_Name = "";
        String Current_Watchlist_Url = "";

        String ST2_Cndt3_Watchlist_Name = prop.getProperty("ST2_Cndt_3_Watchlist_Name");
        String ST2_Cndt_3_Watchlist_Url = prop.getProperty("ST2_Cndt_3_Watchlist_Url");
        // </editor-fold>

        try {

            // If new alert displayed for strategy : ST2_CONDITION_1_PART_B then add it to watchlist of strategy : ST2_Cndt2 and ST2_Cndt3
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST1_CONDITION_2,
                    Constants.TAB_ALERTPAGE_NAME_ST_2_FIRST_CONDITION_PART_B)) {

                Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                String[] stocks;

                // Update Stock Alert to textfile
                FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                // <editor-fold desc=" Step 1 - Sub 1">

                ReportUtil.report(true, "INFO", "Step 1 - Sub 1-- Adding stocks from alert ST2_CONDITION_1_PART_B to ST2_Cndt2 watchlist ", "");
                //Add Stocks to watchlist
                if (Alerts_Stock_Names.contains(",")) {
                    stocks = Alerts_Stock_Names.split(",");
                } else {
                    stocks = new String[]{Alerts_Stock_Names};
                }
//
                Current_Watchlist_Details = watchlistPage.get_Watchlist_Url(Constants.ST2_CNDT_2_TIME, latest_Alert_TimeStamp);
                Current_Watchlist_Name = Current_Watchlist_Details.split(",")[0];
                Current_Watchlist_Url = Current_Watchlist_Details.split(",")[1];

                // add Stocks from "ST2_CONDITION_1_PART_B" to watchlist of ST2_Cndt2
                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        Current_Watchlist_Name, Current_Watchlist_Url, stocks);

                // Update Stock Alerts to  output textfile for end of the day validation
                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST2_CNDT2_WATCHLIST_UPDATES_FROM_CNDT_1_PART_A,
                        Constants.ACTION_STOCKS_ADDED, Current_Watchlist_Name,
                        Current_Watchlist_Url, Alerts_Stock_Names);
                // </editor-fold>


                // <editor-fold desc=" Step 1 - Sub 2">
                ReportUtil.report(true, "INFO", "Step 1 - sub 2-- Adding stocks from alert ST2_CONDITION_1_PART_B to ST2_Cndt3 watchlist ", "");

                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        ST2_Cndt3_Watchlist_Name, ST2_Cndt_3_Watchlist_Url, stocks);

                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST2_CNDT3_WATCHLIST_UPDATES,
                        Constants.ACTION_STOCKS_ADDED, ST2_Cndt3_Watchlist_Name,
                        ST2_Cndt_3_Watchlist_Url, Alerts_Stock_Names);

                // </editor-fold>
            }

        } catch (IOException e) {

            System.out.println("Step 1 Checking_ST2_CONDITION_1_PART_B_Alerts: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "Step 1 Checking_ST2_CONDITION_1_PART_B_Alerts, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Step 1 -- Ending -- Checking_ST2_CONDITION_1_PART_B_Alerts", "");
    }

    public void Step_2_Checking_ST2_CONDITION_1_PART_A_Alerts() throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- Step 2 -- Starting -- Checking_ST2_CONDITION_1_PART_A_Alerts",  "");

        // <editor-fold desc="Variables">
        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String Current_Watchlist_Details = "";
        String Current_Watchlist_Name = "";
        String Current_Watchlist_Url = "";

        // </editor-fold>

        try {

            // <editor-fold desc="STEP 2 - Checking_ST2_CONDITION_1_PART_A_Alerts">

            // If new alert displayed for strategy : ST2_CONDITION_1_PART_A then add it to watchlist of strategy : ST2_Cndt2
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST2_CONDITION_1,
                    Constants.TAB_ALERTPAGE_NAME_ST_2_FIRST_CONDITION_PART_A)) {

                Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                String[] stocks;

                // Condition 2
                // Update Stock Alert to textfile
                FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                // TODO : Write a logic to remove stocks already altered in ST1_CONDITION_1_PART_B

                //Add Stocks to watchlist
                //Alerts_Stock_Names = "360ONE,ABB,ADANIENSOL,ADANIENT,ADANIGREEN,ADANIPORTS,ATGL,ABCAPITAL,ABFRL,ALKEM,AMBER,AMBUJACEM,ANGELONE,APLAPOLLO,APOLLOHOSP,ASHOKLEY,ASIANPAINT,ASTRAL,AUROPHARMA,DMART,AXISBANK,BAJAJ-AUTO,BAJFINANCE,BAJAJFINSV,BANDHANBNK,BANKBARODA,BANKINDIA,BDL,BEL,BHARATFORG,BHEL,BPCL,BHARTIARTL,BIOCON,BLUESTARCO,BOSCHLTD,BRITANNIA,BSE,CANBK,CDSL,CESC,CGPOWER,CHOLAFIN,CIPLA,COALINDIA,COFORGE,COLPAL,CAMS,CONCOR,CROMPTON,CUMMINSIND,CYIENT,DABUR,DALBHARAT,DELHIVERY,DIVISLAB,DIXON,DLF,DRREDDY,EICHERMOT,ETERNAL,EXIDEIND,FORTIS,NYKAA,GAIL,GLENMARK,GMRAIRPORT,GODREJCP,GODREJPROP,GRANULES,GRASIM,HAVELLS,HCLTECH,HDFCAMC,HDFCBANK,HDFCLIFE,HEROMOTOCO,HFCL,HINDALCO,HAL,HINDPETRO,HINDUNILVR,HINDZINC,HUDCO,ICICIBANK,ICICIGI,ICICIPRULI,IDEA,IDFCFIRSTB,IIFL,INDIANB,IEX,IOC,IRCTC,IRFC,IREDA,IGL,INDUSINDBK,INDUSTOWER,NAUKRI,INFY,INOXWIND,INDIGO,IRB,ITC,JSL,JINDALSTEL,JIOFIN,JSWENERGY,JSWSTEEL,JUBLFOOD,KALYANKJIL,KAYNES,KEI,KFINTECH,KOTAKBANK,KPITTECH,LT,LAURUSLABS,LICHSGFIN,LICI,LTF,LTIM,LUPIN,LODHA,M&M,MANAPPURAM,MANKIND,MARICO,MARUTI,MFSL,MAXHEALTH,MAZDOCK,MOTHERSON,MPHASIS,MCX,MUTHOOTFIN,NATIONALUM,NBCC,NCC,NESTLEIND,NHPC,NMDC,NTPC,NUVAMA,OBEROIRLTY,OIL,ONGC,PAYTM,OFSS,PAGEIND,PATANJALI,POLICYBZR,PERSISTENT,PETRONET,PGEL,PIDILITIND,PIIND,PPLPHARMA,PNBHOUSING,POLYCAB,POONAWALLA,PFC,POWERGRID,PRESTIGE,PNB,RVNL,RBLBANK,RELIANCE,RECLTD,SBICARD,SBILIFE,SHREECEM,SHRIRAMFIN,SIEMENS,SJVN,SOLARINDS,SONACOMS,SRF,SBIN,SAIL,SUNPHARMA,SUPREMEIND,SUZLON,SYNGENE,TATACHEM,TCS,TATACONSUM,TATAELXSI,TATAMOTORS,TATAPOWER,TATASTEEL,TATATECH,TECHM,FEDERALBNK,INDHOTEL,PHOENIXLTD,TITAGARH,TITAN,TORNTPHARM,TORNTPOWER,TRENT,TIINDIA,TVSMOTOR,ULTRACEMCO,UNIONBANK,UNITDSPR,UNOMINDA,UPL,VBL,VEDL,VOLTAS,WIPRO,YESBANK,ZYDUSLIFE";
//                    Alerts_Stock_Names = "IRFC,IREDA,IGL,INDUSINDBK,INDUSTOWER,NAUKRI,INFY,INOXWIND,INDIGO,IRB,ITC,JSL,JINDALSTEL,JIOFIN,JSWENERGY,JSWSTEEL,JUBLFOOD,KALYANKJIL,KAYNES,KEI,KFINTECH,KOTAKBANK,KPITTECH,LT,LAURUSLABS,LICHSGFIN,LICI,LTF,LTIM,LUPIN,LODHA,M&M,MANAPPURAM,MANKIND,MARICO,MARUTI,MFSL,MAXHEALTH,MAZDOCK,MOTHERSON,MPHASIS,MCX,MUTHOOTFIN,NATIONALUM,NBCC,NCC,NESTLEIND,NHPC,NMDC,NTPC,NUVAMA,OBEROIRLTY,OIL,ONGC,PAYTM,OFSS,PAGEIND,PATANJALI,POLICYBZR,PERSISTENT,PETRONET,PGEL,PIDILITIND,PIIND,PPLPHARMA,PNBHOUSING,POLYCAB,POONAWALLA,PFC,POWERGRID,PRESTIGE,PNB,RVNL,RBLBANK,RELIANCE,RECLTD,SBICARD,SBILIFE,SHREECEM,SHRIRAMFIN,SIEMENS,SJVN,SOLARINDS,SONACOMS,SRF,SBIN,SAIL,SUNPHARMA,SUPREMEIND,SUZLON,SYNGENE,TATACHEM,TCS,TATACONSUM,TATAELXSI,TATAMOTORS,TATAPOWER,TATASTEEL,TATATECH,TECHM,FEDERALBNK,INDHOTEL,PHOENIXLTD,TITAGARH,TITAN,TORNTPHARM,TORNTPOWER,TRENT,TIINDIA,TVSMOTOR,ULTRACEMCO,UNIONBANK,UNITDSPR,UNOMINDA,UPL,VBL,VEDL,VOLTAS,WIPRO,YESBANK,ZYDUSLIFE";
                // Create an array to pass to delete_Stock_From_Watchlist function
                if (Alerts_Stock_Names.contains(",")) {
                    stocks = Alerts_Stock_Names.split(",");
                }else {
                    stocks = new String[]{Alerts_Stock_Names};
                }
//
                Current_Watchlist_Details =  watchlistPage.get_Watchlist_Url(Constants.ST2_CNDT_2_TIME, latest_Alert_TimeStamp);
                Current_Watchlist_Name = Current_Watchlist_Details.split(",")[0];
                Current_Watchlist_Url = Current_Watchlist_Details.split(",")[1];

                // add Stocks from "ST2_Cndt_1_" to watchlist of ST2_Cndt2
                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        Current_Watchlist_Name,Current_Watchlist_Url,stocks);
//
//                    //Upload Stocks text file
//                    watchlistPage.upload_Stock_List_TextFile_Using_Robot(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST);

                // Update Stock Alerts to  output textfile for end of the day validation
                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST2_CNDT2_WATCHLIST_UPDATES_FROM_CNDT_1_PART_A,
                        Constants.ACTION_STOCKS_ADDED, Current_Watchlist_Name,
                        Current_Watchlist_Url, Alerts_Stock_Names);

            }
            // </editor-fold>

        }catch (IOException e) {

            System.out.println("Step_2_Checking_ST2_CONDITION_1_PART_A_Alerts: " + e.getMessage());
            ReportUtil.report( false, "FAIL", "Step_2_Checking_ST2_CONDITION_1_PART_A_Alerts, ",  e.getMessage());
        }

        ReportUtil.report( true, "INFO", "-- Step 2 -- Ending -- Step_2_Checking_ST2_CONDITION_1_PART_A_Alert",  "");

    }

    public void Step_3_Checking_ST2_CONDITION_2_Alerts() throws InterruptedException {

        ReportUtil.report(true, "INFO", "-- Step 3 -- Starting -- Checking_ST2_CONDITION_2_Alerts", "");

        // <editor-fold desc="Variables">
        String Alerts_Stock_Names = "";
        String latest_Alert_TimeStamp = "";

        String ST2_Cndt3_Watchlist_Name = prop.getProperty("ST2_Cndt_3_Watchlist_Name");
        String ST2_Cndt_3_Watchlist_Url = prop.getProperty("ST2_Cndt_3_Watchlist_Url");
        // </editor-fold>

        try {

            // If new alert displayed for strategy : ST2_CONDITION_1_PART_B then add it to watchlist of strategy : ST2_Cndt2 and ST1_Cndt3
            if (alertPage.verify_And_Get_Latest_Alert_Displayed_For_Strategies(Constants.ST1_CONDITION_1,
                    Constants.TAB_ALERTPAGE_NAME_ST_2_SECOND_CONDITION)) {

                Alerts_Stock_Names = Constants.LATEST_ALERT_STOCK_NAMES;
                latest_Alert_TimeStamp = Constants.LATEST_ALERT_TIMESTAMP;
                String[] stocks;

                // Update Stock Alert to textfile
                FileAndFolderFunctions.Overwrite_To_Text_File(Constants.TEXTFILE_PATH_FOR_RUNTIME_STOCKS_FOR_WATCHLIST, Alerts_Stock_Names);

                // <editor-fold desc=" Step 1 - Sub 1">

                ReportUtil.report(true, "INFO", "Step 3 - Sub 1-- Adding stocks from alert ST2_CONDITION_2 to ST1_Cndt3 watchlist ", "");
                //Add Stocks to watchlist
                if (Alerts_Stock_Names.contains(",")) {
                    stocks = Alerts_Stock_Names.split(",");
                } else {
                    stocks = new String[]{Alerts_Stock_Names};
                }

                watchlistPage.add_Stocks_To_Watchlist(Constants.TAB_DEFAULT_WATCHLIST_PAGE,
                        ST2_Cndt3_Watchlist_Name, ST2_Cndt_3_Watchlist_Url, stocks);

                FileAndFolderFunctions.update_Output_Text_File_for_Alert_Results(Constants.TEXTFILE_PATH_ST2_CNDT3_WATCHLIST_UPDATES,
                        Constants.ACTION_STOCKS_ADDED, ST2_Cndt3_Watchlist_Name,
                        ST2_Cndt_3_Watchlist_Url, Alerts_Stock_Names);

                // </editor-fold>
            }

        } catch (IOException e) {

            System.out.println("Step 3 Checking_ST2_CONDITION_2_Alerts: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "Step 3 Checking_ST2_CONDITION_2_Alerts, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Step 3 -- Ending -- Checking_ST2_CONDITION_2_Alerts", "");
    }

}
