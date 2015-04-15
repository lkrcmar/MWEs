package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ComposionalityNumScorerBasic{} /*extends ComposionalityNumScorer {
	
	
	public ComposionalityNumScorerBasic(Map<Compound, Double> value,
			ArrayList<CompoundACLNumBean> enTrainNum) {
		// TODO Auto-generated constructor stub
	}
	
	public Map<Compound, Double> createNumScores(Map<Compound, Double> valueScoredCompounds) {
		Map<Compound, Double> compoundsValueScoresToNumScores = new LinkedHashMap<Compound, Double>();
		double minValue = findMinValueScoreInSetValues(valueScoredCompounds.entrySet());
		for (Map.Entry<Compound, Double> valueScoredOneCompound: valueScoredCompounds.entrySet()) {
			if(valueScoredOneCompound.getValue() == Constants.MY_DOUBLE_MIN) {
				compoundsValueScoresToNumScores.put(valueScoredOneCompound.getKey(), (minValue/10)*11); // 110% of min value (expcted negative min value..)
			}
			else {
				compoundsValueScoresToNumScores.put(valueScoredOneCompound.getKey(), valueScoredOneCompound.getValue());
			}
		}
		return compoundsValueScoresToNumScores;
	}

	private double findMinValueScoreInSetValues(Set<Entry<Compound, Double>> entrySet) {
		double minValue = Double.MAX_VALUE;
		for (Entry<Compound, Double> e: entrySet) {
			if (e.getValue() < minValue && e.getValue() != Constants.MY_DOUBLE_MIN) minValue = e.getValue();
		}
		return minValue;
	}

	public String getName() {
		return "BASICN";
	}
}*/
