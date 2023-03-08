package android.example.bobo.ituneplayer

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL

class CnnXMLParser(val listener: ParserListener) {
    private val factory = XmlPullParserFactory.newInstance()
    private val parser = factory.newPullParser()
    private val data = mutableListOf<NewsData>()
    private var newsTitle = ""
    private var text = ""

    fun parseURL(url: String) {
        listener.start()
        GlobalScope.launch {
            try {
                val inputStream = URL(url).openStream()
                parser.setInput(inputStream, null)
                var eventType = parser.next()

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    var tagName = parser.name
                    if (tagName.equals(
                            "item",
                            ignoreCase = true
                        ) && eventType == XmlPullParser.START_TAG
                    ) {
                        while (!(tagName.equals(
                                "item",
                                ignoreCase = true
                            ) && eventType == XmlPullParser.END_TAG)
                        ) {
                            when (eventType) {
                                XmlPullParser.TEXT -> text = parser.text
                                XmlPullParser.END_TAG -> if (tagName.equals(
                                        "title",
                                        ignoreCase = true
                                    )
                                ) {
                                    newsTitle = text
                                }
                            }
                            eventType = parser.next()
                            tagName = parser.name
                        }
                        data.add(NewsData(newsTitle))
                        Log.i("title", newsTitle)
                    }
                    eventType = parser.next()
                }
                withContext(Dispatchers.Main) {
                    listener.finish(data)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}