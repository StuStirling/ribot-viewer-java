package com.stustirling.ribotviewerjava.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.stustirling.ribotviewerjava.data.local.model.LocalRibot;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Stu Stirling on 27/09/2017.
 */
@Dao
public interface RibotDao {
    @Query("SELECT * FROM ribots")
    Flowable<List<LocalRibot>> getRibots();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<LocalRibot> ribots);
}
