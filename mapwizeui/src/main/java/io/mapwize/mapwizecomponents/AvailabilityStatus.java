package io.mapwize.mapwizecomponents;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import io.mapwize.mapwizecomponents.ui.API.RetrofitClient;
import io.mapwize.mapwizecomponents.ui.API.ShowTeacherNames;
import io.mapwize.mapwizecomponents.ui.API.TeacherArray;
import io.mapwize.mapwizecomponents.ui.API.TeacherStatus;
import io.mapwize.mapwizecomponents.ui.API.TeacherStatusServices;
import io.mapwize.mapwizecomponents.ui.API.TeaherNamesServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailabilityStatus extends Fragment {
    Spinner spinnerTeacher;
    Button btn_showStatus;
    TextView txtStatus;
    Context context;
    AppCompatActivity activity;
    static ShowTeacherNames<TeacherArray> teacherArray;
    TeacherStatus teacherStatus;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_availability_status, container, false);
        context = view.getContext();
        activity = (AppCompatActivity) getActivity();
        showTeacherName();
        spinnerTeacher = (Spinner) view.findViewById(R.id.spn_teacherName);
        btn_showStatus = (Button) view.findViewById(R.id.btn_status);
        txtStatus = (TextView) view.findViewById(R.id.txt_status);

        btn_showStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacher = (String) spinnerTeacher.getSelectedItem();
                showStatus(teacher);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void showStatus(String name)
    {
        teacherStatus = new TeacherStatus();
        //Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
        TeacherStatusServices teacherStatusServices = RetrofitClient.getClient().create(TeacherStatusServices.class);
        Call serviceCall = teacherStatusServices.showStatus(name);
        serviceCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful())
                {
                    TeacherStatus teacherStatus = (TeacherStatus) response.body();
                    if(!teacherStatus.getError())
                    {
                        if(teacherStatus.getStatus() == 0) {
                            txtStatus.setText("Not Available");
                        }
                        else if(teacherStatus.getStatus() == 1) {
                            txtStatus.setText("Available");
                        }
                        //Toast.makeText(showTeacher.this, teacherStatus.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Toast.makeText(showTeacher.this, teacherStatus.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                //Toast.makeText(showTeacher.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showTeacherName()
    {
        teacherArray = new ShowTeacherNames<>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Get Records Plz Wait...");
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
                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(activity, teacherArray.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(activity, teacherArray.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShowTeacherNames<TeacherArray>> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}

