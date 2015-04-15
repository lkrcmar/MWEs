package cz.zcu.luk.mwes.acl2011;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 27.11.12
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public class OneCompoundWithAltsAndCountsData implements Serializable {
    final int compoundOriginalOccurrence;

    final List<CompoundWithCount> alternativesLeftWordReplaced;
    final List<CompoundWithCount> alternativesRightWordReplaced;

    public OneCompoundWithAltsAndCountsData(int compoundOriginalOccurrence,
                                            List<CompoundWithCount> alternativesLeftWordReplaced,
                                            List<CompoundWithCount> alternativesRightWordReplaced) {
        this.compoundOriginalOccurrence = compoundOriginalOccurrence;
        this.alternativesLeftWordReplaced = alternativesLeftWordReplaced;
        this.alternativesRightWordReplaced = alternativesRightWordReplaced;
    }
}
