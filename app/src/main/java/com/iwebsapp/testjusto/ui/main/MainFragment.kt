package com.iwebsapp.testjusto.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.iwebsapp.testjusto.R
import com.iwebsapp.testjusto.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        init()
        setUpObservers()
    }

    private fun init() {
        viewModel.getDataUser()
    }

    private fun setUpObservers() {
        viewModel.getData().observe(viewLifecycleOwner, { response ->
            Glide.with(this)
                .load(response.results[0].picture.large)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageUser)

            binding.textNameUser.text = response.results[0].name.title.plus(" ")
                .plus(response.results[0].name.first).plus(" ")
                .plus(response.results[0].name.last)
            binding.textEmail.text = response.results[0].email
            binding.textPhone.text =
                resources.getString(R.string.phone).plus(": ").plus(response.results[0].phone)
            binding.textCell.text =
                resources.getString(R.string.cell).plus(": ").plus(response.results[0].cell)
            binding.textGender.text =
                resources.getString(R.string.gender).plus(": ").plus(response.results[0].gender)
            binding.textLocation.text =
                resources.getString(R.string.street).plus(" ")
                    .plus(response.results[0].location.street.name)
                    .plus(" # ").plus(response.results[0].location.street.number).plus(", ")
                    .plus(resources.getString(R.string.city)).plus(" ")
                    .plus(response.results[0].location.city).plus(", ")
                    .plus(resources.getString(R.string.state)).plus(" ")
                    .plus(response.results[0].location.state).plus(", ")
                    .plus(resources.getString(R.string.country)).plus(" ")
                    .plus(response.results[0].location.country).plus(", ")
                    .plus(resources.getString(R.string.postcode)).plus(" ")
                    .plus(response.results[0].location.postcode)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroy()
    }

}