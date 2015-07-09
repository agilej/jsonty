package org.agilej.jsonty;

@SuppressWarnings("rawtypes")

public interface CustomizedFieldBuilder extends ConditionalFieldBuilder {
    
    public ConditionalFieldBuilder withNameAndType(String name, Class<? extends EntityModel> entityClass);

    public ConditionalFieldBuilder withName(String string);
    
    public ConditionalFieldBuilder withType(Class<? extends EntityModel> entityClass);
    
}
