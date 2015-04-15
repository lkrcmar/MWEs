package cz.zcu.luk.mwes.scoring;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.luk.mwes.acl2011.*;
import cz.zcu.luk.mwes.common.CommonMisc;
import cz.zcu.luk.mwes.mains.ScorersGroup;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 25.11.12
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class ComValScorersFactory {

    private ComValScorersFactory() {}

//    public static ArrayList<ComValScorer> getScorersNeightbors(ESemanticSpaceName spaceName) {
//        ArrayList<ComValScorer> allComValScorers = new ArrayList<ComValScorer>();
//        for (int i = 0; i <= 20; i++) {
//            for (int j = 0; j <= 20; j++) {
//                allComValScorers.add(
//                    new ComValScorerNeighbors(spaceName,
//                        ECompoundsOrdering.COSSIM, new ComValScorerNeighborsType(WeightingType.NONE,
//                        Operator.PLUS, false), i, j));
//                allComValScorers.add(
//                    new ComValScorerNeighbors(spaceName,
//                        ECompoundsOrdering.COSSIM, new ComValScorerNeighborsType(WeightingType.ALL_LOG,
//                        Operator.PLUS, false), i, j));
//            }
//            for (int j = 30; j <= 20; j++) {
//                allComValScorers.add(
//                        new ComValScorerNeighbors(spaceName,
//                                ECompoundsOrdering.COSSIM, new ComValScorerNeighborsType(WeightingType.NONE,
//                                Operator.PLUS, false), i, j));
//                allComValScorers.add(
//                        new ComValScorerNeighbors(spaceName,
//                                ECompoundsOrdering.COSSIM, new ComValScorerNeighborsType(WeightingType.ALL_LOG,
//                                Operator.PLUS, false), i, j));
//            }
//        }
//
//        return allComValScorers;
//    }

    private static ArrayList<ComValScorer> getScorersEndocentricity(List<String> spaceNames, List<ECompoundsOrdering> compoundsOrdering) {
        ArrayList<ComValScorer> allComValScorers = new ArrayList<ComValScorer>();
        for (String oneSpaceName : spaceNames) {
            for (ECompoundsOrdering oneCompoundsOrdering : compoundsOrdering) {
//                allComValScorers.add(new ComValScorerEndocentricity(oneSpaceName,
//                        oneCompoundsOrdering, EndocentricityType.HEAD_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(oneSpaceName,
//                        oneCompoundsOrdering, EndocentricityType.MODIFYING_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(oneSpaceName,
//                        oneCompoundsOrdering, EndocentricityType.MIN_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(oneSpaceName,
//                        oneCompoundsOrdering, EndocentricityType.MAX_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(oneSpaceName,
//                        oneCompoundsOrdering, EndocentricityType.HEAD_DIST_INV));
//                allComValScorers.add(new ComValScorerEndocentricity(oneSpaceName,
//                        oneCompoundsOrdering, EndocentricityType.AVG_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(oneSpaceName,
//                        oneCompoundsOrdering, EndocentricityType.MODIFYING_DIST_INV));
//                allComValScorers.add(new ComValScorerEndocentricity(oneSpaceName,
//                        oneCompoundsOrdering, EndocentricityType.MIN_DIST_INV));
//                allComValScorers.add(new ComValScorerEndocentricity(oneSpaceName,
//                        oneCompoundsOrdering, EndocentricityType.MAX_DIST_INV));
//                allComValScorers.add(new ComValScorerEndocentricity(oneSpaceName,
//                        oneCompoundsOrdering, EndocentricityType.AVG_DIST_INV));
            }
        }
        return allComValScorers;
    }

    public static ArrayList<ComValScorer> getTestScorersEndocentricity() {
        ArrayList<ComValScorer> allComValScorers = new ArrayList<ComValScorer>();

  //allComValScorers.add(new ComValScorerLoaderPMI("/storage/brno2/home/lkrcmar/MWE/misc/BadPMIwhenNoExpAndCountsllDocs22W3-M50-DelDfalse.txt"));


        return allComValScorers;
    }

    public static ArrayList<ComValScorer> getAllScorers() {
        ArrayList<ComValScorer> allComValScorers = new ArrayList<ComValScorer>();
        //addAllNeigboursScorers(allComValScorers);
        return  allComValScorers;
    }

    private static ArrayList<ComValScorer> getScorersNeightbors(List<String> spaceNames, List<ECompoundsOrdering> compoundsOrdering) {
        ArrayList<ComValScorer> allComValScorers = new ArrayList<ComValScorer>();

        // store all possible counts of neighbors into list
        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i <= 20; i++) {
            numbers.add(i);
        }
        for (int i = 30; i <= 100; i+=10) {
            numbers.add(i);
        }
//        for (int i = 200; i <= 1000; i+=100) {
//            numbers.add(i);
//        }
//		for (int i = 2000; i <= 4000; i+=1000) {
//			numbers.add(i);
//		}

        // store all possible comValScorerNeighborsTypes into List
        //List<ComValScorerNeighborsType> comValScorerNeighborsTypesUsed =
        //        ComValScorerNeighborsType.getAllComValScorersTypes();
        List<ComValScorerNeighborsType> comValScorerNeighborsTypesUsed = new ArrayList<ComValScorerNeighborsType>();
        comValScorerNeighborsTypesUsed.add(new ComValScorerNeighborsType(WeightingType.NONE, Operator.PLUS, false));
        comValScorerNeighborsTypesUsed.add(new ComValScorerNeighborsType(WeightingType.NONE, Operator.MULT, false));
        comValScorerNeighborsTypesUsed.add(new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false));
        comValScorerNeighborsTypesUsed.add(new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.MULT, false));


        // create all ComValScorerNeighbors scorers..
        for (String oneSemanticSpaceName : spaceNames) {
            for (ECompoundsOrdering oneCompoundsOrdering : compoundsOrdering) {
                for (ComValScorerNeighborsType oneComValScorerNeighborsType : comValScorerNeighborsTypesUsed) {
                    for (EExpressionViewed expViewed : EExpressionViewed.values()) {
                        for (int firstLimitNeigbors : numbers) {
                            for (int secondLimitNeigbors : numbers) {
                                allComValScorers.add(new ComValScorerNeighbors(oneSemanticSpaceName, oneCompoundsOrdering,
                                        oneComValScorerNeighborsType,
                                        new WordNeigborsCountLimits(expViewed, firstLimitNeigbors, secondLimitNeigbors)));
                            }
                        }
                    }
                }
            }
        }
        return allComValScorers;
    }

    public static ArrayList<ComValScorer> getScorersCompositionality(List<String> spaceNames, List<ECompoundsOrdering> compoundsOrdering) {
        ArrayList<ComValScorer> allComValScorers = new ArrayList<ComValScorer>();

        for (String oneSpaceName : spaceNames) {
            for (ECompoundsOrdering oneCompoundsOrdering : compoundsOrdering) {
                allComValScorers.add(new ComValScorerCompositionality(oneSpaceName,
                        oneCompoundsOrdering, ECompositionalityType.PLUS));
                allComValScorers.add(new ComValScorerCompositionality(oneSpaceName,
                        oneCompoundsOrdering, ECompositionalityType.POINTWISE_MULTIPLICATION));
            }
        }

        return allComValScorers;
    }

    public static ArrayList<ComValScorer> getScorersCompoNeigbors(List<String> spaceNames, List<ECompoundsOrdering> compoundsOrdering) {
        ArrayList<ComValScorer> allComValScorers = new ArrayList<ComValScorer>();

        for (String oneSpaceName : spaceNames) {
            for (ECompoundsOrdering oneCompoundsOrdering : compoundsOrdering) {
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_10));
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_20));
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_30));
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_40));
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_50));
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_100));
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_200));
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_300));
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_400));
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_500));
                allComValScorers.add(new ComValScorerCompositionalityNeighbors(oneSpaceName,
                        oneCompoundsOrdering, ECompoNeigborsType.COUNT_1000));
            }
        }

        return allComValScorers;
    }

    private static List<ESemanticSpaceName> getAllSpaceNames(char startLetter) {
        List<ESemanticSpaceName> spaceNames = new ArrayList<ESemanticSpaceName>();
        for (ESemanticSpaceName oneSpaceName : ESemanticSpaceName.values()) {
            if (oneSpaceName.toString().charAt(0) == startLetter) {
                spaceNames.add(oneSpaceName);
            }
        }
        return spaceNames;
    }


    private static ArrayList<ComValScorer> getAllScorers(List<String> spaceNames, List<ECompoundsOrdering> orderingList) {
        ArrayList<ComValScorer> comValScorers = new ArrayList<ComValScorer>();
        comValScorers.addAll(getScorersEndocentricity(spaceNames, orderingList));
        comValScorers.addAll(getScorersNeightbors(spaceNames, orderingList));
        comValScorers.addAll(getScorersCompositionality(spaceNames, orderingList));
        comValScorers.addAll(getScorersCompoNeigbors(spaceNames, orderingList));
        return comValScorers;
    }

    public static ArrayList<ComValScorer> getScorers(ScorersGroup scorersGroup) {
        ArrayList<ComValScorer> allComValScorers = new ArrayList<ComValScorer>();
        switch (scorersGroup) {
            case HYBRID: {
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_COALS_M10000000_N10000000_100ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.PLUS));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_100ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.AVG_SIM ));
                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_EXPS));
                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_LEFT));
                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_RIGHT));
                break;
            }
//            case HYBRID: {
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.AVG_SIM ));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.LEFT_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.RIGHT_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.AVG_SIM_NEG ));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.LEFT_SIM_NEG));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.RIGHT_SIM_NEG));
//
//
//                allComValScorers.add(new ComValScorerCompositionalityNeighbors(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX,
//                        ECompoundsOrdering.COSSIM, ECompoNeigborsType.COUNT_50));
//                allComValScorers.add(new ComValScorerCompositionalityNeighbors(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX,
//                        ECompoundsOrdering.COSSIM, ECompoNeigborsType.COUNT_500));
//
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 2, 2)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 0, 2)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 2, 0)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 10, 10)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 0, 10)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 10, 0)));
//
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.PLUS));
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.PLUS_NEG));
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_LSA_D300_LOGENT_10ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.POINTWISE_MULTIPLICATION));
//
//
//                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_EXPS));
//                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_LEFT));
//                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_RIGHT));
//                //COALS
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.AVG_SIM ));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.LEFT_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.RIGHT_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.AVG_SIM_NEG ));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.LEFT_SIM_NEG));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.RIGHT_SIM_NEG));
//
//
//                allComValScorers.add(new ComValScorerCompositionalityNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, ECompoNeigborsType.COUNT_50));
//                allComValScorers.add(new ComValScorerCompositionalityNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, ECompoNeigborsType.COUNT_500));
//
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.PLUS));
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.PLUS_NEG));
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.POINTWISE_MULTIPLICATION));
//                allComValScorers.add(new ComValScorerLoaderPMI("/storage/brno2/home/lkrcmar/MWE/pdtAllXXExpsCountsPMI-W2-M0-DelDfalse-retJNVfalseXX.txt"));
//                break;
//            }
//            case HYBRID: {
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.AVG_SIM ));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.LEFT_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.RIGHT_SIM));
//
//                allComValScorers.add(new ComValScorerCompositionalityNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, ECompoNeigborsType.COUNT_50));
//                allComValScorers.add(new ComValScorerCompositionalityNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX,
//                        ECompoundsOrdering.COSSIM, ECompoNeigborsType.COUNT_500));
//
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 2, 2)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 0, 2)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 2, 0)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 10, 10)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 0, 10)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 10, 0)));
//
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.PLUS));
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.PLUS_NEG));
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.POINTWISE_MULTIPLICATION));
//
//
//                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_EXPS));
//                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_LEFT));
//                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_RIGHT));
//                //allComValScorers.add(new ComValScorerLoaderPMI("/storage/brno2/home/lkrcmar/MWE/misc/BadPMIwhenNoExpAndCountsAllDocsXX-W3-M50-DelDfalse-retJNVfalseXX.txt"));
//                break;
//            }
//            case HYBRID: {
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_COALS_M10000000_N10000000_10ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.PLUS_NEG));
//                break;
//            }
//            case HYBRID: {
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.AVG_SIM ));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX,
//                          ECompoundsOrdering.COSSIM, EndocentricityType.LEFT_SIM ));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.RIGHT_SIM ));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.LEFT_SIM));
//                allComValScorers.add(new ComValScorerEndocentricity(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX,
//                        ECompoundsOrdering.COSSIM, EndocentricityType.RIGHT_SIM));
//
//                allComValScorers.add(new ComValScorerCompositionalityNeighbors(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX,
//                        ECompoundsOrdering.COSSIM, ECompoNeigborsType.COUNT_50));
//                allComValScorers.add(new ComValScorerCompositionalityNeighbors(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX,
//                        ECompoundsOrdering.COSSIM, ECompoNeigborsType.COUNT_500));
//                allComValScorers.add(new ComValScorerCompositionalityNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX,
//                        ECompoundsOrdering.COSSIM, ECompoNeigborsType.COUNT_50));
//                allComValScorers.add(new ComValScorerCompositionalityNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX,
//                        ECompoundsOrdering.COSSIM, ECompoNeigborsType.COUNT_500));
//
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 10, 0)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 0, 10)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 10, 10)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.HEAD_MODIFYING, 10, 0)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.HEAD_MODIFYING, 0, 10)));
////                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
////                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.HEAD_MODIFYING, 10, 10)));
//
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 2, 2)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 0, 2)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 2, 0)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 10, 10)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 0, 10)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 10, 0)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 2, 2)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 0, 2)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 2, 0)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 10, 10)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 0, 10)));
//                allComValScorers.add(new ComValScorerNeighbors(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX, ECompoundsOrdering.COSSIM,
//                        new ComValScorerNeighborsType(WeightingType.ALL_LOG, Operator.PLUS, false), new WordNeigborsCountLimits(EExpressionViewed.LEFT_RIGHT, 10, 0)));
//
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.PLUS));
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_COALS_M10000000_N10000000_5000ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.POINTWISE_MULTIPLICATION));
//                allComValScorers.add(new ComValScorerCompositionality(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX, ECompoundsOrdering.COSSIM, ECompositionalityType.PLUS));
//
////                ArrayList<ESemanticSpaceName> sNames = new ArrayList<ESemanticSpaceName>();
////                sNames.add(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX);
////                List<ECompoundsOrdering> compoundsOrdering = new ArrayList<ECompoundsOrdering>();
////                compoundsOrdering.add(ECompoundsOrdering.COSSIM);
////                //sNames.add(ESemanticSpaceName.H_LSA_D300_LOGENT_5000ALLXX);
////                allComValScorers.addAll(getScorersNeightbors(sNames, compoundsOrdering));
//
//                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_EXPS));
//                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_LEFT));
//                allComValScorers.add(new ComValScorerFrequency(EFrequencyType.F_RIGHT));
//                allComValScorers.add(new ComValScorerLoaderPMI("/storage/brno2/home/lkrcmar/MWE/misc/BadPMIwhenNoExpAndCountsAllDocsXX-W3-M50-DelDfalse-retJNVfalseXX.txt"));
//                break;
//            }

            default: {
                throw new IllegalArgumentException("Unknown group of scorers");
            }
        }
        return allComValScorers;
    }


    private static List<ECompoundsOrdering> getAllOrderings() {
        List<ECompoundsOrdering> orderings = new ArrayList<ECompoundsOrdering>();
        orderings.add(ECompoundsOrdering.COSSIM);
        orderings.add(ECompoundsOrdering.EUCLID);
        orderings.add(ECompoundsOrdering.PCORR);
        return orderings;
    }

    public static List<ComValScorer> getScorers(String scorersSettingsFN) {
        List<ComValScorer> comValScorers = new ArrayList<ComValScorer>();
        List<String> singleSettings = CommonMisc.readLinesFromFile(scorersSettingsFN);

        for (String oneSingleSetting : singleSettings) {
            comValScorers.add(parseSettings(oneSingleSetting));
        }

        return comValScorers;
    }

    private static ComValScorer parseSettings(String oneSingleSetting) {
        String[] pars = oneSingleSetting.split("\t");
        if (pars[0].equals("WSMbased")) {
            String wsmName = pars[1];
            if (pars[2].equals("endocentricity")) {
                ComValScorer comValScorer = new ComValScorerEndocentricity(wsmName,
                        ECompoundsOrdering.valueOf(pars[3]), EndocentricityType.valueOf(pars[4]));
                System.out.println(comValScorer.toString()  );
                return comValScorer;
            }
        }
        throw new IllegalArgumentException("Wrong parameters of scorer: " + oneSingleSetting);
    }
}
