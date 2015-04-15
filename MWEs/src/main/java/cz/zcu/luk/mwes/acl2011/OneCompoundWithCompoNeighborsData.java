package cz.zcu.luk.mwes.acl2011;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 5.3.13
 * Time: 18:24
 * To change this template use File | Settings | File Templates.
 */
public class OneCompoundWithCompoNeighborsData implements Serializable {
    final List<String> neigborsCompound;
    final List<String> neigborsLeftWord;
    final List<String> neigborsRightWord;

    public OneCompoundWithCompoNeighborsData(List<String> neigborsCompound,
                                             List<String> neigborsLeftWord,
                                             List<String> neigborsRightWord) {
        this.neigborsCompound = neigborsCompound;
this.neigborsLeftWord = neigborsLeftWord;
this.neigborsRightWord = neigborsRightWord;
}
        }
