package me.emmy.artex.punishment;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.punishment.enums.EnumPunishmentType;

/**
 * @author Emmy
 * @project Artex
 * @date 07/11/2024 - 14:20
 */
@Getter
@Setter
public class Punishment {
    private EnumPunishmentType punishmentType;

    private String punisher;
    private String punishReason;
    private String pardoner;
    private String pardonReason;

    private long duration;
    private long expiration;
    private long addedAt;
    private long removedAt;

    private boolean active;
    private boolean permanent;
}