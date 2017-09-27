package com.stustirling.ribotviewjava.domain;

import android.support.annotation.Nullable;

import com.stustirling.ribotviewjava.domain.model.Ribot;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public interface RibotRepository {
    Flowable<Resource<List<Ribot>>> getRibots(@Nullable RefreshTrigger refreshTrigger);
    void insertRibots(List<Ribot> ribots);
}
