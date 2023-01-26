package com.example.chatappwithfirebase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.chatappwithfirebase.model.UserModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupActivity :AppCompatActivity() {

    lateinit var email:EditText
    lateinit var name:EditText
    lateinit var password:EditText
    lateinit var signup:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        email=findViewById(R.id.signupActivity_edittext_email)
        name=findViewById(R.id.signupActivity_edittext_name)
        password=findViewById(R.id.signupActivity_edittext_password)
        signup=findViewById(R.id.signupActivity_button_signup)


        signup.setOnClickListener{
            if(email.text == null || name.text ==null || password.text == null){
               return@setOnClickListener
            }
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener{
                    val userModel = UserModel()
                    userModel.userName=name.text.toString()
                    var uid = it.result.user!!.uid
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel.userName)
                    //val database : FirebaseDatabase = Firebase.database("https://chatapp-with-firebase-5bd1e-default-rtdb.firebaseio.com/")
                    //val myRef : DatabaseReference = database.getReference("message")
                    //myRef.setValue("안녕 반가워!")
                }
        }
    }
}