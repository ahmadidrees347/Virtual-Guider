package io.mapwize.mapwizecomponents;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.mapwize.mapwizecomponents.ui.API.FacultyArray;
import io.mapwize.mapwizecomponents.ui.API.FacultyLogin;
import io.mapwize.mapwizecomponents.ui.API.FacultyLoginServices;
import io.mapwize.mapwizecomponents.ui.API.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class signInFragment extends Fragment {
    EditText edt_email, edt_password;
    Button btnSignIn;
    Context context;
    AppCompatActivity activity;
    private ProgressDialog progressDialog;
    FacultyLogin facultyLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        edt_email = view.findViewById(R.id.email);
        edt_password = view.findViewById(R.id.password);
        btnSignIn = view.findViewById(R.id.btnSignIn);

        context = view.getContext();
        activity = (AppCompatActivity) getActivity();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacultyLogin();

            }
        });

        return view;
    }
    private void FacultyLogin()
    {
        final String email, password;
        email = edt_email.getText().toString();
        password = edt_password.getText().toString();

        facultyLogin = new FacultyLogin();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Authentiation...");
        progressDialog.show();

        FacultyLoginServices facultyLoginServices = RetrofitClient.getClient().create(FacultyLoginServices.class);
        Call serviceCall = facultyLoginServices.FacultyLogin(email,password);
        serviceCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful())
                {
                    progressDialog.dismiss();
                    FacultyLogin faculty = (FacultyLogin) response.body();
                    if(!faculty.getError())
                    {
                        String email = faculty.getFacultyArray().get(0).getFEmail().toString();
                        String name = faculty.getFacultyArray().get(0).getFName().toString();
                        SharedPreferences sp = activity.getSharedPreferences("status" ,Context.MODE_PRIVATE);
                        sp.edit().putBoolean("Login",true).commit();
                        sp.edit().putString("email", email).commit();
                        sp.edit().putString("name", name).commit();

                        /*SharedPreferences.Editor editor = activity.getSharedPreferences("status", Context.MODE_PRIVATE).edit();
                        editor.putBoolean("Login", true);
                        editor.apply();*/

                        activity.setTitle("View Availability Status");
                        AvailabilityStatus availabilityStatus = new AvailabilityStatus();
                        activity.getSupportFragmentManager().beginTransaction().replace( 0 ,availabilityStatus).commit();

                        activity.recreate();
                        //getActivity().finish();
                        //startActivity(getActivity().getIntent());
                    }
                    else
                    {
                        Toast.makeText(getActivity(), faculty.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}

