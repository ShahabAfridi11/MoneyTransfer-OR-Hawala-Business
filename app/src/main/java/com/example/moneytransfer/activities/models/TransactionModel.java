package com.example.moneytransfer.activities.models;

public class TransactionModel {

   private String sendername, receivername, sendercnic, receivercnic, senderphonenumber,
           receiverphonenumber, payingbranch, payingadmin, totalamount, sendingadmin, sendingdate, payingdate;


   public TransactionModel(){
   }

   public TransactionModel(String sendername, String receivername, String sendercnic, String receivercnic,
                           String senderphonenumber, String receiverphonenumber, String payingbranch,
                           String payingadmin,
                           String totalamount, String sendingadmin, String sendingdate, String payingdate) {
      this.sendername = sendername;
      this.receivername = receivername;
      this.sendercnic = sendercnic;
      this.receivercnic = receivercnic;
      this.senderphonenumber = senderphonenumber;
      this.receiverphonenumber = receiverphonenumber;
      this.payingbranch = payingbranch;
      this.payingadmin = payingadmin;
      this.totalamount = totalamount;
      this.sendingadmin = sendingadmin;
      this.sendingdate = sendingdate;
      this.payingdate = payingdate;
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

   public void setPayingbranch(String payingbranch) {
      this.payingbranch = payingbranch;
   }

   public void setPayingadmin(String payingadmin) {
      this.payingadmin = payingadmin;
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

   public void setPayingdate(String payingdate) {
      this.payingdate = payingdate;
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

   public String getPayingbranch() {
      return payingbranch;
   }

   public String getPayingadmin() {
      return payingadmin;
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

   public String getPayingdate() {
      return payingdate;
   }
}
