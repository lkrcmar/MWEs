package cz.zcu.luk.mwes.acl2011;

import jsc.correlation.KendallCorrelation;
import jsc.correlation.SpearmanCorrelation;
import jsc.datastructures.PairedData;

import java.io.*;
import java.util.*;

import cz.zcu.luk.sspace.util.MapUtil;

public class ResultsEvaluator {
	CompoundSetsHandler comSetsHandler;
    private int collocationCount;

	private Map<ACLCompoundSetCoarse, Map<String, CompoundACLCoarseBean>> humanCoarseJudgements;
	//private Map<String, Map<String, CompoundACLCoarseBean>> computerCoarseJudgements;
	//private Map<String, Map<String, CompoundACLNumBean>> computerNumJudgements;
	private ArrayList<String> resultsCoarseExcel;
	private ArrayList<String> resultsNumExcel;

	public ResultsEvaluator(ACLCompoundSetNum datasetEvaluated) {
		comSetsHandler = new CompoundSetsHandler();
        collocationCount = Constants.getCollocationCount(datasetEvaluated);

		//prepareHumanCoarseJudgements();
	}
	
	private Map<String, CompoundACLCoarseBean> readCoarseJudgementsFromMemory(CompoundSetCoarseData dataInMemory) {
		Map<String, CompoundACLCoarseBean> composCoarsejudgements = new LinkedHashMap<String, CompoundACLCoarseBean>();
		for (CompoundACLCoarseBean comCoarseBean : dataInMemory.getCompounds()) {
			composCoarsejudgements.put(comCoarseBean.getCompound(), comCoarseBean);
		}
		return composCoarsejudgements;
	}
	
	private void prepareHumanCoarseJudgements() {
		humanCoarseJudgements = new LinkedHashMap<ACLCompoundSetCoarse, Map<String,CompoundACLCoarseBean>>();
		ACLCompoundSetCoarse[] coarseSets = ACLCompoundSetCoarse.values();
		for (ACLCompoundSetCoarse oneCoarseComSet : coarseSets) {
			CompoundSetCoarseData comSetCoarseData = CompoundsIO.loadCompoundsCoarseFromFile(oneCoarseComSet);
			humanCoarseJudgements.put(oneCoarseComSet, readCoarseJudgementsFromMemory(comSetCoarseData));
		}
	}
	
	private CoarseResult compareCoarseJudgements(Map<String, CompoundACLCoarseBean> computerJudgements,
			Map<String, CompoundACLCoarseBean> humanJudgements) {
		int matchingCompounds = 0;
		int correct = 0;
		int correctAN = 0;
		int correctVO = 0;
		int correctSV = 0;
		int tagANCount = 0;
		int tagVOCount = 0;
		int tagSVCount = 0;
		CompoundACLCoarseBean computerJudgement;
		for (Map.Entry<String, CompoundACLCoarseBean> oneHumanJudgement : humanJudgements.entrySet()) {
			if ((computerJudgement = computerJudgements.get(oneHumanJudgement.getKey())) != null) {
				if (computerJudgement.getTag() == oneHumanJudgement.getValue().getTag()) {
					matchingCompounds++;
					switch (computerJudgement.getTag()) {
						case EN_ADJ_NN: {
							tagANCount++; break;
						}
						case EN_V_OBJ: {
							tagVOCount++; break;
						}
						case EN_V_SUBJ: {
							tagSVCount++; break;
						}
						default: {
							System.out.println("Error in compareJudgements..");
						}
					}
					if (computerJudgement.getComLevel() == (oneHumanJudgement.getValue().getComLevel())) {
						correct++;
						switch (computerJudgement.getTag()) {
							case EN_ADJ_NN: {
								correctAN++; break;
							}
							case EN_V_OBJ: {
								correctVO++; break;
							}
							case EN_V_SUBJ: {
								correctSV++; break;
							}
							default: {
								System.out.println("Error in compareJudgements..");
							}
						}
					}
				}
				else {
					System.out.println("Error: in compareJudgements.. not same compounds' tags..");
				}
			} 
		}
		double precision = (double)correct / (double)matchingCompounds;
		double precisionJJNN = (double)correctAN / (double)tagANCount;
		double precisionVVNN = (double)correctVO / (double)tagVOCount;
		double precisionNNVV = (double)correctSV / (double)tagSVCount;
		//resultsCoarse.put(computerJudgementsName, "matching compounds: " + machingCompounds + ", precision: " + precision);
		CoarseResult coarseRes = new CoarseResult(matchingCompounds, precision, precisionJJNN, precisionVVNN, precisionNNVV);
		return coarseRes;
	}
	
	private Map<String, CompoundACLNumBean> readNumJudgementsFromListOfStrings (ArrayList<String> judgements) {
		Map<String, CompoundACLNumBean> composNumjudgements = new LinkedHashMap<String, CompoundACLNumBean>();
		for (String oneJudgement : judgements) {
			CompoundACLNumBean comNumBean = new CompoundACLNumBean(oneJudgement);
			composNumjudgements.put(comNumBean.getCompound(), comNumBean);
		}
		return composNumjudgements;
	}
	
	private PairedData preparePairedData(
			Map<String, Double> humanJudgements,
			Map<String, Double> computerJudgements) {

//        System.out.println();
//        System.out.println("HJ");
//        System.out.println(humanJudgements.toString());
//        System.out.println("CJ");
//        System.out.println(computerJudgements.toString());
//        System.out.println("END");
//        System.out.println();

		//Map<String, Double> humanJudgementsBasicTypesSorted = sortByComparator(humanJudgements);
		//Map<String, Double> computerJudgementsBasicTypesSorted = sortByComparator(computerJudgements);
		
		int size = humanJudgements.size();
		double[] originalVals = new double[size];
		double[] countedVals = new double[size];
//		ArrayList<String> computerJudgementsBasicTypesSortedArrayList = new ArrayList<String>();
//		for (Map.Entry<String, Double> e: computerJudgementsBasicTypesSorted.entrySet()) {
//			computerJudgementsBasicTypesSortedArrayList.add(e.getKey());
//		}
		int i = 0;
		for (Map.Entry<String, Double> e: humanJudgements.entrySet()) {
            originalVals[i] = e.getValue();
            countedVals[i] = computerJudgements.get(e.getKey());
			i++;
		}

        if (countedVals.length == 0) {
            System.out.println("empty data in preparePairedData(..)!");
            return null;
        }
		PairedData pd = new PairedData(originalVals, countedVals);
		return pd;
	}

    private Map<String,Double> extractJudgementsBasicTypes(Map<String,CompoundACLNumBean> computerJudgements,
                                                           ArrayList<ACLCompoundTag> extractedTags) {
        Map<String, Double> judgementsBasicTypes = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, CompoundACLNumBean> oneJudgement : computerJudgements.entrySet()) {
            if (extractedTags.contains(oneJudgement.getValue().getTag())) {
                judgementsBasicTypes.put(oneJudgement.getKey(), oneJudgement.getValue().getScore());
            }
        }
        return judgementsBasicTypes;
    }
	
	private SingleValueResult compareNumJudgements(Map<String, CompoundACLNumBean> computerJudgements,
			Map<String, CompoundACLNumBean> humanJudgements, boolean detailed) {

		int matchingCompounds = 0;

		CompoundACLNumBean computerJudgement;
		for (Map.Entry<String, CompoundACLNumBean> oneHumanJudgement : humanJudgements.entrySet()) {
			if ((computerJudgement = computerJudgements.get(oneHumanJudgement.getKey())) != null) {
				if (computerJudgement.getTag() == oneHumanJudgement.getValue().getTag()) {
					matchingCompounds++;
//					System.out.println(oneHumanJudgement.getValue().getCompound() + " " + computerJudgement.getCompound()
//							+ "H: " + oneHumanJudgement.getValue().getScore() + ", C: " + computerJudgement.getScore());
				}
				else {
					throw new IllegalStateException("Error: in compareJudgements.. not same compounds' tags..");
				}
			}
			else {
                throw new IllegalStateException("Error: no match in computer judgement found for: " + oneHumanJudgement.getKey());
			}
		}

		// prepare data for evaluation of all ACL compounds
        ArrayList<ACLCompoundTag> extractedTags = new ArrayList<ACLCompoundTag>();
        extractedTags.add(ACLCompoundTag.EN_ADJ_NN); extractedTags.add(ACLCompoundTag.EN_V_OBJ); extractedTags.add(ACLCompoundTag.EN_V_SUBJ);
		Map<String, Double> computerJudgementsBasicTypesAllACL = extractJudgementsBasicTypes(computerJudgements, extractedTags);
		Map<String, Double> humanJudgementsBasicTypesAllACL = extractJudgementsBasicTypes(humanJudgements, extractedTags);
		PairedData pdAllACL = preparePairedData(humanJudgementsBasicTypesAllACL, computerJudgementsBasicTypesAllACL);
        int matchingCompoundsAllACL = humanJudgementsBasicTypesAllACL.size();

		// prepare data for evaluation of all ACL AdjNouns compounds..
        extractedTags = new ArrayList<ACLCompoundTag>();
        extractedTags.add(ACLCompoundTag.EN_ADJ_NN);
		Map<String, Double> computerJudgementsBasicTypesAdjNouns = extractJudgementsBasicTypes(computerJudgements, extractedTags);
		Map<String, Double> humanJudgementsBasicTypesAdjNouns = extractJudgementsBasicTypes(humanJudgements, extractedTags);
		PairedData pdAdjNouns = preparePairedData(humanJudgementsBasicTypesAdjNouns, computerJudgementsBasicTypesAdjNouns);

		// prepare data for evaluation of all ACL VerbObjs compounds..
        extractedTags = new ArrayList<ACLCompoundTag>();
        extractedTags.add(ACLCompoundTag.EN_V_OBJ);
		Map<String, Double> computerJudgementsBasicTypesVerbObjs = extractJudgementsBasicTypes(computerJudgements, extractedTags);
		Map<String, Double> humanJudgementsBasicTypesVerbObjs = extractJudgementsBasicTypes(humanJudgements, extractedTags);
        PairedData pdVerbObjs = preparePairedData(humanJudgementsBasicTypesVerbObjs, computerJudgementsBasicTypesVerbObjs);

        // prepare data for evaluation of all ACL SubjsVerbs compounds..
        extractedTags = new ArrayList<ACLCompoundTag>();
        extractedTags.add(ACLCompoundTag.EN_V_SUBJ);
		Map<String, Double> computerJudgementsBasicTypesSubjsVerbs = extractJudgementsBasicTypes(computerJudgements, extractedTags);
		Map<String, Double> humanJudgementsBasicTypesSubjsVerbs = extractJudgementsBasicTypes(humanJudgements, extractedTags);
        PairedData pdSubjsVerbs = preparePairedData(humanJudgementsBasicTypesSubjsVerbs, computerJudgementsBasicTypesSubjsVerbs);

        // prepare data for evaluation of all Reddy' NounNoun compounds..
        extractedTags = new ArrayList<ACLCompoundTag>();
        extractedTags.add(ACLCompoundTag.RE_NN_NN);
        Map<String, Double> computerJudgementsBasicTypesNounNoun = extractJudgementsBasicTypes(computerJudgements, extractedTags);
        Map<String, Double> humanJudgementsBasicTypesNounNoun = extractJudgementsBasicTypes(humanJudgements, extractedTags);
        PairedData pdNounNoun = preparePairedData(humanJudgementsBasicTypesNounNoun, computerJudgementsBasicTypesNounNoun);

        double scAllACL = 0.0;
        if (pdAllACL != null) {
            scAllACL = new SpearmanCorrelation(pdAllACL).getR();
            scAllACL = replaceCorrNaN(scAllACL);
        }
        double kcAllACL = 0.0;
        if (pdAllACL != null) {
            kcAllACL = new KendallCorrelation(pdAllACL).getR();
            kcAllACL = replaceCorrNaN(kcAllACL);
        }
        double scAdjNoun = 0.0;
        if (pdAdjNouns != null) {
            scAdjNoun = new SpearmanCorrelation(pdAdjNouns).getR();
            scAdjNoun = replaceCorrNaN(scAdjNoun);
        }
        double kcAdjNoun = 0.0;
        if (pdAdjNouns != null) {
            kcAdjNoun = new KendallCorrelation(pdAdjNouns).getR();
            kcAdjNoun = replaceCorrNaN(kcAdjNoun);
        }
        double scVerbObjs = 0.0;
        if (pdVerbObjs != null) {
            scVerbObjs = new SpearmanCorrelation(pdVerbObjs).getR();
            scVerbObjs = replaceCorrNaN(scVerbObjs);
        }
        double kcVerbObjs = 0.0;
        if (pdVerbObjs != null) {
            kcVerbObjs = new KendallCorrelation(pdVerbObjs).getR();
            kcVerbObjs = replaceCorrNaN(kcVerbObjs);
        }
        double scSubjsVerbs = 0.0;
        if (pdSubjsVerbs != null) {
            scSubjsVerbs = new SpearmanCorrelation(pdSubjsVerbs).getR();
            scSubjsVerbs = replaceCorrNaN(scSubjsVerbs);
        }
        double kcSubjsVerbs = 0.0;
        if (pdSubjsVerbs != null) {
            kcSubjsVerbs = new KendallCorrelation(pdSubjsVerbs).getR();
            kcSubjsVerbs = replaceCorrNaN(kcSubjsVerbs);
        }
        double scNounNoun = 0.0;
        if (pdNounNoun != null) {
            //scNounNoun = new SpearmanCorrelation(pdNounNoun).getR();
            double dPoweredTimes6 = new SpearmanCorrelation(pdNounNoun).getS() * 6;
            double n = new SpearmanCorrelation(pdNounNoun).getN();
            scNounNoun = 1 - (dPoweredTimes6 / (n*(n*n-1)));

            scNounNoun = replaceCorrNaN(scNounNoun);
        }
        double kcNounNoun = 0.0;
        if (pdNounNoun != null) {
            kcNounNoun = new KendallCorrelation(pdNounNoun).getR();
            kcNounNoun = replaceCorrNaN(kcNounNoun);
        }

//        System.out.println();
//        System.out.println(scAllACL);
//        System.out.println(scAdjNoun);
//        System.out.println(scVerbObjs);
//        System.out.println(scSubjsVerbs);
//        System.out.println(scNounNoun);
//        System.out.println();

//        if (scNounNoun <= -0.8) {
//            System.out.println(scNounNoun+"S---------------");
//            System.out.println(pdNounNoun.toString());
//            System.out.println("E---------------");
//        }

		AveragePointDifferenceEval apdEval = null;
		if (detailed) {
			double apdAll = AveragePointDifferenceEval.getAPD(humanJudgementsBasicTypesAllACL, computerJudgementsBasicTypesAllACL);
            double apdAN = AveragePointDifferenceEval.getAPD(humanJudgementsBasicTypesAdjNouns, computerJudgementsBasicTypesAdjNouns);
            double apdVO = AveragePointDifferenceEval.getAPD(humanJudgementsBasicTypesVerbObjs, computerJudgementsBasicTypesVerbObjs);
            double apdSV = AveragePointDifferenceEval.getAPD(humanJudgementsBasicTypesSubjsVerbs, computerJudgementsBasicTypesSubjsVerbs);
            double apdNN = AveragePointDifferenceEval.getAPD(humanJudgementsBasicTypesNounNoun, computerJudgementsBasicTypesNounNoun);
            apdEval = new AveragePointDifferenceEval(apdAll, apdAN, apdVO, apdSV, apdNN);
		}

        // prepare collocations
        //Map<String, Double> sortedHumanJudgements = MapUtil.sortByValue(humanJudgementsBasicTypesAllACL);
        Map<String, Double> sortedHumanJudgements = MapUtil.sortByValue(humanJudgementsBasicTypesNounNoun);
        List<String> collocationsHuman = new LinkedList<String>();
        int colCounter = 0;
        for (Map.Entry<String, Double> e : sortedHumanJudgements.entrySet()) {
            collocationsHuman.add(e.getKey());
            System.out.println(e.getKey());
            colCounter++;
            if (colCounter == collocationCount) {
                break;
            }
        }
        System.out.println();
        System.out.println("===============");
        System.out.println();
        // prepare collocations
        //Map<String, Double> sortedComputerJudgements = MapUtil.sortByValue(computerJudgementsBasicTypesAllACL);
        Map<String, Double> sortedComputerJudgements = MapUtil.sortByValue(computerJudgementsBasicTypesNounNoun);
        List<String> collocationsComputer = new LinkedList<String>();
        for (Map.Entry<String, Double> e : sortedComputerJudgements.entrySet()) {
            collocationsComputer.add(e.getKey());
        }
        //System.out.println(collocationsHuman);
        // count precisions
        double[] nBestPrec = new double[collocationsComputer.size()];
        double[] nBestRec = new double[collocationsComputer.size()];
        List<String> collocationsComputerSubList = new ArrayList<String>();
        int overlap = 0;
        for (int i = 0; i < collocationsComputer.size(); i++) {
            //int top = (i >= Constants.COLLOCATIONS_COUNT) ? Constants.COLLOCATIONS_COUNT : (i+1);
            System.out.println(collocationsComputer.get(i));
            if (collocationsHuman.contains(collocationsComputer.get(i))) {
                overlap++;
            }

            nBestPrec[i] = (double)overlap / (double)(i+1);
            nBestRec[i] = (double)overlap / collocationCount;
       //     System.out.println(collocationsComputerSubList);
        }

        double averagePrec; // counted from different levels of recall
        double lastRecall = 0.0;
        double currentRecall;
        double sumForAvg = 0.0;
        int differentRecallLevelcount = 0;
        for (int i = 0; i < nBestRec.length; i++) {
            currentRecall = nBestRec[i];
            if (currentRecall != 0.0 && currentRecall != lastRecall) {
                sumForAvg += nBestPrec[i];
                differentRecallLevelcount++;
            }
            lastRecall = currentRecall;

        }
        System.out.println(Arrays.toString(nBestPrec));
        System.out.println(Arrays.toString(nBestRec));
        averagePrec = sumForAvg / differentRecallLevelcount;

//        System.out.println(Arrays.toString(nBestPrec));
        double anW = humanJudgementsBasicTypesAdjNouns.size();
        double voW = humanJudgementsBasicTypesVerbObjs.size();
        double svW = humanJudgementsBasicTypesSubjsVerbs.size();
        double nnW = humanJudgementsBasicTypesNounNoun.size();
        double scALLavg = (scAdjNoun*anW + scVerbObjs*voW + scSubjsVerbs*svW + scNounNoun*nnW) / matchingCompounds;

		SingleValueResult sinValEval = new SingleValueResult(matchingCompounds, matchingCompoundsAllACL, scAllACL, kcAllACL, scAdjNoun,
				kcAdjNoun, scVerbObjs, kcVerbObjs, scSubjsVerbs, kcSubjsVerbs, scNounNoun, kcNounNoun, scALLavg, apdEval, nBestPrec, nBestRec, averagePrec);

		return sinValEval;
	}

    private double replaceCorrNaN(double number) {
        if ((new Double(number)).equals(Double.NaN)) {
            return 0.0;
        }
        return number;
    }

    public void printCoarseResultsForExcel(String outputFileName) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
			pw.println("dataset\tvalue scorer\tcoarse scorer\tmatching compounds\tPrec All\tPrec Adj_Noun\tPrec Verb_Obj\tPrec Subj_Verb");
			for (String oneResult: resultsCoarseExcel) {
				pw.println(oneResult);
			}
			pw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Coarse results for Excel printed..");
	}
	
	public void printNumResultsForExcel(String outputFileName) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
			pw.println("dataset\tvalue scorer\tnum scorer\tmatching compounds\tSc-All\tSc-Adj_Noun\tSc-Verb_Obj\tSc-Subj_Verb\t"+
				"Kc All\tKc-Adj_Noun\tKc-Verb_Obj\tKc-Subj_Verb\tAvgPrec");
			for (String oneResult: resultsNumExcel) {
				pw.println(oneResult);
			}
			pw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Num results for Excel printed..");
	}

//	private Map sortByComparator(Map unsortMap) {
//
//		List list = new LinkedList(unsortMap.entrySet());
//
//		// sort list based on comparator
//		Collections.sort(list, new Comparator() {
//			public int compare(Object o1, Object o2) {
//				return ((Comparable) ((Map.Entry) (o1)).getValue())
//						.compareTo(((Map.Entry) (o2)).getValue());
//			}
//		});
//
//		// put sorted list into map again
//		Map sortedMap = new LinkedHashMap();
//		for (Iterator it = list.iterator(); it.hasNext();) {
//			Map.Entry entry = (Map.Entry) it.next();
//			sortedMap.put(entry.getKey(), entry.getValue());
//		}
//		return sortedMap;
//	}

	public ValueEvaluations evaluateValueResults(ArrayList<CompoundSet> compoundSets) {
		ValueEvaluations valueEvals = new ValueEvaluations();
		int testedC = 0;
		// for every compound set..
		for (CompoundSet oneComSet: compoundSets) {
			Map<String, CompoundACLNumBean> humanJudgement = readNumJudgementsFromListOfStrings(oneComSet.getCompoundSetNumData().getCompoundsInStrings());

			LinkedHashMap<ComValScorer, ArrayList<String>> valueScorerXValuesForCompounds =
				oneComSet.getACLNumFormattedValuesCreatedByEveryValueScorer();
			// for every computer judgement..
			
			for (Map.Entry<ComValScorer, ArrayList<String>> oneComputerJudgement : valueScorerXValuesForCompounds.entrySet()) {
				Map<String, CompoundACLNumBean> oneComJudgement = readNumJudgementsFromListOfStrings(oneComputerJudgement.getValue());
				ComValScorer valueScorer = oneComputerJudgement.getKey();
				SingleValueResult singleValueResult = compareNumJudgements(oneComJudgement, humanJudgement, Constants.SIMPLE_DETAILED);
				valueEvals.addSingleValueEvaluation(new SingleValueEvaluation(oneComSet, valueScorer, singleValueResult));
				testedC++;
				if (testedC % 100 == 0) System.out.println(testedC + ". system has been just tested!");
			}
		}
		return valueEvals;
	}

	public FinalEvaluations evaluateFinalResults(FinalResults finalResults, boolean detailed) {
		FinalEvaluations finalEvals = new FinalEvaluations();
		for (OneFinalResult oneFinalRes : finalResults.getFinalResults()) {
			Map<String, CompoundACLNumBean> humanJudgement = readNumJudgementsFromListOfStrings((CompoundsIO.loadCompoundsNumFromFile(oneFinalRes.getDataSet())).getCompoundsInStrings());
//			System.out.println("HHHHHHHHHHHH:" + humanJudgement);
			Map<String, CompoundACLNumBean> oneComJudgement = readNumJudgementsFromListOfStrings(oneFinalRes.getOneFinalResNum().getCompoundsInStrings());
//			System.out.println("CCCCCCCCCCCC:" + oneComJudgement);
			SingleValueResult singleValueResult = compareNumJudgements(oneComJudgement, humanJudgement, detailed);
			if (detailed) {
				Map<String, CompoundACLCoarseBean> oneComputerJudgement = readCoarseJudgementsFromMemory(oneFinalRes.getOneFinalResCoarseBasic());
				Map<String, CompoundACLCoarseBean> oneHumanJudgement = humanCoarseJudgements.get(
						Config.numDatasetToCoarseDataset.get(oneFinalRes.getDataSet()));
				singleValueResult.setBasicCoarseResult(compareCoarseJudgements(oneComputerJudgement, oneHumanJudgement));
				oneComputerJudgement = readCoarseJudgementsFromMemory(oneFinalRes.getOneFinalResCoarseTyped());
				singleValueResult.setTypedCoarseResult(compareCoarseJudgements(oneComputerJudgement, oneHumanJudgement));
				oneComputerJudgement = readCoarseJudgementsFromMemory(oneFinalRes.getOneFinalResCoarseBorders());
				singleValueResult.setBordersCoarseResult(compareCoarseJudgements(oneComputerJudgement, oneHumanJudgement));
			}
			finalEvals.putOneFinalResult(oneFinalRes, singleValueResult);
		}
		return finalEvals;
	}
}
