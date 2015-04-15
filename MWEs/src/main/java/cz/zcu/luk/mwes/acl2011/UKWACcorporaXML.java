package cz.zcu.luk.mwes.acl2011;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

public class UKWACcorporaXML {
	private String ukwacXmlDirName;

    public UKWACcorporaXML() {
    }

	public UKWACcorporaXML(String ukwacXmlDirName) {
		this.ukwacXmlDirName = ukwacXmlDirName;
	}

	/**
	 * transforms unzipped tagged UKWAC corpora to txt files
	 * 
	 * @param ukwacTxtDirName name of the directory which contains UKWAC unzipped tagged corpora
	 */
	public void transformToTxtTagged(String ukwacTxtDirName) {
		// get list of file names for files which occur in the directories of the same names..
		ArrayList<String> inputFileNames = new ArrayList<String>(Arrays.asList(new File(ukwacXmlDirName).list()));
		for (String inputFileName: inputFileNames) {
			String inputFileNamePath = ukwacXmlDirName + "\\\\" + inputFileName + "\\\\" + inputFileName;
			String outputFileNamePath = ukwacTxtDirName + "\\\\" + (inputFileName.substring(0, inputFileName.length()-4)).concat(".txt");
			transformXMLtoTXT(inputFileNamePath, outputFileNamePath);
		}
	}

    public void transformCleanXMLtoTXT(String inputFileName, String outputFN, boolean replaceTag) {
        PrintWriter pw = null;
        BufferedReader documentsReader = null;
        try {
            documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "UTF-8"));

            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFN), "UTF-8"));

            String lemma = "";
            String tagShortcut = "";
            StringBuilder output = new StringBuilder(); // for the first document
            String[] splitted;
            int counter = 0;
			final int STOP = 20000;
            String nextLine;
            nextLine = documentsReader.readLine(); // read first line.. <doc id.. and skip it..
            while((nextLine = documentsReader.readLine()) != null) {
                if(nextLine.startsWith("<doc id")) {
                    if ((counter+1) % STOP == 0) {
                        break;
                    }

                    pw.println(output);
                    counter++;
                    output = new StringBuilder();
                    continue;
                }
                splitted = nextLine.split("\\t");
                if (splitted.length == 3) {
                   // System.out.println(lemma);
                    if ((splitted[2].length()-2) > 0) { // in corpora "<tabSYMtab<" as row occurs..
                        lemma = splitted[2].substring(0, splitted[2].length()-2);
                    }
                    else {
                        lemma = splitted[2];
                    }
                    if (splitted[1].length() > 1) {
                        tagShortcut = splitted[1].substring(0, 2);
                        // map modal verbs, verb "have", verb "be" to verbs..
                        if (tagShortcut.equals("MD") || tagShortcut.equals("VH") || tagShortcut.equals("VB")) {
                            tagShortcut = "VV";
                        }
                    }
                    else {
                        tagShortcut = splitted[1];
                    }

                    if (tagShortcut.length() == 1) {
                        tagShortcut = tagShortcut+tagShortcut;
                    }

                    if (tagShortcut.length() != 2) System.out.println("Bad shortcut length!!!");

                    if (replaceTag) tagShortcut = "XX";
                    output.append(lemma);
                    output.append("_");
                    output.append(tagShortcut);
                    output.append(" ");
                }
            }
            // print last document
            pw.print(output);
            counter++;

            documentsReader.close();
            pw.close();
            System.out.println(counter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findAllTags(String inputFileName, String outputFN) {
        PrintWriter pw = null;
        BufferedReader documentsReader = null;
        try {
            documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "UTF-8"));
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFN), "UTF-8"));
            String[] splitted;
            String nextLine;
            int counter = 0;
            Set<String> allTags = new LinkedHashSet<String>();
            while((nextLine = documentsReader.readLine()) != null) {
                counter++;
                if (nextLine.isEmpty()) throw new IllegalStateException("Empty line!!! " + counter);
                splitted = nextLine.split(" ");

                for (int i = 0; i < splitted.length; i++) {
                    String word = splitted[i].substring(0, splitted[i].length() - 3);
                    if (word.isEmpty()) throw new IllegalStateException("Empty word!!! " + counter);

                    String tag = splitted[i].substring(splitted[i].length() - 2);

                    if (tag.isEmpty()) throw new IllegalStateException("Empty tag!!! " + counter);
                    allTags.add(tag);
                }
            }
            documentsReader.close();
            ArrayList<String> allTagsArr = new ArrayList<String>(allTags);
            //System.out.println(allTagsArr.size());
            for (int i = 0; i < allTagsArr.size() - 1; i++) {
                pw.println(allTagsArr.get(i));
            }
            pw.print(allTagsArr.get(allTagsArr.size()-1));
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findStopwords(String inputFN, String outputFN, ArrayList<String> preservedTags, boolean replaceTag) {
        PrintWriter pw = null;
        BufferedReader documentsReader = null;

        LinkedHashSet<String> stopwords = new LinkedHashSet<String>();

        try {
            documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFN), "UTF-8"));
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFN), "UTF-8"));

            String[] splitted;
            String nextLine;
            int counter = 0;
            while((nextLine = documentsReader.readLine()) != null) {
                counter++;
                if (nextLine.isEmpty()) throw new IllegalStateException("Empty line!!!" + counter);
                splitted = nextLine.split(" ");

                for (int i = 0; i < splitted.length; i++) {
                    String word = splitted[i].substring(0, splitted[i].length() - 3);
                    if (word.isEmpty())  throw new IllegalStateException("Empty word!!! " + counter);

                    String tag = splitted[i].substring(splitted[i].length() - 2);

                    if (tag.isEmpty()) throw new IllegalStateException("Empty tag!!! " + counter);

                    if (!preservedTags.contains(tag)) {
                        stopwords.add(splitted[i]);
                    }
                }
            }
            documentsReader.close();
            ArrayList<String> stopwordsArr = new ArrayList<String>(stopwords);
            //System.out.println(allTagsArr.size());
            for (int i = 0; i < stopwordsArr.size() - 1; i++) {
                if (replaceTag) {
                    pw.println(stopwordsArr.get(i).substring(0, stopwordsArr.get(i).length() - 3) + "_XX");
                }
                else {
                    pw.println(stopwordsArr.get(i));
                }
            }
            if (replaceTag) {
                pw.print(stopwordsArr.get(stopwordsArr.size()-1).substring(0, stopwordsArr.get(stopwordsArr.size()-1).length() - 3) + "_XX");
            }
            else {
                pw.print(stopwordsArr.get(stopwordsArr.size()-1));
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> loadTagsFromFile(String fileWithTagsFN) {
        Set<String> tags = new LinkedHashSet<String>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileWithTagsFN), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                tags.add(line);
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tags;
    }

    /**
	 * transforms unzipped tagged UKWAC XML file to txt file,
	 * in txt format there are words' lemmas with their tags in format:
	 * lemma_tag (when tag has lenght > 1 only two tag's letters are left,
	 * tags "MD", "VH" and "VB" are mapped to "VV" because determine also verbs
	 * the resulting tag examples are: NN, NP, VV, JJ..
	 * 
	 * @param inputFileName input XML file
	 * @param outputFileName output TXT file 
	 */
	private void transformXMLtoTXT(String inputFileName, String outputFileName) {
		try {
			BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "Cp1252"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
			
			String lemma = "";
			String tagShortcut = "";
			StringBuilder output = new StringBuilder(); // for the first document
			String[] splitted;
			int counter = 1;
//			final int STOP = 10000;
			String nextLine;
			while((nextLine = documentsReader.readLine()) != null) {
				if(nextLine.startsWith("</text>")) {
					pw.println(output);
					counter++;
//					if (counter % STOP == 0) {
//						break;
//					}
					output = new StringBuilder();
					continue;
				}
				splitted = nextLine.split("\\t");
				if (splitted.length == 3) {
					lemma = splitted[2];
					if (splitted[1].length() > 1) {
						tagShortcut = splitted[1].substring(0, 2);
						// map modal verbs, verb "have", verb "be" to verbs..
						if (tagShortcut.equals("MD") || tagShortcut.equals("VH") || tagShortcut.equals("VB")) {
							tagShortcut = "VV";
						}
					}
					else {
						tagShortcut = splitted[1];
					}
					output.append(lemma);
					output.append("_");
					output.append(tagShortcut);
					output.append(" ");
				}
			}
			System.out.println(counter);
			
			documentsReader.close();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * infers mapping of nouns to their lemmas (plural forms to single ones.. and
	 * prints mapping into file..
	 * 
	 * @param nounsMappingFileName output file name
	 */
	public void inferMappingFromNounsToTheirLemmas(String nounsMappingFileName) {
		try {
			Map<String, String> pluralToSingularMap = new HashMap<String, String>(100000);
			// get list of file names for files which occur in the directories of the same names..
			ArrayList<String> inputFileNames = new ArrayList<String>(Arrays.asList(new File(ukwacXmlDirName).list()));
			for (String inputFileName: inputFileNames) {
				String inputFileNamePath = ukwacXmlDirName + "\\\\" + inputFileName + "\\\\" + inputFileName;
			
				BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileNamePath), "Cp1252"));
				
				String word = "";
				String tag = "";
				String lemma = "";
				String[] splitted;
				int counter = 1;				
				String nextLine;
				while((nextLine = documentsReader.readLine()) != null) {
					if(nextLine.startsWith("</text>")) {
						counter++;
						continue;
					}
					splitted = nextLine.split("\\t");
					if (splitted.length == 3) {
						tag = splitted[1];
						// map only plural nouns (not proper ones.. NPS)
						if (tag.equals("NNS")) {
							word = splitted[0];
							lemma = splitted[2];
							pluralToSingularMap.put(word.toLowerCase(), lemma);
						}
					}
				}
				System.out.println(counter);
				documentsReader.close();
			}
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nounsMappingFileName), "UTF-8"));
			for (Map.Entry<String, String> pair: pluralToSingularMap.entrySet()) {
				pw.println(pair.getKey() + " " + pair.getValue());
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
