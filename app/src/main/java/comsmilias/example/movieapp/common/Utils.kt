package comsmilias.example.movieapp.common


object Utils {

    fun removeTagsFromString(text: String): String{
        return text.replace("</*[a-zA-Z]+>".toRegex(),"")
    }



}