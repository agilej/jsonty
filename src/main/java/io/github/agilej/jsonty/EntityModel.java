package io.github.agilej.jsonty;

public interface EntityModel<E> {

    public void config(E object, FieldExposer exposer, Environment env);
    
}