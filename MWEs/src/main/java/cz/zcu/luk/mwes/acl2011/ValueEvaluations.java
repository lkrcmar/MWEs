package cz.zcu.luk.mwes.acl2011;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import cz.zcu.luk.mwes.common.PdfGraphPrinter;
import cz.zcu.luk.sspace.util.MapUtil;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class ValueEvaluations {
	private Map<EvalCriteria, Comparator<SingleValueEvaluation>> valEvalsComparators;
	private ArrayList<SingleValueEvaluation> valueEvaluations;
	
	public ValueEvaluations() {
		this.valueEvaluations = new ArrayList<SingleValueEvaluation>();
		this.valEvalsComparators = createEvalCriteriaToComparatorMap();
	}
	
	public ArrayList<SingleValueEvaluation> getValueEvaluations() {
		return valueEvaluations;
	}
	
	private Map<EvalCriteria, Comparator<SingleValueEvaluation>> createEvalCriteriaToComparatorMap() {
		Map<EvalCriteria, Comparator<SingleValueEvaluation>> aMap = new LinkedHashMap<EvalCriteria, Comparator<SingleValueEvaluation>>();
		aMap.put(EvalCriteria.SPEARMAN_C_ALL, COMPARATOR_S_C_ALL);
		aMap.put(EvalCriteria.KENDALL_C_ALL, COMPARATOR_K_C_ALL);
		aMap.put(EvalCriteria.SPEARMAN_C_AN, COMPARATOR_S_C_AN);
		aMap.put(EvalCriteria.KENDALL_C_AN, COMPARATOR_K_C_AN);
		aMap.put(EvalCriteria.SPEARMAN_C_VO, COMPARATOR_S_C_VO);
		aMap.put(EvalCriteria.KENDALL_C_VO, COMPARATOR_K_C_VO);
		aMap.put(EvalCriteria.SPEARMAN_C_SV, COMPARATOR_S_C_SV);
		aMap.put(EvalCriteria.KENDALL_C_SV, COMPARATOR_K_C_SV);
        aMap.put(EvalCriteria.SPEARMAN_C_NN, COMPARATOR_S_C_NN);
        aMap.put(EvalCriteria.KENDALL_C_NN, COMPARATOR_K_C_NN);
        aMap.put(EvalCriteria.AVERAGE_PREC, COMPARATOR_AVG_PREC);
        aMap.put(EvalCriteria.SPEARMAN_C_ALL_AVG, COMPARATOR_S_C_ALL_AVG);
		Map<EvalCriteria, Comparator<SingleValueEvaluation>> evalCriteriaToComparator = Collections.unmodifiableMap(aMap);
		return evalCriteriaToComparator;
	}

	public void addSingleValueEvaluation(SingleValueEvaluation singleValueEval) {
		this.valueEvaluations.add(singleValueEval);
	}
	
	private String getHeader() {
		//return "dataset\tmodel\tneighboursHead\tneighboursModifying\tweighting\tmatching compounds\tS c All\tK c All\t"+
        //	"S c Adj_Noun\tK c Adj_Noun\tS c Verb_Obj\tK c Verb_Obj\tS c Subj_Verb\tK c Subj_Verb";
        return "dataset\tmatching compounds\tSc-All\tSc-Adj_Noun\tSc-Verb_Obj\tSc-Subj_Verb\tSc-Noun_Noun\t"+
                "Kc-All\tKc-Adj_Noun\tKc-Verb_Obj\tKc-Subj_Verb\tKc-Noun_Noun\tAvgPrec";
	}
	
	private ArrayList<SingleValueEvaluation> getValueEvaluationsForGivenDataSet(ACLCompoundSetNum dataSet) {
		ArrayList<SingleValueEvaluation> valEvalsOfGivenDataSet = new ArrayList<SingleValueEvaluation>();
		for (SingleValueEvaluation oneEval : valueEvaluations) {
			if (oneEval.getCompoundSet().getCompoundSetNumData().getName() == dataSet) {
				valEvalsOfGivenDataSet.add(oneEval);
			}
		}
		return valEvalsOfGivenDataSet;
	}
	
	public Map<EvalCriteria, List<SingleValueEvaluation>> getBestSingleEvaluations(int numberOfBest, ACLCompoundSetNum dataSet) {
		Map<EvalCriteria, List<SingleValueEvaluation>> bestEvals = new LinkedHashMap<EvalCriteria, List<SingleValueEvaluation>>();
		ArrayList<SingleValueEvaluation> valEvalsForGivenDataSet = getValueEvaluationsForGivenDataSet(dataSet);
		for (Map.Entry<EvalCriteria, Comparator<SingleValueEvaluation>> oneComparator: valEvalsComparators.entrySet()) {
			Collections.sort(valEvalsForGivenDataSet, oneComparator.getValue());
            if (numberOfBest == 0) {
                bestEvals.put(oneComparator.getKey(), new ArrayList<SingleValueEvaluation>(valEvalsForGivenDataSet));
            }
            else {
                bestEvals.put(oneComparator.getKey(), new ArrayList<SingleValueEvaluation>(valEvalsForGivenDataSet.subList(0, numberOfBest)));
            }
		}	
		return bestEvals;
	}

	public void printValueResults(String outputFileName) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
			pw.println(getHeader());
			for (SingleValueEvaluation oneEval : valueEvaluations) {
				pw.println(oneEval.toString());
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

	public void printResultsOfBestValScorers(String outputFileName, int numberOfBest, ACLCompoundSetNum dataSet) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
			Map<EvalCriteria, List<SingleValueEvaluation>> bestEvals = getBestSingleEvaluations(numberOfBest, dataSet);
			for (Map.Entry<EvalCriteria, List<SingleValueEvaluation>> valEvalsForGivenDataSet: bestEvals.entrySet()) {
				pw.println(valEvalsForGivenDataSet.getKey());
				pw.println(getHeader());
				for (SingleValueEvaluation oneEval : valEvalsForGivenDataSet.getValue()) {
					pw.println(oneEval.toString());
				}
				pw.println();
			}
			pw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Best num results for Excel printed..");
	}
	
	private ArrayList<SingleValueEvaluation> getValueEvaluations(String comValScorerType, ACLCompoundSetNum dataset) {
		ArrayList<SingleValueEvaluation> singleValEvalsOfSpecKindAndDS = new ArrayList<SingleValueEvaluation>();
		for (SingleValueEvaluation oneSingleValEval : this.valueEvaluations) {
			if (oneSingleValEval.getValueScorer().getType().equals(comValScorerType) &&
					oneSingleValEval.getCompoundSet().getName().equals(dataset)) {
				singleValEvalsOfSpecKindAndDS.add(oneSingleValEval);
			}
		}
		return singleValEvalsOfSpecKindAndDS;
	}

    /**
     * filter evals - retains the ones having the specified generalType
     *
     * @param evals
     * @param generalType
     * @return
     */
    private Map<EvalCriteria, List<SingleValueEvaluation>> getEvalsHavingGeneralType(Map<EvalCriteria, List<SingleValueEvaluation>> evals,
            GeneralScorerType generalType) {
        Map<EvalCriteria, List<SingleValueEvaluation>> filteredEvals = new LinkedHashMap<EvalCriteria, List<SingleValueEvaluation>>();
        for (Map.Entry<EvalCriteria, List<SingleValueEvaluation>> entry : evals.entrySet()) {
            filteredEvals.put(entry.getKey(), new ArrayList<SingleValueEvaluation>());
            for (SingleValueEvaluation oneEvalaution : entry.getValue()) {
                if (oneEvalaution.getValueScorer().getGeneralType() == generalType) {
                    filteredEvals.get(entry.getKey()).add(oneEvalaution);
                }
            }
        }
        return filteredEvals;
    }

    private Map<EvalCriteria, List<SingleValueEvaluation>> getEvalsHavingSpace(Map<EvalCriteria, List<SingleValueEvaluation>> evalsNotFiltered, String spaceName) {
        Map<EvalCriteria, List<SingleValueEvaluation>> filteredEvals = new LinkedHashMap<EvalCriteria, List<SingleValueEvaluation>>();
        for (Map.Entry<EvalCriteria, List<SingleValueEvaluation>> entry : evalsNotFiltered.entrySet()) {
            filteredEvals.put(entry.getKey(), new ArrayList<SingleValueEvaluation>());
            for (SingleValueEvaluation oneEvalaution : entry.getValue()) {
                if (Common.mapToStringRep(oneEvalaution.getValueScorer().getNameWithoutGeneralType()).equals(spaceName)) {
                    filteredEvals.get(entry.getKey()).add(oneEvalaution);
                }
            }
        }
        return filteredEvals;
    }

    public Map<EvalCriteria, List<ComValScorer>> printValueResultsOrganizedLatex(String outputFN, int numberOfBest, ACLCompoundSetNum dataset,
                                                                                 Map<EvalCriteria, List<ComValScorer>> demandedComValScorers) {
        Map<EvalCriteria, List<ComValScorer>> bestComValScorers = new LinkedHashMap<EvalCriteria, List<ComValScorer>>();

        PrintWriter pw;
        ArrayList<EvalCriteria> printedEvalCriteria = new ArrayList<EvalCriteria>();
//        printedEvalCriteria.add(EvalCriteria.SPEARMAN_C_AN);
//        printedEvalCriteria.add(EvalCriteria.SPEARMAN_C_VO);
//        printedEvalCriteria.add(EvalCriteria.SPEARMAN_C_SV);
//        printedEvalCriteria.add(EvalCriteria.SPEARMAN_C_NN);
//        printedEvalCriteria.add(EvalCriteria.SPEARMAN_C_ALL);
        printedEvalCriteria.add(EvalCriteria.SPEARMAN_C_ALL_AVG);
        ArrayList<EvalCriteria> printedEvals = new ArrayList<EvalCriteria>();
        //printedEvals.add(EvalCriteria.AVERAGE_PREC);
        printedEvals.add(EvalCriteria.SPEARMAN_C_ALL_AVG);
        printedEvals.add(EvalCriteria.SPEARMAN_C_ALL);
        printedEvals.add(EvalCriteria.SPEARMAN_C_AN);
        printedEvals.add(EvalCriteria.SPEARMAN_C_VO);
        printedEvals.add(EvalCriteria.SPEARMAN_C_SV);
        printedEvals.add(EvalCriteria.SPEARMAN_C_NN);


        try {
            Map<String, String> wParMap = new LinkedHashMap<String, String>();
            Map<String, String> cParMap = new LinkedHashMap<String, String>();

            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFN), "UTF-8"));
            pw.println("\t\tAllAvg\tAN-VO-SV\tAN\tVO\tSV\tNN");

            Map<EvalCriteria, List<SingleValueEvaluation>> evalsNotFiltered;
            if (demandedComValScorers == null) {
                evalsNotFiltered = getBestSingleEvaluations(0, dataset);
            }
            else {
                evalsNotFiltered = getDemandedComValScorers(demandedComValScorers);
            }
            // for each general type

            ArrayList<String> spaceNames = new ArrayList<String>();
            spaceNames.add("VSM");
            spaceNames.add("LSA");
            spaceNames.add("HAL");
            spaceNames.add("COALS");
            spaceNames.add("RI");
            Map<GeneralScorerType, Integer> modelIndexCounterMap = new LinkedHashMap<GeneralScorerType, Integer>();
            for (GeneralScorerType oneGT : GeneralScorerType.values()) {
                modelIndexCounterMap.put(oneGT, 1);
            }

            for (String spaceName : spaceNames) {
                Map<EvalCriteria, List<SingleValueEvaluation>> evals = getEvalsHavingSpace(evalsNotFiltered, spaceName);
                int spaceIndexCounter = 1;

                for (GeneralScorerType oneGeneralType : GeneralScorerType.values()) {
                    if (oneGeneralType == GeneralScorerType.PMI_LOADER) continue; // skip PMI-based model..
                    Map<EvalCriteria, List<SingleValueEvaluation>> evalsFiltered = getEvalsHavingGeneralType(evals, oneGeneralType);
                    for (Map.Entry<EvalCriteria, List<SingleValueEvaluation>> entry : evalsFiltered.entrySet()) {
                        if (printedEvalCriteria.contains(entry.getKey())) {
                            if (!entry.getValue().isEmpty()) {
                                ArrayList<ComValScorer> bestComValsList = new ArrayList<ComValScorer>();
                                for (int i = 0; i < numberOfBest; i++) {
                                    SingleValueEvaluation singleValueEvaluation = entry.getValue().get(i);
                                    bestComValsList.add(singleValueEvaluation.getValueScorer());

                                    String spacePars = singleValueEvaluation.getValueScorer().getSpacePars();
                                    String composPars = singleValueEvaluation.getValueScorer().getPars();

                                    if (!wParMap.containsKey(spaceName + spacePars)) {
                                        String spaceIndex = "\\textsubscript{" + spaceIndexCounter + "}";
                                        wParMap.put(spaceName + spacePars, spaceName + spaceIndex);
                                        spaceIndexCounter++;
                                    }
                                    if (!cParMap.containsKey(shortCutModel(oneGeneralType) + composPars)) {
                                        int modelIndexCounter = modelIndexCounterMap.get(oneGeneralType);
                                        String modelIndex = "\\textsubscript{" + modelIndexCounter + "}";
                                        cParMap.put(shortCutModel(oneGeneralType) + composPars, shortCutModel(oneGeneralType) + modelIndex);
                                        modelIndexCounterMap.put(oneGeneralType, ++modelIndexCounter);
                                    }
                                    pw.print(wParMap.get(spaceName + spacePars));
                                    pw.print(" & " + cParMap.get(shortCutModel(oneGeneralType) + composPars));
                                    for (EvalCriteria evCrit : printedEvals) {
                                        //pw.print(" & " + singleValueEvaluation.getSingleValueResult().toString(entry.getKey()));
                                        pw.print(" & " + singleValueEvaluation.getSingleValueResult().toString(evCrit));
                                    }
                                }
                                if (bestComValScorers.get(entry.getKey()) == null) {
                                    bestComValScorers.put(entry.getKey(), bestComValsList);
                                }
                                else {
                                    bestComValScorers.get(entry.getKey()).addAll(bestComValsList);
                                }
                            }
                            else {
                                pw.print(" & &");
                            }
                        }
                    }
                    pw.println(" \\\\");
                }
                pw.println("\\hline");
            }
            pw.println();
            pw.println();
            for (Map.Entry<String,String> oneWpar : wParMap.entrySet()) {
                pw.print(oneWpar.getValue());
                pw.print(" & ");
                String printedWpar = oneWpar.getKey();
                if (printedWpar.startsWith("D")) printedWpar = printedWpar.replaceFirst("D", "D_");
                printedWpar = printedWpar.replace("LOGENT", "logEnt");
                printedWpar = printedWpar.replace("TFIDFC", "tfIdf");
                printedWpar = printedWpar.replace("LOGENT", "logEnt");
                printedWpar = printedWpar.replace("D300", "D_300");
                printedWpar = printedWpar.replace("D900", "D_900");
                printedWpar = printedWpar.replace("PNO", "P_no");
                printedWpar = printedWpar.replace("PTPF", "P_yes");
                printedWpar = printedWpar.replace("NO", "no");
                printedWpar = printedWpar.replace("W2", "W_2");
                printedWpar = printedWpar.replace("W3", "W_3");
                printedWpar = printedWpar.replace("W5", "W_5");
                printedWpar = printedWpar.replace("R20000", "R_20000");
                printedWpar = printedWpar.replace("R10000", "R_10000");
                printedWpar = printedWpar.replace("L4000", "L_4000");
                printedWpar = printedWpar.replace("RNEG1", "R_all");
                printedWpar = printedWpar.replace("VSM", "");
                printedWpar = printedWpar.replace("LSA", "");
                printedWpar = printedWpar.replace("COALS", "");
                printedWpar = printedWpar.replace("RI", "");
                printedWpar = printedWpar.replace("HAL", "");
                printedWpar = printedWpar.replace("true", "yes");
                printedWpar = printedWpar.replace("false", "no");
                //oneWpar = oneWpar.replace("LOGENTX", "logEnt");

                pw.println(printedWpar.replaceAll("_", "\\\\_") + " \\\\");
            }
            pw.println();
            pw.println();
            cParMap = MapUtil.sortByValue(cParMap);
            for (Map.Entry<String,String> oneCpar : cParMap.entrySet()) {
                System.out.println(oneCpar);
                pw.print(oneCpar.getValue());
                String printedCpar = oneCpar.getKey();
                printedCpar = printedCpar.replace("COSSIM", "cos");
                printedCpar = printedCpar.replace("AVG", "avg");
                printedCpar = printedCpar.replace("MAX", "max");
                printedCpar = printedCpar.replace("MIN", "min");
                printedCpar = printedCpar.replace("_SIM", "Sim");
                printedCpar = printedCpar.replace("_DIST", "Dist");
                printedCpar = printedCpar.replace("all_log", "allLog");
                printedCpar = printedCpar.replace("CNS", "S");
                printedCpar = printedCpar.replace("COS", "S");
                printedCpar = printedCpar.replace("ENS", "S");
                printedCpar = printedCpar.replace("NES", "S");
                printedCpar = printedCpar.replace("PCORR", "pCorr");
                pw.print(" & ");
                pw.println(printedCpar.replaceAll("_", "\\\\_") + " \\\\");
            }

            pw.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Num results for Latex printed..");

        return bestComValScorers;
    }

    private Map<EvalCriteria, List<SingleValueEvaluation>> getDemandedComValScorers(Map<EvalCriteria, List<ComValScorer>> demandedComValScorers) {
        Map<EvalCriteria, List<SingleValueEvaluation>> critToEvals = new LinkedHashMap<EvalCriteria, List<SingleValueEvaluation>>();
        for (Map.Entry<EvalCriteria, List<ComValScorer>> entry : demandedComValScorers.entrySet()) {
            List<SingleValueEvaluation> valueEvaluationList = new ArrayList<SingleValueEvaluation>();
            for (ComValScorer oneCVS : entry.getValue()) {
                SingleValueEvaluation oneEval = getSingleValueEvalWithScorer(oneCVS);
                if (oneEval != null) {
                    valueEvaluationList.add(oneEval);
                }
            }
            critToEvals.put(entry.getKey(), valueEvaluationList);
        }
        return critToEvals;
    }

    private SingleValueEvaluation getSingleValueEvalWithScorer(ComValScorer oneCVS) {
        for (SingleValueEvaluation oneEval : valueEvaluations) {
            if (oneEval.getValueScorer().equals(oneCVS)) {
                return oneEval;
            }
        }
        return null;
    }

    private String shortCutModel(GeneralScorerType generalScorerType) {
        switch (generalScorerType) {
            case ENDOCENTRICITY: return "EN";
            case COMPONEIGBORS: return "CN";
            case COMPOSITIONALITY: return "CO";
            case NEIGHBOURS: return "NE";
            default: {
                throw new IllegalArgumentException("Unknnown generalScoererType!");
            }
        }
    }



//    // Generate the graph
//    JFreeChart chart = ChartFactory.createXYLineChart(
//            "", // Title
//            "Recall", // x-axis Label
//            "Precision", // y-axis Label
//            datasetPreRec, // Dataset
//            PlotOrientation.VERTICAL, // Plot Orientation
//            true, // Show Legend
//            true, // Use tooltips
//            false // Configure chart to generate URLs?
//    );

    public void printPrecisionRecallGraphs(String resultsDir, PrecRecGraphSmoothing smoothingType, ACLCompoundSetNum datasetE) {
        int collocationsCount = Constants.getCollocationCount(datasetE);

        XYSeriesCollection datasetPreRec = new XYSeriesCollection();
        XYSeriesCollection datasetPreNbest = new XYSeriesCollection();
        XYSeriesCollection datasetRecNbest = new XYSeriesCollection();

        ArrayList<SingleValueEvaluation> valEvalsForTrainValD = getValueEvaluationsForGivenDataSet(datasetE);
        int matchingCompoundsAllACL = valEvalsForTrainValD.get(0).getSingleValueResult().getMatchingCompoundsAllACL();

        boolean addBaselines = true;
        XYSeries seriesBaselinePreRec = null;
        XYSeries seriesBaselinePreNbest = null;
        XYSeries seriesBaselineRecNbest = null;
        for (SingleValueEvaluation evaluation : valEvalsForTrainValD) {
            double[] nBestRec = evaluation.getSingleValueResult().getnBestRecall();
            double[] nBestPre = evaluation.getSingleValueResult().getnBestPrecision();

            if (addBaselines) {
                seriesBaselinePreRec = new XYSeries("baseline");
                seriesBaselinePreNbest = new XYSeries("baseline");
                seriesBaselineRecNbest = new XYSeries("baseline");

                for (int i = 0; i < nBestRec.length; i++) {
                    seriesBaselinePreNbest.add((double)(i+1), collocationsCount / (double)matchingCompoundsAllACL);
                    // (((double)Constants.COLLOCATIONS_COUNT / (double)matchingCompounds) * (i+1)) / (double)Constants.COLLOCATIONS_COUNT
                    seriesBaselineRecNbest.add(i+1, (double)(i+1) / (double)matchingCompoundsAllACL);

                }
                for (int i = 0; i < collocationsCount; i++) {
                    seriesBaselinePreRec.add((double)(i+1) / collocationsCount, collocationsCount / (double)matchingCompoundsAllACL);
                }

                datasetPreRec.addSeries(seriesBaselinePreRec);
                datasetPreNbest.addSeries(seriesBaselinePreNbest);
                datasetRecNbest.addSeries(seriesBaselineRecNbest);

                addBaselines = false;
            }

            // Add the series to your data set
            String seriesName = evaluation.getValueScorer().toString();
            seriesName = seriesName.replace("ENDOCENTRICITY_NEW_", "");
            seriesName = seriesName.replace("_COSSIM_E_AVG_SIM", "");
            seriesName = seriesName.replace("_UALL", "");
            seriesName = seriesName.replace("_C_M50_S_D300_LOG_ENTROPY", "");
            seriesName = seriesName.replace("PMI_LOADER_BASIC_NONAME", "PMI");
            seriesName = seriesName.replace("ENDOCENTRICITY_LSAC_D300_LOGENTROPY_F50OT", "LSA");

            XYSeries precRecSeries = createPrecRecSeries(nBestRec, nBestPre, seriesName, smoothingType);
            datasetPreRec.addSeries(precRecSeries);
            XYSeries precNbestSeries = createPrecNbestSeries(nBestRec, nBestPre, seriesName, smoothingType);
            datasetPreNbest.addSeries(precNbestSeries);
            XYSeries recNbestSeries = createRecNbestSeries(nBestRec, nBestPre, seriesName, smoothingType);
            datasetRecNbest.addSeries(recNbestSeries);

//            XYSeries precRecSeries = createPrecRecSeries(nBestRec, nBestPre, seriesName, PrecRecGraphSmoothing.SECOND_TYPE);
//            datasetPreRec.addSeries(precRecSeries);
//            precRecSeries = createPrecRecSeries(nBestRec, nBestPre, seriesName, PrecRecGraphSmoothing.FIRST_TYPE);
//            datasetPreRec.addSeries(precRecSeries);
//            precRecSeries = createPrecRecSeries(nBestRec, nBestPre, seriesName, PrecRecGraphSmoothing.NO);
//            datasetPreRec.addSeries(precRecSeries);
//
//            XYSeries precNbestSeries = createPrecNbestSeries(nBestRec, nBestPre, seriesName, PrecRecGraphSmoothing.SECOND_TYPE);
//            datasetPreNbest.addSeries(precNbestSeries);
//            precNbestSeries = createPrecNbestSeries(nBestRec, nBestPre, seriesName, PrecRecGraphSmoothing.FIRST_TYPE);
//            datasetPreNbest.addSeries(precNbestSeries);
//            precNbestSeries = createPrecNbestSeries(nBestRec, nBestPre, seriesName, PrecRecGraphSmoothing.NO);
//            datasetPreNbest.addSeries(precNbestSeries);
//
//            XYSeries recNbestSeries = createRecNbestSeries(nBestRec, nBestPre, seriesName, PrecRecGraphSmoothing.SECOND_TYPE);
//            datasetRecNbest.addSeries(recNbestSeries);
//            recNbestSeries = createRecNbestSeries(nBestRec, nBestPre, seriesName, PrecRecGraphSmoothing.NO);
//            datasetRecNbest.addSeries(recNbestSeries);
        }

        String datasetName = datasetE.toString();
        // Generate the graph
        JFreeChart chart = ChartFactory.createXYLineChart("", "Recall", "Precision", datasetPreRec, PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        String chartName = "Precision-Recall" + datasetName;
        setMyRenderer(chart);
        PdfGraphPrinter.writeChartToPDFClipped(chart, 500, 500, resultsDir + chartName + ".pdf");

        chart = ChartFactory.createXYLineChart("", "nBest", "Precision", datasetPreNbest, PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        chartName = "Precision-nBest" + datasetName;
        setMyRenderer(chart);
        PdfGraphPrinter.writeChartToPDFClipped(chart, 500, 500, resultsDir + chartName + ".pdf");

        chart = ChartFactory.createXYLineChart("", "nBest", "Recall", datasetRecNbest, PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        chartName = "Recall-nBest" + datasetName;
        setMyRenderer(chart);
        PdfGraphPrinter.writeChartToPDFClipped(chart, 500, 500, resultsDir + chartName + ".pdf");
    }

    private void setMyRenderer(JFreeChart chart) {
        float[] emptySpaces = new float[]{4.0f, 0.0f, 6.0f, 6.0f, 8.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
        Color[] colors = new Color[] {Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE};
        for (int i = 0; i < chart.getXYPlot().getSeriesCount(); i++) {
            chart.getXYPlot().getRenderer().setSeriesPaint(i, Color.BLACK);
            if (i != 0 && i < emptySpaces.length && i < colors.length) {
                Stroke stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                        1.0f, new float[] { 6.0f, emptySpaces[i-1] }, 0.0f);
                chart.getXYPlot().getRenderer().setSeriesStroke(i, stroke);
                chart.getXYPlot().getRenderer().setSeriesPaint(i, colors[i-1]);
            }
            else {
                chart.getXYPlot().getRenderer().setSeriesPaint(i, Color.GRAY);
            }
        }
    }

    private XYSeries createRecNbestSeries(double[] nBestRec, double[] nBestPre, String seriesName, PrecRecGraphSmoothing smoothingType) {
        XYSeries seriesRecNbest = new XYSeries(seriesName);
        switch (smoothingType) {
            case NO : {
                for (int i = 0; i < nBestRec.length; i++) {
                    seriesRecNbest.add((i+1), nBestRec[i]);
                }
                break;
            }
            case FIRST_TYPE:
            case SECOND_TYPE: {
                addSmoothedRecallNbestToSeries(nBestRec, seriesRecNbest);
                break;
            }
        }
        return seriesRecNbest;
    }

    private XYSeries createPrecNbestSeries(double[] nBestRec, double[] nBestPre, String seriesName, PrecRecGraphSmoothing smoothingType) {
        XYSeries seriesPreNbest = new XYSeries(seriesName);
        switch (smoothingType) {
            case NO : {
                for (int i = 0; i < nBestPre.length; i++) {
                    seriesPreNbest.add((i+1), nBestPre[i]);
                }
                break;
            }
            case FIRST_TYPE: {
                addSmoothedPrecNbestToSeries(nBestRec, nBestPre, seriesPreNbest, false);
                break;
            }
            case SECOND_TYPE: {
                addSmoothedPrecNbestToSeries(nBestRec, nBestPre, seriesPreNbest, true);
                break;
            }
        }
        return seriesPreNbest;
    }

    private XYSeries createPrecRecSeries(double[] nBestRec, double[] nBestPre, String seriesName, PrecRecGraphSmoothing smoothingType) {
        XYSeries seriesPreRec = new XYSeries(seriesName);
        switch (smoothingType) {
            case NO : {
                for (int i = 0; i < nBestRec.length; i++) {
                    seriesPreRec.add(nBestRec[i], nBestPre[i]);
                }
                break;
            }
            case FIRST_TYPE: {
                addSmoothedPrecRecToSeries(nBestRec, nBestPre, seriesPreRec, false);
                break;
            }
            case SECOND_TYPE: {
                addSmoothedPrecRecToSeries(nBestRec, nBestPre, seriesPreRec, true);
                break;
            }
        }
        return seriesPreRec;
    }


    private void addSmoothedRecallNbestToSeries(double[] nBestRec, XYSeries series) {
        int index = 0;
        double value = Double.MIN_VALUE;
        while (index < nBestRec.length) {
            if (nBestRec[index] > value) {
                value = nBestRec[index];
                series.add((index+1), nBestRec[index]);
            }
            index++;
        }
    }

    private void addSmoothedPrecNbestToSeries(double[] nBestRec, double[] nBestPre, XYSeries series, boolean differentLevelOfRecall) {
        int index = 0;
        while (index < nBestPre.length) {
            index = findLastIndexWithMaxValue(index, nBestPre);
            if (differentLevelOfRecall == false || index == 0 || nBestRec[index] != nBestRec[index-1]) {
                series.add((index+1), nBestPre[index]);
            }
            index++;
        }
    }

    private void addSmoothedPrecRecToSeries(double[] nBestRec, double[] nBestPre, XYSeries series, boolean differentLevelOfRecall) {
        int index = 0;
        while (index < nBestPre.length) {
            index = findLastIndexWithMaxValue(index, nBestPre);
            if (differentLevelOfRecall == false || index == 0 || nBestRec[index] != nBestRec[index-1]) {
                series.add(nBestRec[index], nBestPre[index]);
            }
            index++;
        }
    }

    private int findLastIndexWithMaxValue(int startIndex, double[] data) {
        int index = startIndex;
        double maxValue = Double.MIN_VALUE;
        for (int i = startIndex; i < data.length; i++) {
            if (data[i] >= maxValue) {
                index = i;
                maxValue = data[i];
            }
        }
        return index;
    }

//	public Map<String, List<SingleValueEvaluation>> getBestSingleEvaluationsKinded(
//			int numberOfBest, ACLCompoundSetNum dataset, EvalCriteria evalCriteria) {
//		Map<String, List<SingleValueEvaluation>> comValScoreKindXbestEvals =
//			new LinkedHashMap<String, List<SingleValueEvaluation>>();
//		for (String oneComValScorerNeigboursType : String.getPossibleComValScoreKinds()) {
//			ArrayList<SingleValueEvaluation> demandedValEvals = getValueEvaluations(oneComValScorerNeigboursType, dataset);
//			if (demandedValEvals.size() != 0) {
//				Collections.sort(demandedValEvals, this.valEvalsComparators.get(evalCriteria));
//				//comValScoreKindXbestEvals.put(oneComValScorerNeigboursType, demandedValEvals);
//				comValScoreKindXbestEvals.put(oneComValScorerNeigboursType, new ArrayList<SingleValueEvaluation>(demandedValEvals.subList(0, numberOfBest)));
//			}
//			else {
//				System.out.println("No evals available..");
//			}
//		}
//		return comValScoreKindXbestEvals;
//	}
	
//	public Map<EvalCriteria, List<SingleValueEvaluation>> getBestSingleEvaluationsCombinationZeros(
//			int numberOfBest, ACLCompoundSetNum dataSet) {
//		Map<EvalCriteria, List<SingleValueEvaluation>> bestEvals = new LinkedHashMap<EvalCriteria, List<SingleValueEvaluation>>();
//		ArrayList<SingleValueEvaluation> valEvalsForGivenDataSet = getValueEvaluationsForGivenDataSet(dataSet);
//		ArrayList<SingleValueEvaluation> valEvalsWithZerosHead = reduceToValEvalWithZerosOnHead(valEvalsForGivenDataSet, true);
//		ArrayList<SingleValueEvaluation> valEvalsWithZerosModifying = reduceToValEvalWithZerosOnHead(valEvalsForGivenDataSet, false);
//		for (Map.Entry<EvalCriteria, Comparator<SingleValueEvaluation>> oneComparator: valEvalsComparators.entrySet()) {
//			Collections.sort(valEvalsWithZerosHead, oneComparator.getValue());
//			Collections.sort(valEvalsWithZerosModifying, oneComparator.getValue());
//			ArrayList<SingleValueEvaluation> valEvalsCombined = combineFromZeroed(
//					new ArrayList<SingleValueEvaluation>(valEvalsWithZerosHead.subList(0, numberOfBest)),
//					new ArrayList<SingleValueEvaluation>(valEvalsWithZerosModifying.subList(0, numberOfBest)));
//			Collections.sort(valEvalsCombined, oneComparator.getValue());
//			bestEvals.put(oneComparator.getKey(), valEvalsCombined);
//		}
//		return bestEvals;
//	}
	

//	private ArrayList<SingleValueEvaluation> combineFromZeroed(
//			ArrayList<SingleValueEvaluation> valEvalsWithZerosOnHead,
//			ArrayList<SingleValueEvaluation> valEvalsWithZerosOnModifying) {
//
//		ArrayList<Integer> modifyingNumbers = new ArrayList<Integer>();
//		for (SingleValueEvaluation oneSingleValEval : valEvalsWithZerosOnHead) {
//			modifyingNumbers.add(oneSingleValEval.getValueScorer().getModifyingNeighborsCount());
//		}
//		ArrayList<Integer> headNumbers = new ArrayList<Integer>();
//		for (SingleValueEvaluation oneSingleValEval : valEvalsWithZerosOnModifying) {
//			headNumbers.add(oneSingleValEval.getValueScorer().getHeadNeighborsCount());
//		}
//
//		ArrayList<SingleValueEvaluation> combinedFromZeroed = new ArrayList<SingleValueEvaluation>();
//		for (SingleValueEvaluation oneSingleValEval : this.valueEvaluations) {
//			if (headNumbers.contains(oneSingleValEval.getValueScorer().getHeadNeighborsCount()) &&
//					modifyingNumbers.contains(oneSingleValEval.getValueScorer().getModifyingNeighborsCount())) {
//				combinedFromZeroed.add(oneSingleValEval);
//			}
//		}
//
//		return combinedFromZeroed;
//	}

//	private ArrayList<SingleValueEvaluation> reduceToValEvalWithZerosOnHead(ArrayList<SingleValueEvaluation> valEvals, boolean trueHead) {
//		ArrayList<SingleValueEvaluation> valEvalsWithZerosOnOneSide = new ArrayList<SingleValueEvaluation>();
//		for (SingleValueEvaluation oneSingleValEval : valEvals) {
//			if (trueHead) { // zeros on head wanted..
//				if (oneSingleValEval.getValueScorer().getHeadNeighborsCount() == 0) {
//					valEvalsWithZerosOnOneSide.add(oneSingleValEval);
//				}
//			}
//			else { // zeros on modifying wanted..
//				if (oneSingleValEval.getValueScorer().getModifyingNeighborsCount() == 0) {
//					valEvalsWithZerosOnOneSide.add(oneSingleValEval);
//				}
//			}
//		}
//		return valEvalsWithZerosOnOneSide;
//	}

	private int comparision(double selectedValue1, double selectedValue2) {
//		String first = (new Double(selectedValue1)).toString();
//		String second = (new Double(selectedValue2)).toString();
//		return second.compareTo(first);
		return (new Double(selectedValue2)).compareTo(new Double(selectedValue1));
	}
	
	private final Comparator<SingleValueEvaluation> COMPARATOR_S_C_ALL = new Comparator<SingleValueEvaluation>() {
		public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
			return comparision(o1.getSingleValueResult().getSpearmanCAll(), o2.getSingleValueResult().getSpearmanCAll());
		}
	};
	
	private final Comparator<SingleValueEvaluation> COMPARATOR_K_C_ALL = new Comparator<SingleValueEvaluation>() {
		public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
			return comparision(o1.getSingleValueResult().getKendallCAll(), o2.getSingleValueResult().getKendallCAll());
		}
	};
	
	private final Comparator<SingleValueEvaluation> COMPARATOR_S_C_AN = new Comparator<SingleValueEvaluation>() {
		public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
			return comparision(o1.getSingleValueResult().getSpearmanCAdjNoun(), o2.getSingleValueResult().getSpearmanCAdjNoun());
		}
	};
	
	private final Comparator<SingleValueEvaluation> COMPARATOR_K_C_AN = new Comparator<SingleValueEvaluation>() {
		public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
			return comparision(o1.getSingleValueResult().getKendallCAdjNoun(), o2.getSingleValueResult().getKendallCAdjNoun());
		}
	};
	
	private final Comparator<SingleValueEvaluation> COMPARATOR_S_C_VO = new Comparator<SingleValueEvaluation>() {
		public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
			return comparision(o1.getSingleValueResult().getSpearmanCVerbObj(), o2.getSingleValueResult().getSpearmanCVerbObj());
		}
	};
	
	private final Comparator<SingleValueEvaluation> COMPARATOR_K_C_VO = new Comparator<SingleValueEvaluation>() {
		public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
			return comparision(o1.getSingleValueResult().getKendallCVerbObj(), o2.getSingleValueResult().getKendallCVerbObj());
		}
	};
	
	private final Comparator<SingleValueEvaluation> COMPARATOR_S_C_SV = new Comparator<SingleValueEvaluation>() {
		public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
			return comparision(o1.getSingleValueResult().getSpearmanCSubjVerb(), o2.getSingleValueResult().getSpearmanCSubjVerb());
		}
	};
	
	private final Comparator<SingleValueEvaluation> COMPARATOR_K_C_SV = new Comparator<SingleValueEvaluation>() {
		public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
			return comparision(o1.getSingleValueResult().getKendallCSubjVerb(), o2.getSingleValueResult().getKendallCSubjVerb());
		}
	};

    private final Comparator<SingleValueEvaluation> COMPARATOR_S_C_NN = new Comparator<SingleValueEvaluation>() {
        public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
            return comparision(o1.getSingleValueResult().getSpearmanCNounNoun(), o2.getSingleValueResult().getSpearmanCNounNoun());
        }
    };

    private final Comparator<SingleValueEvaluation> COMPARATOR_K_C_NN = new Comparator<SingleValueEvaluation>() {
        public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
            return comparision(o1.getSingleValueResult().getKendallCNounNoun(), o2.getSingleValueResult().getKendallCNounNoun());
        }
    };

    private final Comparator<SingleValueEvaluation> COMPARATOR_AVG_PREC = new Comparator<SingleValueEvaluation>() {
        public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
            return comparision(o1.getSingleValueResult().getAveragePrec(), o2.getSingleValueResult().getAveragePrec());
        }
    };

    private final Comparator<SingleValueEvaluation> COMPARATOR_S_C_ALL_AVG = new Comparator<SingleValueEvaluation>() {
        public int compare(SingleValueEvaluation o1, SingleValueEvaluation o2) {
            return comparision(o1.getSingleValueResult().getSpearmanCAllAvg(), o2.getSingleValueResult().getSpearmanCAllAvg());
        }
    };
}
