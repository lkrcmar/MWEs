package cz.zcu.luk.mwes.acl2011;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 15.11.12
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public interface ValScoringAble {
    public String getName();
    public String getType();

    public double getScore(CompoundSS compound);


}
