package com.example.todoister;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;

import com.example.todoister.Model.Priority;
import com.example.todoister.Model.Task;
import com.example.todoister.Model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private ImageButton saveButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date dueDate;
    Calendar calendar=Calendar.getInstance();



    public BottomSheetFragment() {

    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.bottom_sheet, container, false);

        calendarGroup=view.findViewById(R.id.calendar_group);
        calendarView=view.findViewById(R.id.calendar_view);
        calendarButton=view.findViewById(R.id.today_calendar_button);
        enterTodo=view.findViewById(R.id.enter_todo_et);
        saveButton=view.findViewById(R.id.save_todo_button);
        priorityButton=view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup=view.findViewById(R.id.radioGroup_priority);

        Chip todayChip=view.findViewById(R.id.today_chip);
        Chip tomorrowChip=view.findViewById(R.id.tomorrow_chip);
        Chip nextweekChip=view.findViewById(R.id.next_week_chip);


        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarGroup.setVisibility(calendarGroup.getVisibility() == View.GONE ? View.VISIBLE :View.GONE  );
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.clear();
                calendar.set(year,month,dayOfMonth);
                dueDate=calendar.getTime();

                Log.d("Cal", "onSelectedDayChange:  month--->> "+(month+1)+" dayofmonth-->> " +dayOfMonth );

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        String task=enterTodo.getText().toString().trim();
                        if(!TextUtils.isEmpty(task) && dueDate!=null){
                            Task myTask=new Task(task, Priority.HIGH, dueDate, Calendar.getInstance().getTime(), false);
                            TaskViewModel.insert(myTask);

                        }
            }
        });
    }
}
