package io.mapwize.mapwizeuicomponents


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mapbox.mapboxsdk.Mapbox.getApplicationContext
import io.mapwize.mapwizecomponents.AvailabilityStatus
import io.mapwize.mapwizecomponents.ui.API.FacultyLogin
import io.mapwize.mapwizecomponents.ui.API.FacultyLoginServices
import io.mapwize.mapwizecomponents.ui.API.RetrofitClient
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.fragment_signin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 *
 */
class signinFragment : Fragment() {

    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var btnSignIn: Button
    var activity: AppCompatActivity? = null

    var facultyLogin = io.mapwize.mapwizecomponents.ui.API.FacultyLogin()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signin, container, false)

        edt_email = view.findViewById<EditText>(io.mapwize.mapwizecomponents.R.id.email)
        edt_password = view.findViewById<EditText>(io.mapwize.mapwizecomponents.R.id.password)
        btnSignIn = view.findViewById<Button>(io.mapwize.mapwizecomponents.R.id.btnSignIn)

        activity = getActivity() as AppCompatActivity?

        btnSignIn.setOnClickListener(View.OnClickListener {
            FacultyLogin()
        })
        return view
    }
    private fun FacultyLogin() {
        val email: String
        val password: String
        email = edt_email.text.toString()
        password = edt_password.text.toString()

        val facultyLoginServices = RetrofitClient.getClient().create(FacultyLoginServices::class.java)
        val serviceCall = facultyLoginServices.FacultyLogin(email, password)
        serviceCall.enqueue( object :  Callback<FacultyLogin> {

            override fun onResponse(call: Call<FacultyLogin>, response: Response<FacultyLogin>) {
                if (response.isSuccessful) {

                    val faculty = response.body()

                    if (!faculty!!.error) {
                        val email = faculty.facultyArray[0].fEmail.toString()
                        val name = faculty.facultyArray[0].fName.toString()
                        val sp = getActivity()?.getSharedPreferences("status", Context.MODE_PRIVATE)
                        sp?.edit()?.putBoolean("Login", true)?.commit()
                        sp?.edit()?.putString("email", email)?.commit()
                        sp?.edit()?.putString("name", name)?.commit()

                        val availabilityStatus = AvailabilityStatus()
                        //activity?.supportFragmentManager?.beginTransaction()?.replace(fragment.id, availabilityStatus)?.commit()

                        activity?.recreate()
                    }
                    else {
                        Toast.makeText(getActivity(), faculty.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<FacultyLogin>, t: Throwable) {
                Toast.makeText(getActivity(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}

