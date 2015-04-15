package cz.zcu.luk.mwes.acl2011;

import cz.zcu.luk.sspace.common.HybridStaticSemanticSpace;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import cz.zcu.luk.sspace.config.Config;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 27.11.12
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public class SSpaceProvider {
    private static final SSpaceProvider INSTANCE = new SSpaceProvider();

    private String semanticSpaceName = null;
    private SemanticSpace space = null;

    private SSpaceProvider() {}

    public static SSpaceProvider getInstance() {
        return INSTANCE;
    }

    private void loadSemanticSpace(String semanticSpaceName) {
        String ssFullPath = Config.getInstance().configuration.get("dataDir") + File.separator +
                Config.getInstance().configuration.get("wordSpaceDir") + File.separator +
                semanticSpaceName + Constants.SSFN_EXTENSION;

        try {
            if (semanticSpaceName.toString().startsWith("H_")) {
                space = new HybridStaticSemanticSpace(ssFullPath);
            }
            else {
                space = SemanticSpaceIO.load(ssFullPath);
            }
            this.semanticSpaceName = semanticSpaceName;
            System.out.println("Semantic space \"" + this.semanticSpaceName.toString() + "\"loaded..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SemanticSpace getSSpace(String semanticSpaceName) {
        if (this.semanticSpaceName != semanticSpaceName) {
            loadSemanticSpace(semanticSpaceName);
        }
        return space;
    }
}
