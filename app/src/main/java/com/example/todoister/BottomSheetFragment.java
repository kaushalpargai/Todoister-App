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
import androidx.lifecycle.ViewModelProvider;

import com.example.todoister.Model.Priority;
import com.example.todoister.Model.SharedViewModel;
import com.example.todoister.Model.Task;
import com.example.todoister.Model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener{
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
    private SharedViewModel sharedViewModel;
    private boolean isEdit;



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
        todayChip.setOnClickListener(this);
        Chip tomorrowChip=view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextweekChip=view.findViewById(R.id.next_week_chip);
        nextweekChip.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();



        if(sharedViewModel.getSelectedItem().getValue() != null){
            isEdit= sharedViewModel.getIsEdit();
            Task task = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(task.getTask());
            Log.d("My", "onViewCreated: " + task.getTask());

        }


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel=new ViewModelProvider(requireActivity())
                .get(SharedViewModel.class);



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
                        if(!TextUtils.isEmpty(task) && dueDate!=null) {
                            Task myTask = new Task(task, Priority.HIGH, dueDate, Calendar.getInstance().getTime(), false);


                            if (isEdit) {
                                Task updateTask = sharedViewModel.getSelectedItem().getValue();
                                updateTask.setTask(task);
                                updateTask.setIsCreated(Calendar.getInstance().getTime());
                                updateTask.setPriorirty(Priority.HIGH);
                                updateTask.setDueDate(dueDate);
                                TaskViewModel.update(updateTask);
                                sharedViewModel.setIsEdit(false);
                            } else {
                                TaskViewModel.insert(myTask);
                            }
                        }

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==R.id.today_chip){
            //set data for today
            calendar.add(Calendar.DAY_OF_YEAR,0);
            dueDate=calendar.getTime();
            Log.d("Time", "onClick: "+dueDate.toString());
        }
        else if (id==R.id.tomorrow_chip) {
            calendar.add(Calendar.DAY_OF_YEAR,1);
            dueDate=calendar.getTime();
            Log.d("Time", "onClick: "+dueDate.toString());
        } else if (id == R.id.next_week_chip) {
            calendar.add(Calendar.DAY_OF_YEAR,7);
            dueDate=calendar.getTime();
            Log.d("Time", "onClick: "+dueDate.toString());
        }

    }
}
