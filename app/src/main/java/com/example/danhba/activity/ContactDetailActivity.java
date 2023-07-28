package com.example.danhba.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.danhba.R;
import com.example.danhba.adapter.adapter_yt;
import com.example.danhba.databinding.ActivityContactDetailBinding;
import com.example.danhba.model.Call;
import com.example.danhba.util.DataClass;

import java.util.ArrayList;

public class ContactDetailActivity extends AppCompatActivity {
    public static ActivityContactDetailBinding binding;
    Call calls;
    int vitri = HomeActivity.vitri;
    private Menu mymenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataClass.listStar = new ArrayList<>();
        DataClass.listStar.add(new Call(1,"dfg",123));
        DataClass.adapterYt = new adapter_yt(this, R.layout.layout_main1,DataClass.listStar);

        getData();

        imgbyeuthich();
        menu();
        Back();
        imgcall();

    }
    private void getData(){
        Intent intent = getIntent();
        calls = (Call) intent.getSerializableExtra("user");
        binding.txtuser.setText(calls.getTen());
        binding.txtsdtmain2.setText(calls.getSdt()+"");

        binding.txtusersinglemain2.setText(calls.getTen().substring(0,1));
    }
    private void imgbyeuthich(){
        DataClass.listStar.add(calls);
        DataClass.adapterYt.notifyDataSetChanged();
    }
    private void menu(){
        binding.imgbmenumain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }
    private void Back(){
        binding.imageButtonback.setOnClickListener(view->{
            startActivity(new Intent(ContactDetailActivity.this, HomeActivity.class));
        });
    }
    private void imgcall(){
        if(ContextCompat.checkSelfPermission(ContactDetailActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ContactDetailActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},vitri );
        }
        binding.imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phone_intent = new Intent(Intent.ACTION_CALL);
                phone_intent.setData(Uri.parse("tel:" + binding.txtsdtmain2.getText().toString()+""));
                startActivity(phone_intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumain2, menu);
        mymenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(ContactDetailActivity.this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menumain2, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sua:
                        Intent intentsua = new Intent(ContactDetailActivity.this, EditContactActivity.class);
                        Call calls = DataClass.listContact.get(HomeActivity.vitri);
                        intentsua.putExtra("sua", calls);
                        startActivityForResult(intentsua, HomeActivity.vitri);
                        return true;
                    case R.id.xoa:
                        xoa();
                        return true;
                }
                return onOptionsItemSelected(item);
            }
        });
        popupMenu.show();
    }
    private void xoa(){}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}