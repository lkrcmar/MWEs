package cz.zcu.luk.mwes.acl2011;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Config {

	public static String SSPACE_DIR_NAME;
	//public static String COMPOUNDS_COUNTS_DIR_NAME;
    public static String WORDS_COUNTS_FILE_NAME;
    public static String EXPRESSION_COUNTS_FILE_NAME;
	public static String UKWAC_XML_DIR_NAME;
	public static String UKWAC_TXT_DIR_NAME;
	public static String NOUNS_MAPPING_FILE_NAME;
    public static String DISCO_COMPOUNDS_FILE_NAME;
	//public static String EN_TRAIN_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME;
	//public static String EN_VAL_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME;
	//public static String EN_TEST_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME;
	//public static String EN_TRAINVAL_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME;
	
	public static String ACL2011_EN_TRAIN_NUM_FILE_NAME;
	public static String ACL2011_EN_TRAIN_COARSE_FILE_NAME;
	public static String ACL2011_EN_VAL_NUM_FILE_NAME;
	public static String ACL2011_EN_VAL_COARSE_FILE_NAME;
	public static String ACL2011_EN_TEST_NUM_FILE_NAME;
	public static String ACL2011_EN_TEST_COARSE_FILE_NAME;
	public static String ACL2011_EN_TRAINVAL_NUM_FILE_NAME;
	public static String ACL2011_EN_TRAINVAL_COARSE_FILE_NAME;
    public static String DATASET_REDDY_NUM_FILE_NAME;
    public static String DATASET_CZ_SURF_NUM_FILE_NAME;
	
	public static String ACL2011_RESULTS_DIR;
	public static String TASK_DATA_DIR;
    public static String ACL2011_DIR;
	
	public static Map<ACLCompoundSetNum, String> fileNumNamesMapping;
	public static Map<ACLCompoundSetCoarse, String> fileCoarseNamesMapping;
	public static Map<ACLCompoundSetNum, ACLCompoundSetCoarse> numDatasetToCoarseDataset;
	//public static Map<ACLCompoundSetNum, String> fileCountsNamesMapping;
	
	public void loadConfinguration(String configFileName) {
		try {
			Map<String, String> configuration = new HashMap<String, String>();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configFileName), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
                //System.out.println(line);
				configuration.put(line.split("=")[0], line.split("=")[1]);
			}
			br.close();
			SSPACE_DIR_NAME = configuration.get("SSPACE_DIR_NAME");
			//COMPOUNDS_COUNTS_DIR_NAME = configuration.get("COMPOUNDS_COUNTS_DIR_NAME");
            WORDS_COUNTS_FILE_NAME = configuration.get("WORDS_COUNTS_FILE_NAME");
            EXPRESSION_COUNTS_FILE_NAME = configuration.get("EXPRESSION_COUNTS_FILE_NAME");
			UKWAC_XML_DIR_NAME = configuration.get("UKWAC_XML_DIR_NAME");
			UKWAC_TXT_DIR_NAME = configuration.get("UKWAC_TXT_DIR_NAME");
			NOUNS_MAPPING_FILE_NAME = configuration.get("NOUNS_MAPPING_FILE_NAME");
            DISCO_COMPOUNDS_FILE_NAME = configuration.get("DISCO_COMPOUNDS_FILE_NAME");
			//EN_TRAIN_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME = configuration.get("EN_TRAIN_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME");
			//EN_VAL_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME = configuration.get("EN_VAL_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME");
			//EN_TEST_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME = configuration.get("EN_TEST_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME");
			//EN_TRAINVAL_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME = configuration.get("EN_TRAINVAL_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME");
			ACL2011_EN_TRAIN_NUM_FILE_NAME = configuration.get("ACL2011_EN_TRAIN_NUM_FILE_NAME");
			ACL2011_EN_TRAIN_COARSE_FILE_NAME = configuration.get("ACL2011_EN_TRAIN_COARSE_FILE_NAME");
			ACL2011_EN_VAL_NUM_FILE_NAME = configuration.get("ACL2011_EN_VAL_NUM_FILE_NAME");
			ACL2011_EN_VAL_COARSE_FILE_NAME = configuration.get("ACL2011_EN_VAL_COARSE_FILE_NAME");
			ACL2011_EN_TEST_NUM_FILE_NAME = configuration.get("ACL2011_EN_TEST_NUM_FILE_NAME");
			ACL2011_EN_TEST_COARSE_FILE_NAME = configuration.get("ACL2011_EN_TEST_COARSE_FILE_NAME");
			ACL2011_EN_TRAINVAL_NUM_FILE_NAME = configuration.get("ACL2011_EN_TRAINVAL_NUM_FILE_NAME");
			ACL2011_EN_TRAINVAL_COARSE_FILE_NAME = configuration.get("ACL2011_EN_TRAINVAL_COARSE_FILE_NAME");
            DATASET_REDDY_NUM_FILE_NAME = configuration.get("DATASET_REDDY_NUM_FILE_NAME");
            DATASET_CZ_SURF_NUM_FILE_NAME = configuration.get("DATASET_CZ_SURF_NUM_FILE_NAME");
			ACL2011_RESULTS_DIR = configuration.get("ACL2011_RESULTS_DIR");
			TASK_DATA_DIR = configuration.get("TASK_DATA_DIR");
            ACL2011_DIR = configuration.get("ACL2011_DIR");
			
			fileNumNamesMapping = new HashMap<ACLCompoundSetNum, String>();
			fileNumNamesMapping.put(ACLCompoundSetNum.EN_TRAIN_NUM, ACL2011_EN_TRAIN_NUM_FILE_NAME);
			fileNumNamesMapping.put(ACLCompoundSetNum.EN_VAL_NUM, ACL2011_EN_VAL_NUM_FILE_NAME);
			fileNumNamesMapping.put(ACLCompoundSetNum.EN_TRAINVAL_NUM, ACL2011_EN_TRAINVAL_NUM_FILE_NAME);
			fileNumNamesMapping.put(ACLCompoundSetNum.EN_TEST_NUM, ACL2011_EN_TEST_NUM_FILE_NAME);
            fileNumNamesMapping.put(ACLCompoundSetNum.REDDY_NUM, DATASET_REDDY_NUM_FILE_NAME);
            fileNumNamesMapping.put(ACLCompoundSetNum.CZ_SURF_NUM, DATASET_CZ_SURF_NUM_FILE_NAME);
			fileCoarseNamesMapping = new HashMap<ACLCompoundSetCoarse, String>();
			fileCoarseNamesMapping.put(ACLCompoundSetCoarse.EN_TRAIN_COARSE, ACL2011_EN_TRAIN_COARSE_FILE_NAME);
			fileCoarseNamesMapping.put(ACLCompoundSetCoarse.EN_VAL_COARSE, ACL2011_EN_VAL_COARSE_FILE_NAME);
			fileCoarseNamesMapping.put(ACLCompoundSetCoarse.EN_TRAINVAL_COARSE, ACL2011_EN_TRAINVAL_COARSE_FILE_NAME);
			fileCoarseNamesMapping.put(ACLCompoundSetCoarse.EN_TEST_COARSE, ACL2011_EN_TEST_COARSE_FILE_NAME);
			numDatasetToCoarseDataset = new HashMap<ACLCompoundSetNum, ACLCompoundSetCoarse>();
			numDatasetToCoarseDataset.put(ACLCompoundSetNum.EN_TRAIN_NUM, ACLCompoundSetCoarse.EN_TRAIN_COARSE);
			numDatasetToCoarseDataset.put(ACLCompoundSetNum.EN_VAL_NUM, ACLCompoundSetCoarse.EN_VAL_COARSE);
			numDatasetToCoarseDataset.put(ACLCompoundSetNum.EN_TEST_NUM, ACLCompoundSetCoarse.EN_TEST_COARSE);
			numDatasetToCoarseDataset.put(ACLCompoundSetNum.EN_TRAINVAL_NUM, ACLCompoundSetCoarse.EN_TRAINVAL_COARSE);
			//fileCountsNamesMapping = new HashMap<ACLCompoundSetNum, String>();
			//fileCountsNamesMapping.put(ACLCompoundSetNum.EN_TRAIN_NUM, EN_TRAIN_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME);
			//fileCountsNamesMapping.put(ACLCompoundSetNum.EN_VAL_NUM, EN_VAL_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME);
			//fileCountsNamesMapping.put(ACLCompoundSetNum.EN_TEST_NUM, EN_TEST_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME);
			//fileCountsNamesMapping.put(ACLCompoundSetNum.EN_TRAINVAL_NUM, EN_TRAINVAL_NUM_COMPOUNDS_ALTS_AND_COUNTS_FILE_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
