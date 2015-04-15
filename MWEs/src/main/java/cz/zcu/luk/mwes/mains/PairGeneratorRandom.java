package cz.zcu.luk.mwes.mains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 10.2.14
 * Time: 20:43
 * To change this template use File | Settings | File Templates.
 */
public class PairGeneratorRandom {
    private static final int RANDOM_CONST = 1;
    private static final int REPETITION_COUNT = 10;
    private static final int EXP_COUNT = 200;


    public static void main(String args[]){
        Random genGenerator = new Random(RANDOM_CONST);
        List<Random> generators = new ArrayList<Random>();
        for (int i = 0; i < REPETITION_COUNT; i++) {
            generators.add(new Random(genGenerator.nextInt()));
        }

        List<Integer> intNumbers = new ArrayList<Integer>();
        for (int i = 0; i < 200; i++) {
            intNumbers.add(i, i);
        }
        for (int i = 0; i < REPETITION_COUNT; i++) {
            List<Integer> newList = new ArrayList<Integer>(intNumbers);
            Collections.shuffle(newList, generators.get(i));
            //System.out.println(newList.toString());
            for (int j = 0; j < newList.size(); j+=2) {
                System.out.println("(NULL, '" + (newList.get(j)+1) + "', '" + (newList.get(j+1)+1) + "')");
            }
        }


    }
}
