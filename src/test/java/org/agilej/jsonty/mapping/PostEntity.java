package org.agilej.jsonty.mapping;

import org.agilej.jsonty.EntityModel;
import org.agilej.jsonty.Environment;
import org.agilej.jsonty.FieldExposer;
import org.agilej.jsonty.model.Post;

public class PostEntity implements EntityModel<Post>{

    @Override
    public void config(Post post, FieldExposer exposer, Environment env) {
        exposer.expose(post.title).withName("title");
    }

}
