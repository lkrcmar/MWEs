package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.vector.DoubleVector;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 5.3.12
 * Time: 10:25
 * To change this template use File | Settings | File Templates.
 */
public class OneCompoundWithCompositionalityData implements Serializable {
    private final DoubleVector vectorExpression;
    private final DoubleVector vectorLeft;
    private final DoubleVector vectorRight;

    public OneCompoundWithCompositionalityData(DoubleVector vectorExpression,
                                               DoubleVector vectorLeft,
                                               DoubleVector vectorRight) {
        this.vectorExpression = vectorExpression;
        this.vectorLeft = vectorLeft;
        this.vectorRight = vectorRight;
    }

    public DoubleVector getVectorExpression() {
        return vectorExpression;
    }

    public DoubleVector getVectorLeftWord() {
        return vectorLeft;
    }

    public DoubleVector getVectorRightWord() {
        return vectorRight;
    }
}
