package cz.zcu.luk.mwes.acl2011;

import java.util.ArrayList;
import java.util.Map;

public class CompoundSetsHandler {
	
	private Map<ACLCompoundSetNum, CompoundSetNumData> humanNumJudgements;
	
//	private ArrayList<CompoundACLCoarseBean> enTrainCoarse;
//	private ArrayList<CompoundACLCoarseBean> enValCoarse;
//	
//	public ArrayList<CompoundACLCoarseBean> getEnTrainCoarse() {
//		return enTrainCoarse;
//	}
	

//	private static Map<String, String> fixCompounds(ArrayList<CompoundACLNumBean> compounds, Map<String, String> nounsMapping) {
//		Map<String, String> fixes = new HashMap<String,String>();;
//		for (CompoundACLNumBean comp: compounds) {
//			if (comp.getCompound().split(" ")[0].equals("God")) {
//				comp.setCompoundFixed("god" + " " + comp.getCompound().split(" ")[1]);
//				fixes.put(comp.getCompound(), comp.getCompoundFixed());
//				continue;
//			}
//			else if (comp.getCompound().split(" ")[1].equals("God")) {
//				comp.setCompoundFixed(comp.getCompound().split(" ")[0] + " " + "god");
//				fixes.put(comp.getCompound(), comp.getCompoundFixed());
//				continue;
//			}
//
//			if (comp.getTag().equals(ACLCompoundTag.EN_ADJ_NN) || comp.getTag().equals(ACLCompoundTag.EN_V_OBJ) ||
//                    comp.getTag().equals(ACLCompoundTag.RE_NN_NN)) { // fix second word.. NN or OBJ..
//				String compoundSecondWord = comp.getCompound().split(" ")[1];
//				if (compoundSecondWord.equals("data")) continue;
//				// if word is not found in corpora.. try to fix it using nouns to noun mapping
//				if (!nounsMapping.containsValue(compoundSecondWord) && nounsMapping.containsKey(compoundSecondWord)) {
////					System.out.println(compoundSecondWord);
//					String fix = nounsMapping.get(compoundSecondWord).toLowerCase();
//					if (fix != null) { // fix found
//						String fixLower = fix.toLowerCase();
//						comp.setCompoundFixed(comp.getCompound().split(" ")[0] + " " + fixLower);
//						fixes.put(comp.getCompound(), comp.getCompoundFixed());
//					}
//				}
//			}
//			else if (comp.getTag().equals(ACLCompoundTag.EN_V_SUBJ)) { // fix first word (SUBJ)
//				String compoundFirstWord = comp.getCompound().split(" ")[0];
//				if (compoundFirstWord.equals("data")) continue;
//				// if word is not found in corpora.. try to fix it using nouns to noun mapping
//				if (!nounsMapping.containsValue(compoundFirstWord) && nounsMapping.containsKey(compoundFirstWord)) {
////					System.out.print(compoundFirstWord);
//					String fix = nounsMapping.get(compoundFirstWord);
////					System.out.println(fix);
//					if (fix != null) { // fix found
//						String fixLower = fix.toLowerCase();
//						comp.setCompoundFixed(fixLower + " " + comp.getCompound().split(" ")[1]);
//						fixes.put(comp.getCompound(), comp.getCompoundFixed());
//					}
//				}
//			}
//            else if (comp.getTag().equals(ACLCompoundTag.RE_NN_NN)) {
//                // suppose every compound is correct..
//            }
//			else {
//				System.out.println("Error: in CompoundsSets.fixCompounds: Uknown ACL compound tag!");
//			}
//		}
//		return fixes;
//	}
	
	public static ArrayList<CompoundSet> loadNumCompoundSets(/*Map<String, String> nounsMapping,*/
			ArrayList<ACLCompoundSetNum> loadedCompoundSetsACL) {
		
		ArrayList<CompoundSet> comSets = new ArrayList<CompoundSet>();

		// load compounds from files..
		for (ACLCompoundSetNum dataSet : loadedCompoundSetsACL) {
			CompoundSetNumData compoundSetNumData = CompoundsIO.loadCompoundsNumFromFile(dataSet);

			// fix compounds' lemmatization errors..
			//Map<String, String> fixes = fixCompounds(compoundSetNumData.getCompounds(), nounsMapping);
//			System.out.println("----FIXES-------------------------------------------------");
//			System.out.println("-----for: " + dataSet);
//			System.out.println(fixes);
//			System.out.println("----------------------------------------------------------");
//			System.out.println();
			comSets.add(new CompoundSet(compoundSetNumData));
		}
		
		return comSets;
	}
}
