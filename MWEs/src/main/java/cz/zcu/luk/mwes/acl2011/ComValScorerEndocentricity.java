package cz.zcu.luk.mwes.acl2011;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 11.11.12
 * Time: 19:28
 * To change this template use File | Settings | File Templates.
 */
public class ComValScorerEndocentricity extends ComValScorer {
    private static final GeneralScorerType generalScorerType = GeneralScorerType.ENDOCENTRICITY;

    private final String semanticSpaceName;
    private final ECompoundsOrdering compoundsOrdering;
    private final EndocentricityType endoType;

    private Map<CompoundSS, OneCompoundWithEndocentricityData> endoData = null;

    public ComValScorerEndocentricity(String semanticSpaceName,
                                      ECompoundsOrdering compoundsOrdering,
                                      EndocentricityType endoType) {
        this.semanticSpaceName = semanticSpaceName;
        this.compoundsOrdering = compoundsOrdering;
        this.endoType = endoType;
    }

    @Override
    protected void prepareDataForScoring(CompoundSet compoundSet) {
        endoData = EndocentricityDataProvider.getInstance().getEndocentricity(compoundSet,
                semanticSpaceName, compoundsOrdering);
    }

    @Override
    protected void cleanDataForScoring() {
        endoData = null;
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
        return getType() + "_E_" + endoType.toString();
    }

    @Override
    public String getPars() {
        return "S_" + compoundsOrdering.toString() + "_T_" + endoType.toString();
    }

    @Override
    public String getSpacePars() {
        return Common.extractSpacePars(semanticSpaceName);
    }

    public double getScore(CompoundSS compound) {
        OneCompoundWithEndocentricityData endoDataForComp = endoData.get(compound);
        switch (endoType) {
            case LEFT_SIM: {
                return endoDataForComp.getSimilarityLeft();
            }
            case RIGHT_SIM: {
                return endoDataForComp.getSimilarityRight();
            }
            case LEFT_SIM_NEG: {
                return (-1)*endoDataForComp.getSimilarityLeft();
            }
            case RIGHT_SIM_NEG: {
                return (-1)*endoDataForComp.getSimilarityRight();
            }
            case HEAD_SIM: {
                if (compound.leftWordIsHead()) {
                    return endoDataForComp.getSimilarityLeft();
                }
                else {
                    return endoDataForComp.getSimilarityRight();
                }
            }
            case MODIFYING_SIM: {
                if (compound.leftWordIsHead()) {
                    return endoDataForComp.getSimilarityRight();
                }
                else {
                    return endoDataForComp.getSimilarityLeft();
                }
            }
            case MAX_SIM: {
                return Math.max(endoDataForComp.getSimilarityLeft(), endoDataForComp.getSimilarityRight());
            }
            case MIN_SIM: {
                return Math.min(endoDataForComp.getSimilarityLeft(), endoDataForComp.getSimilarityRight());
            }
            case AVG_SIM: {
                return ((endoDataForComp.getSimilarityLeft() + endoDataForComp.getSimilarityRight()) / 2);
            }
            case AVG_SIM_NEG: {
                return (-1)*((endoDataForComp.getSimilarityLeft() + endoDataForComp.getSimilarityRight()) / 2);
            }
            case HEAD_DIST_INV: {
                if (compound.leftWordIsHead()) {
                    return (-1)*endoDataForComp.getDistanceLeft();
                }
                else {
                    return (-1)*endoDataForComp.getDistanceRight();
                }
            }
            case MODIFYING_DIST_INV: {
                if (compound.leftWordIsHead()) {
                    return (-1)*endoDataForComp.getDistanceRight();
                }
                else {
                    return (-1)*endoDataForComp.getDistanceLeft();
                }
            }
            case MAX_DIST_INV: {
                return (-1)*Math.max(endoDataForComp.getDistanceLeft(), endoDataForComp.getDistanceRight());
            }
            case MIN_DIST_INV: {
                return (-1)*Math.min(endoDataForComp.getDistanceLeft(), endoDataForComp.getDistanceRight());
            }
            case AVG_DIST_INV: {
                return (-1)*((endoDataForComp.getDistanceLeft() + endoDataForComp.getDistanceRight()) / 2);
            }
            default: {
                throw new IllegalArgumentException("Error: unknown endoType!!");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComValScorerEndocentricity that = (ComValScorerEndocentricity) o;

        if (compoundsOrdering != that.compoundsOrdering) return false;
        if (endoData != null ? !endoData.equals(that.endoData) : that.endoData != null) return false;
        if (endoType != that.endoType) return false;
        if (semanticSpaceName != that.semanticSpaceName) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = semanticSpaceName != null ? semanticSpaceName.hashCode() : 0;
        result = 31 * result + (compoundsOrdering != null ? compoundsOrdering.hashCode() : 0);
        result = 31 * result + (endoType != null ? endoType.hashCode() : 0);
        result = 31 * result + (endoData != null ? endoData.hashCode() : 0);
        return result;
    }
}
