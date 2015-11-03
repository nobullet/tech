package com.nobullet.vertx.entities;

import java.util.Objects;

/**
 * User entity.
 */
public class User {

    private final String id;
    private final String email;
    private final String passwordHash;
    private final long created;
    private final long lastEdited;

    public User(String id, String email, String passwordHash, long created, long lastEdited) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.created = created;
        this.lastEdited = lastEdited;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public long getCreated() {
        return created;
    }

    public long getLastEdited() {
        return lastEdited;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", created=" + created + ", lastEdited=" + lastEdited + '}';
    }
}
