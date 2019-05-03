package edu.utep.cs.sirenandroidapp.Model;

public class Video {
    private int id;
    private String path;
    private String name;
    private String date;
    public Video(){
        this("","","");
    }
    public Video(String path,String name, String date){
        this.path=path;
        this.name=name;
        this.date=date;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public void setPath(String path){
        this.path=path;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getPath(){
        return path;
    }
    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
}
