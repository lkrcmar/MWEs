package cz.zcu.luk.mwes.mains;

import java.sql.Time;
import java.util.*;

import cz.zcu.luk.mwes.acl2011.Common;
import cz.zcu.luk.mwes.common.CommonMisc;
import cz.zcu.luk.mwes.evaluation.ScoredPairMap;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 16.3.14
 * Time: 18:26
 * To change this template use File | Settings | File Templates.
 */
public class KendallCalculator {
    private static int INDEX_ID_USER = 1;
    private static int INDEX_ID_PAIR = 5;
    private static int INDEX_ID_SCORE = 6;

    private static int MIN_SCORED_TO_EVALUATE = 200;

    public static void main (String args[]) {
        List<String> lines = CommonMisc.readNotEmptyLinesFromFile("C:\\Users\\Lukr\\Desktop\\MWE\\mwe_pair_scored-27-3-2.csv");
        //for (int i = 0; i < 50;i++) {
            //parseData(i, lines);
        //}
        Set<Integer> allIdUsers = extractUniques(lines, INDEX_ID_USER);
        Map<String, Integer> valToOccurrences = countOccurrences(lines, INDEX_ID_USER);

        //System.out.println(allIdUsers);
        Map<Integer, ScoredPairMap> idUserToScoredPairs = new HashMap<Integer, ScoredPairMap>();
        for (Integer idUser : allIdUsers) {
            idUserToScoredPairs.put(idUser, mapIdUserToScore(idUser, lines));
        }
        printMatrix(idUserToScoredPairs);

        double[][] scores = new double[28][28];
        for (Map.Entry<Integer, ScoredPairMap> oneIdUserToScoredPairsFirst : idUserToScoredPairs.entrySet()) {
            //System.out.println(oneIdUserToScoredPairsFirst.getKey() + ": " +oneIdUserToScoredPairsFirst.getValue().toString());
            for (Map.Entry<Integer, ScoredPairMap> oneIdUserToScoredPairsSecond : idUserToScoredPairs.entrySet()) {
                if (oneIdUserToScoredPairsFirst.getValue().size() < MIN_SCORED_TO_EVALUATE || oneIdUserToScoredPairsSecond.getValue().size() < MIN_SCORED_TO_EVALUATE) {

                }
                else {
                //    System.out.println(oneIdUserToScoredPairsFirst.getKey() + ": " +oneIdUserToScoredPairsFirst.getValue().toString());
                //    System.out.println(oneIdUserToScoredPairsSecond.getValue().toString());
                //    printValues(oneIdUserToScoredPairsFirst.getValue(), oneIdUserToScoredPairsSecond.getValue());
                    double scoreBasic = evaluateKendallBasic(oneIdUserToScoredPairsFirst.getValue(), oneIdUserToScoredPairsSecond.getValue());
                    double scoreTies = evaluateKendallWithTies(oneIdUserToScoredPairsFirst.getValue(), oneIdUserToScoredPairsSecond.getValue());

                    scores[oneIdUserToScoredPairsFirst.getKey()][oneIdUserToScoredPairsSecond.getKey()] = Common.round(scoreTies, 3);
                //    System.out.println(oneIdUserToScoredPairsFirst.getKey() + "\t" + oneIdUserToScoredPairsSecond.getKey() + "\t" + scoreBasic + "\t" + scoreTies);
                }
            }
        }

        System.out.println();
        System.out.println();
        System.out.print( "\t");
        for (int i = 0; i < 28; i++) {
            if (allIdUsers.contains(i)) {
                System.out.print(i + "\t");
            }
        }
        System.out.println();
        for (int i = 0; i < scores.length; i++) {
            if (allIdUsers.contains(i)) System.out.print(i + "\t");
            for (int j = 0; j < scores.length; j++) {
                if (allIdUsers.contains(i) && allIdUsers.contains(j)) {
                    System.out.print(scores[i][j] + "\t");
                }
            }
            if (allIdUsers.contains(i)) {
                System.out.println();
            }
        }

        System.out.println();
        System.out.println();
        System.out.println(valToOccurrences.toString());

    }

    private static void printMatrix(Map<Integer, ScoredPairMap> idUserToScoredPairs) {
        String[][] matrix = new String[1001][30];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[j].length; j++) {
                matrix[i][j] = "";
            }
        }


        for (Map.Entry<Integer, ScoredPairMap> e : idUserToScoredPairs.entrySet()) {
            for (Map.Entry<Integer, String> e2 : e.getValue().getMap().entrySet()) {
                matrix[e2.getKey()][e.getKey()] = e2.getValue();
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[j].length; j++) {
                System.out.println(matrix[i][j]+"\t");
            }
            System.out.println();
        }
    }

    private static void printValues(ScoredPairMap firstVals, ScoredPairMap secondVals) {
        int maxSize = Math.min(firstVals.size(), secondVals.size());
        int[] printedFirst = new int[maxSize];
        int[] printedSecond = new int[maxSize];

        for (int i = 1; i <= maxSize; i++) {
            printedFirst[i-1] = convert(firstVals.get(i));
            printedSecond[i-1] = convert(secondVals.get(i));
        }
        System.out.println(Arrays.toString(printedFirst));
        System.out.println(Arrays.toString(printedSecond));
        for (int i = 0; i < printedFirst.length; i++) {
            if (printedFirst[i] != -1000 && printedSecond[i] != -1000) {
                System.out.print(printedFirst[i] + " ");
            }
        }
        System.out.println();
        for (int i = 0; i < printedSecond.length; i++) {
            if (printedFirst[i] != -1000 && printedSecond[i] != -1000) {
                System.out.print(printedSecond[i] + " ");
            }
        }
        System.out.println();
    }

    private static int convert(String val) {
        if (val.equals("&lt")) return -1;
        else if (val.equals("&gt")) return 1;
        else if (val.equals("=")) return 0;
        else return -1000;

    }

    private static double evaluateKendallWithTies(ScoredPairMap scoredByFirst, ScoredPairMap scoredBySecond) {
        // (P - Q) / sqrt((P + Q + T) * (P + Q + U))
        int concordant = countConcordant(scoredByFirst, scoredBySecond);
        int disconcordant = countDisconcordant(scoredByFirst, scoredBySecond);
        int leftTies = countLeftTies(scoredByFirst, scoredBySecond);
        int rightTies = countRightTies(scoredByFirst, scoredBySecond);
       // System.out.println(" xxxz:"+concordant + "   " + disconcordant+":xx ");
        // System.out.println(" xx:"+leftTies + "   " + rightTies+":xx ");
        return (concordant - disconcordant) / Math.sqrt((double) ((concordant + disconcordant + leftTies) * (concordant + disconcordant + rightTies)));
    }

    private static double evaluateKendallBasic(ScoredPairMap scoredByFirst, ScoredPairMap scoredBySecond) {
        // (P - Q) / (P + Q)
        int concordant = countConcordant(scoredByFirst, scoredBySecond);
        int disconcordant = countDisconcordant(scoredByFirst, scoredBySecond);
        return (concordant - disconcordant) / (double)(concordant + disconcordant);
    }

    private static int countRightTies(ScoredPairMap scoredByFirst, ScoredPairMap scoredBySecond) {
        int rightTies = 0;
        for (Map.Entry<Integer, String> oneScoredByFirst : scoredByFirst.getMap().entrySet()) {
            if (scoredBySecond.get(oneScoredByFirst.getKey()) != null) {
                if ((oneScoredByFirst.getValue().equals("&lt") && scoredBySecond.get(oneScoredByFirst.getKey()).equals("=")) ||
                        (oneScoredByFirst.getValue().equals("&gt") && scoredBySecond.get(oneScoredByFirst.getKey()).equals("="))) {
                    rightTies++;
                }
            }
        }
        return rightTies;
    }

    private static int countLeftTies(ScoredPairMap scoredByFirst, ScoredPairMap scoredBySecond) {
        int leftTies = 0;
        for (Map.Entry<Integer, String> oneScoredByFirst : scoredByFirst.getMap().entrySet()) {
            if (scoredBySecond.get(oneScoredByFirst.getKey()) != null) {
                if ((oneScoredByFirst.getValue().equals("=") && scoredBySecond.get(oneScoredByFirst.getKey()).equals("&lt")) ||
                        (oneScoredByFirst.getValue().equals("=") && scoredBySecond.get(oneScoredByFirst.getKey()).equals("&gt"))) {
                    leftTies++;
                }
            }
        }
        return leftTies;
    }

    private static int countDisconcordant(ScoredPairMap scoredByFirst, ScoredPairMap scoredBySecond) {
        int disconcordant = 0;
        for (Map.Entry<Integer, String> oneScoredByFirst : scoredByFirst.getMap().entrySet()) {
            if (scoredBySecond.get(oneScoredByFirst.getKey()) != null) {
                if ((oneScoredByFirst.getValue().equals("&gt") && scoredBySecond.get(oneScoredByFirst.getKey()).equals("&lt")) ||
                    (oneScoredByFirst.getValue().equals("&lt") && scoredBySecond.get(oneScoredByFirst.getKey()).equals("&gt"))) {
                    disconcordant++;
                }
            }
        }
        return disconcordant;

    }

    private static int countConcordant(ScoredPairMap scoredByFirst, ScoredPairMap scoredBySecond) {
        int concordant = 0;
        for (Map.Entry<Integer, String> oneScoredByFirst : scoredByFirst.getMap().entrySet()) {
            if (scoredBySecond.get(oneScoredByFirst.getKey()) != null) {
                if ((oneScoredByFirst.getValue().equals("&lt") && scoredBySecond.get(oneScoredByFirst.getKey()).equals("&lt")) ||
                        (oneScoredByFirst.getValue().equals("&gt") && scoredBySecond.get(oneScoredByFirst.getKey()).equals("&gt"))) {
                    concordant++;
                }
            }
        }
        return concordant;
    }


    private static ScoredPairMap mapIdUserToScore(int idUser, List<String> lines) {
        ScoredPairMap scoredPairs = new ScoredPairMap();
        for (String line : lines) {
            String[] oneLineSplitted = processAndSplitLine(line);
            int idUserExtracted = Integer.parseInt(oneLineSplitted[INDEX_ID_USER]);
            if (idUser == idUserExtracted) {
                int idPairExtracted = Integer.parseInt(oneLineSplitted[INDEX_ID_PAIR]);
                String idScoreExtracted = oneLineSplitted[INDEX_ID_SCORE];
                scoredPairs.put(idPairExtracted, idScoreExtracted);
            }
        }
        return scoredPairs;
    }

    private static String[] processAndSplitLine (String line) {
        return line.replaceAll("\"","").split(";");
    }


    private static Set<Integer> extractUniques(List<String> lines, int column) {
        Set<Integer> allIds = new LinkedHashSet<Integer>();
        for (String line : lines) {
            String[] oneLineSplitted = processAndSplitLine(line);
            allIds.add(Integer.parseInt(oneLineSplitted[column]));
        }
        return  allIds;
    }

    private static Map<String, Integer> countOccurrences(List<String> lines, int column) {
        Map<String, Integer> valToOccurrence = new LinkedHashMap<String, Integer>();
        for (String line : lines) {
            String[] oneLineSplitted = processAndSplitLine(line);
            String val = oneLineSplitted[column];
            if (valToOccurrence.containsKey(val)) {
                valToOccurrence.put(val, valToOccurrence.get(val)+1);
            }
            else {
                valToOccurrence.put(val, 1);
            }

        }
        return  valToOccurrence;
    }



    private static void parseData(int idUser, List<String> lines) {
        List<String> userLines = new ArrayList<String>();
        for (String line : lines) {
            String[] oneLineSplitted = line.replaceAll("\"","").split(";");

            if (Integer.parseInt(oneLineSplitted[1]) == idUser) {
                userLines.add(line.replaceAll("\"",""));
            }
        }
        if (userLines.isEmpty()) return;
        String[] oneLineSplitted = userLines.get(0).split(";");
        Time firstTime = Time.valueOf(oneLineSplitted[4]);
        //System.out.println(firstTime);

        List<Long> timeDifs = new ArrayList<Long>();
        Time lastTime = (Time) firstTime.clone();
        for (String line : userLines) {
            oneLineSplitted = line.split(";");
            Time current = Time.valueOf(oneLineSplitted[4]);

            Long timeDifference = current.getTime() - lastTime.getTime();
            timeDifs.add(timeDifference);

            lastTime = (Time) current.clone();
        }

        System.out.println("--------------");
        for (Long oneTimeDif : timeDifs) {
          //  System.out.println(oneTimeDif/1000);
        }


        long sum = 0;
        for (Long oneTimeDif : timeDifs) {
            if (oneTimeDif > -1800000 && oneTimeDif < 1800000) {
                sum +=oneTimeDif/1000;
           //     System.out.println(sum);
            }
        }
        System.out.println(userLines.get(0).split(";")[1]+ "\t" +userLines.get(0).split(";")[2]+ "\t" +


        Integer.parseInt(userLines.get(userLines.size()-1).split(";")[0])%1000 + "\t" +sum/(double)60);
       // System.out.println(userLines.get(0));
    }

    private static String parseLines(String[] prevLineSplitted, String[] oneLineSplitted) {

          return Arrays.toString(prevLineSplitted) + "\n" + Arrays.toString(oneLineSplitted);
    }
}
