package org.agilej.jsonty;

/**
 * The essential interface for exposing json fields.
 *
 * <p><b>Expose some value</b>
 * <pre><code>
 *     expose("some value");
 * </code></pre>
 *
 * exposed value can be any type, jsonty will use correct build strategy to generate json string for it.
 *
 * <pre><code>
 *     expose(1);
 *     expose(Map);
 *     expose(List);
 *     expose(Object);
 * </code></pre>
 *
 * <p><b>Expose some value and specify it's name in json output</b>
 * <pre><code>
 *     expose("some value").withName("value");
 * </code></pre>
 *
 * <p><b>Expose some value  and use the given mapping {@link EntityModel} to control it's json fileds</b>
 * <pre><code>
 *     expose(userObject).withType(UserEntity.class);
 * </code></pre>
 *
 * when use entity mapping to build json for one object value, the value object must be the type which your {@link EntityModel}
 * support it. The value can be one instance for the type or a collection of instance of the type. If the value is a collection
 * which is java {@link java.lang.reflect.Array} or {@link java.util.Collection}, jsonty will iterate the collection
 * and generate json for each value with the given mapping type {@link EntityModel}
 *
 * <pre><code>
 *     List<User> users = ....;
 *     expose(users).withType(UserEntity.class);
 * </code></pre>
 *
 * <p><b>Pass environment variables when do entity mapping json build</b>
 * <pre><code>
 *     Environment env = Environments.envWith("isAdmin", currentLoginUserIsAdmin);
 *     expose(users).withType(UserEntity.class).plusEnv(env);
 * </code></pre>*
 *
 * <p><b>Expose field only when some condition matched</b>
 * use <code>when</code> or <code>unless</code> to controller the condition.
 * <pre><code>
 *     expose(someLinke).withName("editLink").when(currentUser.isAdmin());
 *     expose(someLinke).withName("editLink").unless(someCondition);
 * </code></pre>

 */
public interface FieldExposer {

    CustomizedFieldBuilder expose(Object value);
    
}
