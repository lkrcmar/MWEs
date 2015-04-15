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
public class CompoundsWithAltsAndCountsData implements Serializable {
    final Map<CompoundSS, OneCompoundWithAltsAndCountsData> compoundsXaltsAndCount;
    final boolean ignoreTags; // do not know reason why this is stored since it implies from space name ending or not with "XX"

    public CompoundsWithAltsAndCountsData(Map<CompoundSS, OneCompoundWithAltsAndCountsData> compoundsXaltsAndCount,
            boolean ignoreTags) {
        this.compoundsXaltsAndCount = compoundsXaltsAndCount;
        this.ignoreTags = ignoreTags;
    }

    public void storeToFile(String fileName) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            for (Map.Entry<CompoundSS, OneCompoundWithAltsAndCountsData> oneCompWithAltsAndCounts: compoundsXaltsAndCount.entrySet()) {
                pw.println(oneCompWithAltsAndCounts.getKey().getSSpaceRep(ignoreTags) + " " + oneCompWithAltsAndCounts.getValue().compoundOriginalOccurrence);
                int counter = 1;
                for (CompoundWithCount oneComWithCount : oneCompWithAltsAndCounts.getValue().alternativesLeftWordReplaced) {
                    if (counter > Constants.PRINTED_ALTERNATIVES) break;
                    pw.println(" " + oneComWithCount.toString());
                    counter++;
                }
                counter = 1;
                for (CompoundWithCount oneComWithCount : oneCompWithAltsAndCounts.getValue().alternativesRightWordReplaced) {
                    if (counter > Constants.PRINTED_ALTERNATIVES) break;
                    pw.println(" " + oneComWithCount.toString());
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
