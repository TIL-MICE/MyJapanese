package pri.weiqiang.myjapanese.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pri.weiqiang.myjapanese.MyApplication;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.comparator.GojuonItemComporator;
import pri.weiqiang.myjapanese.config.Constants;
import pri.weiqiang.myjapanese.db.JPDatabase;
import pri.weiqiang.myjapanese.mvp.bean.Book;
import pri.weiqiang.myjapanese.mvp.bean.GojuonItem;
import pri.weiqiang.myjapanese.mvp.bean.Lesson;
import pri.weiqiang.myjapanese.mvp.bean.Word;
import pri.weiqiang.myjapanese.utils.ResourceUtils;

public class DBManager {

    private static final String TAG = DBManager.class.getSimpleName();
    private static DBManager instance = null;

    private List<GojuonItem> query = null;
    private List<GojuonItem> qingYin = null;
    private List<GojuonItem> zhuoYin = null;
    private List<GojuonItem> aoYin = null;
    private List<GojuonItem> qingYinWithoutHeader = null;
    private List<GojuonItem> zhuoYinWithoutHeader = null;
    private List<GojuonItem> aoYinWithoutHeader = null;

    private List<Word> mWordList = null;
    private List<Lesson> mLessonList = null;
    private ArrayList<Book> mBookList = null;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {

        if (instance == null) {
            instance = new DBManager();
        }

        return instance;
    }


    public void init() {

        getQingYinWithoutHeader();
        getZhuoYinWithoutHeader();
        getAoYinWithoutHeader();

    }

    public synchronized List<Book> getBooks() {
        //仅第一次调用数据库时填充mBookList
        if (mBookList == null) {
            Log.e(TAG, "mBookList = null");
            SQLiteDatabase db = JPDatabase.getInstance(MyApplication.getInstance()).getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + JPDatabase.DB_TABLE_LESSONS, null);
            mBookList = new ArrayList<>();
            mLessonList = new ArrayList<>();
            Book bookItem;
            Lesson lessonItem;
            String curbook = "大家的日本语第一册";
            int i = 0;
            while (cursor.moveToNext() /*&& i < 50*/) {
                i++;
                String book = cursor.getString(cursor.getColumnIndex("book"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                if (book.equals(curbook)) {
                    lessonItem = new Lesson(book, title);
                    mLessonList.add(lessonItem);
                } else {
                    bookItem = new Book(curbook, mLessonList);
                    mBookList.add(bookItem);
                    mLessonList = new ArrayList<>();
                    lessonItem = new Lesson(book, title);
                    mLessonList.add(lessonItem);
                    curbook = book;
                }
            }
            if (curbook.equals("新编日语IV")) {
                bookItem = new Book(curbook, mLessonList);
                mBookList.add(bookItem);
            }
            cursor.close();
            db.close();
        }
        return mBookList;
    }

    public synchronized List<Word> queryWord(String lesson) {
        Log.e(TAG, "lesson:" + lesson);

        SQLiteDatabase db = JPDatabase.getInstance(MyApplication.getInstance()).getReadableDatabase();
        String selection = "lesson_id=?";
        String[] selectionArgs = new String[]{lesson};
        Cursor cursor = db.query(JPDatabase.DB_TABLE_WORDS,
                null, selection, selectionArgs, null, null,
                null);
        mWordList = new ArrayList<>();
        Word item;
        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String book_id = cursor.getString(cursor.getColumnIndex("book_id"));
            String lesson_id = cursor.getString(cursor.getColumnIndex("lesson_id"));
            String word = cursor.getString(cursor.getColumnIndex("word"));
            String phonetic = cursor.getString(cursor.getColumnIndex("phonetic"));
            String translation = cursor.getString(cursor.getColumnIndex("translation"));
            int fav = cursor.getInt(cursor.getColumnIndex("fav"));
            int cache = cursor.getInt(cursor.getColumnIndex("cache"));
            item = new Word(id, book_id, lesson_id, word, phonetic, translation, fav, cache);
//            Log.e(TAG, "word:" + item.toString());
            mWordList.add(item);
        }
        cursor.close();
        db.close();

        return mWordList;
    }

    public synchronized List<GojuonItem> query() {

        if (query == null) {
//            SQLiteDatabase db = new JPDatabase(MyApplication.getInstance()).getReadableDatabase();
            SQLiteDatabase db = JPDatabase.getInstance(MyApplication.getInstance()).getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + JPDatabase.DB_TABLE_GOJUON, null);
            query = new ArrayList<>();
            GojuonItem item;
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int row = cursor.getInt(cursor.getColumnIndex("row"));
                int column = cursor.getInt(cursor.getColumnIndex("column"));
                String hiragana = cursor.getString(cursor.getColumnIndex("hiragana"));
                String katakana = cursor.getString(cursor.getColumnIndex("katakana"));
                String rome = cursor.getString(cursor.getColumnIndex("rome"));
                int category = cursor.getInt(cursor.getColumnIndex("category"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                boolean existed = cursor.getInt(cursor.getColumnIndex("existed")) == 1;

                item = new GojuonItem(id, row, column, hiragana, katakana, rome, category, type, existed);

                query.add(item);
            }
            cursor.close();
            db.close();
        }

        return query;
    }


    public synchronized List<GojuonItem> getQingYin() {

        if (qingYin == null) {

            qingYin = new ArrayList<>();
            List<GojuonItem> query = query();
            for (GojuonItem item : query) {

                if (item.getCategory() == Constants.CATEGORY_QINGYIN) {
                    qingYin.add(item);
                }

            }

            Collections.sort(qingYin, new GojuonItemComporator());


        }

        return qingYin;
    }

    public List<GojuonItem> getZhuoYin() {

        if (zhuoYin == null) {

            zhuoYin = new ArrayList<>();
            List<GojuonItem> query = query();
            for (GojuonItem item : query) {

                if (item.getCategory() == Constants.CATEGORY_ZHUOYIN) {
                    zhuoYin.add(item);
                }

            }

            Collections.sort(zhuoYin, new GojuonItemComporator());


        }


        return zhuoYin;
    }

    public List<GojuonItem> getAoYin() {

        if (aoYin == null) {

            aoYin = new ArrayList<>();
            List<GojuonItem> query = query();
            for (GojuonItem item : query) {

                if (item.getCategory() == Constants.CATEGORY_AOYIN) {
                    aoYin.add(item);
                }

            }

            Collections.sort(aoYin, new GojuonItemComporator());


        }

        return aoYin;
    }

    public List<GojuonItem> getQingYinWithoutHeader() {

        if (qingYinWithoutHeader == null) {

            qingYinWithoutHeader = new ArrayList<>();
            List<GojuonItem> query = getQingYin();
            for (GojuonItem item : query) {

                if (item.getRow() != 0 && item.getColumn() != 0 && item.isExisted()) {
                    qingYinWithoutHeader.add(item);
                }

            }
        }


        return qingYinWithoutHeader;
    }

    public List<GojuonItem> getZhuoYinWithoutHeader() {

        if (zhuoYinWithoutHeader == null) {

            zhuoYinWithoutHeader = new ArrayList<>();
            List<GojuonItem> query = getZhuoYin();
            for (GojuonItem item : query) {

                if (item.getRow() != 0 && item.getColumn() != 0 && item.isExisted()) {
                    zhuoYinWithoutHeader.add(item);
                }

            }
        }

        return zhuoYinWithoutHeader;
    }

    public List<GojuonItem> getAoYinWithoutHeader() {

        if (aoYinWithoutHeader == null) {

            aoYinWithoutHeader = new ArrayList<>();
            List<GojuonItem> query = getAoYin();
            for (GojuonItem item : query) {

                if (item.getRow() != 0 && item.getColumn() != 0 && item.isExisted()) {
                    aoYinWithoutHeader.add(item);
                }

            }
        }

        return aoYinWithoutHeader;
    }

    public static void addHeaderString(List<GojuonItem> list, int row, int column) {

        for (int i = 1; i < column; i++) {

            GojuonItem item = list.get(i);
            String hiragana = item.getHiragana();
            String katakana = item.getKatakana();
            item.setHiragana(hiragana + ResourceUtils.getString(MyApplication.getInstance(), R.string.column));
            item.setKatakana(katakana + ResourceUtils.getString(MyApplication.getInstance(), R.string.column));

        }

        for (int i = 1; i < row; i++) {

            GojuonItem item = list.get(i * column);
            String hiragana = item.getHiragana();
            String katakana = item.getKatakana();
            item.setHiragana(hiragana + ResourceUtils.getString(MyApplication.getInstance(), R.string.row));
            item.setKatakana(katakana + ResourceUtils.getString(MyApplication.getInstance(), R.string.row));

        }

    }


}
