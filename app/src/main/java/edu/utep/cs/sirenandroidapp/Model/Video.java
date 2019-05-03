package edu.utep.cs.sirenandroidapp.Model;

public class Video {
    private int id;
    private String name;
    private String date;
    private String path;
    public Video(){
        this("","","");
    }
    public Video(String name, String date,String path){
        this.name=name;
        this.date=date;
        this.path=path;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
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
    public String getPath(){
        return path;
    }
    public void setPath(String path){
        this.path=path;
    }


}

