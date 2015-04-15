package cz.zcu.luk.mwes.acl2011;

import java.util.LinkedHashMap;
import java.util.Map;

public class SingleValueResult {

    private int matchingCompounds;
    private int matchingCompoundsAllACL;
	private double spearmanCAll;
	private double kendallCAll;
	private double spearmanCAdjNoun;
	private double kendallCAdjNoun;
	private double spearmanCVerbObj;
	private double kendallCVerbObj;
	private double spearmanCSubjVerb;
	private double kendallCSubjVerb;
    private double spearmanCNounNoun;
    private double kendallCNounNoun;
    private double spearmanCAllAvg;
    private double averagePrec;
	private Map<EvalCriteria, Double> evalCriteriaToResults;
	private AveragePointDifferenceEval apdEval;
	private CoarseResult basicCoarseResult;
	private CoarseResult typedCoarseResult;
	private CoarseResult bordersCoarseResult;

    private double[] nBestPrecision;
    private double[] nBestRecall;

    public int getMatchingCompounds() {
        return matchingCompounds;
    }
    public int getMatchingCompoundsAllACL() {
        return matchingCompoundsAllACL;
    }
	public double getSpearmanCAll() {
		return spearmanCAll;
	}
	public double getKendallCAll() {
		return kendallCAll;
	}
	public double getSpearmanCAdjNoun() {
		return spearmanCAdjNoun;
	}
	public double getKendallCAdjNoun() {
		return kendallCAdjNoun;
	}
	public double getSpearmanCVerbObj() {
		return spearmanCVerbObj;
	}
	public double getKendallCVerbObj() {
		return kendallCVerbObj;
	}
	public double getSpearmanCSubjVerb() {
		return spearmanCSubjVerb;
	}
	public double getKendallCSubjVerb() {
		return kendallCSubjVerb;
	}
    public double getSpearmanCNounNoun() {
        return spearmanCNounNoun;
    }

    public double getKendallCNounNoun() {
        return kendallCNounNoun;
    }

    public double getAveragePrec() {
        return averagePrec;
    }

    public double getSpearmanCAllAvg() {
        return spearmanCAllAvg;
    }

    public double[] getnBestPrecision() {
        return nBestPrecision;
    }

    public double[] getnBestRecall() {
        return nBestRecall;
    }

	public void setBasicCoarseResult(CoarseResult basicCoarseResult) {
		this.basicCoarseResult = basicCoarseResult;
	}

	public void setTypedCoarseResult(CoarseResult typedCoarseResult) {
		this.typedCoarseResult = typedCoarseResult;
	}
	public void setBordersCoarseResult(CoarseResult bordersCoarseResult) {
		this.bordersCoarseResult = bordersCoarseResult;
	}
	
	public SingleValueResult(int matchingCompounds, int matchingCompoundsAllACL,
                             double spearmanCAll, double kendallCAll,
                             double spearmanCAdjNoun, double kendallCAdjNoun,
                             double spearmanCVerbObj, double kendallCVerbObj,
                             double spearmanCSubjVerb, double kendallCSubjVerb,
                             double spearmanCNounNoun, double kendallCNounNoun,
                             double spearmanCAllAvg,
                             AveragePointDifferenceEval apdEval,
                             double[] nBestPrecision,
                             double[] nBestRec, double averagePrec) {
		this.matchingCompounds = matchingCompounds;
        this.matchingCompoundsAllACL = matchingCompoundsAllACL;
		this.spearmanCAll = spearmanCAll;
		this.kendallCAll = kendallCAll;
		this.spearmanCAdjNoun = spearmanCAdjNoun;
		this.kendallCAdjNoun = kendallCAdjNoun;
		this.spearmanCVerbObj = spearmanCVerbObj;
		this.kendallCVerbObj = kendallCVerbObj;
		this.spearmanCSubjVerb = spearmanCSubjVerb;
		this.kendallCSubjVerb = kendallCSubjVerb;
        this.spearmanCNounNoun = spearmanCNounNoun;
        this.kendallCNounNoun = kendallCNounNoun;
        this.spearmanCAllAvg = spearmanCAllAvg;
		this.apdEval = apdEval;
        this.averagePrec = averagePrec;
		
		this.evalCriteriaToResults = new LinkedHashMap<EvalCriteria, Double>();
		this.evalCriteriaToResults.put(EvalCriteria.SPEARMAN_C_ALL, spearmanCAll);
		this.evalCriteriaToResults.put(EvalCriteria.SPEARMAN_C_AN, spearmanCAdjNoun);
		this.evalCriteriaToResults.put(EvalCriteria.SPEARMAN_C_VO, spearmanCVerbObj);
		this.evalCriteriaToResults.put(EvalCriteria.SPEARMAN_C_SV, spearmanCSubjVerb);
        this.evalCriteriaToResults.put(EvalCriteria.SPEARMAN_C_NN, spearmanCNounNoun);
		this.evalCriteriaToResults.put(EvalCriteria.KENDALL_C_ALL, kendallCAll);
		this.evalCriteriaToResults.put(EvalCriteria.KENDALL_C_AN, kendallCAdjNoun);
		this.evalCriteriaToResults.put(EvalCriteria.KENDALL_C_VO, kendallCVerbObj);
		this.evalCriteriaToResults.put(EvalCriteria.KENDALL_C_SV, kendallCSubjVerb);
        this.evalCriteriaToResults.put(EvalCriteria.KENDALL_C_NN, kendallCNounNoun);
        this.evalCriteriaToResults.put(EvalCriteria.SPEARMAN_C_ALL_AVG, spearmanCAllAvg);
        this.evalCriteriaToResults.put(EvalCriteria.AVERAGE_PREC, averagePrec);

        this.nBestPrecision = nBestPrecision;
        this.nBestRecall = nBestRec;
	}
	
	public String toString() {
		String resultsInString = matchingCompounds + "\t" +
		Common.round(spearmanCAll, Constants.ROUND_DECIMAL_PLACES) + "\t" +
		Common.round(spearmanCAdjNoun, Constants.ROUND_DECIMAL_PLACES) + "\t" +
        Common.round(spearmanCVerbObj, Constants.ROUND_DECIMAL_PLACES) + "\t" +
        Common.round(spearmanCSubjVerb, Constants.ROUND_DECIMAL_PLACES) + "\t" +
        Common.round(spearmanCNounNoun, Constants.ROUND_DECIMAL_PLACES) + "\t" +
        Common.round(kendallCAll, Constants.ROUND_DECIMAL_PLACES) + "\t" +
        Common.round(kendallCAdjNoun, Constants.ROUND_DECIMAL_PLACES) + "\t" +
        Common.round(kendallCVerbObj, Constants.ROUND_DECIMAL_PLACES) + "\t" +
        Common.round(kendallCSubjVerb, Constants.ROUND_DECIMAL_PLACES) + "\t" +
        Common.round(kendallCNounNoun, Constants.ROUND_DECIMAL_PLACES) + "\t" +
        Common.round(spearmanCAllAvg, Constants.ROUND_DECIMAL_PLACES) + "\t" +
        Common.round(averagePrec, Constants.ROUND_DECIMAL_PLACES);

		if (apdEval != null) resultsInString += "\t" + apdEval.toString();
		if (basicCoarseResult != null) resultsInString += "\t" + basicCoarseResult.toString();
		if (typedCoarseResult != null) resultsInString += "\t" + typedCoarseResult.toString();
		if (bordersCoarseResult != null) resultsInString += "\t" + bordersCoarseResult.toString();
		return resultsInString;
	}
	public String toString(EvalCriteria evalCriteria) {
		return (Common.round(this.evalCriteriaToResults.get(evalCriteria), Constants.ROUND_DECIMAL_PLACES) + "");
	}

}
