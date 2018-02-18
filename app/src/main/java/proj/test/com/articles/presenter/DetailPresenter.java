package proj.test.com.articles.presenter;


import android.content.Context;

import proj.test.com.articles.model.Article;
import proj.test.com.articles.storage.database.DataBaseAdapter;
import proj.test.com.articles.view.DetailView;

public class DetailPresenter extends BasePresenter<DetailView> {

    private Article article;
    private DataBaseAdapter dbAdapter;

    public DetailPresenter(String name, DetailView view) {
        super(name, view);
        dbAdapter = new DataBaseAdapter(context);
    }

    public void setArticle(Article article) {
        this.article = article;
    }

   /* public DetailPresenter(Context context, Article article) {
        this.article = article;
        dbAdapter = new DataBaseAdapter(context);
    }*/
}
