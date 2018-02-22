package proj.test.com.articles.presenter;

public abstract class BasePresenter<T> {
    private T view;

    public BasePresenter(T view) {
        attachView(view);
    }

    public T getView() {
        return view;
    }

    public void detachView() {
        view = null;
    }

    public void attachView(T view) {
        this.view = view;
    }

    abstract public void beforeRemove();
}
