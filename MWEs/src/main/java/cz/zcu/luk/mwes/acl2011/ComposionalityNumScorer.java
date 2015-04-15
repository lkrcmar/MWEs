package cz.zcu.luk.mwes.acl2011;

import java.util.Map;

public abstract class ComposionalityNumScorer {
	public abstract Map<CompoundSS, Double> createNumScores(Map<CompoundSS, Double> valueScoredCompounds);
	
	public abstract String getName();
}
