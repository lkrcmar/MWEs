package cz.zcu.luk.mwes.acl2011;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 15.11.12
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public enum EndocentricityType {
    HEAD_SIM,
    MODIFYING_SIM,
    HEAD_DIST_INV, // inverted distance of head word
    MODIFYING_DIST_INV, // inverted distance of modifying word
    LEFT_SIM,
    RIGHT_SIM,
    MIN_SIM,
    MAX_SIM,
    MIN_DIST_INV, // inverted minimum distance from expression words
    MAX_DIST_INV, // inverted maximum distance from expression words
    AVG_SIM,
    AVG_DIST_INV, // inverted average distance from expression words
    AVG_SIM_NEG,
    LEFT_SIM_NEG,
    RIGHT_SIM_NEG
}
