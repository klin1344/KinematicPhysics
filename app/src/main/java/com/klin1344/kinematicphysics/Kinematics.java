package com.klin1344.kinematicphysics;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by kevin on 5/17/15.  This class is the main class of the app.
 * It provides places to receive input and then calculates everything.
 */
public class Kinematics extends AppCompatActivity {
    private double a;
    private double deltaXI;
    //private double deltaYI;
    private double deltaXF;
    //private double deltaYF;
    private double time;
    private double vI;
    private double vF;
    private double theta;
    private double range;
    private double height;
    private EditText aEditText;
    private EditText deltaXEditTextI;
    //private EditText deltaYEditTextI;
    private EditText deltaXEditTextF;
    //private EditText deltaYEditTextF;
    private EditText timeEditText;
    private EditText vIEditText;
    private EditText vFEditText;
    private EditText angleEditText;
    private boolean checked;
    private boolean success = true;
    public final double GRAVITY = 9.8;

    /* Inflate the view, create the UI, and set the calculate button to
       calculate everything then pass it to the next activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinematics);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0277BD")));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        aEditText = (EditText) (findViewById(R.id.editText));
        deltaXEditTextI = (EditText) (findViewById(R.id.editText2));
        deltaXEditTextF = (EditText) (findViewById(R.id.editText8));
        timeEditText = (EditText) (findViewById(R.id.editText3));
        vIEditText = (EditText) (findViewById(R.id.editText4));
        vFEditText = (EditText) (findViewById(R.id.editText5));
        angleEditText = (EditText) (findViewById(R.id.editText6));
        //Button calculateButton = (Button) (findViewById(R.id.button));

        angleEditText.setEnabled(false);

    }

    /*  handle the code for the Projectile Motion checkbox.  Disable and enable appropriate
        fields.
     */
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkBox:
                if (checked) {
                    aEditText.setText("9.8");
                    aEditText.setEnabled(false);
                    angleEditText.setEnabled(true);
                    timeEditText.setText("");
                    vFEditText.setText("");
                    timeEditText.setEnabled(false);
                    vFEditText.setEnabled(false);

                } else {
                    aEditText.setEnabled(true);
                    aEditText.setText("");
                    angleEditText.setText("");
                    angleEditText.setEnabled(false);
                    timeEditText.setEnabled(true);
                    vFEditText.setEnabled(true);

                }
                break;

        }
    }

    /*  inflate the menu resource */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kinematics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(Kinematics.this, InfoScreen.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_calculate) {
            calculate();
            if (success) {
                theta = Math.toDegrees(theta);
                Intent intent = new Intent(Kinematics.this, Results.class);
                intent.putExtra("vF", vF);
                intent.putExtra("vI", vI);
                intent.putExtra("time", Math.abs(time));
                intent.putExtra("deltaXI", deltaXI);
                intent.putExtra("deltaXF", deltaXF);
                intent.putExtra("checked", checked);

                if (checked) {
                    intent.putExtra("acceleration", GRAVITY);
                    intent.putExtra("height", height);
                    intent.putExtra("range", range);
                    intent.putExtra("theta", theta);
                } else {
                    intent.putExtra("acceleration", a);
                }
                startActivity(intent);
                finish();
            }
            return true;
        }
        else if (id == R.id.action_help) {
            Intent intent = new Intent(Kinematics.this, Help.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*  code to calculate all values using the four kineamtic equations of motion */
    private void calculate() {
        success = true;
        // equation one - missing deltaX
        if (!checked) {
            if (deltaXEditTextF.getText().length() == 0 && deltaXEditTextI.getText().length() == 0) {
                if (aEditText.getText().length() > 0 && vFEditText.getText().length() > 0 && vIEditText.getText().length() > 0
                        && timeEditText.getText().length() == 0) {
                    // time unknown
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    a = Double.parseDouble(aEditText.getText().toString());
                    time = (vF - vI) / a;
                    deltaXF = vI * time + 0.5 * a * Math.pow(time, 2);
                    deltaXI = 0;
                } else if (timeEditText.getText().length() > 0 && vFEditText.getText().length() > 0 && vIEditText.getText().length() > 0
                        && aEditText.getText().length() == 0) {
                    // a unknown
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    time = Double.parseDouble(timeEditText.getText().toString());
                    a = (vF - vI) / time;
                    deltaXF = vI * time + 0.5 * a * Math.pow(time, 2);
                    deltaXI = 0;
                } else if (timeEditText.getText().length() > 0 && vIEditText.getText().length() > 0 && aEditText.getText().length() > 0
                        && vFEditText.getText().length() == 0) {
                    // vF unknown
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    a = Double.parseDouble(aEditText.getText().toString());
                    time = Double.parseDouble(timeEditText.getText().toString());
                    vF = vI + a * time;
                    deltaXF = vI * time + 0.5 * a * Math.pow(time, 2);
                    deltaXI = 0;
                } else if (timeEditText.getText().length() > 0 && vFEditText.getText().length() > 0 && aEditText.getText().length() > 0
                        && vIEditText.getText().length() == 0) {
                    // vI unknown
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    a = Double.parseDouble(aEditText.getText().toString());
                    time = Double.parseDouble(timeEditText.getText().toString());
                    vI = vF - a * time;
                    deltaXF = vI * time + 0.5 * a * Math.pow(time, 2);
                    deltaXI = 0;
                } else {
                    showImpossibleSituation();
                    success = false;
                }
            }

            // equation 2 - missing vF
            else if (vFEditText.getText().length() == 0) {

                if (deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() > 0 && aEditText.getText().length() > 0
                        && timeEditText.getText().length() > 0 && vIEditText.getText().length() == 0) {
                    // vI unknown
                    a = Double.parseDouble(aEditText.getText().toString());
                    time = Double.parseDouble(timeEditText.getText().toString());
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    vI = ((deltaXF - deltaXI) - 0.5 * a * Math.pow(time, 2)) / time;
                    vF = vI + a * time;
                } else if (deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() > 0 && aEditText.getText().length() > 0
                        && vIEditText.getText().length() > 0 && timeEditText.getText().length() == 0) {
                    // time unknown
                    a = Double.parseDouble(aEditText.getText().toString());
                    //time = Double.parseDouble(timeEditText.getText().toString());
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    if (Math.pow(vI, 2) - 2 * a * -(deltaXF - deltaXI) < 0) {
                        showImpossibleSituation();
                        success = false;
                    } else {
                        time = (-vI + Math.sqrt(Math.pow(vI, 2) - 2 * a * -(deltaXF - deltaXI))) / a;
                        vF = vI + a * time;
                    }

                } else if (deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() > 0 && timeEditText.getText().length() > 0
                        && vIEditText.getText().length() > 0 && aEditText.getText().length() == 0) {
                    // a unknown
                    time = Double.parseDouble(timeEditText.getText().toString());
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    a = 2 * (deltaXF - deltaXI - vI * time) / Math.pow(time, 2);
                    vF = vI + a * time;
                } else if (timeEditText.getText().length() > 0 && vIEditText.getText().length() > 0 && aEditText.getText().length() > 0
                        && deltaXEditTextI.getText().length() == 0 && deltaXEditTextF.getText().length() == 0) {
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    // deltaX unknown
                    a = Double.parseDouble(aEditText.getText().toString());
                    time = Double.parseDouble(timeEditText.getText().toString());
                    deltaXF = vI * time + 0.5 * a * Math.pow(time, 2);
                    deltaXI = 0;
                    vF = vI + a * time;
                } else if (deltaXEditTextI.getText().length() > 0 && vIEditText.getText().length() > 0 && aEditText.getText().length() > 0
                        && timeEditText.getText().length() > 0 && deltaXEditTextF.getText().length() == 0) {
                    // deltaXF unknown
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    a = Double.parseDouble(aEditText.getText().toString());
                    time = Double.parseDouble(timeEditText.getText().toString());
                    deltaXF = vI * time + 0.5 * a * Math.pow(time, 2) + deltaXI;
                    vF = vI + a * time;
                } else if (deltaXEditTextF.getText().length() > 0 && vIEditText.getText().length() > 0 && aEditText.getText().length() > 0
                        && timeEditText.getText().length() > 0 && deltaXEditTextI.getText().length() == 0) {
                    // deltaXI unknown
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    a = Double.parseDouble(aEditText.getText().toString());
                    time = Double.parseDouble(timeEditText.getText().toString());
                    deltaXI = -vI * time + -0.5 * a * Math.pow(time, 2) + deltaXF;
                    vF = vI + a * time;
                } else {
                    showImpossibleSituation();
                    success = false;
                }
            } else if (aEditText.getText().length() == 0) {
                // equation 3 - missing a
                if (deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() > 0 && timeEditText.getText().length() > 0
                        && vFEditText.getText().length() > 0 && vIEditText.getText().length() == 0) {
                    // vI unknown
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    time = Double.parseDouble(timeEditText.getText().toString());
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    vI = 2 * (deltaXF - deltaXI) / time - vF;
                    a = (vF - vI) / time;
                } else if (deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() > 0
                        && vIEditText.getText().length() > 0 && vFEditText.getText().length() > 0 && timeEditText.getText().length() == 0) {
                    // time unknown
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    time = 2 * (deltaXF - deltaXI) / (vF + vI);
                    a = (vF - vI) / time;
                } else if (deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() > 0 && timeEditText.getText().length() > 0
                        && vIEditText.getText().length() > 0 && vFEditText.getText().length() == 0) {
                    // vF
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    vF = 2 * (deltaXF - deltaXI) / time - vI;
                    a = (vF - vI) / time;
                } else if (timeEditText.getText().length() > 0 && vIEditText.getText().length() > 0 && vFEditText.getText().length() > 0
                        && deltaXEditTextI.getText().length() == 0 && deltaXEditTextF.getText().length() == 0) {
                    // deltaX
                    time = Double.parseDouble(timeEditText.getText().toString());
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    deltaXI = 0;
                    deltaXF = 0.5 * (vF + vI) * time;
                    a = (vF - vI) / time;
                } else if (deltaXEditTextI.getText().length() > 0 && timeEditText.getText().length() > 0 && vIEditText.getText().length() > 0
                        && vFEditText.getText().length() > 0 && deltaXEditTextF.getText().length() == 0) {
                    // deltaXF
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    time = Double.parseDouble(timeEditText.getText().toString());
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    deltaXF = 0.5 * (vF + vI) * time + deltaXI;
                    a = (vF - vI) / time;
                } else if (deltaXEditTextF.getText().length() > 0 && timeEditText.getText().length() > 0 && vIEditText.getText().length() > 0
                        && vFEditText.getText().length() > 0 && deltaXEditTextI.getText().length() == 0) {
                    // deltaXI
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    time = Double.parseDouble(timeEditText.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    deltaXI = -0.5 * (vF + vI) * time + deltaXI;
                    a = (vF - vI) / time;
                } else {
                    showImpossibleSituation();
                    success = false;
                }
            } else if (timeEditText.getText().length() == 0) {
                // equation 4 - missing t
                if (deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() > 0 && vFEditText.getText().length() > 0
                        && vIEditText.getText().length() > 0 && aEditText.getText().length() == 0) {
                    // a unknown
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    a = (Math.pow(vF, 2) - Math.pow(vI, 2)) / (2 * (deltaXF - deltaXI));
                    time = (vF - vI) / a;
                } else if (deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() > 0 && vFEditText.getText().length() > 0
                        && aEditText.getText().length() > 0 && vIEditText.getText().length() == 0) {
                    // vI unknown
                    a = Double.parseDouble(aEditText.getText().toString());
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    if (Math.pow(vF, 2) - 2 * a * (deltaXF - deltaXI) < 0) {
                        showImpossibleSituation();
                        success = false;
                    } else {
                        vI = Math.sqrt(Math.pow(vF, 2) - 2 * a * (deltaXF - deltaXI));
                        time = (vF - vI) / a;
                    }

                } else if (deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() > 0 && vIEditText.getText().length() > 0
                        && aEditText.getText().length() > 0 && vFEditText.getText().length() == 0) {
                    // vF unknown
                    a = Double.parseDouble(aEditText.getText().toString());
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    if (Math.pow(vI, 2) + 2 * a * (deltaXF - deltaXI) < 0) {
                        showImpossibleSituation();
                        success = false;
                    } else {
                        vF = Math.sqrt(Math.pow(vI, 2) + 2 * a * (deltaXF - deltaXI));
                        time = (vF - vI) / a;
                    }
                } else if (vIEditText.getText().length() > 0 && aEditText.getText().length() > 0 && vFEditText.getText().length() > 0
                        && deltaXEditTextI.getText().length() == 0 && deltaXEditTextF.getText().length() == 0) {
                    // deltaX unknown
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    a = Double.parseDouble(aEditText.getText().toString());
                    deltaXF = (Math.pow(vF, 2) - Math.pow(vI, 2)) / (2 * a);
                    deltaXI = 0;
                    time = (vF - vI) / a;
                } else if (vIEditText.getText().length() > 0 && aEditText.getText().length() > 0 && vFEditText.getText().length() > 0
                        && deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() == 0) {
                    // deltaXF unknown
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    a = Double.parseDouble(aEditText.getText().toString());
                    deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                    deltaXF = (Math.pow(vF, 2) - Math.pow(vI, 2)) / (2 * a) + deltaXI;
                    time = (vF - vI) / a;
                } else if (vIEditText.getText().length() > 0 && aEditText.getText().length() > 0 && vFEditText.getText().length() > 0
                        && deltaXEditTextF.getText().length() > 0 && deltaXEditTextI.getText().length() == 0) {
                    // deltaXI unknown
                    vI = Double.parseDouble(vIEditText.getText().toString());
                    vF = Double.parseDouble(vFEditText.getText().toString());
                    a = Double.parseDouble(aEditText.getText().toString());
                    deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                    deltaXI = -((Math.pow(vF, 2) - Math.pow(vI, 2)) / (2 * a)) + deltaXF;
                    time = (vF - vI) / a;
                } else {
                    showImpossibleSituation();
                    success = false;
                }
            } else {
                showImpossibleSituation();
                success = false;
            }
        } else {

            if (deltaXEditTextI.getText().length() > 0 && deltaXEditTextF.getText().length() > 0
                    && angleEditText.getText().length() > 0 && vIEditText.getText().length() == 0) {
                theta = Math.toRadians(Double.parseDouble(angleEditText.getText().toString()));
                deltaXF = Double.parseDouble(deltaXEditTextF.getText().toString());
                deltaXI = Double.parseDouble(deltaXEditTextI.getText().toString());
                range = deltaXF - deltaXI;
                if (((range * GRAVITY) / (Math.sin(2 * theta))) < 0) {
                    showImpossibleSituation();
                    success = false;
                }
                else {
                    vI = Math.sqrt((range * GRAVITY) / (Math.sin(2 * theta)));
                    time = (2 * vI * Math.sin(theta)) / GRAVITY;
                    height = (range * Math.tan(theta)) / 4;
                    vF = vI;
                    //deltaXI = 0;
                    //deltaXF = range;
                }

            } else if (angleEditText.getText().length() > 0 && vIEditText.getText().length() > 0 && deltaXEditTextI.getText().length() == 0 && deltaXEditTextF.getText().length() == 0) {
                theta = Math.toRadians(Double.parseDouble(angleEditText.getText().toString()));
                vI = Double.parseDouble(vIEditText.getText().toString());
                range = (Math.pow(vI, 2) * Math.sin(2 * theta)) / GRAVITY;
                if (((range * GRAVITY) / (Math.sin(2 * theta))) < 0) {
                    showImpossibleSituation();
                    success = false;
                }
                else {
                    vF = vI;
                    time = (2 * vI * Math.sin(theta)) / GRAVITY;
                    deltaXI = 0;
                    deltaXF = range;
                    height = (range * Math.tan(theta)) / 4;
                }
            } else {
                showImpossibleSituation();
                success = false;
            }
        }
    }
    private void showImpossibleSituation() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.impossible_situation), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

