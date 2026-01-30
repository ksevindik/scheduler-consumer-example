# Scheduler Consumer Example

A proof of concept demonstrating **distributed task processing** using Spring Boot, Quartz Scheduler, and Apache Kafka.

## Purpose

This POC showcases a pattern for splitting large tasks into smaller sub-tasks and processing them in parallel through a message queue. It demonstrates how to:

- Schedule jobs using Quartz with JDBC-backed clustering support
- Decompose a main task into parallelizable sub-tasks
- Distribute work across Kafka partitions for horizontal scalability
- Process messages reliably with manual acknowledgment

## Architecture

```
┌─────────────────┐     ┌─────────────────────┐     ┌─────────────┐     ┌──────────────────┐
│  REST API       │────▶│  Quartz Scheduler   │────▶│   Kafka     │────▶│  Kafka Consumer  │
│  (submit job)   │     │  (split task)       │     │  (10 parts) │     │  (process & save)│
└─────────────────┘     └─────────────────────┘     └─────────────┘     └──────────────────┘
```

## Key Areas

### 1. Clustered Job Scheduling (Quartz)
- JDBC job store for persistence and cluster safety
- `@DisallowConcurrentExecution` to prevent parallel runs of the same job
- Automatic instance ID generation for cluster node identification
- Jobs can be submitted dynamically via REST API

### 2. Task Decomposition
- `MainTask` represents a large workload defined by a numeric range
- `SubTaskCreator` splits the main task into equal partitions
- Each partition is assigned to a specific Kafka partition for ordered processing

### 3. Message-Driven Processing (Kafka)
- 10-partition topic (`subtask-topic`) for parallel consumption
- Manual acknowledgment (`MANUAL_IMMEDIATE`) for reliable processing
- Consumer group ensures each message is processed once

### 4. Idempotent Execution
- `SubTaskExecutionService` checks for existing records before creating
- Safe for message redelivery scenarios

## Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Kotlin 1.6.21 |
| Framework | Spring Boot 2.7.4 |
| Scheduler | Quartz (JDBC clustered mode) |
| Messaging | Apache Kafka |
| Database | H2 (TCP mode) |
| Java | 17 |

## Getting Started

### 1. Start Infrastructure

```bash
docker-compose up
```

This starts:
- **H2 Database** - TCP server on port 9292, web console on port 8083
- **Zookeeper** - Kafka coordination on port 2181
- **Kafka** - Message broker on port 9092
- **Kafdrop** - Kafka UI on port 9000

### 2. Run the Application

```bash
./gradlew bootRun
```

### 3. Submit a Job

```bash
curl -X POST -H 'Content-Type: application/json' \
  -d '{"start":1,"end":999}' \
  http://localhost:8080/scheduler/jobs
```

### 4. Verify Results

- **H2 Console**: http://localhost:8083
  - JDBC URL: `jdbc:h2:tcp://localhost:9292/h2-data/test`
  - Username: `sa`, Password: (empty)
  - Query: `SELECT * FROM FOO`
- **Kafka Messages**: http://localhost:9000 (Kafdrop UI)

## Class Diagram

![class diagram](class_diagram.svg)

## References

- [Quartz JDBC Job Store Clustering](http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigJDBCJobStoreClustering.html)
- [How to Cluster Effectively Quartz Jobs](https://medium.com/javarevisited/how-to-cluster-effectively-quartz-jobs-9b097f5e1191)
