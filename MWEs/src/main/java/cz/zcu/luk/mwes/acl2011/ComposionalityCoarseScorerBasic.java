package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ComposionalityCoarseScorerBasic extends ComposionalityCoarseScorer {
	private double lowMediumSplitter;
	private double mediumHighSplitter;
	
	// set ACL compounds for training..
	public ComposionalityCoarseScorerBasic(Map<CompoundSS, Double> valueScoredCompounds, ArrayList<CompoundACLCoarseBean> trainingCompounds) {
		super();
		trainScorer(valueScoredCompounds, trainingCompounds);
	}

	/**
	 *  finds borders between low, medium and high composionality compounds
	 *  according to training data
	 */
	private void trainScorer(Map<CompoundSS, Double> valueScoredCompounds, ArrayList<CompoundACLCoarseBean> trainingCompounds) {
		
		// create map from training data.. compound ACL -> level of composionality..
		Map<String, LevelOfComposionality> compoundToLevelOfCom = new HashMap<String, LevelOfComposionality>();
		for (CompoundACLCoarseBean comACL: trainingCompounds) {
			compoundToLevelOfCom.put(comACL.getCompound(), comACL.getComLevel());
		}
		
		// get all possible borders..
		double[] allScores = new double[valueScoredCompounds.size()];
		int arrayIndex = 0;
		for (Map.Entry<CompoundSS, Double> compoundValscore: valueScoredCompounds.entrySet()) {
			allScores[arrayIndex] = compoundValscore.getValue();
			arrayIndex++;
		}
		Arrays.sort(allScores);
		double[] bordersAll = new double[allScores.length + 1];
		// add first and last potential borders..
		bordersAll[0] = Constants.MY_DOUBLE_MIN;
		bordersAll[bordersAll.length - 1] = Constants.MY_DOUBLE_MAX;
		// add middle potential borders..
		for (int i = 0; i < allScores.length - 1; i++) {
			bordersAll[i+1] = (allScores[i] + allScores[i+1]) / 2;
			// !!! now border assigned in the other way.. no average but the higher score!..
			bordersAll[i+1] = (allScores[i+1]);
		}
		
		// find the best borders according to the given training data..
		if(Constants.LOG_DETAILED) System.out.println(Arrays.toString(allScores));
		if(Constants.LOG_DETAILED) System.out.println(Arrays.toString(bordersAll));
		//System.out.println(bordersDoubles.toString());
		// try all possible borders and remember the best splitters..
		double lowMediumSplitterPot;
		double mediumHighSplitterPot;
		int maxCorrectlyAssigned = 0;
		int betterResult;
		for (int i = 0; i < bordersAll.length; i++) {
			for (int j = i; j < bordersAll.length; j++) {
				lowMediumSplitterPot = bordersAll[i];
				mediumHighSplitterPot = bordersAll[j];
				// evaluate suitability of splitters.. if better splitters found.. set them..
				if ((betterResult = evaluateSuitabilityOfSplitters(lowMediumSplitterPot, mediumHighSplitterPot, valueScoredCompounds, compoundToLevelOfCom)) > maxCorrectlyAssigned) {
					maxCorrectlyAssigned = betterResult;
					this.lowMediumSplitter = lowMediumSplitterPot;
					this.mediumHighSplitter = mediumHighSplitterPot;
					if(Constants.LOG_DETAILED) System.out.print(maxCorrectlyAssigned + " ");
				}
			}
		}
		if(Constants.LOG_DETAILED) System.out.println();
		if(Constants.LOG_DETAILED) System.out.println("Trained score LM: " + this.lowMediumSplitter + ", trained score MH: " + this.mediumHighSplitter);
	}

	private int evaluateSuitabilityOfSplitters(double lowMediumSplitterPot, double mediumHighSplitterPot,
			Map<CompoundSS, Double> valueScoredCompounds, Map<String, LevelOfComposionality> trainingData) {
		int correct = 0;
		
		LevelOfComposionality comScorePot;
		for (Map.Entry<CompoundSS, Double> compoundValscore: valueScoredCompounds.entrySet()) {
			// assign coarse score according to suggested borders..
			if (compoundValscore.getValue() < lowMediumSplitterPot) comScorePot = LevelOfComposionality.LOW;
			else if (compoundValscore.getValue() < mediumHighSplitterPot) comScorePot = LevelOfComposionality.MEDIUM;
			else comScorePot = LevelOfComposionality.HIGH;
			// update counts..
			if (comScorePot.equals(trainingData.get(compoundValscore.getKey().getCompound()))) {
				correct++;
			}
		}
		return correct;
	}
	
//	/**
//	 * assigns coarse scores to compounds according to borders..
//	 * 
//	 */
//	public Map<Compound, LevelOfComposionality> createCoarseScores(Map<Compound, Double> valueScoredCompounds) {
//		Map<Compound, LevelOfComposionality> compoundsToCoarseScores = new LinkedHashMap<Compound, LevelOfComposionality>();
//		for (Map.Entry<Compound, Double> valueScoredOneCompound: valueScoredCompounds.entrySet()) {
//			compoundsToCoarseScores.put(valueScoredOneCompound.getKey(), getLevelOfComposionality(valueScoredOneCompound.getValue()));
//		}
//		return compoundsToCoarseScores;
//	}
	
	private LevelOfComposionality getLevelOfComposionality(double value) {
		if (value < lowMediumSplitter) {
			return LevelOfComposionality.LOW;
		}
		else if (value < mediumHighSplitter) {
			return LevelOfComposionality.MEDIUM;
		}
		else {
			return LevelOfComposionality.HIGH;
		}
	}

	public void createCoarseScores(CompoundSetCoarseData coarseComSet, CompoundSetNumData numComSet) {
		int numberSet = 0; 
		for (CompoundACLCoarseBean oneComCoarse : coarseComSet.getCompounds()) {
			for (CompoundACLNumBean oneComNum : numComSet.getCompounds()) {
				if (oneComNum.getCompound().equals(oneComCoarse.getCompound())) {
					oneComCoarse.setComLevel(getLevelOfComposionality(oneComNum.getScore()));
					numberSet++;
				}
			}
		}
		if (numberSet != coarseComSet.getCompounds().size()) {
			System.out.println("Error: not all coarse scores changed!");
		}
	}

//	/**
//	 * assigns coarse scores to compounds according to borders..
//	 * 
//	 */
//	public Map<Compound, LevelOfComposionality> createCoarseScores(Map<Compound, Double> valueScoredCompounds) {
//		Map<Compound, LevelOfComposionality> compoundsToCoarseScores = new LinkedHashMap<Compound, LevelOfComposionality>();
//		for (Map.Entry<Compound, Double> valueScoredOneCompound: valueScoredCompounds.entrySet()) {
//			if (valueScoredOneCompound.getValue() < lowMediumSplitter) {
//				compoundsToCoarseScores.put(valueScoredOneCompound.getKey(), LevelOfComposionality.LOW);
//			}
//			else if (valueScoredOneCompound.getValue() < mediumHighSplitter) {
//				compoundsToCoarseScores.put(valueScoredOneCompound.getKey(), LevelOfComposionality.MEDIUM);
//			}
//			else {
//				compoundsToCoarseScores.put(valueScoredOneCompound.getKey(), LevelOfComposionality.HIGH);
//			}
//		}
//		return compoundsToCoarseScores;
//	}

	@Override
	public String getName() {
		return "BASIC";
	}

}
