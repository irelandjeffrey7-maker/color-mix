package com.colormix.noar;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView preview;
    private TextView hex;
    private SeekBar red, green, blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(32, 32, 32, 32);

        TextView title = new TextView(this);
        title.setText("ColorMix");
        title.setTextSize(30);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        preview = new TextView(this);
        preview.setGravity(Gravity.CENTER);
        preview.setText("COLOR");
        preview.setTextSize(22);
        preview.setTextColor(Color.WHITE);
        preview.setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams previewParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 220);
        previewParams.setMargins(0, 28, 0, 18);
        root.addView(preview, previewParams);

        hex = new TextView(this);
        hex.setGravity(Gravity.CENTER);
        hex.setTextSize(24);
        hex.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        root.addView(hex, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        red = addChannel(root, "Red", 255);
        green = addChannel(root, "Green", 255);
        blue = addChannel(root, "Blue", 255);

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColor();
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
        red.setOnSeekBarChangeListener(listener);
        green.setOnSeekBarChangeListener(listener);
        blue.setOnSeekBarChangeListener(listener);

        red.setProgress(255);
        green.setProgress(0);
        blue.setProgress(0);
        updateColor();

        setContentView(root);
    }

    private SeekBar addChannel(LinearLayout root, String name, int max) {
        TextView label = new TextView(this);
        label.setText(name);
        label.setTextSize(18);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 0);
        root.addView(label, lp);

        SeekBar bar = new SeekBar(this);
        bar.setMax(max);
        root.addView(bar, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return bar;
    }

    private void updateColor() {
        int r = red.getProgress();
        int g = green.getProgress();
        int b = blue.getProgress();
        int color = Color.rgb(r, g, b);
        preview.setBackgroundColor(color);
        hex.setText(String.format("#%02X%02X%02X", r, g, b));

        int brightness = (r * 299 + g * 587 + b * 114) / 1000;
        preview.setTextColor(brightness < 128 ? Color.WHITE : Color.BLACK);
    }
}
