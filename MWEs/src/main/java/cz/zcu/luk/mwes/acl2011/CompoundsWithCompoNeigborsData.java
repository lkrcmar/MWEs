package cz.zcu.luk.mwes.acl2011;

import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 27.11.12
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class CompoundsWithCompoNeigborsData implements Serializable {
    final Map<CompoundSS, OneCompoundWithCompoNeighborsData> compoundsXcompoNeigborsData;
    final boolean ignoreTags; // do not know reason why this is stored since it implies from space name ending or not with "XX"

    public CompoundsWithCompoNeigborsData(Map<CompoundSS, OneCompoundWithCompoNeighborsData> compoundsXcompoNeigborsData,
            boolean ignoreTags) {
        this.compoundsXcompoNeigborsData = compoundsXcompoNeigborsData;
        this.ignoreTags = ignoreTags;
    }

    public void storeToFile(String fileName) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            for (Map.Entry<CompoundSS, OneCompoundWithCompoNeighborsData> oneCompoNeigborsData: compoundsXcompoNeigborsData.entrySet()) {
                int counter = 1;
                pw.println(oneCompoNeigborsData.getKey().getSSpaceRep(ignoreTags));
                for (int i = 0; i < oneCompoNeigborsData.getValue().neigborsCompound.size(); i++) {
                    if (counter > Constants.COMPONEIGBORS_PRINTED_ALTERNATIVES) break;
                    pw.println("\t" + oneCompoNeigborsData.getValue().neigborsCompound.get(i) + "\t" +
                            oneCompoNeigborsData.getValue().neigborsLeftWord.get(i) + "\t" +
                            oneCompoNeigborsData.getValue().neigborsRightWord.get(i) );
                    counter++;
                }
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
