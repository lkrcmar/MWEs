package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 15.11.12
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class ComValScorerNeighborsType {
    private final WeightingType weighting;
    private final Operator operator;
    private final boolean origCountedInAlternatives;

    public WeightingType getWeighting() {
        return weighting;
    }

    public Operator getOperator() {
        return operator;
    }

    public boolean isOrigCountedInAlternatives() {
        return origCountedInAlternatives;
    }

    public static ArrayList<ComValScorerNeighborsType> getAllComValScorersTypes() {
        ArrayList<ComValScorerNeighborsType> comValScorerNeighborsTypes = new ArrayList<ComValScorerNeighborsType>();
        for (WeightingType oneWeightingType : WeightingType.values()) {
            for (Operator oneOperator : Operator.values()) {
                comValScorerNeighborsTypes.add(new ComValScorerNeighborsType(oneWeightingType, oneOperator, true));
                comValScorerNeighborsTypes.add(new ComValScorerNeighborsType(oneWeightingType, oneOperator, false));
            }
        }
        return comValScorerNeighborsTypes;
    }

    public ComValScorerNeighborsType(WeightingType weighting, Operator operator,
                                     boolean origCountedInAlternatives) {
        this.weighting = weighting;
        this.operator = operator;
        this.origCountedInAlternatives = origCountedInAlternatives;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComValScorerNeighborsType that = (ComValScorerNeighborsType) o;

        if (origCountedInAlternatives != that.origCountedInAlternatives) return false;
        if (operator != that.operator) return false;
        if (weighting != that.weighting) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = weighting != null ? weighting.hashCode() : 0;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (origCountedInAlternatives ? 1 : 0);
        return result;
    }
}
