package cz.zcu.luk.mwes.mains;

/**
 * Transforms all disco compounds to the form in which they occur in disco tagged dataset..
 * creates alternatives of VerbObj compound type with determiners
 * prints resulting list into the given file..
 *
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 7.11.12
 * Time: 8:06
 * To change this template use File | Settings | File Templates.
 */
public class DiscoCompoundsPreparer {

//    public static void main(String[] args) {
//        Config config = new Config();
//        config.loadConfinguration(args[0]);
//        boolean ignoreTags = Boolean.parseBoolean(args[1]);
//
//        NounsToNoun ntn = new NounsToNoun();
//        Map<String, String> nounsMapping = ntn.getNounsToNounMapping(Config.NOUNS_MAPPING_FILE_NAME);
//
//        // load compound sets (loads Num compound sets..)
//        ArrayList<ACLCompoundSetNum> dataSets = new ArrayList<ACLCompoundSetNum>();
//        dataSets.add(ACLCompoundSetNum.EN_TRAINVAL_NUM);
//        dataSets.add(ACLCompoundSetNum.EN_TEST_NUM);
//
//        CompoundSetsHandler compoundsSetsHandler = new CompoundSetsHandler();
//        ArrayList<CompoundSet> compoundSets = compoundsSetsHandler.loadNumCompoundSets(nounsMapping, dataSets);
//        try {
//            String outputFileName = config.DISCO_COMPOUNDS_FILE_NAME;
//            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
//            for (CompoundSet oneComSet : compoundSets) {
//                for (CompoundSS oneCom : oneComSet.getCompounds()) {
//                    pw.println(oneCom.getSSpaceRep(ignoreTags));
//                    if (oneCom.getTag().equals(ACLCompoundTag.EN_V_OBJ)) {
//                        pw.println(oneCom.getSSpaceRep(ignoreTags).replace(" ", " " + Constants.DET_A + " "));
//                        pw.println(oneCom.getSSpaceRep(ignoreTags).replace(" ", " " + Constants.DET_AN + " "));
//                        pw.println(oneCom.getSSpaceRep(ignoreTags).replace(" ", " " + Constants.DET_THE + " "));
//                    }
//                }
//            }
//            pw.close();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}



