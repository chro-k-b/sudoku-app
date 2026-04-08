package com.example.homeschoolingjava1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_history")
public class GameHistory {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String difficulty;
    public String timer;
    public String date;
    public int mistakes;
    public String imagePath;

    public GameHistory(String difficulty, String timer, String date, int mistakes, String imagePath) {
        this.difficulty = difficulty;
        this.timer = timer;
        this.date = date;
        this.mistakes = mistakes;
        this.imagePath = imagePath;
    }
}