package org.techtown.northkorean_memorization;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class Test_DriveHolder {
    public ImageView correctImg;
    public TextView num;
    public TextView north;
    public TextView south;
    public CheckBox memory;
    public CheckBox bookMark;

    public Test_DriveHolder(View root) {
        correctImg = root.findViewById(R.id.review_image);
        num = root.findViewById(R.id.review_num);
        north = root.findViewById(R.id.review_north);
        south = root.findViewById(R.id.review_south);
        memory = root.findViewById(R.id.review_memory);
        bookMark = root.findViewById(R.id.review_bookmark);

    }


}
