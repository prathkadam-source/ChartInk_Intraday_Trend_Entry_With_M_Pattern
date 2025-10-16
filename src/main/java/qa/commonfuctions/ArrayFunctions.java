package qa.commonfuctions;

import qa.utils.ReportUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ArrayFunctions {

    public String[]  getUniqueArrayFromSetOfTwoArrays (String CurrentTimeStampStocks, String PreviuosTimeStampStocks ) {

        ReportUtil.report( true, "INFO", "-- Function -- Starting -- getUniqueArrayFromSetOfTwoArrays function",  "");

        String[] CurrentTimeStampStocksArray = CurrentTimeStampStocks.split(",");
        String[] PreviuosTimeStampStocksArray = PreviuosTimeStampStocks.split(",");

        Set<String> set1 = new HashSet<>(Arrays.asList(CurrentTimeStampStocksArray));
        Set<String> set2 = new HashSet<>(Arrays.asList(PreviuosTimeStampStocksArray));

        try {

            // Remove common elements (retain only those not in array2)
            set1.removeAll(set2);

        } catch (Exception e) {

            System.out.println("getUniqueArrayFromSetOfTwoArrays: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "getUniqueArrayFromSetOfTwoArrays, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- getUniqueArrayFromSetOfTwoArrays function", "");
        return set1.toArray(new String[0]);

    }
}
