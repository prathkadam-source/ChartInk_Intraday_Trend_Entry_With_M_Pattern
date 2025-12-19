WinWaitActive("Open", "", 10)
Sleep(2000) ; Wait 2 second
ControlFocus("Open", "", "Edit1")
;ControlSetText("Open", "", "Edit1", "C:\Users\prath\IdeaProjects\ChartInk_Intraday_Trend_Entry_With_M_Pattern\src\main\resources\data\runTime_Stocks_for_watchlist.txt")
ControlSetText("Open", "", "Edit1", $CmdLine[1])
Sleep(100) ; Wait 1 second
ControlClick("Open", "", "Button1")