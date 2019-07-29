package io.mapwize.mapwizecomponents;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import io.mapwize.mapwizecomponents.ui.API.RetrofitClient;
import io.mapwize.mapwizecomponents.ui.API.Timetable;
import io.mapwize.mapwizecomponents.ui.API.TimetableArray;
import io.mapwize.mapwizecomponents.ui.API.TimetableServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTimetable extends Fragment {
    Spinner spnShift, spnSection, spnDay, spnDegree, spnDepartment, spnSemester;
    Button btn_timetable;
    Timetable<TimetableArray> timetableArray;
    RecyclerView recyclerView;
    String shift, section, day, degree, department, semester;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_view_timetable, container, false);

        recyclerView = view.findViewById(R.id.TimetablerecyleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RadioGroup radioGroupSection = (RadioGroup) view.findViewById(R.id.radioGroupSection);
        RadioGroup radioGroupDegree = (RadioGroup) view.findViewById(R.id.radioGroupDegree);
        RadioGroup radioGroupShift = (RadioGroup) view.findViewById(R.id.radioGroupShift);
        RadioGroup radioGroupDepartment = (RadioGroup) view.findViewById(R.id.radioGroupDepartment);

        radioGroupSection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                if (checkedId == R.id.radiobtnSectionA) {
                    section = "A";
                } else if (checkedId == R.id.radiobtnSectionB) {
                    section = "B";
                } else if (checkedId == R.id.radiobtnSectionC) {
                    section = "C";
                }
            }
        });
        radioGroupDegree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                if (checkedId == R.id.radiobtnDegreeBS) {
                    degree = "BS";
                } else if (checkedId == R.id.radiobtnDegreeMSc) {
                    degree = "MSc";
                }
            }
        });
        radioGroupShift.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                if (checkedId == R.id.radiobtnShiftMorning) {
                    shift = "Morning";
                } else if (checkedId == R.id.radiobtnShiftEvening) {
                    shift = "Evening";
                }
            }
        });
        radioGroupDepartment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                if (checkedId == R.id.radiobtnDepartmentCS) {
                    department = "CS";
                } else if (checkedId == R.id.radiobtnDepartmentIT) {
                    department = "IT";
                } else if (checkedId == R.id.radiobtnDepartmentSE) {
                    department = "SE";
                }
            }
        });

        //spnShift = view.findViewById(R.id.spn_Shift);
        //spnSection = view.findViewById(R.id.spn_Section);
        spnDay = view.findViewById(R.id.spn_Day);
        //spnDegree = view.findViewById(R.id.spn_Degree);
        //spnDepartment = view.findViewById(R.id.spn_Department);
        spnSemester = view.findViewById(R.id.spn_Semester);

        btn_timetable = view.findViewById(R.id.btn_timetable);
        btn_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shift = (String) spnShift.getSelectedItem();
                //section = (String) spnSection.getSelectedItem();
                day = (String) spnDay.getSelectedItem();
                //degree = (String) spnDegree.getSelectedItem();
                //department = (String) spnDepartment.getSelectedItem();
                semester = (String) spnSemester.getSelectedItem();

                showTimetable(shift, section, day, degree, department, semester);
            }
        });

        return view;
    }

    private void showTimetable(String shift, String section, String day, String degree, String department, String semester)
    {
        timetableArray = new Timetable<>();
        TimetableServices timetableServices = RetrofitClient.getClient().create(TimetableServices.class);
        Call serviceCall = timetableServices.showTimetable(shift, section, day, degree, department, semester);
        serviceCall.enqueue(new Callback<Timetable<TimetableArray>>() {
            @Override
            public void onResponse(Call<Timetable<TimetableArray>> call, Response<Timetable<TimetableArray>> response) {
                if(response.isSuccessful())
                {
                    timetableArray = response.body();
                    if(!timetableArray.getError())
                    {
                        List<TimetableArray> timetableArrayList = timetableArray.getTimetableArray();

                        Timetable_Recycle_Adapter data= new Timetable_Recycle_Adapter(getActivity(), timetableArrayList);
                        recyclerView.setAdapter(data);
                        //Toast.makeText(showSchedule.this, scheduleArray.getScheduleArray().get(0).getRName(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), timetableArray.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Timetable<TimetableArray>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
