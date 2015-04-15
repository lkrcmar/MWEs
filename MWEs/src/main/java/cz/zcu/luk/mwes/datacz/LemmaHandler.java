package cz.zcu.luk.mwes.datacz;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 5.2.14
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class LemmaHandler {
    public static String removeTechnical(String lemma) {
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
}
