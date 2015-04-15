package cz.zcu.luk.mwes.mains;

import java.util.ArrayList;

import cz.zcu.luk.mwes.acl2011.UKWACcorporaXML;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 7.3.13
 * Time: 11:00
 * To change this template use File | Settings | File Templates.
 */
public class TagStopwordsFinder {
    public static void main(String[] args) {
        UKWACcorporaXML ucx = new UKWACcorporaXML();
        String inputFN = "/storage/home/lkrcmar/MWE/CLEAN_UKWAC_TXT/allDocs.txt";
        String outputFN = "/storage/home/lkrcmar/MWE/tagStopwordsInAllDocsXX.txt";

        ArrayList<String> preservedTags = new ArrayList<String>();
        preservedTags.add("NN");
        preservedTags.add("NP");
        preservedTags.add("JJ");
        preservedTags.add("RB");
        preservedTags.add("VV");
        ucx.findStopwords(inputFN, outputFN, preservedTags, true);
    }
}
