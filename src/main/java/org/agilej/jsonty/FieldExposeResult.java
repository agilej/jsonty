package org.agilej.jsonty;


public interface FieldExposeResult {

    /**
     *
     * the name this field will expose
     *
     */
    String getName();

    /**
     *
     * the value this field will expose
     *
     */
    Object getValue();

    /**
     *
     *
     *
     */
    boolean hasName();

    /**
     * whether the value is a map
     */
    boolean isMapValue();

    /**
     *
     * whether the value can be iterated, means its a array or collection value, {@see isArrayValue()}
     *
     * @see #isArrayValue
     * @see #isCollectionValue
     *
     *
     */
    boolean isValueIterable();

    /**
     *
     * whether this field is using EntityModel mapping
     *
     */
    boolean hasEntityType();

    /**
     *
     * the EntityModel type this field use
     *
     */
    Class<? extends EntityModel> getEntityClass();

    /**
     * whether the value is a java array
     */
    boolean isArrayValue();

    /**
     * whether the value is instanceof  {@link java.util.Collection }
     */
    boolean isCollectionValue();


    Environment getEnviroment();

}
