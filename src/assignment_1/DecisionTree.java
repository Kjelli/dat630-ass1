package assignment_1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static assignment_1.Main.*;

public abstract class DecisionTree {
	static final int POSITIVE = 0, NEGATIVE = 1;
	static final int FALSE = 0, TRUE = 1;

	static final String TRUE_LABEL = ">50K", FALSE_LABEL = "<=50K";

	File output;
	boolean training;
	String filename;

	public DecisionTree(boolean training, String filename) {
		this.training = training;
		this.filename = filename;
	}

	// Run this with the data interpreted from the textfiles.
	public DecisionTreeResult estimate(ArrayList<Adult> data) {
		int correctCount = 0;
		int[][] confusion = new int[2][2];
		int lineCount = 1;
		boolean fileError = false;

		output = new File(filename);
		if (output.exists()) {
			output.delete();
		}

		FileWriter fw = null;
		try {
			fw = new FileWriter(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fileError = true;
		}

		if (!fileError) {
			try {
				fw.write("Id,Target\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}

		for (Adult a : data) {
			boolean estimate = predict(a);
			boolean actual = false;
			if (training) {
				actual = (boolean) a.get(OVER50K);
			}
			if (!fileError) {
				try {
					fw.write((lineCount++) + ","
							+ (estimate ? ">50K\n" : "<=50K\n"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
			if (training) {
				if (estimate) {
					if (actual) {
						correctCount++;
						confusion[TRUE][POSITIVE]++;
					} else {
						confusion[TRUE][NEGATIVE]++;
					}
				} else {
					if (actual) {
						confusion[FALSE][POSITIVE]++;
					} else {
						correctCount++;
						confusion[FALSE][NEGATIVE]++;
					}
				}
			}
		}
		if (!fileError) {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (training) {
			return new DecisionTreeResult(correctCount, data.size(), confusion);
		} else {
			return null;
		}
	}

	// This is the key method to assignment 1 and predicts wether or not an
	// adult makes over 50k or under
	protected abstract boolean predict(Adult a);

}
