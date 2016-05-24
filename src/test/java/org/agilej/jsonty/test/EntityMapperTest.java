package org.agilej.jsonty.test;

import java.util.*;

import org.agilej.jsonty.Environment;
import org.agilej.jsonty.JSONBuilder;
import org.agilej.jsonty.JSONModel;
import org.agilej.jsonty.mapping.DetailedUserEntity;
import org.agilej.jsonty.mapping.PostEntity;
import org.agilej.jsonty.mapping.SummaryUserEntity;
import org.agilej.jsonty.model.Post;
import org.agilej.jsonty.model.Profile;
import org.agilej.jsonty.model.User;
import org.agilej.jsonty.support.AbstractJSONModel;
import org.agilej.jsonty.support.Environments;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EntityMapperTest {

    @Test
    public void test_mapping_entity_value_1() {
        final User user = domainUser();
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isAdmin", true);
        final Environment env = Environments.envFromMap(map);
        JSONModel module = new AbstractJSONModel() {
            public void config() {
                expose(user).withNameAndMapping("user", SummaryUserEntity.class).plusEnv(env);
            }
        };

        String expected = "{\"user\":{\"name\":\"donny\",\"isAdmin\":true}}";
        assertEquals(expected, build(module));
    }

    @Test
    public void test_mapping_entity_value_2() {
        final User user = domainUser();

        JSONModel module = new AbstractJSONModel() {
            public void config() {
                expose(user).withNameAndMapping("user", DetailedUserEntity.class);
            }
        };

        String expected =
                "{\"user\":{\"name\":\"donny\",\"age\":30,\"profile\":{\"address\":\"Beijing China\"},\"posts\":[{\"title\":\"post 1\"},{\"title\":\"post 2\"},{\"title\":\"post 3\"}]}}";
        assertEquals(expected, build(module));
    }
    
    @Test
    public void test_mapping_entity_value_3() {
        final User user = domainUser();

        JSONModel module = new AbstractJSONModel() {
            public void config() {
                expose(user.posts).withMapping(PostEntity.class);
            }
        };

        String expected = "[{\"title\":\"post 1\"},{\"title\":\"post 2\"},{\"title\":\"post 3\"}]";
        assertEquals(expected, build(module));
    }
    
    @Test
    @Ignore
    public void test_mapping_with_map_value(){
        final User user = domainUser();
        
        final Map<String, Object> map = new TreeMap<>();
        map.put("user", user);
        map.put("num", 1);
        
        JSONModel module = new AbstractJSONModel() {
            public void config() {
                expose(map).withName("res");
            }
        };

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
        return new JSONBuilder().build(module);
    }

}