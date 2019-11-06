package net.schwiz.marvel.ui.characters_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.schwiz.marvel.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharactersListFragment : Fragment() {

    companion object {
        fun newInstance() = CharactersListFragment()
    }

    private val viewModel: CharactersListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.characters_list_fragment, container, false)
    }


}
