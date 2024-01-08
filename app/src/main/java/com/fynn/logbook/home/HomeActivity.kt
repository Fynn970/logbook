package com.fynn.logbook.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.fynn.logbook.R
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.databinding.ActivityHomeBinding
import com.fynn.logbook.util.observeState
import com.fynn.logbook.vm.HomeOldViewModel
import com.fynn.logbook.vm.HomeUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {
    lateinit var tvText: TextView
    val viewModel by viewModels<HomeViewModel>()
    val viewOldModel by viewModels<HomeOldViewModel>()
    lateinit var mBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        tvText = findViewById(R.id.tv_text)
        tvText.setOnClickListener {
            viewModel.sendUiIntent(HomeIntent.ToastInfo("asd"))
//            viewOldModel.btnClick()
        }
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        initEvent()

//        viewOldModel.uiState.run {
//            observeState(this@HomeActivity, HomeUiState::clickString){
//                tvText.text = it
//            }
//        }
    }

    private fun initEvent() {
        viewModel.uiStateFlow.run {
//        lifecycleScope.launch {
//            viewModel.uiStateFlow.map {
//                it.str
//            }.distinctUntilChanged().collect{
//                Toast.makeText(this@HomeActivity, it, Toast.LENGTH_SHORT).show()
//
//            }
//        }

            observeState(this@HomeActivity, HomeState::str){
                Toast.makeText(this@HomeActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }


}