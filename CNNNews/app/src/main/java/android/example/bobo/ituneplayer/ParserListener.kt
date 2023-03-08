package android.example.bobo.ituneplayer

interface ParserListener {
    fun start()
    fun finish(songs: List<NewsData>)
}