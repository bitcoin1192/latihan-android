package com.sisalma.movieticketapp

open class movie{
    var title: String = ""
    var genre: String = ""
    var sinopsis: String = ""
}

open class movieDetailData: movie(){
    var photoLink: String = ""
    var ytEmbed: String = ""
}

class movieDataApi: movieDetailData(){
    val serverEndpoint: String = "https://api.movie.sisalma.com/"
    var resultEndpoint: String  = ""
    private fun rangkaiEndpoint(){
        val uriEndpoint = "data/"
        resultEndpoint = serverEndpoint + uriEndpoint
    }

    public fun requestTrending(){
        //Ask for most watched movie
    }

    public fun requestPersonalize(){
        //Ask for personalized movie selection
    }

    public fun requestSort(sortType: Int){
        //Ask for sorted list of movie: A-Z, Z-A, Sort New, Sort Oldest
    }
}
