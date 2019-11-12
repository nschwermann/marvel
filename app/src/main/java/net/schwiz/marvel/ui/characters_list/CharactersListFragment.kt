package net.schwiz.marvel.ui.characters_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import net.schwiz.marvel.R
import net.schwiz.marvel.databinding.CharactersListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class CharactersListFragment : Fragment() {

    companion object {
        fun newInstance() = CharactersListFragment()
    }

    private val viewModel: CharactersListViewModel by viewModel()
    private val binding by lazy {
        CharactersListFragmentBinding.inflate(layoutInflater)
    }
    private val characterAdapter = CharacterAdapter()
    private val channel = BroadcastChannel<CharacterListEvents>(1)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root.also {
            binding.recyclerView.apply {
                adapter = characterAdapter
                layoutManager = GridLayoutManager(context, 2)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
        viewModel.state.observe(viewLifecycleOwner) {
            Timber.d("onState $it")
            characterAdapter.submitList(it.characters)
        }
        viewModel.dispatchEvent(CharacterListEvents.RequestCharacters(null))

    }


}
