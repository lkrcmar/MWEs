package cz.zcu.luk.mwes.acl2011;

/**
 * COMPOUNDS COMPOSIONALITY EVALUATOR
 * 
 * @author lkrcmar
 *
 */
public class CompoundsEvaluator {
	
//	public static void main(String args[]) {
//		Config config = new Config();
//		config.loadConfinguration(args[0]);
//
//		CompoundsEvaluatorRun cer = new CompoundsEvaluatorRun();
//		int windowSize2 = 2;
//		int windowSize3 = 3;
//		int windowSize4 = 4;
//
//		UKWACcompoundsCounts comCounts = null;// loadCompoundCountsToMemory(windowSize3);
//
//		ResultsComplete resComplete = new ResultsComplete();
//		ArrayList<GeneralPars> genParsList = new ArrayList<GeneralPars>();
//        genParsList.add(new GeneralPars(ECompoundsOrdering.COSSIM, ESemanticSpaceName.NEW_HAL_C_UALL_W5_R10000));
//        genParsList.add(new GeneralPars(ECompoundsOrdering.COSSIM, ESemanticSpaceName.NEW_LSAC_UALL_D300_LOG_ENTROPY));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.RANDOM, ESemanticSpaceName.NONE));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_M50X0));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_OBM50X0RS800));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.H_U1_25_M50));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.L_U1_25_M50));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.R_U1_25_M50));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.R_U1_25_M50PTRUE));
////		comCounts = loadCompoundCountsToMemory(windowSize2);
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.RANDOM, ESemanticSpaceName.NONE));
//		//genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_M50X0));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_OBM50X0RS800));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.H_U1_25_M50));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.L_U1_25_M50));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.R_U1_25_M50));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.R_U1_25_M50PTRUE));
////		comCounts = loadCompoundCountsToMemory(windowSize4);
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.RANDOM, ESemanticSpaceName.NONE));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_M50X0));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_OBM50X0RS800));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.H_U1_25_M50));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.L_U1_25_M50));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.R_U1_25_M50));
////		genParsList.add(new GeneralPars(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.R_U1_25_M50PTRUE));
//
//		for (GeneralPars oneGenPars : genParsList) {
//			resComplete.add(oneGenPars, cer.run(oneGenPars, comCounts));
//		}
//
//        // LK changed
//		//resComplete.print(Config.ACL2011_RESULTS_DIR + "resultsComplete.txt", Constants.TRAINING_DS);
////		cer.run(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.R_U1_25_M50);
////		comCounts = loadCompoundCountsToMemory(windowSize4);
////		cer.run(comCounts, ECompoundsOrdering.EUCLID, ESemanticSpaceName.H_U1_25_M50);
//
//
////		UKWACcompoundsCounts comCounts = loadCompoundCountsToMemory(windowSize2);
////		cer.run(comCounts, ECompoundsOrdering.RANDOM, ESemanticSpaceName.NONE);
////		cer.run(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_OBM50X0RS800);
////
////		comCounts = loadCompoundCountsToMemory(windowSize4);
////		cer.run(comCounts, ECompoundsOrdering.RANDOM, ESemanticSpaceName.NONE);
////		cer.run(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_OBM50X0RS800);
////
////		comCounts = loadCompoundCountsToMemory(windowSize3);
////		cer.run(comCounts, ECompoundsOrdering.RANDOM, ESemanticSpaceName.NONE);
//
//
//
//		//cer.run(comCounts, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_M50X0);
//
////		comCounts = loadCompoundCountsToMemory(windowSize4);
////		cer.run(comCounts, ECompoundsOrdering.EUCLID, ESemanticSpaceName.H_U1_25_M50);
//
////		cer.run(comCounts, ECompoundsOrdering.EUCLID, ESemanticSpaceName.C_U1_8_M50X0);
//
//
////		cer.run(windowSize4, ECompoundsOrdering.COSSIM, ESemanticSpaceName.H_U1_25_M50);
////		cer.run(windowSize4, ECompoundsOrdering.COSSIM, ESemanticSpaceName.R_U1_25_M50);
////		cer.run(windowSize4, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_M50X0);
////		cer.run(windowSize4, ECompoundsOrdering.COSSIM, ESemanticSpaceName.L_U1_25_M50);
////		cer.run(windowSize4, ECompoundsOrdering.FREQ, ESemanticSpaceName.NONE);
//
////		cer.run(windowSize3, ECompoundsOrdering.COSSIM, ESemanticSpaceName.H_U1_25_M50);
////		cer.run(windowSize2, ECompoundsOrdering.COSSIM, ESemanticSpaceName.H_U1_25_M50);
////		cer.run(windowSize2, ECompoundsOrdering.COSSIM, ESemanticSpaceName.R_U1_25_M50);
////		cer.run(windowSize3, ECompoundsOrdering.COSSIM, ESemanticSpaceName.R_U1_25_M50);
////		cer.run(windowSize2, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_M50X0);
////		cer.run(windowSize3, ECompoundsOrdering.COSSIM, ESemanticSpaceName.C_U1_25_M50X0);
////		cer.run(windowSize2, ECompoundsOrdering.COSSIM, ESemanticSpaceName.L_U1_25_M50);
////		cer.run(windowSize3, ECompoundsOrdering.COSSIM, ESemanticSpaceName.L_U1_25_M50);
////		cer.run(windowSize2, ECompoundsOrdering.FREQ, ESemanticSpaceName.NONE);
////		cer.run(windowSize3, ECompoundsOrdering.FREQ, ESemanticSpaceName.NONE);
//
//	}
//
////	private static UKWACcompoundsCounts loadCompoundCountsToMemory(int windowSize) {
////		UKWACcompoundsCounts comCounts = new UKWACcompoundsCounts(windowSize);
////        if (!Constants.LOAD_COMPOUNDS_ALTS_AND_COUNTS_FROM_FILE) {
////			//comCounts.loadCompoundsCountsRestricted(Config.COMPOUNDS_COUNTS_DIR_NAME + Constants.COMPOUNDS_COUNTS_FILE_NAME_PREFIX + windowSize + ".txt");
////            //comCounts.loadCompoundsCountsRestricted(Config.COMPOUNDS_COUNTS_DIR_NAME + Constants.COMPOUNDS_COUNTS_FILE_NAME_PREFIX + "2d.txt");
////		}
////		return comCounts;
////	}
//
////	// transformes UKWAC corpora XML files to text format
////	UKWACcorporaXML ucx = new UKWACcorporaXML(Constants.UKWAC_XML_DIR_NAME);
////	ucx.transformToTxtTagged(Constants.UKWAC_TXT_DIR_NAME);
////	// infers mapping of nouns to their lemmas (plural forms to single ones..)
////	// stores mapping into file
////	UKWACcorporaXML ucx2 = new UKWACcorporaXML(Constants.UKWAC_XML_DIR_NAME);
////	ucx2.inferMappingFromNounsToTheirLemmas(Constants.NOUNS_MAPPING_FILE_NAME);
////	// counts count of all compounds and
////	// stores counts of compounds into one file and stats of tokens to other one
////	UKWACcompoundsCounter ucc = new UKWACcompoundsCounter(Constants.UKWAC_TXT_DIR_NAME);
////	ucc.countCoumpoundsCounts(Constants.COMPOUNDS_COUNTS_FILE_NAME);
}




