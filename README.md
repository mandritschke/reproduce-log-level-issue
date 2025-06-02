# Reproducer for a Log Level configuration issue in Keycloak 26.1.x

## Issue

Configuring log levels for package names with underscores
https://github.com/keycloak/keycloak/issues/37772

## Reproducing the issue

1. Build the artifact
```
mvn package -Dkeycloak.version=26.1.0
```
2. Drop the .jar into Keycloak's `providers` folder and boot up Keycloak using default log level settings
```
docker run \
    --mount type=bind,source=./target/reproducer.jar,target=/opt/keycloak/providers/reproducer.jar \
    quay.io/keycloak/keycloak:26.1.0 start-dev
```
Observe the following lines
```
2025-03-03 08:30:27,951 INFO  [reproducer.ok.LogLevelReproducer] (main) Expected info message
2025-03-03 08:30:27,951 ERROR [reproducer.ok.LogLevelReproducer] (main) Expected error message
2025-03-03 08:30:27,951 INFO  [reproducer.not_ok.LogLevelReproducer] (main) Expected info message
2025-03-03 08:30:27,951 ERROR [reproducer.not_ok.LogLevelReproducer] (main) Expected error message
```
3. Drop the .jar into Keycloak's `providers` folder and boot up Keycloak with customized log level settings
```
docker run \
    --mount type=bind,source=./target/reproducer.jar,target=/opt/keycloak/providers/reproducer.jar \
    -e KC_LOG_LEVEL=error,reproducer.ok:debug,reproducer.not_ok:debug \
    quay.io/keycloak/keycloak:26.1.0 start-dev
```
Observe the following lines
```
2025-03-03 08:35:53,454 DEBUG [reproducer.ok.LogLevelReproducer] (main) Expected debug message
2025-03-03 08:35:53,454 INFO  [reproducer.ok.LogLevelReproducer] (main) Expected info message
2025-03-03 08:35:53,454 ERROR [reproducer.ok.LogLevelReproducer] (main) Expected error message
2025-03-03 08:35:53,454 ERROR [reproducer.not_ok.LogLevelReproducer] (main) Expected error message
```

Result: DEBUG and INFO lines for `reproducer.not_ok.LogLevelReproducer` are missing.
