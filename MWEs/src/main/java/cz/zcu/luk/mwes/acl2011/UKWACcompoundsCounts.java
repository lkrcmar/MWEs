package cz.zcu.luk.mwes.acl2011;

import edu.ucla.sspace.util.BoundedSortedMultiMap;
import edu.ucla.sspace.util.SortedMultiMap;
import edu.ucla.sspace.util.TrieMap;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class UKWACcompoundsCounts implements CompoundsCountsProvider {

    private static final UKWACcompoundsCounts INSTANCE = new UKWACcompoundsCounts();

	private Map<String, Integer> tokenIndexes;
	
	private String[] adjectives;
	private String[] nouns;
	private String[] verbs;
	
	Random randomGen;
	
	public Map<String, Integer> getTokenIndexes() {
		return tokenIndexes;
	}

	/**
     * A mapping from the packed-long (consisting of the two {@code int}
     * token-indices values representing a bigram) to the number of times the
     * bigram occurred in the corpus.
     */
    private Map<Long,Integer> bigramCounts;
    
    private int windowSize;
    
    private boolean preparedIndexesForTags;

    private UKWACcompoundsCounts () {
        this.preparedIndexesForTags = false;
        this.randomGen = new Random(149L);
        loadCompoundsCountsRestricted(Config.EXPRESSION_COUNTS_FILE_NAME);
        System.out.println("UKWACcompoundsCounts initialized - compounds loaded!");
    }

    public static UKWACcompoundsCounts getInstance() {
        return INSTANCE;
    }
	
	private void loadCompoundsCountsRestricted(String compoundsCountsFileName) {
		try {		
			BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(compoundsCountsFileName), "UTF-8"));
			String line;
			String[] lineSplitted = null;
			String[] compoundSplitted = null;
			String firstWord, secondWord;
			int counter = 0;
			int index = 0;
			int interval = 5000000;
			int indexFirstWord = -1, indexSecondWord = -1;
			tokenIndexes = new TrieMap<Integer>();
			bigramCounts = new HashMap<Long,Integer>(Constants.EXPECTED_DISTINCT_BIGRAMS_COUNT);
			while ((line = documentsReader.readLine()) != null) {
				lineSplitted = line.split("\t");
				compoundSplitted = lineSplitted[0].split(" ");
				firstWord = compoundSplitted[0];
				secondWord = compoundSplitted[1];
				if (!((firstWord.endsWith("JJ") && secondWord.endsWith("NN")) ||
						(firstWord.endsWith("VV") && secondWord.endsWith("NN")) ||
						(firstWord.endsWith("NN") && secondWord.endsWith("VV")) ||
                        (firstWord.endsWith("NN") && secondWord.endsWith("NN")))) {
					counter++;
					if (counter % interval == 0) {
						System.out.println(counter);
					}
					continue;
				}
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
		        	bigramCounts.put(bigramIndex, new Integer(lineSplitted[1]));
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

	public int getOccurrenceOfCompound(String compound) {
		if (tokenIndexes.get(compound.split(" ")[0]) == null || tokenIndexes.get(compound.split(" ")[1]) == null) {
			//System.out.println("\"" + compound + "\" does not occur in corpora..");
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

	private void prepareIndexesOfWordsOfSameMorphTag() {
		ArrayList<String> adjectivesL = new ArrayList<String>();
		ArrayList<String> nounsL = new ArrayList<String>();
		ArrayList<String> verbsL = new ArrayList<String>();
		for (Map.Entry<String, Integer> oneWordWithIndex : this.tokenIndexes.entrySet()) {
			String tag = oneWordWithIndex.getKey().substring(oneWordWithIndex.getKey().length() - Constants.TAG_LENGTH);
			if (tag.equals("JJ")) adjectivesL.add(oneWordWithIndex.getKey());
			else if (tag.equals("NN")) nounsL.add(oneWordWithIndex.getKey());
			else if (tag.equals("VV")) verbsL.add(oneWordWithIndex.getKey());
		}
		this.adjectives = (String[])adjectivesL.toArray(new String[0]);
		this.nouns = (String[])nounsL.toArray(new String[0]);
		this.verbs = (String[])verbsL.toArray(new String[0]);
	}
	
	public SortedMultiMap<Integer,String> getMostFrequentCompoundsSharingFirstWord(String compound) {
		String firstWord = compound.split(" ")[0];
		String secondWord = compound.split(" ")[1];
		String secondWordType = secondWord.substring(secondWord.length() - Constants.TAG_LENGTH);
		if (!preparedIndexesForTags) {
			prepareIndexesOfWordsOfSameMorphTag();
			preparedIndexesForTags = true;
		}
		
		String[] wordsWithTypeOfSecondWord = null;

		if (secondWordType.equals("JJ")) wordsWithTypeOfSecondWord = this.adjectives;
		else if (secondWordType.equals("NN")) wordsWithTypeOfSecondWord = this.nouns;
		else if (secondWordType.equals("VV")) wordsWithTypeOfSecondWord = this.verbs;

		SortedMultiMap<Integer,String> mostFrequent = new BoundedSortedMultiMap<Integer,String>(Constants.NEIGHBOURS_COUNT, false);
		for (String s : wordsWithTypeOfSecondWord) {
			if (s.equals(secondWord)) continue; // do not count the same compound as the original..
			String compoundSharingWord = firstWord + " " + s;
			mostFrequent.put(getOccurrenceOfCompound(compoundSharingWord), compoundSharingWord);
		}
		return mostFrequent;
	}

	public SortedMultiMap<Integer, String> getMostFrequentCompoundsSharingSecondWord(String compound) {
		String firstWord = compound.split(" ")[0];
		String secondWord = compound.split(" ")[1];
		String firstWordType = firstWord.substring(firstWord.length() - Constants.TAG_LENGTH);
		if (!preparedIndexesForTags) {
			prepareIndexesOfWordsOfSameMorphTag();
			preparedIndexesForTags = true;
		}
		
		String[] wordsWithTypeOfFirstWord = null;

		if (firstWordType.equals("JJ")) wordsWithTypeOfFirstWord = this.adjectives;
		else if (firstWordType.equals("NN")) wordsWithTypeOfFirstWord = this.nouns;
		else if (firstWordType.equals("VV")) wordsWithTypeOfFirstWord = this.verbs;

		SortedMultiMap<Integer,String> mostFrequent = new BoundedSortedMultiMap<Integer,String>(Constants.NEIGHBOURS_COUNT, false);
		for (String s : wordsWithTypeOfFirstWord) {
			if (s.equals(firstWord)) continue; // do not count the same compound as the original..
			String compoundSharingWord = s + " " + secondWord;
			mostFrequent.put(getOccurrenceOfCompound(compoundSharingWord), compoundSharingWord);
		}
		return mostFrequent;
	}
	
	private String chooseRandomWordFrom(String[] wordsWithTypeOfSecondWord) {
		int index = this.randomGen.nextInt(wordsWithTypeOfSecondWord.length);
		return wordsWithTypeOfSecondWord[index];
	}

	public Map<String, Integer> getRandomCompoundsSharingFirstWord(String compound) {
		String firstWord = compound.split(" ")[0];
		String secondWord = compound.split(" ")[1];
		String secondWordType = secondWord.substring(secondWord.length() - Constants.TAG_LENGTH);
		if (!preparedIndexesForTags) {
			prepareIndexesOfWordsOfSameMorphTag();
			preparedIndexesForTags = true;
		}
		
		String[] wordsWithTypeOfSecondWord = null;

		if (secondWordType.equals("JJ")) wordsWithTypeOfSecondWord = this.adjectives;
		else if (secondWordType.equals("NN")) wordsWithTypeOfSecondWord = this.nouns;
		else if (secondWordType.equals("VV")) wordsWithTypeOfSecondWord = this.verbs;

		Map<String, Integer> randomNeigboursWithCounts = new LinkedHashMap<String, Integer>(Constants.NEIGHBOURS_COUNT);
		while (randomNeigboursWithCounts.size() != Constants.NEIGHBOURS_COUNT) {
			String randomWordWithSpecifiedType = chooseRandomWordFrom(wordsWithTypeOfSecondWord);
			String compoundSharingWord = firstWord + " " + randomWordWithSpecifiedType;
			randomNeigboursWithCounts.put(compoundSharingWord, getOccurrenceOfCompound(compoundSharingWord));
		}
		return randomNeigboursWithCounts;
	}

	public Map<String, Integer> getRandomCompoundsSharingSecondWord(String compound) {
		String firstWord = compound.split(" ")[0];
		String secondWord = compound.split(" ")[1];
		String firstWordType = firstWord.substring(firstWord.length() - Constants.TAG_LENGTH);
		if (!preparedIndexesForTags) {
			prepareIndexesOfWordsOfSameMorphTag();
			preparedIndexesForTags = true;
		}
		
		String[] wordsWithTypeOfFirstWord = null;

		if (firstWordType.equals("JJ")) wordsWithTypeOfFirstWord = this.adjectives;
		else if (firstWordType.equals("NN")) wordsWithTypeOfFirstWord = this.nouns;
		else if (firstWordType.equals("VV")) wordsWithTypeOfFirstWord = this.verbs;

		Map<String, Integer> randomNeigboursWithCounts = new LinkedHashMap<String, Integer>(Constants.NEIGHBOURS_COUNT);
		while (randomNeigboursWithCounts.size() != Constants.NEIGHBOURS_COUNT) {
			String randomWordWithSpecifiedType = chooseRandomWordFrom(wordsWithTypeOfFirstWord);
			String compoundSharingWord = randomWordWithSpecifiedType + " " + secondWord;
			randomNeigboursWithCounts.put(compoundSharingWord, getOccurrenceOfCompound(compoundSharingWord));
		}
		return randomNeigboursWithCounts;
	}
}
