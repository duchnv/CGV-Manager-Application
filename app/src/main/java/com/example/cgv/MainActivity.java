package com.example.cgv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvPhim;
    private PhimAdapter phimAdapter;
    private List<Phim> phimList;
    private Button btThemPhim;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        getListPhim();
    }

    private void initUi(){
        rcvPhim = findViewById(R.id.rcv_phim);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvPhim.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvPhim.addItemDecoration(dividerItemDecoration);
        btThemPhim = findViewById(R.id.bt_themPhim);
        phimList = new ArrayList<>();
        phimAdapter = new PhimAdapter(phimList, new PhimAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(Phim phim) {
                openDiaUpdatePhim(phim);
            }

            @Override
            public void onClickDeleteItem(Phim phim) {
                onClickDeletePhim(phim);
            }
        });
        rcvPhim.setAdapter(phimAdapter);
        btThemPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemMoiPhim();
            }
        });
    }

    private void getListPhim(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("phim");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Phim phim = snapshot.getValue(Phim.class);
                if(phim != null){
                    phimList.add(phim);
                    phimAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Phim phim = snapshot.getValue(Phim.class);
                if(phim == null||phimList == null||phimList.isEmpty()){
                    return;
                }
                for(int i = 0; i <phimList.size(); i++){
                    if(phim.getId() == phimList.get(i).getId()){
                        phimList.set(i, phim);
                        break;
                    }
                }
                phimAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Phim phim = snapshot.getValue(Phim.class);
                if(phim == null||phimList == null||phimList.isEmpty()){
                    return;
                }
                for(int i = 0; i <phimList.size(); i++){
                    if(phim.getId() == phimList.get(i).getId()){
                        phimList.remove(phimList.get(i));
                        break;
                    }
                }
                phimAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void openDiaUpdatePhim(Phim phim){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_phim);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText eTen = dialog.findViewById(R.id.et_ten);
        EditText eGiave = dialog.findViewById(R.id.et_giave);
        EditText eGio = dialog.findViewById(R.id.et_gio);
        EditText eTheLoai = dialog.findViewById(R.id.et_theloai);
        EditText eMota = dialog.findViewById(R.id.et_mota);
        Button btLuu = dialog.findViewById(R.id.bt_luu);
        Button btHuy = dialog.findViewById(R.id.bt_huy);

        eTen.setText(phim.getTen());
        eGiave.setText(String.valueOf(phim.getGia()));
        eTheLoai.setText(phim.getTheloai());
        eMota.setText(phim.getMota());
        eGio.setText(phim.getGio());
        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("phim");
                phim.setTen(eTen.getText().toString().trim());
                phim.setGia(Integer.parseInt(eGiave.getText().toString().trim()));
                phim.setGio(eGio.getText().toString().trim());
                phim.setTheloai(eTheLoai.getText().toString().trim());
                phim.setMota(eMota.getText().toString().trim());
                myRef.child(String.valueOf(phim.getId())).updateChildren(phim.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(MainActivity.this, "Update data success", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }
    private void ThemMoiPhim(){
        Intent i = new Intent();
        i.setClass(MainActivity.this,ThemPhimAdmin.class);
        startActivity(i);
    }
    private void onClickDeletePhim(Phim phim){
        new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage("Bạn có chắc chắn muốn xóa bản ghi này không?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("phim");
                        myRef.child(String.valueOf(phim.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(MainActivity.this, "Delete data success", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel",null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mysearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void mysearch(String newText){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("phim");
        Query query = myRef.orderByChild("ten").startAt(newText).endAt(newText+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(phimList != null|| phimList.isEmpty()){
                    phimList.clear();
                    for (DataSnapshot phimSnapshot : snapshot.getChildren()) {
                        Phim phim = phimSnapshot.getValue(Phim.class);
                        phimList.add(phim);
                        phimAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}