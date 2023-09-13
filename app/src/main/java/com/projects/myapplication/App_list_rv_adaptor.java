package com.projects.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class App_list_rv_adaptor extends RecyclerView.Adapter<App_list_rv_adaptor.viewHolder> {
    Context context;
    ArrayList<AppModel> student;
    AlertDialog.Builder builder;

    public App_list_rv_adaptor(Context context, ArrayList<AppModel> student) {
        this.context = context;
        this.student = student;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.app_rv, parent, false);
        return new viewHolder(view);
    }

    @Override
    public int getItemCount() {
        return student.size();
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        AppModel appModel = student.get(position);
        holder.app_name.setText(appModel.getName());
        holder.permission.setText(appModel.getPermissions()+" permissions");
        builder = new AlertDialog.Builder(context);
        holder.appBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<String> arr;
                builder.setTitle("List of permissions");
                arr = new ArrayAdapter<String>(context,com.google.android.material.R.layout.support_simple_spinner_dropdown_item, appModel.getPermissions_name());
                builder.setAdapter(arr, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(appModel.getLink()));
                context.startActivity(intent);
            }
        });
    }
    public class viewHolder extends RecyclerView.ViewHolder {

        TextView app_name;
        TextView permission;
        CardView appBase;
        Button link;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            link = itemView.findViewById(R.id.link);
            appBase = itemView.findViewById(R.id.appBase);
            app_name = itemView.findViewById(R.id.appname);
            permission = itemView.findViewById(R.id.no_perm);
        }
    }
}
