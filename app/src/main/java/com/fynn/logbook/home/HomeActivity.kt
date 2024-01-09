package com.fynn.logbook.home

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.fynn.logbook.R
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.databinding.ActivityHomeBinding
import com.fynn.logbook.util.observeState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {
    @Inject lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btn.setOnClickListener {
            viewModel.sendUiIntent(HomeIntent.ToastInfo("asd"))
        }
        initEvent()
    }

    private fun initEvent() {
        viewModel.uiStateFlow.run {
            observeState(this@HomeActivity, HomeState::str){
                if (!TextUtils.isEmpty(it)) {
                    Toast.makeText(this@HomeActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}