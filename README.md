# CourseRatingApp
Mandatory_1_Android_App

# OBS! 
## This program will not work outright, read the NOTES section for more info.

# Deploy
* First download the zip of this repository and unzip it in the desired location on your machine.
* In Android Studio click **File** --> **New** --> **Import Project** --> **Select the unzipped project**
* Finally click Run and select either your desired emulator, or a connected android device.

## App functionality:
* 1.1 Login and validation
* 1.2 Rating Courses
* 1.3 Prompting user to send email to teacher of chosen course
* 1.4 Passing Parcelable models between activities
* 1.5 Using the SharedPreferences interface to store data locally
* 1.6 Persists certain data through orientation change

## Describing the App
* 1.1 Login and validation
This App uses SharedPreferences to store users, and to check if an email already exists. If the email does not exist,
the user is redirected back to the login screen with his info already entered into the required fields, and needs only tap "Sign In".

* 1.2 Rating Courses 
After loging in, the user can rate one of six courses. When the user taps a course, he is redirected to the proper page for rating
the corresponding course. The proper Course is started via the startAcitivtyForResult() method, and once the user is done rating the course,
the average rating for the course is displayed to the user upon returning to the previous page.

* 1.3 Prompting user to send email to teacher of chosen course
When the user taps "Submit answers" button, he's prompted to choose an email app and thereafter send the email with the average rating
to the teacher.

* 1.4 Passing Parcelable models between activities
Parcelable models in this app are the User and Course class. They are passed between activies via Intents.

* 1.5 Using SharedPreferences intertface to store data
This is already covered partly in section 1.1, however other than user input and validation, the SharedPreferences is also used to
store the ratings that the user outputs to the app. The app retrieves data from the SharedPreferences, manipulates the data and stores 
it in the same place.

* 1.6 Persists certain data through orientation change
This is only done when logging in, to tell the user which account they are logged in on.



## NOTES
The default email that receives a notification when someone rates a course is empty, and to test this functionality the user will have
to change the default_email string in values/strings.xml to the desired email. This is the case for all six courses.
