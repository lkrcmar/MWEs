package cz.zcu.luk.mwes.acl2011;

public class ComposionalityCoarseScorerBorders extends ComposionalityCoarseScorer {
	
	private LevelOfComposionality getLevelOfComposionality(double value) {
		if (value < Constants.LOW_MEDIUM_SPLITTER) {
			return LevelOfComposionality.LOW;
		}
		else if (value < Constants.MEDIUM_HIGH_SPLITTER) {
			return LevelOfComposionality.MEDIUM;
		}
		else {
			return LevelOfComposionality.HIGH;
		}
	}

	@Override
	public void createCoarseScores(CompoundSetCoarseData coarseComSet, CompoundSetNumData numComSet) {
		int numberSet = 0; 
		for (CompoundACLCoarseBean oneComCoarse : coarseComSet.getCompounds()) {
			for (CompoundACLNumBean oneComNum : numComSet.getCompounds()) {
				if (oneComNum.getCompound().equals(oneComCoarse.getCompound())) {
					oneComCoarse.setComLevel(getLevelOfComposionality(oneComNum.getScore()));
					numberSet++;
				}
			}
		}
		if (numberSet != coarseComSet.getCompounds().size()) {
			System.out.println("Error: not all coarse scores changed!");
		}
	}
	
	@Override
	public String getName() {
		return "BORDERS";
	}
}
