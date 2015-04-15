package cz.zcu.luk.mwes.mains;

import org.apache.commons.io.FileUtils;

import cz.zcu.luk.mwes.common.CommonMisc;
import cz.zcu.luk.mwes.datacz.LemmaHandler;
import cz.zcu.luk.mwes.datacz.TagHandler;
import cz.zcu.luk.mwes.pdt.IteratorTagM;
import cz.zcu.luk.mwes.pdt.TagMParsed;
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
public class PDTToLines {

    private static Set<String> allLemmasPlusTags;
    private static Map<String, Map<String, Integer>> toCommonStrsMap;

    private static String parseFileCSTS(File oneFile) throws Exception {
        BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "UTF-8"));
        //BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "ISO-8859-2"));
        String lemma;
        String lemmaNoTechnical;
        String tag;
        StringBuilder tagShort;
        StringBuilder output = new StringBuilder();

        String nextLine;
        String nextLineTrimmed;
        int lineNumber =0;
        while((nextLine = documentsReader.readLine()) != null) {
            lineNumber++;
            //System.out.println(lineNumber);
            nextLineTrimmed = nextLine.trim();
            int lemmaStartIndex;
            int tagStartIndex;


            /*if ((lemmaStartIndex = nextLineTrimmed.indexOf("<MDl src=\"b\">")) != -1 &&
                    (tagStartIndex = nextLineTrimmed.indexOf("<MDt src=\"b\">")) != -1) {
                lemma = nextLineTrimmed.substring(lemmaStartIndex+13, tagStartIndex);
                lemmaNoTechnical = removeTechnical(lemma);

                tag = nextLineTrimmed.substring(tagStartIndex+13, tagStartIndex+13+15);
                tagShort = extractShortTag(tag);

                output.append(lemmaNoTechnical).append(tagShort).append("_XX ");
            }
            else */ if ((lemmaStartIndex = nextLineTrimmed.indexOf("<l>")) != -1) {
                tagStartIndex = nextLineTrimmed.indexOf("<t>");
                lemma = nextLineTrimmed.substring(lemmaStartIndex+3, tagStartIndex);
                lemmaNoTechnical = removeTechnical(lemma);

                tag = nextLineTrimmed.substring(tagStartIndex+3, tagStartIndex+3+15);
                tagShort = extractShortTag(tag);

                output.append(lemmaNoTechnical).append(tagShort).append("_XX ");
            }
        }
        documentsReader.close();
        String outputStr = output.toString();
        if (outputStr.endsWith(" ")) outputStr = outputStr.substring(0, outputStr.length() - 1);

        return outputStr;
    }

    private static String zalohaparseFileCSTS(File oneFile) throws Exception {
        BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "UTF-8"));
        //BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "ISO-8859-2"));
        String lemma;
        String lemmaNoTechnical;
        String tag;
        StringBuilder tagShort;
        StringBuilder output = new StringBuilder();

        String nextLine;
        String nextLineTrimmed;
        while((nextLine = documentsReader.readLine()) != null) {
            nextLineTrimmed = nextLine.trim();
            int lemmaStartIndex;
            int tagStartIndex;
            if ((lemmaStartIndex = nextLineTrimmed.indexOf("<l>")) != -1) {
                tagStartIndex = nextLineTrimmed.indexOf("<t>");
                lemma = nextLineTrimmed.substring(lemmaStartIndex+3, tagStartIndex);
                lemmaNoTechnical = removeTechnical(lemma);

                tag = nextLineTrimmed.substring(tagStartIndex+3, tagStartIndex+3+15);
                tagShort = extractShortTag(tag);

                output.append(lemmaNoTechnical).append(tagShort).append("_XX ");
            }
        }
        documentsReader.close();
        String outputStr = output.toString();
        if (outputStr.endsWith(" ")) outputStr = outputStr.substring(0, outputStr.length() - 1);

        return outputStr;
    }


    private static String parseFile(File oneFile) throws Exception {
        BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "UTF-8"));
        //BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "ISO-8859-2"));

        StringBuilder output = new StringBuilder();

        String tagShort;
        String lemmaNoTechnical;

        IteratorTagM itM = new IteratorTagM(documentsReader);
        while(itM.hasNext()) {
            TagMParsed tagMParsed = itM.next();
            //System.out.println(tagMParsed.form + " " + tagMParsed.lemma + " " + tagMParsed.tag);
            lemmaNoTechnical = LemmaHandler.removeTechnical(tagMParsed.lemma);
            tagShort = TagHandler.extractShortTag(tagMParsed.tag);
            output.append(lemmaNoTechnical).append(tagShort).append("_XX ");
        }
        documentsReader.close();
        String outputStr = output.toString();
        if (outputStr.endsWith(" ")) outputStr = outputStr.substring(0, outputStr.length() - 1);

        return outputStr;
    }

    private static StringBuilder extractShortTag(String tag) {
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
        //runParse();
        
        runMapping();
    }

    private static void runMapping() throws Exception  {
        String inputDir = "C:\\Skola\\MWE\\pdt_cd_m";
        //String inputANlemmaTagFN = "C:\\Skola\\MWE\\ANfreqNoUpperExps.txt";
        String inputANlemmaTagFN = "C:\\Skola\\MWE\\ANfreqExps.txt";
        String outputFN = "C:\\Skola\\MWE\\ANlemmaTagXbasicForm.txt";


        Collection files = FileUtils.listFiles(new File(inputDir), new String[]{"m"}, true);

        allLemmasPlusTags = new LinkedHashSet<String>(CommonMisc.readLinesFromFile(inputANlemmaTagFN));
        toCommonStrsMap = new LinkedHashMap<String, Map<String, Integer>>();


        int counter = 1;
        for (Object oneFile : files) {
            if (counter % 100 == 0) {
                System.out.println(counter + ": " + ((File)oneFile).toString());
                //break;
            }
            parseFileAndMap((File) oneFile);
            //break;
            counter++;
        }

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFN), "UTF-8"));
        for (String lemmaPlusTag : allLemmasPlusTags) {
            Map<String, Integer> commonStrMap = toCommonStrsMap.get(lemmaPlusTag);
            if (commonStrMap == null) continue;

            List<Map.Entry<String, Integer>> sorted = MapUtil.extractEntriesSortedByValue(commonStrMap);
            Collections.reverse(sorted);

            pw.println(lemmaPlusTag + "\t" + getBasicForm(sorted, lemmaPlusTag));
            //pw.print(lemmaPlusTag);
            //for (Map.Entry<String,Integer> eee : sorted) {
            //    pw.print("|"+eee.getKey() + ":"+eee.getValue());
            //}
            //pw.println();
        }
        pw.close();
    }

    private static String getType(String expression) {
        return "" + expression.split(" ")[0].charAt(expression.split(" ")[0].length()-6) +
                expression.split(" ")[1].charAt(expression.split(" ")[1].length()-6);
    }

    private static String getBasicForm(List<Map.Entry<String, Integer>> sorted, String lemmaPlusTag) {
        String mweType = getType(lemmaPlusTag);
        if (mweType.equals("AN")) {
            return getBasicFormSecondWordFirstOrFourCase(sorted);
        }
        else if (mweType.equals("NN")) {
            //return getBasicFormFirstWordFirstOrFourCase(sorted);
            throw new IllegalStateException();
        }
        else if (mweType.equals("VN")) {
            //return getBasicFormFirstWordInfinitiv(sorted);
            throw new IllegalStateException();
        }
        else if (mweType.equals("NV")) {
            //return getBasicFormFirstWordFirstOrFourCase(sorted);
            throw new IllegalStateException();
        }
        else {
            throw new IllegalStateException();
        }
        //return sorted.get(0).getKey();
    }

    private static String getBasicFormSecondWordFirstOrFourCase(List<Map.Entry<String, Integer>> sorted) {
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().charAt(expWithCount.getKey().length()-3) == '1' &&
                    expWithCount.getKey().charAt(expWithCount.getKey().length()-4) == 'S') {
                //return lowerCasedFirstLetttersIfExists(expWithCount.getKey(), sorted);
                return expWithCount.getKey();
            }
        }
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().charAt(expWithCount.getKey().length()-3) == '1') {
                //return lowerCasedFirstLetttersIfExists(expWithCount.getKey(), sorted);
                return expWithCount.getKey();
            }
        }
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().charAt(expWithCount.getKey().length()-3) == '4' &&
                    expWithCount.getKey().charAt(expWithCount.getKey().length()-4) == 'S') {
                //return lowerCasedFirstLetttersIfExists(expWithCount.getKey(), sorted);
                return expWithCount.getKey();
            }
        }
        for (Map.Entry<String, Integer> expWithCount : sorted) {
            //System.out.println(expWithCount.getKey().charAt(expWithCount.getKey().length()-1));
            if (expWithCount.getKey().charAt(expWithCount.getKey().length()-3) == '4') {
                //return lowerCasedFirstLetttersIfExists(expWithCount.getKey(), sorted);
                return expWithCount.getKey();
            }
        }
        //return lowerCasedFirstLetttersIfExists(sorted.get(0).getKey(), sorted);
        return sorted.get(0).getKey();
        //return "NOT_FOUND";
    }

    private static String lowerCasedFirstLetttersIfExists(String expression, List<Map.Entry<String, Integer>> sorted) {
        String[] words = expression.split(" ");
        String firstWordLowerCased = words[0].substring(0, words[0].length() - 5).toLowerCase(); // 5 - tag length
        firstWordLowerCased += words[0].substring(words[0].length() -5);
        String secondWordLowerCased = words[1].substring(0, words[1].length() - 5).toLowerCase(); // 5 - tag length
        secondWordLowerCased += words[1].substring(words[1].length() -5);

        String lowerCasedExp = firstWordLowerCased + " " + secondWordLowerCased;
        if (lowerCasedExp.equals(expression)) {
            return expression;
        }

        //System.out.print(lowerCasedFirstLettersExp+":"+expression);
        for (Map.Entry<String, Integer> e : sorted) {
            //System.out.print("|"+e.getKey());
            if (e.getKey().equals(lowerCasedExp)) {
                System.out.println(expression + "0----" + e.getKey());
                return e.getKey();
            }
        }

        String lowerCasedFirstWord = firstWordLowerCased + " " + words[1];
        if (!lowerCasedFirstWord.equals(expression)) {
            for (Map.Entry<String, Integer> e : sorted) {
                //System.out.print("|"+e.getKey());
                if (e.getKey().equals(lowerCasedFirstWord)) {
                    System.out.println(expression + "1----" + e.getKey());
                    return e.getKey();
                }
            }
        }
        String lowerCasedSecondWord = words[0] + " " + secondWordLowerCased;
        if (!lowerCasedSecondWord.equals(expression)) {
            for (Map.Entry<String, Integer> e : sorted) {
                //System.out.print("|"+e.getKey());
                if (e.getKey().equals(lowerCasedSecondWord)) {
                    System.out.println(expression + "2----" + e.getKey());
                    return e.getKey();
                }
            }
        }

        return expression;
    }


    private static void parseFileAndMap(File oneFile) throws Exception {
        BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "UTF-8"));
        IteratorTagM itM = new IteratorTagM(documentsReader);

        if (itM.hasNext()) {
            TagMParsed tagMParsedOld;
            TagMParsed tagMParsed = itM.next();
            while(itM.hasNext()) {
                tagMParsedOld = tagMParsed;
                tagMParsed = itM.next();
                addToStrToLemmaMap(tagMParsedOld, tagMParsed);
            }
        }
        documentsReader.close();
    }

    private static void addToStrToLemmaMap(TagMParsed oldMParsed, TagMParsed mParsed) {
        String oldWord = oldMParsed.form;
        String oldLemmaNoTechnical = LemmaHandler.removeTechnical(oldMParsed.lemma);
        String oldTagShort = TagHandler.extractShortTag(oldMParsed.tag);
        String oldTagShortWithNumCase = TagHandler.extractShortTagWithNumberAndCase(oldMParsed.tag);
        String word = mParsed.form;
        String lemmaNoTechnical = LemmaHandler.removeTechnical(mParsed.lemma);
        String tagShort = TagHandler.extractShortTag(mParsed.tag);
        String tagShortWithNumCase = TagHandler.extractShortTagWithNumberAndCase(mParsed.tag);

        String lemmaTagXXLemmaTagXX = oldLemmaNoTechnical+oldTagShort+"_XX " + lemmaNoTechnical+tagShort+"_XX";

        if (allLemmasPlusTags.contains(lemmaTagXXLemmaTagXX)) { //   if processed bigram..
           // String wordLCTagWordLCTtag = oldWord.toLowerCase() + oldTagShortWithNumCase + " " + word.toLowerCase() + tagShortWithNumCase;
            String wordLCTagWordLCTtag = oldWord + oldTagShortWithNumCase + " " + word + tagShortWithNumCase;
            //System.out.println(lemmaTagXXLemmaTagXX + " | " + wordTagWordTtag);
            if (toCommonStrsMap.containsKey(lemmaTagXXLemmaTagXX)) {
                Map<String, Integer> commonStrToCount = toCommonStrsMap.get(lemmaTagXXLemmaTagXX);
                Integer count = commonStrToCount.get(wordLCTagWordLCTtag);
                if (count == null) {
                    commonStrToCount.put(wordLCTagWordLCTtag, 1);
                }
                else {
                    commonStrToCount.put(wordLCTagWordLCTtag, count+1);
                }
            }
            else {
                Map<String, Integer> commonStrXCount = new HashMap<String, Integer>();
                commonStrXCount.put(wordLCTagWordLCTtag, 1);
                toCommonStrsMap.put(lemmaTagXXLemmaTagXX, commonStrXCount);
            }
        }
    }

    private static void runParse() throws Exception  {
        //String inputDir = "C://tmttt//data//utf8";
        //String inputDir = "/storage/brno2/home/lkrcmar/MWE/pdt/utf8/";
        //String inputDir = "/storage/brno2/home/lkrcmar/MWEPDT/pdt_cd_m/";
        String inputDir = "C:\\Skola\\MWE\\pdt_cd_m";

        //String outputFN = "C://tmttt//output.txt";
        String outputFN = "C:\\Skola\\MWE\\pdtAllM.txt";


        Collection files;
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFN), "UTF-8"));

        files = FileUtils.listFiles(new File(inputDir), new String[]{"m"}, true);

        for (Object oneFile : files) {
            //System.out.println(((File)oneFile).toString());
            String fileInString = parseFile((File) oneFile);
            pw.println(fileInString);
        }
        pw.close();
    }

}
