package com.example.moneytransfer.activities.models;

public class BranchesModel {

   private String branchname;

   public BranchesModel(){

   }

   public BranchesModel(String branchname) {
      this.branchname = branchname;

   }

   public void setBranchname(String branchname) {
      this.branchname = branchname;
   }

   public String getBranchname() {
      return branchname;
   }
}
