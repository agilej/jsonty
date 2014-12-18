package io.github.agilej.jsonty.mapping;

import io.github.agilej.jsonty.Environment;
import io.github.agilej.jsonty.FieldExposer;
import io.github.agilej.jsonty.EntityModel;
import io.github.agilej.jsonty.model.User;

public class DetailedUserEntity implements EntityModel<User>{

    @Override
    public void config(User user, FieldExposer exposer, Environment env) {
        exposer.expose(user.name).withName("name");
        exposer.expose(user.age).withName("age");
        exposer.expose(user.profile).withNameAndType("profile", ProfileEntity.class);
        exposer.expose(user.posts).withNameAndType("posts", PostEntity.class);
    }

}
