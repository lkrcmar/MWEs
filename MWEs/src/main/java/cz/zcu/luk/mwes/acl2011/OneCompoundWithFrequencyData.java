package cz.zcu.luk.mwes.acl2011;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 1.11.13
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class OneCompoundWithFrequencyData implements Serializable {
    private final int frequencyOfExpression;
    private final int frequencyOfLeftWord;
    private final int frequencyOfRightWord;

    public double getFrequencyOfExpression() {
        return frequencyOfExpression;
    }

    public double getFrequencyOfLeftWord() {
        return frequencyOfLeftWord;
    }

    public int getFrequencyOfRightWord() {
        return frequencyOfRightWord;
    }

    public OneCompoundWithFrequencyData(int frequencyOfExpression, int frequencyOfLeftWord,
                                        int frequencyOfRightWord) {
        this.frequencyOfExpression = frequencyOfExpression;
        this.frequencyOfLeftWord = frequencyOfLeftWord;
        this.frequencyOfRightWord = frequencyOfRightWord;
    }
}
