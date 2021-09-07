package com.example.newsapp.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemHomeBinding
import com.example.newsapp.model.Article
import com.example.newsapp.viewModel.HomeViewModel
import com.squareup.picasso.Picasso
import java.util.*

class HomeAdapter(private var homeList: ArrayList<Article>, private val homeVM: HomeViewModel) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int

    ): HomeViewHolder {
        return HomeViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    fun filterList(filteredList: ArrayList<Article>) {
        homeList = filteredList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.sourceNameTxt.text = homeList[position].source?.name
     //   Log.i("i", "@@@@@@@@@" + homeList[position].author)
        holder.binding.titleTxt.text = homeList[position].title
        Picasso.get().load(homeList[position].urlToImage)
            .into(holder.binding.imageView)
        holder.binding.card.setOnClickListener({
            homeVM.navToDetails(homeList[position])
        })

    }

    fun updateHome(newFavoriteList: List<Article>) {
        homeList.clear()
        homeList.addAll(newFavoriteList)
        notifyDataSetChanged()
    }


    inner class HomeViewHolder(val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root)


}