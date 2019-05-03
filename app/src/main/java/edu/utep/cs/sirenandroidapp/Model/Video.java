package edu.utep.cs.sirenandroidapp.Model;

public class Video {
    private String name;
    private String date;
    public Video(){
        this("","");
    }
    public Video(String name, String date){
        this.name=name;
        this.date=date;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
}
