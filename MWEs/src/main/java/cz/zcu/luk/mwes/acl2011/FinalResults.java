package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;
import java.util.List;

public class FinalResults {
	private List<OneFinalResult> finalResults;

	public List<OneFinalResult> getFinalResults() {
		return finalResults;
	}

	public FinalResults() {
		finalResults = new ArrayList<OneFinalResult>(); 
	}
	
	public void addOneFinalResult(OneFinalResult finalRes) {
		finalResults.add(finalRes);
	}

}
