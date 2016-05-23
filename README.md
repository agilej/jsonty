[![Build Status](https://drone.io/github.com/agilej/jsonty/status.png)](https://drone.io/github.com/agilej/jsonty/latest)

Make fun with JSON serializer.

## How to use

For Maven project:

```xml
  <dependency>
      <groupId>org.agilej</groupId>
      <artifactId>jsonty</artifactId>
      <version>0.1</version>
  </dependency>
```

For Gradle Project

```groovy
  
  compile group: 'org.agilej', name: 'jsonty', version: '0.1'

```

Then write your code ^_^:

```java

    final Account account = ...;
    final int status = 20;
    
    JSONModel model = new JSONModel() {
        public void config(FieldExposer exposer) {
            exposer.expose(status).withName("status");
            exposer.expose(account).withNameAndMapping("account", AccountEntity.class);
        }
    };

    //or use lambda in java8
    JSONModel model = e -> {
        e.expose(status).withName("status");
        e.expose(account).withNameAndMapping("account", AccountEntity.class);
    }

    //to json
    String json = new JSONBuilder(model).build();

    //or write to stream, not implemented yet
    new JSONBuilder(model).build(writer);

```

## Entity Mapping

Where `AccountEntity` implement `EntityMapper` interface and defined fields will be exposed to json result.

```java

    public class AccountEntity implements EntityMapper<Account>{
        
        public void config(Account account, FieldExposer exposer, Environment env) {
            exposer.expose(account.getLogin()).withName("loginName");
            exposer.expose(account.getAvatar()).withName("avatar");

            //you can use nested EntityMapper too
            exposer.expose(account.getProfile()).withNameAndMapping("profile", ProfileEntity.class);
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
    exposer.expose(user.profile).withNameAndMapping("profile", ProfileEntity.class).plusEnv(env);

    //then in ProfileEntity

    public class ProfileEntity implements EntityMapper<Profile>{

        public void config(Profile profile, FieldExposer exposer, Environment env) {
            exposer.expose(xxx).withName("xx");
            if(env.contains("isAdmin") && env.get("isAdmin")){
                exposer.expose(xxx).withName("onlyAviableForAdmin"); 
            }
        }
    }
```


## TODO

These features are considered to be implement in version 0.2.

* Buid json to `Writer` directly
* Beauty json print support

