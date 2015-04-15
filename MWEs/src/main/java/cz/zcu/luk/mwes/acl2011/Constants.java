package cz.zcu.luk.mwes.acl2011;

import java.util.Comparator;

public class Constants {
    public static final String CLEAN_UKWAC_FN = "/storage/home/lkrcmar/ukwac.vert";
    //public static final String CLEAN_UKWAC_TXT_DIR_NAME = "/storage/home/lkrcmar/MWE/CLEAN_UKWAC_TXT";

//	public static final String TEST_FILE = "D:\\Vyzkum\\pokusy\\tests\\UKWAC_1_1000.txt";
	public static final boolean LOG_DETAILED = false;

	//public static final int WINDOW_SIZE = 2;
	//public static final ESemanticSpaceName SS_USED = ESemanticSpaceName.COALS_M50X0;
	//public static final ECompoundsOrdering COMPOUNDS_ORDERING_USED = ECompoundsOrdering.FREQ;
	
//	public static final int NEIGHBOURS_COUNT = 30;
//	public static final ACLCompoundSetNum TRAINING_DS = ACLCompoundSetNum.EN_TRAIN_NUM;
//	public static final ACLCompoundSetNum TESTING_DS = ACLCompoundSetNum.EN_VAL_NUM;
//	public static final boolean CHOSEN_VALUE_SCORERS_ONLY = true;
//	public static final boolean CROSVAL_USED = false;
//	public static final boolean LOAD_COMPOUNDS_ALTS_AND_COUNTS_FROM_FILE = false;
	public static final int NEIGHBOURS_COUNT = 1000;
    public static final int COMPO_NEIGHBOURS_COUNT = 1000;
//	public static final ACLCompoundSetNum TRAINING_DS = ACLCompoundSetNum.EN_TRAINVAL_NUM;
//	public static final ACLCompoundSetNum TESTING_DS = ACLCompoundSetNum.EN_TEST_NUM;
	public static final boolean CHOSEN_VALUE_SCORERS_ONLY = false;
	public static final boolean CROSVAL_USED = false;

    // true - load counts of compounds and their alternatives from file.. false - otherwise..
	public static final boolean LOAD_COMPOUNDS_ALTS_AND_COUNTS_FROM_FILE = false;

	public static final double LOW_MEDIUM_SPLITTER = 31.5;
	public static final double MEDIUM_HIGH_SPLITTER = 68.5;
	public static final int CROSS_VAL_FOLDS_COUNT = 5; // Spearman correlation evaluation is 100 times longer for folds of size 10! 
	public static final String DET_A = "a_XX";
	public static final String DET_AN = "an_XX";
	public static final String DET_THE = "the_XX";
    public static final String DET_A_WITH_SPACES = " a_XX ";
    public static final String DET_AN_WITH_SPACES = " an_XX ";
    public static final String DET_THE_WITH_SPACES = " the_XX ";
    public static final String MULTIPLE_DET_1 = " a_XX an_XX ";
    public static final String MULTIPLE_DET_2 = " an_XX a_XX ";
    public static final String MULTIPLE_DET_3 = " a_XX the_XX ";
    public static final String MULTIPLE_DET_4 = " the_XX a_XX ";
    public static final String MULTIPLE_DET_5 = " an_XX the_XX ";
    public static final String MULTIPLE_DET_6 = " the_XX an_XX ";
    public static final String MULTIPLE_DET_7 = " a_XX a_XX ";
    public static final String MULTIPLE_DET_8 = " an_XX an_XX ";
    public static final String MULTIPLE_DET_9 = " the_XX the_XX ";
	public static final int MIN_OCCURANCE_PER_TOKEN_FOR_BIGRAM_EXTRACTOR = 50;
	public static final String CORRELATIONS_SIMPLE_MODELS_FN = "correlations_of_simple_models.txt";
	public static final String CORRELATIONS_SIMPLE_MODELS_BEST_FN = "correlations_of_simple_models_best.txt";
	public static final String FINAL_RESULTS_FN = "final_results.txt";
	public static final int NUMBER_OF_BEST = 5;
	public static final int NUMBER_OF_BEST_COMBINED = 3;
	public static final boolean SIMPLE_DETAILED = false;
	public static final boolean FINAL_DETAILED = true;
	
	
	public static final Comparator<CompoundACLNumBean> COMPARATOR_SCORE_ACL_COMPOUNDBEAN = new Comparator<CompoundACLNumBean>() {
		public int compare(CompoundACLNumBean o1, CompoundACLNumBean o2) {
			return (new Double(o1.getScore()).compareTo(new Double(o2.getScore())));
		}
	};

	public static final int EXPECTED_DISTINCT_BIGRAMS_COUNT = 30000000;

	public static final double MY_DOUBLE_MIN = -100;
	public static final double MY_DOUBLE_MAX = 0;
	public static final double NUM_VALUE_MIN = 1;
	public static final double NUM_VALUE_MAX = 100;

	public static final int ROUND_DECIMAL_PLACES = 2;

	public static final int TAG_LENGTH = 2;

	//public static final String COMPOUNDS_COUNTS_FILE_NAME = "BadPMIwhenNoExpAndCountsllDocs22W3-M50-DelDfalse.txt";//"cc_w2d.txt";
    //public static final String COMPOUNDS_COUNTS_XX_FILE_NAME = "BadPMIwhenNoExpAndCountsAllDocsXX-W3-M50-DelDfalse-retJNVfalse.txt";

	public static final String SSFN_EXTENSION = ".sspace";
    public static final String SSNOTAGSFN_EXTENSION = "XX.sspace";

	public static final int PROCESSORS_AVAILABLE = 4;

	public static final int RESULTS_COMPLETE_PRINTED = 20;


    public static final boolean LOAD_ENDOCENTRICITY_DATA_FROM_FILE = false;
    public static final int MAX_DISTANCE = NEIGHBOURS_COUNT;
    public static final int PRINTED_ALTERNATIVES = 15;
    public static final int COMPONEIGBORS_PRINTED_ALTERNATIVES = 50;
    public static final int COLLOCATIONS_COUNT_TRAINVALD_AVN = 46;
    public static final int COLLOCATIONS_COUNT_TESTD_AVN = 35;
    //public static final int COLLOCATIONS_COUNT_DCZSURF_AVN = 2196;
    //public static final int COLLOCATIONS_COUNT_DCZSURF_AVN = 1849-inverse number!;
    public static final int COLLOCATIONS_COUNT_DCZSURF_AVN = 2113;
    public static final String NOTAG = "XX";
    //public static final String EXPS_DATA_FN = "/storage/brno2/home/lkrcmar/MWE/misc/BadPMIwhenNoExpAndCountsAllDocsXX-W3-M50-DelDfalse-retJNVfalseXX.txt";
    //public static final String WORDS_DATA_FN = "/storage/brno2/home/lkrcmar/MWE/misc/tokenCountsAllDocsXX.txt";
    public static final boolean IGNORE_TAGS = true;

    public static final boolean ADD_TYPE = true;


    public static int getCollocationCount(ACLCompoundSetNum dataset) {
        int collocationsCount;
        switch (dataset) {
            case EN_TRAINVAL_NUM: {
                collocationsCount = Constants.COLLOCATIONS_COUNT_TRAINVALD_AVN;
                break;
            }
            case EN_TEST_NUM: {
                collocationsCount = Constants.COLLOCATIONS_COUNT_TESTD_AVN;
                break;
            }
            case CZ_SURF_NUM: {
                collocationsCount = Constants.COLLOCATIONS_COUNT_DCZSURF_AVN;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown dataset!");
            }
        }
        return collocationsCount;
    }
}
