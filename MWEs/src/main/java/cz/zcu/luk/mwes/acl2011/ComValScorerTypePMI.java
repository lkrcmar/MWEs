package cz.zcu.luk.mwes.acl2011;

public class ComValScorerTypePMI {
//	UKWACcompoundsCountsTyped ucct = new UKWACcompoundsCountsTyped();
//
//	public ComValScorerTypePMI(String fileName) {
//		//	super();
//		ucct.loadCompoundsCountsRestricted(fileName);
//	}
//
//	//	@Override
//	protected double getScore(Compound compound) {;
//		// not interested in all alternatives number, however in left and right occurrences
//		double compoundOccurrence = (double)ucct.getOccurrenceOfCompound(compound.getSSpaceRep(ignoreTags), compound.getTag());
//		if (compoundOccurrence == 0.0) {
//			System.out.println("Compound occurrence 0!?");
//		}
//		double compoundFirstWordOccurrence = (double)ucct.getOccurrenceOfFirstWord(compound.getFirstWord().getSSpaceRep(), compound.getTag());
//		double compoundSecondWordOccurrence = (double)ucct.getOccurrenceOfSecondWord(compound.getSecondWord().getSSpaceRep(), compound.getTag());
//		double pmiInNeighbors = compoundOccurrence / (compoundFirstWordOccurrence * compoundSecondWordOccurrence);
//		System.out.println(compound.toString() + ": " + compoundOccurrence + ", " + compoundFirstWordOccurrence + ", " +
//				compoundSecondWordOccurrence + ", " + pmiInNeighbors + ", ");
//
//		return -1.0 * pmiInNeighbors;
//
//	}
	
	//@Override
	protected String getName() {
		return "TYPE_PMI";
	}

	//@Override
	protected ComValScorerNeighborsType getKind() {
		// TODO Auto-generated method stub
		return null;
	}
}
