package cz.zcu.luk.mwes.acl2011;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResultsComplete {
	private Map<GeneralPars, ValueEvaluations> valEvalsToOneGenPars;
	
	public ResultsComplete() {
		this.valEvalsToOneGenPars = new LinkedHashMap<GeneralPars, ValueEvaluations>();
	}

	public void add(GeneralPars oneGenPars, ValueEvaluations valEvals) {
		this.valEvalsToOneGenPars.put(oneGenPars, valEvals);
	}

//	public void print(String outputFileName, ACLCompoundSetNum dataset) {
//		PrintWriter pw;
//		try {
//			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
//			// for every general Pars..
//			for (Map.Entry<GeneralPars, ValueEvaluations> oneGenParsXvalEvals : valEvalsToOneGenPars.entrySet()) {
//				// for Every Eval Criteria
//				for (EvalCriteria oneEvalCriteria : EvalCriteria.values()) {
//					// create map com val kind -> best results
//					Map<String, List<SingleValueEvaluation>> comValScoreKindToBestResults =
//						oneGenParsXvalEvals.getValue().getBestSingleEvaluationsKinded(Constants.RESULTS_COMPLETE_PRINTED, dataset, oneEvalCriteria);
//					for (Map.Entry<String, List<SingleValueEvaluation>> oneCVSKxBSVE : comValScoreKindToBestResults.entrySet()) {
//						// print head.. general pars, eval criteria..
//						// print best results (com val kind.. inside..)
//						for (SingleValueEvaluation oneSingleValEval : oneCVSKxBSVE.getValue()) {
//							StringBuilder printed = new StringBuilder();
//							printed.append(oneGenParsXvalEvals.getKey().toStringTab()).append("\t");
//							printed.append(oneEvalCriteria.toString()).append("\t");
//							printed.append(oneSingleValEval.toString(oneEvalCriteria));
//							pw.println(printed.toString());
//						}
//					}
//				}
//			}
//			pw.close();
//		}
//		catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
