package com.example.evaluation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluation.R
import com.example.evaluation.adapter.DevByteAdapter
import com.example.evaluation.databinding.FragmentMainBinding
import com.example.evaluation.domain.DevByteVideo
import com.example.evaluation.viewmodels.DevByteViewModel


class MainFragment : Fragment() {
    private  var viewModelAdapter: DevByteAdapter? = null


    private val viewModel: DevByteViewModel by lazy {
        val activity = requireNotNull(this.activity) {

        }
        ViewModelProvider(this, DevByteViewModel.Factory(activity.application))
            .get(DevByteViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner, Observer<List<DevByteVideo>> { videos ->
            videos?.apply {
                viewModelAdapter?.videos = videos
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter =viewModelAdapter

//        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = viewModelAdapter
//        }
        return binding.root
    }


}