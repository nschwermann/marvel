package net.schwiz.marvel.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.internal.nullable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import net.schwiz.marvel.data.vo.Image
import net.schwiz.marvel.data.vo.MarvelUrl
import net.schwiz.marvel.data.vo.ResourceList

object Converters{

    private val kJson = Json(JsonConfiguration.Stable)

    @TypeConverter @JvmStatic fun imageToString(image : Image?) : String? {
        return kJson.stringify(Image.serializer().nullable, image)
    }

    @TypeConverter @JvmStatic fun stringToImage(json : String?) : Image? {
        return json?.let {
            kJson.parse(Image.serializer().nullable, it)
        }
    }

    @TypeConverter @JvmStatic fun imagesToString(images : List<Image>?) : String? {
        return images?.let{kJson.stringify(Image.serializer().list, images)}
    }

    @TypeConverter @JvmStatic fun stringToImages(json : String?) : List<Image>?{
        return json?.run{kJson.parse(Image.serializer().list, json) }
    }

    @TypeConverter @JvmStatic fun resourceListToString(resourceList: ResourceList?) : String?{
        return kJson.stringify(ResourceList.serializer().nullable, resourceList)
    }

    @TypeConverter @JvmStatic fun stringToResourceList(json : String?) : ResourceList?{
        return json?.let {
            kJson.parse(ResourceList.serializer(), json)
        }
    }

    @TypeConverter @JvmStatic fun marvelUrlsToString(list : List<MarvelUrl>?) : String?{
        return list?.let{kJson.stringify(MarvelUrl.serializer().list, list)}
    }

    @TypeConverter @JvmStatic fun stringToMarvelUrls(json : String?) : List<MarvelUrl>?{
        return json?.let {
            kJson.parse(MarvelUrl.serializer().list, it)
        }
    }
}