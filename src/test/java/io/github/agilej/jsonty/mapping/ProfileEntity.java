package io.github.agilej.jsonty.mapping;

import io.github.agilej.jsonty.Environment;
import io.github.agilej.jsonty.FieldExposer;
import io.github.agilej.jsonty.SrapeEntity;
import io.github.agilej.jsonty.model.Profile;

public class ProfileEntity implements SrapeEntity<Profile>{

    @Override
    public void config(Profile profile, FieldExposer exposer, Environment env) {
        exposer.expose(profile.address).withName("address");
    }

}
