package io.github.agilej.jsonty;

public interface SrapeEntity<E> {

    public void config(E object, FieldExposer exposer, Environment env);
    
}
