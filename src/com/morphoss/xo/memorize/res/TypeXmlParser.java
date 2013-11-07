package com.morphoss.xo.memorize.res;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class TypeXmlParser {
    // We don't use namespaces
    private static final String ns = null;
    private static final String TAG = "TypeXmlParser";
    
   
    public String parse(InputStream in, File file) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readGames(parser, file.getParentFile().getAbsolutePath());
        } finally {
            in.close();
        }
    }
    
    private String readGames(XmlPullParser parser, String path) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "memorize");
        Log.d(TAG, "path: " + path);
        
        String gameType = parser.getAttributeValue(ns, "type");
        return gameType;
    }
}