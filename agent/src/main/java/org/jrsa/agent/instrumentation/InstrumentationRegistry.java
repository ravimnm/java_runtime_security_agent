package org.jrsa.agent.instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.instrument.Instrumentation;

import org.jrsa.agent.hooks.CommandExecHook;
import org.jrsa.agent.hooks.ReflectionHook;
import org.jrsa.agent.hooks.FileWriteHook;

public final class InstrumentationRegistry {

    private InstrumentationRegistry() {}

    public static void register(Instrumentation inst) {

        System.out.println("[JRSA] Registering instrumentation...");

        new AgentBuilder.Default()

                // Handle already loaded classes
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)

                // Debug logs (VERY IMPORTANT)
                .with(AgentBuilder.Listener.StreamWriting.toSystemOut())

                // Do not ignore core JDK classes
                .ignore(ElementMatchers.none())

                // =========================
                // Runtime.exec
                // =========================
                .type(ElementMatchers.named("java.lang.Runtime"))
                .transform((builder, type, classLoader, module, pd) -> {
                    System.out.println("[JRSA] Hooking Runtime.exec");
                    return builder.method(ElementMatchers.named("exec"))
                            .intercept(MethodDelegation.to(CommandExecHook.class));
                })

                // =========================
                // ProcessBuilder.start (more reliable than Runtime.exec)
                // =========================
                .type(ElementMatchers.named("java.lang.ProcessBuilder"))
                .transform((builder, type, classLoader, module, pd) -> {
                    System.out.println("[JRSA] Hooking ProcessBuilder.start");
                    return builder.method(ElementMatchers.named("start"))
                            .intercept(MethodDelegation.to(CommandExecHook.class));
                })

                // =========================
                // Reflection.invoke
                // =========================
                .type(ElementMatchers.named("java.lang.reflect.Method"))
                .transform((builder, type, classLoader, module, pd) -> {
                    System.out.println("[JRSA] Hooking Method.invoke");
                    return builder.method(ElementMatchers.named("invoke"))
                            .intercept(MethodDelegation.to(ReflectionHook.class));
                })

                // =========================
                // FileWriter.write
                // =========================
                .type(ElementMatchers.named("java.io.FileWriter"))
                .transform((builder, type, classLoader, module, pd) -> {
                    System.out.println("[JRSA] Hooking FileWriter.write");
                    return builder.method(ElementMatchers.named("write"))
                            .intercept(MethodDelegation.to(FileWriteHook.class));
                })

                // =========================
                // Files.write (NIO)
                // =========================
                .type(ElementMatchers.named("java.nio.file.Files"))
                .transform((builder, type, classLoader, module, pd) -> {
                    System.out.println("[JRSA] Hooking Files.write");
                    return builder.method(ElementMatchers.named("write"))
                            .intercept(MethodDelegation.to(FileWriteHook.class));
                })

                // =========================
                // INSTALL (CRITICAL)
                // =========================
                .installOn(inst);

        System.out.println("[JRSA] Instrumentation installed successfully");
    }
}