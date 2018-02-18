package proj.test.com.articles;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;

import proj.test.com.articles.service.DataLoader;

public class ArticleApplication  extends Application {

    private static Application application;

    private DataLoader favoritesDataLoader = new DataBaseDataLoader();
    private DataLoader mostEmailedDataLoader = new NetworkDataLoader("email");
    private DataLoader mostSharedDataLoader = new NetworkDataLoader("shared");
    private DataLoader mostViewedDataLoader = new NetworkDataLoader("viewed");



    public static Application getApplication() {
        return application;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
