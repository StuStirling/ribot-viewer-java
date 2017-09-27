package com.stustirling.ribotviewerjava.data.local.model.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public class Converters {
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        return date.getTime();
    }
}
