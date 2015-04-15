package cz.zcu.luk.mwes.PDTtmp;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 29.1.14
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
public interface Criteria {
    public List<String> meetCriteria(List<String> mwesWithTags);
}
