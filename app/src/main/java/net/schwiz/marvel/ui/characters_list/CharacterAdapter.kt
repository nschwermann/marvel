package net.schwiz.marvel.ui.characters_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.schwiz.marvel.data.vo.Character
import net.schwiz.marvel.databinding.CharacterListItemBinding
import timber.log.Timber

class CharacterAdapter : PagedListAdapter<Character, CharacterViewHolder>(CharacterCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(CharacterListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.run {
            holder.bind(this)
        }
    }
}

class CharacterViewHolder(private val binding : CharacterListItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(character : Character){
        binding.run {
            this.character = character
            clickListener = View.OnClickListener { Timber.i("$character clicked") }
            executePendingBindings()
        }
    }
}

object CharacterCallback : DiffUtil.ItemCallback<Character>(){

    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}
