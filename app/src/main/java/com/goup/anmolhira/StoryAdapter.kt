package com.goup.anmolhira

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goup.anmolhira.util.DirectoryUtils
import com.goup.anmolhira.util.Utils
import com.squareup.picasso.Picasso
import okio.Utf8
import papayacoders.instastory.models.ItemModel

class StoryAdapter : ListAdapter<ItemModel, StoryAdapter.StoryViewHolder>(DiffUtils()) {

    class DiffUtils : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class StoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.story_image)
        val download = view.findViewById<ImageView>(R.id.download_story)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.story_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        //get stories
        val item = getItem(position)
        Picasso.get().load(item.imageversions2.candidates[0].url).into(holder.imageView)

        holder.download.setOnClickListener {
            //Checking media type and download Accordingly
            if (item.mediatype == 2) {
                val url = item.videoversions[0].url
                Utils.startDownload(
                    url, DirectoryUtils.DIRECTORY, holder.itemView.context,
                    "Insta_Story_" + System.currentTimeMillis() + ".mp4"
                )

            } else {
                val url2 = item.imageversions2.candidates[0].url
                Utils.startDownload(
                    url2, DirectoryUtils.DIRECTORY, holder.itemView.context,
                    "Insta_Story_" + System.currentTimeMillis() + ".png"
                )
            }

        }
    }
}