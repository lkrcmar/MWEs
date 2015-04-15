package cz.zcu.luk.mwes.acl2011;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Tester {
	public static void main(String args[]) {
//		String bestAANNfileName = "EN_TRAINVAL_NUM-NEIGHBOURS_H_1_M_5_W_ALL_LOG-BASICN-RESULTS.txt";
//		String bestVVNNfileName = "EN_TRAINVAL_NUM-NEIGHBOURS_H_26_M_22_W_ALL_LOG-BASICN-RESULTS.txt";
//		String bestNNVVfileName = "EN_TRAINVAL_NUM-NEIGHBOURS_H_24_M_300_W_ALL_LOG-BASICN-RESULTS.txt";
//		
//		Config config = new Config();
//		config.loadConfinguration("config.cfg");
//		
//		ResultsEvaluator re = new ResultsEvaluator(false);
//	//	re.evaluateNumberResultsInFiles();
//	//	re.printNumResultsForExcel(Config.ACL2011_RESULTS_DIR + "Tester_out.txt");
//		
//		Tester tester = new Tester();	
//		Map<String,CompoundACLNumBean> bestAANNmodel = tester.prepareJudgements(Config.ACL2011_RESULTS_DIR + "/" + bestAANNfileName);
//		Map<String,CompoundACLNumBean> bestVVNNmodel = tester.prepareJudgements(Config.ACL2011_RESULTS_DIR + "/" + bestVVNNfileName);
//		Map<String,CompoundACLNumBean> bestNNVVmodel = tester.prepareJudgements(Config.ACL2011_RESULTS_DIR + "/" + bestNNVVfileName);
//		
//		// prepare tags for compounds
//		Map<String, ACLCompoundTag> tagsForCompounds = tester.prepareTags(bestAANNmodel);
//		// prepare models for AANN, VVNN, NNVV
//		Map<String, Double> bestAANNmodelBT = tester.prepareModel(bestAANNmodel);
//		Map<String, Double> bestVVNNmodelBT = tester.prepareModel(bestVVNNmodel);
//		Map<String, Double> bestNNVVmodelBT = tester.prepareModel(bestNNVVmodel);
//		ArrayList<String> bestAANNmodelBTOrdered = new ArrayList<String>(tester.sortByComparator(bestAANNmodelBT).keySet());
//		ArrayList<String> bestVVNNmodelBTOrdered = new ArrayList<String>(tester.sortByComparator(bestVVNNmodelBT).keySet());
//		ArrayList<String> bestNNVVmodelBTOrdered = new ArrayList<String>(tester.sortByComparator(bestNNVVmodelBT).keySet());
//		
//		System.out.println(bestAANNmodelBTOrdered);
//		System.out.println(bestVVNNmodelBTOrdered);
//		System.out.println(bestNNVVmodelBTOrdered);
//		
//		ArrayList<String> finalOrdering = new ArrayList<String>();
//		int score = 1;
//		String compound;
//		Map<String, Integer> compPositions = new LinkedHashMap<String, Integer>();
//		// take if correct tags.. and put into list..
//		for(int i = 0; i < bestAANNmodelBTOrdered.size(); i++) {
//			compound = bestAANNmodelBTOrdered.get(i);
//			if (tagsForCompounds.get(compound).equals(ACLCompoundTag.EN_ADJ_NN)) {
//				finalOrdering.add(ACLCompoundTag.EN_ADJ_NN.toString() + "\t" + compound + "\t" + score);
//				compPositions.put(compound, score-1);
//				score++;
//			}
//			compound = bestVVNNmodelBTOrdered.get(i);
//			if (tagsForCompounds.get(compound).equals(ACLCompoundTag.EN_V_OBJ)) {
//				finalOrdering.add(ACLCompoundTag.EN_V_OBJ.toString() + "\t" + compound + "\t" + score);
//				compPositions.put(compound, score-1);
//				score++;
//			}
//			compound = bestNNVVmodelBTOrdered.get(i);
//			if (tagsForCompounds.get(compound).equals(ACLCompoundTag.EN_V_SUBJ)) {
//				finalOrdering.add(ACLCompoundTag.EN_V_SUBJ.toString() + "\t" + compound + "\t" + score);
//				compPositions.put(compound, score-1);
//				score++;
//			}
//		}
//		System.out.println(finalOrdering.toString());
//		for (Map.Entry<String, CompoundACLNumBean> compInMap: bestAANNmodel.entrySet()) {
//			System.out.println(finalOrdering.get(compPositions.get(compInMap.getKey())));
//		}
			
	}
	
	private Map<String, ACLCompoundTag> prepareTags(Map<String, CompoundACLNumBean> processed) {
		Map<String, ACLCompoundTag> model = new LinkedHashMap<String, ACLCompoundTag>();
		for (Map.Entry<String, CompoundACLNumBean> oneJudgement : processed.entrySet()) {
			model.put(oneJudgement.getKey(), oneJudgement.getValue().tag);
		}
		return model;
	}

	public Map<String, Double> prepareModel(Map<String,CompoundACLNumBean> processed) {
			Map<String, Double> model = new LinkedHashMap<String, Double>();
			for (Map.Entry<String, CompoundACLNumBean> oneJudgement : processed.entrySet()) {
				model.put(oneJudgement.getKey(), oneJudgement.getValue().getScore());
			}
			return model;
	}

	public Map<String, CompoundACLNumBean> prepareJudgements(String inputFileNamePath) {
		Map<String,CompoundACLNumBean> humanJudgements = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileNamePath), "UTF-8"));
			humanJudgements = readNumJudgementsFromFile(br);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return humanJudgements;
	}
	
	private Map<String, CompoundACLNumBean> readNumJudgementsFromFile(
			BufferedReader br) throws IOException {
		Map<String, CompoundACLNumBean> composNumjudgements = new LinkedHashMap<String, CompoundACLNumBean>();
		String line;
		while ((line = br.readLine()) != null) {
			CompoundACLNumBean comNumBean = new CompoundACLNumBean(line);
			composNumjudgements.put(comNumBean.getCompound(), comNumBean);
		}
		return composNumjudgements;
	}
	
	public Map sortByComparator(Map unsortMap) {

		List list = new LinkedList(unsortMap.entrySet());

		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// put sorted list into map again
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
