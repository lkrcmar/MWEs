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
public class CompoNeigboursDataProvider {
    private static final CompoNeigboursDataProvider INSTANCE = new CompoNeigboursDataProvider(
            Constants.COMPO_NEIGHBOURS_COUNT);

    private final int neigboursCount;

    // dataName (compoundSet-semanticSpace-compoundsOrdering form) X data
    private Map<String, CompoundsWithCompoNeigborsData> cashedData;

    private CompoNeigboursDataProvider(int neigboursCount) {
        this.neigboursCount = neigboursCount;
        cashedData = new LinkedHashMapLimited<String, CompoundsWithCompoNeigborsData>();
    }

    public static CompoNeigboursDataProvider getInstance() {
        return INSTANCE;
    }

    public Map<CompoundSS, OneCompoundWithCompoNeighborsData> getCompoNeigbors(
            CompoundSet compoundSet,
            String semanticSpaceName, ECompoundsOrdering compoundsOrdering) {

        String dataName = "COMPONEIGHBORS-" + compoundSet.getName() + "-" + semanticSpaceName + "-" +  compoundsOrdering.toString();

        CompoundsWithCompoNeigborsData oneCompoNeigborsData = cashedData.get(dataName);

        if (oneCompoNeigborsData == null) {
            oneCompoNeigborsData = (CompoundsWithCompoNeigborsData)DataSerializer.deserialiazeData(dataName);
            cashedData.put(dataName, oneCompoNeigborsData);
        }

        if (oneCompoNeigborsData == null) {
            SemanticSpace space = SSpaceProvider.getInstance().getSSpace(semanticSpaceName);
            oneCompoNeigborsData = getCompoNeighborsData(compoundSet, space, compoundsOrdering);
            oneCompoNeigborsData.storeToFile(Config.ACL2011_RESULTS_DIR + dataName);

            DataSerializer.serializeData(oneCompoNeigborsData, dataName);
            cashedData.put(dataName, oneCompoNeigborsData);
        }

        return oneCompoNeigborsData.compoundsXcompoNeigborsData;
    }



    private CompoundsWithCompoNeigborsData getCompoNeighborsData(CompoundSet compoundSet, SemanticSpace space,
                                                            ECompoundsOrdering compoundsOrdering) {
        Map<CompoundSS, OneCompoundWithCompoNeighborsData> compoundsXalternatives = new LinkedHashMap<CompoundSS, OneCompoundWithCompoNeighborsData>();

        boolean ignoreTags = Common.ignoreTags(space);

        for (CompoundSS oneCompound : compoundSet.getCompounds()) {
            SSWord leftWordSS = oneCompound.getWordLeftSS();
            SSWord rightWordSS = oneCompound.getWordRightSS();
            SSWord compoundAsWordSS = new SSWord(oneCompound.getSSpaceRep(ignoreTags));

            ArrayList<SSWord> altsCompound = AlternativesProvider.getInstance().getAlternatives(compoundAsWordSS, "ANY", space, neigboursCount, compoundsOrdering);
            ArrayList<SSWord> altsFirstWord = AlternativesProvider.getInstance().getAlternatives(leftWordSS, "ANY", space, neigboursCount, compoundsOrdering);
            ArrayList<SSWord> altsSecondWord = AlternativesProvider.getInstance().getAlternatives(rightWordSS, "ANY", space, neigboursCount, compoundsOrdering);

            List<String> alternativesCompound = new ArrayList<String>();
            List<String> alternativesLeftWord = new ArrayList<String>();
            List<String> alternativesRightWord = new ArrayList<String>();

            if (altsFirstWord != null && !altsFirstWord.isEmpty()) {
                for (SSWord firstWordAlt: altsFirstWord) {
                    String wordAltStr = firstWordAlt.getSSpaceRep();
                    alternativesLeftWord.add(wordAltStr);
                }
            }
            else {
                System.out.println(leftWordSS.getSSpaceRep(ignoreTags) + " does not have neighbours in sspace..");
            }

            if (altsSecondWord != null && !altsSecondWord.isEmpty()) {
                for (SSWord secondWordAlt: altsSecondWord) {
                    String wordAltStr = secondWordAlt.getSSpaceRep();
                    alternativesRightWord.add(wordAltStr);
                }
            }
            else {
                System.out.println(rightWordSS.getSSpaceRep(ignoreTags) + " does not have neighbours in sspace..");
            }

            if (altsCompound != null && !altsCompound.isEmpty()) {
                for (SSWord compoundAlt: altsCompound) {
                    String wordAltStr = compoundAlt.getSSpaceRep();
                    alternativesCompound.add(wordAltStr);
                }
            }
            else {
                System.out.println(leftWordSS.getSSpaceRep(ignoreTags) + " does not have neighbours in sspace..");
            }

            compoundsXalternatives.put(oneCompound, new OneCompoundWithCompoNeighborsData(alternativesCompound,
                    alternativesLeftWord,
                    alternativesRightWord
            ));
        }

        return new CompoundsWithCompoNeigborsData(compoundsXalternatives, ignoreTags);
    }
}
