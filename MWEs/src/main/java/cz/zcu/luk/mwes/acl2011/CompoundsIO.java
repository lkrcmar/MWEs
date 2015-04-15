package cz.zcu.luk.mwes.acl2011;

import java.io.*;
import java.util.ArrayList;

public class CompoundsIO {
	public static CompoundSetCoarseData loadCompoundsCoarseFromFile(ACLCompoundSetCoarse dataSetCoarse) {
		String fileName = Config.fileCoarseNamesMapping.get(dataSetCoarse);
		ArrayList<CompoundACLCoarseBean> compounds = new ArrayList<CompoundACLCoarseBean>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Config.TASK_DATA_DIR + fileName), "UTF-8"));
			String line;
			CompoundACLCoarseBean compound;
			while ((line = br.readLine()) != null) {
				compound = new CompoundACLCoarseBean(line);
				compounds.add(compound);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (new CompoundSetCoarseData(dataSetCoarse, compounds));
	}
	
	public static CompoundSetCoarseData loadCompoundsCoarseFromFileForGivenNumDataSet(ACLCompoundSetNum dataSet) {
		ACLCompoundSetCoarse dataSetCoarse = Config.numDatasetToCoarseDataset.get(dataSet);
		return loadCompoundsCoarseFromFile(dataSetCoarse);
	}
	
	public static CompoundSetNumData loadCompoundsNumFromFile(ACLCompoundSetNum dataSet) {
		ArrayList<CompoundACLNumBean> compounds = new ArrayList<CompoundACLNumBean>();
		String fileName = Config.fileNumNamesMapping.get(dataSet);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Config.TASK_DATA_DIR + fileName), "UTF-8"));
			String line;
			CompoundACLNumBean compound;
			while ((line = br.readLine()) != null) {
				compound = new CompoundACLNumBean(line);
				compounds.add(compound);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (new CompoundSetNumData(dataSet, compounds));
	}
	
	/*public void printCompoundsWithAlternativesToFile(String fileName, ArrayList<Compound> compounds) {
		// open file for compounds..
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
			// print compounds
			for (Compound com: compounds) {
				// print compound
				pw.println(com.getSSpaceRep());
				// print compound's alternatives
				for (Compound alternative: com.getAlternatives()) {
					pw.println(alternative.getSSpaceRep());
				}
			}
			// close file for compounds
			pw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public static void printResultsNumToFile(String outputFileName, CompoundSetNumData compoundSetNumData) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
			for (String oneLine : compoundSetNumData.getCompoundsInStrings()) {
				pw.println(oneLine);
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

	public static void printResultsCoarseToFile(String outputFileNameCoarse, CompoundSetCoarseData compoundSetCoarseData) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileNameCoarse), "UTF-8"));
			for (String oneLine : compoundSetCoarseData.getCompoundsInStrings()) {
				pw.println(oneLine);
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

//	public static void printResultsNumToFile(String outputFileName, Map<Compound, Double> numberScores) {
//		PrintWriter pw;
//		try {
//			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
//			for (Map.Entry<Compound, Double> compoundWithScore: numberScores.entrySet()) {
//				pw.println(compoundWithScore.getKey().getACLOutputNum(compoundWithScore.getValue()));
//			}
//			pw.close();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
