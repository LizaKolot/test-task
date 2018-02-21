package proj.test.com.articles.storage.database;

import android.provider.BaseColumns;

final public class DbContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database.db";
    private static final String COMMA_SEP = ",";
    private static final String TEXT_TYPE = " TEXT";

    private DbContract() {
    }

    public static abstract class ArticleEntry implements BaseColumns {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PATH = "path";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_SOURCE = "source";
        public static final String COLUMN_NAME_SECTION = "section";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_PATH + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SOURCE + TEXT_TYPE +COMMA_SEP +
                COLUMN_NAME_SECTION + TEXT_TYPE  + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}

