package cz.zcu.luk.mwes.mains;

import cz.zcu.luk.mwes.acl2011.UKWACcorporaXML;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 6.3.13
 * Time: 18:13
 * To change this template use File | Settings | File Templates.
 */
public class AllTagFinder {
    public static void main(String[] args) {
        UKWACcorporaXML ucx = new UKWACcorporaXML();
        String inputFN = "/storage/home/lkrcmar/MWE/CLEAN_UKWAC_TXT/allDocs.txt";
        String outputFN = "/storage/home/lkrcmar/MWE//allTagsInAllDocs.txt";

        ucx.findAllTags(inputFN, outputFN);
    }
}
