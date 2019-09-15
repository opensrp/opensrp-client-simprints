package org.smartregister.simprint;

import com.simprints.libsimprints.Tier;

import java.io.Serializable;

public class SimprintsVerification implements Serializable {
    private String guid;
    private Boolean checkStatus;
    private Tier tier;
    public SimprintsVerification(String guId){
        this.guid = guId;
    }


    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Tier getTier() {
        return tier;
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
