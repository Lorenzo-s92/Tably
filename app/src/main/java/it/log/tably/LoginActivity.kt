package it.log.tably

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import java.util.*
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.firebase.ui.auth.ResultCodes.*


private const val RC_SIGN_IN = 123


class LoginActivity : AppCompatActivity() {


   private lateinit var mAuth: FirebaseAuth
   private val providers = Arrays.asList(AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build())


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            // val response = IdpResponse.fromResultIntent(data)
            if (resultCode == OK) {
                // Successfully signed in
                goToMainActivity()
            } else {
                // Sign in failed, check response for error code
                // ...
                //TODO
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        signIn.setOnClickListener({
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN)
        })

    }

    override fun onResume() {
        super.onResume()

        // If user is already logged in, skip login
        if (mAuth.currentUser != null) {
            Toast.makeText(this, "Logged as: ${mAuth.currentUser!!.displayName}", Toast.LENGTH_LONG).show()
            goToMainActivity()
        }

    }

    private fun goToMainActivity() {

        // star to load DB from Firebase
        TablyApplication.loadPlayersDB()

        // Go to MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}
