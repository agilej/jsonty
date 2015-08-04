package org.agilej.jsonty.model;

import org.agilej.jsonty.FieldExposer;
import org.agilej.jsonty.JSONBuilder;
import org.agilej.jsonty.JSONModel;
import org.agilej.jsonty.mapping.Account;
import org.agilej.jsonty.mapping.AccountEntity;

import java.util.List;

public class Controller{
 
    public JSONBuilder jsonResult(){
        final List<User> users = null;
        final Account account = null;
        final int age = 20;
        
        return new JSONBuilder(new JSONModel() {
            @Override
            public void config(FieldExposer exposer) {
                exposer.expose(users).withName("users").when(age > 76);
                exposer.expose(account).withNameAndMapping("account", AccountEntity.class);
            }
        });
    }
 
}