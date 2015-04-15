package cz.zcu.luk.mwes.acl2011;

import java.util.List;
import java.util.Map;

public class ComValScorerCompositionalityNeighbors extends ComValScorer {
    private static final GeneralScorerType generalScorerType = GeneralScorerType.COMPONEIGBORS;

    private final String semanticSpaceName;
    private final ECompoundsOrdering compoundsOrdering;
    private final ECompoNeigborsType compoNeigborsType;
	private final int neighborsCount;

    private Map<CompoundSS, OneCompoundWithCompoNeighborsData> compoNeigborsData = null;

	public ComValScorerCompositionalityNeighbors(String semanticSpaceName,
                                                 ECompoundsOrdering compoundsOrdering,
                                                 ECompoNeigborsType compoNeigborsType) {
        this.semanticSpaceName = semanticSpaceName;
        this.compoundsOrdering = compoundsOrdering;
        this.compoNeigborsType = compoNeigborsType;
        switch (compoNeigborsType) {
            case COUNT_10: {
                this.neighborsCount = 10;
                break;
            }
            case COUNT_20: {
                this.neighborsCount = 20;
                break;
            }
            case COUNT_30: {
                this.neighborsCount = 30;
                break;
            }
            case COUNT_40: {
                this.neighborsCount = 40;
                break;
            }
            case COUNT_50: {
                this.neighborsCount = 50;
                break;
            }
            case COUNT_100: {
                this.neighborsCount = 100;
                break;
            }
            case COUNT_200: {
                this.neighborsCount = 200;
                break;
            }
            case COUNT_300: {
                this.neighborsCount = 300;
                break;
            }
            case COUNT_400: {
                this.neighborsCount = 400;
                break;
            }
            case COUNT_500: {
                this.neighborsCount = 500;
                break;
            }case COUNT_1000: {
                this.neighborsCount = 1000;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown neigbors count!");
            }
        }
		if (neighborsCount > Constants.COMPO_NEIGHBOURS_COUNT) {
            throw new IllegalArgumentException("Error: not enough neighbors..");
        }
	}

	protected double getAlternativesOverlap(CompoundSS compound) {
        OneCompoundWithCompoNeighborsData oneCompoundWithCompoNeighborsData = compoNeigborsData.get(compound);
        List<String> neigborsOfCompound = oneCompoundWithCompoNeighborsData.neigborsCompound.subList(0,neighborsCount);
        List<String> neigborsOfLeftWord = oneCompoundWithCompoNeighborsData.neigborsLeftWord.subList(0,neighborsCount);
        List<String> neigborsOfRightWord = oneCompoundWithCompoNeighborsData.neigborsRightWord.subList(0,neighborsCount);
        int overlapLeftWord = 0;
        for (int j = 0; j < neigborsOfCompound.size(); j++) {
            if (neigborsOfLeftWord.contains(neigborsOfCompound.get(j))) {
                overlapLeftWord++;
            }
        }
        int overlapRightWord = 0;
        for (int j = 0; j < neigborsOfCompound.size(); j++) {
            if (neigborsOfRightWord.contains(neigborsOfCompound.get(j))) {
                overlapRightWord++;
            }
        }
        //overlapModifying =0;
        return ((double)overlapLeftWord + (double)overlapRightWord);// / 2.0;
	}

    @Override
    protected void prepareDataForScoring(CompoundSet compoundSet) {
        compoNeigborsData = CompoNeigboursDataProvider.getInstance().
                getCompoNeigbors(compoundSet, semanticSpaceName, compoundsOrdering);
    }

    @Override
    protected void cleanDataForScoring() {
        compoNeigborsData = null;
    }

    @Override
	protected double getScore(CompoundSS compound) {
        return getAlternativesOverlap(compound);
	}

    @Override
    public GeneralScorerType getGeneralType() {
        return generalScorerType;
    }

    @Override
    public String getType() {
        return getGeneralType().toString() + "_" + semanticSpaceName + "_" + compoundsOrdering.toString() +
                "_" + compoNeigborsType.toString();
    }

    @Override
	public String getName() {
		return getType() + "_C_" + neighborsCount;
	}


    @Override
    public String getPars() {
        return "S_" + compoundsOrdering.toString() +
                "_C_" + neighborsCount;
    }

    @Override
    public String getSpacePars() {
        return Common.extractSpacePars(semanticSpaceName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComValScorerCompositionalityNeighbors that = (ComValScorerCompositionalityNeighbors) o;

        if (neighborsCount != that.neighborsCount) return false;
        if (compoNeigborsData != null ? !compoNeigborsData.equals(that.compoNeigborsData) : that.compoNeigborsData != null)
            return false;
        if (compoNeigborsType != that.compoNeigborsType) return false;
        if (compoundsOrdering != that.compoundsOrdering) return false;
        if (semanticSpaceName != that.semanticSpaceName) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = semanticSpaceName != null ? semanticSpaceName.hashCode() : 0;
        result = 31 * result + (compoundsOrdering != null ? compoundsOrdering.hashCode() : 0);
        result = 31 * result + (compoNeigborsType != null ? compoNeigborsType.hashCode() : 0);
        result = 31 * result + neighborsCount;
        result = 31 * result + (compoNeigborsData != null ? compoNeigborsData.hashCode() : 0);
        return result;
    }
}
