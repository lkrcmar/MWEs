package cz.zcu.luk.mwes.acl2011;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 1.11.13
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public class ComValScorerFrequency extends ComValScorer {
    private static final GeneralScorerType generalScorerType = GeneralScorerType.FREQUENCY;
    private final EFrequencyType freqType;

    private Map<CompoundSS, OneCompoundWithFrequencyData> freqData = null;

    public ComValScorerFrequency(EFrequencyType freqType) {
        this.freqType = freqType;
    }

    @Override
    public GeneralScorerType getGeneralType() {
        return generalScorerType;
    }

    @Override
    public String getType() {
        return getGeneralType().toString()  + "_noSemSpace";
    }

    @Override
    public String getName() {
        return getType() + "_F_" + freqType.toString();
    }

    @Override
    public String getPars() {
        return "NotImplemented";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getSpacePars() {
        return "NotImplemented";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void prepareDataForScoring(CompoundSet compoundSet) {
        freqData = FrequencyDataProvider.getInstance().getFrequencies(compoundSet);
    }

    @Override
    protected void cleanDataForScoring() {
        freqData = null;
    }

    @Override
    protected double getScore(CompoundSS compound) {
        OneCompoundWithFrequencyData freqDataForComp = freqData.get(compound);
        switch (freqType) {
            case F_EXPS: {
                return freqDataForComp.getFrequencyOfExpression();
            }
            case F_LEFT: {
                return freqDataForComp.getFrequencyOfLeftWord();
            }
            case F_RIGHT: {
                return freqDataForComp.getFrequencyOfRightWord();
            }
            default: {
                System.out.println("Error: unknown freqType!!");
                System.exit(1);
                return 0;
            }
        }
    }
}
