package com.learnyourself.object;

/**
 * Created by Nha on 12/7/2015.
 */
public class Word {
    private String name;
    private String meaning;
    private String example;
    private String synonym;
    private boolean isRemember;

    public Word(){
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setMeaning(String meaning){
        this.meaning = meaning;
    }
    public String getMeaning(){
        return this.meaning;
    }

    public void setExample(String example){
        this.example = example;
    }
    public String getExample(){
        return this.example;
    }

    public void setSynonym(String synonym){
        this.synonym = synonym;
    }
    public String getSynonym(){
        return this.synonym;
    }

    public void setIsRemember(boolean isRemember){
        this.isRemember = isRemember;
    }
    public boolean getIsRemember(){
        return this.isRemember;
    }
}
