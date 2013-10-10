package com.morphoss.xo.memorize.res;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.morphoss.xo.memorize.UnzipUtility;

import android.content.Context;
import android.content.res.AssetManager;

public class GameResource {
    final static String DIRECTORY_GAMES = "games";
    final static String TAG = "GameResource";

    private ArrayList<GameInfo> allGames = new ArrayList<GameInfo>();

    Context mCtx;

    public GameResource(Context ctx) {
        mCtx = ctx;
    }

    public void initAssetGame() {
        unzipPreInstalledGames();
        createGameInfo();
    }

    private void createGameInfo() {
        File path = mCtx.getExternalFilesDir(DIRECTORY_GAMES);
        String[] gameList = path.list();
        for (String gameDirName : gameList) {
            File file = new File(path + "/" + gameDirName, "game.xml");
            if (file != null && file.exists()) {
                GameXmlParser parser = new GameXmlParser();
                try {
                    GameInfo gameInfo = parser.parse(new FileInputStream(file), file);
                    if (gameInfo != null)
                        allGames.add(gameInfo);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public final ArrayList<GameInfo> getAllGames() {
        return allGames;
    }

    private void unzipPreInstalledGames() {
        AssetManager am = mCtx.getAssets();
        try {
            String zipFileList[] = am.list(DIRECTORY_GAMES);
            if (zipFileList != null) {
                for (String zipFile : zipFileList) {
                    int index = zipFile.lastIndexOf('.');
                    String dirName = index > 0 ? zipFile.substring(0, index) : zipFile;
                    File path = mCtx.getExternalFilesDir(DIRECTORY_GAMES);
                    
                    if (zipFile.endsWith("zip") && !hasGameFiles(dirName)) {
                        UnzipUtility.unzip(am.open(DIRECTORY_GAMES + '/' + zipFile), 
                                path.getAbsoluteFile() + "/" + dirName);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasGameFiles(final String dirName) {
        File path = mCtx.getExternalFilesDir(DIRECTORY_GAMES);
        if (path != null) {
            File file = new File(path, dirName);
            if (file != null) 
                return file.exists();
        }
        return false;
    }
}