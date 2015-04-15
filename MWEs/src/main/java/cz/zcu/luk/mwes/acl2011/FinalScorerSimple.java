package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FinalScorerSimple extends FinalScorer {

	private ComValScorer valScorer;
	private CompoundSet trainingDataSet;
	private Map<CompoundSS, Double> trainingComValues;
	// position -> value
	private Double[] borderValues;
	private LinearRegression linReg;
	private ComposionalityCoarseScorerBasic coarseScorerBasic;
	private ComposionalityCoarseScorerTyped coarseScorerTyped;
	private ComposionalityCoarseScorerBorders coarseScorerBorders;

	public FinalScorerSimple(SingleValueEvaluation singleValEvalUsedForTrain, Map<CompoundSS, Double> trainingComValues) {
		// valueScorer simple on which the final scorer was trained..
		this.valScorer = singleValEvalUsedForTrain.getValueScorer();
		// needed for information on which dataSet valScorer was trained and for finding of borders..
		this.trainingDataSet = singleValEvalUsedForTrain.getCompoundSet();
		this.trainingComValues = trainingComValues;
		
		storeBorders();
		prepareLinReg();
		prepareCoarseScorers();
	}

	private void prepareCoarseScorers() {
		CompoundSetCoarseData coarseDataset = CompoundsIO.loadCompoundsCoarseFromFileForGivenNumDataSet(trainingDataSet.getCompoundSetNumData().getName());
		
		Map<CompoundSS, Double> numScoredCompounds = new LinkedHashMap<CompoundSS, Double>();
		for (Map.Entry<CompoundSS, Double> oneCompoundWithValue : trainingComValues.entrySet()) {
			if (coarseDataset.contains(oneCompoundWithValue.getKey().getCompound())) {
				double orderingVal = findValue(oneCompoundWithValue.getValue(), borderValues, Constants.MY_DOUBLE_MIN, Constants.MY_DOUBLE_MAX);
				double numScore = getTrainedScore2(orderingVal);
				numScoredCompounds.put(oneCompoundWithValue.getKey(), numScore);
			}
		}
		if (coarseDataset.getCompounds().size() != numScoredCompounds.size()) {
			System.out.println("Error: Not all coarse compounds used!");
		}
		coarseScorerBasic = new ComposionalityCoarseScorerBasic(numScoredCompounds, coarseDataset.getCompounds());
		coarseScorerTyped = new ComposionalityCoarseScorerTyped(numScoredCompounds, coarseDataset.getCompounds(), trainingDataSet.getCompoundSetNumData());
		coarseScorerBorders = new ComposionalityCoarseScorerBorders();
	}

	private void prepareLinReg() {
		ArrayList<Double> trainOrdering = new ArrayList<Double>();
		for (Double oneBorderVal : borderValues) {
			trainOrdering.add(findValue(oneBorderVal, borderValues, Constants.MY_DOUBLE_MIN, Constants.MY_DOUBLE_MAX));
		}
		Collections.sort(trainOrdering); // should be sorted before.. because borderValues are sorted..
		Double[] trainOrderingArray = (Double[])trainOrdering.toArray(new Double[0]);
		
		List<Double> allValues = new ArrayList<Double>(trainingDataSet.getCompoundSetNumData().getAllScores());
		Collections.sort(allValues);
		Double[] numBorderValues = (Double[])allValues.toArray(new Double[0]);
		//System.out.println(Arrays.toString(numBorderValues));
	//	System.out.println(Arrays.toString(trainOrderingArray));
	//	System.out.println(Arrays.toString(numBorderValues));
		linReg = new LinearRegression();
		linReg.countRegression(trainOrderingArray, numBorderValues);
	}

	// train..
	private void storeBorders() {
		// train final scorer
		List<Double> allValues = new ArrayList<Double>(trainingComValues.values());
		Collections.sort(allValues);
		borderValues = (Double[])allValues.toArray(new Double[0]);
	//	System.out.println(Arrays.toString(borderValues));
	}

	@Override
	public CompoundSetNumData getValueScoresFor(CompoundSet dataSetWithComValues) {
		// only one CompoundSet is scored by values by one ValScorer
		Map<CompoundSS, Double> finalValues = dataSetWithComValues.getComposValuesAssignedByDifferentScorers().get(valScorer);
		return dataSetWithComValues.getCompoundSetNumData().replaceNumValues(finalValues);
	}

	@Override
	public CompoundSetNumData getOrderingScoresFor(CompoundSet dataSetWithComValues) {
		CompoundSetNumData newCompoundSet = getValueScoresFor(dataSetWithComValues);
		for (CompoundACLNumBean oneCompound: newCompoundSet.getCompounds()) {
			double foundValue = findValue(oneCompound.getScore(), borderValues, Constants.MY_DOUBLE_MIN, Constants.MY_DOUBLE_MAX);
			oneCompound.setScore(foundValue);
		}
		return newCompoundSet;
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
		
		double distance = result - 66.39;
		result = 66.39 + (distance * 4) / 5;
		
		return result;
	}

	@Override
	public CompoundSetNumData getNumScoresFor(CompoundSet dataSetWithComValues) {
		CompoundSetNumData newCompoundSet = getValueScoresFor(dataSetWithComValues);
//		ArrayList<Double> originalValues = new ArrayList<Double>();
//		ArrayList<Double> foundValues = new ArrayList<Double>();
//		ArrayList<Double> foundValues2 = new ArrayList<Double>();
		double orderingVal;
		for (CompoundACLNumBean oneCompound: newCompoundSet.getCompounds()) {
			orderingVal = findValue(oneCompound.getScore(), borderValues, Constants.MY_DOUBLE_MIN, Constants.MY_DOUBLE_MAX);
//			originalValues.add(oneCompound.getScore());
//			foundValues.add(getTrainedScore(orderingVal));
//			foundValues2.add(getTrainedScore2(orderingVal));

        // change 12.3.2013!! removed next row and added next next row..
		//	oneCompound.setScore(getTrainedScore2(orderingVal));
            oneCompound.setScore(oneCompound.getScore());
		}
//		Collections.sort(originalValues);
//		Collections.sort(foundValues);
//		Collections.sort(foundValues2);
//		System.out.println("X: " + Arrays.toString(borderValues));
//		System.out.println("Y: " + originalValues);
//		System.out.println("Z: " + foundValues);
//		System.out.println("F: " + foundValues2);
//		System.out.println();
		
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

	@Override
	public String getName() {
		return "FINAL_SIMPLE_" + valScorer.getName();
	}

	@Override
	public String toString() {
		return "FINAL\tSIMPLE\t" + valScorer.toString();
	}
}
