package com.example.login;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity1 extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, View.OnClickListener {
    private TextView mDateText, mTimeText;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;

    private EditText UserName, UserAge, UserMobileNo;
    private Spinner TicketType, ServiceType, City1, City2;
    private Button BookTicket1, BookedTicket1;
    private String mMessage;
    private String mTime;
    private String mDate;

    private String[] Ticket;
    private String[] ServiceTrain;
    private String[] ServiceBus;
    private String[] ServiceFlight;
    private String[] ServiceConcert;
    private String[] Concert;
    private String[] ListOfCities;
    private String[] Cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        UserName = (EditText)findViewById(R.id.user_name);
        UserAge = (EditText)findViewById(R.id.lage);
        UserMobileNo = (EditText)findViewById(R.id.lmobile);
        mDateText = (TextView) findViewById(R.id.set_date);
        mTimeText = (TextView) findViewById(R.id.set_time);
        BookTicket1 = (Button) findViewById(R.id.bookticket2);
        BookTicket1.setOnClickListener(this);
        BookedTicket1 = (Button) findViewById(R.id.bookedticket);
        BookedTicket1.setOnClickListener(this);

        Ticket = getResources().getStringArray(R.array.ticket_type);
        ServiceTrain = getResources().getStringArray(R.array.train_type);
        ServiceBus = getResources().getStringArray(R.array.bus_type);
        ServiceFlight = getResources().getStringArray(R.array.flight_type);
        ServiceConcert = getResources().getStringArray(R.array.live_concert);
        Concert = getResources().getStringArray(R.array.person_in_concert);
        ListOfCities = getResources().getStringArray(R.array.india_top_places);
        Cities = getResources().getStringArray(R.array.places);

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        mDateText.setText(mDate);
        mTimeText.setText(mTime);

        TicketType = (Spinner)findViewById(R.id.spinner1);
        ServiceType = (Spinner)findViewById(R.id.spinner2);
        City1 = (Spinner)findViewById(R.id.spinner3);
        City2 = (Spinner)findViewById(R.id.spinner4);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity1.this,
                android.R.layout.simple_spinner_item,Ticket);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TicketType.setPrompt("Select a Service for Ticket");

        TicketType.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        //TicketType.setAdapter(adapter);
        TicketType.setOnItemSelectedListener(this);
    }

    // On clicking Time picker
    public void setTime(View v){
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity1.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                if (minute < 10) {
                    if (hourOfDay > 11) {
                        if (hourOfDay > 12) {
                            hourOfDay = hourOfDay - 12;
                        }
                        mTime = hourOfDay + ":" + "0" + minute + " PM";
                    }
                    else {
                        if (hourOfDay == 0){
                            hourOfDay = hourOfDay + 12;
                        }
                        mTime = hourOfDay + ":" + "0" + minute + " AM";
                    }
                } else {
                    if (hourOfDay > 11) {
                        if (hourOfDay > 12) {
                            hourOfDay = hourOfDay - 12;
                        }
                        mTime = hourOfDay + ":" + minute + " PM";
                    }
                    else {
                        if (hourOfDay == 0){
                            hourOfDay = hourOfDay + 12;
                        }
                        mTime = hourOfDay + ":" + minute + " AM";
                    }
                }
                mTimeText.setText(mTime);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    // On clicking Date picker
    public void setDate(View v){
        Calendar mcurrentDate = Calendar.getInstance();
        int mmYear = mcurrentDate.get(Calendar.YEAR);
        int mmMonth = mcurrentDate.get(Calendar.MONTH);
        int mmDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(MainActivity1.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear ++;
                mDay = dayOfMonth;
                mMonth = monthOfYear;
                mYear = year;
                mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
                mDateText.setText(mDate);
            }
        }, mmYear, mmMonth, mmDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month ++;
        mDay = dayOfMonth;
        mMonth = month;
        mYear = year;
        mDate = dayOfMonth + "/" + month + "/" + year;
        mDateText.setText(mDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            if (hourOfDay > 11) {
                if (hourOfDay > 12) {
                    hourOfDay = hourOfDay - 12;
                }
                mTime = hourOfDay + ":" + "0" + minute + " PM";
            }
            else {
                if (hourOfDay == 0){
                    hourOfDay = hourOfDay + 12;
                }
                mTime = hourOfDay + ":" + "0" + minute + " AM";
            }
        } else {
            if (hourOfDay > 11) {
                if (hourOfDay > 12) {
                    hourOfDay = hourOfDay - 12;
                }
                mTime = hourOfDay + ":" + minute + " PM";
            }
            else {
                if (hourOfDay == 0){
                    hourOfDay = hourOfDay + 12;
                }
                mTime = hourOfDay + ":" + minute + " AM";
            }
        }
        mTimeText.setText(mTime);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        //String selectedClass = parent.getItemAtPosition(position).toString();
        switch (parent.getId()) {
            case R.id.spinner1:
            switch (position) {
                case 1:
                    // Whatever you want to happen when the first item gets selected
                    ServiceType.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity1.this,
                            android.R.layout.simple_spinner_item, ServiceBus);

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    ServiceType.setAdapter(
                            new NothingSelectedSpinnerAdapter(
                                    adapter1,
                                    R.layout.contact_spinner_row_nothing_selected1,
                                    // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                                    this));
                    //TicketType.setAdapter(adapter);
                    ServiceType.setOnItemSelectedListener(this);
                    break;
                case 2:
                    // Whatever you want to happen when the second item gets selected
                    ServiceType.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainActivity1.this,
                            android.R.layout.simple_spinner_item, ServiceTrain);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    ServiceType.setAdapter(
                            new NothingSelectedSpinnerAdapter(
                                    adapter2,
                                    R.layout.contact_spinner_row_nothing_selected2,
                                    // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                                    this));
                    //TicketType.setAdapter(adapter);
                    ServiceType.setOnItemSelectedListener(this);
                    break;
                case 3:
                    // Whatever you want to happen when the thrid item gets selected
                    ServiceType.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MainActivity1.this,
                            android.R.layout.simple_spinner_item, ServiceFlight);

                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    ServiceType.setAdapter(
                            new NothingSelectedSpinnerAdapter(
                                    adapter3,
                                    R.layout.contact_spinner_row_nothing_selected3,
                                    // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                                    this));
                    //TicketType.setAdapter(adapter);
                    ServiceType.setOnItemSelectedListener(this);
                    break;
            }
            break;
            case R.id.spinner2:
                    City1.setVisibility(View.VISIBLE);
                    City2.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity1.this,
                            android.R.layout.simple_spinner_item, ListOfCities);

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainActivity1.this,
                            android.R.layout.simple_spinner_item, Cities);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    City1.setAdapter(
                            new NothingSelectedSpinnerAdapter(
                                    adapter1,
                                    R.layout.contact_spinner_row_nothing_selected_arr,
                                    // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                                    this));
                    City1.setOnItemSelectedListener(this);
                    City2.setAdapter(
                            new NothingSelectedSpinnerAdapter(
                                    adapter2,
                                    R.layout.contact_spinner_row_nothing_selected_dept,
                                    // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                                    this));
                    City2.setOnItemSelectedListener(this);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bookticket2:
                String temp = UserName.getText().toString();
                String temp1 = UserAge.getText().toString();
                String temp2 = UserMobileNo.getText().toString();
                String temp3 = TicketType.getSelectedItem().toString();
                String temp4 = ServiceType.getSelectedItem().toString();
                String temp5 = City1.getSelectedItem().toString();
                String temp6 = City2.getSelectedItem().toString();
                String temp7 = mDateText.getText().toString();
                String temp8 = mTimeText.getText().toString();
                if (temp.isEmpty() || temp1.isEmpty() || temp2.isEmpty() || temp3.isEmpty()
                        || temp4.isEmpty() || temp5.isEmpty() || temp6.isEmpty() || temp7.isEmpty()
                        || temp8.isEmpty()) {
                    Toast.makeText(this, "Some Field is Empty", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        char age = (char) Integer.parseInt(temp1);
                        long mobileNo = Long.parseLong(temp2);

                        DBHelper addb1 = new DBHelper(this);
                        addb1.open();
                        addb1.insertEntry(temp, age, mobileNo, temp3, temp4, temp5, temp6, temp7, temp8);
                        addb1.close();

                        Intent i1 = new Intent(MainActivity1.this, Receiver.class);
                        Bundle b = new Bundle();
                        b.putString("name", temp);
                        b.putInt("age", age);
                        b.putLong("mobileno", mobileNo);
                        b.putString("tickettype", temp3);
                        b.putString("servicetype", temp4);
                        b.putString("departure", temp5);
                        b.putString("arrival", temp6);
                        b.putString("dates", temp7);
                        b.putString("times", temp8);
                        i1.putExtras(b);
                        sendBroadcast(i1);

                        UserName.setText("");
                        UserAge.setText("");
                        UserMobileNo.setText("");
                        mDateText.setText("");
                        mTimeText.setText("");

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity1.this,
                                android.R.layout.simple_spinner_item, Ticket);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        TicketType.setPrompt("Select a Service for Ticket");
                        TicketType.setAdapter(new NothingSelectedSpinnerAdapter(
                                adapter,
                                R.layout.contact_spinner_row_nothing_selected,
                                this));
                        TicketType.setOnItemSelectedListener(this);
                        ServiceType.setVisibility(View.GONE);
                        City1.setVisibility(View.GONE);
                        City2.setVisibility(View.GONE);

                        Toast.makeText(this, "Fertilizer Booked Successfully.", Toast.LENGTH_LONG).show();
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.bookedticket:
                Intent int1 = new Intent(MainActivity1.this, Listofcandidates.class);
                startActivity(int1);
                break;
        }
    }

}
