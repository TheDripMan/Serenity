package dev.serenity.event.impl;

import dev.serenity.event.Event;

public class ChatEvent extends Event {
    private String message;

    public ChatEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
