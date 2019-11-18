package net.schwiz.marvel.ui

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import coil.api.load
import net.schwiz.marvel.R
import net.schwiz.marvel.data.vo.Image
import kotlin.random.Random
import kotlin.random.nextInt

@BindingAdapter(value = ["image", "size"])
fun loadImage(view : ImageView, image : Image?, size : String) {
   val randomImage =  Random.nextInt(1..4).let { random ->
        when(random) {
            1 -> AppCompatResources.getDrawable(view.context, R.drawable.ic_hero1)
            2 -> AppCompatResources.getDrawable(view.context, R.drawable.ic_hero2)
            3 -> AppCompatResources.getDrawable(view.context, R.drawable.ic_hero3)
            else -> AppCompatResources.getDrawable(view.context, R.drawable.ic_hero4)
        }
    }
    if(image == null || image.path.contains("image_not_available", ignoreCase = false)){
        view.load(randomImage){
            crossfade(true)
        }
    } else {
        view.load("${image.path}/${size}.${image.extension}"){
            crossfade(true)
            placeholder(randomImage)
            error(randomImage)
        }
    }
}