package cz.zcu.luk.mwes.evaluation;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 30.3.14
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */
public class ScoredPairMap {
    public LinkedHashMap<Integer, String> idPairToScore;

    public ScoredPairMap() {
        idPairToScore = new LinkedHashMap<Integer, String>();
    }

    public void put(int idPair, String score) {
        idPairToScore.put(idPair, score);
    }

    public String get(int idPair) {
        return idPairToScore.get(idPair);
    }

    public int size() {
        return idPairToScore.size();
    }

    @Override
    public String toString() {
        return idPairToScore.toString();
    }

    public Map<Integer, String> getMap() {
        return idPairToScore;
    }

}
