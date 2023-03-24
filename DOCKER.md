https://hub.docker.com/_/postgres

```bash
$ docker run \
    --name geocoder-postgres \
    -e POSTGRES_PASSWORD=geocoder \
    -d postgres:15

$ docker exec -it geocoder-postgres /bin/bash
```

```bash
$ ./gradlew bootjar
```
