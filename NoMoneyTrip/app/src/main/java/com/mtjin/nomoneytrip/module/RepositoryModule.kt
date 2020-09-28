package com.mtjin.nomoneytrip.module

import com.mtjin.nomoneytrip.data.alarm.source.AlarmRepository
import com.mtjin.nomoneytrip.data.alarm.source.AlarmRepositoryImpl
import com.mtjin.nomoneytrip.data.community.source.CommunityRepository
import com.mtjin.nomoneytrip.data.community.source.CommunityRepositoryImpl
import com.mtjin.nomoneytrip.data.email_login.source.EmailLoginRepository
import com.mtjin.nomoneytrip.data.email_login.source.EmailLoginRepositoryImpl
import com.mtjin.nomoneytrip.data.favorite.source.FavoriteRepository
import com.mtjin.nomoneytrip.data.favorite.source.FavoriteRepositoryImpl
import com.mtjin.nomoneytrip.data.home.source.HomeRepository
import com.mtjin.nomoneytrip.data.home.source.HomeRepositoryImpl
import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepository
import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepositoryImpl
import com.mtjin.nomoneytrip.data.lodgment_detail.source.LodgmentDetailRepository
import com.mtjin.nomoneytrip.data.lodgment_detail.source.LodgmentDetailRepositoryImpl
import com.mtjin.nomoneytrip.data.login.source.LoginRepository
import com.mtjin.nomoneytrip.data.login.source.LoginRepositoryImpl
import com.mtjin.nomoneytrip.data.master_login.source.MasterLoginRepository
import com.mtjin.nomoneytrip.data.master_login.source.MasterLoginRepositoryImpl
import com.mtjin.nomoneytrip.data.master_main.source.MasterMainRepository
import com.mtjin.nomoneytrip.data.master_main.source.MasterMainRepositoryImpl
import com.mtjin.nomoneytrip.data.master_write.source.MasterWriteRepository
import com.mtjin.nomoneytrip.data.master_write.source.MasterWriteRepositoryImpl
import com.mtjin.nomoneytrip.data.phone.source.PhoneAuthRepository
import com.mtjin.nomoneytrip.data.phone.source.PhoneAuthRepositoryImpl
import com.mtjin.nomoneytrip.data.profile.soruce.ProfileRepository
import com.mtjin.nomoneytrip.data.profile.soruce.ProfileRepositoryImpl
import com.mtjin.nomoneytrip.data.profile_edit.source.ProfileEditRepository
import com.mtjin.nomoneytrip.data.profile_edit.source.ProfileEditRepositoryImpl
import com.mtjin.nomoneytrip.data.recommend_review.source.RecommendReviewRepository
import com.mtjin.nomoneytrip.data.recommend_review.source.RecommendReviewRepositoryImpl
import com.mtjin.nomoneytrip.data.reservation.source.ReservationRepository
import com.mtjin.nomoneytrip.data.reservation.source.ReservationRepositoryImpl
import com.mtjin.nomoneytrip.data.reservation_history.source.ReservationHistoryRepository
import com.mtjin.nomoneytrip.data.reservation_history.source.ReservationHistoryRepositoryImpl
import com.mtjin.nomoneytrip.data.reservation_phase_first.source.ReservationPhaseFirstRepository
import com.mtjin.nomoneytrip.data.reservation_phase_first.source.ReservationPhaseFirstRepositoryImpl
import com.mtjin.nomoneytrip.data.setting.source.SettingRepository
import com.mtjin.nomoneytrip.data.setting.source.SettingRepositoryImpl
import com.mtjin.nomoneytrip.data.tour_detail.source.TourDetailRepository
import com.mtjin.nomoneytrip.data.tour_detail.source.TourDetailRepositoryImpl
import com.mtjin.nomoneytrip.data.tour_no_history.source.TourNoHistoryRepository
import com.mtjin.nomoneytrip.data.tour_no_history.source.TourNoHistoryRepositoryImpl
import com.mtjin.nomoneytrip.data.tour_write.source.TourWriteRepository
import com.mtjin.nomoneytrip.data.tour_write.source.TourWriteRepositoryImpl
import com.mtjin.nomoneytrip.utils.REVIEW
import com.mtjin.nomoneytrip.utils.USER
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<LocalPageRepository> { LocalPageRepositoryImpl(get(named("tour")), get()) }
    single<EmailLoginRepository> { EmailLoginRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<TourWriteRepository> {
        TourWriteRepositoryImpl(
            get(),
            get(named(REVIEW)),
            get(named("fcm"))
        )
    }
    single<ReservationRepository> { ReservationRepositoryImpl(get(), get(named("fcm")), get()) }
    single<ReservationHistoryRepository> {
        ReservationHistoryRepositoryImpl(
            get(),
            get(named("fcm"))
        )
    }
    single<ReservationPhaseFirstRepository> { ReservationPhaseFirstRepositoryImpl(get()) }
    single<CommunityRepository> { CommunityRepositoryImpl(get()) }
    single<TourNoHistoryRepository> { TourNoHistoryRepositoryImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<ProfileEditRepository> { ProfileEditRepositoryImpl(get(), get(named(USER))) }
    single<LodgmentDetailRepository> { LodgmentDetailRepositoryImpl(get()) }
    single<AlarmRepository> { AlarmRepositoryImpl(get()) }
    single<PhoneAuthRepository> { PhoneAuthRepositoryImpl(get()) }
    single<MasterLoginRepository> { MasterLoginRepositoryImpl(get(), get()) }
    single<MasterMainRepository> { MasterMainRepositoryImpl(get(), get(named("fcm"))) }
    single<SettingRepository> { SettingRepositoryImpl(get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    single<RecommendReviewRepository> { RecommendReviewRepositoryImpl(get()) }
    single<MasterWriteRepository> { MasterWriteRepositoryImpl(get()) }
    single<TourDetailRepository> { TourDetailRepositoryImpl(get(named("tour"))) }
}