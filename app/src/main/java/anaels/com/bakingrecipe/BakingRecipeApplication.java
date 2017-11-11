package anaels.com.bakingrecipe;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Used to init Stetho
 */
public class BakingRecipeApplication extends Application {
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
  }
}