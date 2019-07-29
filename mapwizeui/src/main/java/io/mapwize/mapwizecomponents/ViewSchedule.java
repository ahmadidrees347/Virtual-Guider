package io.mapwize.mapwizecomponents;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import io.mapwize.mapwizecomponents.ui.API.RetrofitClient;
import io.mapwize.mapwizecomponents.ui.API.ScheduleArray;
import io.mapwize.mapwizecomponents.ui.API.ScheduleData;
import io.mapwize.mapwizecomponents.ui.API.ScheduleServices;
import io.mapwize.mapwizecomponents.ui.API.ShowTeacherNames;
import io.mapwize.mapwizecomponents.ui.API.TeacherArray;
import io.mapwize.mapwizecomponents.ui.API.TeaherNamesServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSchedule extends Fragment {
    Spinner spinnerTeacher, spinnerDay;
    Button btn_schedule;
    ShowTeacherNames<TeacherArray> teacherArray;
    ScheduleData<ScheduleArray> scheduleArray;
    private ProgressDialog progressDialog;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_schedule, container, false);
        recyclerView = view.findViewById(R.id.recyleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        spinnerTeacher = view.findViewById(R.id.spn_teacherName);
        spinnerDay = view.findViewById(R.id.spn_Day);
        btn_schedule = view.findViewById(R.id.btn_schedule);
        showTeacherName();
        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacher = (String) spinnerTeacher.getSelectedItem();
                String day = (String) spinnerDay.getSelectedItem();
                showSchedule(teacher, day);
            }
        });

        return view;
    }

    private void showSchedule(String name, String day)
    {
        scheduleArray = new ScheduleData<ScheduleArray>();
        ScheduleServices scheduleServices = RetrofitClient.getClient().create(ScheduleServices.class);
        Call serviceCall = scheduleServices.showSchedule(name, day);
        serviceCall.enqueue(new Callback<ScheduleData<ScheduleArray>>() {
            @Override
            public void onResponse(Call<ScheduleData<ScheduleArray>> call, Response<ScheduleData<ScheduleArray>> response) {
                if(response.isSuccessful())
                {
                    scheduleArray = response.body();
                    if(!scheduleArray.getError())
                    {
                        List<ScheduleArray> scheduleArrayList = scheduleArray.getScheduleArray();

                        Data_Recyle_Adapter data= new Data_Recyle_Adapter(getActivity(), scheduleArrayList);
                        recyclerView.setAdapter(data);
                        //Toast.makeText(getActivity(), scheduleArray.getScheduleArray().get(0).getRName(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), scheduleArray.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ScheduleData<ScheduleArray>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showTeacherName()
    {
        teacherArray = new ShowTeacherNames<>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Get Result Plz Wait...");
        progressDialog.show();
        TeaherNamesServices teaherNamesServices = RetrofitClient.getClient().create(TeaherNamesServices.class);
        Call serviceCall = teaherNamesServices.showTeacherName();
        serviceCall.enqueue(new Callback<ShowTeacherNames<TeacherArray>>() {
            @Override
            public void onResponse(Call<ShowTeacherNames<TeacherArray>> call, Response<ShowTeacherNames<TeacherArray>> response) {
                if(response.isSuccessful())
                {
                    progressDialog.dismiss();
                    teacherArray = response.body();

                    if(!teacherArray.getError())
                    {
                        List<TeacherArray> teacherArrayList = teacherArray.getTeacherArray();
                        //Toast.makeText(showTeacher.this, teacherArrayList.get(0).getFName(), Toast.LENGTH_SHORT).show();

                        List<String> spnTeacherArray = new ArrayList<>();
                        spnTeacherArray.add("Select Teacher");
                        for(int i=0;i<teacherArrayList.size();i++)
                        {
                            spnTeacherArray.add(teacherArrayList.get(i).getFName());
                        }
                        try
                        {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                                    spnTeacherArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerTeacher.setAdapter(adapter);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), teacherArray.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), teacherArray.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShowTeacherNames<TeacherArray>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}
