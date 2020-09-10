package com.mtjin.nomoneytrip.module

import com.mtjin.nomoneytrip.data.community.source.CommunityRepository
import com.mtjin.nomoneytrip.data.community.source.CommunityRepositoryImpl
import com.mtjin.nomoneytrip.data.email_login.source.EmailLoginRepository
import com.mtjin.nomoneytrip.data.email_login.source.EmailLoginRepositoryImpl
import com.mtjin.nomoneytrip.data.home.source.HomeRepository
import com.mtjin.nomoneytrip.data.home.source.HomeRepositoryImpl
import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepository
import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepositoryImpl
import com.mtjin.nomoneytrip.data.login.source.LoginRepository
import com.mtjin.nomoneytrip.data.login.source.LoginRepositoryImpl
import com.mtjin.nomoneytrip.data.profile.soruce.ProfileRepository
import com.mtjin.nomoneytrip.data.profile.soruce.ProfileRepositoryImpl
import com.mtjin.nomoneytrip.data.reservation.source.ReservationRepository
import com.mtjin.nomoneytrip.data.reservation.source.ReservationRepositoryImpl
import com.mtjin.nomoneytrip.data.reservation_history.source.ReservationHistoryRepository
import com.mtjin.nomoneytrip.data.reservation_history.source.ReservationHistoryRepositoryImpl
import com.mtjin.nomoneytrip.data.reservation_phase_first.source.ReservationPhaseFirstRepository
import com.mtjin.nomoneytrip.data.reservation_phase_first.source.ReservationPhaseFirstRepositoryImpl
import com.mtjin.nomoneytrip.data.tour_no_history.source.TourNoHistoryRepository
import com.mtjin.nomoneytrip.data.tour_no_history.source.TourNoHistoryRepositoryImpl
import com.mtjin.nomoneytrip.data.tour_write.source.TourWriteRepository
import com.mtjin.nomoneytrip.data.tour_write.source.TourWriteRepositoryImpl
import com.mtjin.nomoneytrip.utils.REVIEW
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<LocalPageRepository> { LocalPageRepositoryImpl(get(named("tour")), get()) }
    single<EmailLoginRepository> { EmailLoginRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<TourWriteRepository> { TourWriteRepositoryImpl(get(), get(named(REVIEW))) }
    single<ReservationRepository> { ReservationRepositoryImpl(get()) }
    single<ReservationHistoryRepository> { ReservationHistoryRepositoryImpl(get()) }
    single<ReservationPhaseFirstRepository> { ReservationPhaseFirstRepositoryImpl(get()) }
    single<CommunityRepository> { CommunityRepositoryImpl(get()) }
    single<TourNoHistoryRepository> { TourNoHistoryRepositoryImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
}