package me.emmy.artex.grant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.Artex;
import me.emmy.artex.rank.Rank;

/**
 * @author Emmy
 * @project Artex
 * @date 16/08/2024 - 11:39
 */
@Data
@Getter
@Setter
public class Grant {
    private String rank;
    private String addedOn;
    private String reason;
    private String addedBy;
    private String removedBy;
    private String removedReason;

    private long addedAt;
    private long removedAt;
    private long duration;

    private boolean active;
    private boolean permanent;

    /**
     * Get the rank object by accessing the rank repository
     *
     * @return the rank object
     */
    public Rank getRank() {
        return Artex.getInstance().getRankService().getRank(this.rank);
    }

    /**
     * Check if the grant has expired
     *
     * @return if the grant has expired
     */
    public boolean hasExpired() {
        return !this.permanent && System.currentTimeMillis() >= this.addedAt + this.duration;
    }
}