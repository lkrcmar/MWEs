package cz.zcu.luk.mwes.acl2011;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FinalEvaluations {
	private Map<OneFinalResult, SingleValueResult> finalResults;

	public FinalEvaluations() {
		this.finalResults = new LinkedHashMap<OneFinalResult, SingleValueResult>();
	}
	
	public void putOneFinalResult(OneFinalResult parameters, SingleValueResult resultNumbers) {
		finalResults.put(parameters, resultNumbers);
	}
	
	private String getHeader() {
		return "resultType\tdataset\tfinal\tfinalType\tmodel\tneighboursHead\tneighboursModifying\tweighting\tmatching compounds\tS c All\tK c All\t"+
			"S c Adj_Noun\tK c Adj_Noun\tS c Verb_Obj\tK c Verb_Obj\tS c Subj_Verb\tK c Subj_Verb\tAPD all\tAPD AN\tAPD VO\tAPD SV"+
			"\tmatchingBa\tprecBa\tprecBa_AN\tprecBa_VO\tprecBa_SV\tmatchingTy\tprecTy\tprecTy_AN\tprecT_VO\tprecTy_SV"+
			"\tmatchingBo\tprecBo\tprecBo_AN\tprecBo_VO\tprecBo_SV";
	}
	
	public void printFinalResults(String outputFileName) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
			pw.println(getHeader());
			for (Map.Entry<OneFinalResult, SingleValueResult> parsXnumbers: finalResults.entrySet()) {
				pw.println(parsXnumbers.getKey().toString() + "\t" + parsXnumbers.getValue().toString());
			}
			pw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Final results for Excel printed..");
	}
}
