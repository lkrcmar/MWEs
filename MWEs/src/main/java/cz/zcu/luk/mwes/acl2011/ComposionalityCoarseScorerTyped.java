package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ComposionalityCoarseScorerTyped extends ComposionalityCoarseScorer {
    CompoundSetNumData dataSetNum;
	private double lowMediumSplitterJJNN;
	private double mediumHighSplitterJJNN;
	private double lowMediumSplitterVVNN;
	private double mediumHighSplitterVVNN;
	private double lowMediumSplitterNNVV;
	private double mediumHighSplitterNNVV;

	// set ACL compounds for training..
	public ComposionalityCoarseScorerTyped(Map<CompoundSS, Double> valueScoredCompounds,

                                           ArrayList<CompoundACLCoarseBean> trainingCompounds, CompoundSetNumData dataSetNum) {
		super();
        this.dataSetNum = dataSetNum;
		trainScorer(valueScoredCompounds, trainingCompounds);
	}
	
	private double[] getAllScoresTyped(Map<CompoundSS, Double> valueScoredCompounds, ACLCompoundTag tag) {
		// count compounds of given type..
		int countOfCompoundType = 0;
		for (Map.Entry<CompoundSS, Double> compoundValscore: valueScoredCompounds.entrySet()) {
            CompoundACLNumBean aclExp = ExpsMapper.getACLExpression(dataSetNum, compoundValscore.getKey(), -1000.0);
			if (aclExp.getTag() == tag) {
				countOfCompoundType++;
			}
		}
		
		double[] allScores = new double[countOfCompoundType];
		int arrayIndex = 0;
		for (Map.Entry<CompoundSS, Double> compoundValscore: valueScoredCompounds.entrySet()) {
            CompoundACLNumBean aclExp = ExpsMapper.getACLExpression(dataSetNum, compoundValscore.getKey(), -1000.0);
			if (aclExp.getTag() == tag) {
				allScores[arrayIndex] = compoundValscore.getValue();
				arrayIndex++;				
			}
		}
		return allScores;
	}

	private void trainScorerForType(Map<CompoundSS, Double> valueScoredCompounds,
			Map<String, LevelOfComposionality> compoundToLevelOfCom,
			ACLCompoundTag aclCompoundTag) {

		// get all possible borders..
		double[] allScores = getAllScoresTyped(valueScoredCompounds, aclCompoundTag);	
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
		double lowMediumSplitter = Constants.MY_DOUBLE_MIN;
		double mediumHighSplitter = Constants.MY_DOUBLE_MIN;
		int maxCorrectlyAssigned = 0;
		int betterResult;
		for (int i = 0; i < bordersAll.length; i++) {
			for (int j = i; j < bordersAll.length; j++) {
				lowMediumSplitterPot = bordersAll[i];
				mediumHighSplitterPot = bordersAll[j];
				// evaluate suitability of splitters.. if better splitters found.. set them..
				if ((betterResult = evaluateSuitabilityOfSplittersTyped(lowMediumSplitterPot, mediumHighSplitterPot,
						valueScoredCompounds, compoundToLevelOfCom, aclCompoundTag)) > maxCorrectlyAssigned) {
					maxCorrectlyAssigned = betterResult;
					lowMediumSplitter = lowMediumSplitterPot;
					mediumHighSplitter = mediumHighSplitterPot;
					if(Constants.LOG_DETAILED) System.out.print(maxCorrectlyAssigned + " ");
				}
			}
		}
		if(Constants.LOG_DETAILED) System.out.println();
		// assign counted splitters to global values..
		switch (aclCompoundTag) {
			case EN_ADJ_NN: {
				this.lowMediumSplitterJJNN = lowMediumSplitter;
				this.mediumHighSplitterJJNN = mediumHighSplitter;
				break;
			}
			case EN_V_OBJ: {
				this.lowMediumSplitterVVNN = lowMediumSplitter;
				this.mediumHighSplitterVVNN = mediumHighSplitter;
				break;
			}
			case EN_V_SUBJ: {
				this.lowMediumSplitterNNVV = lowMediumSplitter;
				this.mediumHighSplitterNNVV = mediumHighSplitter;
				break;
			}
			default: {
				System.out.println("Error: Unknown ACLCompoundTag in ComposionalityCoarseScorerTyped..");
			}
		}
	}

	private int evaluateSuitabilityOfSplittersTyped(double lowMediumSplitterPot, double mediumHighSplitterPot,
			Map<CompoundSS, Double> valueScoredCompounds, Map<String, LevelOfComposionality> trainingData,
			ACLCompoundTag aclCompoundTag) {
		int correct = 0;
		
		LevelOfComposionality comScorePot;
		for (Map.Entry<CompoundSS, Double> compoundValscore: valueScoredCompounds.entrySet()) {
            CompoundACLNumBean aclExp = ExpsMapper.getACLExpression(dataSetNum, compoundValscore.getKey(), -1000.0);
			if (aclExp.getTag() == aclCompoundTag) {
				// assign coarse score according to suggested borders..
				if (compoundValscore.getValue() < lowMediumSplitterPot) comScorePot = LevelOfComposionality.LOW;
				else if (compoundValscore.getValue() < mediumHighSplitterPot) comScorePot = LevelOfComposionality.MEDIUM;
				else comScorePot = LevelOfComposionality.HIGH;
				// update counts..
				if (comScorePot.equals(trainingData.get(compoundValscore.getKey().getCompound()))) {
					correct++;
				}
			}
		}
		return correct;
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
		
		for (int i = 0; i < ACLCompoundTag.values().length; i++) {
			trainScorerForType(valueScoredCompounds, compoundToLevelOfCom, ACLCompoundTag.values()[i]);
		}
		
		if(Constants.LOG_DETAILED) System.out.println("Trained score LM_JJNN: " + this.lowMediumSplitterJJNN + ", MH_JJNN: " + this.mediumHighSplitterJJNN +
				", LM_VVNN: " + this.mediumHighSplitterVVNN + ", MH_VVNN: " + this.mediumHighSplitterVVNN +
				", LM_NNVV: " + this.mediumHighSplitterNNVV + ", MH_NNVV: " + this.mediumHighSplitterNNVV);
		if(Constants.LOG_DETAILED) System.out.println();
	}

//	/**
//	 * assigns coarse scores to compounds according to borders..
//	 * 
//	 */
//	public Map<Compound, LevelOfComposionality> createCoarseScores(Map<Compound, Double> valueScoredCompounds) {
//		Map<Compound, LevelOfComposionality> compoundsToCoarseScores = new LinkedHashMap<Compound, LevelOfComposionality>();
//		for (Map.Entry<Compound, Double> valueScoredOneCompound: valueScoredCompounds.entrySet()) {
//			double lowMediumSplitter = Constants.MY_DOUBLE_MIN;
//			double mediumHighSplitter = Constants.MY_DOUBLE_MIN;
//			// assign counted splitters to global values..
//			switch (valueScoredOneCompound.getKey().getTag()) {
//				case EN_ADJ_NN: {
//					lowMediumSplitter = this.lowMediumSplitterJJNN;
//					mediumHighSplitter = this.mediumHighSplitterJJNN;
//					break;
//				}
//				case EN_V_OBJ: {
//					lowMediumSplitter = this.lowMediumSplitterVVNN;
//					mediumHighSplitter = this.mediumHighSplitterVVNN;
//					break;
//				}
//				case EN_V_SUBJ: {
//					lowMediumSplitter = this.lowMediumSplitterNNVV;
//					mediumHighSplitter = this.mediumHighSplitterNNVV;
//					break;
//				}
//				default: {
//					System.out.println("Error: Unknown ACLCompoundTag in ComposionalityCoarseScorerTyped..");
//				}
//			}
//			
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
		return "TYPED";
	}
	
	private LevelOfComposionality getLevelOfComposionality(double value, ACLCompoundTag aclCompoundTag) {
		double lowMediumSplitter = Constants.MY_DOUBLE_MIN;
		double mediumHighSplitter = Constants.MY_DOUBLE_MIN;
		// assign counted splitters to global values..
		switch (aclCompoundTag) {
			case EN_ADJ_NN: {
				lowMediumSplitter = this.lowMediumSplitterJJNN;
				mediumHighSplitter = this.mediumHighSplitterJJNN;
				break;
			}
			case EN_V_OBJ: {
				lowMediumSplitter = this.lowMediumSplitterVVNN;
				mediumHighSplitter = this.mediumHighSplitterVVNN;
				break;
			}
			case EN_V_SUBJ: {
				lowMediumSplitter = this.lowMediumSplitterNNVV;
				mediumHighSplitter = this.mediumHighSplitterNNVV;
				break;
			}
			default: {
				System.out.println("Error: Unknown ACLCompoundTag in ComposionalityCoarseScorerTyped..");
			}
		}
		
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

	@Override
	public void createCoarseScores(CompoundSetCoarseData coarseComSet, CompoundSetNumData numComSet) {
		int numberSet = 0; 
		for (CompoundACLCoarseBean oneComCoarse : coarseComSet.getCompounds()) {
			for (CompoundACLNumBean oneComNum : numComSet.getCompounds()) {
				if (oneComNum.getCompound().equals(oneComCoarse.getCompound())) {
					oneComCoarse.setComLevel(getLevelOfComposionality(oneComNum.getScore(), oneComNum.getTag()));
					numberSet++;
				}
			}
		}
		if (numberSet != coarseComSet.getCompounds().size()) {
			System.out.println("Error: not all coarse scores changed!");
		}
	}
}
