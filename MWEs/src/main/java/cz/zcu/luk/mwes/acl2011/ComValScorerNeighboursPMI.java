package cz.zcu.luk.mwes.acl2011;

public class ComValScorerNeighboursPMI {
	private int headNeighborsCount;
	private int modifyingNeighborsCount;
	private int sumOfHeadAlternativesOccurrence;
	private int sumOfModifyingAlternativesOccurrence;
	private WeightingType weighting;

//	public ComValScorerNeighboursPMI(int headNeighborsCount, int modifyingNeighborsCount) {
//		super();
//		if (headNeighborsCount > Constants.NEIGHBOURS_COUNT) System.out.println("Error: not enough neighbours..");
//		if (modifyingNeighborsCount > Constants.NEIGHBOURS_COUNT) System.out.println("Error: not enough neighbours..");
//		this.headNeighborsCount = headNeighborsCount;
//		this.modifyingNeighborsCount = modifyingNeighborsCount;
//		this.weighting = WeightingType.NONE;
//	}

//	protected double getCompoundNumber(Compound compound) {
//		return compound.getOccurrence();
//
//	}
//
//	protected double getAlternativesNumber(Compound compound) {
//		// in PMI model.. not interested in what this method returns.. but what it counts!..
//		int sum = 1;
//		int alternativesTakenIntoAccount = 0;
//		for (Compound altCompound: compound.getAlternativesHeadWordReplaced()) {
//			if (alternativesTakenIntoAccount >= headNeighborsCount) break;
//			sum += altCompound.getOccurrence();
//			alternativesTakenIntoAccount++;
//		}
//		// the modifying word occurs when head word is being replaced..
//		this.sumOfModifyingAlternativesOccurrence = sum;
//		sum = 0;
//		alternativesTakenIntoAccount = 0;
//		for (Compound altCompound: compound.getAlternativesModifyingWordReplaced()) {
//			if (alternativesTakenIntoAccount >= modifyingNeighborsCount) break;
//			sum += altCompound.getOccurrence();
//			alternativesTakenIntoAccount++;
//		}
//		// the head word occurs when modifying word is being replaced..
//		this.sumOfHeadAlternativesOccurrence = sum;
//
//		return 0;
//	}

	//@Override
/*	protected double getScore(Compound compound) {
		double compoundNumber = getCompoundNumber(compound);
		// not interested in all alternatives number, however in left and right occrrences
		getAlternativesNumber(compound);		
		if (compoundNumber == 0.0) {
			System.out.println("Compound occurrence 0!?");
		}
		
		if (sumOfHeadAlternativesOccurrence == 0 || sumOfModifyingAlternativesOccurrence == 0) {
			return Constants.MY_DOUBLE_MIN;	
		}
		else {
//			double sumOfCompoundAndItsAlternativesOccurrence = compound.getOccurrence() + sumOfHeadAlternativesOccurrence + sumOfModifyingAlternativesOccurrence;
//			sumOfCompoundAndItsAlternativesOccurrence = 210000;
			double probOfCompoundOccurrence = compoundNumber;
			double probOfHeadWordOccurrence = (compoundNumber +  sumOfHeadAlternativesOccurrence);
			double probOfModifyingWordOccurrence = (compoundNumber + sumOfModifyingAlternativesOccurrence);
			double pmiInNeighbors = probOfCompoundOccurrence / (probOfHeadWordOccurrence * probOfModifyingWordOccurrence);
			//System.out.println(compound.toString() + ": " + probOfCompoundOccurrence + ", " + probOfHeadWordOccurrence + ", " +
			//		probOfModifyingWordOccurrence + ", " + pmiInNeighbors + ", ");
			
			return -1.0 * pmiInNeighbors;
		}
	}*/
	
	//@Override
	protected String getName() {
		return "NEIGHBOURS_PMI_H_" + this.headNeighborsCount + "_M_" + this.modifyingNeighborsCount + "_W_" + this.weighting.toString();
	}
	
	public String toString() {
		return "NEIGHBOURS_PMI" + "\t" + headNeighborsCount + "\t" + modifyingNeighborsCount + "\t" + weighting.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + headNeighborsCount;
		result = prime * result + modifyingNeighborsCount;
		result = prime * result
				+ ((weighting == null) ? 0 : weighting.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComValScorerNeighboursPMI other = (ComValScorerNeighboursPMI) obj;
		if (headNeighborsCount != other.headNeighborsCount)
			return false;
		if (modifyingNeighborsCount != other.modifyingNeighborsCount)
			return false;
		if (weighting != other.weighting)
			return false;
		return true;
	}

//	@Override
	protected ComValScorerNeighborsType getKind() {
		// TODO Auto-generated method stub
		return null;
	}
}
