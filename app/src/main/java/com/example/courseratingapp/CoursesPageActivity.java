package com.example.courseratingapp;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseratingapp.domain.Course;
import com.example.courseratingapp.domain.User;

import static com.example.courseratingapp.RateCourse.RATING;


public class CoursesPageActivity extends AppCompatActivity {

    private final static String TAG = "CoursesPageActivity";

    private static final String TOP_TEXT = "";
    private static final String BOTTOM_TEXT = "";

    // Used to check which method gets run in the RateCourse.java class //
    public static final String ACTION_PYTHON = "PYTHON";
    public static final String ACTION_C = "C";
    public static final String ACTION_JS = "JS";
    public static final String ACTION_ANGULAR = "ANGULAR";
    public static final String ACTION_NODEJS = "NODEJS";
    public static final String ACTION_JAVA = "JAVA";


    // Request codes for startActivityForResult method //
    private static final int ACTIVITY_PYTHON = 1;
    private static final int ACTIVITY_C = 2;
    private static final int ACTIVITY_JS = 3;
    private static final int ACTIVITY_ANGULAR = 4;
    private static final int ACTIVITY_NODEJS = 5;
    private static final int ACTIVITY_JAVA = 6;


    private TextView tvLoggedInAs;
    private TextView tvWelcomeMsg;
    private Button btnPython;
    private Button btnC;
    private Button btnJS;
    private Button btnAngular;
    private Button btnNodejs;
    private Button btnJava;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_page);
        Log.d(TAG, getString(R.string.onCreate));
        init();
        Intent receivedIntent = getIntent();
        user = receivedIntent.getParcelableExtra(MainActivity.USER_KEY);


        if (savedInstanceState != null) {
            tvLoggedInAs.setText(savedInstanceState.getString(TOP_TEXT));
            tvWelcomeMsg.setText(savedInstanceState.getString(BOTTOM_TEXT));
        }

        tvLoggedInAs.setText(getString(R.string.LoggedInAs) + user.getEmail());

        // OnClickListeners for the 6 different courses. An Action is set that will be read by the RateCourse class to determine which Course to run //

        btnPython.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, getString(R.string.python_button_clicked));
                Intent pythonRedirect = new Intent(CoursesPageActivity.this, RateCourse.class);
                pythonRedirect.setAction(ACTION_PYTHON);
                startActivityForResult(pythonRedirect, ACTIVITY_PYTHON);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, getString(R.string.C_button_clicked));
                Intent CRedirect = new Intent(CoursesPageActivity.this, RateCourse.class);
                CRedirect.setAction(ACTION_C);
                startActivityForResult(CRedirect, ACTIVITY_C);
            }
        });

        btnJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, getString(R.string.js_button_clicked));
                Intent JSRedirect = new Intent(CoursesPageActivity.this, RateCourse.class);
                JSRedirect.setAction(ACTION_JS);
                startActivityForResult(JSRedirect, ACTIVITY_JS);
            }
        });

        btnAngular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, getString(R.string.angular_button_clicked));
                Intent angularRedirect = new Intent(CoursesPageActivity.this, RateCourse.class);
                angularRedirect.setAction(ACTION_ANGULAR);
                startActivityForResult(angularRedirect, ACTIVITY_ANGULAR);
            }
        });

        btnNodejs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, getString(R.string.nodejs_button_clicked));
                Intent nodeJSRedirect = new Intent(CoursesPageActivity.this, RateCourse.class);
                nodeJSRedirect.setAction(ACTION_NODEJS);
                startActivityForResult(nodeJSRedirect, ACTIVITY_NODEJS);
            }
        });

        btnJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, getString(R.string.java_button_clicked));
                Intent javaRedirect = new Intent(CoursesPageActivity.this, RateCourse.class);
                javaRedirect.setAction(ACTION_JAVA);
                startActivityForResult(javaRedirect, ACTIVITY_JAVA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, getString(R.string.on_activity_result));



        // Depending on which requestCode comes back from the RateCourseActivity class,
        // we show the corresponding score given by the user when we return to this Activity.
        // if the user hit the back button on the phone the result is canceled and we notify the user.
        switch (requestCode) {
            case(ACTIVITY_PYTHON):
                if (resultCode == RESULT_OK) {
                    Course pythonCourseRating = data.getParcelableExtra(RATING);
                    Float pythonRating = pythonCourseRating.getNewRating();
                    Toast.makeText(CoursesPageActivity.this, "Thank you for rating your teacher's course! You rated them: " + pythonRating.toString(), Toast.LENGTH_LONG).show();
                }
                if (resultCode == RESULT_CANCELED) {
                    tvWelcomeMsg.setText(getString(R.string.result_canceled_answers));
                }
                break;

            case(ACTIVITY_C):
                if (resultCode == RESULT_OK){
                    Course C_CourseRating = data.getParcelableExtra(RATING);
                    Float C_Rating = C_CourseRating.getNewRating();
                    Toast.makeText(CoursesPageActivity.this, "Thank you for rating your teacher's course! You rated them: " + C_Rating.toString(), Toast.LENGTH_LONG).show();
                }
                if (resultCode == RESULT_CANCELED) {
                    tvWelcomeMsg.setText(getString(R.string.result_canceled_answers));
                }
                break;

            case(ACTIVITY_JS):
                if (resultCode == RESULT_OK){
                    Course JSCourseRating = data.getParcelableExtra(RATING);
                    Float JSRating = JSCourseRating.getNewRating();
                    Toast.makeText(CoursesPageActivity.this, "Thank you for rating your teacher's course! You rated them: " + JSRating.toString(), Toast.LENGTH_LONG).show();
                }
                if (resultCode == RESULT_CANCELED) {
                    tvWelcomeMsg.setText(getString(R.string.result_canceled_answers));
                }

                break;

            case(ACTIVITY_ANGULAR):
                if (resultCode == RESULT_OK){
                    Course angularCourseRating = data.getParcelableExtra(RATING);
                    Float angularRating = angularCourseRating.getNewRating();
                    Toast.makeText(CoursesPageActivity.this, "Thank you for rating your teacher's course! You rated them: " + angularRating.toString(), Toast.LENGTH_LONG).show();
                }
                if (resultCode == RESULT_CANCELED) {
                    tvWelcomeMsg.setText(getString(R.string.result_canceled_answers));
                }

                break;

            case(ACTIVITY_NODEJS):
                if (resultCode == RESULT_OK){
                    Course nodeJSCourseRating = data.getParcelableExtra(RATING);
                    Float nodeJSRating = nodeJSCourseRating.getNewRating();
                    Toast.makeText(CoursesPageActivity.this, "Thank you for rating your teacher's course! You rated them: " + nodeJSRating.toString(), Toast.LENGTH_LONG).show();
                }
                if (resultCode == RESULT_CANCELED) {
                    tvWelcomeMsg.setText(getString(R.string.result_canceled_answers));
                }

                break;

            case(ACTIVITY_JAVA):
                if (resultCode == RESULT_OK){
                    Course javaCourseRating = data.getParcelableExtra(RATING);
                    Float javaRating = javaCourseRating.getNewRating();
                    Toast.makeText(CoursesPageActivity.this, "Thank you for rating your teacher's course! You rated them: " + javaRating.toString(), Toast.LENGTH_LONG).show();
                }
                if (resultCode == RESULT_CANCELED) {
                    tvWelcomeMsg.setText(getString(R.string.result_canceled_answers));
                }

                break;

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, getString(R.string.onSaveInstanceState));

        // Saves the top text state as the rating that the student gave a given course //
        outState.putString(TOP_TEXT, getString(R.string.LoggedInAs) + user.getEmail());
        outState.putString(BOTTOM_TEXT, tvWelcomeMsg.getText().toString());

    }

    protected void init() {
        Log.d(TAG, getString(R.string.init));
        tvWelcomeMsg = findViewById(R.id.tv1);
        tvLoggedInAs = findViewById(R.id.tv2);
        btnPython = findViewById(R.id.b1);
        btnC = findViewById(R.id.b2);
        btnJS = findViewById(R.id.b3);
        btnAngular = findViewById(R.id.b4);
        btnNodejs = findViewById(R.id.b5);
        btnJava = findViewById(R.id.b6);
    }

}