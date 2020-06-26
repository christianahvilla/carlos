package com.christianahvilla.carlos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.christianahvilla.carlos.Helpers.Encrypt
import com.christianahvilla.carlos.Models.User
import com.christianahvilla.carlos.SQLite.SetDB
import com.christianahvilla.carlos.SQLite.UserDB
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var db: SetDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        db = SetDB(this@RegisterActivity)

        bt_register_register.setOnClickListener{
            val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        tv_register_already.setOnClickListener {
            openRegister(this@RegisterActivity)
        }

        tv_register_login.setOnClickListener {
            openRegister(this@RegisterActivity)
        }

        bt_register_register.setOnClickListener{
            saveUser()
        }
    }

    private fun validData(): Boolean{
        return et_register_name.text.isNotBlank() && isValidEmail(et_register_email.text.toString()) && et_register_password.text.isNotBlank()
    }

    private fun saveUser(){
        if (validData()) {
            val userDB = UserDB()
            val user = User(
                0,
                et_register_name.text.toString(),
                et_register_email.text.toString(),
                et_register_password.text.toString(),
                1
            )
            userDB.saveUser(user, db)
            val intent = Intent(this@RegisterActivity, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            Toast.makeText(this@RegisterActivity, resources.getString(R.string.invalid_data), Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    private fun openRegister(context: Context){
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
        finish()
    }
}