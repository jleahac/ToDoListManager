package projects.todolistmanager;

import android.content.DialogInterface;
import android.content.Intent;
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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> list_of_item;
    private ArrayAdapter<Item> list_adapter;
    private ListView mList;
    Intent intent;
    private static final String TAG = "MainActivity";
    String message ;
    public final static int RESULT = 0;
    static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_of_item = new ArrayList<Item>();
        list_adapter = new Adapter(this, R.layout.item_to_add, list_of_item );
        mList = (ListView)  findViewById(R.id.list);
        mList.setLongClickable(true);
        mList.setAdapter(list_adapter);
        list_adapter.notifyDataSetChanged();
        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {


                String title = list_adapter.getItem(position).getTitle();
                String task = title + "\n" + list_adapter.getItem(position).getStringDate();
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
                list_adapter.add(item_to_add);
                list_adapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save custom values into the bundle
        // Always call the superclass so it can save the view hierarchy state
        for (int i = 0 ; i < list_adapter.getCount() ; i++){
            outState.putString(Integer.toString(i) ,list_adapter.getItem(i).getTitle() );
            outState.putInt(Integer.toString(i+1000) ,list_adapter.getItem(i).getDay());
            outState.putInt(Integer.toString(i+2000) ,(list_adapter.getItem(i).getMonth()));
            outState.putInt(Integer.toString(i+3000) ,list_adapter.getItem(i).getYear());
        }
        index =list_adapter.getCount();
        outState.putInt("int",index);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void  onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        int index2 = savedInstanceState.getInt("int");
        for (int i = 0; i < index2; i++ ){
            String item_to_restore= savedInstanceState.getString(Integer.toString(i));
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, savedInstanceState.getInt(Integer.toString(i+1000)));
            cal.set(Calendar.MONTH, savedInstanceState.getInt(Integer.toString(i+2000)));
            cal.set(Calendar.YEAR, savedInstanceState.getInt(Integer.toString(i+3000)));
            Date date = cal.getTime();
            list_adapter.add(new Item(item_to_restore, date));
            list_adapter.notifyDataSetChanged();
        }
    }

}