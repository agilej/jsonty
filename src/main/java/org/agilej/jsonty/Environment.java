package org.agilej.jsonty;

/**
 * Environment variables container.
 */
public interface Environment {

    <T> T get(String key);
    
    boolean contains(String key);

    Environment set(String key, Object value);

}
