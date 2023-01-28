package comsmilias.example.movieapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("average")
    val average: Double
)