package org.jrsa.agent.evidence;

import java.util.List;
import java.util.stream.Collectors;

public final class StackSnapshot {

    private StackSnapshot() {}

    public static List<String> capture() {

        return StackWalker.getInstance()
                .walk(frames ->
                        frames.limit(10)
                                .map(f ->
                                        f.getClassName() + "."
                                                + f.getMethodName()
                                )
                                .collect(Collectors.toList()));
    }
}
