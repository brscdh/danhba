package com.example.danhba.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.danhba.R;
import com.example.danhba.adapter.adapter_yt;
import com.example.danhba.databinding.ActivityLikeBinding;
import com.example.danhba.model.Call;
import com.example.danhba.util.DataClass;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class LikeActivity extends AppCompatActivity {

    private ActivityLikeBinding binding;
    public static int vitri;
    SearchView searchView;
    Call callinsertyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLikeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataClass.adapterYt = new adapter_yt(this, R.layout.layout_main1,DataClass.listStar);
        binding.lstyeuthich.setAdapter(DataClass.adapterYt);

        ClickLst();
        MoveToMainthemyt();
        record();
        insert();
        xoa();
        setSearchView();

    }
    private void ClickLst(){
        binding.lstyeuthich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri=i;
                Intent intent = new Intent(LikeActivity.this, ContactDetailActivity.class);
                Call calls = DataClass.listStar.get(vitri);
                intent.putExtra("user", calls);
                startActivityForResult(intent, vitri);
            }
        });
    }
    private void insert(){
        Intent intent = getIntent();
        callinsertyt = (Call) intent.getSerializableExtra("themyeuthich");
        if (callinsertyt != null && !callinsertyt.getTen().isEmpty() && !callinsertyt.getTen().isEmpty()) {
           DataClass.listStar.add(new Call(callinsertyt.getId(),
                   callinsertyt.getTen(),
                   callinsertyt.getSdt()));
           DataClass.adapterYt.notifyDataSetChanged();
            binding.lstyeuthich.setAdapter(DataClass.adapterYt);
        }
    }
    private void xoa(){
        binding.lstyeuthich.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlerDialogxoa();
                return false;
            }
        });
    }
    private void setSearchView(){
        binding.searchViewyt.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                searchView =view.findViewById(R.id.searchViewyt);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        DataClass.adapterYt.getFilter().filter(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        DataClass.adapterYt.getFilter().filter(s);
                        if(s.isEmpty()){
                            return true;
                        }
                        return false;
                    }
                });

            }
        });
    }
    private void MoveToMainthemyt(){
        binding.txtthemyt.setOnClickListener(view->{
            startActivity(new Intent(LikeActivity.this, InsertLikeActivity.class));
        });
    }
    private void record(){
        binding.imgbrecord.setOnClickListener(v->{
            Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault());
            intent1.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

            try{
                startActivityForResult(intent1, vitri);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void AlerDialogxoa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LikeActivity.this);
        builder.setTitle("THONG BAO");
        builder.setMessage("BAN CO MUON XOA KO?");
        builder.setCancelable(false);
        builder.setNegativeButton("CO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call calls = DataClass.listStar.get(vitri);
                DataClass.listStar.remove(calls);
                Toast.makeText(LikeActivity.this, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("KO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == vitri && resultCode == RESULT_OK && data != null){
            ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            binding.searchViewyt.setQuery(Objects.requireNonNull(list).get(0), false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}