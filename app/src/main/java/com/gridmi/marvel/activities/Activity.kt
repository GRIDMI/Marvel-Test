package com.gridmi.marvel.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.gridmi.marvel.R
import com.gridmi.marvel.ui.fragment.master.MasterFragment

class Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        if (savedInstanceState == null) commitFragment {
            val fr = MasterFragment()
            it.add(R.id.rootView, fr)
            it.setPrimaryNavigationFragment(fr)
            it.show(fr)
        }

    }

    fun commitFragment(withTransaction:((FragmentTransaction) -> Unit)) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        withTransaction(ft)
        ft.commit()
    }

}