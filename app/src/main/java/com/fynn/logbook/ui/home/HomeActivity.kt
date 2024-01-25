package com.fynn.logbook.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.fynn.logbook.R
import com.fynn.logbook.adapter.HomePageAdapter
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.databinding.ActivityHomeBinding
import com.fynn.logbook.ui.addexperiment.AddExperimentActivity
import com.fynn.logbook.ui.home.fragment.LiveListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(
    ActivityHomeBinding::inflate) {
    @Inject
    lateinit var viewModel: HomeViewModel
    private val list = mutableListOf<Fragment>()
    override fun getViewModel(): BaseViewMolder<*,*> {
        return viewModel
    }

    override fun initView() {
        setOperate(R.drawable.btn_add_blue) {
            intentView(AddExperimentActivity::class.java)
        }
        val livefragment = LiveListFragment()
        livefragment.setViewType(1)
        list.add(livefragment)
        val abandonfragment = LiveListFragment()
        abandonfragment.setViewType(0)
        list.add(abandonfragment)
        binding.viewpager.let {
            it.adapter = HomePageAdapter(this, list)
            it.currentItem = 0
            it.registerOnPageChangeCallback(object : OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {


                }

            })
        }

        binding.llLive.setOnClickListener {
            binding.viewpager.currentItem = 0
        }

        binding.llAbandon.setOnClickListener {
            binding.viewpager.currentItem = 1
        }
    }


    override fun initData() {
    }

    override fun initEvent() {


    }
}