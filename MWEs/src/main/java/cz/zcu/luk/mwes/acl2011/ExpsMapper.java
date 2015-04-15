package cz.zcu.luk.mwes.acl2011;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 18.9.13
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class ExpsMapper {
//    public static ACLCompoundTag getACLTag(CompoundSS compoundSS) {
//        if (compoundSS.getFirstWord().getTag().equals(SSWordTag.XX) && compoundSS.getSecondWord().getTag().equals(SSWordTag.XX)) return ACL;
//        if (compoundSS.getFirstWord().getTag().equals(SSWordTag.JJ) && compoundSS.getSecondWord().getTag().equals(SSWordTag.NN)) return false;
//        if (compoundSS.getFirstWord().getTag().equals(SSWordTag.NN) && compoundSS.getSecondWord().getTag().equals(SSWordTag.VV)) return false;
//        if (compoundSS.getFirstWord().getTag().equals(SSWordTag.NN) && compoundSS.getSecondWord().getTag().equals(SSWordTag.NN)) return false;
//        if (compoundSS.getFirstWord().getTag().equals(SSWordTag.VV) && compoundSS.getSecondWord().getTag().equals(SSWordTag.NN)) return true;
//
//        throw new IllegalStateException("Unexpected tag combination!");
//    }

    public static CompoundACLNumBean getACLExpression(CompoundSetNumData numData, CompoundSS compoundSS, double score) {
        ACLCompoundTag compoundTag = numData.getTagForCompound(compoundSS.getCompound());
        String compound = compoundSS.getCompound();
        return new CompoundACLNumBean(compound, score, compoundTag);
    }

    public static String getACLExpressionInString(CompoundSetNumData numData, CompoundSS compoundSS, double score) {
        return getACLExpression(numData, compoundSS, score).toString();
    }

    private static SSWordTag getSSTagForFirstWord(ACLCompoundTag aclCompoundTag) {
        switch (aclCompoundTag) {
            case EN_ADJ_NN: return SSWordTag.JJ;
            case EN_V_OBJ: return SSWordTag.VV;
            case EN_V_SUBJ: return SSWordTag.NN;
            case RE_NN_NN: return SSWordTag.NN;
            default: {
                System.out.println("Unknown tag!");
                return null;
            }
        }
    }

    private static SSWordTag getSSTagForSecondWord(ACLCompoundTag aclCompoundTag) {
        switch (aclCompoundTag) {
            case EN_ADJ_NN: return SSWordTag.NN;
            case EN_V_OBJ: return SSWordTag.NN;
            case EN_V_SUBJ: return SSWordTag.VV;
            case RE_NN_NN: return SSWordTag.NN;
            default: {
                System.out.println("Unknown tag!");
                return null;
            }
        }
    }

    public static CompoundSS getCompoundSS(CompoundACLNumBean comACL) {
        SSWord firstWord = new SSWord(comACL.getCompound().split(" ")[0], getSSTagForFirstWord(comACL.getTag()));
        SSWord secondWord = new SSWord(comACL.getCompound().split(" ")[1], getSSTagForSecondWord(comACL.getTag()));
        return (new CompoundSS(firstWord, secondWord));
    }

}
