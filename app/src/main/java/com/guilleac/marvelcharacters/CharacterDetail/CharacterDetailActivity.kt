package com.guilleac.marvelcharacters.CharacterDetail

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.guilleac.marvelcharacters.R

class CharacterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        loadFragment()
    }

    private fun loadFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = CharacterDetailFragment()
        fragmentTransaction.replace(R.id.detail_character_fragment_holder, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}