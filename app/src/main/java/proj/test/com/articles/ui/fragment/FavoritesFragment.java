package proj.test.com.articles.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import proj.test.com.articles.ui.activity.DetailActivity;
import proj.test.com.articles.ui.adapter.ArticleAdapter;
import proj.test.com.articles.R;
import proj.test.com.articles.storage.database.DataBaseAdapter;
import proj.test.com.articles.model.Article;


import java.util.ArrayList;
import java.util.List;



public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView messageView;
    private List<Article> articles = new ArrayList<>();
    private ArticleAdapter adapter;


    public FavoritesFragment() {

    }


    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }


    private void getContent() {
        DataBaseAdapter dbAdapter = new DataBaseAdapter(getContext());
        articles = dbAdapter.getAllArticles();
        if (articles != null) {
            adapter.setArticles(articles);
        }
        if (articles.size() == 0){
            showEmptyView();
        }
        else {
            showList();
        }
    }

    private void showList() {
        adapter.notifyDataSetChanged();
        messageView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showEmptyView() {
        messageView.setText(R.string.message_empty_view);
        messageView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content, container, false);
        messageView = (TextView) view.findViewById(R.id.error);
        recyclerView = (RecyclerView) view.findViewById(R.id.articles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ArticleAdapter(getContext(), articles);
        adapter.setListener(new ArticleAdapter.OnArticleChooseListener() {
            @Override
            public void onChoose(Article article) {
                Intent intent = DetailActivity.newIntent(getContext(),article);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        getContent();
        return view;
    }

}
