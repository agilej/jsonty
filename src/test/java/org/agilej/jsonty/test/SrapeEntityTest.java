package org.agilej.jsonty.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agilej.jsonty.Environment;
import org.agilej.jsonty.JSONBuilder;
import org.agilej.jsonty.JSONModel;
import org.agilej.jsonty.mapping.DetailedUserEntity;
import org.agilej.jsonty.mapping.PostEntity;
import org.agilej.jsonty.mapping.SummaryUserEntity;
import org.agilej.jsonty.model.Post;
import org.agilej.jsonty.model.Profile;
import org.agilej.jsonty.model.User;
import org.agilej.jsonty.support.AbstractJSONMoel;
import org.agilej.jsonty.support.Environments;
import org.junit.Ignore;
import org.junit.Test;


public class SrapeEntityTest {

    @Test
    public void test_srape_entity_value_1() {
        final User user = domainUser();
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isAdmin", true);
        final Environment env = Environments.envFromMap(map);
        JSONModel module = new AbstractJSONMoel() {
            public void config() {
                expose(user).withNameAndType("user", SummaryUserEntity.class).plusEnv(env);
            }
        };

        System.out.println(build(module));
    }

    @Test
    public void test_srape_entity_value_2() {
        final User user = domainUser();

        JSONModel module = new AbstractJSONMoel() {
            public void config() {
                expose(user).withNameAndType("user", DetailedUserEntity.class);
            }
        };

        System.out.println(build(module));
    }
    
    @Test
    public void test_srape_entity_value_3() {
        final User user = domainUser();

        JSONModel module = new AbstractJSONMoel() {
            public void config() {
                expose(user.posts).withType(PostEntity.class);
            }
        };

        System.out.println(build(module));
    }
    
    @Test
    @Ignore
    public void test_srape_with_map_value(){
        final User user = domainUser();
        
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", user);
        map.put("num", 1);
        
        JSONModel module = new AbstractJSONMoel() {
            public void config() {
                expose(map).withNameAndType("res", PostEntity.class);
            }
        };

        System.out.println(build(module));
    }
    

    private User domainUser() {
        User user = new User();
        user.name = "donny";
        user.age = 30;
        
        Profile p = new Profile();
        p.address = "Beijing China";
        user.profile = p;
        
        List<Post> posts = new ArrayList<Post>();
        posts.add(new Post("post 1"));
        posts.add(new Post("post 2"));
        posts.add(new Post("post 3"));
        user.posts = posts;
        return user;
    }

    private String build(JSONModel module) {
        return new JSONBuilder(module).build();
    }

}