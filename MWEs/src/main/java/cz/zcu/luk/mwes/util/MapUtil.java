package cz.zcu.luk.mwes.util;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 29.8.12
 * Time: 10:39
 * from http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
 */
public class MapUtil {
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> entriesInList = extractEntriesSortedByValue(map);

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : entriesInList) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
    public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> extractEntriesSortedByValue(Map<K, V> map) {
    	List<Map.Entry<K, V>> entriesInList = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(entriesInList, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
        return entriesInList;
    }
}
