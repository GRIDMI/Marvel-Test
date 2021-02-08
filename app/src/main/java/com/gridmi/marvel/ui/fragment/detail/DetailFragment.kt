package com.gridmi.marvel.ui.fragment.detail

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gridmi.marvel.R

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fm_detail, container, false)

        val comicsL = rootView.findViewById<LinearLayout>(R.id.comicsL)
        val storiesL = rootView.findViewById<LinearLayout>(R.id.storiesL)
        val eventsL = rootView.findViewById<LinearLayout>(R.id.eventsL)
        val seriesL = rootView.findViewById<LinearLayout>(R.id.seriesL)

        // update ui
        viewModel.getComics().forEach { showItem(comicsL, it.name!!) }
        viewModel.getStories().forEach { showItem(storiesL, it.name!!) }
        viewModel.getEvents().forEach { showItem(eventsL, it.name!!) }
        viewModel.getSeries().forEach { showItem(seriesL, it.name!!) }

        if (comicsL.isEmpty()) showItem(comicsL, "No comics...")
        if (storiesL.isEmpty()) showItem(storiesL, "No stories...")
        if (eventsL.isEmpty()) showItem(eventsL, "No events...")
        if (seriesL.isEmpty()) showItem(seriesL, "No series...")

        return rootView

    }

    private fun showItem(parent:ViewGroup, text:String) {
        val textView = TextView(parent.context)
        textView.text = text
        val p = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, parent.context.resources.displayMetrics).toInt()
        textView.setPadding(p, p, p, p)
        parent.addView(textView)
    }

}