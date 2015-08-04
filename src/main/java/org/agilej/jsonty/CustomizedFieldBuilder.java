package org.agilej.jsonty;

@SuppressWarnings("rawtypes")

public interface CustomizedFieldBuilder extends ConditionalFieldBuilder {
    
    public ConditionalFieldBuilder withNameAndMapping(String name, Class<? extends EntityMapper> entityMapperClass);

    public ConditionalFieldBuilder withName(String string);
    
    public ConditionalFieldBuilder withMapping(Class<? extends EntityMapper> entityMapperClass);
    
}
