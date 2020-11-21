# dynamicQuery

## Important note
You must not use `Collection.emptyList()` nor `Arrays.asList()` because it creates an unmodifiable list and Hibernate seems to expect a modifiable list so if you use this mentioned methods it will throws a UnsupportedOperationException.