package cz.zcu.luk.mwes.acl2011;

public class SingleValueEvaluation {
	private CompoundSet compoundSet;
	private ComValScorer valueScorer;
	private SingleValueResult singleValueResult;
	
	public CompoundSet getCompoundSet() {
		return compoundSet;
	}
	
	public ComValScorer getValueScorer() {
		return valueScorer;
	}
	
	public SingleValueResult getSingleValueResult() {
		return singleValueResult;
	}

	SingleValueEvaluation(CompoundSet compoundSet, ComValScorer valueScorer, SingleValueResult singleValueResult) {
		this.compoundSet = compoundSet;
		this.valueScorer =  valueScorer;
		this.singleValueResult = singleValueResult;
	}
	
	public String toString() {
		return compoundSet.getName() + "\t" + valueScorer.toString()  + "\t" + singleValueResult.toString();
	}

	public String toString(EvalCriteria evalCriteria) {
		return compoundSet.getName() + "\t" + valueScorer.toString()  + "\t" + singleValueResult.toString(evalCriteria);
	}
}
