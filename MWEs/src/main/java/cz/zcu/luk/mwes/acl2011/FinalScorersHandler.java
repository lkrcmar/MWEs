package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FinalScorersHandler {
	private ArrayList<CompoundSet> compoundSetsWithComValues;
	private ValueEvaluations valEvals;
	private ACLCompoundSetNum trainingDataSet;
	private Map<EvalCriteria, List<FinalScorer>> finalScorers;
	private List<ComValScorer> allValueScorersUsed;

	public List<ComValScorer> getAllValueScorersUsed() {
		return allValueScorersUsed;
	}

	public FinalScorersHandler(ArrayList<CompoundSet> compoundSetsWithComValues, 
			ValueEvaluations valEvals, ACLCompoundSetNum trainingDataSet) {
		this.compoundSetsWithComValues = compoundSetsWithComValues;
		this.valEvals = valEvals;
		this.trainingDataSet = trainingDataSet;
		
	}
	
	public void createAndTrainFinalScorers(int numberOfBest, int numberOfBestCombined) {
		this.finalScorers = new LinkedHashMap<EvalCriteria, List<FinalScorer>>();
		
		// prepare best simple scorers..
		Map<EvalCriteria, List<SingleValueEvaluation>> bestEvals = valEvals.getBestSingleEvaluations(numberOfBest, trainingDataSet);
		//Map<EvalCriteria, List<SingleValueEvaluation>> bestEvals = valEvals.getBestSingleEvaluationsCombinationZeros(numberOfBest, trainingDataSet);
		getAllValueScorersUsed(bestEvals);
		
		CompoundSet trainCompoundSetWithVals = getCompoundSetWithComValues(compoundSetsWithComValues, trainingDataSet);
		
		// create final scorers.. using evaluations (-> best simple val scorer and dataset on which it was trained
		for (Map.Entry<EvalCriteria, List<SingleValueEvaluation>> evalCriteriaXbestEvaluations : bestEvals.entrySet()) {
			ArrayList<FinalScorer> simpleFinalScorersBestForEvalCriteria = new ArrayList<FinalScorer>();
			for (SingleValueEvaluation oneSingleValEval : evalCriteriaXbestEvaluations.getValue()) {
				ComValScorer usedComValScorer = oneSingleValEval.getValueScorer();
				FinalScorerSimple oneFinalScorer = new FinalScorerSimple(oneSingleValEval,
						trainCompoundSetWithVals.getComposValuesAssignedByDifferentScorers().get(usedComValScorer));
				simpleFinalScorersBestForEvalCriteria.add(oneFinalScorer);
			}
			finalScorers.put(evalCriteriaXbestEvaluations.getKey(), simpleFinalScorersBestForEvalCriteria);
		}
		
		// create combined final scorers..
		for (int i = 0; i < numberOfBestCombined; i++) {
			for (int j = 0; j < numberOfBestCombined; j++) {
				for (int k = 0; k < numberOfBestCombined; k++) {
					SingleValueEvaluation bestSingleValEvalAN = bestEvals.get(EvalCriteria.SPEARMAN_C_AN).get(i);
					ComValScorer bestValScorerAN = bestSingleValEvalAN.getValueScorer();
					Map<CompoundSS, Double> bestValScorerANcomValues = trainCompoundSetWithVals.getComposValuesAssignedByDifferentScorers().get(bestValScorerAN);
					
					SingleValueEvaluation bestSingleValEvalVO = bestEvals.get(EvalCriteria.SPEARMAN_C_VO).get(j);
					ComValScorer bestValScorerVO = bestSingleValEvalVO.getValueScorer();
					Map<CompoundSS, Double> bestValScorerVOcomValues = trainCompoundSetWithVals.getComposValuesAssignedByDifferentScorers().get(bestValScorerVO);
					
					SingleValueEvaluation bestSingleValEvalSV = bestEvals.get(EvalCriteria.SPEARMAN_C_SV).get(k);
					ComValScorer bestValScorerSV = bestSingleValEvalSV.getValueScorer();
					Map<CompoundSS, Double> bestValScorerSVcomValues = trainCompoundSetWithVals.getComposValuesAssignedByDifferentScorers().get(bestValScorerSV);
					
					FinalScorerCombined combinedFinalScorer = new FinalScorerCombined(bestSingleValEvalAN, bestValScorerANcomValues,
							bestSingleValEvalVO, bestValScorerVOcomValues, bestSingleValEvalSV, bestValScorerSVcomValues);
					
					finalScorers.get(EvalCriteria.SPEARMAN_C_ALL).add(combinedFinalScorer);
				}
			}
		}
		for (int i = 0; i < numberOfBestCombined; i++) {
			for (int j = 0; j < numberOfBestCombined; j++) {
				for (int k = 0; k < numberOfBestCombined; k++) {
					SingleValueEvaluation bestSingleValEvalAN = bestEvals.get(EvalCriteria.KENDALL_C_AN).get(i);
					ComValScorer bestValScorerAN = bestSingleValEvalAN.getValueScorer();
					Map<CompoundSS, Double> bestValScorerANcomValues = trainCompoundSetWithVals.getComposValuesAssignedByDifferentScorers().get(bestValScorerAN);
					
					SingleValueEvaluation bestSingleValEvalVO = bestEvals.get(EvalCriteria.KENDALL_C_VO).get(j);
					ComValScorer bestValScorerVO = bestSingleValEvalVO.getValueScorer();
					Map<CompoundSS, Double> bestValScorerVOcomValues = trainCompoundSetWithVals.getComposValuesAssignedByDifferentScorers().get(bestValScorerVO);
					
					SingleValueEvaluation bestSingleValEvalSV = bestEvals.get(EvalCriteria.KENDALL_C_SV).get(k);
					ComValScorer bestValScorerSV = bestSingleValEvalSV.getValueScorer();
					Map<CompoundSS, Double> bestValScorerSVcomValues = trainCompoundSetWithVals.getComposValuesAssignedByDifferentScorers().get(bestValScorerSV);
					
					FinalScorerCombined combinedFinalScorer = new FinalScorerCombined(bestSingleValEvalAN, bestValScorerANcomValues,
							bestSingleValEvalVO, bestValScorerVOcomValues, bestSingleValEvalSV, bestValScorerSVcomValues);
					
					finalScorers.get(EvalCriteria.KENDALL_C_ALL).add(combinedFinalScorer);
				}
			}
		}
	}

	private CompoundSet getCompoundSetWithComValues(ArrayList<CompoundSet> compoundSetsWithComValues2, ACLCompoundSetNum dataSet) {
		for (CompoundSet oneCompoundSet: compoundSetsWithComValues2) {
			if (oneCompoundSet.getName().equals(dataSet)) return oneCompoundSet;
		}
		return null;
	}

	private void getAllValueScorersUsed(Map<EvalCriteria, List<SingleValueEvaluation>> bestEvals) {
		allValueScorersUsed = new ArrayList<ComValScorer>();
		for (Map.Entry<EvalCriteria, List<SingleValueEvaluation>> evalCriteriaXbestEvaluations : bestEvals.entrySet()) {
			for (SingleValueEvaluation oneSingleValEval : evalCriteriaXbestEvaluations.getValue()) {
				allValueScorersUsed.add(oneSingleValEval.getValueScorer());
			}
		}
		// only unique scorers..
		Set<ComValScorer> scorers = new HashSet<ComValScorer>(allValueScorersUsed);
		allValueScorersUsed = new ArrayList<ComValScorer>(scorers);
	}

	public FinalResults useFinalScorers(String resultsDir, ArrayList<CompoundSet> compoundSetsWithComValuesTesting, ACLCompoundSetNum testingDataSet,
			EvalCriteria evalCriteria, ResultType resultType) {
		FinalResults finalResults = new FinalResults();
		for (FinalScorer oneFinalScorer : finalScorers.get(evalCriteria)) {
			CompoundSet processedComSet = getCompoundSetWithComValues(compoundSetsWithComValuesTesting, testingDataSet);			
			CompoundSetNumData oneFinalResNum;
			switch(resultType) {
				case VALUES: { oneFinalResNum = oneFinalScorer.getValueScoresFor(processedComSet); break; }
				case ORDERING: { oneFinalResNum = oneFinalScorer.getOrderingScoresFor(processedComSet); break; }
				case NUM: { oneFinalResNum = oneFinalScorer.getNumScoresFor(processedComSet); break; }
				default: { oneFinalResNum = null; break; }
			}
			
			String outputFileNameNum = resultsDir + resultType + "-" + testingDataSet + "-" + oneFinalScorer.getName() + ".txt";
			CompoundsIO.printResultsNumToFile(outputFileNameNum, oneFinalResNum);
			
			CompoundSetCoarseData oneFinalResCoarseBasic = oneFinalScorer.getCoarseScoresFor(processedComSet, CoarseScorerType.BASIC);
			String outputFileNameCoarse = resultsDir + "COARSE_BASIC" + "-" + testingDataSet + "-" + oneFinalScorer.getName() + ".txt";
			CompoundsIO.printResultsCoarseToFile(outputFileNameCoarse, oneFinalResCoarseBasic);
			
			CompoundSetCoarseData oneFinalResCoarseTyped = oneFinalScorer.getCoarseScoresFor(processedComSet, CoarseScorerType.TYPED);
			outputFileNameCoarse = resultsDir + "COARSE_TYPED" + "-" + testingDataSet + "-" + oneFinalScorer.getName() + ".txt";
			CompoundsIO.printResultsCoarseToFile(outputFileNameCoarse, oneFinalResCoarseTyped);
			
			CompoundSetCoarseData oneFinalResCoarseBorders = oneFinalScorer.getCoarseScoresFor(processedComSet, CoarseScorerType.BORDERS);
			outputFileNameCoarse = resultsDir + "COARSE_BORDERS" + "-" + testingDataSet + "-" + oneFinalScorer.getName() + ".txt";
			CompoundsIO.printResultsCoarseToFile(outputFileNameCoarse, oneFinalResCoarseBorders);
			
			OneFinalResult finalRes = new OneFinalResult(resultType, testingDataSet, oneFinalScorer, oneFinalResNum,
					oneFinalResCoarseBasic, oneFinalResCoarseTyped, oneFinalResCoarseBorders);
			finalResults.addOneFinalResult(finalRes);
		}
		return finalResults;
	}
}
