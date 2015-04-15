package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.common.SemanticSpace;
import cz.zcu.luk.mwes.common.CommonMisc;
import cz.zcu.luk.sspace.common.WordComparatorChanged;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CompoundSet {
	private ArrayList<CompoundSS> compounds;
	private CompoundSetNumData compoundSetNumData;
    private String name;
	
	// value scorer -> map of compounds and their value scores..
	private Map<ComValScorer, Map<CompoundSS, Double>> composValuesAssignedByDifferentScorers;

	public Map<ComValScorer, Map<CompoundSS, Double>> getComposValuesAssignedByDifferentScorers() {
		return composValuesAssignedByDifferentScorers;
	}

	public CompoundSetNumData getCompoundSetNumData() {
		return compoundSetNumData;
	}

	public ArrayList<CompoundSS> getCompounds() {
		return compounds;
	}
	public String getName() {
        return name;
	}
	
	public void setCompositionalityValuesAssignedByDifferentScorers(
			Map<ComValScorer, Map<CompoundSS, Double>> composValuesAssignedByDifferentScorers) {
		this.composValuesAssignedByDifferentScorers = composValuesAssignedByDifferentScorers;
	}

	public CompoundSet(CompoundSetNumData compoundSetNumData) {
		this.compoundSetNumData = compoundSetNumData;
        this.name = compoundSetNumData.getName().toString();
		ArrayList<CompoundSS> compounds = new ArrayList<CompoundSS>();
		for (CompoundACLNumBean comACL: compoundSetNumData.getCompounds()) {
			compounds.add(ExpsMapper.getCompoundSS(comACL));
		}
		this.compounds = compounds;
	}

    // for other expressions from ACL ones (no tag, no human score.. -> no evaluation!)
    public CompoundSet(String compoundsSSInStringsFN) {
        this.compoundSetNumData = null;
        this.name = compoundsSSInStringsFN.substring(compoundsSSInStringsFN.lastIndexOf(File.separator) + 1);
        ArrayList<CompoundSS> compounds = new ArrayList<CompoundSS>();
        List<String> ssCompoundsStr = CommonMisc.readNotEmptyLinesFromFile(compoundsSSInStringsFN);
        for (String oneSSCompoundStr: ssCompoundsStr) {
            compounds.add(new CompoundSS(oneSSCompoundStr));
        }
        this.compounds = compounds;
    }

    public void printResultsToFile(String dirPlusfileName) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dirPlusfileName), "UTF-8"));
            StringBuilder sbHeadline = new StringBuilder();
            for (Map.Entry<ComValScorer, Map<CompoundSS, Double>> comValScorerXScoredCompounds : composValuesAssignedByDifferentScorers.entrySet()) {
                sbHeadline.append("\t").append(comValScorerXScoredCompounds.getKey().toString());
            }
            pw.println(sbHeadline.toString());
            for (CompoundSS oneCompound: compounds) {
                StringBuilder sb = new StringBuilder(oneCompound.toString());
                for (Map.Entry<ComValScorer, Map<CompoundSS, Double>> comValScorerXScoredCompounds : composValuesAssignedByDifferentScorers.entrySet()) {
                    sb.append("\t").append(comValScorerXScoredCompounds.getValue().get(oneCompound));
                }
                pw.println(sb.toString());
            }
            pw.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	/*public void findMostFreqCompoundsSharingWord(UKWACcompoundsCounts comCounts, int neighboursCount) {
		for (Compound compound: compounds) {
			compound.findMostFreqCompoundsSharingWord(comCounts, neighboursCount);
		}
	}
	
	public void findRandomCompoundsSharingWord(UKWACcompoundsCounts comCounts,
			int neighboursCount) {
		for (Compound compound: compounds) {
			compound.findRandomCompoundsSharingWord(comCounts, neighboursCount);
		}
	}
	
	public void printCompoundsCountsToFile(String fileName) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
			for (Compound compound: compounds) {
				pw.println(compound.getCompoundsWithAltsCountsOutput());
			}
			pw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void serializeCompoundsWithCounts(String fileName) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileName);
			ObjectOutput oos = new ObjectOutputStream(fos);
			for (CompoundSS compound: compounds) {
				oos.writeObject(compound);
			}
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<CompoundSS> deserializeCompoundsWithCounts(String fileName, int comCounts) {
		FileInputStream fis;
		ArrayList<CompoundSS> compoundsRead = new ArrayList<CompoundSS>();
		try {
			fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			for (int i = 0; i < comCounts; i++) {
				compoundsRead.add((CompoundSS)ois.readObject());
			}
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compoundsRead;
	}
	
	public LinkedHashMap<ComValScorer, ArrayList<String>> getACLNumFormattedValuesCreatedByEveryValueScorer() {
		LinkedHashMap<ComValScorer, ArrayList<String>> results = new LinkedHashMap<ComValScorer, ArrayList<String>>();
		ArrayList<String> compoundsACLNumFormatted;
		for (Map.Entry<ComValScorer, Map<CompoundSS, Double>> valueScorerXValuesForCompounds: composValuesAssignedByDifferentScorers.entrySet()) {
			compoundsACLNumFormatted = new ArrayList<String>();
			for (Map.Entry<CompoundSS, Double> compoundWithScore: valueScorerXValuesForCompounds.getValue().entrySet()) {
				compoundsACLNumFormatted.add(ExpsMapper.getACLExpressionInString(compoundSetNumData, compoundWithScore.getKey(), compoundWithScore.getValue()));
			}
			results.put(valueScorerXValuesForCompounds.getKey(), compoundsACLNumFormatted);
		}
		return results;
	}

    public ArrayList<String> getACLNumFormattedValuesCreatedBy(ComValScorer comValScorer) {
        ArrayList<String> compoundsACLNumFormatted;
        Map<CompoundSS, Double> valueScorerXValuesForCompounds = composValuesAssignedByDifferentScorers.get(comValScorer);
        compoundsACLNumFormatted = new ArrayList<String>();
        for (Map.Entry<CompoundSS, Double> compoundWithScore: valueScorerXValuesForCompounds.entrySet()) {
            compoundsACLNumFormatted.add(ExpsMapper.getACLExpressionInString(compoundSetNumData, compoundWithScore.getKey(), compoundWithScore.getValue()));
        }
        return compoundsACLNumFormatted;
    }

	public void setCompounds(ArrayList<CompoundSS> compounds) {
		this.compounds = compounds;
	}


    public void getEndocentricityDataForCompounds(SemanticSpace sspace, WordComparatorChanged wordCom) {
        for (CompoundSS compound: compounds) {
            //compound.fillWithEndocentricityData(sspace, wordCom);
        }
    }
}

//// name of value scorer - name of coarse scorer -> map of compounds and their coarse scores..
//private Map<String, Map<Compound, LevelOfComposionality>> coarseScores;
//// name of value scorer - name of number scorer -> map of compounds and their number scores..
//private Map<String, Map<Compound, Double>> numberScores;
//public void setCoarseScores(
//		Map<String, Map<Compound, LevelOfComposionality>> coarseScores) {
//	this.coarseScores = coarseScores;
//}
//public void setNumberScores(
//		Map<String, Map<Compound, Double>> numberScores) {
//	this.numberScores = numberScores;
//}
// file name -> list of compounds in ACL Compound Num format..
//private Map<String, ArrayList<String>> resultsInMemory;
//public Map<String, ArrayList<String>> getResultsInMemory() {
//	return resultsInMemory;
//}

//private void printResultsCoarse(String outputFileName, Map<Compound, LevelOfComposionality> coarseScores) {
//	PrintWriter pw;
//	try {
//		pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
//		for (Map.Entry<Compound, LevelOfComposionality> compoundWithScore: coarseScores.entrySet()) {
//			pw.println(compoundWithScore.getKey().getACLOutputCoarse(compoundWithScore.getValue()));
//		}
//		pw.close();
//	} catch (UnsupportedEncodingException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}
	
//	
//	public void printResultsCoarse(String acl2011ResultsDir) {
//		for (Map.Entry<String, Map<Compound, LevelOfComposionality>> e: coarseScores.entrySet()) {
//			
//			String outputFileName = acl2011ResultsDir + name + "-" + e.getKey() + "-RESULTS.txt";
//			printResultsCoarse(outputFileName, e.getValue());
//			//System.out.println(outputFileName);
//		}
//	}
	

	
//	public void printResultsNumToFile(String acl2011ResultsDir) {
//		for (Map.Entry<String, Map<Compound, Double>> e: numberScores.entrySet()) {
//			String outputFileName = acl2011ResultsDir + name + "-" + e.getKey() + "-RESULTS.txt";
//			printResultsNumToFile(outputFileName, e.getValue());
//			//System.out.println(outputFileName);
//		}
//	}

//	
//	public void printResultsNumToMemory() {
//		// file name -> list of compounds in ACL Compound Num format..
//		resultsInMemory = new LinkedHashMap<String, ArrayList<String>>();
//		String outputFileName;
//		ArrayList<String> compoundsACLNumFormatted;
//		for (Map.Entry<String, Map<Compound, Double>> oneNumberScores: numberScores.entrySet()) {
//			outputFileName = name + "-" + oneNumberScores.getKey() + "-RESULTS.txt";
//			compoundsACLNumFormatted = new ArrayList<String>();
//			for (Map.Entry<Compound, Double> compoundWithScore: oneNumberScores.getValue().entrySet()) {
//				compoundsACLNumFormatted.add(compoundWithScore.getKey().getACLOutputNum(compoundWithScore.getValue()));
//			}
//			resultsInMemory.put(outputFileName, compoundsACLNumFormatted);
//		}
//	}
