package nimeesh.nimeeshProjects.remind

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nimeesh.nimeeshProjects.remind.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {

    private lateinit var binding: ActivityLoginScreenBinding
    lateinit var userName: EditText
    lateinit var password: EditText
    lateinit var signIn: TextView
    lateinit var pwEye: ImageView
    lateinit var backToast: Toast
    private var backPressedTime: Long = 0
    lateinit var sharedPreferences: SharedPreferences
    val MyPREFERENCES = "MyPrefs"
    val email = "username"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        userName = binding.username
        password = binding.password
        signIn   = binding.signIn
        pwEye = binding.showPasswordButton


        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)

        if (sharedPreferences.contains(email)){

            val d = Intent(applicationContext, DashBoard::class.java)
            startActivity(d)



        }


          // password hide or visible
        pwEye.setOnClickListener{

            if (password.transformationMethod.javaClass.simpleName == "PasswordTransformationMethod"){
                password.transformationMethod = SingleLineTransformationMethod()
                pwEye.setImageResource(R.drawable.password_eye_crossed)



            }else{

                password.transformationMethod = PasswordTransformationMethod()
                pwEye.setImageResource(R.drawable.password_eye)
            }

            password.setSelection(password.text.length)
    }


        signIn.setOnClickListener {

            if (userName.text.isEmpty() || password.text.isEmpty()) {

                Toast.makeText(applicationContext, "Enter credentials", Toast.LENGTH_SHORT).show()
            } else if (userName.text.toString() == "username" && password.text.toString() == "123") {

                val editor = sharedPreferences.edit()
                editor.putString(email, userName.text.toString())
                editor.commit()



                Toast.makeText(this@LoginScreen, "Login Successful", Toast.LENGTH_LONG).show()

                CoroutineScope(Dispatchers.Main).launch {
                    delay(700)
                    val d = Intent(applicationContext, DashBoard::class.java)
                    startActivity(d)
                }
            } else {

                Toast.makeText(applicationContext, "Invalid credentials", Toast.LENGTH_SHORT).show()
                userName.setText("")
                userName.requestFocus()
                password.setText("")
            }


        }
}


    override fun onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){

            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(a)
            finishAffinity()

            super.onBackPressed()
            return

        }else{

            backToast = Toast.makeText(baseContext,"press back again to exit",Toast.LENGTH_SHORT)
            backToast.show()


        }

        backPressedTime = System.currentTimeMillis()



    }

}