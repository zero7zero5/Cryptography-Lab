package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.*;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "my_channel";
    private static final int NOTIFICATION_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        TextView text = findViewById(R.id.txtSolution);
        Button clear = findViewById(R.id.btnClear);
        Button back = findViewById(R.id.btnBack);
        Button zero = findViewById(R.id.btnZero);
        Button one = findViewById(R.id.btnOne);
        Button two = findViewById(R.id.btnTwo);
        Button three = findViewById(R.id.btnThree);
        Button four = findViewById(R.id.btnFour);
        Button five = findViewById(R.id.btnFive);
        Button six = findViewById(R.id.btnSix);
        Button seven = findViewById(R.id.btnSeven);
        Button eight = findViewById(R.id.btnEight);
        Button nine = findViewById(R.id.btnNine);
        Button equals = findViewById(R.id.btnEquals);
        Button add = findViewById(R.id.btnAdd);
        Button subtract = findViewById(R.id.btnSubtract);
        Button multiply = findViewById(R.id.btnMultiply);
        Button divide = findViewById(R.id.btnDivide);
        clear.setOnClickListener(v -> text.setText(""));

        one.setOnClickListener(v -> {
            String t = text.getText()+"1";
            text.setText(t);
        });
        two.setOnClickListener(v -> {
            String t = text.getText()+"2";
            text.setText(t);
        });
        three.setOnClickListener(v -> {
            String t = text.getText()+"3";
            text.setText(t);
        });
        four.setOnClickListener(v -> {
            String t = text.getText()+"4";
            text.setText(t);
        });
        five.setOnClickListener(v -> {
            String t = text.getText()+"5";
            text.setText(t);
        });
        six.setOnClickListener(v -> {
            String t = text.getText()+"6";
            text.setText(t);
        });
        seven.setOnClickListener(v -> {
            String t = text.getText()+"7";
            text.setText(t);
        });
        eight.setOnClickListener(v -> {
            String t = text.getText()+"8";
            text.setText(t);
        });
        nine.setOnClickListener(v -> {
            String t = text.getText()+"9";
            text.setText(t);
        });
        zero.setOnClickListener(v -> {
            String t = text.getText()+"0";
            text.setText(t);
        });
        back.setOnClickListener(v -> {
            String t = text.getText().toString();
            text.setText(t.substring(0,t.length()-1));
        });
        add.setOnClickListener(v->{
            String t = text.getText().toString();
            if(t.equals("")) return;

            if(checkLast(t)) {
                t=t.substring(0,t.length()-1)+"+";
                text.setText(t);
            }
            else{
                t=t+"+";
                text.setText(t);
            }

        });
        subtract.setOnClickListener(v->{
            String t = text.getText().toString();
            if(t.equals("")) return;

            if(checkLast(t)) {
                t=t.substring(0,t.length()-1)+"-";
                text.setText(t);
            }
            else{
                t=t+"-";
                text.setText(t);
            }

        });
        multiply.setOnClickListener(v->{
            String t = text.getText().toString();
            if(t.equals("")) return;

            if(checkLast(t)) {
                t=t.substring(0,t.length()-1)+"x";
                text.setText(t);
            }
            else{
                t=t+"*";
                text.setText(t);
            }

        });
        divide.setOnClickListener(v->{
            String t = text.getText().toString();
            if(t.equals("")) return;

            if(checkLast(t)) {
                t=t.substring(0,t.length()-1)+"/";
                text.setText(t);
            }
            else{
                t=t+"/";
                text.setText(t);
            }

        });
        equals.setOnClickListener(v->{
            int res = evaluateInfixExpression(text.getText().toString());
            text.setText(String.valueOf(res));
        });
        equals.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showNotification(((String) text.getText()));
                return true;
            }
        });
    }
    public boolean checkLast(String a){
        if(a.charAt(a.length()-1) == '+' ||a.charAt(a.length()-1) == '-'|| a.charAt(a.length()-1) == '*'|| a.charAt(a.length()-1) == '/' ) return true;
        return false;
    }
    public  int evaluateInfixExpression(String expression) {
        expression = expression.replaceAll("\\s", "");
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)))) {
                    operand.append(expression.charAt(i));
                    i++;
                }
                i--; // Adjust index after parsing the number
                values.push(Integer.parseInt(operand.toString()));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    applyOperator(values, operators);
                }
                operators.pop(); // Pop the open parenthesis
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    applyOperator(values, operators);
                }
                operators.push(c);
            }
        }
        while (!operators.isEmpty()) {
            applyOperator(values, operators);
        }

        if (values.size() == 1) {
            return values.pop();
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
    }
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", importance);
            NotificationManager notificationManager = getSystemService( NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
    private void showNotification(String ans) {
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID).setContentTitle("Sample").setContentText(ans).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,notification);
    }
    private static void applyOperator(Stack<Integer> values, Stack<Character> operators) {
        char operator = operators.pop();
        int operand2 = values.pop();
        int operand1 = values.pop();
        int result;
        if (operator == '+') {
            result = operand1 + operand2;
        } else if (operator == '-') {
            result = operand1 - operand2;
        } else if (operator == '*') {
            result = operand1 * operand2;
        } else if (operator == '/') {
            if (operand2 == 0) {
                throw new ArithmeticException("Division by zero");
            }
            result = operand1 / operand2;
        } else {
            throw new IllegalArgumentException("Invalid operator");
        }
        values.push(result);
    }
    private static int precedence(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
            return 2;
        }
        return 0;
    }


}