package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.common.Similarity;
import edu.ucla.sspace.vector.DenseVector;
import edu.ucla.sspace.vector.DoubleVector;
import edu.ucla.sspace.vector.VectorMath;
import edu.ucla.sspace.vector.Vectors;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 5.3.13
 * Time: 10:17
 * To change this template use File | Settings | File Templates.
 */
public class ComValScorerCompositionality extends ComValScorer {
    private static final GeneralScorerType generalScorerType = GeneralScorerType.COMPOSITIONALITY;

    private final String semanticSpaceName;
    private final ECompoundsOrdering compoundsOrdering;
    private final ECompositionalityType composType;
    private Map<CompoundSS, OneCompoundWithCompositionalityData> composData = null;

    public ComValScorerCompositionality(String semanticSpaceName,
                                        ECompoundsOrdering compoundsOrdering,
                                        ECompositionalityType composType) {
        this.semanticSpaceName = semanticSpaceName;
        this.compoundsOrdering = compoundsOrdering;
        this.composType = composType;
    }

    @Override
    protected void prepareDataForScoring(CompoundSet compoundSet) {
        composData = CompositionalityDataProvider.getInstance().getCompositionality(compoundSet,
                semanticSpaceName);
    }

    @Override
    protected void cleanDataForScoring() {
        composData = null;
    }

    @Override
    public GeneralScorerType getGeneralType() {
        return generalScorerType;
    }

    @Override
    public String getType() {
        return getGeneralType().toString()  + "_" + semanticSpaceName + "_" + compoundsOrdering.toString();
    }

    @Override
    public String getName() {
        return getType() + "_C_" + composType.toString();
    }

    @Override
    public String getPars() {
        return "S_" + compoundsOrdering.toString() +
                "_T_" + composType.toString().toLowerCase();
    }

    @Override
    public String getSpacePars() {
        return Common.extractSpacePars(semanticSpaceName);
    }

    public double getScore(CompoundSS compound) {
        OneCompoundWithCompositionalityData composDataForComp = composData.get(compound);
        DoubleVector compoundV = composDataForComp.getVectorExpression();
        DoubleVector leftWordVloaded = composDataForComp.getVectorLeftWord();
        DoubleVector rightWordVloaded = composDataForComp.getVectorRightWord();

        // adding dense vectors (maybe also multiplying) turns out to be much faster (seconds instead of minutes),
        // I do not know why..
        DoubleVector leftV;
        DoubleVector rightV;
        if (!(leftWordVloaded instanceof DenseVector)) {
            leftV = new DenseVector(leftWordVloaded.length());
            rightV = new DenseVector(rightWordVloaded.length());
            Vectors.copy(leftV, leftWordVloaded);
            Vectors.copy(rightV, rightWordVloaded);
        }
        else {
            leftV = leftWordVloaded;
            rightV = rightWordVloaded;
        }

        switch (composType) {
            case PLUS: {
                DoubleVector leftPlusRightV = VectorMath.addUnmodified(leftV, rightV);
                double simsim = Similarity.getSimilarity(AlternativesProvider.getInstance().castFrom(compoundsOrdering),
                        compoundV, leftPlusRightV);
                return simsim;
            }
            case PLUS_NEG: {
                DoubleVector leftPlusRightV = VectorMath.addUnmodified(leftV, rightV);
                double simsim = Similarity.getSimilarity(AlternativesProvider.getInstance().castFrom(compoundsOrdering),
                        compoundV, leftPlusRightV);
                return (-1)*simsim;
            }
            case POINTWISE_MULTIPLICATION: {
                DoubleVector headPointMultiplicationModV = VectorMath.multiplyUnmodified(leftV, rightV);
                //System.out.println(" | " + compound.toString() + "  " + Similarity.getSimilarity(AlternativesProvider.getInstance().castFrom(compoundsOrdering),
                        //compoundV, headPointMultiplicationModV) );
                return Similarity.getSimilarity(AlternativesProvider.getInstance().castFrom(compoundsOrdering),
                        compoundV, headPointMultiplicationModV);
            }
            default: {
                System.out.println("Error: unknown composType!!");
                System.exit(1);
                return 0;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComValScorerCompositionality that = (ComValScorerCompositionality) o;

        if (composData != null ? !composData.equals(that.composData) : that.composData != null) return false;
        if (composType != that.composType) return false;
        if (compoundsOrdering != that.compoundsOrdering) return false;
        if (semanticSpaceName != that.semanticSpaceName) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = semanticSpaceName != null ? semanticSpaceName.hashCode() : 0;
        result = 31 * result + (compoundsOrdering != null ? compoundsOrdering.hashCode() : 0);
        result = 31 * result + (composType != null ? composType.hashCode() : 0);
        result = 31 * result + (composData != null ? composData.hashCode() : 0);
        return result;
    }
}
