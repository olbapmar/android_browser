package com.example.neevacodechallenge

import android.app.Dialog
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TabsDialogFragment(tabsManager: TabsManager): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.tabs_dialog, container, true)

        view.findViewById<ImageView>(R.id.addTab).setOnClickListener {
            tabsManager.addTab(true)
            dismiss()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.tabsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = TabAdapter(tabsManager, this)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.TabDialogTheme)
    }

    private val tabsManager = tabsManager
}