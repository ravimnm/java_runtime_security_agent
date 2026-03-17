# Java Runtime Security Agent

## Overview

**Java Runtime Security Agent** is a lightweight Java Agent built using **ByteBuddy** for runtime instrumentation of JVM applications. It monitors and intercepts sensitive operations such as command execution, file writes, and reflection usage to provide visibility into potentially dangerous behaviors.

This project demonstrates how runtime security tooling (similar to RASP/IAST agents) can be implemented using bytecode manipulation.

---

## Key Features

* 🔍 **Runtime Instrumentation**

  * Uses ByteBuddy to modify bytecode at runtime
  * Attaches via Java Agent (`premain`)

* ⚠️ **Security Hooks**

  * Command execution monitoring (`Runtime.exec`, `ProcessBuilder`)
  * File write tracking
  * Reflection usage interception

* 📡 **Event Capture**

  * Captures runtime events from hooked methods
  * Enables forwarding to external logging or monitoring systems

---

## Architecture

```
Java Application
      │
      ▼
Java Agent (premain)
      │
      ▼
ByteBuddy Instrumentation
      │
      ▼
Hooked APIs (Exec / File / Reflection)
      │
      ▼
Event Generation
      │
      ▼
Logging / External API
```

---

## Project Structure

```
src/
 ├── main/
 │   ├── java/org/jrsa/agent/
 │   │   ├── instrumentation/
 │   │   │   └── AgentBuilderConfig.java
 │   │   ├── hooks/
 │   │   │   ├── CommandExecHook.java
 │   │   │   ├── FileWriteHook.java
 │   │   │   └── ReflectionHook.java
 │   │   └── AgentMain.java
 │   └── resources/
 └── test/
```

---

## How It Works

1. The agent is attached at JVM startup using the `-javaagent` flag.
2. ByteBuddy instruments target classes and methods.
3. When sensitive methods are invoked:

   * The hook intercepts execution
   * Relevant data is captured
   * Event is logged or forwarded

---

## Installation & Usage

### 1. Build the Project

```bash
mvn clean package
```

This generates a JAR file in the `target/` directory.

---

### 2. Run with Java Agent

```bash
java -javaagent:target/java-runtime-security-agent.jar -jar your-application.jar
```

---

### 3. Example Output

```
[SECURITY] Command Execution Detected: /bin/sh -c ls
[SECURITY] File Write Detected: /tmp/test.txt
[SECURITY] Reflection Usage Detected: Class.forName(...)
```

---

## Current Limitations

* No request or user context tracking
* No detection or rule engine (only logging)
* No centralized dashboard or storage
* Limited coverage of APIs
* No performance optimizations (sampling/filtering)

---

## Future Improvements

* Add **detection engine** (rule-based or heuristic)
* Implement **taint tracking** for input propagation
* Add **policy engine** for alerting/blocking
* Integrate with **backend service (REST / Kafka)**
* Introduce **performance optimizations**
* Build **dashboard for visualization**

---

## Use Cases

* Runtime security monitoring
* Educational project for Java instrumentation
* Foundation for building:

  * RASP (Runtime Application Self-Protection)
  * IAST (Interactive Application Security Testing)
  * Observability agents

---

## Tech Stack

* Java
* ByteBuddy
* Maven

---

## Why This Project

This project focuses on **JVM internals + security engineering**, specifically:

* Bytecode manipulation
* Runtime interception of sensitive APIs
* Designing low-level monitoring systems

---

## Disclaimer

This is a **learning and experimental project**. It is not production-ready and should not be used as a complete security solution.

---

## Author

Ravi Sankar Manem

