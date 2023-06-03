package dev.serenity.ui.font;

import org.lwjgl.opengl.GL11;

public class CachedFont {
    private int displayList;
    private long lastUsage;
    private boolean deleted;

    public CachedFont(int displayList, long lastUsage) {
        this.displayList = displayList;
        this.lastUsage = lastUsage;
        this.deleted = false;
    }

    protected void finalize() {
        if (!deleted)
            GL11.glDeleteLists(displayList, 1);
    }

    public int getDisplayList() {
        return displayList;
    }

    public void setDisplayList(int displayList) {
        this.displayList = displayList;
    }

    public long getLastUsage() {
        return lastUsage;
    }

    public void setLastUsage(long lastUsage) {
        this.lastUsage = lastUsage;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
