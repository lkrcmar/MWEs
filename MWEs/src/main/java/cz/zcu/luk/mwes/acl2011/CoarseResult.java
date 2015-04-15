package cz.zcu.luk.mwes.acl2011;

public class CoarseResult {
	private int matchingCompounds;
	private double precision;
	private double precisionAN;
	private double precisionVO;
	private double precisionSV;

	public CoarseResult(int matchingCompounds, double precision,
			double precisionAN, double precisionVO, double precisionSV) {
		this.matchingCompounds = matchingCompounds;
		this.precision = precision;
		this.precisionAN = precisionAN;
		this.precisionVO = precisionVO;
		this.precisionSV = precisionSV;
	}
	
	public String toString() {
		return matchingCompounds + "\t" + 
			Common.round(precision, Constants.ROUND_DECIMAL_PLACES) + "\t" + Common.round(precisionAN, Constants.ROUND_DECIMAL_PLACES) + "\t" +
			Common.round(precisionVO, Constants.ROUND_DECIMAL_PLACES) + "\t" + Common.round(precisionSV, Constants.ROUND_DECIMAL_PLACES);
	}
}
