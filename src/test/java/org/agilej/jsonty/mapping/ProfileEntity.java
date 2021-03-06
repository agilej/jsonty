package org.agilej.jsonty.mapping;

import org.agilej.jsonty.EntityMapper;
import org.agilej.jsonty.Environment;
import org.agilej.jsonty.FieldExposer;
import org.agilej.jsonty.model.Profile;

public class ProfileEntity implements EntityMapper<Profile> {

    @Override
    public void config(Profile profile, FieldExposer exposer, Environment env) {
        exposer.expose(profile.address).withName("address");
    }

}
