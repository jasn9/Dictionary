package com.example.dictionary;

public class Word {
    private String word_name;
    private String word_meaning;
    private int word_mark;
    public Word() {

    }

    public void set_word(String s)
    {
        this.word_name = s;
    }

    public void set_meaning(String s)
    {
        this.word_meaning = s;
    }

    public void set_mark(int n){
        this.word_mark = n;
    }

    public String get_word(){
        return this.word_name;
    }

    public String get_meaning(){
        return this.word_meaning;
    }
    public int get_mark(){
        return this.word_mark;
    }

}

