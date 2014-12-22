package org.agilej.jsonty.mapping;

import org.agilej.jsonty.EntityModel;
import org.agilej.jsonty.Environment;
import org.agilej.jsonty.FieldExposer;

public class AccountEntity implements EntityModel<Account> {
    
    @Override
    public void config(Account account, FieldExposer exposer, Environment env) {
        exposer.expose(account.login).withName("username");
    }
    
}