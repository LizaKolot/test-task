package proj.test.com.articles;

import android.app.Application;
import android.content.Context;

public class ArticleApplication  extends Application {

    private static Application application;



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
