package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Borrow extends ObjectPlus implements Serializable {

    private LocalDate from;
    private LocalDate to;
    private LocalDate actualTo; // faktyczna data oddania
    private boolean onTime;
    private String remarks;


    public Borrow(LocalDate from, LocalDate to, LocalDate actualTo, boolean onTime, String remarks) {
        super();
        this.from = from;
        this.to = to;
        this.actualTo = actualTo;
        this.onTime = onTime;
        this.remarks = remarks;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getActualTo () {
        return actualTo;
    }

    public void setActualTo (LocalDate actualTo) {
        this.actualTo = actualTo;
    }

    public boolean isOnTime () {
        return onTime;
    }

    public void setOnTime (boolean onTime) {
        this.onTime = onTime;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "from=" + from +
                ", to=" + to +
                ", onTime=" + onTime +
                ", remarks='" + remarks + '\'' + "}\n" ;
    }

}
