package cz.zcu.luk.mwes.acl2011;

public class CompoundACL {
    protected String compound;
	protected ACLCompoundTag tag;

	public ACLCompoundTag getTag() {
		return tag;
	}
	public String getCompound() {
		return compound;
	}
	
	protected ACLCompoundTag getACLCompoundTag(String tagInString) {
		if (tagInString.equals("EN_ADJ_NN"))
			tag = ACLCompoundTag.EN_ADJ_NN;
		else if (tagInString.equals("EN_V_OBJ"))
			tag = ACLCompoundTag.EN_V_OBJ;
		else if (tagInString.equals("EN_V_SUBJ"))
			tag = ACLCompoundTag.EN_V_SUBJ;
        else if (tagInString.equals("RE_NN_NN"))
            tag = ACLCompoundTag.RE_NN_NN;

		return tag;
	}

    private ACLWordTag getFirstWordTag() {
        switch (tag) {
            case EN_ADJ_NN: return ACLWordTag.ADJ;
            case EN_V_OBJ: return ACLWordTag.V;
            case EN_V_SUBJ: return ACLWordTag.SUBJ;
            case RE_NN_NN: return ACLWordTag.NN;
            default: {
                System.out.println("Unknown tag!");
                return null;
            }
        }
    }

    private ACLWordTag getSecondWordTag() {
        switch (tag) {
            case EN_ADJ_NN: return ACLWordTag.NN;
            case EN_V_OBJ: return ACLWordTag.OBJ;
            case EN_V_SUBJ: return ACLWordTag.V;
            case RE_NN_NN: return ACLWordTag.NN;
            default: {
                System.out.println("Unknown tag!");
                return null;
            }
        }
    }
}
