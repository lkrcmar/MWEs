package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComposionalityNumScorerLinReg extends ComposionalityNumScorer {
	private double regA;
	private double regB;

	public ComposionalityNumScorerLinReg(Map<CompoundSS, Double> compounds,
			ArrayList<CompoundACLNumBean> enTrainNum) {
		LinearRegression lr = new LinearRegression();
		double[] x = new double[compounds.size()];
		double[] y = new double[compounds.size()];
		int i = 0;
		Map<String, Double> trainingData = new HashMap<String, Double>();
		for (CompoundACLNumBean trainingComp: enTrainNum) {
			trainingData.put(trainingComp.getCompound(), trainingComp.getScore());
		}
		for (Map.Entry<CompoundSS, Double> comp: compounds.entrySet()) {
			x[i] = comp.getValue();
			y[i] = trainingData.get(comp.getKey().getCompound());
			i++;
		}
		System.out.println(Arrays.toString(x));
		System.out.println(Arrays.toString(y));
		lr.countRegression(x, y);
		this.regA = lr.getA();
		this.regB = lr.getB();
	}
	
	private double countRegValue(double value) {
		return regA * value + regB;
	}

	public Map<CompoundSS, Double> createNumScores(Map<CompoundSS, Double> valueScoredCompounds) {
		Map<CompoundSS, Double> compoundsValueScoresToNumScores = new LinkedHashMap<CompoundSS, Double>();
		for (Map.Entry<CompoundSS, Double> valueScoredOneCompound: valueScoredCompounds.entrySet()) {
			compoundsValueScoresToNumScores.put(valueScoredOneCompound.getKey(), countRegValue(valueScoredOneCompound.getValue()));
		}
		return compoundsValueScoresToNumScores;
	}

	public String getName() {
		return "LINREGB";
	}
}
