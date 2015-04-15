package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;
import java.util.Map;

public class CompoundSetNumData {
	private ACLCompoundSetNum name;
	private ArrayList<CompoundACLNumBean> compoundsData;
	
	public ArrayList<CompoundACLNumBean> getCompounds() {
		return compoundsData;
	}

	public CompoundSetNumData(ACLCompoundSetNum name, ArrayList<CompoundACLNumBean> compounds) {
		this.name = name;
		this.compoundsData = compounds;
	}

	public ArrayList<String> getCompoundsInStrings() {
		ArrayList<String> compoundsInStrings = new ArrayList<String>();
		for (CompoundACLNumBean oneCom : compoundsData) {
			compoundsInStrings.add(oneCom.toString());
		}
		return compoundsInStrings;
	}
	
	private void replaceOneNumValue(String compoundOriginal, Double newScore, CompoundSetNumData withReplacedValues) {
		for (CompoundACLNumBean oneCompoundBean : withReplacedValues.getCompounds()) {
			if (oneCompoundBean.getCompound().equals(compoundOriginal)) {
				oneCompoundBean.setScore(newScore);
				return;
			}
		}
		System.out.println("No match to compound found!");
	}

	public CompoundSetNumData replaceNumValues(Map<CompoundSS, Double> valueScores) {
		CompoundSetNumData withReplacedValues = CompoundsIO.loadCompoundsNumFromFile(this.getName());
		for (Map.Entry<CompoundSS, Double> oneCompound : valueScores.entrySet()) {
			replaceOneNumValue(oneCompound.getKey().getCompound(), oneCompound.getValue(), withReplacedValues);
		}
		return withReplacedValues;
	}

	public ACLCompoundSetNum getName() {
		return this.name;
	}

	public ArrayList<Double> getAllScores() {
		ArrayList<Double> allValues = new ArrayList<Double>();
		for (CompoundACLNumBean oneCom : compoundsData) {
			allValues.add(oneCom.getScore());
		}
		return allValues;
	}

    public ACLCompoundTag getTagForCompound(String compoundSS) {
        for (CompoundACLNumBean oneCompoundBean : compoundsData) {
            if (oneCompoundBean.getCompound().equals(compoundSS)) {
                return oneCompoundBean.getTag();
            }
        }
        throw new IllegalStateException("Compound \"" + compoundSS + "\" not found!");
    }
}
