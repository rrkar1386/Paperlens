package com.paperlens.v3;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.content.Intent;
import android.widget.*;
import android.graphics.Color;
import android.view.ViewGroup;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(36, 50, 36, 36);

        TextView title = new TextView(this);
        title.setText("PaperLens V3");
        title.setTextSize(30);
        title.setTextColor(Color.rgb(55, 48, 40));

        TextView sub = new TextView(this);
        sub.setText("Automatic real-time paper rendering prototype");
        sub.setTextSize(16);

        Button access = new Button(this);
        access.setText("Enable PaperLens Accessibility");

        TextView info = new TextView(this);
        info.setText(
            "\nV3 focuses on low-latency change detection, adaptive sampling " +
            "and a paper-profile engine.\n\n" +
            "The renderer is a prototype: Android system-wide pixel replacement " +
            "is restricted, so this build demonstrates the analysis/rendering pipeline."
        );
        info.setTextSize(15);

        access.setOnClickListener(v ->
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        );

        root.addView(title);
        root.addView(sub);
        root.addView(
            access,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        );
        root.addView(info);

        setContentView(root);
    }
}
