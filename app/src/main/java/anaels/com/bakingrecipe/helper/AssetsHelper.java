package anaels.com.bakingrecipe.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Utility used to read the assets files
 */
public class AssetsHelper {

    public static final String PATH_FILE_RECIPES = "recipe.json";

    /**
     * Read a file from the assets folder and return its content
     *
     * @param pFilePath the path of the file to read
     * @param pContext  the context of the app
     * @return the content of the file in a String
     */
    public static String readJsonFileFromAssets(String pFilePath, Context pContext) {
        StringBuilder lBuf = new StringBuilder();
        BufferedReader lInFile = null;
        String lJsonString = "";
        try {
            InputStream lJsonFileStream = pContext.getAssets().open(pFilePath);
            lInFile = new BufferedReader(new InputStreamReader(lJsonFileStream, "UTF-8"));
            while ((lJsonString = lInFile.readLine()) != null) {
                lBuf.append(lJsonString);
            }
            lJsonString = lBuf.toString();
            lInFile.close();
            Log.i("AssetsHelper", "Json read : " + lJsonString);
        } catch (IOException e) {
            Log.e("AssetsHelper", "Error while reading the assets file", e);
        } finally {
            if (lInFile != null) {
                try {
                    lInFile.close();
                } catch (IOException ioe) {
                    //We ignore this one
                }
            }
        }
        return lJsonString;
    }
}
