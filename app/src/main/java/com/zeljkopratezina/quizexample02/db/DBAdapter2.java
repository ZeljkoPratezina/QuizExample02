package com.zeljkopratezina.quizexample02.db;

/**
 * Created by ZELJKO on 8/23/2016.
 */

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zeljkopratezina.quizexample02.model.Question;



public class DBAdapter2 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "quizexample2";

    // Table name
    private static final String TABLE_QUESTION2 = "question";


    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUESION = "question";
    private static final String KEY_ANSWER = "answer"; //correct option
    private static final String KEY_OPTA= "opta"; //option a
    private static final String KEY_OPTB= "optb"; //option b
    private static final String KEY_OPTC= "optc"; //option c
    private static final String KEY_OPTD= "optd"; //option d

    private SQLiteDatabase myDatabase;

    public DBAdapter2(Context context2) {
        super(context2, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        myDatabase=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION2 + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUESION
                + " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
                +KEY_OPTB +" TEXT, "+KEY_OPTC +" TEXT, "+KEY_OPTD+" TEXT)";




        db.execSQL(sql);

        addQuestions();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION2);

        // Create tables again
        onCreate(db);
    }

    public int rowCount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION2;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor2 = db.rawQuery(selectQuery, null);
        row=cursor2.getCount();
        return row;
    }



    public List<Question> getAllQuestions() {

        List<Question> quesList2 = new ArrayList<Question>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION2;
        myDatabase=this.getReadableDatabase();

        Cursor cursor2 = myDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor2.moveToFirst()) {
            do {
                Question quest2 = new Question();
                quest2.setId(cursor2.getInt(0));
                quest2.setQUESTION(cursor2.getString(1));
                quest2.setANSWER(cursor2.getString(2));
                quest2.setOptionA(cursor2.getString(3));
                quest2.setOptionB(cursor2.getString(4));
                quest2.setOptionC(cursor2.getString(5));
                quest2.setOptionD(cursor2.getString(6));

                quesList2.add(quest2);


            } while (cursor2.moveToNext());
        }
        // return quest list
        return quesList2;
    }

    private void addQuestions()
    {
        //format is question-option1-option2-option3-option4-answer

        Question q1=new Question("What is widely considered to be the largest lake on Earth (measured by surface area)?", "Caspian Sea", "Lake Superior", "Lake Victoria", "Great Bear Lake", "Caspian Sea");
        this.addQuestion(q1);

        Question q2=new Question("What is the highest mountian on Earth ( from sea level to top)?", "Kilimanjaro", "Matterhorn", "Mount Everest", "K2", "Mount Everest");
        this.addQuestion(q2);

        Question q3=new Question("What is the northermost capital of an independent nation in th world?", "Ottawa", "Reykjavik", "Helsinki", "Ulaanbaatar", "Reykjavik");
        this.addQuestion(q3);

        Question q4=new Question("What is the Earth's largest continent by population", "South America", "Africa", "Europe", "North America", "Africa");
        this.addQuestion(q4);

        Question q5=new Question("What is Earth's largest continent by surface size?", "Europe", "Asia", "Africa", "North America", "Asia");
        this.addQuestion(q5);

        Question q6=new Question("What is generally considered to be the largest island on Earth? (Australia is regarded as continent)", "New Guinea", "Madagascar", "Greenland", "Borneo", "Greenland");
        this.addQuestion(q6);

        Question q7=new Question("What is Earth's largest ocean by surface size ?", "Pacific", "Atlantic", "Indian", "Artic", "Pacific");
        this.addQuestion(q7);

        Question q8=new Question("What is Earth appriximate water vs.land coverage ratio?", "51 to 49", "81 to 19", "71 to 29", "61 to 39", "71 to 29");
        this.addQuestion(q8);

        Question q9=new Question("Which country has the largest population in South America", "Argentina", "Colombia", "Peru", "Brazil", "Brazil");
        this.addQuestion(q9);

        Question q10=new Question("Which country is the second largest in the world by surface area?", "Brazil", "Canada", "China", "USA", "Canada");
        this.addQuestion(q10);


    }


    // Adding new question
    public void addQuestion(Question quest2) {

        ContentValues values2 = new ContentValues();
        values2.put(KEY_QUESION, quest2.getQUESTION());
        values2.put(KEY_ANSWER, quest2.getANSWER());
        values2.put(KEY_OPTA, quest2.getOptionA());
        values2.put(KEY_OPTB, quest2.getOptionB());
        values2.put(KEY_OPTC, quest2.getOptionC());
        values2.put(KEY_OPTD, quest2.getOptionD());

        // Inserting Row
        myDatabase.insert(TABLE_QUESTION2, null, values2);
    }




}
