package com.mathutility;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch usesCubicRootSwitch = findViewById(R.id.useCubicRoot);
        EditText inputNumberText = findViewById(R.id.inputNumber);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView outputTextView = findViewById(R.id.outputNumber);

        calculateButton.setOnClickListener(v -> {
            if (inputNumberText.getEditableText().toString().isEmpty()) {
                Snackbar.make(v, "Please enter a number!", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getColor(R.color.background))
                        .setTextColor(getColor(R.color.text)).show();
                return;
            }

            boolean usesCubic = usesCubicRootSwitch.isChecked();
            double input = Double.parseDouble(inputNumberText.getEditableText().toString());
            double result;
            String format = format(usesCubic, input);

            if (input == 0) {
                outputTextView.setText(format + " = 0");
                return;
            }

            outputTextView.setText("");
            if (usesCubic) {
                double t;
                double sqrtroot = input / 3;
                do {
                    t = sqrtroot;
                    sqrtroot = (t + (input / t)) / 3;
                    outputTextView.setText(outputTextView.getText().toString() + sqrtroot + " < " + format + " < " + t + "\r\n");
                }
                while ((t - sqrtroot) != 0);
                result = sqrtroot;
                result = Math.cbrt(input);
            } else {
                double t;
                double sqrtroot = input / 2;
                do {
                    t = sqrtroot;
                    sqrtroot = (t + (input / t)) / 2;
                    outputTextView.setText(outputTextView.getText().toString() + sqrtroot + " < " + format + " < " + t + "\r\n");
                }
                while ((t - sqrtroot) != 0);
                result = sqrtroot;
            }

            log(format + "= " + result);
            outputTextView.setText("\r\n" + outputTextView.getText().toString() + format + " = " + result);
        });
    }

    private String format(boolean cubic, double input) {
        if (cubic) {
            return "cbrt(" + input + ")";
        } else {
            return "sqrt(" + input + ")";
        }
    }

    private void log(String msg) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        TextView logTextView = findViewById(R.id.outputlog);

        logTextView.setText(logTextView.getText().toString() + "[" + dtf.format(now) + "]: " + msg + "\r\n");
    }
}