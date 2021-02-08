package com.gridmi.marvel.ui.fragment.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gridmi.marvel.R
import com.gridmi.marvel.activities.Activity
import com.gridmi.marvel.enteties.CharacterEntity
import com.gridmi.marvel.ui.fragment.detail.DetailFragment
import com.gridmi.marvel.ui.fragment.detail.DetailViewModel

class MasterFragment : Fragment() {

    private val masterModel:MasterViewModel by activityViewModels()
    private val detailModel:DetailViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fm_master, container, false)

        val recycler:RecyclerView = rootView.findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = CharacterAdapter(this, masterModel.items)

        rootView.findViewById<Button>(R.id.load_more).setOnClickListener {
            masterModel.getMoreItems()
        }

        return rootView

    }

    fun onItemSelected(item:CharacterEntity) {

        detailModel.item.value = item

        (requireActivity() as? Activity)?.let { activity ->
            activity.commitFragment { ft ->
                ft.replace(R.id.rootView, DetailFragment())
                ft.addToBackStack(null)
            }
            activity.title = item.name
        }

    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as? Activity)?.let {
            it.title = "Marvel Characters Example"
        }
    }

}

class CharacterItem(val view:View) : RecyclerView.ViewHolder(view) {
    val avatar: ImageView = view.findViewById(R.id.avatar)
    val name: TextView = view.findViewById(R.id.name)
    val description: TextView = view.findViewById(R.id.description)
}

class CharacterAdapter(
    private val masterFragment:MasterFragment,
    mld: MutableLiveData<List<CharacterEntity>>
) : RecyclerView.Adapter<CharacterItem>() {

    private var items:ArrayList<CharacterEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItem {
        return CharacterItem(LayoutInflater.from(parent.context).inflate(R.layout.fm_master_item, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterItem, position: Int) {

        Glide.with(holder.view.context).load(items[position].avatar).centerCrop().into(holder.avatar)
        holder.name.text = items[position].name
        holder.description.text = items[position].description?.ifEmpty { "..." } ?: "..."

        holder.view.setOnClickListener {
            masterFragment.onItemSelected(items[position])
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    init {
        mld.observe(masterFragment.viewLifecycleOwner) {
            items.clear()
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

}
