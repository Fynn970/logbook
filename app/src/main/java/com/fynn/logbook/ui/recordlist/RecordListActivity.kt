package com.fynn.logbook.ui.recordlist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fynn.logbook.R
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.base.BaseAdapter
import com.fynn.logbook.base.BaseViewHolder
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.bean.RecordInfo
import com.fynn.logbook.databinding.ActivityRecordListBinding
import com.fynn.logbook.databinding.ItemRecordInfoBinding
import com.fynn.logbook.ui.addrecord.AddRecordActivity
import com.fynn.logbook.ui.home.fragment.LiveListUiState
import com.fynn.logbook.util.DateUtils
import com.fynn.logbook.util.StateTuple1
import com.fynn.logbook.util.hideSoftKey
import com.fynn.logbook.util.observeEvent
import com.fynn.logbook.util.observeState
import com.fynn.logbook.util.twoBtnDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecordListActivity :
    BaseActivity<ActivityRecordListBinding>(ActivityRecordListBinding::inflate) {

    @Inject
    lateinit var viewModel: RecordListViewModel
    private var isEdited: Boolean = false
        set(value) {
            if (value) {
                setOperate(R.drawable.ic_save) {
                    binding.apply {
                        mExperiment.mEarTagNumber = etEarTagNumber.text.toString()
                        mExperiment.mDayInterval = etInterval.text.toString().toInt()
                        mExperiment.animalMerchants = etAnimalMerchants.text.toString()
                        mExperiment.mAnimalState = if (rbLive.isChecked) 1 else 0
                        mExperiment.mUpdateDate = System.currentTimeMillis()
                        viewModel.sendUiIntent(RecordListIntent.UpdateExperiment(mExperiment))
                    }
                }
            } else {
                setOperateGone()
            }
            field = value
        }
    private lateinit var mExperiment: ExperimentInfo
    private lateinit var mOriginExperiment: ExperimentInfo
    private lateinit var mAdapter: BaseAdapter<RecordInfo, ItemRecordInfoBinding>
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (!isEdited) {
                isEdited = true;
            }
        }
    }

    override fun getViewModel(): BaseViewMolder<*, *> {
        return viewModel
    }

    override fun initView() {
        showBack(true)
        val extras = intent.extras
        extras?.let {
            mOriginExperiment = it.getSerializable("experiment") as ExperimentInfo
            mExperiment = mOriginExperiment;
        }
        mAdapter = object : BaseAdapter<RecordInfo, ItemRecordInfoBinding>(
            this,
            mutableListOf(),
            ItemRecordInfoBinding::inflate
        ) {
            override fun convert(
                mContext: Context,
                holder: BaseViewHolder<ItemRecordInfoBinding>,
                t: RecordInfo,
                select_position: Int
            ) {
                holder.vb.tvRecordId.text = t.mRecordNum
                holder.vb.tvRoomName.text = t.mRoomNumber
                holder.vb.tvAnimalWeight.text = "${t.mRecordWeight}"
                holder.vb.tvCreateDate.text = DateUtils.longToString(t.mRecordCreateDate)
                holder.vb.tvUpdateDate.text = DateUtils.longToString(t.mRecordUpdateDate)
            }
        }
        handleExperimentData()
    }

    private fun handleExperimentData() {
        binding.apply {
            etEarTagNumber.setText(mExperiment.mEarTagNumber)
            etInterval.setText("${mExperiment.mDayInterval}")
            etAnimalMerchants.setText(mExperiment.animalMerchants)
            if (mExperiment.mAnimalState == 1) {
                rbLive.isChecked = true
            } else {
                rbDied.isChecked = true
            }
            tvOperateState.setText(if (judgeOperateState()) "达标" else "不达标")
            tvRecordLastTime.setText(DateUtils.longToString(mExperiment.mNewRecordCreateDate))

            tvAddRecord.setOnClickListener {
                if (!judgeOperateState()) {
                    twoBtnDialog(this@RecordListActivity, "状态未达标，是否确定添加") {
                        val bundle = Bundle()
                        bundle.putLong("experimentId", mExperiment.mExperimentId)
                        intentView(AddRecordActivity::class.java, bundle)
                    }
                }
            }
            rvRecord.layoutManager = LinearLayoutManager(this@RecordListActivity)
            rvRecord.adapter = mAdapter

            etEarTagNumber.setOnFocusChangeListener(object : OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if (hasFocus) {
                        etEarTagNumber.addTextChangedListener(textWatcher)
                    } else {
                        etEarTagNumber.removeTextChangedListener(textWatcher)
                    }
                }
            })
            etInterval.setOnFocusChangeListener(object : OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if (hasFocus) {
                        etInterval.addTextChangedListener(textWatcher)
                    } else {
                        etInterval.removeTextChangedListener(textWatcher)
                    }
                }
            })
            etAnimalMerchants.setOnFocusChangeListener(object : OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if (hasFocus) {
                        etAnimalMerchants.addTextChangedListener(textWatcher)
                    } else {
                        etAnimalMerchants.removeTextChangedListener(textWatcher)
                    }
                }
            })
            rgRadioGroup.setOnCheckedChangeListener(object : OnCheckedChangeListener {
                override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                    if (!isEdited) {
                        isEdited = true
                    }
                }
            })
        }
    }

    override fun backClickListener() {
        super.backClickListener()
    }

    override fun onResume() {
        super.onResume()

        if (mExperiment.mExperimentId != -1L) {
            viewModel.sendUiIntent(RecordListIntent.GetExperimentById(mExperiment.mExperimentId))
            viewModel.sendUiIntent(RecordListIntent.GetRecordListByExperimentId(mExperiment.mExperimentId))
        }
    }

    override fun initData() {
    }

    override fun initEvent() {
        viewModel.dataSharedFlow.run {
            observeEvent(
                this@RecordListActivity,
                viewModel.viewModelScope,
                RecordListState::experiment
            ) {
                if (it != null && !TextUtils.isEmpty(it.mEarTagNumber)) {
                    it.let {
                        isEdited = false
                        val focusedView = currentFocus
                        if (focusedView is EditText) {
                            focusedView.clearFocus()
                            hideSoftKey(this@RecordListActivity, focusedView)
                        }
                        mOriginExperiment = it
                        mExperiment = mOriginExperiment
                        handleExperimentData()
                    }

                }
            }
        }
    }


    override fun initState() {
        viewModel.uiStateFlow.run {
            observeState(this@RecordListActivity, RecordListState::recordList) {
                mAdapter.updateData(it)
            }
        }
    }

    private fun judgeOperateState(): Boolean {
        return DateUtils.campareLongDate(
            mExperiment.mNewRecordCreateDate,
            mExperiment.mDayInterval
        )
    }

}
