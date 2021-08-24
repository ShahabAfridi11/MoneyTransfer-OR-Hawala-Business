package com.example.moneytransfer.activities.models;

public class PayTransactionModel {

   private String sendername, receivername, sendercnic, receivercnic, senderphonenumber,
           receiverphonenumber, receivingbranch, sendingbranch, totalamount, sendingadmin, sendingdate;

   public PayTransactionModel(){

   }

    public PayTransactionModel(String sendername, String receivername, String sendercnic, String receivercnic,
                               String senderphonenumber, String receiverphonenumber, String receivingbranch,
                               String sendingbranch, String totalamount, String sendingadmin, String sendingdate) {
        this.sendername = sendername;
        this.receivername = receivername;
        this.sendercnic = sendercnic;
        this.receivercnic = receivercnic;
        this.senderphonenumber = senderphonenumber;
        this.receiverphonenumber = receiverphonenumber;
        this.receivingbranch = receivingbranch;
        this.sendingbranch = sendingbranch;
        this.totalamount = totalamount;
        this.sendingadmin = sendingadmin;
        this.sendingdate = sendingdate;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public void setSendercnic(String sendercnic) {
        this.sendercnic = sendercnic;
    }

    public void setReceivercnic(String receivercnic) {
        this.receivercnic = receivercnic;
    }

    public void setSenderphonenumber(String senderphonenumber) {
        this.senderphonenumber = senderphonenumber;
    }

    public void setReceiverphonenumber(String receiverphonenumber) {
        this.receiverphonenumber = receiverphonenumber;
    }

    public void setReceivingbranch(String receivingbranch) {
        this.receivingbranch = receivingbranch;
    }

    public void setSendingbranch(String sendingbranch) {
        this.sendingbranch = sendingbranch;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public void setSendingadmin(String sendingadmin) {
        this.sendingadmin = sendingadmin;
    }

    public void setSendingdate(String sendingdate) {
        this.sendingdate = sendingdate;
    }

    public String getSendername() {
        return sendername;
    }

    public String getReceivername() {
        return receivername;
    }

    public String getSendercnic() {
        return sendercnic;
    }

    public String getReceivercnic() {
        return receivercnic;
    }

    public String getSenderphonenumber() {
        return senderphonenumber;
    }

    public String getReceiverphonenumber() {
        return receiverphonenumber;
    }

    public String getReceivingbranch() {
        return receivingbranch;
    }

    public String getSendingbranch() {
        return sendingbranch;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public String getSendingadmin() {
        return sendingadmin;
    }

    public String getSendingdate() {
        return sendingdate;
    }
}
