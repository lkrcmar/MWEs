package cz.zcu.luk.mwes.acl2011;

public class GeneralPars {
	private ECompoundsOrdering comOrdering;
	private ESemanticSpaceName ssUsed;

	public GeneralPars(ECompoundsOrdering comOrdering, ESemanticSpaceName ssUsed) {
		this.comOrdering = comOrdering;
		this.ssUsed = ssUsed;
	}

	public ECompoundsOrdering getComOrdering() {
		return comOrdering;
	}

	public ESemanticSpaceName getSsUsed() {
		return ssUsed;
	}
}
