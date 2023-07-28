package com.example.danhba.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.danhba.R;
import com.example.danhba.databinding.ActivityAddContactBinding;
import com.example.danhba.model.Call;
import com.example.danhba.util.DataClass;

public class AddContactActivity extends AppCompatActivity {
    private ActivityAddContactBinding binding;
    private Menu mymenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        add();
        btnBack();
        imgbmenu();

    }
    private void add(){
        binding.btnluu.setOnClickListener(view->{
            if(binding.edtsdt.getText().toString().isEmpty()){
                Toast.makeText(this, "Nhap SDT", Toast.LENGTH_SHORT).show();
            }else if(binding.edtten.getText().toString().isEmpty()){
                Toast.makeText(this, "Nhap Ten", Toast.LENGTH_SHORT).show();
            }else{
                Call calls = new Call();
                calls.setTen(binding.edtten.getText().toString());
                calls.setSdt(Integer.parseInt(binding.edtsdt.getText().toString()));
                DataClass.listContact.add(calls);
                DataClass.adapterCall.notifyDataSetChanged();
                trong();
                Toast.makeText(this, "Thanh Cong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void btnBack(){
        binding.btnback.setOnClickListener(view->{
            trong();
        });
    }
    private void imgbmenu(){
        binding.imgbmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });
    }

    private void trong(){
        binding.edtten.setText("");
        binding.edtsdt.setText("");
        startActivity(new Intent(AddContactActivity.this, HomeActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuthem, menu);
        mymenu = menu;
        return super.onCreateOptionsMenu(menu);
    }
    private void showMenu(View view){
        PopupMenu popupMenu = new PopupMenu(AddContactActivity.this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menuthem, popupMenu.getMenu());
        popupMenu.show();
    }
}