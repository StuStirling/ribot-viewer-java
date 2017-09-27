package com.stustirling.ribotviewerjava.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.stustirling.ribotviewerjava.data.local.dao.RibotDao;
import com.stustirling.ribotviewerjava.data.local.model.LocalRibot;
import com.stustirling.ribotviewerjava.data.local.model.converters.Converters;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

@Database(entities = {LocalRibot.class},version = 1)
@TypeConverters(Converters.class)
abstract class LocalDatabase extends RoomDatabase{
    abstract RibotDao ribotDao();
}
