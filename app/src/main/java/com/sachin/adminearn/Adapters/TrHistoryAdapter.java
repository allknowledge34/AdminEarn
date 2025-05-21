package com.sachin.adminearn.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sachin.adminearn.Models.PaymentRequestModel;
import com.sachin.adminearn.R;
import com.sachin.adminearn.databinding.ItemHistoryBinding;

import java.util.ArrayList;

public class TrHistoryAdapter extends RecyclerView.Adapter<TrHistoryAdapter.videHolder> {

    Context context;
    ArrayList<PaymentRequestModel> list;

    public TrHistoryAdapter(Context context, ArrayList<PaymentRequestModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public videHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new videHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull videHolder holder, int position) {

        final PaymentRequestModel model = list.get(position);

        String status = model.getStatus();
        int currentCoin = model.getCoins();
        String methode = model.getPaymentMethod();

        holder.binding.payMethod.setText(model.getPaymentMethod());
        holder.binding.paymentDt.setText(model.getPaymentDetails());
        // holder.binding.payStatus.setText(model.getStatus());
        holder.binding.date.setText(model.getDate());


        double earn = currentCoin * 0.001;
        holder.binding.earn.setText("(" + "₹ " + earn + "" + ")");


        if (status.equals("false")) {

            holder.binding.payStatus.setText("pending");
        } else {

            holder.binding.payStatus.setText("success");
            holder.binding.payStatus.setBackgroundResource(R.drawable.sucessbtn);
        }

        if (methode.equals("Paypal")) {

            holder.binding.logo.setImageResource(R.drawable.paypal);
        } else if (methode.equals("Paytm")) {

            holder.binding.logo.setImageResource(R.drawable.phonepe);
        }
        if (model.getStatus().equals("false")) {
            holder.binding.payStatus.setOnClickListener(v -> {
                updateStatusInFirestore(model, position);
            });
        } else {
            holder.binding.payStatus.setClickable(false);
        }
    }

    private void updateStatusInFirestore(PaymentRequestModel model, int position) {
        FirebaseFirestore.getInstance()
                .collection("redeem")
                .document(model.getUserId())
                .collection("request")
                .document(model.getRequestId())
                .update("status", "true")
                .addOnSuccessListener(aVoid -> {
                    model.setStatus("true");
                    notifyItemChanged(position);
                    Toast.makeText(context, "Status Change", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class videHolder extends RecyclerView.ViewHolder {

        ItemHistoryBinding binding;
        public videHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemHistoryBinding.bind(itemView);
        }
    }

}