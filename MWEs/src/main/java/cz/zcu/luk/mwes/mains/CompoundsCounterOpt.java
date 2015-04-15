package cz.zcu.luk.mwes.mains;

import cz.zcu.luk.sspace.tools.BigramExtractorChangedOpt;

import java.io.*;

/**
 * Class created from UKWACcoumpoundsCounter.. just refactored and main added..
 *
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 6.11.12
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
public class CompoundsCounterOpt {
    private String inputFileName;
    private String outputFileName;
    private String wordsFileName;

    public CompoundsCounterOpt(String inputFileName, String outputFileName, String wordsFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.wordsFileName = wordsFileName;
    }

	public void countCoumpoundsCounts(int windowSize, int minOccurrencePerToken, boolean deleteDeterminers, boolean retainJJNNVVOnly) {
		try {
			//BigramExtractorChangedOpt bec = new BigramExtractorChangedOpt(100000000, "/storage/home/lkrcmar/MWE/misc/allWordsInReddyAndACLExps.txt");
            BigramExtractorChangedOpt bec = new BigramExtractorChangedOpt(1000000/*00*/, wordsFileName);
			// get list of file names for files which occur in the given directory..
			//ArrayList<String> inputFileNames = new ArrayList<String>(Arrays.asList(new File(inputTxtDirName).list()));
			//for (String inputFileName: inputFileNames) {
				String inputFileNamePath = /*inputTxtDirName + "/" +*/ inputFileName;
				BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileNamePath), "UTF-8"));
				bec.processInWindow(documentsReader, windowSize, deleteDeterminers);
				documentsReader.close();
				System.out.println(inputFileNamePath + " processed..");
			//}
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
			bec.printBigrams(pw, BigramExtractorChangedOpt.SignificanceTest.PMI, minOccurrencePerToken, retainJJNNVVOnly);
			pw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = args[1];
        String wordsFileName = args[2];
        int windowSize = Integer.parseInt(args[3]);
        int minOccurrencePerToken = Integer.parseInt(args[4]);
        boolean deleteDeterminers = Boolean.parseBoolean(args[5]);
        boolean retainJJNNVVOnly = Boolean.parseBoolean(args[6]);

        //String pars = "-W"+windowSize+"-M"+minOccurrencePerToken+"-DelD"+deleteDeterminers+"-retJNV"+retainJJNNVVOnly;
        //outputFileName = outputFileName.replace(".", pars+".");
        CompoundsCounterOpt cc = new CompoundsCounterOpt(inputFileName, outputFileName, wordsFileName);
        cc.countCoumpoundsCounts(windowSize, minOccurrencePerToken, deleteDeterminers, retainJJNNVVOnly);
    }
}

