package proj.test.com.articles.presenter;


import java.util.HashMap;
import java.util.Map;

import proj.test.com.articles.ArticleApplication;
import proj.test.com.articles.api.NytimesAdapter;
import proj.test.com.articles.api.NytimesApi;
import proj.test.com.articles.model.Article;
import proj.test.com.articles.service.ArticlesType;
import proj.test.com.articles.service.DataLoader;
import proj.test.com.articles.service.DbDataLoader;
import proj.test.com.articles.service.NetworkDataLoader;
import proj.test.com.articles.storage.database.DataBaseAdapter;
import proj.test.com.articles.view.DetailView;
import proj.test.com.articles.view.ListContainerView;


public class PresenterManager {
    public enum TypePresenter {MOSTVIEWED_PRESENTER, MOSTSHARED_PRESENTER, MOSTEMAILED_PRESENTER, FAVORITES_PRESENTER, DETAIL_PRESENTER}

    private Map<TypePresenter, BasePresenter> presenters;

    private static final PresenterManager instance = new PresenterManager();

    private PresenterManager() {
        presenters = new HashMap<>();
    }

    public static PresenterManager getInstance() {
        return instance;
    }

    public void attachPresenter(ListContainerView view, TypePresenter typePresenter) {
        BaseListContainerPresenter presenter = (BaseListContainerPresenter) presenters.get(typePresenter);
        if (presenter == null) {
            switch (typePresenter) {
                case MOSTEMAILED_PRESENTER:
                case MOSTSHARED_PRESENTER:
                case MOSTVIEWED_PRESENTER: {
                    presenter = createBaseListContainerPresenter(view, typePresenter);
                    break;
                }
                case FAVORITES_PRESENTER: {
                    presenter = createFavoritePresenter(view);
                    break;
                }
            }
            presenters.put(typePresenter, presenter);
        }
        view.setPresenter(presenter);
    }

    public void attachPresenter(DetailView view, TypePresenter typePresenter, Article article) {
        DetailPresenter presenter = (DetailPresenter) presenters.get(typePresenter);
        if (presenter == null) {
            presenter = createDetailPresenter(view, article);
            presenters.put(typePresenter, presenter);
        }
        view.setPresenter(presenter);
    }

    private DetailPresenter createDetailPresenter(DetailView view, Article article) {
        DataBaseAdapter dbAdapter = new DataBaseAdapter(ArticleApplication.getContext());
        return new DetailPresenter(view, dbAdapter, article);
    }

    private BaseListContainerPresenter createBaseListContainerPresenter(ListContainerView view, TypePresenter typePresenter) {
        DataLoader dataLoader = null;
        NytimesApi api = NytimesAdapter.getInstance().getApi();
        switch (typePresenter) {
            case MOSTEMAILED_PRESENTER:
                dataLoader = new NetworkDataLoader(ArticlesType.MOSTEMAILED, api);
                break;
            case MOSTSHARED_PRESENTER:
                dataLoader = new NetworkDataLoader(ArticlesType.MOSTSHARED, api);
                break;
            case MOSTVIEWED_PRESENTER:
                dataLoader = new NetworkDataLoader(ArticlesType.MOSTVIEWED, api);
                break;
        }
        return new BaseListContainerPresenter(view, dataLoader);

    }

    private FavoritesPresenter createFavoritePresenter(ListContainerView view) {
        DataBaseAdapter dbAdapter = new DataBaseAdapter(ArticleApplication.getContext()); //???
        DataLoader dataLoader = new DbDataLoader(dbAdapter);
        return new FavoritesPresenter(view, dataLoader);
    }


    public void deletePresenter(String name) {
        presenters.remove(name);
    }

}
