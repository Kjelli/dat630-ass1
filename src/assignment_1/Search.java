package assignment_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Search {
	// The list containing the original adults to search in.
	private List<Adult> originalList;
	// The list storing all results so far
	private List<Adult> results = new ArrayList<Adult>();
	// The list storing all filters used in the search
	private List<Filter> filters = new ArrayList<Filter>();

	// Set the list of which to search in
	public Search from(List<Adult> list) {
		originalList = new ArrayList<Adult>();
		originalList.addAll(list);
		return this;
	}

	public Search runThrough(DecisionTree tree, boolean over50k) {
		if (originalList == null) {
			throw new NullPointerException("Search has no list!");
		}

		ArrayList<Adult> matches = new ArrayList<>();

		for (Adult adult : originalList) {
			if (tree.predict(adult) ^ !over50k) {
				matches.add(adult);
			}
		}

		results = matches;
		return this;
	}

	// Initiate a new filter. Note: Returns a filter object to be used in a
	// fluent API manner, and returns this search object when the filter is
	// built
	public Filter filter() {
		Filter f = new Filter(this);
		filters.add(f);
		return f;
	}

	// Find the highest concentrated attribute-values from each attribute, from
	// each search result.
	public Map<Object, Double> highCorrelations() {
		Map<Object, Double> correlations = new LinkedHashMap<Object, Double>();
		for (String att : Adult.ATT) {
			Map<Object, Double> temp = valuesFor(att, true);
			if (temp.keySet().isEmpty()) {
				continue;
			}
			Object key = (temp.keySet().iterator().next());
			double value = (double) temp.get(key);
			double ratio = (value * 100.0f / originalList.size()), relratio = (value * 100.0f / results
					.size());
			correlations.put(
					att
							+ " = "
							+ key
							+ ": "
							+ (int) value
							+ " "
							+ String.format(
									"(%.2f%% of total) [%.2f%% of hits]",
									ratio, relratio), value);
		}
		correlations = MapSort.sort(correlations);
		return correlations;
	}

	// Generate a map containing all values of an attribute and the respective
	// frequency
	public Map<Object, Double> valuesFor(String attributeName, boolean sort) {
		Map<Object, Double> map = new LinkedHashMap<Object, Double>();

		for (Adult a : results) {
			Object key = a.get(attributeName);

			map.put(key, map.get(key) == null ? 1 : map.get(key) + 1);
		}

		// Sort
		if (sort)
			map = MapSort.sort(map);

		return map;
	}

	// Retrieve the filters
	public List<Filter> getFilters() {
		return filters;
	}

	// Finalizing the search, you apply all the filters to the search algorithm,
	// and the results list will contain the results
	public Search search() {
		if (originalList == null) {
			throw new NullPointerException("Search has no list!");
		}

		ArrayList<Adult> matches = new ArrayList<>();
		if(results == null || results.isEmpty()){
			results = originalList;
		}
		adults: for (Adult adult : results) {
			boolean match = true;
			for (Filter f : filters) {
				if (!f.match(adult).result()) {
					match = false;
					continue adults;
				}
			}
			if (match) {
				matches.add(adult);
			}
		}

		results = matches;

		return this;
	}

	// Retrieve the list
	public List<Adult> toList() {
		return results;
	}
}
