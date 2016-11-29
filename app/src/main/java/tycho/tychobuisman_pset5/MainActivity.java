package tycho.tychobuisman_pset5;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private DBManager dbManager;

    private ListView lv;

    private MyCustomAdapter adapter;

    private ArrayList<String> Items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        dbManager.open();

        lv = (ListView) findViewById(R.id.list);
        lv.setEmptyView(findViewById(R.id.empty));

        Items = viewAll(1);

        final ListView lv = (ListView) findViewById(R.id.list);
        adapter = new MyCustomAdapter(Items, this);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> IdList = viewAll(0);

                String _id = IdList.get(position);
                long l = Long.parseLong(_id);
                int result = dbManager.delete(l);

                if(result > 0){
                    Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                    Items.remove(position);

                    ListView lv = (ListView) findViewById(R.id.list);
                    adapter = new MyCustomAdapter(Items, getApplicationContext());
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Item not deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent goToSubList = new Intent(this, sublistAcitivity.class);
                goToSubList.putExtra("key", key);//bundle doorgeven aan intent
                startActivity(goToSubList);

            }
        });
    }

    // on click
    public void addItem(View view){
        EditText text = (EditText)findViewById(R.id.editText);
        String item = text.getText().toString();
        dbManager.insert(item);
        Items.add(item);
        Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_SHORT).show();
        text.setText("");
        ArrayList<String> Items = viewAll(1);
        ListView lv = (ListView) findViewById(R.id.list);
        adapter = new MyCustomAdapter(Items, this);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }

    public ArrayList<String> viewAll(int column){
        ArrayList<String> List = new ArrayList<String>();

        Cursor cursor = dbManager.fetch();
        if (cursor.moveToFirst()) {
            do {
                List.add(cursor.getString(column));
            } while (cursor.moveToNext());
        }
        return List;
    }
}