package io.github.agilej.jsonty;

public interface ScopedFieldBuilder extends ConditionalFieldBuilder {
    
    public ConditionalFieldBuilder withNameAndType(String name, Class<? extends SrapeEntity> entityClass);

    public ConditionalFieldBuilder withName(String string);
    
    public ConditionalFieldBuilder withType(Class<? extends SrapeEntity> entityClass);
    
}
