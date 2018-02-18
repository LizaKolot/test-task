package proj.test.com.articles.presenter;

public class BasePresenter<T> {

    private String name;
    private T view;

    public BasePresenter(String name, T view) {
        this.name = name;
        this.view = view;
    }

    public String getName() {
        return name;
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
}
