package com.mtjin.nomoneytrip.views.profile_edit

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentProfileEditBinding
import com.mtjin.nomoneytrip.utils.RC_PICK_IMAGE
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileEditFragment :
    BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {
    private val args: ProfileEditFragmentArgs by navArgs()
    private val viewModel: ProfileEditViewModel by viewModel()
    override fun init() {
        binding.vm = viewModel
        processIntent()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            reviseImage.observe(this@ProfileEditFragment, Observer {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, RC_PICK_IMAGE)
            })

            editSuccessMsg.observe(this@ProfileEditFragment, Observer { success ->
                if (success) showToast(getString(R.string.profile_edit_success_msg))
                else showToast(getString(R.string.profile_edit_fail_msg))
            })

            nameDuplicateMsg.observe(this@ProfileEditFragment, Observer {
                showToast(getString(R.string.duplicate_name_exist_msg))
            })

            nameErrMsg.observe(this@ProfileEditFragment, Observer {
                showToast(getString(R.string.name_blank_msg))
            })
        }
    }

    private fun processIntent() {
        viewModel.setUser(args.user)
        viewModel.name.value = args.user.name
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == RC_PICK_IMAGE) {
            data?.data?.let {
                binding.ivImage.setImageURI(it)
                viewModel.imageUri = it
            }
        }
    }
}