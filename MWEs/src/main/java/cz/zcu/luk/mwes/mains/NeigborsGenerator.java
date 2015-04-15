package cz.zcu.luk.mwes.mains;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.Similarity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import cz.zcu.luk.mwes.acl2011.*;
import cz.zcu.luk.mwes.common.CommonMisc;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 20.1.14
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
public class NeigborsGenerator {
    public static void main(String args[]) throws FileNotFoundException, UnsupportedEncodingException {
        Config config = new Config();
        config.loadConfinguration(args[0]);

        List<String> allLines = CommonMisc.readLinesFromFile("/storage/brno2/home/lkrcmar/inputWordsForNeigbors.txt");
        //List<String> allWords = new ArrayList<String>(new HashSet<String>(extractWords(allLines)));
        List<String> allWords = extractWords(allLines);
        System.out.println("WORD COUNT: " +allWords.size());

        String semanticSpaceName = "H_COALS_M10000000_N10000000_5000ALLXX";
        SemanticSpace space = SSpaceProvider.getInstance().getSSpace(semanticSpaceName);

        String outputFN = "/storage/brno2/home/lkrcmar/outputNeigborsForWords.txt";
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFN), "UTF-8"));
        int counter =0;
        for (String word : allWords) {
            counter++;
            //if (counter%1000==0) break;
            List<String> neigbors = getNeigbors(word, space);
            if (neigbors != null) {
                StringBuilder strBuilder = new StringBuilder(word).append("|");
                for (int i= 0; i < neigbors.size()-1; i++) {
                    strBuilder.append(neigbors.get(i)).append("|");
                    double sim = Similarity.getSimilarity(AlternativesProvider.getInstance().castFrom(ECompoundsOrdering.COSSIM),
                            space.getVector(word + "_XX"),
                            space.getVector(neigbors.get(i) + "_XX"));
                    strBuilder.append(Common.round(sim, 6)).append("|");
                }
                strBuilder.append(neigbors.get(neigbors.size() - 1)).append("|");
                double sim = Similarity.getSimilarity(AlternativesProvider.getInstance().castFrom(ECompoundsOrdering.COSSIM),
                        space.getVector(word + "_XX"),
                        space.getVector(neigbors.get(neigbors.size()-1) + "_XX"));
                strBuilder.append(Common.round(sim, 6));
                pw.println(strBuilder.toString());
            }
            else{
                pw.println(word+"|NOTFOUND");
            }
        }

        pw.close();
    }

    private static List<String> getNeigbors(String word, SemanticSpace space) {
        try {
            SSWord wordSS = new SSWord(word + "_XX");
            List<SSWord> wordNeigbors = AlternativesProvider.getInstance().getAlternatives(wordSS, "ANY", space, 100, ECompoundsOrdering.COSSIM);
            return getRidOfSSpostfix(wordNeigbors);
        }
        catch (IllegalArgumentException e) {
            System.out.println("catched word not found in space");
            return null;
        }
    }

    private static List<String> getRidOfSSpostfix(List<SSWord> wordNeigbors) {
        List<String> allWords = new ArrayList<String>();
        for (SSWord wordSS : wordNeigbors) {
            allWords.add(wordSS.toString().substring(0, wordSS.toString().length() - 3));
        }
        return allWords;
    }

    private static List<String> extractWords(List<String> allLines) {
        List<String> allWords = new ArrayList<String>();
        for (String oneLine : allLines) {
            allWords.add(oneLine.split("\\|")[1]);
        }
        return allWords;
    }
}

