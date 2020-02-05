package com.example.dimappfinal.Entregable;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;


import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.example.dimappfinal.R;

public class Activity6 extends AppCompatActivity {
    private View root;
    public static int currentColor = 0xffff0000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6);
        root = findViewById(R.id.view6);
        root.setBackgroundColor(0xffffffff);
        Toolbar toolbar = findViewById(R.id.my_toolbar6);
        setSupportActionBar(toolbar);
        SeekBar seekBar = findViewById(R.id.seekBar2);
        seekBar.setVisibility(View.INVISIBLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        findViewById(R.id.color_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyView.borrado = false;
                SeekBar seekBar = findViewById(R.id.seekBar2);
                seekBar.setVisibility(View.INVISIBLE);
                final Context context = Activity6.this;

                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("Seleccione el color:")
                        .initialColor(0xffffffff)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorChangedListener(new OnColorChangedListener() {
                            @Override
                            public void onColorChanged(int selectedColor) {
                                // Handle on color change
                                Log.d("ColorPicker", "onColorChanged: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                currentColor = selectedColor;
                                if (allColors != null) {
                                    StringBuilder sb = null;

                                    for (Integer color : allColors) {
                                        if (color == null)
                                            continue;
                                        if (sb == null)
                                            sb = new StringBuilder("Color List:");
                                        sb.append("\r\n#" + Integer.toHexString(color).toUpperCase());
                                    }

                                    if (sb != null)
                                        Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .showColorEdit(true)
                        .setColorEditTextColor(ContextCompat.getColor(Activity6.this, android.R.color.holo_blue_bright))
                        .build()
                        .show();
            }
        });



        findViewById(R.id.gomaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentColor=0xffffffff;
                MyView.borrado = true;
                SeekBar seekBar = findViewById(R.id.seekBar2);
                seekBar.setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.borrarButon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyView mv = findViewById(R.id.view6);
                mv.limpiarTrazos();
                SeekBar seekBar = findViewById(R.id.seekBar2);
                seekBar.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.grosorButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SeekBar seekBar = findViewById(R.id.seekBar2);
                if(seekBar.getVisibility()== View.VISIBLE) {
                    MyView.GROSOR_LINEA = seekBar.getProgress();
                    seekBar.setVisibility(View.INVISIBLE);
                }
                else seekBar.setVisibility(View.VISIBLE);

            }
        });
    }
    public void visibleSeekBar(boolean VISIBLE, int grosorActual){
        SeekBar seekBar = findViewById(R.id.seekBar2);
        seekBar.setProgress(grosorActual);
        if (VISIBLE) {
            seekBar.setVisibility(View.VISIBLE);
        } else {
            seekBar.setVisibility(View.INVISIBLE);
        }
    }
    public void actualizarGrosorSeekBar(int grosorActual){
        SeekBar seekBar = findViewById(R.id.seekBar2);
        seekBar.setProgress(grosorActual);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
