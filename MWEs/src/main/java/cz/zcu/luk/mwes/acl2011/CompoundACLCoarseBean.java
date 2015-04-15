package cz.zcu.luk.mwes.acl2011;

public class CompoundACLCoarseBean extends CompoundACL {
	private LevelOfComposionality comLevel;
	
	public LevelOfComposionality getComLevel() {
		return comLevel;
	}

	public void setComLevel(LevelOfComposionality comLevel) {
		this.comLevel = comLevel;
	}

	public CompoundACLCoarseBean(String compoundLine) {
		String[] splitted = compoundLine.split("\t");
		tag = super.getACLCompoundTag(splitted[0]);
		
		compound = splitted[1];
		
		if (splitted[2].equals("low"))
			comLevel = LevelOfComposionality.LOW;
		else if (splitted[2].equals("medium"))
			comLevel = LevelOfComposionality.MEDIUM;
		else if (splitted[2].equals("high"))
			comLevel = LevelOfComposionality.HIGH;
	}

    public String getACLOutputCoarse(LevelOfComposionality coarseScore) {
        return (tag.toString() + "\t" + compound + "\t" + coarseScore.toString().toLowerCase());
    }
	
	public String toString() {
		return tag.toString() + "\t" + compound + "\t" + comLevel.toString().toLowerCase();
	}
}
