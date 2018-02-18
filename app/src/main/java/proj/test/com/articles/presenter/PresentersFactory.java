package proj.test.com.articles.presenter;

import proj.test.com.articles.view.DetailView;
import proj.test.com.articles.view.ListContainerView;

import static proj.test.com.articles.presenter.PresentersFactory.TypePresenter.DETAIL_PRESENTER;
import static proj.test.com.articles.presenter.PresentersFactory.TypePresenter.FAVORITES_PRESENTER;

public class PresentersFactory {
    enum TypePresenter {MOSTVIEW_PRESENTER, MOSTSHARED_PRESENTER, MOSTEMAILED_PRESENTER, FAVORITES_PRESENTER, DETAIL_PRESENTER}

    private static DetailPresenter getDetailPresenter(DetailView view)

    {
        return new DetailPresenter(DETAIL_PRESENTER.toString(),view);
    }

    private static FavoritesPresenter getFavoritesPresenter(ListContainerView view)

    {
        return new FavoritesPresenter(FAVORITES_PRESENTER.toString(),view);
    }

    private static BaseListContainerPresenter getBaseListContainerPresenter(TypePresenter type, ListContainerView view)

    {
        return new BaseListContainerPresenter();
    }



}
