## How to use


Make fun with JSON serializer.


```java

    final Account account = ...;
    final int status = 20;
    
    JSONModel model = new JSONModel() {
        public void config(FieldExposer exposer) {
            exposer.expose(status).withName("status");
            exposer.expose(account).withNameAndType("account", AccountEntity.class);
        }
    };

    //or use lambda in java8
    JSONModel model = e -> {
        e.expose(status).withName("status");
        e.expose(account).withNameAndType("account", AccountEntity.class);
    }

    //to json
    String json = new JSONBuilder(model).build();

    //or write to stream, not implemented yet
    new JSONBuilder(model).build(writer);

```

Where `AccountEntity` implement `EntityModel` interface and defined fields will be exposed to json result.

```java

    public class AccountEntity implements EntityModel<Account>{
        
        public void config(Account account, FieldExposer exposer, Environment env) {
            exposer.expose(account.getLogin()).withName("loginName");
            exposer.expose(account.getAvatar()).withName("avatar");

            //you can use nested EntityModel too
            exposer.expose(account.getProfile()).withNameAndType("profile", ProfileEntity.class);
        }

    }
```

## Extra

You can set a condition for field expose use `when` or `unless`

```java

    exposer.expose("xxxx").withName("xxxx").when(age > 18);
    //or use unless
    exposer.expose("xxxx").withName("xxxx").unless(age <= 18);
```

You can alse pass enviroment use `plusEnv`and do some extra calculation while exposing field

```java

    Enviroment env = Enviroments.envWith("isAdmin", user.isAdmin()) 
    exposer.expose(user.profile).withNameAndType("profile", ProfileEntity.class).plusEnv(env);

    //then in ProfileEntity

    public class ProfileEntity implements EntityModel<Profile>{

        public void config(Profile profile, FieldExposer exposer, Environment env) {
            exposer.expose(xxx).withName("xx");
            if(env.contains("isAdmin") && env.get("isAdmin")){
                exposer.expose(xxx).withName("onlyAviableForAdmin"); 
            }
        }
    }
```

http://agilej.github.io/jsonty
