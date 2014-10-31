package io.github.agilej.jsonty.mapping;

import io.github.agilej.jsonty.Environment;
import io.github.agilej.jsonty.FieldExposer;
import io.github.agilej.jsonty.SrapeEntity;
import io.github.agilej.jsonty.model.Post;

public class PostEntity implements SrapeEntity<Post>{

    @Override
    public void config(Post post, FieldExposer exposer, Environment env) {
        exposer.expose(post.title).withName("title");
    }

}
