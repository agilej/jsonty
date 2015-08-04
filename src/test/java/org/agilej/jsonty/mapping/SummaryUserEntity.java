package org.agilej.jsonty.mapping;

import org.agilej.jsonty.EntityMapper;
import org.agilej.jsonty.Environment;
import org.agilej.jsonty.FieldExposer;
import org.agilej.jsonty.model.User;

public class SummaryUserEntity implements EntityMapper<User> {

    @Override
    public void config(User user, FieldExposer exposer, Environment env) {
        exposer.expose(user.name).withName("name");
        Boolean isAdmin = env.get("isAdmin");
        exposer.expose(isAdmin).withName("isAdmin").when(isAdmin);
    }

}
