package cz.zcu.luk.mwes.acl2011;

import java.util.Map;

public class AveragePointDifferenceEval {
	private double apdAll;
	private double apdAN;
	private double apdVO;
	private double apdSV;
    private double apdNN;
	
	public AveragePointDifferenceEval(double apdAll, double apdAN,
			double apdVO, double apdSV, double apdNN) {
		this.apdAll = apdAll;
		this.apdAN = apdAN;
		this.apdVO = apdVO;
		this.apdSV = apdSV;
        this.apdNN = apdNN;
	}

    public static double getAPD(Map<String, Double> humanJudgementsBasicTypes, Map<String, Double> computerJudgementsBasicTypes) {
		double differenceSum = 0.0;
		int count = 0;
		for (Map.Entry<String, Double> oneHumanJudgement : humanJudgementsBasicTypes.entrySet()) {
			differenceSum += Math.abs(oneHumanJudgement.getValue() - computerJudgementsBasicTypes.get(oneHumanJudgement.getKey()));
			count++;
		}
		return (differenceSum / (double)count);
	}

	@Override
	public String toString() {
		String results = Common.round(apdAll, Constants.ROUND_DECIMAL_PLACES) + "\t" + Common.round(apdAN, Constants.ROUND_DECIMAL_PLACES) +
		"\t" + Common.round(apdVO, Constants.ROUND_DECIMAL_PLACES) + "\t" + Common.round(apdSV, Constants.ROUND_DECIMAL_PLACES) +
                "\t" + Common.round(apdNN, Constants.ROUND_DECIMAL_PLACES);
		return results;
	}
	
	
}
