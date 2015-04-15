package cz.zcu.luk.mwes.acl2011;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 15.2.13
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
public enum PrecRecGraphSmoothing {
    NO, // precision is tooth-shaped
    FIRST_TYPE, // precision is non-increasing
    SECOND_TYPE // precision is non-increasing and always in different points of recall
}
