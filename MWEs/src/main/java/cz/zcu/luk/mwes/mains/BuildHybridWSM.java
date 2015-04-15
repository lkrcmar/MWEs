package cz.zcu.luk.mwes.mains;

import cz.zcu.luk.mwes.misc.ExpressionFilter;
import cz.zcu.luk.mwes.misc.TokenFilter;
import cz.zcu.luk.sspace.config.Config;
import cz.zcu.luk.sspace.mains.CoalsMainLoadStats;
import cz.zcu.luk.sspace.mains.CoalsMainLoadStatsExpsStops;
import cz.zcu.luk.sspace.mains.CoalsMainSaveStats;
import cz.zcu.luk.sspace.tools.TokenCounterOccurrence;

import java.util.Arrays;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 21.2.14
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public class BuildHybridWSM {

    private static final Map<String, String> config = Config.getInstance().configuration;

    //public static final String DIR_PROGRAM_ROOT = "/storage/brno2/home/lkrcmar";
    public static final String DIR_DATA = config.get("dataDir");

    // corpus filename without extension
    public static final String NAME_CORPUS = config.get("corpusName");
    public static final int MAIN_WORDS_MIN_OCCURRENCE = Integer.parseInt(config.get("MAIN_WORDS_MIN_OCCURRENCE"));
    public static final int WORDS_MORE_FREQUENT_MIN_OCCURRENCE = Integer.parseInt(config.get("WORDS_MORE_FREQUENT_MIN_OCCURRENCE"));
    public static final int EXPRESSION_MIN_OCCURRENCE = Integer.parseInt(config.get("EXPRESSION_MIN_OCCURRENCE"));
    // whether to delete or not English determinters in the following forms: a_XX, an_XX, the_XX
    public static final String DELETE_DETERMINERS = config.get("DELETE_DETERMINERS");

    public static final String WSM_SUFFIX = "C_" + NAME_CORPUS + "_WMO_" + MAIN_WORDS_MIN_OCCURRENCE + "_WMF_" + WORDS_MORE_FREQUENT_MIN_OCCURRENCE + "_EMO_" + EXPRESSION_MIN_OCCURRENCE + "_XX";

    // corpus filename whole path
    public static final String FN_CORPUS = DIR_DATA + "/" + config.get("corpusDir") +"/" + NAME_CORPUS + ".txt";
    // file name with all the tokens in the corpus
    public static final String FN_TOKEN_OCCURRENCES = DIR_DATA + "/" + NAME_CORPUS + "tokensOccurrences.txt";
    // file name with all tag stopwords in the XX tagged corpus -
    // - all words in tagged corpus which are not nouns, adjectives, verbs, and adverbs
    public static final String FN_XX_TAG_STOPWORDS = DIR_DATA + "/wordsTagStopwordsInAllDocsXX.txt";

    public static final String FN_NON_WORDS = DIR_DATA + "/wordsNonWords.txt";
    // words occurring frequently which are not tag- and nonword-stopwords
    public static final String FN_MAIN_WORDS = DIR_DATA + "/wordsMain.txt";
    public static final String FN_MORE_FREQ_WORDS = DIR_DATA + "/wordsMoreFrequent.txt";
    public static final String FN_EXPRESSION_STATS = DIR_DATA + "/expressionsStats.txt";
    public static final String FN_EXPRESSION_MORE_FREQ = DIR_DATA + "/expressionsMoreFrequent.txt";

    //public static final String PARS_TOKEN_COUNTER = FN_TOKEN_OCCURRENCES + " " + FN_CORPUS + " -s";
    //public static final String PARS_EXPS_COUNTER = FN_CORPUS + " " + FN_EXPRESSION_STATS + " " + FN_MORE_FREQ_WORDS + " 2 0 " + DELETE_DETERMINERS + " false";
    public static final String[] PARS_TOKEN_COUNTER = new String[] {FN_TOKEN_OCCURRENCES, FN_CORPUS, "-s"};
    public static final String[] PARS_EXPS_COUNTER = new String[] {FN_CORPUS, FN_EXPRESSION_STATS, FN_MORE_FREQ_WORDS, "2", "0", DELETE_DETERMINERS, "false"};
    public static final String[] PARS_COALS_SAVE_STATS = new String[] {"-d", FN_CORPUS, "-m", "10000000", "-n", "10000000",
            "-F", "include="+FN_MAIN_WORDS, WSM_SUFFIX};
    public static final String[] PARS_COALS_LOAD_STATS = new String[] {"-d", FN_CORPUS,  "-m", "10000000", "-n", "10000000",
            "-F", "include="+FN_MORE_FREQ_WORDS, WSM_SUFFIX};
    public static final String[] PARS_COALS_EXPS_STOPS_LOAD_STATS = new String[] {"-d", FN_CORPUS, "-e", FN_EXPRESSION_MORE_FREQ,  "-m", "10000000", "-n", "10000000",
            "-F", "include="+FN_MORE_FREQ_WORDS, WSM_SUFFIX};

    public static void main (String[] args) throws Exception {
        // number of threads parameter for load stats WSMs for both words and expressions

        // count all token occurrences in corpus
        TokenCounterOccurrence.main(PARS_TOKEN_COUNTER);
        // extract tokens containing two consecutive non-letter characters as non-words
        TokenFilter.storeNonWords(FN_TOKEN_OCCURRENCES, FN_NON_WORDS);
        // store main words (frequent words which are not stopwords nor non-words)
        TokenFilter.storeWordsWithFreq(MAIN_WORDS_MIN_OCCURRENCE, FN_TOKEN_OCCURRENCES, FN_NON_WORDS, FN_XX_TAG_STOPWORDS, FN_MAIN_WORDS);
        // store main words (frequent words which are not stopwords nor non-words)
        TokenFilter.storeWordsWithFreq(WORDS_MORE_FREQUENT_MIN_OCCURRENCE, FN_TOKEN_OCCURRENCES, FN_NON_WORDS, FN_XX_TAG_STOPWORDS, FN_MORE_FREQ_WORDS);

        // count all expression occurrences in corpus
        CompoundsCounterOpt.main(PARS_EXPS_COUNTER);
        // store expressions occurring more frequently than EXPRESSION_MIN_OCCURRENCE
        ExpressionFilter.storeExpressionsWithFreq(EXPRESSION_MIN_OCCURRENCE, FN_EXPRESSION_STATS, FN_EXPRESSION_MORE_FREQ);
        CoalsMainSaveStats.main(PARS_COALS_SAVE_STATS);

        // build part of hybrid WSM for words occurring more frequently than WORDS_MORE_FREQUENT_MIN_OCCURRENCE
        String[] parsLoadStats = Arrays.copyOf(PARS_COALS_LOAD_STATS, PARS_COALS_LOAD_STATS.length + 1);
        parsLoadStats[parsLoadStats.length-1] = "_1";
        System.out.println(Arrays.toString(parsLoadStats));
        CoalsMainLoadStats.main(parsLoadStats);

        // build part of hybrid WSM for all expressions occurring more frequently than EXPRESSION_MIN_OCCURRENCE
        String[] parsExpsStopsLoadStats = Arrays.copyOf(PARS_COALS_EXPS_STOPS_LOAD_STATS, PARS_COALS_EXPS_STOPS_LOAD_STATS.length + 1);
        parsExpsStopsLoadStats[parsExpsStopsLoadStats.length-1] = "_1";
        System.out.println(Arrays.toString(parsExpsStopsLoadStats));
        CoalsMainLoadStatsExpsStops.main(parsExpsStopsLoadStats);
    }
}
