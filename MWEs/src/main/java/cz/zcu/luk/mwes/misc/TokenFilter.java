package cz.zcu.luk.mwes.misc;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.zcu.luk.mwes.common.CommonMisc;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 22.2.14
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
public class TokenFilter {
    private static final Pattern pattern = Pattern.compile("[^a-zA-Z]{2}");

    public static void storeNonWords(String tokenOccurrencesFN, String nonWordsFN) throws FileNotFoundException {
        List<String> lines = CommonMisc.readNotEmptyLinesFromFile(tokenOccurrencesFN);

        PrintWriter pw = new PrintWriter(nonWordsFN);
        for (String oneLine : lines) {
            String possibleWordWithTag = oneLine.split("\t")[0];
            if (isNonWord(possibleWordWithTag)) {
                pw.println(possibleWordWithTag);
            }
        }
        pw.close();
    }

    public static void storeWordsWithFreq(int minFreq, String tokenOccurrencesFN, String nonWordsFN, String tagStopwordsFN, String mainWordsFN) throws FileNotFoundException {
        List<String> linesTokenOccurrences = CommonMisc.readNotEmptyLinesFromFile(tokenOccurrencesFN);
        List<String> linesNonWords = CommonMisc.readNotEmptyLinesFromFile(nonWordsFN);
        List<String> linesTagStopwordsLowerCased = CommonMisc.readNotEmptyLinesFromFileLowerCased(tagStopwordsFN);

        PrintWriter pw = new PrintWriter(mainWordsFN);
        for (String oneTokenOccurrenceLine : linesTokenOccurrences) {
            String possibleWord = oneTokenOccurrenceLine.split("\t")[0];
            if (linesNonWords.contains(possibleWord) || linesTagStopwordsLowerCased.contains(possibleWord.toLowerCase())) {
                // is a nonword or stopword - do not store them..
            }
            else {
                int occurrence = Integer.parseInt(oneTokenOccurrenceLine.split("\t")[1]);
                if (occurrence >= minFreq) {
                    pw.println(possibleWord);
                }
            }
        }
        pw.close();
    }

    private static boolean isNonWord(String token) {
        Matcher matcher = pattern.matcher(token.substring(0, token.length() -3)); // remove tag..
        if (matcher.find()) {
            return true;
        }
        else {
            return false;
        }
    }
}
