package com.jotangi.greenShop

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jotangi.greenShop.databinding.AppBarMainBinding

abstract class BaseFragment : Fragment() {

    abstract fun getToolBar(): AppBarMainBinding?

    val TAG: String = "(TAG)${javaClass.simpleName}"

    var mActivity: MainActivity? = null
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        try {

            mActivity = (activity as MainActivity)

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private fun setupToolBarBtn(
        imageButton: ImageButton?,
        res: Int?,
        onClick: () -> Unit
    ) {
        imageButton?.apply {

            res?.let {
                setImageResource(it)
            }

            setOnClickListener {
                onClick()
            }
        }
    }

    fun setBnv(index: Int) {
        val view: BottomNavigationView? =
            mActivity?.findViewById(R.id.nav_view)
        val menu = view?.menu
        menu?.getItem(index)?.isChecked = true
    }

    fun setupLoginTitle() {
        getToolBar()?.apply {
            toolNotifyImageButton.visibility = View.GONE
            toolShopCarImageButton.visibility = View.GONE

        }
    }
}