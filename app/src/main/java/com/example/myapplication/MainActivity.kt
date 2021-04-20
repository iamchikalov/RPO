package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.parser3.Item

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity(), MyListAdapter.OnItemClick {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.contentContainer,ListFragment())
            .commit()

    }

    override fun onItemClick(item: Item){
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainer, DetailsFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }


}