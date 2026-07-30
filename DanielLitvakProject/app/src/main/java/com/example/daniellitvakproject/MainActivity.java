package com.example.daniellitvakproject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(String language){
        String email = ((EditText)findViewById(R.id.textEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.textPassword)).getText().toString();
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_LONG).show();

            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "You have logged in successfully", Toast.LENGTH_LONG).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("language", language);
                            NavController navController = Navigation.findNavController(MainActivity.this,R.id.fragmentContainerView);
                            navController.navigate(R.id.action_fragmentLogin_to_fragmentAnimalList, bundle);
                        } else {
                            Toast.makeText(MainActivity.this, "login fail", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void register(String language) {
        String email = ((EditText)findViewById(R.id.editTextEmailReg)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextPasswordReg)).getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_LONG).show();

            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Register ok", Toast.LENGTH_LONG).show();
                            NavController navController = Navigation.findNavController(MainActivity.this,R.id.fragmentContainerView);
                            Bundle bundle = new Bundle();
                            bundle.putString("language", language);
                            navController.navigate(R.id.action_fragmentRegister_to_fragmentLogin, bundle);
                        } else {
                            Toast.makeText(MainActivity.this, "register fail", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void sendComment(String animalId, String commentText) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("comments").child(String.valueOf(animalId)).push();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        Comment comment = new Comment(email, commentText);
        ref.setValue(comment);
        Toast.makeText(MainActivity.this, "Comment added", Toast.LENGTH_LONG).show();
    }
}