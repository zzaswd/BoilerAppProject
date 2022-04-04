package com.test.project;

public class MemberId {
    private String userid;
    private String username;
    private String userpw;
    private int id;

    public String getUserId(){
        return userid;
    }
    public String getUsername(){
        return username;
    }

    public String getUserpw(){
        return userpw;
    }
    public int getID(){
        return id;
    }

//    public boolean equals(Object obj){
//        if(obj instanceof MemberId){
//            MemberId mid = (MemberId)obj;
//            if(this.userid == mid.userid) return true;
//            else return false;
//        }
//        return false;
//    }


}