package org.agilej.jsonty.mapping;

import org.agilej.jsonty.EntityMapper;
import org.agilej.jsonty.Environment;
import org.agilej.jsonty.FieldExposer;
import org.agilej.jsonty.model.User;

public class DetailedUserEntity implements EntityMapper<User> {

    @Override
    public void config(User user, FieldExposer exposer, Environment env) {
        exposer.expose(user.name).withName("name");
        exposer.expose(user.age).withName("age");
        exposer.expose(user.profile).withNameAndMapping("profile", ProfileEntity.class);
        exposer.expose(user.posts).withNameAndMapping("posts", PostEntity.class);
    }

}
