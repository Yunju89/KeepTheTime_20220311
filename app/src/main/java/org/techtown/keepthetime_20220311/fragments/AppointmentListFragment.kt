package org.techtown.keepthetime_20220311.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.techtown.keepthetime_20220311.R
import org.techtown.keepthetime_20220311.databinding.FragmentAppointmentListBinding

class AppointmentListFragment : BaseFragment(){

    lateinit var binding : FragmentAppointmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupEvents()
        setValues()


    }

    override fun setupEvents() {

    }

    override fun setValues() {

        binding.txtTest.text = "코틀린에서 문구 바꾸기"



    }

}