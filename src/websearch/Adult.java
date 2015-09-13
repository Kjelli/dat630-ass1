package websearch;

import java.util.HashMap;

public class Adult {

	public static String[] ATT = new String[] { "age", "workclass", "fnlwgt",
			"education", "education_num", "marital_status", "occupation",
			"relationship", "race", "sex", "capital_gain", "capital_loss",
			"hours_per_week", "native_country", "over50k" };

	private HashMap<String, Object> attributes;

	public Adult(String line) {
		attributes = new HashMap<String, Object>();
		set(ATT[0], -1);
		set(ATT[1], "undefined");
		set(ATT[2], -1);
		set(ATT[3], "undefined");
		set(ATT[4], -1);
		set(ATT[5], "undefined");
		set(ATT[6], "undefined");
		set(ATT[7], "undefined");
		set(ATT[8], "undefined");
		set(ATT[9], "undefined");
		set(ATT[10], -1);
		set(ATT[11], -1);
		set(ATT[12], -1);
		set(ATT[13], "undefined");
		set(ATT[14], true);

		String[] elements = line.split(", ");

		for (int i = 0; i < elements.length; i++) {
			if (elements[i].equals("?")) {
				continue;
			}

			switch (i) {
			case 0:
				set("age", Integer.parseInt(elements[i]));
				break;
			case 1:
				set("workclass", elements[i]);
				break;
			case 2:
				set("fnlwgt", Integer.parseInt(elements[i]));
				break;
			case 3:
				set("education", elements[i]);
				break;
			case 4:
				set("education_num", Integer.parseInt(elements[i]));
				break;
			case 5:
				set("marital_status", elements[i]);
				break;
			case 6:
				set("occupation", elements[i]);
				break;
			case 7:
				set("relationship", elements[i]);
				break;
			case 8:
				set("race", elements[i]);
				break;
			case 9:
				set("sex", elements[i]);
				break;
			case 10:
				set("capital_gain", Integer.parseInt(elements[i]));
				break;
			case 11:
				set("capital_loss", Integer.parseInt(elements[i]));
				break;
			case 12:
				set("hours_per_week", Integer.parseInt(elements[i]));
				break;
			case 13:
				set("native_country", elements[i]);
				break;
			case 14:
				set("over50k", elements[i].equals(">50K") ? true : false);
			default:
				break;
			}
		}
	}

	public Object get(String attributeName) {
		return attributes.get(attributeName);
	}

	public boolean match(Filter filter) {

		return true;
	}

	public void set(String attributeName, Object value) {
		attributes.put(attributeName, value);
	}
}
