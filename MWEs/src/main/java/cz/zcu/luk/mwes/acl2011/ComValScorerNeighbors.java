package cz.zcu.luk.mwes.acl2011;

import java.util.Map;

public class ComValScorerNeighbors extends ComValScorer {
    private static final GeneralScorerType generalScorerType = GeneralScorerType.NEIGHBOURS;

    private final String semanticSpaceName;
    private final ECompoundsOrdering compoundsOrdering;
    private final WordNeigborsCountLimits neigborsCountLimits;
    private final ComValScorerNeighborsType comValScorerNeighborsType;

    private Map<CompoundSS, OneCompoundWithAltsAndCountsData> altsAndCountsData = null;

	public ComValScorerNeighbors(String semanticSpaceName,
                                 ECompoundsOrdering compoundsOrdering,
                                 ComValScorerNeighborsType comValScorerNeighborsType,
                                 WordNeigborsCountLimits neigborsCountLimits) {
        this.semanticSpaceName = semanticSpaceName;
        this.compoundsOrdering = compoundsOrdering;
		if (neigborsCountLimits.firstNeigborCountLimit > Constants.NEIGHBOURS_COUNT ||
                neigborsCountLimits.secondNeigborCountLimit> Constants.NEIGHBOURS_COUNT) {
            throw new IllegalStateException("Either first or second neigbors count limit is higher" +
            " than max allowed limit: Constants.NEIGBOURS_COUNT = " + Constants.NEIGHBOURS_COUNT);
        }
		this.comValScorerNeighborsType = comValScorerNeighborsType;
        this.neigborsCountLimits = neigborsCountLimits;
	}
	
	protected double getCompoundOccurrence(CompoundSS compound) {
		if (comValScorerNeighborsType.getWeighting() == WeightingType.ALL_LOG) {
            return getWeightedNumber((altsAndCountsData.get(compound)).compoundOriginalOccurrence);
        }
		else {
            return (altsAndCountsData.get(compound)).compoundOriginalOccurrence;
        }
	}
	
	private double getWeightedNumber(double occurrence) {
		// alternative counts when logged must be smoothed.. log(1) = 0.. log(0) = problem..
		switch (comValScorerNeighborsType.getWeighting()) {
			case NONE: return occurrence;
			//case NEIGHBORS_LOG: return Math.log(occurrence + 1);
			case ALL_LOG: return Math.log(occurrence + 1);
			default: {
				System.out.println("Error: Unknown weighting!");
				return -1.0;
			}
		}
	}

    /**
     * sums occurrences of alternatives
     *
     * @param compound
     * @param firstAlternativesCounting - true means left or head word is replaced in alternatives for a given expression
     * @return
     */
	protected double getAlternativesNumber(CompoundSS compound, boolean firstAlternativesCounting) {
        OneCompoundWithAltsAndCountsData oneCompWithAltsAndCountsData = altsAndCountsData.get(compound);
		double sum = 0;
		int alternativesTakenIntoAccount = 0;
		if (firstAlternativesCounting) {
            switch (neigborsCountLimits.expressionViewedAs) {
                case LEFT_RIGHT: {
                    for (CompoundWithCount altCompound : oneCompWithAltsAndCountsData.alternativesLeftWordReplaced) {
                        if (alternativesTakenIntoAccount >= neigborsCountLimits.firstNeigborCountLimit) break;
                        sum += getWeightedNumber(altCompound.occurrence);
                        alternativesTakenIntoAccount++;
                    }
                    break;
                }
                case HEAD_MODIFYING: {
                    if (compound.leftWordIsHead()) {
                        for (CompoundWithCount altCompound : oneCompWithAltsAndCountsData.alternativesLeftWordReplaced) {
                            if (alternativesTakenIntoAccount >= neigborsCountLimits.firstNeigborCountLimit) break;
                            sum += getWeightedNumber(altCompound.occurrence);
                            alternativesTakenIntoAccount++;
                        }
                    }
                    else {
                        for (CompoundWithCount altCompound : oneCompWithAltsAndCountsData.alternativesRightWordReplaced) {
                            if (alternativesTakenIntoAccount >= neigborsCountLimits.firstNeigborCountLimit) break;
                            sum += getWeightedNumber(altCompound.occurrence);
                            alternativesTakenIntoAccount++;
                        }
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException();
                }
            }
		}
		else {
            switch (neigborsCountLimits.expressionViewedAs) {
                case LEFT_RIGHT: {
                    for (CompoundWithCount altCompound : oneCompWithAltsAndCountsData.alternativesRightWordReplaced) {
                        if (alternativesTakenIntoAccount >= neigborsCountLimits.secondNeigborCountLimit) break;
                        sum += getWeightedNumber(altCompound.occurrence);
                        alternativesTakenIntoAccount++;
                    }
                    break;
                }
                case HEAD_MODIFYING: {
                    if (compound.leftWordIsHead()) {
                        for (CompoundWithCount altCompound : oneCompWithAltsAndCountsData.alternativesRightWordReplaced) {
                            if (alternativesTakenIntoAccount >= neigborsCountLimits.secondNeigborCountLimit) break;
                            sum += getWeightedNumber(altCompound.occurrence);
                            alternativesTakenIntoAccount++;
                        }
                    }
                    else {
                        for (CompoundWithCount altCompound : oneCompWithAltsAndCountsData.alternativesLeftWordReplaced) {
                            if (alternativesTakenIntoAccount >= neigborsCountLimits.secondNeigborCountLimit) break;
                            sum += getWeightedNumber(altCompound.occurrence);
                            alternativesTakenIntoAccount++;
                        }
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException();
                }
            }
		}
		return sum;
	}

    @Override
    protected void prepareDataForScoring(CompoundSet compoundSet) {
        altsAndCountsData = AltsAndCountsDataProvider.getInstance().
                getAltsAndCounts(compoundSet, semanticSpaceName, compoundsOrdering);
    }

    @Override
    protected void cleanDataForScoring() {
        altsAndCountsData = null;
    }

    @Override
	protected double getScore(CompoundSS compound) {
		double compoundNumber = getCompoundOccurrence(compound);
		double alternativesFirstNumber = getAlternativesNumber(compound, true);
		double alternativesSecondNumber = getAlternativesNumber(compound, false);
		if (comValScorerNeighborsType.isOrigCountedInAlternatives()) {
            alternativesFirstNumber += compoundNumber;
            alternativesSecondNumber += compoundNumber;
		}
				
		if (compoundNumber == 0.0) {
            throw new IllegalStateException("Compound occurrence 0!? Cannot be! " + compound.getSSpaceRep(false) + " or " +
                    compound.getSSpaceRep(true));
		}
		if (comValScorerNeighborsType.getOperator() == Operator.PLUS) {
			return (alternativesFirstNumber + alternativesSecondNumber) / (compoundNumber + 1);
		}
		else if (comValScorerNeighborsType.getOperator() == Operator.MULT) {
			return ((alternativesFirstNumber) * (alternativesSecondNumber)) / (compoundNumber + 1);
		}
		else {
			System.out.println("Error: Unknown operator!!");
			return Double.NaN;
		}
//		if (alternativesNumber == 0.0) {
//			return Constants.MY_DOUBLE_MIN;	
//		}
//		else {
//			return -1.0 * (compoundNumber / alternativesNumber);
//		}
	}

    @Override
    public GeneralScorerType getGeneralType() {
        return generalScorerType;
    }

    @Override
    public String getType() {
        return getGeneralType().toString() + "_" + semanticSpaceName + "_" + compoundsOrdering.toString() +
                "_O_" + comValScorerNeighborsType.getOperator().toString() +
                "_C_" + comValScorerNeighborsType.isOrigCountedInAlternatives() +
                "_W_" + comValScorerNeighborsType.getWeighting().toString();
    }

    @Override
	public String getName() {
		return getType() + "_" + neigborsCountLimits.toString();//"_L_" + leftWordNeighborsCount + "_R_" + rightWordNeighborsCount;
	}

    @Override
    public String getPars() {
        return "S_" + compoundsOrdering.toString() + "_O_" + comValScorerNeighborsType.getOperator().toString().toLowerCase() +
                "_C_" + comValScorerNeighborsType.isOrigCountedInAlternatives() +
                "_W_" + comValScorerNeighborsType.getWeighting().toString().toLowerCase()+
                "_" + neigborsCountLimits.toString();
    }

    @Override
    public String getSpacePars() {
        return Common.extractSpacePars(semanticSpaceName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComValScorerNeighbors that = (ComValScorerNeighbors) o;

        if (comValScorerNeighborsType != null ? !comValScorerNeighborsType.equals(that.comValScorerNeighborsType) : that.comValScorerNeighborsType != null)
            return false;
        if (compoundsOrdering != that.compoundsOrdering) return false;
        if (neigborsCountLimits != null ? !neigborsCountLimits.equals(that.neigborsCountLimits) : that.neigborsCountLimits != null)
            return false;
        if (semanticSpaceName != that.semanticSpaceName) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = semanticSpaceName != null ? semanticSpaceName.hashCode() : 0;
        result = 31 * result + (compoundsOrdering != null ? compoundsOrdering.hashCode() : 0);
        result = 31 * result + (neigborsCountLimits != null ? neigborsCountLimits.hashCode() : 0);
        result = 31 * result + (comValScorerNeighborsType != null ? comValScorerNeighborsType.hashCode() : 0);
        return result;
    }
}
