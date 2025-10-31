package ru.kpfu.itis.mukminov.enums;

public enum Status {
    NEW(0),
    IN_PROGRESS(1),
    COMPLETED(2);

    private final int priority;

    Status(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
