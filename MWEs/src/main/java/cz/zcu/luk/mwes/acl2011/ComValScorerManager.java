package cz.zcu.luk.mwes.acl2011;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 28.11.12
 * Time: 8:16
 * To change this template use File | Settings | File Templates.
 */
public class ComValScorerManager {
    public static void assignComposionalityScores(ArrayList<CompoundSet> compoundSets, List<ComValScorer> composValueScorers) {
        for (CompoundSet oneComSet: compoundSets) {
            // value scorer -> values assigned to given compounds..
            Map<ComValScorer, Map<CompoundSS, Double>> composValuesAssignedByDifferentScorers =
                    new LinkedHashMap<ComValScorer, Map<CompoundSS, Double>>();
            Collections.sort(composValueScorers, new Comparator<ComValScorer>() {
                public int compare(ComValScorer o1, ComValScorer o2) {
                    return o1.getNameWithoutGeneralType().compareTo(o2.getNameWithoutGeneralType());
                }
            });
            for (ComValScorer oneComValScorer: composValueScorers) {
                Map<CompoundSS, Double> oneComSetValueScores = oneComValScorer.getComposionalityValueScores(oneComSet);
                composValuesAssignedByDifferentScorers.put(oneComValScorer, oneComSetValueScores);
            }
            oneComSet.setCompositionalityValuesAssignedByDifferentScorers(composValuesAssignedByDifferentScorers);
        }
    }
}