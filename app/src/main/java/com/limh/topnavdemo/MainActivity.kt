package com.limh.topnavdemo

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.limh.readers.adapter.FragmentAdapter

class MainActivity : AppCompatActivity() {
    lateinit var lineNav:RadioGroup
    lateinit var vpRadio:ViewPager
    lateinit var scroll:HorizontalScrollView
    private val titles = arrayOf("人物", "人生", "心灵", "情感", "成长", "处世", "视野", "美文", "青春",
            "生活", "智慧", "乐活")
    private lateinit var radioIds: MutableMap<String, Int>
    private lateinit var radioNames: MutableMap<Int, String>
    private lateinit var fragments: MutableList<Fragment>

    private lateinit var activity:Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lineNav = findViewById(R.id.line_top)
        vpRadio = findViewById(R.id.vp_radio)
        scroll = findViewById(R.id.hor_scroll)
        activity=this
        initViews()
    }

    fun initViews() {
        //设置导航栏
        val wm = this.windowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val params = LinearLayout.LayoutParams((width / 4.5).toInt(), ViewGroup.LayoutParams.MATCH_PARENT)
        params.setMargins(10, 0, 0, 0)
        radioIds = mutableMapOf()
        radioNames = mutableMapOf()
        for (item in titles) {
            val itemView = LayoutInflater.from(this).inflate(R.layout.radio_top_nav, null) as RadioButton
            itemView.text = item
            itemView.layoutParams = params
            itemView.id = item.hashCode()
            //保存id 用来viewPager翻页时更新radioGroup选中状态
            radioIds.put(item, itemView.id)
            //保存title 用来radioGroup点击的时候更新vp页面
            radioNames.put(itemView.id, item)
            lineNav.addView(itemView)
        }
        lineNav.invalidate()
        (lineNav.getChildAt(0) as RadioButton).isChecked = true
        lineNav.setOnCheckedChangeListener { _, checkedId ->
            vpRadio.currentItem = titles.indexOf(radioNames[checkedId])
        }
        //设置页面
        fragments = mutableListOf()
        for (item in titles) {
            val b = Bundle()
            b.putString("title", item)
            val itemFragment = DemoFragment()
            itemFragment.arguments = b
            fragments.add(itemFragment)
        }
        vpRadio.adapter = FragmentAdapter(supportFragmentManager, fragments)
        vpRadio.currentItem = 0
        vpRadio.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
                if (p0 == 2) {
                    radioIds[titles[vpRadio.currentItem]]?.let { lineNav.check(it) }

                    //更新scroll滑动位置
                    val rb: RadioButton? = radioIds[titles[vpRadio.currentItem]]?.let { findViewById(it)}
                    val scrollX = scroll.scrollX
                    val left = rb!!.left
                    val leftScreen = left - scrollX
                    scroll.smoothScrollBy(leftScreen - (width / 2)+rb.width/2, 0)
                }
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
            }
        })
    }
}
