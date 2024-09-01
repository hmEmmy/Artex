package me.emmy.artex.broadcast;

import lombok.Getter;

import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 01/09/2024 - 10:52
 */
@Getter
public class Broadcast {

    private final List<String> lines;

    /**
     * Constructor for the Broadcast class.
     *
     * @param lines the lines of the broadcast
     */
    public Broadcast(List<String> lines) {
        this.lines = lines;
    }
}