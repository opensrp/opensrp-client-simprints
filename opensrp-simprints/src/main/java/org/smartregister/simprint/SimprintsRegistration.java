package org.smartregister.simprint;

import java.io.Serializable;

public class SimprintsRegistration implements Serializable {
    private String guid;
    private Boolean checkStatus;
    public SimprintsRegistration(String guId){
        this.guid = guId;
    }

    public String getGuid() {
        return guid;
    }
    public Boolean getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Boolean checkStatus) {
        this.checkStatus = checkStatus;
    }
}
