package cz.zcu.luk.mwes.acl2011;

public class CompoundsEvaluatorRun {
    //	public  ArrayList<ComValScorer> buildSimpleValueScorersZero(boolean firstIsZero) {
//		ArrayList<ComValScorerNeighborsType> comValScoreKindsUsed = new ArrayList<ComValScorerNeighborsType>();
//		ArrayList<Integer> numbers = new ArrayList<Integer>();
//		ArrayList<ComValScorer> composValueScorers = new ArrayList<ComValScorer>();
//
////		comValScoreKindsUsed.add(new ComValScorerNeighborsType(WeightingType.NONE, Operator.PLUS, true));
////		comValScoreKindsUsed.add(new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, true));
//		comValScoreKindsUsed = ComValScorerNeighborsType.getPossibleComValScoreKinds();
//
//		for (int i = 0; i <= 40; i++) {
//			numbers.add(i);
//		}
//		for (int i = 50; i <= 100; i+=10) {
//			numbers.add(i);
//		}
//		for (int i = 200; i <= 1000; i+=100) {
//			numbers.add(i);
//		}
////		for (int i = 2000; i <= 4000; i+=1000) {
////			numbers.add(i);
////		}
//		Integer[] numbersArray = (Integer[]) numbers.toArray(new Integer[0]);
//
//		for(int k = 0; k < numbersArray.length; k++) {
//			for (int m = 0; m < comValScoreKindsUsed.size(); m++) {
//				if (firstIsZero) composValueScorers.add(new ComValScorerNeighbors(0, numbersArray[k], comValScoreKindsUsed.get(m)));
//				else composValueScorers.add(new ComValScorerNeighbors(numbersArray[k], 0, comValScoreKindsUsed.get(m)));
//			}
//		}
//		return composValueScorers;
//	}


//	public ValueEvaluations run(GeneralPars genPars, UKWACcompoundsCounts comCounts) {
//        // LK changed..
//		//String resultDirWithoutSeparator = Config.ACL2011_RESULTS_DIR + genPars.getSsUsed() +"_W_" +
//		//	genPars.getComCounts().getWindowSize() + "_O_" + genPars.getComOrdering();
//        String resultDirWithoutSeparator = Config.ACL2011_RESULTS_DIR + genPars.getSsUsed() +"_W_" +
//                	3d + "End_O_" + genPars.getComOrdering();
//		new File(resultDirWithoutSeparator).mkdir();
//		String resultsDir = resultDirWithoutSeparator + File.separator;
//
//		ArrayList<ComValScorer> composValueScorers = new ArrayList<ComValScorer>();
////		composValueScorers.add(new ComValScorerNeighbors(0, 0, new ComValScorerNeighborsType(WeightingType.NONE, Operator.PLUS, true)));
////		composValueScorers.add(new ComValScorerNeighbors(0, 0, new ComValScorerNeighborsType(WeightingType.NONE, Operator.MULT, false)));
////		composValueScorers.add(new ComValScorerNeighbors(11, 17, WeightingType.ALL_LOG));
////		composValueScorers.add(new ComValScorerNeighbors(0, 7, WeightingType.ALL_LOG));
////		composValueScorers.add(new ComValScorerNeighbors(1, 1, WeightingType.ALL_LOG));
////		composValueScorers.add(new ComValScorerNeighbors(1, 5, WeightingType.ALL_LOG));
////		composValueScorers.add(new ComValScorerNeighbors(26, 22, WeightingType.ALL_LOG));
//		//composValueScorers.add(new ComValScorerNeighboursPMI(10, 10));
//		if (!Constants.CHOSEN_VALUE_SCORERS_ONLY) {
//			// composValueScorers = buildSimpleValueScorers();
//            composValueScorers = buildEndocentricityValueScorers();
//		}
//
//		// create instance of class providing services (load configuration)
//        // LK change..
//		CompoundsServices compoundsServices = new CompoundsServices(resultsDir, comCounts,
//                genPars.getComOrdering(), genPars.getSsUsed());
//        //CompoundsServices compoundsServices = new CompoundsServices(resultsDir, ECompoundsOrdering.COSSIM,
//        //ESemanticSpaceName.NEW_LSAC_UALL_D300_LOG_ENTROPY);
//
//		// ** try all simple models for every compound set
//		// find best simple models according to correlation with humans.. also finds best correlating for different types..
//		// print best simple models and their correlations to output, also print all settings and correlations to output..
//		// print the value scores for compounds of the best simple models to files..
//		// parameters: compound set used, whether to limit only to several scorers..
//		ArrayList<ACLCompoundSetNum> enComSetsForBestSimpleModelFinding = new ArrayList<ACLCompoundSetNum>();
//		enComSetsForBestSimpleModelFinding.add(Constants.TRAINING_DS);
////		enComSetsForBestSimpleModelFinding.add(ACLCompoundSetNum.EN_TRAIN_NUM);
////		enComSetsForBestSimpleModelFinding.add(ACLCompoundSetNum.EN_VAL_NUM);
////		enComSetsForBestSimpleModelFinding.add(ACLCompoundSetNum.EN_TEST_NUM);
////		enComSetsForBestSimpleModelFinding.add(ACLCompoundSetNum.EN_TRAINVAL_NUM);
//
//        ArrayList<CompoundSet> compoundSetsWithComValues = compoundsServices.assignComValScores(enComSetsForBestSimpleModelFinding,
//				composValueScorers);
//
//		// evaluate successes of models..
//		ResultsEvaluator resEval = new ResultsEvaluator(Constants.CROSVAL_USED);
//		ValueEvaluations valEvals = resEval.evaluateValueResults(compoundSetsWithComValues);
//
//		valEvals.printValueResults(resultsDir + Constants.CORRELATIONS_SIMPLE_MODELS_FN);
//		valEvals.printResultsOfBestValScorers(resultsDir + Constants.CORRELATIONS_SIMPLE_MODELS_BEST_FN,
//				Constants.NUMBER_OF_BEST, Constants.TRAINING_DS);
//
//		System.out.println();
//		System.out.println("Best models are chosen!");
//		System.out.println();
//
//		// ** evaluate models
//		// create final models: best models + combined models good for different types
//		// (could choose e.g. 5 best for every type and combine them..)
//		// models will offer ordering (it will solve problem with combined models..)
//		// models will offer num values (preserve ordering for not typed!)
//		//   (these will be trained using some training data,
//		//   typed training will be also offered..)
//		// models will offer coarse values
//		//   (these will be trained using some training data,
//		//   typed training will be also offered..)
//		FinalScorersHandler finalScorers = new FinalScorersHandler(compoundSetsWithComValues,
//				valEvals, Constants.TRAINING_DS);
//		finalScorers.createAndTrainFinalScorers(5, Constants.NUMBER_OF_BEST_COMBINED);
//		ArrayList<ACLCompoundSetNum> enComSetsForTesting = new ArrayList<ACLCompoundSetNum>();
//		enComSetsForTesting.add(Constants.TESTING_DS);
//		List<ComValScorer> finalComposValueScorers = finalScorers.getAllValueScorersUsed();
//		ArrayList<CompoundSet> compoundSetsWithComValuesTesting = compoundsServices.assignComValScores(enComSetsForTesting,
//				finalComposValueScorers);
//		FinalResults finalResults = finalScorers.useFinalScorers(resultsDir, compoundSetsWithComValuesTesting,
//				Constants.TESTING_DS, EvalCriteria.SPEARMAN_C_ALL, ResultType.NUM);
//
//		ResultsEvaluator finalResEval = new ResultsEvaluator(false); // do not use crosval on test data..
//		FinalEvaluations finalEvaluations = finalResEval.evaluateFinalResults(finalResults, Constants.FINAL_DETAILED);
//		finalEvaluations.printFinalResults(resultsDir + Constants.FINAL_RESULTS_FN);
//	//	finalScorers.printFinalScoresFor(ACLCompoundSet.EN_TRAINVAL_NUM);
//
//		compoundsServices.finish();
//
//		return valEvals;
//	}
//
//    private ArrayList<ComValScorer> buildEndocentricityValueScorers() {
//        ArrayList<ComValScorer> endoScorers = new ArrayList<ComValScorer>();
//        for (int i = 0; i < EndocentricityType.values().length; i++) {
//            //endoScorers.add(new ComValScorerEndocentricity(semanticSpaceName, compoundsOrdering, EndocentricityType.values()[i]));
//        }
//        return  endoScorers;
//    }
}
