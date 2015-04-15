package cz.zcu.luk.mwes.mains;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.zcu.luk.mwes.PDTtmp.*;
import cz.zcu.luk.mwes.common.CommonMisc;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 17.1.14
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class ExpsGeneratorRandom {
    private static final int RANDOM_CONST = 1;

    public static void main(String args[]){
        //List<String> mwesAllRows = CommonMisc.readLinesFromFile("C:\\Skola\\korpus\\CTK\\expsX.txt");
        List<String> mwesAllRows = CommonMisc.readLinesFromFile("C:\\Skola\\MWE\\ANlemmaTagXbasicForm.txt");
        Criteria mwesAllCrit = new CriteriaMWEsBasic();
        Criteria mwesANcrit = new CriteriaAN();
        Criteria mwesNNcrit = new CriteriaNN();
        Criteria mwesVNcrit = new CriteriaVN();
        Criteria mwesNVcrit = new CriteriaNV();
        List<String> mwesAll = mwesAllCrit.meetCriteria(mwesAllRows);
        List<String> mwesAllAN = mwesANcrit.meetCriteria(mwesAll);
        List<String> mwesAllNN = mwesNNcrit.meetCriteria(mwesAll);
        List<String> mwesAllVN = mwesVNcrit.meetCriteria(mwesAll);
        List<String> mwesAllNV = mwesNVcrit.meetCriteria(mwesAll);

        int sizeAll = mwesAll.size();
        int sizeAN = mwesAllAN.size();
        int sizeNN = mwesAllNN.size();
        int sizeVN = mwesAllVN.size();
        int sizeNV = mwesAllNV.size();

        System.out.println(sizeAll);
        System.out.println(sizeAN);
        System.out.println(sizeNN);
        System.out.println(sizeVN);
        System.out.println(sizeNV);
        System.out.println();
        System.out.println("------------");
        System.out.println();


        Random rand = new Random(RANDOM_CONST);
        List<Integer> intNumbers = new ArrayList<Integer>();
        for (int i = 0; i < sizeAN; i++) {
            intNumbers.add(i, i);
        }

        int count = 0;
        for (int number : intNumbers) {
            System.out.println(getMWEprinted(mwesAllAN.get(number)));
            count++;
            //if (count >200) break;
        }

//
//        rand = new Random(RANDOM_CONST);
//        int m;
//        for (int i = 0; i < 140; i++) {
//            //n = rand.nextInt(sizeAll);
//            n = rand.nextInt(sizeAN);
//            if (ExpsGeneratorRandom.getType(mwesAll.get(n)).equals("AN")) {
//                m = rand.nextInt(sizeAN);
//                System.out.println(getMWEprinted(mwesAll.get(n)) + "\t" + getMWEprinted(mwesAllAN.get(m)));
//            }
//            else if (ExpsGeneratorRandom.getType(mwesAll.get(n)).equals("NN")) {
//                m = rand.nextInt(sizeNN);
//                System.out.println(getMWEprinted(mwesAll.get(n)) + "\t" + getMWEprinted(mwesAllNN.get(m)));
//            }
//            else if (ExpsGeneratorRandom.getType(mwesAll.get(n)).equals("VN")) {
//                m = rand.nextInt(sizeVN);
//                System.out.println(getMWEprinted(mwesAll.get(n)) + "\t" + getMWEprinted(mwesAllVN.get(m)));
//            }
//            else if (ExpsGeneratorRandom.getType(mwesAll.get(n)).equals("NV")) {
//                m = rand.nextInt(sizeNV);
//                System.out.println(getMWEprinted(mwesAll.get(n)) + "\t" + getMWEprinted(mwesAllNV.get(m)));
//            }
//        }
    }

    public static String getType(String expression) {
        return "" + expression.split(" ")[0].charAt(expression.split(" ")[0].length()-5) +
                expression.split(" ")[1].charAt(expression.split(" ")[1].length()-5);
    }


    private static String getMWEprinted(String mweWithTag) {
        String[] wordsWithTags = mweWithTag.split(" ");
        return wordsWithTags[0].substring(0, wordsWithTags[0].length()-5) +" "+ wordsWithTags[1].substring(0, wordsWithTags[1].length()-5);
    }

    private static String getMWEfromRow(String rowWithMWE) {
        String mweWithTag = rowWithMWE.split("\t")[1];
        String[] wordsWithTags = mweWithTag.split(" ");
        return wordsWithTags[0].substring(0, wordsWithTags[0].length()-2) +" "+ wordsWithTags[1].substring(0, wordsWithTags[1].length()-2);
    }
}
