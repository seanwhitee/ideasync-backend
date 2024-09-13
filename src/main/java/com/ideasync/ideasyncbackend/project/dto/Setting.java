package com.ideasync.ideasyncbackend.project.dto;

import java.util.UUID;

public class Setting {
    private UUID projectId;
    private boolean isPublic;
    private String status;


    public Setting(UUID projectId, boolean isPublic, String status) {
        this.projectId = projectId;
        this.isPublic = isPublic;
        this.status = status;
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

    public String getStatusName() {
        return status;
    }

    public void setStatusName(String statusName) {
        this.status = statusName;
    }
}
