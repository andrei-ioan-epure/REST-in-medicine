package db.repository;

import db.model.BlacklistItem;

public interface BlacklistRepository {
    void addToBlacklist(BlacklistItem item);
    boolean isTokenBlacklisted(String tokenId);
}
