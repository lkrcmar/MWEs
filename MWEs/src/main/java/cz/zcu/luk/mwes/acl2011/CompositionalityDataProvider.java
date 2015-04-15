package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.vector.DenseVector;
import edu.ucla.sspace.vector.DoubleVector;
import edu.ucla.sspace.vector.SparseVector;
import edu.ucla.sspace.vector.Vectors;

import java.util.LinkedHashMap;
import java.util.Map;

import cz.zcu.luk.mwes.common.LinkedHashMapLimited;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 5.3.12
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public class CompositionalityDataProvider {
    private static final CompositionalityDataProvider INSTANCE = new CompositionalityDataProvider();

    // dataName (compoundSet-semanticSpace form) X data
    private Map<String, CompoundsWithCompositionalityData> cashedData;

    private CompositionalityDataProvider() {
        cashedData = new LinkedHashMapLimited<String, CompoundsWithCompositionalityData>();
    }

    public static CompositionalityDataProvider getInstance() {
        return INSTANCE;
    }

    public Map<CompoundSS, OneCompoundWithCompositionalityData> getCompositionality (
            CompoundSet compoundSet,
            String semanticSpaceName) {

        String dataName = "COMPOSITIONALITY-" + compoundSet.getName() + "-" + semanticSpaceName;

        CompoundsWithCompositionalityData oneCompositionalityData = cashedData.get(dataName);

        if (oneCompositionalityData == null) {
            oneCompositionalityData = (CompoundsWithCompositionalityData)DataSerializer.deserialiazeData(dataName);
            cashedData.put(dataName, oneCompositionalityData);
        }

        if (oneCompositionalityData == null) {
            SemanticSpace space = SSpaceProvider.getInstance().getSSpace(semanticSpaceName);
            oneCompositionalityData = getCompositionalityData(compoundSet, space);
           // oneCompositionalityData.storeToFile(Config.ACL2011_RESULTS_DIR + dataName);

            DataSerializer.serializeData(oneCompositionalityData, dataName);
            cashedData.put(dataName, oneCompositionalityData);
        }

        return oneCompositionalityData.compoundsXcompositionality;
    }

    private CompoundsWithCompositionalityData getCompositionalityData(CompoundSet compoundSet, SemanticSpace space) {
        Map<CompoundSS, OneCompoundWithCompositionalityData> compoundsXcompositionality = new LinkedHashMap<CompoundSS, OneCompoundWithCompositionalityData>();

        boolean ignoreTags = Common.ignoreTags(space);

        for (CompoundSS oneCompound : compoundSet.getCompounds()) {
            SSWord leftWordSS = oneCompound.getWordLeftSS();
            SSWord rightWordSS = oneCompound.getWordRightSS();
            SSWord compoundAsWord = new SSWord(oneCompound.getSSpaceRep(ignoreTags));
            //ACLCompoundTag compoundTag = oneCompound.getTag();


            // not sure why copying.. perhaps some implementation of vectors causes troubles in some
            // compositionality counting..
//            DoubleVector vectorCompound = new DenseVector(space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)).length());
//            DoubleVector vectorLeft = new DenseVector(space.getVector(firstWord.getSSpaceRep(ignoreTags)).length());
//            DoubleVector vectorRight = new DenseVector(space.getVector(secondWord.getSSpaceRep(ignoreTags)).length());
//            Vectors.copy(vectorCompound, space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)));
//            Vectors.copy(vectorLeft, space.getVector(firstWord.getSSpaceRep(ignoreTags)));
//            Vectors.copy(vectorRight, space.getVector(secondWord.getSSpaceRep(ignoreTags)));

//     aa       DoubleVector vectorCompound = new DenseVector(space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)).length());
//            DoubleVector vectorLeft = new DenseVector(space.getVector(firstWord.getSSpaceRep(ignoreTags)).length());
//            DoubleVector vectorRight = new DenseVector(space.getVector(secondWord.getSSpaceRep(ignoreTags)).length());
//            Vectors.copy(vectorCompound, space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)));
//            Vectors.copy(vectorLeft, space.getVector(firstWord.getSSpaceRep(ignoreTags)));
//            Vectors.copy(vectorRight, space.getVector(secondWord.getSSpaceRep(ignoreTags)));
//            DoubleVector vectorCompound = new DenseVector(space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)).length());
//            DoubleVector vectorLeft = new DenseVector(space.getVector(firstWord.getSSpaceRep(ignoreTags)).length());
//            DoubleVector vectorRight = new DenseVector(space.getVector(secondWord.getSSpaceRep(ignoreTags)).length());
//            Vectors.copy(vectorCompound, space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)));
//            Vectors.copy(vectorLeft, space.getVector(firstWord.getSSpaceRep(ignoreTags)));
//            Vectors.copy(vectorRight, space.getVector(secondWord.getSSpaceRep(ignoreTags)));


            //DoubleVector vectorCompound = copyVector(space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)));

            DoubleVector vectorCompound;
            DoubleVector vectorLeft;
            DoubleVector vectorRight;
            if (space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)) instanceof SparseVector) {
                vectorCompound = Vectors.asDouble(space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)));
                //System.out.println(vectorCompound.getClass().getName());
                vectorLeft = Vectors.asDouble(space.getVector(leftWordSS.getSSpaceRep(ignoreTags)));
                //System.out.println(" " + vectorLeft.getClass().getName());
                vectorRight = Vectors.asDouble(space.getVector(rightWordSS.getSSpaceRep(ignoreTags)));
            }
            else {
                // be aware of edu.ucla.sspace.matrix.ArrayMatrix$RowVector - which is dense! and wrongly
                // it is not an instance of DenseVector
                // e.g. LSA matrix is loaded as ArrayMatrix..
                vectorCompound = new DenseVector(space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)).length());
                vectorLeft = new DenseVector(space.getVector(leftWordSS.getSSpaceRep(ignoreTags)).length());
                vectorRight = new DenseVector(space.getVector(rightWordSS.getSSpaceRep(ignoreTags)).length());
                Vectors.copy(vectorCompound, space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)));
                Vectors.copy(vectorLeft, space.getVector(leftWordSS.getSSpaceRep(ignoreTags)));
                Vectors.copy(vectorRight, space.getVector(rightWordSS.getSSpaceRep(ignoreTags)));
            }

//      works big LSA?!      DoubleVector vectorCompound = Vectors.asDouble(space.getVector(compoundAsWord.getSSpaceRep(ignoreTags)));
//            System.out.println(vectorCompound.getClass().getName());
//            DoubleVector vectorLeft = Vectors.asDouble(space.getVector(firstWord.getSSpaceRep(ignoreTags)));
//            System.out.println(" " +vectorLeft.getClass().getName());
//            DoubleVector vectorRight = Vectors.asDouble(space.getVector(secondWord.getSSpaceRep(ignoreTags)));

            compoundsXcompositionality.put(oneCompound, new OneCompoundWithCompositionalityData(
                    vectorCompound, vectorLeft, vectorRight));
        }

        return new CompoundsWithCompositionalityData(compoundsXcompositionality, ignoreTags);
    }

    //private DoubleVector copyVector(DoubleVector vector) {
    // }
}
