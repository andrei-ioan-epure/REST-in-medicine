package db.model;


import java.time.Instant;

public class BlacklistItem {

    private final String tokenId;
    private final Instant timestamp;

    public BlacklistItem(String tokenId, Instant timestamp) {
        this.tokenId = tokenId;
        this.timestamp = timestamp;
    }

    public String getTokenId() {
        return tokenId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}