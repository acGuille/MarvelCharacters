package com.guilleac.marvelcharacters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.guilleac.marvelcharacters.CharacterList.CharacterListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment()
    }

    private fun loadFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = CharacterListFragment()
        fragmentTransaction.replace(R.id.main_holder, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}