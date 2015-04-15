package cz.zcu.luk.mwes.mains;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 7.11.13
 * Time: 9:57
 * To change this template use File | Settings | File Templates.
 */
public class ExpressionToValueExtractor {
    public static void main(String args[]) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplitted = line.trim().replaceAll(" +", " ").split(" ");
                if (lineSplitted.length > 5 && lineSplitted[4].startsWith("('")) {
                    pw.println(lineSplitted[4].substring(2) + " " + lineSplitted[5].substring(0, lineSplitted[5].length() - 2) + "\t" + lineSplitted[2]);
                }
                else {
                    //System.out.println("|||"+line.trim().replaceAll(" +", " ")+"|||");
                }
            }
            br.close();
            pw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
