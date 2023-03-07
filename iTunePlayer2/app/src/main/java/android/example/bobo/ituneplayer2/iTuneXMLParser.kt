package android.example.bobo.ituneplayer2

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL

class iTuneXMLParser {
    private val factory = XmlPullParserFactory.newInstance()
    private val parser = factory.newPullParser()
    private val data = mutableListOf<SongData>()
    private var songTitle = ""
    private var text = ""
    fun parseURL(url:String) {
        GlobalScope.launch {
        try {
        val inputStream = URL(url).openStream()
        parser.setInput(inputStream, null)
        var eventType = parser.next()


        while(eventType != XmlPullParser.END_DOCUMENT) {
             var tagName = parser.name
             if (tagName.equals ("entry", ignoreCase = true) && eventType == XmlPullParser.START_TAG){
                 while (!(tagName.equals( "entry", ignoreCase = true) && eventType == XmlPullParser.END_DOCUMENT)) {
                    when(eventType) {
                        XmlPullParser.TEXT -> text = parser.text
                        XmlPullParser.END_TAG -> if (tagName.equals("title", ignoreCase = true)) {
                            songTitle = text
                        }
                    }
                     eventType = parser.next()
                     tagName = parser.name
                 }
                 data.add(SongData(songTitle))
                 Log.i("title", songTitle)
              }
            }

    } catch (e: Throwable){
        e.printStackTrace()
     }
        }
    }
}