package cz.zcu.luk.mwes.acl2011;

import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 25.11.12
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */
public class CompoundsWithEndocentricityData implements Serializable {
    final Map<CompoundSS, OneCompoundWithEndocentricityData> compoundsXendocentricity;
    final boolean ignoreTags; // do not know reason why this is stored since it implies from space name ending or not with "XX"

    public CompoundsWithEndocentricityData(Map<CompoundSS, OneCompoundWithEndocentricityData> compoundsXendocentricity,
            boolean ignoreTags) {
        this.compoundsXendocentricity = compoundsXendocentricity;
        this.ignoreTags = ignoreTags;
    }

    public void storeToFile(String fileName) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            for (Map.Entry<CompoundSS, OneCompoundWithEndocentricityData> oneCompWithEndocentricity: compoundsXendocentricity.entrySet()) {
                pw.println(oneCompWithEndocentricity.getKey().getSSpaceRep(ignoreTags));
                pw.println(" SL: " + oneCompWithEndocentricity.getValue().getSimilarityLeft());
                pw.println(" SR: " + oneCompWithEndocentricity.getValue().getSimilarityRight());
                pw.println(" DL: " + oneCompWithEndocentricity.getValue().getDistanceLeft());
                pw.println(" DR: " + oneCompWithEndocentricity.getValue().getDistanceRight());
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
