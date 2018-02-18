package proj.test.com.articles.presenter;

import proj.test.com.articles.view.ListContainerView;
import proj.test.com.articles.storage.database.DataBaseObserver;



public class FavoritesPresenter extends BaseListContainerPresenter {
    public FavoritesPresenter(String name, ListContainerView view) {
        super(name, view);
        DataBaseObserver.getInstance().addDataBaseChangeListener(new DataBaseObserver.DataBaseChangeListener() {
            @Override
            public void onChange() {
                resetData();
            }
        });
    }

    @Override
    public void onFragmentVisibleUser() {
        getView().hideFilterSection();
    }
}
