package de.hsworms.vl.ema.AutoManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private  ArrayList<Cars>exampleList=new ArrayList<>();
    private Button insertButton;

    MyAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadData();
        ArrayList<Cars> NewMember=new ArrayList<>();

        buildRecyclerView();
        Intent intentAddCarsActivity = new Intent(this, AddCarsActivity.class);


        insertButton = findViewById(R.id.button_insert);
      insertButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               intentAddCarsActivity.putExtra("TheOne",NewMember);
               startActivityForResult(intentAddCarsActivity,1);
            }

        });

      ;

    }


    private void buildRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OpenEditor(position);
            }

            @Override
            public void onDeleteClick(int position) {
                exampleList.remove(position);
                saveData();
                mAdapter.notifyDataSetChanged();
               buildRecyclerView();

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1){
            if(resultCode==RESULT_OK){
                ArrayList<Cars> buffer ;
                buffer= data.getParcelableArrayListExtra("Cars");
                exampleList.addAll(buffer);
                buildRecyclerView();
                saveData();
            }
        }


        if(requestCode==2){
            if(resultCode==RESULT_OK){
                Cars buffer ;
                int pos;

                buffer= data.getParcelableExtra("Done");
                pos=data.getIntExtra("DonePos",0);

                exampleList.set(pos,buffer);
                buildRecyclerView();
                saveData();
            }
        }
    }


    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(exampleList);
        editor.putString("task list", json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Cars>>() {}.getType();
        exampleList = gson.fromJson(json, type);
        if (exampleList == null) {
            exampleList = new ArrayList<>();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

        public void OpenEditor(int pos){
            Intent OpenEditorPage= new Intent(this,EditCar.class);
            OpenEditorPage.putExtra("TheItem",exampleList.get(pos));
            OpenEditorPage.putExtra("ThePos",pos);
            startActivityForResult(OpenEditorPage,2);

        }

}