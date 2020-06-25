package com.christianahvilla.carlos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.christianahvilla.carlos.SQLite.SetDB
import com.christianahvilla.carlos.SQLite.UserDB
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val monitor = Runnable {
            mHandler.postDelayed(openWelcome(this),2500)
        }

        val ani = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        iv_splash_image.startAnimation(ani)
        tv_splash_app_name.startAnimation(ani)
        monitor.run()
    }

    private fun openWelcome(context: Context): Runnable {
        return Runnable{
            val setDB = SetDB(this@SplashActivity)
            loggedUser(this@SplashActivity, setDB)
        }
    }

    private fun loggedUser(context: Context, setDB: SetDB){
        val userDB = UserDB()

        val user = userDB.loggedUser(setDB)

        if (user != null) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
            finish()
        } else {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            finish()
        }

    }

}
