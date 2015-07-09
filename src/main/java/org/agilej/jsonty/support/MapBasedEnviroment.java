package org.agilej.jsonty.support;

import java.util.HashMap;
import java.util.Map;

import org.agilej.jsonty.Environment;

@Deprecated
public class MapBasedEnviroment implements Environment{

    private Map<String, Object> map;

    public MapBasedEnviroment(Map<String, Object> map){
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
