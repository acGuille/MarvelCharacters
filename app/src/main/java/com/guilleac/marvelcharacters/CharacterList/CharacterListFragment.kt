package com.guilleac.marvelcharacters.CharacterList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guilleac.marvelapi.model.MarvelCharacter
import com.guilleac.marvelapi.model.ResponseHolder
import com.guilleac.marvelcharacters.CharacterDetail.CharacterDetailActivity
import com.guilleac.marvelcharacters.CharacterDetail.CharacterDetailFragment
import com.guilleac.marvelcharacters.MainViewModel
import com.guilleac.marvelcharacters.R
import com.guilleac.marvelcharacters.base.BaseEvent


class CharacterListFragment : Fragment(), MarvelCharacterListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var characterViewAdapter: CharacterAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mainViewModel: MainViewModel

    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.characterList.observe(this, getCharacterObserver())
        mainViewModel.observeEvents(this, createEventObserver())
        mainViewModel.getCharacters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        config(view)
    }

    private fun getCharacterObserver(): Observer<ResponseHolder> {
        return Observer {
            characterViewAdapter.addData(it?.data?.results ?: listOf())
        }
    }

    private fun createEventObserver(): Observer<BaseEvent>{
        return Observer {
            when(it) {
                BaseEvent.GenericConnectionError -> Toast.makeText(context, context?.getString(R.string.generic_error), Toast.LENGTH_LONG).show()
                else -> Log.d("EventObserver","Uncontrolled event")
            }
        }
    }

    private fun config(view: View) {
        characterViewAdapter = CharacterAdapter()
        characterViewAdapter.listener = this
        linearLayoutManager = LinearLayoutManager(context ?: requireContext())
        recyclerView = view.findViewById(R.id.character_recycler)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = characterViewAdapter

        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                getMoreItems()
            }
        })
    }

    fun getMoreItems() {
        isLoading = false
        mainViewModel.getCharacters(characterViewAdapter.itemCount)
    }

    private fun openCharacterDetail(character: MarvelCharacter) {
        val bundle = Bundle()
        val intent = Intent(context!!, CharacterDetailActivity::class.java)
        bundle.putSerializable("CHARACTER_DETAIL_DATA", character)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onCharacterSelected(character: MarvelCharacter) {
        openCharacterDetail(character)
    }
}