package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.util.TrieMap;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 1.11.13
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
public class ExpressionsWithValsLoader {
    private final String expressionsDataFileName;

    private final Map<String, Integer> tokenIndexes;
    private final Map<Long,Double[]> expressionValues;

    public ExpressionsWithValsLoader(String expressionsDataFileName) {
        this.expressionsDataFileName = expressionsDataFileName;

        tokenIndexes = new TrieMap<Integer>();
        expressionValues = new HashMap<Long,Double[]>(Constants.EXPECTED_DISTINCT_BIGRAMS_COUNT);
        loadDataForExpressions();
    }

    private void loadDataForExpressions() {
        try {
            BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(expressionsDataFileName), "UTF-8"));
            String line;
            String[] lineSplitted = null;
            String[] compoundSplitted = null;
            String firstWord, secondWord;
            int counter = 0;
            int index = 0;
            int interval = 1000000;
            int indexFirstWord = -1, indexSecondWord = -1;

            while ((line = documentsReader.readLine()) != null) {
                lineSplitted = line.split("\t");
                compoundSplitted = lineSplitted[0].split(" ");
                firstWord = compoundSplitted[0];
                secondWord = compoundSplitted[1];
                //	if (!((firstWord.endsWith("JJ") && secondWord.endsWith("NN")) ||
                //			(firstWord.endsWith("VV") && secondWord.endsWith("NN")) ||
                //		(firstWord.endsWith("NN") && secondWord.endsWith("VV")))) {

                if (!tokenIndexes.containsKey(firstWord)) {
                    tokenIndexes.put(firstWord, index);
                    index++;
                }
                if (!tokenIndexes.containsKey(secondWord)) {
                    tokenIndexes.put(secondWord, index);
                    index++;
                }
                indexFirstWord = tokenIndexes.get(firstWord);
                indexSecondWord = tokenIndexes.get(secondWord);
                // Map the two token's indices into a single long
                long bigramIndex = (((long)indexFirstWord) << 32) |  indexSecondWord;
//		        if (new Integer(lineSplitted[1]) > 1) {

                Double[] values = new Double[lineSplitted.length - 1];  // expression is stored on zero index -> -1
                for (int i = 1; i < lineSplitted.length; i++) {
                    values[i-1] = new Double(lineSplitted[i]);
                }
                expressionValues.put(bigramIndex, values);
//		        }
                counter++;
                if (counter % interval == 0) {
                    System.out.println(counter);
                }
            }
            documentsReader.close();
            System.out.println("Bigrams loaded: " + counter);
            System.out.println("Distinct words: " + index);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public double getValueForExpression(String expression, int column) {
        if (tokenIndexes.get(expression.split(" ")[0]) == null || tokenIndexes.get(expression.split(" ")[1]) == null) {
            System.out.println("\"" + expression + "\" does not occur in data..");
            return 0;
        }
        int indexFirstWord = tokenIndexes.get(expression.split(" ")[0]);
        int indexSecondWord = tokenIndexes.get(expression.split(" ")[1]);
        long bigramIndex = (((long)indexFirstWord) << 32) |  indexSecondWord;
        if (expressionValues.get(bigramIndex) != null) {
            return expressionValues.get(bigramIndex)[column-1];
        }
        else {
            // return 0 since bigram doesn't occur in spite of its constituents do
            System.out.println("\"" + expression + "\" does not occur in data although its parts do..");
            return 0;
        }
    }
}
