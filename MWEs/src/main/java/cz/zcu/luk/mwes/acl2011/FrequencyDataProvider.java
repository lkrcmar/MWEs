package cz.zcu.luk.mwes.acl2011;

import java.util.LinkedHashMap;
import java.util.Map;

import cz.zcu.luk.mwes.common.LinkedHashMapLimited;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 1.11.13
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
public class FrequencyDataProvider {
    private static final FrequencyDataProvider INSTANCE = new FrequencyDataProvider();

    // dataName (compoundSet-freqType form) X data
    private Map<String, CompoundsWithFrequencyData> cashedData;

    private FrequencyDataProvider() {
        cashedData = new LinkedHashMapLimited<String, CompoundsWithFrequencyData>();
    }

    public static FrequencyDataProvider getInstance() {
        return INSTANCE;
    }

    public Map<CompoundSS, OneCompoundWithFrequencyData> getFrequencies(
            CompoundSet compoundSet) {

        String dataName = "FREQUENCIES-" + compoundSet.getName();

        CompoundsWithFrequencyData oneFrequencyData = cashedData.get(dataName);

        if (oneFrequencyData == null) {
            oneFrequencyData = (CompoundsWithFrequencyData)DataSerializer.deserialiazeData(dataName);
            cashedData.put(dataName, oneFrequencyData);
        }

        if (oneFrequencyData == null) {
            oneFrequencyData = getFrequencyData(compoundSet);
            oneFrequencyData.storeToFile(Config.ACL2011_RESULTS_DIR + dataName);

            DataSerializer.serializeData(oneFrequencyData, dataName);
            cashedData.put(dataName, oneFrequencyData);
        }

        return oneFrequencyData.compoundsXfrequencies;
    }

    private CompoundsWithFrequencyData getFrequencyData(CompoundSet compoundSet) {
        Map<CompoundSS, OneCompoundWithFrequencyData> compoundsXfreqs = new LinkedHashMap<CompoundSS, OneCompoundWithFrequencyData>();

        boolean ignoreTags = Constants.IGNORE_TAGS;

        String expsCountsFN = Config.EXPRESSION_COUNTS_FILE_NAME;
        String wordsCountsFN = Config.WORDS_COUNTS_FILE_NAME;

        ExpressionsWithValsLoader expsDataLoader = new ExpressionsWithValsLoader(expsCountsFN);
        WordsWithValsLoader wordsDataLoader = new WordsWithValsLoader(wordsCountsFN);

        for (CompoundSS oneCompound : compoundSet.getCompounds()) {
            SSWord leftWordSS = oneCompound.getWordLeftSS();
            SSWord rightWordSS = oneCompound.getWordRightSS();
            SSWord compoundAsWord = new SSWord(oneCompound.getSSpaceRep(ignoreTags));

            int freqExps = (int)expsDataLoader.getValueForExpression(compoundAsWord.getSSpaceRep(ignoreTags), 1);
            int freqLeftWord = (int)wordsDataLoader.getValueForWord(leftWordSS.getSSpaceRep(ignoreTags), 1);
            int freqRightWord = (int)wordsDataLoader.getValueForWord(rightWordSS.getSSpaceRep(ignoreTags), 1);
            compoundsXfreqs.put(oneCompound, new OneCompoundWithFrequencyData(
                    freqExps, freqLeftWord, freqRightWord));
        }

        return new CompoundsWithFrequencyData(compoundsXfreqs, ignoreTags);
    }

}
