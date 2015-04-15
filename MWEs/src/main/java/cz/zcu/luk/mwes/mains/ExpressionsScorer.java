package cz.zcu.luk.mwes.mains;

import java.util.List;
import java.util.Map;

import cz.zcu.luk.mwes.acl2011.ComValScorer;
import cz.zcu.luk.mwes.acl2011.CompoundSet;
import cz.zcu.luk.mwes.scoring.ComValScorersFactory;
import cz.zcu.luk.sspace.config.Config;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 25.2.14
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class ExpressionsScorer {

    private static final Map<String, String> config = Config.getInstance().configuration;

    public static void main (String[] args) throws Exception {
        // parameter - output format: arff versus csv
        CompoundSet compoundSet = new CompoundSet(config.get("dataDir") + "/" + config.get("expressionsScoredFN"));

        // create scorers - read their settings from file
        List<ComValScorer> allScorers = ComValScorersFactory.getScorers(config.get("dataDir") + "/" + config.get("scorersSettingsFN"));

        // score data



    }

}
