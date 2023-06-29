package model;

import exception.ValidationException;

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
        setFrom(from);
        setTo(to);
        setActualTo(actualTo);
        setOnTime(onTime);
        setRemarks(remarks);
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        if (from == null){
            throw new ValidationException("Date cannot be empty");
        }
        else if (from.getYear() < 1900 ) {
            throw new ValidationException("Provide valid date");
        }
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        if (to == null){
            throw new ValidationException("Date cannot be empty");
        }
        else if (to.getYear() < 1900 ) {
            throw new ValidationException("Provide valid date");
        }
        this.to = to;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        if (remarks == null || remarks.trim().isBlank()) {
            throw new ValidationException("Result cannot be empty");
        }
        this.remarks = remarks;
    }

    public LocalDate getActualTo () {
        return actualTo;
    }

    public void setActualTo (LocalDate actualTo) {
        if (actualTo == null){
            throw new ValidationException("Date cannot be empty");
        }
        else if (actualTo.getYear() < 1900 ) {
            throw new ValidationException("Provide valid date");
        }
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
