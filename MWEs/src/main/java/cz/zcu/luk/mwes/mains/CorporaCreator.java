package cz.zcu.luk.mwes.mains;

import cz.zcu.luk.mwes.acl2011.Constants;
import cz.zcu.luk.mwes.acl2011.UKWACcorporaXML;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 12.11.12
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class CorporaCreator {

    public static void main(String[] args) {
        // transformes UKWAC corpora XML files to text format
	    UKWACcorporaXML ucx = new UKWACcorporaXML();
        String outputFileName = "/storage/home/lkrcmar/MWE/CLEAN_UKWAC_TXT/20000.txt";
    	ucx.transformCleanXMLtoTXT(Constants.CLEAN_UKWAC_FN, outputFileName, false);
    }
}
