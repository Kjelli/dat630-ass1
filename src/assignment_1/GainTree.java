package assignment_1;

import static assignment_1.Main.*;
public class GainTree extends DecisionTree {

	public GainTree(boolean training, String filename) {
		super(training, filename);
	}

	@Override
	protected boolean predict(Adult a) {
		if((int) a.get(AGE) < 38){
			return true;
		}
		return false;
	}
}
