package cz.zcu.luk.mwes.acl2011;

import java.io.Serializable;

public class SSWord implements Serializable {
	private String word;
    private SSWordTag tag;
	
	public String getWord() {
		return word;
	}

	public SSWordTag getTag() {
		return tag;
	}

    public SSWord(String word, SSWordTag tag) {
        this.word = word;
        this.tag = tag;
    }
	
	public SSWord(String wordWithTag) {
		this.word = wordWithTag.substring(0, wordWithTag.length() - 3);
		String minedTag = wordWithTag.substring(wordWithTag.length() - 2);
		if (minedTag.equals("JJ")) tag = SSWordTag.JJ;
		else if (minedTag.equals("NN")) tag = SSWordTag.NN;
		else if (minedTag.equals("VV")) tag = SSWordTag.VV;
        else if (minedTag.equals("XX")) tag = SSWordTag.XX;
		else {
			throw new IllegalArgumentException("Error: given word in ss format has unspecified tag!");
		}
	}

    public String getSSpaceRep() {
        return word + "_" + tag.toString();
    }

    public String getSSpaceRep(boolean ignoreTags) {
        return (ignoreTags) ? (word + "_" + Constants.NOTAG) : (word + "_" + tag.toString());
    }

    public String toString() {
        return getSSpaceRep(false); // + " or " + getSSpaceRep(true)
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SSWord ssWord = (SSWord) o;

        if (tag != ssWord.tag) return false;
        if (word != null ? !word.equals(ssWord.word) : ssWord.word != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}
