package com.example.neevacodechallenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView

class TabAdapter(tabManager: TabsManager, dialog: TabsDialogFragment): RecyclerView.Adapter<TabAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView
        val closeButton: ImageView

        init {
            textView = view.findViewById(R.id.tabName)
            imageView = view.findViewById(R.id.favIcon)
            closeButton = view.findViewById(R.id.closeButton)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.browser_tab_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageBitmap(tabsManager.webViews[position].favicon)
        holder.textView.text = tabsManager.webViews[position].title
        holder.closeButton.setOnClickListener {
            tabsManager.closeTab(position)
            notifyDataSetChanged()

            if(tabsManager.numberOfTabs() == 1) {
                dialogFragment.dismiss()
            }
        }

        holder.textView.setOnClickListener {
            tabsManager.selectTab(position)
            dialogFragment.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return tabsManager.numberOfTabs()
    }

    val tabsManager: TabsManager = tabManager
    val dialogFragment : DialogFragment = dialog

}