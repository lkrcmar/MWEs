package cz.zcu.luk.mwes.acl2011;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 7.11.13
 * Time: 16:18
 * To change this template use File | Settings | File Templates.
 */
public class WordNeigborsCountLimits {
    public final EExpressionViewed expressionViewedAs;
    public final int firstNeigborCountLimit;
    public final int secondNeigborCountLimit;

    public WordNeigborsCountLimits(EExpressionViewed expressionViewedAs, int firstNeigborCountLimit, int secondNeigborCountLimit) {
        this.expressionViewedAs = expressionViewedAs;
        this.firstNeigborCountLimit = firstNeigborCountLimit;
        this.secondNeigborCountLimit = secondNeigborCountLimit;
    }

    public String toString() {
        switch (expressionViewedAs) {
            case LEFT_RIGHT: {
                return "L_" + firstNeigborCountLimit + "_R_" + secondNeigborCountLimit;
            }
            case HEAD_MODIFYING: {
                return "H_" + firstNeigborCountLimit + "_M_" + secondNeigborCountLimit;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordNeigborsCountLimits that = (WordNeigborsCountLimits) o;

        if (firstNeigborCountLimit != that.firstNeigborCountLimit) return false;
        if (secondNeigborCountLimit != that.secondNeigborCountLimit) return false;
        if (expressionViewedAs != that.expressionViewedAs) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = expressionViewedAs != null ? expressionViewedAs.hashCode() : 0;
        result = 31 * result + firstNeigborCountLimit;
        result = 31 * result + secondNeigborCountLimit;
        return result;
    }
}
