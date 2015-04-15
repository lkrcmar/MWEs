package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.Similarity;

import java.util.LinkedHashMap;
import java.util.Map;

import cz.zcu.luk.mwes.common.LinkedHashMapLimited;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 29.11.12
 * Time: 16:15
 *
 * Singleton
 *
 * Provides given set of compounds with endocentricity data
 *
 * Expects the following constants to be set:
 *  Constants.NEIGHBOURS_COUNT
 *  Constants.MAX_DISTANCE
 *  Config.ACL2011_RESULTS_DIR
 * TODO: refactor - add Constants.NEIGHBORS_COUNT to dataName
 *
 */
public class EndocentricityDataProvider {
    private static final EndocentricityDataProvider INSTANCE = new EndocentricityDataProvider(
            Constants.NEIGHBOURS_COUNT);

    /** number of neigbors being compared */
    private final int neigboursCount;

    /** dataName (compoundSet-semanticSpace form) X data */
    private Map<String, CompoundsWithEndocentricityData> cashedData;

    private EndocentricityDataProvider(int neigboursCount) {
        if (neigboursCount > Constants.NEIGHBOURS_COUNT) {
            throw new IllegalArgumentException("not enough neigbors!");
        }
        this.neigboursCount = neigboursCount;
        cashedData = new LinkedHashMapLimited<String, CompoundsWithEndocentricityData>();
    }

    public static EndocentricityDataProvider getInstance() {
        return INSTANCE;
    }

    /** Provides given set of compounds with endocentricity data */
    public Map<CompoundSS, OneCompoundWithEndocentricityData> getEndocentricity (
            CompoundSet compoundSet,
            String semanticSpaceName,
            ECompoundsOrdering compoundsOrdering) {

        String dataName = "ENDOCENTRICITY-" + compoundSet.getName() + "-" + semanticSpaceName.toString() + "-" +  compoundsOrdering.toString();

        CompoundsWithEndocentricityData oneEndocentricityData = cashedData.get(dataName);

        if (oneEndocentricityData == null) { // data are not in cash
            oneEndocentricityData = (CompoundsWithEndocentricityData)DataSerializer.deserialiazeData(dataName);
            cashedData.put(dataName, oneEndocentricityData);
        }

        if (oneEndocentricityData == null) { // data are not in cash nor serialized
            SemanticSpace space = SSpaceProvider.getInstance().getSSpace(semanticSpaceName);
            //System.out.println("+"+space.getSpaceName());

            oneEndocentricityData = getEndocentricityData(compoundSet, space, compoundsOrdering);
            oneEndocentricityData.storeToFile(Config.ACL2011_RESULTS_DIR + dataName);

            DataSerializer.serializeData(oneEndocentricityData, dataName);
            cashedData.put(dataName, oneEndocentricityData); // cash data
        }

        return oneEndocentricityData.compoundsXendocentricity;
    }

    /**
     * loads endocentricity data from given semantic space
     */
    private CompoundsWithEndocentricityData getEndocentricityData(CompoundSet compoundSet, SemanticSpace space,
                                                                  ECompoundsOrdering compoundsOrdering) {
        Map<CompoundSS, OneCompoundWithEndocentricityData> compoundsXendocentricity = new LinkedHashMap<CompoundSS, OneCompoundWithEndocentricityData>();

        boolean ignoreTags = Common.ignoreTags(space);

        for (CompoundSS oneCompound : compoundSet.getCompounds()) {
            SSWord leftWordSS = oneCompound.getWordLeftSS();
            SSWord rightWordSS = oneCompound.getWordRightSS();
            SSWord compoundAsWord = new SSWord(oneCompound.getSSpaceRep(ignoreTags));
            //ACLCompoundTag compoundTag = oneCompound.getTag();
            //System.out.println(oneCompound.getSSpaceRep(ignoreTags));
            double simLeft;
            double simRight;
            int distanceLeft = Constants.MAX_DISTANCE;
            int distanceRight = Constants.MAX_DISTANCE;
            simLeft = Similarity.getSimilarity(AlternativesProvider.getInstance().castFrom(compoundsOrdering),
                    space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)),
                    space.getVector(leftWordSS.getSSpaceRep(ignoreTags)));
            simRight = Similarity.getSimilarity(AlternativesProvider.getInstance().castFrom(compoundsOrdering),
                    space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)),
                    space.getVector(rightWordSS.getSSpaceRep(ignoreTags)));

//  using distance is problematic in hybrid semantic spaces since their getWords returns
//  only main words and not the other words (lower freq e.g.)
//  therefore distances would be Constants.MAX_DISTANCE in most cases probably
//  solution: create alternative of hybrid semantic space with getWords method providing other words also..
//
//           ArrayList<SSWord> altsCompoundWordLeft = AlternativesProvider.getInstance().
//                    getAlternatives(compoundAsWord, firstWord.getTag().toString(), space, neigboursCount, compoundsOrdering);
//            int distance = 1;
//            for (SSWord oneSSWord : altsCompoundWordLeft) {
//                if (oneSSWord.getSSpaceRep().equals(firstWord.getSSpaceRep(ignoreTags))) {
//                    distanceLeft = distance;
//                }
//                distance++;
//            }
//            ArrayList<SSWord> altsCompoundWordRight = AlternativesProvider.getInstance().
//                    getAlternatives(compoundAsWord, secondWord.getTag().toString(), space, neigboursCount, compoundsOrdering);
//            distance = 1;
//            for (SSWord oneSSWord : altsCompoundWordRight) {
//                if (oneSSWord.getSSpaceRep().equals(secondWord.getSSpaceRep(ignoreTags))) {
//                    distanceRight = distance;
//                }
//                distance++;
//            }

            compoundsXendocentricity.put(oneCompound, new OneCompoundWithEndocentricityData(
                    simLeft, simRight, distanceLeft, distanceRight));
        }

        return new CompoundsWithEndocentricityData(compoundsXendocentricity, ignoreTags);
    }
}
