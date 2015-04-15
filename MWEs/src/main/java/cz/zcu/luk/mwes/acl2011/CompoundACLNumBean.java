package cz.zcu.luk.mwes.acl2011;

public class CompoundACLNumBean extends CompoundACL {
	private double score;
	
	public void setScore(double score) {
		this.score = score;
	}

	public double getScore() {
		return score;
	}

	public CompoundACLNumBean(String compoundLine) {
		String[] splitted = compoundLine.split("\t");
		tag = super.getACLCompoundTag(splitted[0]);
		compound = splitted[1];
		score = new Double(splitted[2]).doubleValue();
	}

    public CompoundACLNumBean(String compound, double score, ACLCompoundTag tag) {
        this.compound = compound;
        this.score = score;
        this.tag = tag;
    }

    public String getACLOutputNum(double numScore) {
        return (tag.toString() + "\t" + compound + "\t" + numScore);
    }
	
	public String toString() {
		return tag.toString() + "\t" + compound + "\t" + score;
	}
}
