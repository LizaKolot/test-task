package proj.test.com.articles.presenter;

public class BasePresenter <T>{

   // private FragmentsFactory.TypePresenter type;
    private T view;

    public BasePresenter(T view) {
      //  this.type = type;
        attachView(view);
    }

   // public FragmentsFactory.TypePresenter getType() {
  //      return type;
  //  }

    public T getView() {
        return  view;
    }

    public void detachView() {
        view = null;
    }

    public void attachView(T view) {
        this.view = view;
    }
}
