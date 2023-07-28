package com.example.danhba.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.danhba.R;
import com.example.danhba.databinding.ActivityEditContactBinding;
import com.example.danhba.model.Call;
import com.example.danhba.util.DataClass;

public class EditContactActivity extends AppCompatActivity {
    private ActivityEditContactBinding binding;
    private Menu mymenu;
    Call calls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();
        Fix();
        back();
        menu();

    }
    private void getData(){
        Intent intent = getIntent();
        calls = (Call) intent.getSerializableExtra("sua");
        binding.edtten.setText(calls.getTen());
        binding.edtsdt.setText(calls.getSdt()+"");
    }
    private void Fix(){
        calls.setTen(binding.edtten.getText().toString());
        calls.setSdt(Integer.parseInt(binding.edtsdt.getText().toString()));
        DataClass.listContact.add(calls);
        DataClass.adapterCall.notifyDataSetChanged();
        trong();
        Toast.makeText(this, "Thanh Cong", Toast.LENGTH_SHORT).show();
    }
    private void back(){
        binding.btnback.setOnClickListener(view->{
            trong();
        });
    }
    private void menu(){
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
        startActivity(new Intent(EditContactActivity.this, HomeActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuthem, menu);
        mymenu = menu;
        return super.onCreateOptionsMenu(menu);
    }
    private void showMenu(View view){
        PopupMenu popupMenu = new PopupMenu(EditContactActivity.this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menuthem, popupMenu.getMenu());
        popupMenu.show();
    }
}