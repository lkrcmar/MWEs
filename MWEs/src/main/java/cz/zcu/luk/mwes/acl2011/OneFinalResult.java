package cz.zcu.luk.mwes.acl2011;

public class OneFinalResult {
	private ResultType resultType;
	private FinalScorer oneFinalScorer;
	private CompoundSetNumData oneFinalResNum;
	private CompoundSetCoarseData oneFinalResCoarseBasic;
	private CompoundSetCoarseData oneFinalResCoarseTyped;
	private CompoundSetCoarseData oneFinalResCoarseBorders;

	private ACLCompoundSetNum dataSet;
	
	public ACLCompoundSetNum getDataSet() {
		return dataSet;
	}
	public CompoundSetNumData getOneFinalResNum() {
		return oneFinalResNum;
	}
	public CompoundSetCoarseData getOneFinalResCoarseBasic() {
		return oneFinalResCoarseBasic;
	}
	public CompoundSetCoarseData getOneFinalResCoarseTyped() {
		return oneFinalResCoarseTyped;
	}
	public CompoundSetCoarseData getOneFinalResCoarseBorders() {
		return oneFinalResCoarseBorders;
	}
	
	public OneFinalResult(ResultType resultType, ACLCompoundSetNum dataSet, FinalScorer oneFinalScorer,
			CompoundSetNumData oneFinalResNum, CompoundSetCoarseData oneFinalResCoarseBasic, CompoundSetCoarseData oneFinalResCoarseTyped,
			CompoundSetCoarseData oneFinalResCoarseBorders) {
		this.resultType = resultType;
		this.dataSet = dataSet;
		this.oneFinalScorer = oneFinalScorer;
		this.oneFinalResNum = oneFinalResNum;
		this.oneFinalResCoarseBasic = oneFinalResCoarseBasic;
		this.oneFinalResCoarseTyped = oneFinalResCoarseTyped;
		this.oneFinalResCoarseBorders = oneFinalResCoarseBorders;
	}
	
	@Override
	public String toString() {
		return resultType + "\t" + dataSet + "\t" + oneFinalScorer.toString();
	}
}
