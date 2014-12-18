package io.github.agilej.jsonty.mapping;

import io.github.agilej.jsonty.Environment;
import io.github.agilej.jsonty.FieldExposer;
import io.github.agilej.jsonty.EntityModel;
import io.github.agilej.jsonty.model.User;

public class SummaryUserEntity implements EntityModel<User>{

    @Override
    public void config(User user, FieldExposer exposer, Environment env) {
        exposer.expose(user.name).withName("name");
        Boolean isAdmin = env.get("isAdmin");
        exposer.expose(isAdmin).withName("isAdmin").when(isAdmin);
    }

}
