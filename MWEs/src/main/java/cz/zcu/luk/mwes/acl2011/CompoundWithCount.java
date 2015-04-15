package cz.zcu.luk.mwes.acl2011;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 27.11.12
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class CompoundWithCount implements Serializable {
    String compound;
    int occurrence;

    public CompoundWithCount(String compound, int occurrence) {
        this.compound = compound;
        this.occurrence = occurrence;
    }

    @Override
    public String toString() {
        return compound + " " + occurrence;
    }
}
