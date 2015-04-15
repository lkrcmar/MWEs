package cz.zcu.luk.mwes.acl2011;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import edu.ucla.sspace.util.TrieMap;

public class UKWACcompoundsCountsTyped {
	private Map<String, Integer> tokenIndexesJJNN;
	private Map<String, Integer> tokenIndexesVVNN;
	private Map<String, Integer> tokenIndexesNNVV;
	
	/**
     * A mapping from the packed-long (consisting of the two {@code int}
     * token-indices values representing a bigram) to the number of times the
     * bigram occurred in the corpus.
     */
    private Map<Long,Integer> bigramCountsJJNN;
    private Map<Long,Integer> bigramCountsVVNN;
    private Map<Long,Integer> bigramCountsNNVV;
	
	public void loadCompoundsCountsRestricted(String compoundsCountsFileName) {
		try {		
			BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(compoundsCountsFileName), "UTF-8"));
			String line;
			String[] lineSplitted = null;
			String[] compoundSplitted = null;
			String firstWord, secondWord;
			int counter = 0;
			int indexJJNN = 0;
			int indexVVNN = 0;
			int indexNNVV = 0;
			int interval = 5000000;
			int indexFirstWord = -1, indexSecondWord = -1;
			tokenIndexesJJNN = new TrieMap<Integer>();
			tokenIndexesVVNN = new TrieMap<Integer>();
			tokenIndexesNNVV = new TrieMap<Integer>();
			bigramCountsJJNN = new HashMap<Long,Integer>(Constants.EXPECTED_DISTINCT_BIGRAMS_COUNT / 3);
			bigramCountsVVNN = new HashMap<Long,Integer>(Constants.EXPECTED_DISTINCT_BIGRAMS_COUNT / 3);
			bigramCountsNNVV = new HashMap<Long,Integer>(Constants.EXPECTED_DISTINCT_BIGRAMS_COUNT / 3);
			while ((line = documentsReader.readLine()) != null) {
				lineSplitted = line.split("\t");
				compoundSplitted = lineSplitted[0].split(" ");
				firstWord = compoundSplitted[0];
				secondWord = compoundSplitted[1];
				if (!((firstWord.endsWith("JJ") && secondWord.endsWith("NN")) ||
						(firstWord.endsWith("VV") && secondWord.endsWith("NN")) ||
						(firstWord.endsWith("NN") && secondWord.endsWith("VV")))) {
					counter++;
					if (counter % interval == 0) {
						System.out.println(counter);
					}
					continue;
				}
				if (firstWord.endsWith("JJ") && secondWord.endsWith("NN")) {
					if (!tokenIndexesJJNN.containsKey(firstWord)) {
						tokenIndexesJJNN.put(firstWord, indexJJNN);
						indexJJNN++;
					}
					if (!tokenIndexesJJNN.containsKey(secondWord)) {
						tokenIndexesJJNN.put(secondWord, indexJJNN);
						indexJJNN++;
					}
					indexFirstWord = tokenIndexesJJNN.get(firstWord);
					indexSecondWord = tokenIndexesJJNN.get(secondWord);
					// Map the two token's indices into a single long
			        long bigramIndex = (((long)indexFirstWord) << 32) |  indexSecondWord;
			        	bigramCountsJJNN.put(bigramIndex, new Integer(lineSplitted[1]));
				}
				else if (firstWord.endsWith("VV") && secondWord.endsWith("NN")) {
					if (!tokenIndexesVVNN.containsKey(firstWord)) {
						tokenIndexesVVNN.put(firstWord, indexVVNN);
						indexVVNN++;
					}
					if (!tokenIndexesVVNN.containsKey(secondWord)) {
						tokenIndexesVVNN.put(secondWord, indexVVNN);
						indexVVNN++;
					}
					indexFirstWord = tokenIndexesVVNN.get(firstWord);
					indexSecondWord = tokenIndexesVVNN.get(secondWord);
					// Map the two token's indices into a single long
			        long bigramIndex = (((long)indexFirstWord) << 32) |  indexSecondWord;
			        	bigramCountsVVNN.put(bigramIndex, new Integer(lineSplitted[1]));
				}
				else if (firstWord.endsWith("NN") && secondWord.endsWith("VV")) {
					if (!tokenIndexesNNVV.containsKey(firstWord)) {
						tokenIndexesNNVV.put(firstWord, indexNNVV);
						indexNNVV++;
					}
					if (!tokenIndexesNNVV.containsKey(secondWord)) {
						tokenIndexesNNVV.put(secondWord, indexNNVV);
						indexNNVV++;
					}
					indexFirstWord = tokenIndexesNNVV.get(firstWord);
					indexSecondWord = tokenIndexesNNVV.get(secondWord);
					// Map the two token's indices into a single long
			        long bigramIndex = (((long)indexFirstWord) << 32) |  indexSecondWord;
			        	bigramCountsNNVV.put(bigramIndex, new Integer(lineSplitted[1]));
				}
				counter++;
				if (counter % interval == 0) {
					System.out.println(counter);
				}
			}
			documentsReader.close();
			System.out.println("Bigrams loaded: " + counter);
			System.out.println("Distinct words: " + (indexJJNN + indexVVNN + indexNNVV));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getOccurrenceOfCompound(String compound, ACLCompoundTag tag) {
		Map<String, Integer> demandedTokenIndexes = null;
		Map<Long, Integer> demandedBigramCounts = null;
		switch (tag) {
			case EN_ADJ_NN: {
				demandedTokenIndexes = tokenIndexesJJNN;
				demandedBigramCounts = bigramCountsJJNN;
				break;
			}
			case EN_V_OBJ: {
				demandedTokenIndexes = tokenIndexesVVNN;
				demandedBigramCounts = bigramCountsVVNN;
				break;
			}
			case EN_V_SUBJ: {
				demandedTokenIndexes = tokenIndexesNNVV;
				demandedBigramCounts = bigramCountsNNVV;
				break;
			}
			default: {
				System.out.println("Error: Unknown ACLCompoundTag in UKWACcompoundsCountsTyped..");
			}
		}
		if (demandedTokenIndexes.get(compound.split(" ")[0]) == null || demandedTokenIndexes.get(compound.split(" ")[1]) == null) {
			System.out.println("\"" + compound + "\" does not occur in corpora..");
			return 0;
		}
		int indexFirstWord = demandedTokenIndexes.get(compound.split(" ")[0]);
		int indexSecondWord = demandedTokenIndexes.get(compound.split(" ")[1]);
		long bigramIndex = (((long)indexFirstWord) << 32) |  indexSecondWord;
		if (demandedBigramCounts.get(bigramIndex) != null) {
			// return bigram occurrence
			return demandedBigramCounts.get(bigramIndex);
		}
		else {
			// return 0 since bigram doesn't occur in spite of its constituents do
			return 0;
		}
	}

	public int getOccurrenceOfFirstWord(String firstWord, ACLCompoundTag tag) {
		Map<String, Integer> demandedTokenIndexes = null;
		Map<Long, Integer> demandedBigramCounts = null;
		switch (tag) {
			case EN_ADJ_NN: {
				demandedTokenIndexes = tokenIndexesJJNN;
				demandedBigramCounts = bigramCountsJJNN;
				break;
			}
			case EN_V_OBJ: {
				demandedTokenIndexes = tokenIndexesVVNN;
				demandedBigramCounts = bigramCountsVVNN;
				break;
			}
			case EN_V_SUBJ: {
				demandedTokenIndexes = tokenIndexesNNVV;
				demandedBigramCounts = bigramCountsNNVV;
				break;
			}
			default: {
				System.out.println("Error: Unknown ACLCompoundTag in UKWACcompoundsCountsTyped..");
			}
		}
		int occurrenceFirstWord = 0;
		int indexFirstWord = demandedTokenIndexes.get(firstWord);
		int limit = demandedTokenIndexes.size();
		for (int i = 0; i < limit; i++) {
			long bigramIndex = (((long)indexFirstWord) << 32) | i;
			if (demandedBigramCounts.get(bigramIndex) != null) {
				occurrenceFirstWord += demandedBigramCounts.get(bigramIndex);
			}
		}
		
		return occurrenceFirstWord;
	}

	public int getOccurrenceOfSecondWord(String secondWord, ACLCompoundTag tag) {
		Map<String, Integer> demandedTokenIndexes = null;
		Map<Long, Integer> demandedBigramCounts = null;
		switch (tag) {
			case EN_ADJ_NN: {
				demandedTokenIndexes = tokenIndexesJJNN;
				demandedBigramCounts = bigramCountsJJNN;
				break;
			}
			case EN_V_OBJ: {
				demandedTokenIndexes = tokenIndexesVVNN;
				demandedBigramCounts = bigramCountsVVNN;
				break;
			}
			case EN_V_SUBJ: {
				demandedTokenIndexes = tokenIndexesNNVV;
				demandedBigramCounts = bigramCountsNNVV;
				break;
			}
			default: {
				System.out.println("Error: Unknown ACLCompoundTag in UKWACcompoundsCountsTyped..");
			}
		}
		int occurrenceSecondWord = 0;
		int indexSecondWord = demandedTokenIndexes.get(secondWord);
		int limit = demandedTokenIndexes.size();
		for (int i = 0; i < limit; i++) {
			long bigramIndex = (((long)i) << 32) | indexSecondWord;
			if (demandedBigramCounts.get(bigramIndex) != null) {
				occurrenceSecondWord += demandedBigramCounts.get(bigramIndex);
			}
		}
		
		return occurrenceSecondWord;
	}
}
