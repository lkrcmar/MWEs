package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.Similarity;
import cz.zcu.luk.sspace.common.WordComparatorChanged;
import edu.ucla.sspace.util.SortedMultiMap;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 29.11.12
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */
public class AlternativesProvider {

    private static final AlternativesProvider INSTANCE = new AlternativesProvider();

    private WordComparatorChanged wordComparatorChanged = null;

    private AlternativesProvider() {}

    public static AlternativesProvider getInstance() {
        return INSTANCE;
    }

    public ArrayList<SSWord> getAlternatives(SSWord word, String demandedType, SemanticSpace sspace, int neigboursCount,
                                             Similarity.SimType similarity) {
        if (wordComparatorChanged == null) {
            wordComparatorChanged = new WordComparatorChanged(similarity, true, Constants.TAG_LENGTH);
        }
        else {
            wordComparatorChanged.setSimType(similarity);
        }
        return findAlternatives(word, demandedType, sspace, neigboursCount);
    }

    private ArrayList<SSWord> findAlternatives(SSWord word, String demandedType, SemanticSpace sspace, int neigboursCount) {
        ArrayList<SSWord> neighbors = new ArrayList<SSWord>();
        // Using the provided or default arguments find the closest
        // neighbors to the target word in the current semantic space

        boolean ignoreTags = Common.ignoreTags(sspace);
        if (ignoreTags) demandedType = Constants.NOTAG;

        SortedMultiMap<Double,String> mostSimilar =
                wordComparatorChanged.getMostSimilar(word.getSSpaceRep(ignoreTags), demandedType, sspace, neigboursCount);

        if (mostSimilar == null) {
            System.out.println(word.getSSpaceRep(ignoreTags) + " is not in the current semantic space");
        }
        else {
            // reverse returned order to be A-Z
            synchronized(mostSimilar) {
                for (Map.Entry<Double,String> e : mostSimilar.entrySet()) {
                    neighbors.add(0, new SSWord(e.getValue()));
                }
            }
        }

        return  neighbors;
    }

    public Similarity.SimType castFrom(ECompoundsOrdering compoundsOrdering) {
        Similarity.SimType simType = null;

        if (compoundsOrdering == ECompoundsOrdering.COSSIM) {
            simType = Similarity.SimType.COSINE;
        }
        else if (compoundsOrdering == ECompoundsOrdering.EUCLID) {
            simType = Similarity.SimType.EUCLIDEAN;
        }
        else if (compoundsOrdering == ECompoundsOrdering.PCORR) {
            simType = Similarity.SimType.PEARSON_CORRELATION;
        }
        return  simType;
    }

    public ArrayList<SSWord> getAlternatives(SSWord word, String demandedType, SemanticSpace space, int neigborsCount,
                                             ECompoundsOrdering compoundsOrdering) {
        Similarity.SimType simType = castFrom(compoundsOrdering);
        return getAlternatives(word, demandedType, space, neigborsCount, simType);
    }
}
