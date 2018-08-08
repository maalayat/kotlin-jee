# Kotlin JEE

This project is a very simple example of Java EE 7 application entirely programmed in Kotlin.

## Technology

- Java EE 7
    - JPA
    - CDI
    - JSF
    - [QueryDSL](http://www.querydsl.com/)
- Kotlin
- Gradle
- Primefaces

# Run

1. Clone this project and

```bash
# Linux
./gradlew build

# Windows
gradlew.bat build
```

2. Copy __ear.ear__ into deployments folder of your application server. I used __Wildfly__ for my tests.

3. Go to [http://localhost:8080/war/](http://localhost:8080/war/)
 
4. Enjoy

