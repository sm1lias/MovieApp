package comsmilias.example.movieapp.data.remote.dto


import com.google.gson.annotations.SerializedName
import comsmilias.example.movieapp.domain.model.Movie

data class MovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Image,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Rating,
    @SerializedName("summary")
    val summary: String
)

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        name = name,
        imageUrlShort = image.medium,
        imageUrl = image.original,
        rating = rating.average,
        description = summary
    )
}