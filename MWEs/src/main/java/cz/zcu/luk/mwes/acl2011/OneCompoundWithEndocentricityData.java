package cz.zcu.luk.mwes.acl2011;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 15.11.12
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
public class OneCompoundWithEndocentricityData implements Serializable {
    private final double similarityLeft;
    private final double similarityRight;
    private final int distanceLeft;
    private final int distanceRight;

    public double getSimilarityLeft() {
        return similarityLeft;
    }

    public double getSimilarityRight() {
        return similarityRight;
    }

    public int getDistanceLeft() {
        return distanceLeft;
    }

    public int getDistanceRight() {
        return distanceRight;
    }

    public OneCompoundWithEndocentricityData(double similarityLeft, double similarityRight,
                                             int distanceLeft, int distanceRight) {
        this.similarityLeft = similarityLeft;
        this.similarityRight = similarityRight;
        this.distanceLeft = distanceLeft;
        this.distanceRight = distanceRight;
    }
}
