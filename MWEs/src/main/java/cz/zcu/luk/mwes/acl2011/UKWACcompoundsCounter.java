package cz.zcu.luk.mwes.acl2011;

public class UKWACcompoundsCounter {
	private String ukwacTxtDirName;

	public UKWACcompoundsCounter(String ukwacTxtDirName) {
		this.ukwacTxtDirName = ukwacTxtDirName;
	}

	public void countCoumpoundsCounts(String outputFileName, int windowSize) {
//		try {
//			BigramExtractorChanged bec = new BigramExtractorChanged(10000);
//			// get list of file names for files which occur in the given directory..
//			ArrayList<String> inputFileNames = new ArrayList<String>(Arrays.asList(new File(ukwacTxtDirName).list()));
//			for (String inputFileName: inputFileNames) {
//				String inputFileNamePath = ukwacTxtDirName + "/" + inputFileName;
//				BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileNamePath), "UTF-8"));
//				bec.processInWindow(documentsReader, windowSize, false);
//				documentsReader.close();
//				System.out.println(inputFileNamePath + " processed..");
//			}
//			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
//			bec.printBigrams(pw, BigramExtractorChanged.SignificanceTest.PMI, Constants.MIN_OCCURANCE_PER_TOKEN_FOR_BIGRAM_EXTRACTOR);
//			pw.close();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
