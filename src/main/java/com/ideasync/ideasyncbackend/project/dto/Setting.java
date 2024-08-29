package com.ideasync.ideasyncbackend.project.dto;

import java.util.UUID;

public class Setting {
    private UUID projectId;
    private boolean isPublic;

    public Setting(UUID projectId, boolean isPublic) {
        this.projectId = projectId;
        this.isPublic = isPublic;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
