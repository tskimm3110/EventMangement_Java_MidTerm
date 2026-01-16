package model.enums;

public enum EventStatus {
    PLANNED,        // event created but not open
    OPEN,           // registration open
    FULL,           // registration full
    ONGOING,        // event is happening
    COMPLETED,      // event finished
    CANCELLED,      // event cancelled
    POSTPONED,      // event delayed to another date
    ARCHIVED,        // old events stored for record
    UPCOMING
}

