package cz.zcu.luk.mwes.acl2011;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 25.11.12
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */
public class CompoundsWithCompositionalityData implements Serializable {
    final Map<CompoundSS, OneCompoundWithCompositionalityData> compoundsXcompositionality;
    final boolean ignoreTags; // do not know reason why this is stored since it implies from space name ending or not with "XX"

    public CompoundsWithCompositionalityData(Map<CompoundSS, OneCompoundWithCompositionalityData> compoundsXcompositionality,
            boolean ignoreTags) {
        this.compoundsXcompositionality = compoundsXcompositionality;
        this.ignoreTags = ignoreTags;
    }

//    public void storeToFile(String fileName) {
//        PrintWriter pw;
//        try {
//            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
//            for (Map.Entry<Compound, OneCompoundWithEndocentricityData> oneCompWithEndocentricity: compoundsXendocentricity.entrySet()) {
//                pw.println(oneCompWithEndocentricity.getKey().getSSpaceRep());
//                pw.println(" SH: " + oneCompWithEndocentricity.getValue().getSimilarityOfHead());
//                pw.println(" SM: " + oneCompWithEndocentricity.getValue().getSimilarityOfModifying());
//                pw.println(" DH: " + oneCompWithEndocentricity.getValue().getDistanceOfHead());
//                pw.println(" DM: " + oneCompWithEndocentricity.getValue().getDistanceOfModifying());
//            }
//            pw.close();
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
