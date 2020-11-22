# Create queries easily

It is a library that allow you to create queries fast giving you a `Specification` which can be easily integrate with `JpaSpecificationExecutor`.

## Usage

Through the `QuerySpecification` you can add restrictive filters (and), non-restrictive filters (or) and indicate an order. Once you finished adding the modifiers you will be able to get the complete specification.  
You can found all the filters available [here](https://github.com/cjgmj/dynamicQuery/tree/master/src/main/java/com/cjgmj/dynamicQuery/modifier/filter), the text filter allow you indicate the characters you wanna replace and if you wanna normalize the text gave or not. Also you can found the ordination classes [here](https://github.com/cjgmj/dynamicQuery/tree/master/src/main/java/com/cjgmj/dynamicQuery/modifier/order).  
If you need to create a new filter, you only have to create a filter and a predicate.

In tests package you have some examples about how to use the classes.

### Important note

You must not use `Collection.emptyList()` nor `Arrays.asList()` because it creates an unmodifiable list and Hibernate seems to expect a modifiable list so if you use this mentioned methods it will throws a UnsupportedOperationException.

## Dependency

[Repository link](https://mvnrepository.com/artifact/com.github.cjgmj/dynamic-query)

- Maven

```xml
    <dependency>
        <groupId>com.github.cjgmj</groupId>
        <artifactId>dynamic-query</artifactId>
        <version>1.0</version>
    </dependency>
```

- Gradle

```gradle
    compile('com.github.cjgmj:dynamic-query:1.0')
```
