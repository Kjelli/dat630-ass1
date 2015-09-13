package websearch;
public class Filter {
	private static final int EQUAL_TO = 1, GREATER_THAN = 2, LESS_THAN = 3,
			GREATER_OR_EQUAL_TO = 4, LESS_OR_EQUAL_TO = 5;
	
	// Parent search - useful in making the fluent api
	private Search search;

	//Name of the Adult's attribute (key) to check against value
	private String attributeName;
	
	// Determines what operator the filter will use in matching
	private int operator;
	
	// The attribute will be compared against this value
	private Object value;
	
	// The result of the match
	private Boolean result;
	
	// By using the .not() function, it will negate the filter
	private Boolean negate = false;

	// Used by the search-class when making new search-filters
	public Filter(Search search) {
		this.search = search;
	}

	// Set the name(key) of the attribute
	public Filter attribute(String attributeName) {
		this.attributeName = attributeName;
		return this;
	}
	
	// Negate the filter (equalTo(val) -> not().equalTo(val))
	public Filter not() {
		negate ^= true;
		return this;
	}

	// Equality check
	public Search equalTo(Object value) {
		operator = EQUAL_TO;
		this.value = value;
		return search;
	}

	// Greater than check
	public Search greaterThan(Object value) {
		operator = GREATER_THAN;
		this.value = value;
		return search;
	}

	// Less than check
	public Search lessThan(Object value) {
		operator = LESS_THAN;
		this.value = value;
		return search;
	}

	// Less or equal check
	public Search lessOrEqualTo(Object value) {
		operator = LESS_OR_EQUAL_TO;
		this.value = value;
		return search;
	}

	// Greater or equal check
	public Search greaterOrEqualTo(Object value) {
		operator = GREATER_OR_EQUAL_TO;
		this.value = value;
		return search;
	}
	
	// Match method. This is called from the search class when all filters are sequentially applied to each adult object.
	public Filter match(Adult a) {
		if (attributeName == null) {
			throw new NullPointerException("No attribute to check!");
		}
		
		if(a.get(attributeName) == null){
			result = value == null;
			return this;
		}
		
		switch (operator) {
		case EQUAL_TO:
			result = (a.get(attributeName) == value || a.get(attributeName)
					.equals(value));
			break;
		case LESS_THAN:
			result = (Integer) a.get(attributeName) < (Integer) value;
			break;
		case LESS_OR_EQUAL_TO:
			result = (Integer) a.get(attributeName) <= (Integer) value;
			break;
		case GREATER_THAN:
			result = (Integer) a.get(attributeName) > (Integer) value;
			break;
		case GREATER_OR_EQUAL_TO:
			result = (Integer) a.get(attributeName) >= (Integer) value;
			break;
		default:
			throw new IllegalArgumentException();
		}
		return this;
	}

	// Return the result, whether a match is made.
	public Boolean result() {
		return result ^ negate;
	}
	
	// Useful when printing filters.
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");

		String operatorText;
		switch (operator) {
		case EQUAL_TO:
			operatorText = "";
			break;
		case LESS_THAN:
			operatorText = "less than ";
			break;
		case LESS_OR_EQUAL_TO:
			operatorText = "less or equal to ";
			break;
		case GREATER_THAN:
			operatorText = "greater than ";
			break;
		case GREATER_OR_EQUAL_TO:
			operatorText = "greater or equal to ";
			break;
		default:
			operatorText = "OPERATOR!?";
		}

		sb.append(attributeName.toUpperCase() + " is "
				+ (negate ? "not " : "") + operatorText + value);

		return sb.toString();
	}
}
