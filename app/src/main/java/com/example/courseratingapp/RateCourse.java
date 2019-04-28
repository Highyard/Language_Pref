package com.example.courseratingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseratingapp.domain.Course;

import org.simplejavamail.MailException;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

public class RateCourse extends AppCompatActivity {

    private static final String TAG = "RateCourseActivity";

    final static String RATING = "RATING";

    private RatingBar subjectRelevance;
    private RatingBar teacherPerformance;
    private RatingBar teacherPreparation;
    private RatingBar teacherFeedback;
    private RatingBar exampleQuality;
    private RatingBar jobOpportunities;


    private TextView subjectText;
    private TextView teacherPerText;
    private TextView teacherPrepText;
    private TextView teacherFeedText;
    private TextView exampleText;
    private TextView jobOppText;

    private Button submit;

    private final Course pythonCourse    = new Course();
    private final Course C_Course        = new Course();
    private final Course JSCourse        = new Course();
    private final Course angularCourse   = new Course();
    private final Course nodeJSCourse    = new Course();
    private final Course javaCourse      = new Course();

    Float pythonRetrievedOverallRating,
            C_RetrievedOverallRating,
            JS_RetrievedOverallRating,
            angularRetrievedOverallRating,
            nodejsRetrievedOverallRating,
            javaRetrievedOverallRating;


    int pythonRetrievedAverageCount,
            C_RetrievedAverageCount,
            JS_RetrievedAverageCount,
            angularRetrievedAverageCount,
            nodejsRetrievedAverageCount,
            javaRetrievedAverageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_course);
        Log.d(TAG, getString(R.string.onCreate));
        init();

        // get Intent from CoursesPageActivity class and store the Action from intent in a String //
        Intent receivedIntent = getIntent();
        final String receivedAction = receivedIntent.getAction();

        final Intent mailIntent = new Intent(Intent.ACTION_SENDTO);

        // Logs the received Action from CoursesPageActivity class, e.g. ACTION_PYTHON = "PYTHON" //
        Log.d(TAG, receivedAction);

        // Depending on the Action received we run the corresponding part of the switch, 1 case for every Course //
        try {

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, getString(R.string.onClick));

                    Context context = getApplication();
                    SharedPreferences sharedPrefs = context.getSharedPreferences(getString(R.string.COURSE_RATINGS_KEY), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();

                    switch (receivedAction) {
                        case (CoursesPageActivity.ACTION_PYTHON):

                            // Fetches the overall accumulated rating value //
                            pythonRetrievedOverallRating = sharedPrefs.getFloat(getString(R.string.python_rating_key), getResources().getInteger(R.integer.defaultValue));

                            // Fetches the average counter (default = 0), otherwise returns 0 //
                            pythonRetrievedAverageCount = sharedPrefs.getInt(getString(R.string.PYTHON_AVERAGE_COUNT_KEY), getResources().getInteger(R.integer.defaultValue));

                            // Check if there is a rating above 0 already stored //
                            if (pythonRetrievedOverallRating != getResources().getInteger(R.integer.defaultValue))
                                // If there is we want to set the overall rating of the python course to that retrieved value//
                                pythonCourse.setCurrentOverallRating(pythonRetrievedOverallRating);

                            // Takes all the ratings, adds them all together and sets this value on course.newRating //
                            pythonCourse.setNewRating(subjectRelevance.getRating() +
                                    teacherPerformance.getRating() +
                                    teacherPreparation.getRating() +
                                    teacherFeedback.getRating() +
                                    exampleQuality.getRating() +
                                    jobOpportunities.getRating());

                            // Takes the new rating and adds it to the already existing overall rating //
                            pythonCourse.setCurrentOverallRating(pythonCourse.getCurrentOverallRating() + pythonCourse.getNewRating());

                            // A new user is rating the course so we increment the counter by 1 //
                            pythonRetrievedAverageCount++;

                            // Calculate and assigns average rating  to Course object//
                            pythonCourse.setAverageRating(pythonCourse.getCurrentOverallRating() / pythonRetrievedAverageCount);

                            // Adds the new overall rating, average rating, and the incremented averageCount to the sharedPreferences CourseRatings.xml file //
                            // Note: Saving the average rating to sharedPreferences is optional, depending
                            // whether we want to retrieve and  show it somewhere else in the App//
                            editor.putFloat(getString(R.string.python_rating_key), pythonCourse.getCurrentOverallRating());
                            editor.putInt(getString(R.string.PYTHON_AVERAGE_COUNT_KEY), pythonRetrievedAverageCount);
                            editor.putFloat(getString(R.string.PYTHON_AVERAGE_RATING_KEY), pythonCourse.getAverageRating());
                            editor.apply();

                            // Assign key and value on intent that is sent back to CoursesPageActivity //
                            Intent pythonCourseIntent = new Intent();
                            pythonCourseIntent.putExtra(RATING, pythonCourse);
                            setResult(RESULT_OK, pythonCourseIntent);

                            // !!! NOTE !!! default_email is currently set to an empty string. //
                            // Change string in the values/strings.xml //
                            // Give our email a recipient, subject, body, and the average rating that they now have //
                            String pythonUriText =
                                            getString(R.string.mailto) + Uri.encode(getString(R.string.default_email)) +
                                            getString(R.string.subject) + Uri.encode(getString(R.string.subject_text)) +
                                            getString(R.string.body) + Uri.encode(getString(R.string.student_new_rating) + pythonCourse.getNewRating() +
                                                    getString(R.string.new_line) +
                                                    getString(R.string.new_average_rating) + pythonCourse.getAverageRating()) + getString(R.string.period);

                            Uri pythonUri = Uri.parse(pythonUriText);

                            mailIntent.setData(pythonUri);
                            startActivity(Intent.createChooser(mailIntent, getString(R.string.app_name)));

                            break;

                        case (CoursesPageActivity.ACTION_C):

                            C_RetrievedOverallRating = sharedPrefs.getFloat(getString(R.string.c_rating_key), getResources().getInteger(R.integer.defaultValue));

                            C_RetrievedAverageCount = sharedPrefs.getInt(getString(R.string.C_AVERAGE_COUNT_KEY), getResources().getInteger(R.integer.defaultValue));

                            if (C_RetrievedOverallRating != getResources().getInteger(R.integer.defaultValue))
                                C_Course.setCurrentOverallRating(C_RetrievedOverallRating);

                            C_Course.setNewRating(subjectRelevance.getRating() +
                                    teacherPerformance.getRating() +
                                    teacherPreparation.getRating() +
                                    teacherFeedback.getRating() +
                                    exampleQuality.getRating() +
                                    jobOpportunities.getRating());

                            C_Course.setCurrentOverallRating(C_Course.getCurrentOverallRating() + C_Course.getNewRating());

                            C_RetrievedAverageCount++;
                            C_Course.setAverageRating(C_Course.getCurrentOverallRating() / C_RetrievedAverageCount);


                            editor.putFloat(getString(R.string.c_rating_key), C_Course.getCurrentOverallRating());
                            editor.putInt(getString(R.string.C_AVERAGE_COUNT_KEY), C_RetrievedAverageCount);
                            editor.putFloat(getString(R.string.C_AVERAGE_RATING_KEY), C_Course.getAverageRating());

                            editor.apply();

                            Intent C_courseIntent = new Intent();
                            C_courseIntent.putExtra(RATING, C_Course);
                            setResult(RESULT_OK, C_courseIntent);


                            String C_UriText =
                                            getString(R.string.mailto) + Uri.encode(getString(R.string.default_email)) +
                                            getString(R.string.subject) + Uri.encode(getString(R.string.subject_text)) +
                                            getString(R.string.body) + Uri.encode(getString(R.string.student_new_rating) + C_Course.getNewRating() +
                                                    getString(R.string.new_line) +
                                                    getString(R.string.new_average_rating) + C_Course.getAverageRating()) + getString(R.string.period);

                            Uri C_Uri = Uri.parse(C_UriText);

                            mailIntent.setData(C_Uri);
                            startActivity(Intent.createChooser(mailIntent, getString(R.string.app_name)));

                            break;

                        case (CoursesPageActivity.ACTION_JS):

                            JS_RetrievedOverallRating = sharedPrefs.getFloat(getString(R.string.js_rating_key), getResources().getInteger(R.integer.defaultValue));

                            JS_RetrievedAverageCount = sharedPrefs.getInt(getString(R.string.JS_AVERAGE_COUNT_KEY), getResources().getInteger(R.integer.defaultValue));

                            if (JS_RetrievedOverallRating != getResources().getInteger(R.integer.defaultValue))
                                JSCourse.setCurrentOverallRating(JS_RetrievedOverallRating);

                            JSCourse.setNewRating(subjectRelevance.getRating() +
                                    teacherPerformance.getRating() +
                                    teacherPreparation.getRating() +
                                    teacherFeedback.getRating() +
                                    exampleQuality.getRating() +
                                    jobOpportunities.getRating());

                            JSCourse.setCurrentOverallRating(JSCourse.getCurrentOverallRating() + JSCourse.getNewRating());

                            JS_RetrievedAverageCount++;

                            JSCourse.setAverageRating(JSCourse.getCurrentOverallRating() / JS_RetrievedAverageCount);

                            editor.putFloat(getString(R.string.js_rating_key), JSCourse.getCurrentOverallRating());
                            editor.putInt(getString(R.string.JS_AVERAGE_COUNT_KEY), JS_RetrievedAverageCount);
                            editor.putFloat(getString(R.string.JS_AVERAGE_RATING_KEY), JSCourse.getAverageRating());
                            editor.apply();

                            Intent JS_courseIntent = new Intent();
                            JS_courseIntent.putExtra(RATING, JSCourse);
                            setResult(RESULT_OK, JS_courseIntent);


                            String JS_UriText =
                                            getString(R.string.mailto) + Uri.encode(getString(R.string.default_email)) +
                                            getString(R.string.subject) + Uri.encode(getString(R.string.subject_text)) +
                                            getString(R.string.body) + Uri.encode(getString(R.string.student_new_rating) + JSCourse.getNewRating() +
                                                    getString(R.string.new_line) +
                                                    getString(R.string.new_average_rating) + JSCourse.getAverageRating()) + getString(R.string.period);

                            Uri JS_Uri = Uri.parse(JS_UriText);

                            mailIntent.setData(JS_Uri);
                            startActivity(Intent.createChooser(mailIntent, getString(R.string.app_name)));

                            break;

                        case (CoursesPageActivity.ACTION_ANGULAR):

                            angularRetrievedOverallRating = sharedPrefs.getFloat(getString(R.string.angular_rating_key), getResources().getInteger(R.integer.defaultValue));

                            angularRetrievedAverageCount = sharedPrefs.getInt(getString(R.string.ANGULAR_AVERAGE_COUNT_KEY), getResources().getInteger(R.integer.defaultValue));

                            if (angularRetrievedOverallRating != getResources().getInteger(R.integer.defaultValue))
                                angularCourse.setCurrentOverallRating(angularRetrievedOverallRating);

                            angularCourse.setNewRating(subjectRelevance.getRating() +
                                    teacherPerformance.getRating() +
                                    teacherPreparation.getRating() +
                                    teacherFeedback.getRating() +
                                    exampleQuality.getRating() +
                                    jobOpportunities.getRating());

                            angularCourse.setCurrentOverallRating(angularCourse.getCurrentOverallRating() + angularCourse.getNewRating());

                            angularRetrievedAverageCount++;

                            angularCourse.setAverageRating(angularCourse.getCurrentOverallRating() / angularRetrievedAverageCount);

                            editor.putFloat(getString(R.string.angular_rating_key), angularCourse.getCurrentOverallRating());
                            editor.putInt(getString(R.string.ANGULAR_AVERAGE_COUNT_KEY), angularRetrievedAverageCount);
                            editor.putFloat(getString(R.string.ANGULAR_AVERAGE_RATING_KEY), angularCourse.getAverageRating());
                            editor.apply();

                            Intent angularCourseIntent = new Intent();
                            angularCourseIntent.putExtra(RATING, angularCourse);
                            setResult(RESULT_OK, angularCourseIntent);

                            String angularUriText =
                                            getString(R.string.mailto) + Uri.encode(getString(R.string.default_email)) +
                                            getString(R.string.subject) + Uri.encode(getString(R.string.subject_text)) +
                                            getString(R.string.body) + Uri.encode(getString(R.string.student_new_rating) + angularCourse.getNewRating() +
                                                    getString(R.string.new_line) +
                                                    getString(R.string.new_average_rating) + angularCourse.getAverageRating()) + getString(R.string.period);

                            Uri angularUri = Uri.parse(angularUriText);

                            mailIntent.setData(angularUri);
                            startActivity(Intent.createChooser(mailIntent, getString(R.string.app_name)));
                            break;

                        case (CoursesPageActivity.ACTION_NODEJS):

                            nodejsRetrievedOverallRating = sharedPrefs.getFloat(getString(R.string.nodejs_rating_key), getResources().getInteger(R.integer.defaultValue));

                            nodejsRetrievedAverageCount = sharedPrefs.getInt(getString(R.string.NODEJS_AVERAGE_COUNT_KEY), getResources().getInteger(R.integer.defaultValue));

                            if (nodejsRetrievedOverallRating != getResources().getInteger(R.integer.defaultValue))
                                nodeJSCourse.setCurrentOverallRating(nodejsRetrievedOverallRating);

                            nodeJSCourse.setNewRating(subjectRelevance.getRating() +
                                    teacherPerformance.getRating() +
                                    teacherPreparation.getRating() +
                                    teacherFeedback.getRating() +
                                    exampleQuality.getRating() +
                                    jobOpportunities.getRating());

                            nodeJSCourse.setCurrentOverallRating(nodeJSCourse.getCurrentOverallRating() + nodeJSCourse.getNewRating());

                            nodejsRetrievedAverageCount++;

                            nodeJSCourse.setAverageRating(nodeJSCourse.getCurrentOverallRating() / nodejsRetrievedAverageCount);

                            editor.putFloat(getString(R.string.nodejs_rating_key), nodeJSCourse.getCurrentOverallRating());
                            editor.putInt(getString(R.string.NODEJS_AVERAGE_COUNT_KEY), nodejsRetrievedAverageCount);
                            editor.putFloat(getString(R.string.NODEJS_AVERAGE_RATING_KEY), nodeJSCourse.getAverageRating());
                            editor.apply();

                            Intent nodeJS_courseIntent = new Intent();
                            nodeJS_courseIntent.putExtra(RATING, nodeJSCourse);
                            setResult(RESULT_OK, nodeJS_courseIntent);

                            String nodeJS_UriText =
                                            getString(R.string.mailto) + Uri.encode(getString(R.string.default_email)) +
                                            getString(R.string.subject) + Uri.encode(getString(R.string.subject_text)) +
                                            getString(R.string.body) + Uri.encode(getString(R.string.student_new_rating) + nodeJSCourse.getNewRating() +
                                                    getString(R.string.new_line) +
                                                    getString(R.string.new_average_rating) + nodeJSCourse.getAverageRating()) + getString(R.string.period);

                            Uri nodeJS_Uri = Uri.parse(nodeJS_UriText);

                            mailIntent.setData(nodeJS_Uri);
                            startActivity(Intent.createChooser(mailIntent, getString(R.string.app_name)));
                            break;

                        case (CoursesPageActivity.ACTION_JAVA):

                            javaRetrievedOverallRating = sharedPrefs.getFloat(getString(R.string.java_rating_key), getResources().getInteger(R.integer.defaultValue));

                            javaRetrievedAverageCount = sharedPrefs.getInt(getString(R.string.JAVA_AVERAGE_COUNT_KEY), getResources().getInteger(R.integer.defaultValue));

                            if (javaRetrievedOverallRating != getResources().getInteger(R.integer.defaultValue))
                                javaCourse.setCurrentOverallRating(javaRetrievedOverallRating);

                            javaCourse.setNewRating(subjectRelevance.getRating() +
                                    teacherPerformance.getRating() +
                                    teacherPreparation.getRating() +
                                    teacherFeedback.getRating() +
                                    exampleQuality.getRating() +
                                    jobOpportunities.getRating());

                            javaCourse.setCurrentOverallRating(javaCourse.getCurrentOverallRating() + javaCourse.getNewRating());

                            javaRetrievedAverageCount++;
                            javaCourse.setAverageRating(javaCourse.getCurrentOverallRating() / javaRetrievedAverageCount);

                            editor.putFloat(getString(R.string.java_rating_key), javaCourse.getCurrentOverallRating());
                            editor.putInt(getString(R.string.JAVA_AVERAGE_COUNT_KEY), javaRetrievedAverageCount);
                            editor.putFloat(getString(R.string.JAVA_AVERAGE_RATING_KEY), javaCourse.getAverageRating());
                            editor.apply();

                            Intent javaCourseIntent = new Intent();
                            javaCourseIntent.putExtra(RATING, javaCourse);
                            setResult(RESULT_OK, javaCourseIntent);

                            String javaUriText =
                                            getString(R.string.mailto) + Uri.encode(getString(R.string.default_email)) +
                                            getString(R.string.subject) + Uri.encode(getString(R.string.subject_text)) +
                                            getString(R.string.body) + Uri.encode(getString(R.string.student_new_rating) + javaCourse.getNewRating() +
                                                    getString(R.string.new_line) +
                                                    getString(R.string.new_average_rating) + javaCourse.getAverageRating()) + getString(R.string.period);

                            Uri javaUri = Uri.parse(javaUriText);

                            mailIntent.setData(javaUri);
                            startActivity(Intent.createChooser(mailIntent, getString(R.string.app_name)));
                            break;
                    }
                    // Free this activity from memory once we have our result //
                    RateCourse.this.finish();
                }
            });
        } catch (NullPointerException e) {
            Log.d(TAG, e.toString());
            Log.d(TAG, getString(R.string.nullPointerAction));
        } catch (NoClassDefFoundError err) {
            //Toast.makeText(RateCourse.this, "Couldn't send mail.", Toast.LENGTH_LONG).show();
        } catch (MailException error) {
            Log.d(TAG, error.toString());
            //Toast.makeText(RateCourse.this, "Couldn't send mail.", Toast.LENGTH_LONG).show();
        }

    }

    protected void init () {
        Log.d(TAG, getString(R.string.init));
        subjectRelevance = findViewById(R.id.r1);
        teacherPerformance = findViewById(R.id.r2);
        teacherPreparation = findViewById(R.id.r3);
        teacherFeedback = findViewById(R.id.r4);
        exampleQuality = findViewById(R.id.r5);
        jobOpportunities = findViewById(R.id.r6);

        subjectText = findViewById(R.id.tv1);
        teacherPerText = findViewById(R.id.tv2);
        teacherPrepText = findViewById(R.id.tv3);
        teacherFeedText = findViewById(R.id.tv4);
        exampleText = findViewById(R.id.tv5);
        jobOppText = findViewById(R.id.tv6);

        submit = findViewById(R.id.b1);
    }

}

//simplejavamail is a wrapper for the JavaMail API,
// however conflicting dependencies means this does not work in android,
// and as a result is disabled
//                            Email email = EmailBuilder.startingBlank()
//                                    .from("CourseRatingApp", "EMAIL_FROM_HERE")
//                                    .to("Teacher", "EMAIL_TO_HERE")
//                                    .withSubject("A new student has rated your course!")
//                                    .withPlainText("Your current course overall rating is: " + String.valueOf(pythonCourse.getCurrentOverallRating()))
//                                    .buildEmail();
//
//                            Mailer mailer = MailerBuilder.withSMTPServer("smtp.gmail.com", 587, "EMAIL_FROM_HERE", "PASSWORD")
//                                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
//                                    .buildMailer();
//
//                            mailer.sendMail(email);