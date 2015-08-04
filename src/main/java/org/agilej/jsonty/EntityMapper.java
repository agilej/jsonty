package org.agilej.jsonty;

/**
 * Mapping one entity to customized json fields.
 *
 * <pre>{@code
 *
 * public class UserMapper implements EntityMapper<User>{
 *     public void config(User user, FieldExposer exposer, Environment env){
 *         exposer.expose(user.getName()).withName("login");
 *         exposer.expose(user.getAge()).withName("age");
 *     }
 * }
 *
 * }</pre>
 *
 * @param <E> the entity type will be map from
 */
public interface EntityMapper<E> {

    void config(E object, FieldExposer exposer, Environment env);
    
}
