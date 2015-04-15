package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.util.TrieMap;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class UKWACcompoundsPMIScores {
	private Map<String, Integer> tokenIndexes;
	
	public Map<String, Integer> getTokenIndexes() {
		return tokenIndexes;
	}

	/**
     * A mapping from the packed-long (consisting of the two {@code int}
     * token-indices values representing a bigram) to the number of times the
     * bigram occurred in the corpus.
     */
    private Map<Long,Double> bigramCounts;
	
	public void loadCompoundsPMIScoresRestricted(String compoundsCountsFileName) {
		try {		
			BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(compoundsCountsFileName), "UTF-8"));
			String line;
			String[] lineSplitted = null;
			String[] compoundSplitted = null;
			String firstWord, secondWord;
			int counter = 0;
			int index = 0;
			int interval = 1000000;
			int indexFirstWord = -1, indexSecondWord = -1;
			tokenIndexes = new TrieMap<Integer>();
			bigramCounts = new HashMap<Long,Double>(Constants.EXPECTED_DISTINCT_BIGRAMS_COUNT);
			while ((line = documentsReader.readLine()) != null) {
				lineSplitted = line.split("\t");
				compoundSplitted = lineSplitted[0].split(" ");
				firstWord = compoundSplitted[0];
				secondWord = compoundSplitted[1];
			    //	if (!((firstWord.endsWith("JJ") && secondWord.endsWith("NN")) ||
                //			(firstWord.endsWith("VV") && secondWord.endsWith("NN")) ||
                //		(firstWord.endsWith("NN") && secondWord.endsWith("VV")))) {

				if (!tokenIndexes.containsKey(firstWord)) {
					tokenIndexes.put(firstWord, index);
					index++;
				}
				if (!tokenIndexes.containsKey(secondWord)) {
					tokenIndexes.put(secondWord, index);
					index++;
				}
				indexFirstWord = tokenIndexes.get(firstWord);
				indexSecondWord = tokenIndexes.get(secondWord);
				// Map the two token's indices into a single long
		        long bigramIndex = (((long)indexFirstWord) << 32) |  indexSecondWord;
//		        if (new Integer(lineSplitted[1]) > 1) {
		        	bigramCounts.put(bigramIndex, new Double(lineSplitted[2]));
//		        }
				counter++;
				if (counter % interval == 0) {
					System.out.println(counter);
				}
			}
			documentsReader.close();
			System.out.println("Bigrams loaded: " + counter);
			System.out.println("Distinct words: " + index);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getPMIOfCompound(String compound) {
		if (tokenIndexes.get(compound.split(" ")[0]) == null || tokenIndexes.get(compound.split(" ")[1]) == null) {
			System.out.println("\"" + compound + "\" does not occur in corpora..");
			return 0;
		}
		int indexFirstWord = tokenIndexes.get(compound.split(" ")[0]);
		int indexSecondWord = tokenIndexes.get(compound.split(" ")[1]);
		long bigramIndex = (((long)indexFirstWord) << 32) |  indexSecondWord;
		if (bigramCounts.get(bigramIndex) != null) {
			// return bigram occurrence
			return bigramCounts.get(bigramIndex);
		}
		else {
			// return 0 since bigram doesn't occur in spite of its constituents do
			return 0;
		}
	}

    public void setTokenIndexes(Map<String, Integer> tokenIndexes) {
        this.tokenIndexes = tokenIndexes;
    }
}
