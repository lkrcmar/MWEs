package cz.zcu.luk.mwes.acl2011;


public abstract class FinalScorer {
	
	public abstract String getName();
	public abstract CompoundSetNumData getValueScoresFor(CompoundSet dataSet);
	public abstract CompoundSetNumData getOrderingScoresFor(CompoundSet dataSet);
	public abstract CompoundSetNumData getNumScoresFor(CompoundSet dataSet);
	public abstract CompoundSetCoarseData getCoarseScoresFor(CompoundSet dataSet, CoarseScorerType scorerType);
	
	protected double findValue(double score, Double[] borderVals, Double min, Double max) {
		double result;
		if (score <= borderVals[0]) {
			int position = 0;
			double scoreDif = borderVals[position] - (min);
			if (scoreDif == 0.0) return (double)position + 1;
			double forSubstraction = (score - borderVals[position]) / scoreDif;
			result =  (double)position + forSubstraction;
		}
		else if (score > borderVals[borderVals.length-1]) {
			int position = borderVals.length;
			double scoreDif = max - borderVals[position-1];
			if (scoreDif == 0.0) return (double)position + 1;
			double forSubstraction = (score - max) / scoreDif;
			result =  (double)position + forSubstraction;
		}
		else {
			int position = 0;
			for (int i = 0 ; i < borderVals.length; i++) {
				position = i;
				if (score > borderVals[i]) continue;
				else break;
			}
			double scoreDif = borderVals[position] - borderVals[position-1];
			if (scoreDif == 0.0) return (double)position + 1;
			double forSubstraction = (score - borderVals[position]) / scoreDif;
			result = (double)position + forSubstraction;
		}
		return result + 1;
	}
}
