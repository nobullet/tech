package com.nobullet.vertx.entities;

import java.util.List;
import java.util.Objects;

/**
 * Video entity.
 */
public class Video {

    private final String id;
    private final String title;
    private final User owner;
    private final List<String> urlSources;
    private final long views;
    private final long created;
    private final long lastEdited;

    public Video(String id, User owner, String title, List<String> urlSources, long views, long created, long lastEdited) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.urlSources = urlSources;
        this.views = views;
        this.created = created;
        this.lastEdited = lastEdited;
    }

    public String getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getUrlSources() {
        return urlSources;
    }

    public long getViews() {
        return views;
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
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Video other = (Video) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Video{id=" + id + ", title=" + title + ", urlSources=" + urlSources + ", views=" + views
                + ", created=" + created + ", lastEdited=" + lastEdited + ", ownerId=" + owner.getId() + '}';
    }
}
