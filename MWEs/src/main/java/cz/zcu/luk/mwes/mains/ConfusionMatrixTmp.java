package cz.zcu.luk.mwes.mains;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 12.2.14
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public class ConfusionMatrixTmp {


    public static void main(String[] args) {
        List<String> data = new ArrayList<String>();
        data.add("=,=");
        data.add("=,=");
        data.add(">,<");
        data.add(">,>");
        data.add("<,>");
        data.add("=,=");
        data.add(">,>");
        data.add("<,=");
        data.add(">,=");
        data.add("<,=");
        data.add("=,=");
        data.add("<,<");
        data.add("<,=");
        data.add("=,>");
        data.add("=,=");
        data.add(">,>");
        data.add(">,>");
        data.add("=,>");
        data.add("<,=");
        data.add(">,=");
        data.add(">,=");
        data.add(">,>");
        data.add("<,<");
        data.add("<,<");
        data.add(">,>");
        data.add("?,=");
        data.add("=,=");
        data.add("=,=");
        data.add("<,<");
        data.add("?,?");
        data.add("<,<");
        data.add("=,>");
        data.add(">,<");
        data.add("=,=");
        data.add("?,?");
        data.add(">,>");
        data.add(">,>");
        data.add("=,<");
        data.add("<,>");
        data.add("=,<");
        data.add(">,=");
        data.add("?,>");
        data.add("<,=");
        data.add("=,=");
        data.add("?,=");
        data.add("=,?");
        data.add("=,<");
        data.add("=,=");
        data.add("=,<");
        data.add(">,=");
        data.add("=,>");
        data.add("=,=");
        data.add("<,<");
        data.add("<,<");
        data.add("=,>");
        data.add(">,=");
        data.add("=,=");
        data.add("=,<");
        data.add("?,=");
        data.add(">,<");
        data.add("=,>");
        data.add("=,=");
        data.add("<,=");
        data.add("<,<");
        data.add("<,<");
        data.add("?,<");
        data.add("=,=");
        data.add(">,>");
        data.add(">,=");
        data.add("?,=");
        data.add(">,>");
        data.add(">,=");
        data.add(">,=");
        data.add(">,<");
        data.add("?,=");
        data.add("<,=");
        data.add("<,<");
        data.add("<,=");
        data.add("=,=");
        data.add("<,=");
        data.add("=,<");
        data.add("=,>");
        data.add(">,>");
        data.add("=,=");
        data.add("?,=");
        data.add("<,=");
        data.add("?,>");
        data.add(">,>");
        data.add("<,<");
        data.add("=,=");
        data.add(">,>");
        data.add(">,>");
        data.add("<,>");
        data.add("=,<");
        data.add("=,<");
        data.add("=,<");
        data.add("<,<");
        data.add("<,<");
        data.add("<,=");
        data.add("=,=");
        data.add("?,<");
        data.add("=,>");
        data.add(">,>");
        data.add("=,=");
        data.add("?,>");
        data.add(">,>");
        data.add("?,<");
        data.add("<,=");
        data.add("=,=");
        data.add(">,=");
        data.add("<,=");
        data.add("?,=");
        data.add(">,>");
        data.add("=,<");
        data.add("?,>");
        data.add("<,<");
        data.add("=,=");
        data.add("=,<");
        data.add("<,<");
        data.add("<,<");
        data.add("=,=");
        data.add("<,<");
        data.add("=,<");
        data.add("=,<");
        data.add("=,<");
        data.add("=,>");
        data.add("=,>");
        data.add("<,<");
        data.add("=,=");
        data.add("<,<");
        data.add(">,>");
        data.add(">,<");
        data.add("<,<");
        data.add("=,>");
        data.add("=,>");
        data.add("=,>");
        data.add("<,<");
        data.add("=,>");
        data.add("<,<");
        data.add("?,=");
        data.add(">,>");
        data.add("<,<");
        data.add("=,>");
        data.add("=,>");
        data.add("=,<");
        data.add("=,=");
        data.add("=,=");
        data.add("=,<");
        data.add("=,=");
        data.add("=,>");
        data.add(">,>");
        data.add(">,>");
        data.add("=,=");
        data.add("=,<");
        data.add("<,>");
        data.add("?,>");
        data.add("=,>");
        data.add(">,>");
        data.add("?,?");
        data.add("=,=");
        data.add(">,=");
        data.add("<,<");
        data.add("<,<");
        data.add("=,>");
        data.add("<,<");
        data.add(">,=");
        data.add(">,>");
        data.add(">,>");
        data.add("=,=");
        data.add("<,<");
        data.add(">,<");
        data.add("=,>");
        data.add("=,=");
        data.add("<,<");
        data.add("<,<");
        data.add("=,=");
        data.add("<,<");
        data.add("<,<");
        data.add(">,=");
        data.add(">,=");
        data.add(">,>");
        data.add(">,=");
        data.add("=,<");
        data.add("=,>");
        data.add(">,=");
        data.add("=,<");
        data.add(">,=");
        data.add(">,=");
        data.add("<,<");
        data.add("<,<");
        data.add("=,>");
        data.add("<,<");
        data.add("=,=");
        data.add(">,=");
        data.add("?,=");
        data.add("=,>");
        data.add("=,>");
        data.add(">,=");
        data.add(">,=");
        data.add("<,<");
        data.add(">,<");
        data.add(">,=");
        data.add(">,=");
        data.add("<,=");
        data.add("=,=");
        data.add("<,<");
        data.add("=,>");
        data.add(">,=");
        data.add(">,>");
        data.add("=,=");
        data.add("=,=");
        data.add("<,>");

        Map<String, Integer> converter = new HashMap<String, Integer>();
        converter.put("<", 0);
        converter.put(">", 1);
        converter.put("=", 2);
        converter.put("?", 3);

        int[][] result = new int[4][4];
        for (String singleD : data) {
            int indexFirst = converter.get(singleD.split(",")[0]);
            int indexSecond = converter.get(singleD.split(",")[1]);
            result[indexFirst][indexSecond]++;
        }
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j <result[i].length; j++) {
                System.out.print(result[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
