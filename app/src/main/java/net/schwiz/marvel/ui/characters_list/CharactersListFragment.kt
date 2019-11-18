package net.schwiz.marvel.ui.characters_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.channels.BroadcastChannel
import net.schwiz.marvel.databinding.CharactersListFragmentBinding
import net.schwiz.marvel.domain.DomainError
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersListFragment : Fragment() {


    private val viewModel: CharactersListViewModel by viewModel()
    private val binding by lazy {
        CharactersListFragmentBinding.inflate(layoutInflater)
    }
    private val characterAdapter = CharacterAdapter()

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
        viewModel.state.observe(viewLifecycleOwner) {
            characterAdapter.submitList(it.characters)
            it.fetchError?.let { handleError(it) }
        }
        viewModel.dispatchEvent(CharacterListEvents.RequestCharacters(null))

    }

    private fun handleError(domainError: DomainError){
        if(domainError.message != null) Snackbar.make(binding.root, domainError.message, Snackbar.LENGTH_LONG).show()
        if(domainError.cause != null) domainError.cause.printStackTrace()
    }

}
