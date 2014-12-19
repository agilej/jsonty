Jsonty
======


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


http://agilej.github.io/jsonty
