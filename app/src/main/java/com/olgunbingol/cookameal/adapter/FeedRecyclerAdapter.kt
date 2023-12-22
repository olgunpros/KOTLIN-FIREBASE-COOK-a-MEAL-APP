package com.olgunbingol.cookameal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olgunbingol.cookameal.databinding.RecyclerRowBinding
import com.olgunbingol.cookameal.model.Tarif
import com.squareup.picasso.Picasso

class FeedRecyclerAdapter(private val tarifList : ArrayList<Tarif>) : RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {


    class PostHolder (val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
       val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)

    }

    override fun getItemCount(): Int {
        return tarifList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.yemekadiText.text = tarifList.get(position).ad
        holder.binding.tarifadiText.text = tarifList.get(position).tarif
        Picasso.get().load(tarifList.get(position).downloadUrl).into(holder.binding.recyclerImageView)

    }
}