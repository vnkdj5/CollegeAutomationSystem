package com.gpp.cas.com.gpp.cas.attendance.show;

/**
 * Created by Vaibhav on 30/03/2016.
 */
public class Attendance {
    private String subject;
    private String LP;
    private String LA;
    private String PP;
    private String PA;

    public String getPERC() {
        return PERC;
    }

    public void setPERC(String PERC) {
        this.PERC = PERC;
    }

    private String PERC;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLP() {
        return LP;
    }

    public void setLP(String LP) {
        this.LP = LP;
    }

    public String getLA() {
        return LA;
    }

    public void setLA(String LA) {
        this.LA = LA;
    }

    public String getPP() {
        return PP;
    }

    public void setPP(String PP) {
        this.PP = PP;
    }

    public String getPA() {
        return PA;
    }

    public void setPA(String PA) {
        this.PA = PA;
    }
}
