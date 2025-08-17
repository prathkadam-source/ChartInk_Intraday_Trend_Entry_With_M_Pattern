package qa.testCases;

import org.testng.annotations.Test;
import qa.base.BaseTest;
import org.testng.annotations.Listeners;
import qa.commonfuctions.NewTabsSetUp;
import qa.pages.AlertPage;
import qa.pages.LoginPage;
import qa.pages.WatchlistPage;
import qa.utils.ReportUtil;

import java.io.IOException;

@Listeners(qa.listeners.TestListener.class)
public class MainTestCases  extends BaseTest{

    LoginPage loginPage = new LoginPage();
    NewTabsSetUp newTabsSetUp = new NewTabsSetUp();

    AlertPage alertPage = new AlertPage();

    WatchlistPage watchlistPage = new WatchlistPage();

    Sub_Test_Case sub_Test_Case = new Sub_Test_Case();


    @Test(priority = 1)
    public void TestCase_1_Prerequisite_To_Login_And_Set_Sub_Tabs_Urls() throws InterruptedException {

        ReportUtil.report( true, "INFO", "-- TestCase -- Starting -- TestCase_1_Prerequisite_To_Login_And_Set_Sub_Tabs_Urls",  "");

        sub_Test_Case.Prerequisite_To_Login_And_Set_Sub_Tabs_Urls();

        ReportUtil.report( true, "INFO", "-- TestCase -- Ending -- TestCase_1_Prerequisite_To_Login_And_Set_Sub_Tabs_Urls",  "");

    }

    @Test(priority = 2, dependsOnMethods = "TestCase_1_Prerequisite_To_Login_And_Set_Sub_Tabs_Urls")
    public void TestCase_2_Execute_All_Strategies() throws InterruptedException, IOException {

        ReportUtil.report( true, "INFO", "-- TestCase -- Starting -- TestCase_Execute_All_Strategies",  "");

        sub_Test_Case.Execute_Strategies();

        ReportUtil.report( true, "INFO", "-- TestCase -- Ending -- TestCase_Execute_All_Strategies",  "");

    }


}
