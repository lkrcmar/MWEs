package cz.zcu.luk.mwes.mains;

import org.apache.commons.io.FileUtils;

import cz.zcu.luk.mwes.common.CommonMisc;
import cz.zcu.luk.mwes.util.MapUtil;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 8.11.13
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public class CTKToLines {
    public static Map<String, Map<String, Integer>> toCommonStrsMap = new LinkedHashMap<String, Map<String, Integer>>();
    public static Set<String> allLemmasPlusTags = new LinkedHashSet<String>();

    private static String parseFileCSTSXML(File oneFile) throws Exception {
        String textFileName = oneFile.getAbsolutePath().toString().replace(".csts.xml", ".txt");
        textFileName =  textFileName.replace("CTK1999TAGGED", "CTK1999");

        ArrayList<String> lastNotEmptyLines = getStringsBeforeStars(textFileName);
        System.out.println(lastNotEmptyLines.toString());

        BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "UTF-8"));
        //BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "ISO-8859-2"));
        String word = "";
        String lemma;
        String lemmaNoTechnical = "";
        String tag = "";

        String oldWord;
        String oldLemmaNoTechnical;
        String oldTagShort;
        StringBuilder tagShort = null;
        StringBuilder output = new StringBuilder();
        StringBuilder sentence = new StringBuilder();
        StringBuilder sentenceOrig = new StringBuilder();
        String lastSentence;
        String nextLine;
        String nextLineTrimmed;
        int index = 0;
        while((nextLine = documentsReader.readLine()) != null) {
            nextLineTrimmed = nextLine.trim();

            if (nextLineTrimmed.startsWith("<sentence")) {
                continue;
            }
            else if (nextLineTrimmed.startsWith("</sentence>")) {
                //String prefLastSentence = lastSentence;
                lastSentence = sentence.toString();
                word = "";
                lemmaNoTechnical = "";
                tagShort = null;
                sentence = new StringBuilder();

                //output.append(lastSentence + "\n");
                output.append(lastSentence + " ");

                //System.out.println(lastSentence);
                if (index < lastNotEmptyLines.size() && (sentenceOrig.toString().replaceAll(" ", "").endsWith(lastNotEmptyLines.get(index))
                       /* || sentenceOrig.toString().replaceAll(" ", "").startsWith(lastNotEmptyLines.get(index))*/)) {
                    //System.out.println(sentenceOrig.toString().replaceAll(" ", ""));
                    output.append("\n");
                    //System.out.println(lastNotEmptyLines.get(index)+" |OK");
                        index++;
                }
                else if (index+1< lastNotEmptyLines.size() && (sentenceOrig.toString().replaceAll(" ", "").endsWith(lastNotEmptyLines.get(index+1))
                        /*|| sentenceOrig.toString().replaceAll(" ", "").startsWith(lastNotEmptyLines.get(index+1))*/)) {
                        //System.out.println(sentenceOrig.toString().replaceAll(" ", ""));
                        output.append("\n");
                    System.out.println("SENTENCE: " +sentenceOrig.toString());
                    System.out.println("LASTSENTENCE: " +lastSentence.toString());
                    System.out.println(" PHRASE: " +lastNotEmptyLines.get(index));
                    System.out.println("PHRASE NEXT: " +lastNotEmptyLines.get(index+1));
                        System.out.println(lastNotEmptyLines.get(index)+" | Error joined with following");
                        index++;
                        if (!(index+1 >= lastNotEmptyLines.size())) {
                            index++;
                        }
                }
                sentenceOrig = new StringBuilder();
                continue;
            }

            if (nextLineTrimmed.startsWith("<text>")) {
                oldWord = word;
                oldLemmaNoTechnical = lemmaNoTechnical;
                if (tagShort == null) oldTagShort = "";
                else oldTagShort = tagShort.toString();

                sentenceOrig.append(" ").append(nextLineTrimmed.substring(6, nextLineTrimmed.length() - 7));

                word = nextLineTrimmed.substring(6, nextLineTrimmed.length() - 7);

                if ((nextLine = documentsReader.readLine()) == null) {
                    throw new IllegalStateException("1 next line should be line containing <lemma>");
                }
                nextLineTrimmed = nextLine.trim();
                if (nextLineTrimmed.startsWith("<lemma>")) {
                    ; // everything is ok..
                }
                else {
                    throw new IllegalStateException("2 next line should be line containing <lemma>");
                }

                lemma = nextLineTrimmed.substring(7, nextLineTrimmed.length() - 8);
                lemmaNoTechnical = removeTechnical(lemma);

                if ((nextLine = documentsReader.readLine()) == null) {
                    throw new IllegalStateException("1 next line should be line containing <tag>");
                }
                nextLineTrimmed = nextLine.trim();
                if (nextLineTrimmed.startsWith("<tag>")) {
                    tag = nextLineTrimmed.substring(5, nextLineTrimmed.length() - 6);
                    tagShort = extractShortTag(tag);
                }
                else {
                    throw new IllegalStateException("2 next line should be line containing <tag>");
                }
                //sentence.append(lemmaNoTechnical).append(tagShort).append("_XX ");
                addToStrToLemmaMap(word, lemmaNoTechnical, tagShort.toString(), oldWord, oldLemmaNoTechnical, oldTagShort);
                //sentence.append(word).append("_XYX_").append(lemmaNoTechnical).append(tagShort).append("_XX ");
                sentence.append(lemmaNoTechnical).append(tagShort).append("_XX ");
            }
        }
        documentsReader.close();
        String outputStr = output.toString();
        if (outputStr.endsWith(" ")) outputStr = outputStr.substring(0, outputStr.length() - 1);

        if (index != lastNotEmptyLines.size()) {
            System.out.println("Some Error: index: " + index + " != lastNotEmptyLines.size(): " + lastNotEmptyLines.size());
        }
        return outputStr;
    }

    private static void addToStrToLemmaMap(String word, String lemmaNoTechnical, String tagShort,
                  String oldWord, String oldLemmaNoTechnical, String oldTagShort) {

        if (oldTagShort.length() < 1 || tagShort.length() < 1) return;
        String lemmaTagXXLemmaTagXX = oldLemmaNoTechnical+oldTagShort.charAt(0)+"_XX " + lemmaNoTechnical+tagShort.charAt(0)+"_XX";
        //System.out.println(word + " | " + lemmaNoTechnical + " | " + tagShort + " | " +  oldWord + " | " + oldLemmaNoTechnical + " | " + oldTagShort);
        if (oldWord.equals("")) return; // cannot be bigram.. sentence starts..

        if (allLemmasPlusTags.contains(lemmaTagXXLemmaTagXX)) { //   if processed bigram..
            String wordLCtagWordLCtag = oldWord.toLowerCase() + oldTagShort + " " + word.toLowerCase() + tagShort;
            //System.out.println(lemmaTagXXLemmaTagXX + " | " + wordLCtagWordLCtag);
            if (toCommonStrsMap.containsKey(lemmaTagXXLemmaTagXX)) {
                Map<String, Integer> commonStrToCount = toCommonStrsMap.get(lemmaTagXXLemmaTagXX);
                Integer count = commonStrToCount.get(wordLCtagWordLCtag);
                if (count == null) {
                    commonStrToCount.put(wordLCtagWordLCtag, 1);
                }
                else {
                    commonStrToCount.put(wordLCtagWordLCtag, count+1);
                }
            }
            else {
                Map<String, Integer> commonStrXCount = new HashMap<String, Integer>();
                commonStrXCount.put(wordLCtagWordLCtag, 1);
                toCommonStrsMap.put(lemmaTagXXLemmaTagXX, commonStrXCount);
            }
        }
    }

    private static String parseFileCSTSXMLOld(File oneFile) throws Exception {
        String textFileName = oneFile.getAbsolutePath().toString().replace(".csts.xml", ".txt");
        textFileName =  textFileName.replace("CTK1999TAGGED", "CTK1999");

        ArrayList<String> lastNotEmptyLines = getStringsBeforeStars(textFileName);
        System.out.println(lastNotEmptyLines.toString());

        BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "UTF-8"));
        //BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "ISO-8859-2"));
        String lemma;
        String lemmaNoTechnical;
        String tag;
        StringBuilder tagShort;
        StringBuilder output = new StringBuilder();
        StringBuilder sentence = new StringBuilder();
        StringBuilder sentenceOrig = new StringBuilder();
        String lastSentence;
        String nextLine;
        String nextLineTrimmed;
        int index = 0;
        while((nextLine = documentsReader.readLine()) != null) {
            nextLineTrimmed = nextLine.trim();

            if (nextLineTrimmed.startsWith("<sentence")) {
                continue;
            }
            else if (nextLineTrimmed.startsWith("</sentence>")) {
                //String prefLastSentence = lastSentence;
                lastSentence = sentence.toString();
                sentence = new StringBuilder();

                //output.append(lastSentence + "\n");
                output.append(lastSentence + " ");

                //System.out.println(lastSentence);
                if (index < lastNotEmptyLines.size() && (sentenceOrig.toString().replaceAll(" ", "").endsWith(lastNotEmptyLines.get(index))
                        /* || sentenceOrig.toString().replaceAll(" ", "").startsWith(lastNotEmptyLines.get(index))*/)) {
                    //System.out.println(sentenceOrig.toString().replaceAll(" ", ""));
                    output.append("\n");
                    //System.out.println(lastNotEmptyLines.get(index)+" |OK");
                    index++;
                }
                else if (index+1< lastNotEmptyLines.size() && (sentenceOrig.toString().replaceAll(" ", "").endsWith(lastNotEmptyLines.get(index+1))
                        /*|| sentenceOrig.toString().replaceAll(" ", "").startsWith(lastNotEmptyLines.get(index+1))*/)) {
                    //System.out.println(sentenceOrig.toString().replaceAll(" ", ""));
                    output.append("\n");
                    System.out.println("SENTENCE: " +sentenceOrig.toString());
                    System.out.println("LASTSENTENCE: " +lastSentence.toString());
                    System.out.println(" PHRASE: " +lastNotEmptyLines.get(index));
                    System.out.println("PHRASE NEXT: " +lastNotEmptyLines.get(index+1));
                    System.out.println(lastNotEmptyLines.get(index)+" | Error joined with following");
                    index++;
                    if (!(index+1 >= lastNotEmptyLines.size())) {
                        index++;
                    }
                }
                sentenceOrig = new StringBuilder();
                continue;
            }

            if (nextLineTrimmed.startsWith("<text>")) {
                sentenceOrig.append(" ").append(nextLineTrimmed.substring(6, nextLineTrimmed.length() - 7));
            }
            else if (nextLineTrimmed.startsWith("<lemma>")) {
                lemma = nextLineTrimmed.substring(7, nextLineTrimmed.length() - 8);
                lemmaNoTechnical = removeTechnical(lemma);

                if ((nextLine = documentsReader.readLine()) == null) {
                    throw new IllegalStateException("1 next line should be line containing <tag>");
                }
                nextLineTrimmed = nextLine.trim();
                if (nextLineTrimmed.startsWith("<tag>")) {
                    tag = nextLineTrimmed.substring(5, nextLineTrimmed.length() - 6);
                    tagShort = extractShortTag(tag);
                }
                else {
                    throw new IllegalStateException("2 next line should be line containing <tag>");
                }
                sentence.append(lemmaNoTechnical).append(tagShort).append("_XX ");
            }
        }
        documentsReader.close();
        String outputStr = output.toString();
        if (outputStr.endsWith(" ")) outputStr = outputStr.substring(0, outputStr.length() - 1);

        if (index != lastNotEmptyLines.size()) {
            System.out.println("Some Error: index: " + index + " != lastNotEmptyLines.size(): " + lastNotEmptyLines.size());
        }
        return outputStr;
    }

    private static ArrayList<String> getStringsBeforeStars(String fileName) throws IOException {
        ArrayList<String> lastLinesList = new ArrayList<String>();
        BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "windows-1250"));   //"ISO8859-2"

        String nextLine;
        String nextLineTrimmed;
        String lastLine = "";
        boolean firstDoc = true;
        while((nextLine = documentsReader.readLine()) != null) {
            nextLineTrimmed = nextLine.trim();
            if (nextLineTrimmed.equals("")) {
                continue;
            }

            if (nextLineTrimmed.contains("*******************")) {
                if (firstDoc) {
                    firstDoc = false;
                }
                else {
                    lastLinesList.add(lastLine.replaceAll("\\s", ""));
                }
            }
            lastLine = nextLineTrimmed;
        }
        documentsReader.close();
        lastLinesList.add(lastLine.replaceAll(" ", ""));

        return lastLinesList;

    }

    private static StringBuilder extractShortTag(String tag) {
        StringBuilder tagShort = new StringBuilder();

        return tagShort.append(tag.charAt(0)).append(tag.charAt(1)).append(tag.charAt(3)).append(tag.charAt(4));
    }

    private static StringBuilder extractShortTagOld(String tag) {
        StringBuilder tagShort = new StringBuilder();
        if (tag.charAt(0) == 'V') {        // if verb put '-'
            tagShort.append(tag.charAt(0)).append('-').append(tag.charAt(9)).append('-');
        }
        else if (tag.charAt(0) == 'X' || tag.charAt(0) == 'P') {
            tagShort.append(tag.charAt(0)).append('-').append('-').append('-');
           // System.out.println("tag X!");
        }
        else {
            tagShort.append(tag.charAt(0)).append(tag.charAt(2)).append(tag.charAt(9)).append(tag.charAt(10));
        }

        return tagShort;
    }

    private static String removeTechnical(String lemma) {
        int techStartIndex = lemma.indexOf("_:"); // obchodovat_:T;
        if (techStartIndex != -1) {
            lemma = lemma.substring(0, techStartIndex);
        }
        techStartIndex = lemma.indexOf("_;"); // Bonn_;G
        if (techStartIndex != -1) {
            lemma = lemma.substring(0, techStartIndex);
        }
        techStartIndex = lemma.indexOf("_^"); // maso_^(jídlo_apod.)
        if (techStartIndex != -1) {
            lemma = lemma.substring(0, techStartIndex);
        }
        techStartIndex = lemma.indexOf("_,"); // the-1_,t
        if (techStartIndex != -1) {
            lemma = lemma.substring(0, techStartIndex);
        }
        techStartIndex = lemma.indexOf("`"); // sedm`7
        if (techStartIndex != -1) {
            // check if not something like "xxx`" or "`" alone..
            if (lemma.length() != techStartIndex+1) {
                //System.out.print(lemma);
                lemma = lemma.substring(0, techStartIndex);
                //System.out.println(" --> " +lemma);
            }
        }

        // remove "-" and number specifying meaning..
        techStartIndex = lemma.lastIndexOf("-");
        if (techStartIndex != -1 && techStartIndex == (lemma.length()-2) && Character.isDigit(lemma.charAt(lemma.length()-1))) {
            lemma = lemma.substring(0, techStartIndex);
        }

        return lemma;
    }

    private static boolean isInteger(String possibleInteger) {
        try {
            Integer.parseInt(possibleInteger);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {

        allLemmasPlusTags = new LinkedHashSet<String>(CommonMisc.readLinesFromFile("C:\\Skola\\korpus\\CTK\\allExpsPOSTagged.txt"));

        String inputDir = "C:\\Skola\\korpus\\CTK\\CTK1999TAGGED";
        //String inputDir = "C:\\Skola\\korpus\\CTK\\19990101";
        //String inputDir = "/storage/brno2/home/lkrcmar/MWE/pdt/utf8/";

        String outputFN = "C:\\Skola\\korpus\\CTK\\extract.txt";
        String outputFN2 = "C:\\Skola\\korpus\\CTK\\expsX.txt";
        //String outputFN = "/storage/brno2/home/lkrcmar/MWE/corpus/ctkAllXX.txt";


        Collection files;
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFN), "UTF-8"));
//        Collection files = FileUtils.listFiles(new File(inputDir), new String[]{"m"}, true);
//        for (Object oneFile : files) {
//            System.out.println(((File)oneFile).toString());
//        }
//
//        for (Object oneFile : files) {
//            String fileInString = parseFile((File)oneFile);
//            pw.println(fileInString);
//        }

        files = FileUtils.listFiles(new File(inputDir), new String[]{"csts.xml"}, true);

        int count = 0;
        for (Object oneFile : files) {
            count++;
            System.out.println(((File)oneFile).toString());
            String fileInString = parseFileCSTSXML((File) oneFile);
            pw.print(fileInString);

            //if (count == 50) break;
        }
        pw.close();


        //System.out.println(toCommonStrsMap.toString());


        PrintWriter pw2 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFN2), "UTF-8"));
        //for (Map.Entry<String, Map<String, Integer>> e : toCommonStrsMap.entrySet()) {
        for (String lemmaPlusTag : allLemmasPlusTags) {
            Map<String, Integer> commonStrMap = toCommonStrsMap.get(lemmaPlusTag);
            if (commonStrMap == null) continue;

            //for (Map.Entry<String, Integer> e2 : e.getValue().entrySet()) {
            List<Map.Entry<String, Integer>> sorted = MapUtil.extractEntriesSortedByValue(commonStrMap);
            Collections.reverse(sorted);
//            String expFirstCases = getMostFirstCases(sorted);
//            if (expFirstCases != null) {
//                pw2.print(expFirstCases + "\t" + commonStrMap.get(expFirstCases));
//            }
//            else {
//                pw2.print(sorted.get(0).getKey() + "\t" + sorted.get(0).getValue());
//            }
     //       for (Map.Entry<String, Integer> e2 : sorted) {
     //           pw2.print(e2.getKey() + "-" + e2.getValue() + " | ");
     //       }
            if (isWithSimpleVerb(lemmaPlusTag)) {
                ;
            }
            else {
                pw2.println(lemmaPlusTag + "\t" + getBasicForm(sorted, lemmaPlusTag));
            }
        }
        pw2.close();
    }

    private static boolean isWithSimpleVerb(String lemmaPlusTag) {
        String[] wordsPlusTags = lemmaPlusTag.split(" ");
        if (wordsPlusTags[0].equals("mociV_XX") || wordsPlusTags[0].equals("býtV_XX") || wordsPlusTags[0].equals("mítV_XX") ||
                wordsPlusTags[1].equals("mociV_XX") || wordsPlusTags[1].equals("býtV_XX") || wordsPlusTags[1].equals("mítV_XX")) {
            return true;
        }
        return false;
    }

    private static String getBasicForm(List<Map.Entry<String, Integer>> sorted, String lemmaPlusTag) {
        String mweType = getType(lemmaPlusTag);
        if (mweType.equals("AN")) {
            return getBasicFormSecondWordFirstOrFourCase(sorted);
        }
        else if (mweType.equals("NN")) {
            return getBasicFormFirstWordFirstOrFourCase(sorted);
        }
        else if (mweType.equals("VN")) {
            return getBasicFormFirstWordInfinitiv(sorted);
        }
        else if (mweType.equals("NV")) {
            return getBasicFormFirstWordFirstOrFourCase(sorted);
        }
        else {
            throw new IllegalStateException();
        }
        //return sorted.get(0).getKey();
    }

    private static String getBasicFormFirstWordInfinitiv(List<Map.Entry<String, Integer>> sorted) {
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            if (expWithCount.getKey().split(" ")[0].charAt(expWithCount.getKey().split(" ")[0].length() - 3) == 'f') {
                return expWithCount.getKey();
            }
        }
        return sorted.get(0).getKey();
        //return "NOT_FOUND";
    }

    private static String getBasicFormSecondWordFirstOrFourCase(List<Map.Entry<String, Integer>> sorted) {
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().charAt(expWithCount.getKey().length()-1) == '1' &&
                    expWithCount.getKey().charAt(expWithCount.getKey().length()-2) == 'S') {
                return expWithCount.getKey();
            }
        }
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().charAt(expWithCount.getKey().length()-1) == '1') {
                return expWithCount.getKey();
            }
        }
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().charAt(expWithCount.getKey().length()-1) == '4' &&
                    expWithCount.getKey().charAt(expWithCount.getKey().length()-2) == 'S') {
                return expWithCount.getKey();
            }
        }
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().charAt(expWithCount.getKey().length()-1) == '4') {
                return expWithCount.getKey();
            }
        }
        return sorted.get(0).getKey();
        //return "NOT_FOUND";
    }

    private static String getBasicFormFirstWordFirstOrFourCase(List<Map.Entry<String, Integer>> sorted) {
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().split(" ")[0].charAt(expWithCount.getKey().split(" ")[0].length()-1) == '1' &&
                    expWithCount.getKey().split(" ")[0].charAt(expWithCount.getKey().split(" ")[0].length()-2) == 'S') {
                return expWithCount.getKey();
            }
        }
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().split(" ")[0].charAt(expWithCount.getKey().split(" ")[0].length()-1) == '1') {
                return expWithCount.getKey();
            }
        }
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().split(" ")[0].charAt(expWithCount.getKey().split(" ")[0].length()-1) == '4' &&
                    expWithCount.getKey().split(" ")[0].charAt(expWithCount.getKey().split(" ")[0].length()-2) == 'S') {
                return expWithCount.getKey();
            }
        }
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().split(" ")[0].charAt(expWithCount.getKey().split(" ")[0].length()-1) == '4') {
                return expWithCount.getKey();
            }
        }
        return sorted.get(0).getKey();
       // return "NOT_FOUND";
    }

    private static String getType(String expression) {
        return "" + expression.split(" ")[0].charAt(expression.split(" ")[0].length()-4) +
                expression.split(" ")[1].charAt(expression.split(" ")[1].length()-4);
    }

    private static String getMostFirstCases(List<Map.Entry<String, Integer>> expsXcounts) {
        String expOneFirstCase = null;
        String expTwoFirstCases = null;
        for (Map.Entry<String, Integer> expWithCount : expsXcounts) {
            boolean firstWordFirstCase = (expWithCount.getKey().contains("1 ")) ? true : false;
            boolean secondWordFirstCase = (expWithCount.getKey().endsWith("1")) ? true : false;
            if (firstWordFirstCase && secondWordFirstCase && expTwoFirstCases == null) {
                 expTwoFirstCases = expWithCount.getKey();
            }
            else if ((firstWordFirstCase || secondWordFirstCase) && expOneFirstCase == null) {
                expOneFirstCase = expWithCount.getKey();
            }
        }

        if (expTwoFirstCases != null) {
            return expTwoFirstCases;
        }
        else if (expOneFirstCase != null) {
            return expOneFirstCase;
        }
        else {
            return null;
        }
    }
}
