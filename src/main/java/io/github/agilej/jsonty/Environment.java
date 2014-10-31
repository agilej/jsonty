package io.github.agilej.jsonty;

public interface Environment {

    <T> T get(String key);
    
    boolean contains(String key);
    
}
