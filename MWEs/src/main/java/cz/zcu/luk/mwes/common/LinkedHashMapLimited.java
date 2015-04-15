package cz.zcu.luk.mwes.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 27.11.12
 * Time: 13:10
 * To change this template use File | Settings | File Templates.
 */
public class LinkedHashMapLimited<K, V> extends LinkedHashMap<K, V> {
    private static final int MAX_ENTRIES = 3;

    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > MAX_ENTRIES;
    }
}
