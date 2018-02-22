package proj.test.com.articles.presenter;

import proj.test.com.articles.service.DataLoader;
import proj.test.com.articles.view.ListContainerView;
import proj.test.com.articles.storage.database.DataBaseObserver;


public class FavoritesPresenter extends BaseListContainerPresenter {
    private DataBaseObserver.DataBaseChangeListener listener = new DataBaseObserver.DataBaseChangeListener() {
        @Override
        public void onChange() {
            resetData();
        }
    };

    public FavoritesPresenter(ListContainerView view, DataLoader dataLoader) {
        super(view, dataLoader);
        DataBaseObserver.getInstance().addDataBaseChangeListener(listener);
    }

    @Override
    public void beforeRemove() {
        super.beforeRemove();
        DataBaseObserver.getInstance().removeDataBaseChangeListener(listener);
    }
}
