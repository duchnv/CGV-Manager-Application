package com.example.cgv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThemPhimAdmin extends AppCompatActivity {
    private EditText etId,etTen,etTheloai,etMota,etGio,etGia;
    private Button btLuu,btHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phim_admin);
        initUi();
        btLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phim phim = new Phim();
                phim.setId(Integer.parseInt(etId.getText().toString().trim()));
                phim.setTen(etTen.getText().toString().trim());
                phim.setTheloai(etTheloai.getText().toString().trim());
                phim.setMota(etMota.getText().toString().trim());
                phim.setGia(Integer.parseInt(etGia.getText().toString().trim()));
                phim.setGio(etGio.getText().toString().trim());
                LuuPhimMoi(phim);
            }
        });
        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initUi(){
        etId = findViewById(R.id.et_themId);
        etTen = findViewById(R.id.et_themTen);
        etTheloai = findViewById(R.id.et_themTheloai);
        etMota = findViewById(R.id.et_themMota);
        etGio = findViewById(R.id.et_themGio);
        etGia = findViewById(R.id.et_themGia);
        btLuu = findViewById(R.id.btLuuThem);
        btHuy = findViewById(R.id.btHuyThem);
    }

    private void LuuPhimMoi(Phim phim){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("phim");
        myRef.child(String.valueOf(phim.getId())).setValue(phim, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ThemPhimAdmin.this, "Add data success", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}