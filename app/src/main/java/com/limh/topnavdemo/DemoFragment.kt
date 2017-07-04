package com.limh.topnavdemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Function:
 * author: limh
 * time:2017/7/4
 */
class DemoFragment : Fragment() {
    lateinit var txtTitle:TextView
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_demo, container, false)
        txtTitle = view.findViewById(R.id.txt)
        txtTitle.text = arguments.getString("title")
        return view
    }

}