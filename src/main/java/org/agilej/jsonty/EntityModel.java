package org.agilej.jsonty;

public interface EntityModel<E> {

    void config(E object, FieldExposer exposer, Environment env);
    
}
