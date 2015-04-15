package cz.zcu.luk.mwes.acl2011;

import java.util.*;

public class FinalScorerCombined extends FinalScorer {

	private ComValScorer valScorerAN;
	private ComValScorer valScorerVO;
	private ComValScorer valScorerSV;
	private CompoundSet trainingDataSetAN;
	private CompoundSet trainingDataSetVO;
	private CompoundSet trainingDataSetSV;
	private Map<CompoundSS, Double> trainingComValuesAN;
	private Map<CompoundSS, Double> trainingComValuesVO;
	private Map<CompoundSS, Double> trainingComValuesSV;
	private Double[] borderValuesAN;
	private Double[] borderValuesVO;
	private Double[] borderValuesSV;
	// final borders are used because border values are just for 1.0 2.0 3.0... and not for 1.0 3.0 7.0..
	private Double[] finalBordersAN;
	private Double[] finalBordersVO;
	private Double[] finalBordersSV;
	private LinearRegression linReg;
	private ComposionalityCoarseScorerBasic coarseScorerBasic;
	private ComposionalityCoarseScorerTyped coarseScorerTyped;
	private ComposionalityCoarseScorerBorders coarseScorerBorders;
	
	public FinalScorerCombined(SingleValueEvaluation bestSingleValEvalAN, Map<CompoundSS, Double> bestValScorerANcomValues,
			SingleValueEvaluation bestSingleValEvalVO, Map<CompoundSS, Double> bestValScorerVOcomValues,
			SingleValueEvaluation bestSingleValEvalSV, Map<CompoundSS, Double> bestValScorerSVcomValues) {
		this.valScorerAN = bestSingleValEvalAN.getValueScorer();
		this.valScorerVO = bestSingleValEvalVO.getValueScorer();
		this.valScorerSV = bestSingleValEvalSV.getValueScorer();
		this.trainingDataSetAN = bestSingleValEvalAN.getCompoundSet();
		this.trainingDataSetVO = bestSingleValEvalVO.getCompoundSet();
		this.trainingDataSetSV = bestSingleValEvalSV.getCompoundSet();
		this.trainingComValuesAN = bestValScorerANcomValues;
		this.trainingComValuesVO = bestValScorerVOcomValues;
		this.trainingComValuesSV = bestValScorerSVcomValues;
		storeBorders();
		storeFinalBorders();
		prepareLinReg();
		prepareCoarseScorers();
	}
	
	private void prepareCoarseScorers() {
		CompoundSetCoarseData coarseDataset = CompoundsIO.loadCompoundsCoarseFromFileForGivenNumDataSet(trainingDataSetAN.getCompoundSetNumData().getName());
		Map<CompoundSS, Double> finalValuesAN = trainingComValuesAN;
		Map<CompoundSS, Double> finalValuesVO = trainingComValuesVO;
		Map<CompoundSS, Double> finalValuesSV = trainingComValuesSV;
		Map<CompoundSS, Double> finalValues = new LinkedHashMap<CompoundSS, Double>();
		
		Map<CompoundSS, Double> numScoredCompounds = new LinkedHashMap<CompoundSS, Double>();
		for (Map.Entry<CompoundSS, Double> oneCompoundWithValue : trainingComValuesAN.entrySet()) {
			if (coarseDataset.contains(oneCompoundWithValue.getKey().getCompound())) {
				CompoundSS compound = oneCompoundWithValue.getKey();
				double valScore = Constants.MY_DOUBLE_MIN * 2;
                CompoundACLNumBean aclExp = ExpsMapper.getACLExpression(trainingDataSetAN.getCompoundSetNumData(), compound, -1000.0);
				switch (aclExp.getTag()) {
					case EN_ADJ_NN : { valScore = finalValuesAN.get(compound); break; }
					case EN_V_OBJ : { valScore = finalValuesVO.get(compound); break; }
					case EN_V_SUBJ : { valScore = finalValuesSV.get(compound); break; }
					default: System.out.println("Error: compound match not found!");
				}
				double orderingVal = Constants.MY_DOUBLE_MIN * 2;
				switch (aclExp.getTag()) {
					case EN_ADJ_NN : { 
						double typedOrdering = findValue(valScore, borderValuesAN, Constants.MY_DOUBLE_MIN, Constants.MY_DOUBLE_MAX);
						orderingVal = getFinalScore(typedOrdering, ACLCompoundTag.EN_ADJ_NN);
						break;
					}
					case EN_V_OBJ : { 
						double typedOrdering = findValue(valScore, borderValuesVO, Constants.MY_DOUBLE_MIN, Constants.MY_DOUBLE_MAX);
						orderingVal = getFinalScore(typedOrdering, ACLCompoundTag.EN_V_OBJ);
						break;
					}
					case EN_V_SUBJ : {
						double typedOrdering = findValue(valScore, borderValuesSV, Constants.MY_DOUBLE_MIN, Constants.MY_DOUBLE_MAX);
						orderingVal = getFinalScore(typedOrdering, ACLCompoundTag.EN_V_SUBJ);
						break;
					}
					default: System.out.println("Error: compound match not found!");
				}
				double numScore = getTrainedScore2(orderingVal);
				numScoredCompounds.put(oneCompoundWithValue.getKey(), numScore);
			}
		}
		if (coarseDataset.getCompounds().size() != numScoredCompounds.size()) {
			System.out.println("Error: Not all coarse compounds used!");
		}
		coarseScorerBasic = new ComposionalityCoarseScorerBasic(numScoredCompounds, coarseDataset.getCompounds());
		coarseScorerTyped = new ComposionalityCoarseScorerTyped(numScoredCompounds, coarseDataset.getCompounds(), trainingDataSetAN.getCompoundSetNumData());
		coarseScorerBorders = new ComposionalityCoarseScorerBorders();
	}
	
	private void prepareLinReg() {
		CompoundSetNumData trainingDSwithOrdering = getOrderingScoresFor(trainingDataSetAN);
		List<Double> allTrainingScores = trainingDSwithOrdering.getAllScores();
		Collections.sort(allTrainingScores);
		Double[] allTrainingScoresArray = (Double[])allTrainingScores.toArray(new Double[0]);
		
		List<Double> allValues = new ArrayList<Double>(trainingDataSetAN.getCompoundSetNumData().getAllScores());
		Collections.sort(allValues);
		Double[] numBorderValues = (Double[])allValues.toArray(new Double[0]);
		//System.out.println(Arrays.toString(numBorderValues));
	//	System.out.println(Arrays.toString(trainOrderingArray));
	//	System.out.println(Arrays.toString(numBorderValues));
		linReg = new LinearRegression();
		linReg.countRegression(allTrainingScoresArray, numBorderValues);
	}

	private Map<CompoundSS, Double> retainOnlyOneType(Map<CompoundSS, Double> finalValuesAN,
			ACLCompoundTag tag) {
		Map<CompoundSS, Double> filteredMap = new LinkedHashMap<CompoundSS, Double>();
		for (Map.Entry<CompoundSS, Double> oneComWithVal : finalValuesAN.entrySet()) {
            CompoundACLNumBean aclExp = ExpsMapper.getACLExpression(trainingDataSetAN.getCompoundSetNumData(), oneComWithVal.getKey(), -1000.0);
			if (aclExp.getTag().equals(tag)) {
				filteredMap.put(oneComWithVal.getKey(), oneComWithVal.getValue());
			}
		}
		return filteredMap;
	}
	
	private void storeBorders() {
		// train final scorer
		List<Double> allValuesAN = new ArrayList<Double>(retainOnlyOneType(trainingComValuesAN, ACLCompoundTag.EN_ADJ_NN).values());
		Collections.sort(allValuesAN);
		borderValuesAN = (Double[])allValuesAN.toArray(new Double[0]);
//		System.out.println(Arrays.toString(borderValuesAN));
		List<Double> allValuesVO = new ArrayList<Double>(retainOnlyOneType(trainingComValuesVO, ACLCompoundTag.EN_V_OBJ).values());
		Collections.sort(allValuesVO);
		borderValuesVO = (Double[])allValuesVO.toArray(new Double[0]);
//		System.out.println(Arrays.toString(borderValuesVO));
		List<Double> allValuesSV = new ArrayList<Double>(retainOnlyOneType(trainingComValuesSV, ACLCompoundTag.EN_V_SUBJ).values());
		Collections.sort(allValuesSV);
		borderValuesSV = (Double[])allValuesSV.toArray(new Double[0]);
//		System.out.println(Arrays.toString(borderValuesSV));
	}

	private void storeFinalBorders() {
		List<CompoundACLNumBean> copyOfCompoundsData = new ArrayList<CompoundACLNumBean>(trainingDataSetAN.getCompoundSetNumData().getCompounds());
		Collections.sort(copyOfCompoundsData, Constants.COMPARATOR_SCORE_ACL_COMPOUNDBEAN);
		
		ArrayList<Double> finalBordersANList = new ArrayList<Double>();
		ArrayList<Double> finalBordersVOList = new ArrayList<Double>();
		ArrayList<Double> finalBordersSVList = new ArrayList<Double>();
		finalBordersANList.add(0.0);
		finalBordersVOList.add(0.0);
		finalBordersSVList.add(0.0);
		
		for (int i = 0; i < copyOfCompoundsData.size(); i++) {
			switch (copyOfCompoundsData.get(i).getTag()) {
				case EN_ADJ_NN : { finalBordersANList.add((double)(i+1)); break; }
				case EN_V_OBJ : { finalBordersVOList.add((double)(i+1)); break; }
				case EN_V_SUBJ : { finalBordersSVList.add((double)(i+1)); break; }
				default: System.out.println("Error: in storeFinalBorders()!");
			}
		}
		
		finalBordersANList.add((double)copyOfCompoundsData.size() + 1);
		finalBordersVOList.add((double)copyOfCompoundsData.size() + 1);
		finalBordersSVList.add((double)copyOfCompoundsData.size() + 1);
		
		Collections.sort(finalBordersANList);
		Collections.sort(finalBordersVOList);
		Collections.sort(finalBordersSVList);
		
		finalBordersAN = (Double[])finalBordersANList.toArray(new Double[0]);
		finalBordersVO = (Double[])finalBordersVOList.toArray(new Double[0]);
		finalBordersSV = (Double[])finalBordersSVList.toArray(new Double[0]);
		
//		System.out.println("AN " + Arrays.toString(finalBordersAN));
//		System.out.println("VO " + Arrays.toString(finalBordersVO));
//		System.out.println("SV " + Arrays.toString(finalBordersSV));
	}

	@Override
	public String getName() {
		return "COMBINED_AN_" + valScorerAN.getName() + "_VO_" + valScorerVO.getName() + "_SV_" + valScorerSV.getName();
	}
	
	@Override
	public String toString() {
		return "FINAL\tCOMBINED\t" + valScorerAN.getName() + "\t" + valScorerVO.getName() + "\t" + valScorerSV.getName();
	}

	@Override
	public CompoundSetNumData getValueScoresFor(CompoundSet dataSetWithComValues) {
		Map<CompoundSS, Double> finalValuesAN = dataSetWithComValues.getComposValuesAssignedByDifferentScorers().get(valScorerAN);
		Map<CompoundSS, Double> finalValuesVO = dataSetWithComValues.getComposValuesAssignedByDifferentScorers().get(valScorerVO);
		Map<CompoundSS, Double> finalValuesSV = dataSetWithComValues.getComposValuesAssignedByDifferentScorers().get(valScorerSV);
		Map<CompoundSS, Double> finalValues = new LinkedHashMap<CompoundSS, Double>();
		for (CompoundSS compound: dataSetWithComValues.getCompounds()) {
            CompoundACLNumBean aclExp = ExpsMapper.getACLExpression(trainingDataSetAN.getCompoundSetNumData(), compound, -1000.0);
			switch (aclExp.getTag()) {
				case EN_ADJ_NN : { finalValues.put(compound, finalValuesAN.get(compound)); break; }
				case EN_V_OBJ : { finalValues.put(compound, finalValuesVO.get(compound)); break; }
				case EN_V_SUBJ : { finalValues.put(compound, finalValuesSV.get(compound)); break; }
				default: System.out.println("Error: compound match not found!");
			}
		}
		return dataSetWithComValues.getCompoundSetNumData().replaceNumValues(finalValues);
	}
	
	@Override
	public CompoundSetNumData getOrderingScoresFor(CompoundSet dataSetWithComValues) {
		CompoundSetNumData newCompoundSet = getValueScoresFor(dataSetWithComValues);
		for (CompoundACLNumBean oneCompound: newCompoundSet.getCompounds()) {
			switch (oneCompound.getTag()) {
			case EN_ADJ_NN : { 
				double typedOrdering = findValue(oneCompound.getScore(), borderValuesAN, Constants.MY_DOUBLE_MIN, Constants.MY_DOUBLE_MAX);
				oneCompound.setScore(getFinalScore(typedOrdering, ACLCompoundTag.EN_ADJ_NN));
				break;
			}
			case EN_V_OBJ : { 
				double typedOrdering = findValue(oneCompound.getScore(), borderValuesVO, Constants.MY_DOUBLE_MIN, Constants.MY_DOUBLE_MAX);
				oneCompound.setScore(getFinalScore(typedOrdering, ACLCompoundTag.EN_V_OBJ));
				break;
			}
			case EN_V_SUBJ : {
				double typedOrdering = findValue(oneCompound.getScore(), borderValuesSV, Constants.MY_DOUBLE_MIN, Constants.MY_DOUBLE_MAX);
//				System.out.println(typedOrdering + "   " + oneCompound.getScore() + "    "+ Arrays.toString(borderValuesSV));
				oneCompound.setScore(getFinalScore(typedOrdering, ACLCompoundTag.EN_V_SUBJ));
				break;
			}
			default: System.out.println("Error: compound match not found!");
			}
		}
		return newCompoundSet;
	}
	
	private double getWeightedMean(Double[] borderValues, int position, double rest) {
//		System.out.println(position + "  " + rest);
		if (position >= borderValues.length - 1) return Constants.MY_DOUBLE_MAX; // avoids this: 0.0 score for compound -> array out of bound..
		double result = borderValues[position] * (1.0 - rest) + borderValues[position + 1] * rest;
	//	System.out.println("A: " + borderValues[position] + ", B: " + (1.0 - rest) + ", C: " + borderValues[position + 1] + ", D: " + rest + ", E: " + result);
		return result;
	}

	private double getFinalScore(double typedOrdering, ACLCompoundTag tag) {
		int position = (int)(typedOrdering);
		double rest = typedOrdering - (double)(position);
		switch (tag) {
			case EN_ADJ_NN : return getWeightedMean(finalBordersAN, position, rest);
			case EN_V_OBJ : return getWeightedMean(finalBordersVO, position, rest);
			case EN_V_SUBJ : return getWeightedMean(finalBordersSV, position, rest);
			default: {
				System.out.println("Error in getFinalScore()");
				return 0;
			}
		}
	}
	
//	private double getTrainedScore(double value) {
//		return linReg.getA() * value + linReg.getB();
//	}
	
	private double getTrainedScore2(double value) {
		double a = 7.301;
		double b = 0.506;
		double result = a*Math.pow(value, b);
		if (result > Constants.NUM_VALUE_MAX) result = Constants.NUM_VALUE_MAX;
		if (result < Constants.NUM_VALUE_MIN) result = Constants.NUM_VALUE_MIN;
		
//		if (result > 66.39 + 5.0) result = result - 5.0;
//		else if (result < 66.39 - 5.0) result = result + 5.0;
//		else result = 66.39;
		
		double distance = result - 66.39;
		result = 66.39 + (distance * 4) / 5;
		
		return result;
	}

	@Override
	public CompoundSetNumData getNumScoresFor(CompoundSet dataSetWithComValues) {
		CompoundSetNumData newCompoundSet = getOrderingScoresFor(dataSetWithComValues);
		for (CompoundACLNumBean oneCompound: newCompoundSet.getCompounds()) {
			oneCompound.setScore(getTrainedScore2(oneCompound.getScore()));
		}
		return newCompoundSet;
	}

	@Override
	public CompoundSetCoarseData getCoarseScoresFor(CompoundSet dataSetWithComValues, CoarseScorerType scorerType) {
		CompoundSetNumData numComSet = getNumScoresFor(dataSetWithComValues);
		CompoundSetCoarseData coarseComSet = CompoundsIO.loadCompoundsCoarseFromFileForGivenNumDataSet(dataSetWithComValues.getCompoundSetNumData().getName());
		switch (scorerType) {
			case BASIC: {
				coarseScorerBasic.createCoarseScores(coarseComSet, numComSet);
				break;
			}
			case TYPED: {
				coarseScorerTyped.createCoarseScores(coarseComSet, numComSet);
				break;
			}
			case BORDERS: {
				coarseScorerBorders.createCoarseScores(coarseComSet, numComSet);
				break;
			}
		}
		
		return coarseComSet;
	}
}
