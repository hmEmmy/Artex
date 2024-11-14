package me.emmy.artex.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 16:35
 */
@Getter
@Setter
public class ChatService {
    private boolean isChatMuted;

    /**
     * Constructor for the ChatRepository class.
     *
     * @param isChatMuted boolean
     */
    public ChatService(boolean isChatMuted) {
        this.isChatMuted = isChatMuted;
    }
}