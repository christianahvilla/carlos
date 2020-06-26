package com.christianahvilla.carlos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.christianahvilla.carlos.Helpers.Encrypt
import com.christianahvilla.carlos.Models.User
import com.christianahvilla.carlos.SQLite.SetDB
import com.christianahvilla.carlos.SQLite.UserDB
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var db: SetDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = SetDB(this@LoginActivity)

        bt_login_login.setOnClickListener{
            login()
        }

        tv_login_are_not_member.setOnClickListener {
            openRegister(this@LoginActivity)
        }

        tv_login_register.setOnClickListener {
            openRegister(this@LoginActivity)
        }

    }

    private fun validData(): Boolean{
        return isValidEmail(et_login_email.text.toString()) && et_login_password.text.isNotBlank()
    }

    private fun login(){
        if (validData()) {
            val userDB = UserDB()
            userDB.findUser(et_login_email.text.toString(), et_login_password.text.toString(), db)
            val intent = Intent(this@LoginActivity, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            Toast.makeText(this@LoginActivity, resources.getString(R.string.invalid_data), Toast.LENGTH_SHORT).show()
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
        val intent = Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
        finish()
    }
}