package a.suman.ghajini;


import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Eight_Packs extends AppCompatActivity {
    dataBaseHelper d;
    TextView T1;
    TextView T2;
    EditText T3;
    TextView T4;
    ListView todo;
    int pos=-1;
    boolean m=false;
    String abc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight__packs);
        todo = findViewById(R.id.todo);
        T1 = findViewById(R.id.T1);
        T2 = findViewById(R.id.T2);
        T3 = findViewById(R.id.T3);
        T4 = findViewById(R.id.T4);
        T1.setOnClickListener(v);

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = T3.getText().toString();
                if (!a.equals("")){
                    if(m){
                        d.Edt(a, abc);
                    }
                    else {
                        d.Add(a);
                    }
                }
                T1.setVisibility(View.VISIBLE);
                todo.setVisibility(View.VISIBLE);
                T2.setVisibility(View.GONE);
                T4.setVisibility(View.GONE);
                T3.setText("");
                T3.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                Update();
                m=false;

            }
        });
        T4.setOnClickListener(x);
        d = new dataBaseHelper(this);
        Update();
        todo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abc=todo.getItemAtPosition(position).toString();
                T3.setText(abc);
                T1.setVisibility(View.GONE);
                todo.setVisibility(View.GONE);
                T2.setVisibility(View.VISIBLE);
                T4.setVisibility(View.VISIBLE);
                T3.setVisibility(View.VISIBLE);
                m=true;
            }
        });
        todo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int i, long l) {
                pos=i;
                builder.setMessage("Do you want to delete this task?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        del(pos);
                        Update();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();

               return true;
            }
        });
    }
    public void del(int i){
        String  abc=todo.getItemAtPosition(i).toString();
        d.Del(abc);

    }



    View.OnClickListener v = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            T1.setVisibility(View.GONE);
            todo.setVisibility(View.GONE);
            T2.setVisibility(View.VISIBLE);
            T4.setVisibility(View.VISIBLE);
            T3.setVisibility(View.VISIBLE);
            T3.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(T3, InputMethodManager.SHOW_IMPLICIT);

        }
    };
    View.OnClickListener x = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            T1.setVisibility(View.VISIBLE);
            todo.setVisibility(View.VISIBLE);
            T2.setVisibility(View.GONE);
            T4.setVisibility(View.GONE);
            T3.setText("");
            T3.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            m=false;
        }
    };

    public void Update() {
        Cursor c = d.getLCon();
        ArrayList a= new ArrayList<String>();
        while(c.moveToNext()){
            a.add(c.getString(1));
        }
        ArrayAdapter adp= new ArrayAdapter(this, R.layout.item, a);
        todo.setAdapter(adp);
    }
}