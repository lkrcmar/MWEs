package cz.zcu.luk.mwes.PDTtmp;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.luk.mwes.mains.ExpsGeneratorRandom;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 29.1.14
 * Time: 21:38
 * To change this template use File | Settings | File Templates.
 */
public class CriteriaVN implements Criteria {
    public List<String> meetCriteria(List<String> mwesWithTags) {
        List<String> mwesAN = new ArrayList<String>();
        for (String mweWithTag : mwesWithTags) {
            if (ExpsGeneratorRandom.getType(mweWithTag).equals("VN")) {
                mwesAN.add(mweWithTag);
            }
        }
        return mwesAN;
    }
}
