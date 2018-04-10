package com.stringcalculator.my.stringcalculator;
// made by Roman Kryvolapov
// Новая версия калькулятора, с двумя дисплеями- для ввода и результата
// Умеет считать со скобками, скобок можно использовать много уровней
// Свайп по дисплеям стирает последнюю цифру
// Есть история
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<BigDecimal> digits = new ArrayList<BigDecimal>();
    // числа
    static ArrayList<Character> operators = new ArrayList<Character>();
    // операторы
    static ArrayList<Integer> proirity = new ArrayList<Integer>();
    // приоритет - скобки, можно использовать много уровней

    static String display = "";
    static String displayConvert = "";
    final static int textSize = 80;
    final static int maxDigitSize = 25;
    static float xTouch;

    static StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        onTouchListenerTr();
        textSizeChange("");
        textSizeChangeResult("");
        displayDisplay("");
        displayDisplayResult("0");

    }

    static String stringBufferAdd(String string1, String string2){
        MainActivity.stringBuffer.setLength(0);
        MainActivity.stringBuffer.append(string1);
        MainActivity.stringBuffer.append(string2);
        string1 = MainActivity.stringBuffer.toString();
        MainActivity.stringBuffer.setLength(0);
        return string1;
    }

    static String stringBufferAdd(String string1, char string2){
        MainActivity.stringBuffer.setLength(0);
        MainActivity.stringBuffer.append(string1);
        MainActivity.stringBuffer.append(string2);
        string1 = MainActivity.stringBuffer.toString();
        MainActivity.stringBuffer.setLength(0);
        return string1;
    }

    static String stringBufferEreseLast(String string1){
        MainActivity.stringBuffer.setLength(0);
        MainActivity.stringBuffer.append(string1);
        MainActivity.stringBuffer.setLength(MainActivity.stringBuffer.length() - 1);
        string1 = MainActivity.stringBuffer.toString();
        MainActivity.stringBuffer.setLength(0);
        return string1;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void onTouchListenerTr(){
        Button button = (Button) findViewById(R.id.buttonTr);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        MainActivity.xTouch = event.getX();
                        // получаем координатиы при касании
                        break;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:

                        if(event.getX()>MainActivity.xTouch+100) {

                            MainActivity.xTouch = event.getX();

                            if (display.length() > 0) {
                                display = MainActivity.stringBufferEreseLast(display);
                                displayDisplay("");
                            }
                        }

                        if(event.getX()<MainActivity.xTouch-100) {
                            MainActivity.xTouch = event.getX();
                        }
                        // при свайпе вправо стрирает по одному символу каждые 100 единиц
                        // можно вернуть палец влево и снова провести вправо, не отрывая от экрана
                        // от слушателей остальных кнопок пришлось отказаться в пользу onClick в XML
                        // так как они блокировали события XML
                        break;
                }
                return true;
            }
        });
    }

    public void onClickHistory(View view) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    public void onClickRavno(View view)
    {
            digits.clear();
            operators.clear();
            proirity.clear();

            DigitsReader.digitsReader(display);
            if(MainActivity.digits.size() > 0) {
                ConvertTo.convertTo();
                // Конвертирует строку в выражение, добавляет скобки, если нужно
                displayDisplay("");
                Calculator.calculator();
                // Делает рассчет
                displayDisplayResult(digits.get(0).toPlainString());

                if(HistoryActivity.history.size()>0) {
                    if (!HistoryActivity.history.get(HistoryActivity.history.size() - 1).equals(display + "\n= " + EraseLastZero.eraseLastZero(digits.get(0).toPlainString())))
                        HistoryActivity.history.add(display + "\n= " + EraseLastZero.eraseLastZero(digits.get(0).toPlainString()));

                }else {
                    HistoryActivity.history.add(display + "\n= " + EraseLastZero.eraseLastZero(digits.get(0).toPlainString()));
                }
                digits.clear();
                operators.clear();
                proirity.clear();
            }

    }

    public void onClickC(View view)
    {
        digits.clear();
        operators.clear();
        proirity.clear();
        display="";
        displayConvert="";
        displayDisplayResult("0");
        displayDisplay("");
        digits.clear();
        operators.clear();
        proirity.clear();
    }

    public void onClick0(View view)
    {
        displayDisplay("0");
    }
    public void onClick1(View view)
    {
        displayDisplay("1");
    }
    public void onClic2(View view)
    {
        displayDisplay("2");
    }
    public void onClic3(View view)
    {
        displayDisplay("3");
    }
    public void onClic4(View view)
    {
        displayDisplay("4");
    }
    public void onClic5(View view)
    {
        displayDisplay("5");
    }
    public void onClic6(View view)
    {
        displayDisplay("6");
    }
    public void onClic7(View view)
    {
        displayDisplay("7");
    }
    public void onClic8(View view)
    {
        displayDisplay("8");
    }
    public void onClic9(View view)
    {
        displayDisplay("9");
    }
    public void onClicTochka(View view)
    {
        displayDisplay(".");
    }
    public void onClicPlus(View view)
    {
        displayDisplay("+");
    }
    public void onClicMinus(View view)
    {
        displayDisplay("-");
    }
    public void onClicUmnojenie(View view)
    {
        displayDisplay("*");
    }
    public void onClicDelenie(View view)
    {
        displayDisplay("/");
    }
    public void onClicOtkritjSkobku(View view)
    {
        displayDisplay("(");
    }
    public void onClicZakritjSkobku(View view)
    {
        displayDisplay(")");
    }

    public final void displayDisplay (String in) {
        // Выводит на верхний экран
        boolean add = true;

        if(display.length()>0&&in.equals("(")) {
            switch (display.charAt(display.length()-1)) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '(':
                    add = true;
                    break;
                default:
                    add = false;
                    break;
            }
        }
            if(display.length()==0&&(in.equals(")")||in.equals("+")||in.equals("-")||in.equals("*")||in.equals("/")))
                add=false;

        if(display.length()>0&&(in.equals(")")||in.equals("+")||in.equals("-")||in.equals("*")||in.equals("/"))) {
            switch (display.charAt(display.length()-1)) {
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                case ')':
                    add = true;
                    break;
                default:
                    add = false;
                    break;
            }
        }

        if(add) {
            display = MainActivity.stringBufferAdd(display, in);
        }
            TextView vivod = (TextView) findViewById(R.id.textOut);
            vivod.setText(display);
        textSizeChange (display);
    }

    public final void displayDisplayResult (String result) {
// Выводит на нижник экран
        result = EraseLastZero.eraseLastZero(result);
        textSizeChangeResult (result);
        if (result.length()==0) {
            TextView vivod = (TextView) findViewById(R.id.textResult);
            vivod.setText("0");
        }else {
            TextView vivod = (TextView) findViewById(R.id.textResult);
            vivod.setText(result);
        }
    }

    public void textSizeChange(String result) {
        // Меняет размер щрифта верхнего экрана
        TextView vivod = (TextView) findViewById(R.id.textOut);
        if (result.length() <= 30) {
            switch (result.length()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    vivod.setTextSize(textSize);
                    break;
                case 12:
                    vivod.setTextSize(textSize - 5);
                    break;
                case 13:
                    vivod.setTextSize(textSize - 10);
                    break;
                case 14:
                    vivod.setTextSize(textSize - 15);
                    break;
                case 15:
                    vivod.setTextSize(textSize - 20);
                    break;
                case 16:
                    vivod.setTextSize(textSize - 23);
                    break;
                case 17:
                    vivod.setTextSize(textSize - 26);
                    break;
                case 18:
                    vivod.setTextSize(textSize - 29);
                    break;
                case 19:
                    vivod.setTextSize(textSize - 32);
                    break;
                case 20:
                    vivod.setTextSize(textSize - 34);
                    break;
                case 21:
                    vivod.setTextSize(textSize - 36);
                    break;
                case 22:
                    vivod.setTextSize(textSize - 38);
                    break;
                case 23:
                    vivod.setTextSize(textSize - 40);
                    break;
                case 24:
                    vivod.setTextSize(textSize - 42);
                    break;
                case 25:
                    vivod.setTextSize(textSize - 44);
                    break;
                case 26:
                    vivod.setTextSize(textSize - 45);
                    break;
                case 27:
                    vivod.setTextSize(textSize - 46);
                    break;
                case 28:
                    vivod.setTextSize(textSize - 47);
                    break;
                case 29:
                    vivod.setTextSize(textSize - 48);
                    break;
                case 30:
                    vivod.setTextSize(textSize - 49);
                    break;
            }
        } else {
            vivod.setTextSize(textSize - 49);
        }
    }

    public void textSizeChangeResult(String result) {
// Меняет размер шрифта нижнего экрана
        TextView vivod = (TextView) findViewById(R.id.textResult);
        if (result.length() <= 30) {
            switch (result.length()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    vivod.setTextSize(textSize);
                    break;
                case 12:
                    vivod.setTextSize(textSize - 5);
                    break;
                case 13:
                    vivod.setTextSize(textSize - 10);
                    break;
                case 14:
                    vivod.setTextSize(textSize - 15);
                    break;
                case 15:
                    vivod.setTextSize(textSize - 20);
                    break;
                case 16:
                    vivod.setTextSize(textSize - 23);
                    break;
                case 17:
                    vivod.setTextSize(textSize - 26);
                    break;
                case 18:
                    vivod.setTextSize(textSize - 29);
                    break;
                case 19:
                    vivod.setTextSize(textSize - 32);
                    break;
                case 20:
                    vivod.setTextSize(textSize - 34);
                    break;
                case 21:
                    vivod.setTextSize(textSize - 36);
                    break;
                case 22:
                    vivod.setTextSize(textSize - 38);
                    break;
                case 23:
                    vivod.setTextSize(textSize - 40);
                    break;
                case 24:
                    vivod.setTextSize(textSize - 42);
                    break;
                case 25:
                    vivod.setTextSize(textSize - 44);
                    break;
                case 26:
                    vivod.setTextSize(textSize - 45);
                    break;
                case 27:
                    vivod.setTextSize(textSize - 46);
                    break;
                case 28:
                    vivod.setTextSize(textSize - 47);
                    break;
                case 29:
                    vivod.setTextSize(textSize - 48);
                    break;
                case 30:
                    vivod.setTextSize(textSize - 49);
                    break;
            }
        } else {
            vivod.setTextSize(textSize - 49);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class Calculator {
// Делает рассчеты
// Использует приоритет для скобок
    static void calculator() {

        if (MainActivity.digits.size() > 0) {

            int maxPriority = 0;
            for (int i : MainActivity.proirity) {
                if (maxPriority < i)
                    maxPriority = i;
            }

            for (int currentPriority = maxPriority; currentPriority >= 0; currentPriority--) {
                for (int i = 0; i < MainActivity.operators.size(); i++) {
                    if (MainActivity.operators.get(i) == '*' && MainActivity.proirity.get(i) == currentPriority) {
                        MainActivity.digits.set(i + 1, MainActivity.digits.get(i).multiply(MainActivity.digits.get(i + 1)));
                        removeLast(i);
                        i--;
                    } else if (MainActivity.operators.get(i) == '/' && MainActivity.proirity.get(i) == currentPriority) {
                        if (!MainActivity.digits.get(i + 1).equals(BigDecimal.valueOf(0)))
                            MainActivity.digits.set(i + 1, MainActivity.digits.get(i).divide((MainActivity.digits.get(i + 1)), MainActivity.maxDigitSize, BigDecimal.ROUND_CEILING));
                        removeLast(i);
                        i--;
                    }
                }
                for (int i = 0; i < MainActivity.operators.size(); i++) {
                    if (MainActivity.operators.get(i) == '+' && MainActivity.proirity.get(i) == currentPriority) {
                        MainActivity.digits.set(i + 1, MainActivity.digits.get(i).add(MainActivity.digits.get(i + 1)));
                        removeLast(i);
                        i--;
                    } else if (MainActivity.operators.get(i) == '-' && MainActivity.proirity.get(i) == currentPriority) {
                        MainActivity.digits.set(i + 1, MainActivity.digits.get(i).subtract(MainActivity.digits.get(i + 1)));
                        removeLast(i);
                        i--;
                    }
                }
            }
        }
    }

    private static void removeLast(int i){
        MainActivity.digits.remove(i);
        MainActivity.operators.remove(i);
        MainActivity.proirity.remove(i);
    }
}

class DigitsReader {
    // Записывает строки в массивы
    static void digitsReader(String digits) {

        boolean someDigit = false;
        int priority = 0;
        String tempDigits = "";
        char content;
        for (int i = 0; i < MainActivity.display.length() && MainActivity.display.length() > 0; i++) {
            content = MainActivity.display.charAt(i);
            switch (content) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '.':
                    if (tempDigits.length() < MainActivity.maxDigitSize) {
                        tempDigits = MainActivity.stringBufferAdd(tempDigits, content);
                        someDigit = true;
                    }
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    if (tempDigits != "" && MainActivity.operators.size() <= MainActivity.digits.size()&&someDigit) {
                        MainActivity.digits.add(new BigDecimal(tempDigits, new MathContext(MainActivity.maxDigitSize, RoundingMode.HALF_UP)));
                        tempDigits = "";
                        MainActivity.operators.add(content);
                        MainActivity.proirity.add(priority);
                    }
                    break;
                case '(':
                    if (MainActivity.operators.size() == MainActivity.digits.size() && tempDigits == "") {
                        priority++;
                    }
                    break;
                case ')':
                    if (priority > 0 && tempDigits != "") {
                        priority--;
                    }
                    break;
            }

        }
        if (tempDigits != ""&&someDigit)
            MainActivity.digits.add(new BigDecimal(tempDigits, new MathContext(MainActivity.maxDigitSize, RoundingMode.HALF_UP)));

        else if (MainActivity.operators.size() > 1 && MainActivity.operators.size() >= MainActivity.digits.size()&&someDigit) {
            MainActivity.operators.remove(MainActivity.operators.size() - 1);
            MainActivity.proirity.remove(MainActivity.proirity.size() - 1);
        }
        if (MainActivity.digits.size() == MainActivity.operators.size()&&someDigit)
            MainActivity.operators.remove(MainActivity.operators.size() - 1);
        MainActivity.proirity.add(0);
    }
}

class ConvertTo {
// Переводит массивы в строку- выражение
    static void convertTo() {
        if (MainActivity.digits.size() > 0) {
            int temp1 = 0;
            for (int i = 0; i < MainActivity.digits.size(); i++) {
                if (MainActivity.proirity.size() > i) {
                    while (MainActivity.proirity.get(i) > temp1) {
                        MainActivity.displayConvert = MainActivity.stringBufferAdd(MainActivity.displayConvert, '(');
                        temp1++;
                    }
                }

                MainActivity.displayConvert = MainActivity.stringBufferAdd(MainActivity.displayConvert, EraseLastZero.eraseLastZero(MainActivity.digits.get(i).toPlainString()));

                if (MainActivity.proirity.size() > i) {
                    while (MainActivity.proirity.get(i) < temp1) {
                        MainActivity.displayConvert = MainActivity.stringBufferAdd(MainActivity.displayConvert, ')');
                        temp1--;
                    }
                }
                if (MainActivity.operators.size() > i) {
                    MainActivity.displayConvert = MainActivity.stringBufferAdd(MainActivity.displayConvert, MainActivity.operators.get(i));
                }
            }
            MainActivity.display = MainActivity.displayConvert;
            MainActivity.displayConvert = "";
        }
    }
}

class EraseLastZero{
// Удаляет нули и точку в конце числа
    static String eraseLastZero (String lastZero){
        if(lastZero.length()>1)
            while ((lastZero.length()>1&&lastZero.charAt(lastZero.length()-1)=='0'&&lastZero.contains("."))||lastZero.charAt(lastZero.length()-1)=='.')
            {
                lastZero = MainActivity.stringBufferEreseLast(lastZero);
            }
            return lastZero;
    }
}

// нужно прикрутить повторитель

//                            final Timer myTimer = new Timer();
//                            final Handler myHandler = new Handler();
//
//                        if(MainActivity.toched) {
//                            myTimer.schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    myHandler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if(!MainActivity.toched)
//                                                myTimer.cancel();
//                                            if (display.length() > 0 && MainActivity.toched) {
//                                                display = MainActivity.stringBufferEreseLast(display);
//                                                displayDisplay("");
//                                            }
//
//                                        }
//                                    });
//                                }
//                            }, 800, 200);
//                        }