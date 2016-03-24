package com.example.horiprogressbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btnStart;
	private HoriProgress progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progress = (HoriProgress) findViewById(R.id.hprogress);
		
		findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				new Thread(progress).start();
			}
		});
	}
}
