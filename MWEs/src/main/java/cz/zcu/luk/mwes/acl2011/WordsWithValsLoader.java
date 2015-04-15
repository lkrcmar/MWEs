package cz.zcu.luk.mwes.acl2011;

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
public class WordsWithValsLoader {
    private final String wordsDataFileName;

    private final Map<String,Double[]> tokenValues;

    public WordsWithValsLoader(String wordsDataFileName) {
        this.wordsDataFileName = wordsDataFileName;

        tokenValues = new HashMap<String,Double[]>();
        loadDataForWords();
    }

    private void loadDataForWords() {
        try {
            BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(wordsDataFileName), "UTF-8"));
            String line;
            String[] lineSplitted = null;
            int counter = 0;
            int interval = 1000000;

            while ((line = documentsReader.readLine()) != null) {
                lineSplitted = line.split("\t");

                String token = lineSplitted[0];

                Double[] values = new Double[lineSplitted.length - 1];  // word is stored on zero index -> -1
                for (int i = 1; i < lineSplitted.length; i++) {
                    values[i-1] = new Double(lineSplitted[i]);
                }
                tokenValues.put(token, values);

                counter++;
                if (counter % interval == 0) {
                    System.out.println(counter);
                }
            }
            documentsReader.close();
            System.out.println("Words loaded: " + counter);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public double getValueForWord(String word, int column) {
        if (tokenValues.get(word) == null) {
            System.out.println("\"" + word + "\" does not occur in data..");
            return 0;
        }

        return tokenValues.get(word)[column-1];
    }
}
