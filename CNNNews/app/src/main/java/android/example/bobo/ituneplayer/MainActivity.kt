package android.example.bobo.ituneplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        CnnXMLParser(object : ParserListener {
            override fun start() {

            }

            override fun finish(songs: List<NewsData>) {
                for (song in songs) {
                    val textView = TextView(this@MainActivity)
                    textView.text = song.title
                    linearLayout.addView(textView)
                }
            }
        }).parseURL(
//            url = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml"
            url = "http://rss.cnn.com/rss/cnn_topstories.rss"
        )
    }
}