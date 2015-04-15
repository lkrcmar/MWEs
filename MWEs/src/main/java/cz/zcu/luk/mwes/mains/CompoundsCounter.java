package cz.zcu.luk.mwes.mains;

import cz.zcu.luk.sspace.tools.BigramExtractorChanged;

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
public class CompoundsCounter {
    private String inputFileName;

    public CompoundsCounter(String inputFileName) {
        this.inputFileName = inputFileName;
    }

	public void countCoumpoundsCounts(String outputFileName, int windowSize, int minOccurrencePerToken, boolean deleteDeterminers) {
		try {
			BigramExtractorChanged bec = new BigramExtractorChanged(10000);
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
			bec.printBigrams(pw, BigramExtractorChanged.SignificanceTest.PMI, minOccurrencePerToken);
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
        int windowSize = Integer.parseInt(args[2]);
        int minOccurrencePerToken = Integer.parseInt(args[3]);
        boolean deleteDeterminers = Boolean.parseBoolean(args[4]);
        CompoundsCounter cc = new CompoundsCounter(inputFileName);

        String pars = "W"+windowSize+"-"+"M"+minOccurrencePerToken+"-"+"DelD"+deleteDeterminers;
        outputFileName = outputFileName.replace(".", pars+".");
        cc.countCoumpoundsCounts(outputFileName, windowSize, minOccurrencePerToken, deleteDeterminers);
    }
}

