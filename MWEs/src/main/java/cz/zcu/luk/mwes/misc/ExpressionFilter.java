package cz.zcu.luk.mwes.misc;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import cz.zcu.luk.mwes.common.CommonMisc;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 25.2.14
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */
public class ExpressionFilter {
    public static void storeExpressionsWithFreq(int minFreq, String fnExpressionStats, String fnExpressionMoreFreq) throws FileNotFoundException {
        List<String> linesExpressionsOccurrences = CommonMisc.readNotEmptyLinesFromFile(fnExpressionStats);

        PrintWriter pw = new PrintWriter(fnExpressionMoreFreq);
        for (String oneExpOccurrenceLine : linesExpressionsOccurrences) {
            String possibleExp = oneExpOccurrenceLine.split("\t")[0];
            int occurrence = Integer.parseInt(oneExpOccurrenceLine.split("\t")[1]);

            if (occurrence >= minFreq) {
                pw.println(possibleExp);
            }
        }
        pw.close();
    }
}
