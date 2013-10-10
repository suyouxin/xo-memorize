package com.morphoss.xo.memorize.res;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.morphoss.xo.memorize.obj.MMedia;
import com.morphoss.xo.memorize.obj.MStr;
import com.morphoss.xo.memorize.obj.MemoryObj;

import android.util.Log;
import android.util.Xml;

public class GameXmlParser {
    // We don't use namespaces
    private static final String ns = null;
    private static final String TAG = "GameXmlParser";
    
   
    public GameInfo parse(InputStream in, File file) throws XmlPullParserException, IOException {
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
    
    private GameInfo readGames(XmlPullParser parser, String path) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "memorize");
        Log.d(TAG, "path: " + path);
        
        String gameName = parser.getAttributeValue(ns, "name");
        boolean isDivided = parser.getAttributeValue(ns, "divided").equals("1") ? true : false;
        
        if (gameName == null)
            return null;
        
        GameInfo gameInfo = new GameInfo();
        gameInfo.name = gameName;
        gameInfo.divided = isDivided;
        gameInfo.objs = new ArrayList<MemoryObj>();
        
        Log.d(TAG, "gameName: " + gameInfo.name);
        Log.d(TAG, "isDivided: " + gameInfo.divided);
        // parser.next();
        // Log.d(TAG, "name: " + parser.getName());
        int type;
        
        while ((type = parser.next()) != XmlPullParser.END_DOCUMENT) {
            if (type == XmlPullParser.START_TAG) {
                String name = parser.getName();
                // Log.d(TAG, "name: " + name);
                if (name.equals("pair")) {
                    String charA = parser.getAttributeValue(ns, "achar");
                    String charB = parser.getAttributeValue(ns, "bchar");
                    if (charA != null && charB != null) {
                        MemoryObj objA = new MStr(charA);
                        MemoryObj objB = new MStr(charB);
                        objA.setPairedObj(objB);
                        objB.setPairedObj(objA);
                        gameInfo.objs.add(objA);
                    }

                    String imgA = parser.getAttributeValue(ns, "aimg");
                    String imgB = parser.getAttributeValue(ns, "bimg");
                    String sndA = parser.getAttributeValue(ns, "asnd");
                    String sndB = parser.getAttributeValue(ns, "bsnd");
                    
                    String imgAPath = null;
                    String imgBPath = null;
                    if (imgA != null && imgB != null) {
                        imgAPath = path + "/images/" + imgA;
                        imgBPath = path + "/images/" + imgB;
                    }
                    
                    String sndAPath = null;
                    String sndBPath = null;
                    if (sndA != null && sndB != null) {
                        sndAPath = path + "/sounds/" + sndA;
                        sndBPath = path + "/sounds/" + sndB;
                    }
                    
                    if ((imgAPath != null && imgAPath != null) || 
                            (sndAPath != null && sndBPath != null)) {
                        MemoryObj objA = new MMedia(imgAPath, sndAPath);
                        MemoryObj objB = new MMedia(imgBPath, sndBPath);
                        objA.setPairedObj(objB);
                        objB.setPairedObj(objA);
                        gameInfo.objs.add(objA);
                    }
                }
            }
        }
        return gameInfo;
    }
}