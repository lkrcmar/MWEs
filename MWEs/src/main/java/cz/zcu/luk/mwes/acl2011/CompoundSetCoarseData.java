package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;

public class CompoundSetCoarseData {
	ACLCompoundSetCoarse name;
	private ArrayList<CompoundACLCoarseBean> compoundsData;
	
	public ArrayList<CompoundACLCoarseBean> getCompounds() {
		return compoundsData;
	}
	
	public CompoundSetCoarseData(ACLCompoundSetCoarse name, ArrayList<CompoundACLCoarseBean> compoundsData) {
		this.name = name;
		this.compoundsData = compoundsData;
	}
	
	public ArrayList<String> getCompoundsInStrings() {
		ArrayList<String> compoundsInStrings = new ArrayList<String>();
		for (CompoundACLCoarseBean oneCom : compoundsData) {
			compoundsInStrings.add(oneCom.toString());
		}
		return compoundsInStrings;
	}

	public boolean contains(String compoundOriginal) {
		for (CompoundACLCoarseBean oneCompound : compoundsData) {
			if (oneCompound.getCompound().equals(compoundOriginal)) {
				return true;
			}
		}
		return false;
	}
}
