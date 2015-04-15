package cz.zcu.luk.mwes.acl2011;

import java.io.Serializable;

public class CompoundSS implements Serializable {
    private final String compound;
	private final SSWord wordLeftSS;
	private final SSWord wordRightSS;

	public SSWord getWordLeftSS() {
		return wordLeftSS;
	}

	public SSWord getWordRightSS() {
		return wordRightSS;
	}
    public String getCompound() {
        return compound;
    }

    public CompoundSS(String compoundWithTags) {
        String[] split = compoundWithTags.split(" ");
        this.wordLeftSS = new SSWord(split[0]);
        this.wordRightSS = new SSWord(split[1]);
        this.compound = wordLeftSS.getWord() + " " + wordRightSS.getWord();
    }

    public CompoundSS(SSWord wordLeftSS, SSWord wordRightSS) {
        this.wordLeftSS = wordLeftSS;
        this.wordRightSS = wordRightSS;
        this.compound = wordLeftSS.getWord() + " " + wordRightSS.getWord();
    }

    public boolean leftWordIsHead() {
        if (wordLeftSS.getTag().equals(SSWordTag.XX) && wordRightSS.getTag().equals(SSWordTag.XX)) return false; // right word is head on default..
        if (wordLeftSS.getTag().equals(SSWordTag.JJ) && wordRightSS.getTag().equals(SSWordTag.NN)) return false;
        if (wordLeftSS.getTag().equals(SSWordTag.NN) && wordRightSS.getTag().equals(SSWordTag.VV)) return false;
        if (wordLeftSS.getTag().equals(SSWordTag.NN) && wordRightSS.getTag().equals(SSWordTag.NN)) return false;
        if (wordLeftSS.getTag().equals(SSWordTag.VV) && wordRightSS.getTag().equals(SSWordTag.NN)) return true;

        throw new IllegalStateException("Unexpected tag combination!");
    }

    public String getSSpaceRep(boolean ignoreTag) {
        return wordLeftSS.getSSpaceRep(ignoreTag) + " " + wordRightSS.getSSpaceRep(ignoreTag);
    }

    public String toString() {
        return getSSpaceRep(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompoundSS that = (CompoundSS) o;

        if (compound != null ? !compound.equals(that.compound) : that.compound != null) return false;
        if (wordLeftSS != null ? !wordLeftSS.equals(that.wordLeftSS) : that.wordLeftSS != null) return false;
        if (wordRightSS != null ? !wordRightSS.equals(that.wordRightSS) : that.wordRightSS != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = compound != null ? compound.hashCode() : 0;
        result = 31 * result + (wordLeftSS != null ? wordLeftSS.hashCode() : 0);
        result = 31 * result + (wordRightSS != null ? wordRightSS.hashCode() : 0);
        return result;
    }
}
