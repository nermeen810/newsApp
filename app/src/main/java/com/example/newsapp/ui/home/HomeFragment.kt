package com.example.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.model.Article
import com.example.newsapp.viewModel.HomeViewModel
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var homeList: List<Article>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeAdapter = HomeAdapter(arrayListOf(), homeViewModel)
        binding.shimmer.startShimmerAnimation()
        binding.homeRecycle.visibility = View.GONE
        setUpUI()
        observeData()
    }

    private fun setUpUI() {
        binding.homeRecycle.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    filterArticle(query)
                }
                return false
            }
        })
    }

    private fun observeData() {
        observeNetwork()
        observeArticles()
        observeNavToDetails()

    }

    private fun observeNetwork() {
        homeViewModel.isOnline()
        homeViewModel.isOnline.observe(viewLifecycleOwner, {
            it?.let { it1 ->
                if (!it1) {
                    binding.noInternet.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun observeArticles() {
        homeViewModel.fetchData()
        homeViewModel.articles.observe(viewLifecycleOwner, {
            it?.let {
                homeAdapter.updateHome(it)
                homeList = it
                binding.homeRecycle.visibility = View.VISIBLE
                binding.shimmer.visibility = View.GONE
            }
        })
    }

    private fun observeNavToDetails() {
        homeViewModel.navToDetails.observe(viewLifecycleOwner, {
            it?.let { it1 ->
                navigate(it1)
            }
        })
    }

    private fun navigate(article: Article) {
        val action = HomeFragmentDirections.actionFromHomeToDetails(article)
        findNavController().navigate(action)

    }

    fun filterArticle(text: String) {
        val filteredList: ArrayList<Article> = ArrayList()
        if (homeList.isNotEmpty()) {
            for (item in homeList) {
                if (item.source?.name?.lowercase(Locale.getDefault())?.contains(text.lowercase(Locale.getDefault())) == true) {
                    filteredList.add(item)
                }
            }
            homeAdapter.filterList(filteredList)
        }
    }

    override fun onResume() {
        super.onResume()
        observeArticles()
        observeNetwork()
    }


}