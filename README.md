# kafka-consumer

Example Spring Boot application that **consumes** messages from an Apache Kafka topic.
Companion project: [kafka-producer](https://github.com/mateusvpassos/kafka-producer).

> Sample / learning project. Not production-grade.

## What it does

A `@KafkaListener` ([`KafkaConsumer`](src/main/java/br/com/passos/kafkaconsumer/service/consumer/KafkaConsumer.java))
subscribes to the configured topic and logs each record (timestamp, offset, partition,
key, value, headers).

```
Producer  --(JSON "Hello World!")-->  topic_test  -->  Consumer (logs it)
```

## Stack

| | |
|---|---|
| Language | Java 24 |
| Framework | Spring Boot 3.5.0 |
| Messaging | Spring for Apache Kafka |
| Build | Gradle (wrapper, 8.14) |
| Extras | Lombok, Jackson (JSR-310) |

## Configuration

`kafka.topic` (default `topic_test`, override with env `KAFKA_TOPIC`) and the broker
address are profile-based:

| Profile | Bootstrap servers | Use |
|---------|-------------------|-----|
| `local` | `localhost:19092` | App on host, Kafka in Docker |
| `docker` | `${KAFKA_BOOTSTRAP_SERVERS:kafka:9092}` | App inside the Compose network |
| `desenv` / `homolog` / `prod` | env vars (SASL/SSL ready) | remote brokers |

Consumer group: `group-1`. HTTP port: **8091**.

## Run everything with Docker (easiest)

The included [`docker-compose.yml`](docker-compose.yml) brings up Kafka (KRaft, no
Zookeeper), Kafka UI, and **both** the producer and consumer (built straight from
GitHub):

```bash
docker compose up --build
```

- Kafka UI: http://localhost:8080
- Kafka external listener (from host): `localhost:19092`
- Watch this consumer pick up messages:

```bash
docker compose logs -f consumer
```

Stop and clean up:

```bash
docker compose down
```

## Run the app on the host, Kafka in Docker

```bash
# 1. start just the broker (+ UI)
docker compose up -d kafka kafka-ui

# 2. run the consumer against localhost:19092
./gradlew bootRun --args='--spring.profiles.active=local'
```

## Build

```bash
./gradlew build          # jar + tests
./gradlew bootJar        # runnable jar only
```
