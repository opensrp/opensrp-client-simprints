package org.smartregister.simprint;

import java.io.Serializable;

/**
 * Author : Isaya Mollel on 2019-10-30.
 */
public class SimPrintsIdentification implements Serializable {

    private String guid;
    private int confidence;
    private boolean check;

    public SimPrintsIdentification(String guid){
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

    public int getConfidence() {
        return confidence;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
