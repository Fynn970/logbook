package com.fynn.logbook.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.fynn.logbook.util.observeState
import com.fynn.logbook.util.showToast
import com.fynn.logbook.view.LoadingDialog

abstract class BaseFragment<VB: ViewBinding>(val inflater: (LayoutInflater) -> VB) : Fragment() {


    private lateinit var _binding: VB
    private lateinit var dialog: LoadingDialog
    private var _viewModel: BaseViewMolder<*,*>? = null
    protected val binding: VB
        get() = requireNotNull(_binding){
            "The property of binding has been destroyed."
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflater(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = getViewModel()
        dialog = LoadingDialog()
        initView()
        initData()
        initLoadEvent()
        initEvent()
    }


    abstract fun getViewModel(): BaseViewMolder<*,*>?

    abstract fun initView()
    abstract fun initData()
    abstract fun initEvent()

    fun showLoading(){
        if (dialog.notShow()) {
            dialog.show(activity)
        }
    }

    fun hideLoading(){
        if (!dialog.notShow()){
            dialog.dismiss()
        }
    }

    fun <T> intentView(classa: Class<T>, bundle: Bundle = Bundle()){
        val intent = Intent(requireContext(), classa)
        intent.putExtras(bundle)
        startActivity(intent)
    }


    open fun refreshEnd(){

    }

    private fun initLoadEvent(){
        _viewModel?.let {vm->
            vm.loadUiIntentFlow.run {
            observeState(viewLifecycleOwner) {
                when (it) {
                    is LoadUIState.Loading -> {
                        if (it.isShow) {
                            showLoading()
                        } else {
                            hideLoading()
                            refreshEnd()
                        }
                    }

                    is LoadUIState.onError -> {
                        showToast(it.errorMsg)
                    }
                }
            } }
        }
    }
}