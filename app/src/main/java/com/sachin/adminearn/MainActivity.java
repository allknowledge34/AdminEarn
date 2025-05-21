package com.sachin.adminearn;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sachin.adminearn.Adapters.TrHistoryAdapter;
import com.sachin.adminearn.Models.PaymentRequestModel;
import com.sachin.adminearn.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menu;
    View header;
    ArrayList<PaymentRequestModel> list;
    TrHistoryAdapter adapter;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();

        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.historyRecy.setLayoutManager(layoutManager);

        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.navigationView);
        menu = findViewById(R.id.menu);
        header = navigationView.getHeaderView(0);

        menu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.leaderboard) {
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        // Setup RecyclerView
        adapter = new TrHistoryAdapter(this,list);
        binding.historyRecy.setAdapter(adapter);

        firestore.collectionGroup("request")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list.clear();
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            PaymentRequestModel user = snapshot.toObject(PaymentRequestModel.class);

                            String userId = snapshot.getReference().getParent().getParent().getId();
                            String requestId = snapshot.getId();

                            user.setUserId(userId);
                            user.setRequestId(requestId);

                            list.add(user);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}