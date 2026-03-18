# Java Runtime Security Agent (JRSA)

## Overview

**Java Runtime Security Agent (JRSA)** is a JVM-based Runtime Application Self-Protection (RASP) agent built using **ByteBuddy** for real-time instrumentation and enforcement.

It intercepts sensitive runtime operations (e.g., command execution, reflection, file writes), analyzes behavior, and enforces security policies to **detect and block malicious activity during execution**.

This project demonstrates core concepts behind modern runtime security systems such as **RASP and IAST agents**.

---

## Key Capabilities

### 🔍 Runtime Instrumentation
- Uses ByteBuddy to modify bytecode at runtime via Java Agent (`premain`)
- Instruments critical JVM methods without requiring application code changes

### 🛡️ Detection & Enforcement
- Intercepts high-risk APIs:
  - `Runtime.exec`
  - `ProcessBuilder`
  - Reflection APIs (`Method.invoke`, `Class.forName`)
  - File write operations
- Applies **policy-based decision engine** (detect / simulate / block)
- Prevents malicious execution by terminating calls before original method invocation

### ⚡ Active Blocking Mechanism
- Rewrites method calls to delegate execution to security hooks
- Hooks analyze arguments and execution context
- Malicious operations are **blocked via runtime exceptions before execution**

### 📡 Telemetry & Observability
- Generates structured security events:
  - call stack
  - execution context
  - timestamps
- Produces **1000+ runtime events** in test scenarios
- Designed for integration with external monitoring systems

### 🚀 Performance
- Lightweight instrumentation with **<5ms overhead per 1000 operations**
- No application code modification required

---

## Architecture


Application Code
│
▼
Java Agent (premain)
│
▼
ByteBuddy Instrumentation
│
▼
Hook Injection (Method Delegation)
│
▼
Policy Engine (Detect / Block)
│
▼
✔ Allow Execution ✖ Block Execution
│
▼
Event Generation → Logging / External Systems


---

## How It Works

1. The agent is attached using the `-javaagent` flag.
2. ByteBuddy instruments selected JVM classes and methods.
3. Method calls are redirected to custom hooks before execution.
4. Hooks:
   - inspect arguments
   - evaluate security policies
5. Based on policy:
   - allow execution
   - or **block via runtime exception**

---

## Project Structure


src/
├── main/java/org/jrsa/agent/
│ ├── instrumentation/
│ │ └── AgentBuilderConfig.java
│ ├── hooks/
│ │ ├── CommandExecHook.java
│ │ ├── FileWriteHook.java
│ │ └── ReflectionHook.java
│ ├── policy/
│ │ └── PolicyEngine.java
│ └── AgentMain.java


---

## Installation & Usage

### Build

```bash
mvn clean package
##Run with Agent
java -javaagent:target/java-runtime-security-agent.jar -jar your-app.jar
##Example Behavior
##Allowed Execution
[INFO] Safe operation executed
##Blocked Execution
[SECURITY] Blocked command execution: /bin/sh -c ls
Exception in thread "main" java.lang.SecurityException: Blocked by JRSA policy
##Current Limitations

Limited API coverage (focused on core high-risk operations)

No distributed tracing / request context propagation

No UI/dashboard (CLI/log-based visibility)

Basic rule engine (no ML-based detection yet)

##Future Enhancements

Advanced policy engine with rule DSL

Taint tracking for data flow analysis

Integration with SIEM / Kafka pipelines

Distributed tracing support

Performance optimizations (sampling, filtering)

##Use Cases

Runtime application security (RASP)

Detection engineering experimentation

JVM instrumentation research

Security monitoring and enforcement prototypes

##Tech Stack

Java

ByteBuddy

Maven

##Author

Ravi Sankar Manem
