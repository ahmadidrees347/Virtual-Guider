package io.mapwize.mapwizecomponents;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.mapwize.mapwizecomponents.ui.API.RetrofitClient;
import io.mapwize.mapwizecomponents.ui.API.TeacherStatus;
import io.mapwize.mapwizecomponents.ui.API.TeacherStatusServices;
import io.mapwize.mapwizecomponents.ui.API.UpdateStatus;
import io.mapwize.mapwizecomponents.ui.API.UpdateStatusServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFacultyStatus extends Fragment {
    Context context;
    AppCompatActivity activity;
    String name;
    int status;
    TeacherStatus teacherStatus;
    RadioGroup radioGroupStatus;
    Button btn_updateStatus;

    public EditFacultyStatus() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_faculty_status, container, false);
        context = view.getContext();
        activity = (AppCompatActivity) getActivity();

        SharedPreferences sp = activity.getSharedPreferences("status" , Context.MODE_PRIVATE);
        name = sp.getString("name", null);

        radioGroupStatus = (RadioGroup) view.findViewById(R.id.radioGroupStatus);
        btn_updateStatus = view.findViewById(R.id.btn_updateStatus);

        showStatus(name);
        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radiobtnAvailable) {
                    status = 1;
                } else if (checkedId == R.id.radiobtnNotAvailable) {
                    status = 0;
                }
            }
        });
        btn_updateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateStatus(name, status);
            }
        });

        return view;
    }
    private void UpdateStatus(final String name, int status)
    {
        UpdateStatusServices updateStatusServices = RetrofitClient.getClient().create(UpdateStatusServices.class);
        Call serviceCall = updateStatusServices.UpdateFacultyStatus(name, status);
        serviceCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful())
                {
                    UpdateStatus updateStatus = (UpdateStatus) response.body();

                    if(!updateStatus.getError())
                    {
                        Toast.makeText(getActivity(), updateStatus.getMessage(), Toast.LENGTH_SHORT).show();
                        //showStatus(name);
                    }
                    else
                    {
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
                        if(teacherStatus.getStatus() == 1 ) {
                            status = 1;
                            radioGroupStatus.check(R.id.radiobtnAvailable);
                        }
                        else {
                            status = 0;
                            radioGroupStatus.check(R.id.radiobtnNotAvailable);
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), teacherStatus.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getActivity(), teacherStatus.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
