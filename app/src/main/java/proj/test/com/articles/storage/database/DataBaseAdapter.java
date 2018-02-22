package proj.test.com.articles.storage.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import proj.test.com.articles.model.Article;

import static android.provider.BaseColumns._ID;
import static proj.test.com.articles.storage.database.DbContract.ArticleEntry.COLUMN_NAME_DATE;
import static proj.test.com.articles.storage.database.DbContract.ArticleEntry.COLUMN_NAME_PATH;
import static proj.test.com.articles.storage.database.DbContract.ArticleEntry.COLUMN_NAME_SECTION;
import static proj.test.com.articles.storage.database.DbContract.ArticleEntry.COLUMN_NAME_SOURCE;
import static proj.test.com.articles.storage.database.DbContract.ArticleEntry.COLUMN_NAME_TITLE;
import static proj.test.com.articles.storage.database.DbContract.ArticleEntry.TABLE_NAME;

public class DataBaseAdapter {
    private SQLiteOpenHelper dbHelper;

    public DataBaseAdapter(Context context) {
        dbHelper = new ArticlesDbHelper(context);
    }

    public long addArticle(Article article) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, article.getTitle());
        values.put(COLUMN_NAME_PATH, article.getPath());
        values.put(COLUMN_NAME_DATE, article.getPublishedDate());
        values.put(COLUMN_NAME_SOURCE, article.getSource());
        values.put(COLUMN_NAME_SECTION, article.getSection());

        long id = db.insert(TABLE_NAME,
                null,
                values);
        DataBaseObserver.getInstance().onDataBaseChange();
        return id;
    }

    public boolean existByTitle(String title) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {_ID, COLUMN_NAME_TITLE};
        boolean result = false;
        Cursor cursor =
                db.query(TABLE_NAME,
                        columns,
                        COLUMN_NAME_TITLE + " = ?",
                        new String[]{String.valueOf(title)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null) {
            result = cursor.getCount() > 0;
        }
        cursor.close();
        return result;
    }

    public Article getArticle(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {_ID, COLUMN_NAME_TITLE, COLUMN_NAME_PATH, COLUMN_NAME_DATE, COLUMN_NAME_SOURCE, COLUMN_NAME_SECTION};

        Cursor cursor =
                db.query(TABLE_NAME,
                        columns,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Article article = new Article(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
        return article;
    }

    public List<Article> getAllArticlesBySection(String section) {
        if (section.equals("all-sections")){
            return getAllArticles();
        }
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_SECTION + " == '" + section + "'";
        return getArticles(query);
    }

    public List<Article> getAllArticles() {
        String query = "SELECT  * FROM " + TABLE_NAME;
        return getArticles(query);

    }

    private List<Article> getArticles(String query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Article> articles = new ArrayList();
        Cursor cursor = db.rawQuery(query, null);

        Article article;
        if (cursor.moveToFirst()) {
            do {
                article = new Article(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                articles.add(article);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return articles;
    }


    public int updateArticle(Article article) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_ID, article.getId());
        values.put(COLUMN_NAME_TITLE, article.getTitle());
        values.put(COLUMN_NAME_PATH, article.getPath());
        values.put(COLUMN_NAME_DATE, article.getPublishedDate());
        values.put(COLUMN_NAME_SOURCE, article.getSource());
        values.put(COLUMN_NAME_SECTION, article.getSection());

        int result = db.update(TABLE_NAME,
                values,
                _ID + " = ?",
                new String[]{String.valueOf(article.getId())});
        DataBaseObserver.getInstance().onDataBaseChange();
        return result;

    }

    public int deleteArticle(Article article) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int result = db.delete(TABLE_NAME,
                COLUMN_NAME_TITLE + " = ?",
                new String[]{String.valueOf(article.getTitle())});
        DataBaseObserver.getInstance().onDataBaseChange();
        return result;
    }


}

