package com.example.moneytransfer.activities.models;

public class CurrencyTypeModel {

   private String dhrtopkr, pkrtodhr, dollartopkr, pkrtodollar, eurotopkr,
           pkrtoeuro;

   public CurrencyTypeModel(){
   }

   public CurrencyTypeModel(String dhrtopkr, String pkrtodhr, String dollartopkr,
                            String pkrtodollar, String eurotopkr, String pkrtoeuro) {
      this.dhrtopkr = dhrtopkr;
      this.pkrtodhr = pkrtodhr;
      this.dollartopkr = dollartopkr;
      this.pkrtodollar = pkrtodollar;
      this.eurotopkr = eurotopkr;
      this.pkrtoeuro = pkrtoeuro;
   }

   public void setDhrtopkr(String dhrtopkr) {
      this.dhrtopkr = dhrtopkr;
   }

   public void setPkrtodhr(String pkrtodhr) {
      this.pkrtodhr = pkrtodhr;
   }

   public void setDollartopkr(String dollartopkr) {
      this.dollartopkr = dollartopkr;
   }

   public void setPkrtodollar(String pkrtodollar) {
      this.pkrtodollar = pkrtodollar;
   }

   public void setEurotopkr(String eurotopkr) {
      this.eurotopkr = eurotopkr;
   }

   public void setPkrtoeuro(String pkrtoeuro) {
      this.pkrtoeuro = pkrtoeuro;
   }

   public String getDhrtopkr() {
      return dhrtopkr;
   }

   public String getPkrtodhr() {
      return pkrtodhr;
   }

   public String getDollartopkr() {
      return dollartopkr;
   }

   public String getPkrtodollar() {
      return pkrtodollar;
   }

   public String getEurotopkr() {
      return eurotopkr;
   }

   public String getPkrtoeuro() {
      return pkrtoeuro;
   }
}
