package com.goup.anmolhira

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import papayacoders.instastory.models.TrayModel


class ProfileAdapter(private val userInterface: UserInterface) : ListAdapter<TrayModel, ProfileAdapter.ProfileViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<TrayModel>() {
        override fun areItemsTheSame(oldItem: TrayModel, newItem: TrayModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TrayModel, newItem: TrayModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.user_image)
        val textView = view.findViewById<TextView>(R.id.profile_user_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.profile_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
       val item = getItem(position)
        //user name
        holder.textView.text = item.user.fullname
        //user image
        Picasso.get().load(item.user.profilepicurl).into(holder.imageView)
        holder.imageView.setOnClickListener{
            userInterface.userInterfaceClick(position,item)
        }
    }
}