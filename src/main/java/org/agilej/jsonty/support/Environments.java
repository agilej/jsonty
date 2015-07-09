package org.agilej.jsonty.support;

import java.util.HashMap;
import java.util.Map;

import org.agilej.jsonty.Environment;

public class Environments {
    
    public static Environment envFromMap(Map<String, Object> hashMap) {
        Environment env = new MapBasedEnvironment(hashMap);
        return env;
    }

    public static Environment envWith(String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, value);
        return envFromMap(map);
    }
    
    public static Environment envWith(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key1, value1);
        map.put(key2, value2);
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

        @SuppressWarnings("unchecked")
        @Override
        public Object get(String key) {
            return this.map.get(key);
        }
    }

}
