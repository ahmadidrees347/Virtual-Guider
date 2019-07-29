package io.mapwize.mapwizecomponents;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.mapwize.mapwizecomponents.ui.API.ScheduleArray;

import java.util.List;

import retrofit2.Callback;


public class Data_Recyle_Adapter extends RecyclerView.Adapter<Data_Recyle_Adapter.myViewHolder> {
    List<ScheduleArray> datalist;
    Context context;

    public Data_Recyle_Adapter(Context context, List<ScheduleArray> data) {
        this.context = context;
        this.datalist = data;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_data_recycle, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        //datalist.get(position);
        holder.txt_room.setText(datalist.get(position).getRName().toString());
        holder.txt_time.setText(datalist.get(position).getTTime().toString());
        //holder.txt_shift.setText(datalist.get(position).getTShift().toString());
        //holder.txt_section.setText(datalist.get(position).getTSection().toString());
        //holder.txt_day.setText(datalist.get(position).getTDay().toString());
        holder.txt_degree.setText(datalist.get(position).getTDegree().toString());
        holder.txt_degree.append("-"+datalist.get(position).getTDepartment().toString());
        holder.txt_degree.append(" ("+datalist.get(position).getTSemester().toString());
        holder.txt_degree.append(datalist.get(position).getTSection().toString()+")");
        holder.txt_degree.append("-"+datalist.get(position).getTShift().toString().substring(0,1));
        //holder.txt_department.setText(datalist.get(position).getTDepartment().toString());
        //holder.txt_semester.setText(datalist.get(position).getTSemester().toString());
        holder.txt_course.setText(datalist.get(position).getTCourse().toString());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_room,txt_time, txt_shift, txt_section, txt_day, txt_degree, txt_department, txt_semester, txt_course;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_room = itemView.findViewById(R.id.txt_room);
            txt_time = itemView.findViewById(R.id.txt_time);
            //txt_shift = itemView.findViewById(R.id.txt_shift);
            //txt_section = itemView.findViewById(R.id.txt_section);
            //txt_day = itemView.findViewById(R.id.txt_day);
            txt_degree = itemView.findViewById(R.id.txt_degree);
            //txt_department = itemView.findViewById(R.id.txt_department);
            //txt_semester = itemView.findViewById(R.id.txt_semester);
            txt_course = itemView.findViewById(R.id.txt_course);
        }
    }
}

