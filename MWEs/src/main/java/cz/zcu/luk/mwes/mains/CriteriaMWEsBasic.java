package cz.zcu.luk.mwes.mains;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.luk.mwes.PDTtmp.Criteria;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 29.1.14
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public class CriteriaMWEsBasic implements Criteria {
    public List<String> meetCriteria(List<String> mwesAllRows) {
        List<String> mwesAllBasic = new ArrayList<String>();
        for (String mweRow : mwesAllRows) {
            mwesAllBasic.add(mweRow.split("\t")[1]);
        }
        return mwesAllBasic;
    }
}
