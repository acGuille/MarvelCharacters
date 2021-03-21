package com.guilleac.marvelcharacters.CharacterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.guilleac.marvelapi.model.MarvelCharacter
import com.guilleac.marvelcharacters.MainViewModel
import com.guilleac.marvelcharacters.R
import com.squareup.picasso.Picasso

class CharacterDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        val bundle = activity?.intent?.extras
        val character = bundle?.getSerializable("CHARACTER_DETAIL_DATA") as MarvelCharacter?
        if (character != null) {
            loadCharacterInfo(character)
        }
    }

    private fun loadCharacterInfo(character: MarvelCharacter) {
        val characterImage = view?.findViewById<AppCompatImageView>(R.id.character_image)
        val characterName = view?.findViewById<AppCompatTextView>(R.id.character_name)
        val characterDescription = view?.findViewById<AppCompatTextView>(R.id.character_description)

        val path = character.thumbnail.path
        val extension = character.thumbnail.extension
        val typeImage = "landscape_large"
        val url = "$path/$typeImage.$extension"

        Picasso.get().load(url).into(characterImage)

        characterName?.text = character.name
        characterDescription?.text = character.description
    }
}