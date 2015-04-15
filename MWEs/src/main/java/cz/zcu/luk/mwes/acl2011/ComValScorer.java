package cz.zcu.luk.mwes.acl2011;

import java.util.LinkedHashMap;

public abstract class ComValScorer {

    abstract public GeneralScorerType getGeneralType();

    abstract public String getType();

    abstract public String getName();

    abstract public String getPars();

    abstract public String getSpacePars();

    public String getNameWithoutGeneralType() {
        // -1 ... do not include "_"
        return getName().substring(getGeneralType().toString().length() + 1);
    }

    abstract protected void prepareDataForScoring(CompoundSet compoundSet);
    abstract protected void cleanDataForScoring();
	
	abstract protected double getScore(CompoundSS compound);

	public LinkedHashMap<CompoundSS, Double> getComposionalityValueScores(CompoundSet compoundSet) {
        prepareDataForScoring(compoundSet);
		LinkedHashMap<CompoundSS, Double> compoundCompositionalityValues = new LinkedHashMap<CompoundSS, Double>();
		for (CompoundSS compound: compoundSet.getCompounds()) {
			compoundCompositionalityValues.put(compound, getScore(compound));
		}
        cleanDataForScoring();
		return compoundCompositionalityValues;
	}

    @Override
    public String toString() {
        return getName();
    }
}
