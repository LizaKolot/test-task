package proj.test.com.articles.presenter;


import java.util.HashMap;
import java.util.Map;


public class PresenterManager {
    private static final String TYPE_DETAIL = "detail";

    private Map<String, BasePresenter> presenters;


    private static final PresenterManager instance = new PresenterManager();

    private PresenterManager() {
        presenters = new HashMap<>();
    }

    public static PresenterManager getInstance() {
        return instance;
    }



    public <T> BasePresenter getPresenter(String name, T view) {
        BasePresenter presenter = presenters.get(name);
        if (presenter == null) {
            switch (name) {
                case TYPE_DETAIL:
                    presenter = new DetailPresenter(name, view);
                    break;
                case TYPE_ARTICLE_FAVORITES:
                    presenter = new FavoritesPresenter(name, view);
                    break;
                default:
                    presenter = new BaseListContainerPresenter(name, view);
            }
            presenters.put(name, presenter);
        } else {
            presenter.attachView(view);
        }
        return presenter;
    }

    public void deletePresenter(String name) {
        presenters.remove(name);
    }
}
