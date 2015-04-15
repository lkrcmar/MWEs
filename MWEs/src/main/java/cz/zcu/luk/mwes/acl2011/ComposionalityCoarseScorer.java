package cz.zcu.luk.mwes.acl2011;


public abstract class ComposionalityCoarseScorer {
	
	public abstract void createCoarseScores(CompoundSetCoarseData coarseComSet, CompoundSetNumData numComSet);
	
	public abstract String getName();
}
