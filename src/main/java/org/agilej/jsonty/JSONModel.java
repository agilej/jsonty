package org.agilej.jsonty;

/**
 * represent one json model you want to build.
 */
public interface JSONModel {

    /**
     * config the fields you want expose to json model.
     *
     * @param exposer fields exposer
     */
    void config(FieldExposer exposer);
    
}
