package com.zjl.rengwuxian.kotlindemo.fargment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.zjl.rengwuxian.kotlindemo.R
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_main, container, false)

        view.materialEditTextButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_materialEditTextFragment)
        }

        return view
    }
}
