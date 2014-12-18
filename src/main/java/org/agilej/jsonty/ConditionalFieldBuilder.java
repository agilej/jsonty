package org.agilej.jsonty;

public interface ConditionalFieldBuilder {

    public void unless(boolean condition);
    
    public void when(boolean condition);
    
    public ScopedFieldBuilder plusEnv(Environment env);
    
}
