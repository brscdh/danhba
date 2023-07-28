package com.example.danhba.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.danhba.R;
import com.example.danhba.adapter.adapter_call;
import com.example.danhba.databinding.ActivityInsertLikeBinding;
import com.example.danhba.model.Call;
import com.example.danhba.util.DataClass;

import java.util.ArrayList;

public class InsertLikeActivity extends AppCompatActivity {

    private ActivityInsertLikeBinding binding;
    int vitri;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertLikeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();
        ClickLst();
        searchView();

    }
    private void getData(){
//        DataClass.listContact = new ArrayList<>();
        DataClass.adapterCall = new adapter_call(this, R.layout.layout_main1, DataClass.listContact);
        binding.lstthemyt.setAdapter(DataClass.adapterCall);

    }
    private void ClickLst(){
        binding.lstthemyt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
                Intent intent1 = new Intent(InsertLikeActivity.this, LikeActivity.class);
                Call callsyt = DataClass.listContact.get(DataClass.vitri);
                intent1.putExtra("themyeuthich", callsyt);
                //intent1.putExtra("txtyt",binding.txtuser.getText().toString());
                startActivityForResult(intent1, DataClass.vitri);
                Toast.makeText(InsertLikeActivity.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchView(){
        binding.searchView.setOnClickListener(v->{
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = v.findViewById(R.id.searchView);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    DataClass.adapterCall.getFilter().filter(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    DataClass.adapterCall.getFilter().filter(s);
                    if(s.isEmpty()){
                        return true;
                    }
                    return false;
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}