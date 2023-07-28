package com.example.danhba.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.danhba.R;
import com.example.danhba.adapter.adapter_call;
import com.example.danhba.databinding.ActivityHomeBinding;
import com.example.danhba.model.Call;
import com.example.danhba.util.DataClass;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    public static int vitri;
    private Menu mymenu;
    private int record = 1;
    android.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataClass.listContact = new ArrayList<>();

        showMain();
        themlienhe();
        menu();
        imgbStar();
        imgdanhba();
        lstView();
        record();
        searchView();
        floatingbtn();

    }
    private void showMain(){
        DataClass.listContact = new ArrayList<>();
        DataClass.adapterCall = new adapter_call(HomeActivity.this, R.layout.layout_main1, DataClass.listContact);
        binding.listviewnguoidung.setAdapter(DataClass.adapterCall);
    }
    private void themlienhe(){
        binding.txtthemlienhe.setOnClickListener(view->{
            startActivity(new Intent(HomeActivity.this, AddContactActivity.class));
        });
    }
    private void menu(){
        binding.imgbmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }
    private void imgbStar(){
        binding.imgbStar.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, LikeActivity.class));
        });
    }
    private void lstView(){
        binding.listviewnguoidung.setOnItemClickListener((adapterView, view, i, l) -> {
            DataClass.vitri = i;
            Intent intent = new Intent(HomeActivity.this, ContactDetailActivity.class);
            Call calls = DataClass.listContact.get(DataClass.vitri);
            intent.putExtra("user", calls);
            startActivityForResult(intent, DataClass.vitri);
        });
    }
    private void record(){
        binding.imgbrecord.setOnClickListener(v->{
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Spaek to text");
            try{
                startActivityForResult(intent, record);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchView(){
        binding.searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                searchView =view.findViewById(R.id.searchView);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
            }
        });
    }
    private void floatingbtn(){
        binding.floatingbtn.setOnClickListener(view->{
            shownumberkeybroard();
        });
    }
    private void imgdanhba(){
        binding.imgdanhba.setOnClickListener(view->{
            recreate();
        });
    }

    private void shownumberkeybroard(){
        // nhan trong diem
        binding.edtphim.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager!=null){
            // hien thi phim = showSoftInput
            inputMethodManager.showSoftInput(binding.edtphim, inputMethodManager.SHOW_IMPLICIT);
            binding.edtphim.requestFocus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mymenu = menu;
        return super.onCreateOptionsMenu(menu);
    }
    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(HomeActivity.this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==record && requestCode == RESULT_OK && data != null){
            ArrayList<String> result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            binding.searchView.setQuery(Objects.requireNonNull(result).get(0),false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}