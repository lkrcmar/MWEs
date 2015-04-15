package cz.zcu.luk.mwes.acl2011;

public class Word {
	private String word;
    private SSWordTag tag;

    public SSWordTag getTag() {
        return tag;
    }
	public String getWord() {
		return word;
	}

	public Word(String word, ACLWordTag tagACL) {
		this.word = word;
		this.tag = setSSTag(tagACL);
	}
	
	public Word(SSWord wordSS) {
		this.word = wordSS.getWord();
		this.tag = wordSS.getTag();
	}
	
	private SSWordTag setSSTag(ACLWordTag tagACL) {
		switch (tagACL) {
			case ADJ: return SSWordTag.JJ;
			case NN: return SSWordTag.NN;
			case V: return SSWordTag.VV;
			case OBJ: return SSWordTag.NN;
			case SUBJ: return SSWordTag.NN;
			default: {
				System.out.println("Unknown tag!");
				return null;
			}
		}
	}


}
