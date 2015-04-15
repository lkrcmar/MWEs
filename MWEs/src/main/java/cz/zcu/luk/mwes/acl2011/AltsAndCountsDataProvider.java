package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.common.SemanticSpace;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cz.zcu.luk.mwes.common.LinkedHashMapLimited;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 27.11.12
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class AltsAndCountsDataProvider {
    private static final AltsAndCountsDataProvider INSTANCE = new AltsAndCountsDataProvider(
            Constants.NEIGHBOURS_COUNT);

    private final int neigboursCount;

    // dataName (compoundSet-semanticSpace-compoundsOrdering form) X data
    private Map<String, CompoundsWithAltsAndCountsData> cashedData;

    private AltsAndCountsDataProvider(int neigboursCount) {
        this.neigboursCount = neigboursCount;
        cashedData = new LinkedHashMapLimited<String, CompoundsWithAltsAndCountsData>();
    }

    public static AltsAndCountsDataProvider getInstance() {
        return INSTANCE;
    }

    public Map<CompoundSS, OneCompoundWithAltsAndCountsData> getAltsAndCounts(
            CompoundSet compoundSet,
            String semanticSpaceName, ECompoundsOrdering compoundsOrdering) {

        String dataName = "NEIGHBORS-" + compoundSet.getName() + "-" + semanticSpaceName + "-" +  compoundsOrdering.toString();

        CompoundsWithAltsAndCountsData oneAltsAndCountsData = cashedData.get(dataName);

        if (oneAltsAndCountsData == null) {
            oneAltsAndCountsData = (CompoundsWithAltsAndCountsData)DataSerializer.deserialiazeData(dataName);
            cashedData.put(dataName, oneAltsAndCountsData);
        }

        if (oneAltsAndCountsData == null) {
            SemanticSpace space = SSpaceProvider.getInstance().getSSpace(semanticSpaceName);
            oneAltsAndCountsData = getAltsAndCounts(compoundSet, space, compoundsOrdering);
            oneAltsAndCountsData.storeToFile(Config.ACL2011_RESULTS_DIR + dataName);

            DataSerializer.serializeData(oneAltsAndCountsData, dataName);
            cashedData.put(dataName, oneAltsAndCountsData);
        }

        return oneAltsAndCountsData.compoundsXaltsAndCount;
    }

    private CompoundsWithAltsAndCountsData getAltsAndCounts(CompoundSet compoundSet, SemanticSpace space,
                                                            ECompoundsOrdering compoundsOrdering) {
        Map<CompoundSS, OneCompoundWithAltsAndCountsData> compoundsXaltsAndCount = new LinkedHashMap<CompoundSS, OneCompoundWithAltsAndCountsData>();

        boolean ignoreTags = Common.ignoreTags(space);

        for (CompoundSS oneCompound : compoundSet.getCompounds()) {
            SSWord leftWordSS = oneCompound.getWordLeftSS();
            SSWord rightWordSS = oneCompound.getWordRightSS();

            ArrayList<SSWord> altsLeftWordSS = AlternativesProvider.getInstance().getAlternatives(leftWordSS, null, space, neigboursCount, compoundsOrdering);
            ArrayList<SSWord> altsRightWordSS = AlternativesProvider.getInstance().getAlternatives(rightWordSS, null, space, neigboursCount, compoundsOrdering);

            CompoundsCountsProvider comCountsProv;
            if (ignoreTags) {
                comCountsProv = UKWACcompoundsCountsXX.getInstance();
            }
            else {
                comCountsProv = UKWACcompoundsCounts.getInstance();
            }

            List<CompoundWithCount> alternativesLeftWordReplaced = new ArrayList<CompoundWithCount>();
            List<CompoundWithCount> alternativesRightWordReplaced = new ArrayList<CompoundWithCount>();
            if (altsRightWordSS != null && !altsRightWordSS.isEmpty()) {
                for (SSWord rightWordAltSS: altsRightWordSS) {
                    String compoundAltStr = leftWordSS.getSSpaceRep(ignoreTags) + " " + rightWordAltSS.getSSpaceRep();
                    int compCount = comCountsProv.getOccurrenceOfCompound(compoundAltStr);
                    alternativesRightWordReplaced.add(new CompoundWithCount(compoundAltStr, compCount));
                }
            }
            else {
                throw new IllegalStateException("For the following right word, alternatives were not found: " + rightWordSS.getSSpaceRep(ignoreTags));
            }

            if (altsLeftWordSS != null && !altsLeftWordSS.isEmpty()) {
                for (SSWord leftWordAltSS: altsLeftWordSS) {
                    String compoundAltStr = leftWordAltSS.getSSpaceRep() + " " + rightWordSS.getSSpaceRep(ignoreTags);
                    int compCount = comCountsProv.getOccurrenceOfCompound(compoundAltStr);
                    alternativesLeftWordReplaced.add(new CompoundWithCount(compoundAltStr, compCount));
                }
            }
            else {
                throw new IllegalStateException("For the following left word, alternatives were not found: " + leftWordSS.getSSpaceRep(ignoreTags));
            }

            compoundsXaltsAndCount.put(oneCompound, new OneCompoundWithAltsAndCountsData(
                    comCountsProv.getOccurrenceOfCompound(oneCompound.getSSpaceRep(ignoreTags)),
                    alternativesLeftWordReplaced, alternativesRightWordReplaced));
        }

        return new CompoundsWithAltsAndCountsData(compoundsXaltsAndCount, ignoreTags);
    }
}
