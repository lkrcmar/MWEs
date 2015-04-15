package cz.zcu.luk.mwes.acl2011;

public class ComValScorerLoaderPMI extends ComValScorer {
    private static final GeneralScorerType generalScorerType = GeneralScorerType.PMI_LOADER;

	UKWACcompoundsPMIScores loaderOfPMIScores;
    String fileName;
    boolean ignoreTags;

	public ComValScorerLoaderPMI(String fileName) {
        this.fileName = fileName;
        this.ignoreTags = fileName.endsWith("XX.txt");
	}

	
	protected double getScore(CompoundSS compound) {
		double score = loaderOfPMIScores.getPMIOfCompound(compound.getSSpaceRep(ignoreTags));
        //System.out.println("AAAAAAAA" + compound.getSSpaceRep(ignoreTags) + "\t" + score) ;
		return (-1.0)*score;
	}

    @Override
    public GeneralScorerType getGeneralType() {
        return generalScorerType;
    }

    @Override
    public String getType() {
        return getGeneralType().toString()  + "_BASIC";
    }

    public String getName() {
        return getType() + "_NONAME";
    }

    @Override
    public String getPars() {
        return "NONE";
    }

    @Override
    public String getSpacePars() {
        return "NONE";
    }

    @Override
    protected void prepareDataForScoring(CompoundSet compoundSet) {
        loaderOfPMIScores = new UKWACcompoundsPMIScores();
        loaderOfPMIScores.loadCompoundsPMIScoresRestricted(fileName);
    }

    @Override
    protected void cleanDataForScoring() {
        loaderOfPMIScores.setTokenIndexes(null);
    }
}
