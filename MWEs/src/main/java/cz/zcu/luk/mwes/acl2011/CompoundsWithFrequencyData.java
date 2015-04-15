package cz.zcu.luk.mwes.acl2011;

import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 1.11.13
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */
public class CompoundsWithFrequencyData implements Serializable {
    final Map<CompoundSS, OneCompoundWithFrequencyData> compoundsXfrequencies;
    final boolean ignoreTags; // do not know reason why this is stored since it implies from space name ending or not with "XX"

    public CompoundsWithFrequencyData(Map<CompoundSS, OneCompoundWithFrequencyData> compoundsXfrequencies,
                                           boolean ignoreTags) {
        this.compoundsXfrequencies = compoundsXfrequencies;
        this.ignoreTags = ignoreTags;
    }

    public void storeToFile(String fileName) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            for (Map.Entry<CompoundSS, OneCompoundWithFrequencyData> oneCompWithFrequencies: compoundsXfrequencies.entrySet()) {
                pw.println(oneCompWithFrequencies.getKey().getSSpaceRep(ignoreTags));
                pw.println(" FE: " + oneCompWithFrequencies.getValue().getFrequencyOfExpression());
                pw.println(" FL: " + oneCompWithFrequencies.getValue().getFrequencyOfLeftWord());
                pw.println(" FR: " + oneCompWithFrequencies.getValue().getFrequencyOfRightWord());
            }
            pw.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
