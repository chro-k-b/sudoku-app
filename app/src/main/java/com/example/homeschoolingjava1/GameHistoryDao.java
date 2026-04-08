package com.example.homeschoolingjava1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameHistoryDao {
    @Insert
    void insert(GameHistory gameHistory);

    @Query("SELECT * FROM game_history ORDER BY id DESC")
    List<GameHistory> getAll();

    @Query("DELETE FROM game_history")
    void deleteAll();
}