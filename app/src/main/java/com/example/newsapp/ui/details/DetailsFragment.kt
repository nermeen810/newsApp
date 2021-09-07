package com.example.newsapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentDetailsBinding
import com.example.newsapp.details.DetailsViewModel
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {
    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        init()
        return binding.root
    }

    private fun init() {
        observeToHome()
        setUpUI()


    }

    private fun observeToHome() {
        detailsViewModel.navToHome.observe(viewLifecycleOwner, {
            it?.let { it1 ->
                if (it1) {
                    navigate()
                }
            }
        })

    }

    private fun setUpUI() {
        val articleParcel = args.article
        binding.sourceNameTxt.text = articleParcel.source?.name
        binding.titleTxt.text = articleParcel.title
        binding.descriptionTxt.text = articleParcel.description
        Picasso.get().load(articleParcel.urlToImage)
            .into(binding.img)
        binding.backToNews.setOnClickListener({
            detailsViewModel.navToHome()
        })
    }


    private fun navigate() {
        val action = DetailsFragmentDirections.actionFromDetailsToHome()
        findNavController().navigate(action)
    }
}