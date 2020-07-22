package org.smartregister.simprint;

import com.simprints.libsimprints.Tier;

import java.io.Serializable;

/**
 * Author : Isaya Mollel on 2019-10-30.
 */
public class SimPrintsIdentification implements Serializable {

    private String guid;
    private float confidence;
    private boolean check;
    private Tier tier;

    public SimPrintsIdentification(String guid){
        this.guid = guid;
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

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
