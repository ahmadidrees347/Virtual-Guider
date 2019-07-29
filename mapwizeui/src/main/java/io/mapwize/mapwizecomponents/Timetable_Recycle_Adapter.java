package io.mapwize.mapwizecomponents;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.mapwize.mapwizecomponents.ui.API.TimetableArray;

import java.util.List;

public class Timetable_Recycle_Adapter extends RecyclerView.Adapter<Timetable_Recycle_Adapter.myViewHolder> {
    List<TimetableArray> datalist;
    Context context;

    public Timetable_Recycle_Adapter(Context context, List<TimetableArray> data) {
        this.context = context;
        this.datalist = data;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_timetable_recycle, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        //datalist.get(position);
        holder.txt_room.setText(datalist.get(position).getRName().toString());
        holder.txt_time.setText(datalist.get(position).getTTime().toString());
        holder.txt_course.setText(datalist.get(position).getTCourse().toString());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_room,txt_time, txt_course;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_room = itemView.findViewById(R.id.txt_room);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_course = itemView.findViewById(R.id.txt_course);
        }
    }
}

