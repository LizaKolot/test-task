package proj.test.com.articles.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import proj.test.com.articles.R;
import proj.test.com.articles.model.Article;
import proj.test.com.articles.presenter.BaseListContainerPresenter;
import proj.test.com.articles.ui.activity.DetailActivity;
import proj.test.com.articles.ui.adapter.ArticleAdapter;
import proj.test.com.articles.view.ListContainerView;


public class ListFragment extends Fragment implements ListContainerView {

    private BaseListContainerPresenter presenter;

    private RecyclerView recyclerView;
    private TextView errorView;
    private ProgressBar progressBar;
    private Spinner spinner;

    private static int checkAmountClickSelection = 0;

    private ArticleAdapter adapter;


    public static ListFragment newInstance() {
        return new ListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("my test", " onCreate() " + getTag());

    }

    private String getSection() {
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.section);
        Log.e("my test", spinner.getSelectedItem().toString());
        return spinner.getSelectedItem().toString();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        errorView.setText(message);
    }

    @Override
    public void hideMessage() {
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        spinner = (Spinner) getActivity().findViewById(R.id.section);
        spinner.setOnItemSelectedListener(listener);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("my test", " onStart() " + getTag());
        if (presenter != null) {
            presenter.showContent(getSection());
            setSectionView();
            Log.e("my test", " onStart() show content  " + getTag());
        }


    }

    @Override
    public void showList(List<Article> list) {
        errorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setArticles(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyList() {
        recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(getString(R.string.message_empty_view));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("my test", " onCreateView() " + getTag());
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        //  spinner = (Spinner) getActivity().findViewById(R.id.section);
        errorView = (TextView) view.findViewById(R.id.error);
        recyclerView = (RecyclerView) view.findViewById(R.id.articles);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ArticleAdapter(getContext(), Collections.<Article>emptyList());
        adapter.setListener(new ArticleAdapter.OnArticleChooseListener() {
            @Override
            public void onChoose(Article article) {
                presenter.chooseArticle(article);
            }
        });
        recyclerView.setAdapter(adapter);
     /*   if (presenter != null) {
            presenter.showContent(getSection());
            Log.e("my test", " onStart() show content  " + getTag());
        }*/
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("my test", " onResume() " + getTag());
        // if (presenter != null) presenter.attachView(this); //????s\
    //    presenter.showContent(getSection());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("my test", " onDestroy() " + getTag());
        if (presenter != null) presenter.detachView();
    }

    @Override
    public void showDetailArticle(Article article) {
        Intent intent = DetailActivity.newIntent(getContext(), article);
        startActivity(intent);
    }


    private void setSectionView() {
        if (spinner != null && getUserVisibleHint()) {


       // if (presenter != null) {
            checkAmountClickSelection = 0;
            spinner.setOnItemSelectedListener(listener);
            Log.e("my test", " spinner set listener " + this);

       // }
    }}


    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.e("my test", " on item selected " + spinner.getItemAtPosition(position).toString());
            if (presenter != null && (++checkAmountClickSelection > 1))
                presenter.onChooseSection(spinner.getItemAtPosition(position).toString());
            Log.e("my test", " must get content on item selected " + spinner.getItemAtPosition(position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    public void setPresenter(BaseListContainerPresenter presenter) {
        this.presenter = presenter;
        this.presenter.attachView(this);
        Log.e("my test", " set presenter " + getTag());
        //  this.presenter.showContent(getSection());
      /*  if (this.presenter  != null && getActivity() != null) {
            this.presenter.showContent(getSection());
            Log.e("my test", " set presenter " + getTag() + " show content ");
        }*/
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("my test", " set user visible hint " + isVisibleToUser + "  " + getTag()
                + " isVisible()=" + isVisible() + "  isAdded() = " + isAdded() + "  isInLayout()=" + isInLayout());
        if (isVisibleToUser && isVisible() && presenter != null) {
            presenter.onFragmentVisibleUser(getSection());
            setSectionView();
        }
    }


}
