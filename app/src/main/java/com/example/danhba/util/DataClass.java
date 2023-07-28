package com.example.danhba.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.speech.RecognizerIntent;
import android.widget.SearchView;

import androidx.annotation.NonNull;

import com.example.danhba.adapter.adapter_call;
import com.example.danhba.adapter.adapter_yt;
import com.example.danhba.model.Call;
import com.example.danhba.model.Database;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class DataClass {
    public static List<Call> listContact;
    public static List<Call> listStar;
    public static adapter_yt adapterYt;
    public static adapter_call adapterCall;
    public static Database database;
    public static int vitri;
    public static SearchView searchView;

    public static void cursorContact(){
        Cursor cursor = database.select("SELECT * FROM CALL");
        listContact.clear();
        while(cursor.moveToNext()){
            listContact.add(new Call(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            ));
        }
        Collections.sort(listContact, new Comparator<Call>() {
            @Override
            public int compare(Call call, Call t1) {
                return (call.getTen().substring(0,1).compareTo(t1.getTen().substring(0,1)));
            }
        });
        adapterCall.notifyDataSetChanged();
    }
    public static void cursorStar(){
        Cursor cursor = database.select("SELECT * FROM CALL");
        listStar.clear();
        while(cursor.moveToNext()){
            listStar.add(new Call(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            ));
        }
        Collections.sort(listStar, new Comparator<Call>() {
            @Override
            public int compare(Call call, Call t1) {
                return (call.getTen().substring(0,1).compareTo(t1.getTen().substring(0,1)));
            }
        });
        adapterYt.notifyDataSetChanged();
    }
    public static void Create(@NonNull Context context){
        database = new Database(context.getApplicationContext(), "callsss", null, 1);
        database.query("CREATE TABLE IF NOT EXISTS CALL(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN TEXT, SDT TEXT)");
    }

    public static void record( int vitri){
            Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault());
            intent1.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

            try{
                startActivityForResult(intent1, vitri);
            }catch (Exception e){
            }
    }
    private static void startActivityForResult(Intent intent1, int vitri) {
}
//    private void xoa(Context context){
//        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
//        builder.setTitle("THONG BAO");
//        builder.setMessage("BAN CO MUON XOA KO?");
//        builder.setCancelable(false);
//        builder.setNegativeButton("CO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                calls = DataClass.listContact.get(HomeActivity.vitri);
//                HomeActivity.database.DELETE_YT(ContactDetailActivity.this, calls.getId());
//                DataClass.listContact.remove(HomeActivity.vitri);
//                DataClass.cursorContact();
//                Toast.makeText(ContactDetailActivity.this, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        builder.setPositiveButton("KO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//
//        });
//        builder.show();
//    }
}
