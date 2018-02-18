package proj.test.com.articles.service;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import proj.test.com.articles.api.NytimesApi;
import proj.test.com.articles.model.Article;
import proj.test.com.articles.model.ArticleExt;
import proj.test.com.articles.model.ResponseAllArticle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NetworkDataLoader extends DataLoader {
    private static final String PERIOD = "30";

    private final ArticlesType type;
    private final NytimesApi api;// = NytimesAdapter.getInstance().getApi();

    public NetworkDataLoader(ArticlesType type, NytimesApi api) {
        this.type = type;
        this.api = api;
    }

    @Override
    public void getArticles(String section, final OnDataListener listener) {
        api.getArticles(type.toString(), section, PERIOD).enqueue(new Callback<ResponseAllArticle>() {

            @Override
            public void onResponse(Call<ResponseAllArticle> call, Response<ResponseAllArticle> response) {
                List<ArticleExt> result = response.body().getArticleList();
                List<Article> list = new ArrayList<>();
                Log.e("my test", " get article type=" + type + "  " + response.raw());
                for (ArticleExt art : result) {
                    art.setSource(art.getSource() + "  " + type);
                    list.add(art);
                }

                listener.onSuccess(list);
            }

            @Override
            public void onFailure(Call<ResponseAllArticle> call, Throwable t) {
                listener.onFail(t.getMessage());
            }
        });
    }
}
