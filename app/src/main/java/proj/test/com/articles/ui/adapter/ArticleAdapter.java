package proj.test.com.articles.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


import proj.test.com.articles.R;
import proj.test.com.articles.model.Article;
import proj.test.com.articles.ui.activity.DetailActivity;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {



    private List<Article> articles;
    private Context context;
    private OnArticleChooseListener listener;


    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;

    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
    public void setListener(OnArticleChooseListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.source.setText(article.getSource());
        holder.publishedDate.setText(article.getPublishedDate());
        holder.article = article;
    }

    @Override
    public int getItemCount() {
        if (articles == null)
            return 0;
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView source;
        TextView publishedDate;
        Article article;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            source = (TextView) itemView.findViewById(R.id.source);
            publishedDate = (TextView) itemView.findViewById(R.id.published_date);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Intent intent = DetailActivity.newIntent(context,article);
                    context.startActivity(intent);*/
                    if (listener!= null) {
                        listener.onChoose(article);
                    }
                }
            });
        }
    }

    public interface OnArticleChooseListener {
       void onChoose(Article article);
    }
}
