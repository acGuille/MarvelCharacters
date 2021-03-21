package com.guilleac.marvelcharacters.CharacterList

import com.guilleac.marvelapi.model.MarvelCharacter

interface MarvelCharacterListener {
    fun onCharacterSelected(character: MarvelCharacter)
}