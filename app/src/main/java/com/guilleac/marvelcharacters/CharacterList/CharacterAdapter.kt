package com.guilleac.marvelcharacters.CharacterList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.guilleac.marvelapi.model.MarvelCharacter
import com.guilleac.marvelcharacters.R
import com.squareup.picasso.Picasso

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterHolder>() {

    var items: MutableList<MarvelCharacter> = mutableListOf()

    lateinit var listener: MarvelCharacterListener

    class CharacterHolder(val view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_row, parent, false) as ViewGroup
        return CharacterHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val row = holder.view
        val character = items[position]

        val characterName = holder.view.findViewById<AppCompatTextView>(R.id.character_name)
        val characterImage = holder.view.findViewById<AppCompatImageView>(R.id.character_image)
        characterName.text = character.name
        loadCharacterImage(character, characterImage)

        row.setOnClickListener {
            listener.onCharacterSelected(character)
        }
    }

    private fun loadCharacterImage(item: MarvelCharacter, imageView: AppCompatImageView) {
        val path = item.thumbnail.path
        val extension = item.thumbnail.extension
        val typeImage = "portrait_small"
        val url = "$path/$typeImage.$extension"
        Picasso.get().load(url).into(imageView)
    }

    fun addData(listItems: List<MarvelCharacter>) {
        var size = this.items.size
        this.items.addAll(listItems)
        var sizeNew = this.items.size
        notifyItemRangeChanged(size, sizeNew)
    }
}