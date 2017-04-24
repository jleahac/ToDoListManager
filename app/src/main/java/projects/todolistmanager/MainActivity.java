package projects.todolistmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> list_of_item;
    private ArrayAdapter<Item> list_adapter;
    private ListView mList;

    private DbHelper todo_db;
    private Cursor cursor;
    private ToDoListCursorAdaptor cursorAdapter;

    Intent intent;
    private static final String TAG = "MainActivity";
    String message ;
    public final static int RESULT = 0;
    static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todo_db = new DbHelper(this);
        todo_db.deleteTable();
        cursor = todo_db.getDBInformation();
        String columns_name[] = new String[]{DbHelper.TITLE, DbHelper.DUE_DATE};
        int txtViews[] = new int[]{R.id.txtTodoTitle, R.id.txtTodoDueDate};
        cursorAdapter =new ToDoListCursorAdaptor(this, R.layout.activity_main, cursor, columns_name, txtViews);
        list_of_item = new ArrayList<Item>();
        list_adapter = new Adapter(this, R.layout.item_to_add, list_of_item );
        mList = (ListView)  findViewById(R.id.list);
        mList.setLongClickable(true);
        mList.setAdapter(cursorAdapter);
        list_adapter.notifyDataSetChanged();
        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {


                String title = todo_db.getTitleByPosition(position);
                String task = title + "\n" + todo_db.getDueDateByPosition(position);
                if(title.startsWith("CALL"))
                {
                    int startingIndex = title.indexOf(" ");
                    if( startingIndex != -1){
                        String phoneNumber = title.substring(startingIndex);
                        if(PhoneNumberUtils.isWellFormedSmsAddress(phoneNumber))
                        {
                            // Dial
                            Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                            startActivity(dial);
                        }
                    }


                }
                Toast.makeText(MainActivity.this, task, Toast.LENGTH_LONG).show();

                return true;
            }

        });
    }
    public void sendMessage(View view) {
        intent = new Intent(this, AddAnItem.class);
        startActivityForResult(intent, RESULT);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuItemAdd) {
            sendMessage(findViewById(R.id.add_task));


        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == RESULT){
            if (resultCode == RESULT_OK){
                message = data.getStringExtra(AddAnItem.EXTRA_MESSAGE);
                Date dueDate = (Date)data.getSerializableExtra("dueDate");
                Item item_to_add = new Item(message, dueDate);

                todo_db.addNewTask(message, dueDate.getTime());
                cursorAdapter.changeCursor(todo_db.getDBInformation());
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save custom values into the bundle
        // Always call the superclass so it can save the view hierarchy state
        for (int i = 0 ; i < cursorAdapter.getCount() ; i++){
            outState.putString(Integer.toString(i) ,todo_db.getTitleByPosition(i) );
            outState.putString(Integer.toString(i+1000) ,todo_db.getDueDateByPosition(i));
        }
        index =cursorAdapter.getCount();
        outState.putInt("int",index);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void  onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        int index2 = savedInstanceState.getInt("int");
        for (int i = 0; i < index2; i++ ){
            String item_to_restore= savedInstanceState.getString(Integer.toString(i));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(savedInstanceState.getString(Integer.toString(i+1000)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            todo_db.addNewTask(item_to_restore, myDate.getTime());
            cursorAdapter.changeCursor(todo_db.getDBInformation());
        }
    }

}