package com.mtjin.nomoneytrip.views.profile

import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.rx
import com.kakao.sdk.template.model.*
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.master_write.MasterLetter
import com.mtjin.nomoneytrip.databinding.FragmentProfileBinding
import com.mtjin.nomoneytrip.utils.TAG
import com.mtjin.nomoneytrip.views.community.CommunityAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel: ProfileViewModel by viewModel()
    lateinit var letterAdapter: ProfileMasterLetterAdapter
    override fun init() {
        binding.vm = viewModel
        initAdapter()
        initViewModelCallback()
        requestItems()
    }

    private fun requestItems() {
        viewModel.requestProfile()
        viewModel.requestMyReviews()
        viewModel.requestMasterLetters()
    }

    private fun initAdapter() {
        val tourDiaryAdapter = CommunityAdapter(context = thisContext, recommendClick = {
            viewModel.updateReviewRecommend(it)
        }, productClick = {
            findNavController().navigate(
                ProfileFragmentDirections.actionBottomNav4ToLodgmentDetailFragment(
                    it.product
                )
            )
        }, shareClick = {
            sendKakaoLink(it)
        })
        letterAdapter = ProfileMasterLetterAdapter()

        binding.rvTours.adapter = tourDiaryAdapter
        binding.rvLetters.adapter = letterAdapter
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goProfileEdit.observe(this@ProfileFragment, Observer {
                viewModel.user.value?.let {
                    findNavController().navigate(
                        ProfileFragmentDirections.actionBottomNav4ToProfileEditFragment(it)
                    )
                }
            })

            goSetting.observe(this@ProfileFragment, Observer {
                findNavController().navigate(
                    ProfileFragmentDirections.actionBottomNav4ToSettingFragment()
                )
            })

            clickHeart.observe(this@ProfileFragment, Observer {
                findNavController().navigate(
                    ProfileFragmentDirections.actionBottomNav4ToRecommendReviewFragment()
                )
            })

            userReviewList.observe(this@ProfileFragment, Observer {
                binding.run {
                    if (it.isEmpty()) {
                        val height = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            180f,
                            resources.displayMetrics
                        ).toInt()
                        rvTours.layoutParams.height = height
                        ivNoMyTour.visibility = View.VISIBLE
                        rvTours.visibility = View.INVISIBLE
                    } else {
                        ivNoMyTour.visibility = View.GONE
                        rvTours.visibility = View.VISIBLE
                        rvTours.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
            })

            masterLetterList.observe(this@ProfileFragment, Observer {
                letterAdapter.run {
                    clear()
                    addItems(it as List<MasterLetter>)
                }
            })
        }
    }

    private fun sendKakaoLink(userReview: UserReview) {
        // 메시지 템플릿 만들기 (피드형)
        val defaultFeed = FeedTemplate(
            content = Content(
                title = userReview.product.title,
                description = userReview.review.content,
                imageUrl = userReview.review.image,
                link = Link(
                    mobileWebUrl = "https://play.google.com/store/apps/details?id=com.mtjin.nomoneytrip"
                )
            ), social = Social(
                likeCount = userReview.review.recommendList.size
            ),
            buttons = listOf(
                Button(
                    "앱으로 보기",
                    Link(
                        androidExecParams = mapOf(
                            "key1" to "value1",
                            "key2" to "value2"
                        ) // 내 앱에서 파라미터보낼건 필요없음 (앱 다운로드만 유도할것이다)
                    )
                )
            )
        )
        // 피드 메시지 보내기
        LinkClient.rx.defaultTemplate(thisContext, defaultFeed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ linkResult ->
                Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                startActivity(linkResult.intent)

                // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                Log.w(TAG, "Warning Msg: ${linkResult.warningMsg}")
                Log.w(TAG, "Argument Msg: ${linkResult.argumentMsg}")
            }, { error ->
                showToast(getString(R.string.kakao_link_fail_msg))
                Log.e(TAG, "카카오링크 보내기 실패 ", error)
            })
            .addTo(compositeDisposable)
    }

}