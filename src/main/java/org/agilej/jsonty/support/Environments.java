package org.agilej.jsonty.support;

import java.util.HashMap;
import java.util.Map;

import org.agilej.jsonty.Environment;

/**
 * Utility class for creating {@link Environment}
 */
public class Environments {

    /**
     * create {@link Environment} from given map
     * @param map
     * @return
     */
    public static Environment envFromMap(Map<String, Object> map) {
        Environment env = new MapBasedEnvironment(map);
        return env;
    }

    /**
     *
     * create {@link Environment} with given one key-pair
     * @param key
     * @param value
     * @return
     */
    public static Environment envWith(String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, value);
        return envFromMap(map);
    }

    /**
     *
     * create {@link Environment} with given two key-pairs
     * @return
     */
    public static Environment envWith(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key1, value1);
        map.put(key2, value2);
        return envFromMap(map);
    }

    /**
     *
     * create {@link Environment} with given three key-pairs
     * @return
     */
    public static Environment envWith(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return envFromMap(map);
    }

    /**
     *
     * create {@link Environment} with given four key-pairs
     * @return
     */
    public static Environment envWith(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return envFromMap(map);
    }

    /**
     *
     * create {@link Environment} with given five key-pairs
     * @return
     */
    public static Environment envWith(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return envFromMap(map);
    }


    static class MapBasedEnvironment implements Environment{
        private Map<String, Object> map;

        public MapBasedEnvironment(Map<String, Object> map){
            this.map = new HashMap<String, Object>(map);
        }

        @Override
        public boolean contains(String key) {
            return this.map.containsKey(key);
        }

        @Override
        public Environment set(String key, Object value) {
            this.map.put(key, value);
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object get(String key) {
            return this.map.get(key);
        }
    }

}
