package projects.todolistmanager;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.Locale;


public class DbHelper extends SQLiteOpenHelper {

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String DUE_DATE = "due_date";

    public static final int ID_INDEX = 0;
    public static final int TITLE_INDEX = 1;
    public static final int TASK_DUE_DATE_INDEX = 2;

    public static final String DATABASE_NAME = "todo_db";
    public static final String TABLE = "todo";
    public static final int DATABASE_VERSION = 2;
    private SQLiteDatabase db;

    public static final String SQL_CREATE_ENTRY = "CREATE TABLE " + TABLE + "(" +
            ID + " integer primary key autoincrement, " +
            TITLE + " text, " +
            DUE_DATE + " long);";

    public static final String SQL_DELETE_ENTRY= "DROP TABLE if exists " + TABLE + ";";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("DBHelper", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRY);
        onCreate(db);
    }
    public void deleteTable() {
        if (db == null || !db.isOpen())
            db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRY);
        db.execSQL(SQL_CREATE_ENTRY);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void addNewTask(String title, long due_date) {
        SQLiteDatabase SQ = this.getWritableDatabase();

        ContentValues new_task = new ContentValues();
        new_task.put(TITLE, title);
        new_task.put(DUE_DATE, due_date);

        SQ.insert(TABLE, null, new_task);
    }

    public Cursor getDBInformation() {
        SQLiteDatabase SQ = this.getReadableDatabase();
        String[] columns = {ID, TITLE, DUE_DATE};
        Cursor cursor = SQ.query(TABLE, columns, null, null, null, null, null);
        return cursor;
    }
    public String getTitleByPosition (int position) {
        Cursor cursor = this.getDBInformation();
        cursor.moveToPosition(position);
        return cursor.getString(TITLE_INDEX);
    }
    public String getDueDateByPosition (int position) {
        Cursor cursor = this.getDBInformation();
        cursor.moveToPosition(position);
        Date itemDate = new Date(cursor.getLong(TASK_DUE_DATE_INDEX));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(itemDate);
    }
    public Date getDueDate (int position) {
        Cursor cursor = this.getDBInformation();
        cursor.moveToPosition(position);
        return new Date(cursor.getLong(TASK_DUE_DATE_INDEX));
    }

}