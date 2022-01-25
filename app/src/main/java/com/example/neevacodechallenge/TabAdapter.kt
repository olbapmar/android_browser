package com.example.neevacodechallenge

import android.graphics.Typeface.BOLD
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
        val thumbnailView : ImageView

        init {
            textView = view.findViewById(R.id.tabName)
            imageView = view.findViewById(R.id.favIcon)
            closeButton = view.findViewById(R.id.closeButton)
            thumbnailView = view.findViewById(R.id.tabThumbnail)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.browser_tab_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tabsManager.updateThumbnail()

        holder.imageView.setImageBitmap(tabsManager.tabs[position].webView.favicon)
        holder.textView.text = tabsManager.tabs[position].webView.title
        holder.thumbnailView.setImageBitmap(tabsManager.tabs[position].bitmap)

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

        holder.thumbnailView.setOnClickListener {
            tabsManager.selectTab(position)
            dialogFragment.dismiss()
        }

        if(tabsManager.tabs[position] == tabsManager.activeTab) {
            holder.textView.setTypeface(null, BOLD)
        }
    }

    override fun getItemCount(): Int {
        return tabsManager.numberOfTabs()
    }

    val tabsManager: TabsManager = tabManager
    val dialogFragment : DialogFragment = dialog

}