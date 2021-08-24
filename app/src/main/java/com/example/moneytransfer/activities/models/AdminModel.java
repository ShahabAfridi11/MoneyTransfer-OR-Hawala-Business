package com.example.moneytransfer.activities.models;

public class AdminModel {

   private String firstname, secondname, fathername, cnicnumber, branchname,
           phonenumber, useremail, password, date, cnicpath,profilepath;

   public AdminModel(){

   }

   public AdminModel(String firstname, String secondname, String fathername, String cnicnumber,
                     String branchname, String phonenumber,
              String useremail, String password, String date, String cnicpath, String profilepath) {
      this.firstname = firstname;
      this.secondname = secondname;
      this.fathername = fathername;
      this.cnicnumber = cnicnumber;
      this.branchname = branchname;
      this.phonenumber = phonenumber;
      this.useremail = useremail;
      this.password = password;
      this.date = date;
      this.cnicpath = cnicpath;
      this.profilepath = profilepath;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public void setSecondname(String secondname) {
      this.secondname = secondname;
   }

   public void setFathername(String fathername) {
      this.fathername = fathername;
   }

   public void setCnicnumber(String cncicnumber) {
      this.cnicnumber = cncicnumber;
   }

   public void setBranchname(String branchname) {
      this.branchname = branchname;
   }

   public void setPhonenumber(String phonenumber) {
      this.phonenumber = phonenumber;
   }

   public void setUseremail(String useremail) {
      this.useremail = useremail;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public void setCnicpath(String cnicpath) {
      this.cnicpath = cnicpath;
   }

   public void setProfilepath(String profilepath) {
      this.profilepath = profilepath;
   }

   public String getBranchname() {
      return branchname;
   }

   public String getFirstname() {
      return firstname;
   }

   public String getSecondname() {
      return secondname;
   }

   public String getFathername() {
      return fathername;
   }

   public String getCnicnumber() {
      return cnicnumber;
   }


   public String getPhonenumber() {
      return phonenumber;
   }

   public String getUseremail() {
      return useremail;
   }

   public String getPassword() {
      return password;
   }

   public String getDate() {
      return date;
   }

   public String getCnicpath() {
      return cnicpath;
   }

   public String getProfilepath() {
      return profilepath;
   }
}
