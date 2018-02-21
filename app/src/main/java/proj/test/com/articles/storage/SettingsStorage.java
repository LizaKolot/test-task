package proj.test.com.articles.storage;

import android.content.SharedPreferences;

import static proj.test.com.articles.ArticleApplication.getContext;

public class SettingsStorage {
    private static String PREFS_FILENAME = "proj.kolot.uzsearch.prefs";
    private static String PREF_SECTION = "section";

    private SharedPreferences prefs;

    public SettingsStorage() {

        prefs = getContext().getSharedPreferences(PREFS_FILENAME, 0); // вынести в зависимости
    }

    public void saveSection(String section) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_SECTION, section);
        editor.apply();
    }

   public String getSection() {
       return prefs.getString(PREF_SECTION, null);

    }
}
