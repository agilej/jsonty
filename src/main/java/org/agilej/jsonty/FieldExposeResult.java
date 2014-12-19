package org.agilej.jsonty;


public interface FieldExposeResult {

    String getName();

    Object getValue();

    boolean hasName();

    boolean isValueIterable();

    boolean hasEntityType();

    Class<? extends EntityModel> getEntityClass();

    boolean isArrayValue();

    boolean isCollectionValue();

    Environment getEnviroment();

}
