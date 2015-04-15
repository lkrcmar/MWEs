package cz.zcu.luk.mwes.mains;

import java.io.*;
import java.util.*;

import cz.zcu.luk.mwes.acl2011.*;
import cz.zcu.luk.mwes.scoring.ComValScorersFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 11.11.12
 * Time: 19:18
 * To change this template use File | Settings | File Templates.
 */
public class CompoundsEvaluatorNew {

//    private Map<String, String> prepareFixes() {
//        NounsToNoun ntn = new NounsToNoun();
//        Map<String, String> nounsMapping = ntn.getNounsToNounMapping(Config.NOUNS_MAPPING_FILE_NAME);
//        if (nounsMapping.isEmpty()) throw new IllegalStateException("No nouns mapping loaded!");
//        return nounsMapping;
//    }

    public void run(String outputDir, boolean trainVal, ScorersGroup appliedScorersGroup, boolean drawGraphs) {
        ACLCompoundSetNum datasetForTraining;
        if (trainVal) {
            //datasetForTraining = ACLCompoundSetNum.EN_TRAINVAL_NUM;
            datasetForTraining = ACLCompoundSetNum.CZ_SURF_NUM;
        }
        else {
            datasetForTraining = ACLCompoundSetNum.EN_TEST_NUM;
        }
        //ACLCompoundSetNum datasetForTesting = ACLCompoundSetNum.EN_TRAINVAL_NUM;
        String resultDirWithoutSeparator = Config.ACL2011_RESULTS_DIR + outputDir;//"FinalResults-5-4-9";

        // create scorers which will be tested..
        ArrayList<ComValScorer> allScorers = ComValScorersFactory.getScorers(appliedScorersGroup); //.getTestScorersEndocentricity();
        //ArrayList<ComValScorer> allScorers = ComValScorersFactory.getTestScorersCompositionality();
        //ArrayList<ComValScorer> allScorers = ComValScorersFactory.getTestScorersCompoNeigbors();
        //allScorers.addAll(ComValScorersFactory.getTestScorersNeightbors());

        // prepare fixes for nouns in datasets
        //Map<String, String> nounsMapping = prepareFixes();

        // load a given training dataset
        ArrayList<ACLCompoundSetNum> dataSets = new ArrayList<ACLCompoundSetNum>();
        dataSets.add(datasetForTraining);
        ArrayList<CompoundSet> compoundSets = CompoundSetsHandler.loadNumCompoundSets(dataSets);

        // score fixed training dataset by all the scorers..
        ComValScorerManager.assignComposionalityScores(compoundSets, allScorers);

        // evaluate scorers - count correlations with the gold data..
        // evaluate successes of models..
        new File(resultDirWithoutSeparator).mkdir();
        String resultsDir = resultDirWithoutSeparator + File.separator;

        ResultsEvaluator resEval = new ResultsEvaluator(datasetForTraining);
        ValueEvaluations valEvals = resEval.evaluateValueResults(compoundSets);

        valEvals.printValueResults(resultsDir + Constants.CORRELATIONS_SIMPLE_MODELS_FN);
        //Map<EvalCriteria, List<ComValScorer>> bestScorersMap = valEvals.printValueResultsOrganizedLatex(resultsDir + "organizedTrain_" +
        //         Constants.CORRELATIONS_SIMPLE_MODELS_FN, 1, datasetForTraining, null);
        Map<EvalCriteria, List<SingleValueEvaluation>> bestScorersMap = valEvals.getBestSingleEvaluations(0, datasetForTraining);
        if (drawGraphs) {
            valEvals.printPrecisionRecallGraphs(resultsDir, PrecRecGraphSmoothing.SECOND_TYPE, datasetForTraining);
        }
        //valEvals.printResultsOfBestValScorers(resultsDir + Constants.CORRELATIONS_SIMPLE_MODELS_BEST_FN,
        //        Constants.NUMBER_OF_BEST, Constants.TRAINING_DS);


        System.out.println();
        System.out.println("Best models are chosen!");
        System.out.println();

        List<SingleValueEvaluation> bestComVals = bestScorersMap.get(EvalCriteria.SPEARMAN_C_ALL_AVG);

//        for (SingleValueEvaluation sve : bestComVals) {
//            System.out.println(sve.getValueScorer().toString());
//
//            for (String line : compoundSets.get(0).getACLNumFormattedValuesCreatedBy(sve.getValueScorer())) {
//                System.out.println(line);
//            }
//
//            System.out.println();
//            System.out.println();
//        }

        createAtributeMap(compoundSets.get(0), bestComVals, resultsDir);


        System.exit(0);

        //ACLCompoundSetNum datasetForTesting = ACLCompoundSetNum.EN_TEST_NUM;
//        ACLCompoundSetNum datasetForTesting = ACLCompoundSetNum.EN_TRAINVAL_NUM;
//        ArrayList<ACLCompoundSetNum> enComSetsForTesting = new ArrayList<ACLCompoundSetNum>();
//        enComSetsForTesting.add(datasetForTesting);
//        ArrayList<CompoundSet> compoundSetsWithComValuesTesting = CompoundSetsHandler.loadNumCompoundSets(nounsMapping, enComSetsForTesting);
//        ArrayList<ComValScorer> bestScorersList = new ArrayList<ComValScorer>();
//        for (Map.Entry<EvalCriteria, List<ComValScorer>> e : bestScorersMap.entrySet()) {
//            bestScorersList.addAll(e.getValue());
//        }
//
//        ComValScorerManager.assignComposionalityScores(compoundSetsWithComValuesTesting, bestScorersList);
//        ResultsEvaluator resEvalT = new ResultsEvaluator(datasetForTesting);
//        ValueEvaluations valEvalsT = resEvalT.evaluateValueResults(compoundSetsWithComValuesTesting);
//        Map<EvalCriteria, List<ComValScorer>> bestScorersNotUsed = valEvalsT.printValueResultsOrganizedLatex(resultsDir + "organizedTest_" +
//                Constants.CORRELATIONS_SIMPLE_MODELS_FN, 1, datasetForTesting, bestScorersMap);

//        // print results of the models into the given output dir
//        FinalScorersHandler finalScorersHandler = new FinalScorersHandler(compoundSets, valEvals, datasetForTesting);
//        finalScorersHandler.createAndTrainFinalScorers(0, 0);
//        ArrayList<ACLCompoundSetNum> enComSetsForTesting = new ArrayList<ACLCompoundSetNum>();
//        enComSetsForTesting.add(datasetForTesting);
//        ArrayList<CompoundSet> compoundSetsWithComValuesTesting = CompoundSetsHandler.loadNumCompoundSets(nounsMapping, enComSetsForTesting);
//        List<ComValScorer> finalComposValueScorers = finalScorersHandler.getAllValueScorersUsed();
//        ComValScorerManager.assignComposionalityScores(compoundSetsWithComValuesTesting,
//                finalComposValueScorers);
//        FinalResults finalResults = finalScorersHandler.useFinalScorers(resultsDir, compoundSetsWithComValuesTesting,
//                datasetForTesting, EvalCriteria.SPEARMAN_C_ALL, ResultType.NUM);
//
//        ResultsEvaluator finalResEval = new ResultsEvaluator(datasetForTesting);
//        FinalEvaluations finalEvaluations = finalResEval.evaluateFinalResults(finalResults, Constants.FINAL_DETAILED);
//        finalEvaluations.printFinalResults(resultsDir + Constants.FINAL_RESULTS_FN);
    }

    private void createAtributeMap(CompoundSet compoundSet, List<SingleValueEvaluation> comVals, String resultsDir) {
        List<String> columnMeaning = new ArrayList<String>();
        Map<String, String> expressionToVals = new LinkedHashMap<String, String>();
        System.out.println(compoundSet.getName());

        columnMeaning.add("EXPRESSION");
        if(Constants.ADD_TYPE) {
            columnMeaning.add("TYPE");
        }

        for (String line : compoundSet.getACLNumFormattedValuesCreatedBy(comVals.get(0).getValueScorer())) {
            CompoundACLNumBean oneExpression = new CompoundACLNumBean(line);
            String value = "\t";
            if (Constants.ADD_TYPE) {
                value += oneExpression.getTag() +"\t";
            }
            expressionToVals.put(oneExpression.getCompound(), value);
        }

        Collections.sort(comVals, new Comparator<SingleValueEvaluation>() {
            public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
                return o1.getValueScorer().toString().compareTo(o2.getValueScorer().toString());
            }
        });
        for (SingleValueEvaluation sve : comVals) {
            columnMeaning.add(sve.getValueScorer().toString());

            for (String line : compoundSet.getACLNumFormattedValuesCreatedBy(sve.getValueScorer())) {
                CompoundACLNumBean oneExpression = new CompoundACLNumBean(line);
                String key = oneExpression.getCompound();
                String actualVals = expressionToVals.get(key);
                expressionToVals.put(key, actualVals + oneExpression.getScore() + "\t");
            }
        }

        columnMeaning.add("GOLD_SCORE");
        for (String line : compoundSet.getCompoundSetNumData().getCompoundsInStrings()) {
            CompoundACLNumBean oneExpression = new CompoundACLNumBean(line);
            String key = oneExpression.getCompound();
            String actualVals = expressionToVals.get(key);
            expressionToVals.put(key, actualVals + oneExpression.getScore());
        }

        if (resultsDir != "") {
             createArffFile(columnMeaning, expressionToVals, resultsDir);
        }
        else {
            for (String oneColumnMeaning : columnMeaning) {
                System.out.print(oneColumnMeaning + "\t");
            }
            System.out.println();
            for (Map.Entry<String, String> entry : expressionToVals.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        }

    }

    private void createArffFile(List<String> columnMeaning, Map<String, String> expressionToVals, String resultsDir) {
        String fileName = resultsDir + "data.arff";
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            pw.println("%");
            pw.println("@relation 'mweCompositionality'");
            for (String oneColumn : columnMeaning) {
                if (oneColumn.equals("EXPRESSION")) {
                    pw.println("@attribute EXPRESSION string");
                }
                else if (Constants.ADD_TYPE && oneColumn.equals("TYPE")) {
                    pw.println("@attribute TYPE {EN_ADJ_NN,EN_V_OBJ,EN_V_SUBJ}");
                }
                else if (oneColumn.equals("GOLD_SCORE")) {
                    pw.println("@attribute class real");
                }
                else {
                    pw.println("@attribute " + oneColumn + " real");
                }
            }
            pw.println("@data");
            for (Map.Entry<String, String> e : expressionToVals.entrySet()) {
                pw.println(("\"" + e.getKey() +"\"" + e.getValue()).replaceAll("\t", ","));
            }

            pw.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void run2(String outputDir, boolean trainVal, ScorersGroup appliedScorersGroup, boolean drawGraphs) {
        String resultDirWithoutSeparator = Config.ACL2011_RESULTS_DIR + outputDir;//"FinalResults-5-4-9";

        // create scorers which will be tested..
        ArrayList<ComValScorer> allScorers = ComValScorersFactory.getScorers(appliedScorersGroup); //.getTestScorersEndocentricity();;

        String inputFN = "expressionsFinal";

        CompoundSet compoundSet = new CompoundSet(Config.TASK_DATA_DIR + inputFN);
        ArrayList<CompoundSet> compoundSets = new ArrayList<CompoundSet>();
        compoundSets.add(compoundSet);

        // score fixed training dataset by all the scorers..
        ComValScorerManager.assignComposionalityScores(compoundSets, allScorers);

        new File(resultDirWithoutSeparator).mkdir();
        String resultsDir = resultDirWithoutSeparator + File.separator;

        compoundSets.get(0).printResultsToFile(resultsDir + inputFN + "scored.txt");
    }

    public static Map<String, ScorersGroup> scorersGroupsMap = new HashMap<String, ScorersGroup>();

    static {
        scorersGroupsMap.put("HYBRID", ScorersGroup.HYBRID);

        scorersGroupsMap.put("LSA_one_small", ScorersGroup.LSA_ONE_SMALL); // endocentricity, low memory demanding, for validity testing
        scorersGroupsMap.put("LSA_smalls", ScorersGroup.LSA_SMALLS); // endocentricity, low memory demanding, for validity testing
        scorersGroupsMap.put("tmp", ScorersGroup.TMP); // low memory demanding, for tests
        scorersGroupsMap.put("tmp2", ScorersGroup.TMP2); // for tests

        // compositionality counted by Cs only, otherwise files could rewrite each other..!!!
        scorersGroupsMap.put("all", ScorersGroup.ALL);
        scorersGroupsMap.put("LSAallC", ScorersGroup.LSA_ALL_C);
        scorersGroupsMap.put("COALSallC", ScorersGroup.COALS_ALL_C);
        scorersGroupsMap.put("HALallC", ScorersGroup.HAL_ALL_C);
        scorersGroupsMap.put("VSMallC", ScorersGroup.VSM_ALL_C);
        scorersGroupsMap.put("RIallC", ScorersGroup.RI_ALL_C);
        scorersGroupsMap.put("LSAallE", ScorersGroup.LSA_ALL_E);
        scorersGroupsMap.put("COALSallE", ScorersGroup.COALS_ALL_E);
        scorersGroupsMap.put("HALallE", ScorersGroup.HAL_ALL_E);
        scorersGroupsMap.put("VSMallE", ScorersGroup.VSM_ALL_E);
        scorersGroupsMap.put("RIallE", ScorersGroup.RI_ALL_E);
        scorersGroupsMap.put("LSAallP", ScorersGroup.LSA_ALL_P);
        scorersGroupsMap.put("COALSallP", ScorersGroup.COALS_ALL_P);
        scorersGroupsMap.put("HALallP", ScorersGroup.HAL_ALL_P);
        scorersGroupsMap.put("VSMallP", ScorersGroup.VSM_ALL_P);
        scorersGroupsMap.put("RIallP", ScorersGroup.RI_ALL_P);
    }

    public static void main(String args[]) {
        Config config = new Config();
        config.loadConfinguration(args[0]);
        String outputDir = args[1];
        boolean trainVal = Boolean.parseBoolean(args[2]);
        ScorersGroup appliedScorersGroup = scorersGroupsMap.get(args[3]);
        boolean drawGraphs = Boolean.parseBoolean(args[4]);

        CompoundsEvaluatorNew cen = new CompoundsEvaluatorNew();
        cen.run(outputDir, trainVal, appliedScorersGroup, drawGraphs);
    }
}
