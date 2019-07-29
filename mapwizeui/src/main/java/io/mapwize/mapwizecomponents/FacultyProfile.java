package io.mapwize.mapwizecomponents;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FacultyProfile extends Fragment {
    Context context;
    AppCompatActivity activity;
    TextView txt_name, txt_email, txt_phone, txt_address;

    public FacultyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faculty_profile, container, false);
        context = view.getContext();
        activity = (AppCompatActivity) getActivity();

        txt_name = view.findViewById(R.id.text_name);
        txt_email = view.findViewById(R.id.text_email);
        txt_phone = view.findViewById(R.id.text_phone);
        txt_address = view.findViewById(R.id.text_loc);

        SharedPreferences sp = activity.getSharedPreferences("status" , Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        String email = sp.getString("email", null);
        //Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

        txt_name.setText(name);
        txt_email.setText(email);
        // Inflate the layout for this fragment
        return view;
    }

}
