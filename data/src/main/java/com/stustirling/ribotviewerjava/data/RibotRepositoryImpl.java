package com.stustirling.ribotviewerjava.data;

import android.support.annotation.Nullable;

import com.stustirling.ribotviewerjava.data.api.RibotService;
import com.stustirling.ribotviewerjava.data.api.model.ApiRibot;
import com.stustirling.ribotviewerjava.data.local.dao.RibotDao;
import com.stustirling.ribotviewerjava.data.local.model.LocalRibot;
import com.stustirling.ribotviewjava.domain.RefreshTrigger;
import com.stustirling.ribotviewjava.domain.Resource;
import com.stustirling.ribotviewjava.domain.RibotRepository;
import com.stustirling.ribotviewjava.domain.model.Ribot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public class RibotRepositoryImpl implements RibotRepository {

    private final RibotService ribotService;
    private final RibotDao ribotDao;

    @Inject
    public RibotRepositoryImpl(RibotService ribotService,
                               RibotDao ribotDao) {
        this.ribotService = ribotService;
        this.ribotDao = ribotDao;
    }

    @Override
    public Flowable<Resource<List<Ribot>>> getRibots(@Nullable final RefreshTrigger refreshTrigger) {
        return Flowable.create(new FlowableOnSubscribe<Resource<List<Ribot>>>() {
            @Override
            public void subscribe(FlowableEmitter<Resource<List<Ribot>>> emitter) throws Exception {
                new NetworkBoundResource<List<LocalRibot>,List<ApiRibot>,List<Ribot>>(emitter,refreshTrigger) {
                    @Override
                    void saveCallResult(List<LocalRibot> items) {
                        ribotDao.insert(items);
                    }

                    @Override
                    Flowable<List<LocalRibot>> loadFromDb() {
                        return ribotDao.getRibots();
                    }

                    @Override
                    Single<List<ApiRibot>> createCall() {
                        return ribotService.getRibots();
                    }

                    @Override
                    Function<List<ApiRibot>, List<LocalRibot>> mapToLocal() {
                        return new Function<List<ApiRibot>, List<LocalRibot>>() {
                            @Override
                            public List<LocalRibot> apply(List<ApiRibot> apiRibots) throws Exception {
                                List<LocalRibot> convertedRibots = new ArrayList<>();
                                for (ApiRibot apiRibot :
                                        apiRibots) {
                                    ApiRibot.ApiRibotProfile profile = apiRibot.profile;
                                    convertedRibots.add( new LocalRibot(
                                            profile.id,
                                            profile.name.first,
                                            profile.name.last,
                                            profile.email,
                                            profile.dob,
                                            profile.color,
                                            profile.bio,
                                            profile.avatar,
                                            profile.isActive));
                                }
                                return convertedRibots;
                            }
                        };
                    }

                    @Override
                    Function<List<LocalRibot>, List<Ribot>> mapToDomain() {
                        return new Function<List<LocalRibot>, List<Ribot>>() {
                            @Override
                            public List<Ribot> apply(List<LocalRibot> localRibots) throws Exception {
                                List<Ribot> convertedRibots = new ArrayList<>();
                                for (LocalRibot localRibot :
                                        localRibots) {
                                    LocalRibot profile = localRibot;
                                    convertedRibots.add( new Ribot(
                                            profile.id,
                                            profile.firstName,
                                            profile.lastName,
                                            profile.email,
                                            profile.dateOfBirth,
                                            profile.color,
                                            profile.bio,
                                            profile.avatar,
                                            profile.isActive));
                                }
                                return convertedRibots;
                            }
                        };
                    }
                };

            }
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public void insertRibots(List<Ribot> ribots) {
        List<LocalRibot> convertedRibots = new ArrayList<>();
        for (Ribot ribot :
                ribots) {
            convertedRibots.add( new LocalRibot(
                    ribot.getId(),
                    ribot.getFirstName(),
                    ribot.getLastName(),
                    ribot.getEmail(),
                    ribot.getDateOfBirth(),
                    ribot.getColor(),
                    ribot.getBio(),
                    ribot.getAvatar(),
                    ribot.getActive()));
        }
        ribotDao.insert(convertedRibots);
    }
}
