package com.aaron.herri.mytodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by herri on 10/21/2015.
 */
public class StartActivity extends Activity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.start_activity_menu, menu);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        Button btnTaskList = (Button)findViewById(R.id.button);
        Button btnBillList = (Button)findViewById(R.id.button2);

        btnTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), TaskListActivity.class);
                startActivity(nextScreen);
            }
        });

        btnBillList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), BillListActivity.class);
                startActivity(nextScreen);
            }
        });

    }
}
