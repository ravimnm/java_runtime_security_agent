package org.jrsa.agent.evidence;

public final class EventBuilder {

    private EventBuilder() {}

    public static SecurityEvent reflection(String detail) {

        return new SecurityEvent(
                "REFLECTION_ABUSE",
                detail,
                StackSnapshot.capture()
        );
    }
    
    public static SecurityEvent fileWrite(String detail) {

        return new SecurityEvent(
                "FILE_WRITE",
                detail,
                StackSnapshot.capture()
        );
    }
    public static SecurityEvent commandExecution(String detail) {

        return new SecurityEvent(
                "COMMAND_EXECUTION",
                detail,
                StackSnapshot.capture()
        );
    }

	public static SecurityEvent deserialization(String detail) {
		// TODO Auto-generated method stub
		return new SecurityEvent(
                "COMMAND_EXECUTION",
                detail,
                StackSnapshot.capture()
        );
	}
}