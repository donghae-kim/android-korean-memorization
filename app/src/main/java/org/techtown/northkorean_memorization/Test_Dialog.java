package org.techtown.northkorean_memorization;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Test_Dialog extends Dialog implements View.OnClickListener {
    private static Test_Dialog testDialog;

    private Button dialog_Total_btn;
    private Button dialog_Normal_btn;
    private Button dialog_IT_btn;
    private Button dialog_Slang_btn;

    private Button dialog_All_btn;
    private Button dialog_Memo_btn;

    private Button dialog_Check_btn;

    private int field;
    private boolean range;

    private Context context;

    private Test_Dialog(Context context)  {
        super(context);
        this.context = context;
    }

    public static Test_Dialog getInstance(Context context) {
        testDialog = new Test_Dialog(context);

        return testDialog;
    }

    public void showDefaultDialog() {
        testDialog.setContentView(R.layout.main_dialog_layout);

        dialog_getView();
        dialog_init();
        dialog_setOnClick();

        testDialog.show();
    }

    private void dialog_init() {
        dialog_Total_btn.setSelected(true);
        dialog_All_btn.setSelected(true);
        field = 0;
        range = false;
    }

    private void dialog_getView() {
        dialog_Total_btn = testDialog.findViewById(R.id.dialog_total_btn);
        dialog_Normal_btn = testDialog.findViewById(R.id.dialog_normal_btn);
        dialog_IT_btn = testDialog.findViewById(R.id.dialog_it_btn);
        dialog_Slang_btn = testDialog.findViewById(R.id.dialog_slang_btn);

        dialog_All_btn = testDialog.findViewById(R.id.dialog_all_btn);
        dialog_Memo_btn = testDialog.findViewById(R.id.dialog_memo_btn);

        dialog_Check_btn = testDialog.findViewById(R.id.dialog_check_btn);
    }

    private void dialog_setOnClick() {
        dialog_Total_btn.setOnClickListener(this);
        dialog_Normal_btn.setOnClickListener(this);
        dialog_IT_btn.setOnClickListener(this);
        dialog_Slang_btn.setOnClickListener(this);

        dialog_All_btn.setOnClickListener(this);
        dialog_Memo_btn.setOnClickListener(this);

        dialog_Check_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(!dialog_Check_Field(view))
            dialog_Check_Range(view);

        if(view == dialog_Check_btn) {
            Intent intent = new Intent(getContext(), Test_Test.class);
            intent.putExtra("field", field);
            intent.putExtra("range", range);
            intent.putExtra("num", 20);

            getContext().startActivity(intent);
            dismiss();
        }
    }

    private boolean dialog_Check_Field(View view) {
        if(view == dialog_Total_btn) {
            dialog_unPressed_FieldBtn();
            dialog_Total_btn.setSelected(true);
            field = 0;
        }
        else if (view == dialog_Normal_btn) {
            dialog_unPressed_FieldBtn();
            dialog_Normal_btn.setSelected(true);
            field = 1;
        }
        else if (view == dialog_IT_btn) {
            dialog_unPressed_FieldBtn();
            dialog_IT_btn.setSelected(true);
            field = 2;
        }
        else if (view == dialog_Slang_btn) {
            dialog_unPressed_FieldBtn();
            dialog_Slang_btn.setSelected(true);
            field = 3;
        }
        else
            return false;

        return true;
    }

    private void dialog_Check_Range(View view) {
        if (view == dialog_All_btn) {
            dialog_unPressed_RangeBtn();
            dialog_All_btn.setSelected(true);
            range = false;
        }
        else if (view == dialog_Memo_btn) {
            dialog_unPressed_RangeBtn();
            dialog_Memo_btn.setSelected(true);
            range = true;
        }
    }

    private void dialog_unPressed_FieldBtn() {
        dialog_Total_btn.setSelected(false);
        dialog_Normal_btn.setSelected(false);
        dialog_IT_btn.setSelected(false);
        dialog_Slang_btn.setSelected(false);
    }

    private void dialog_unPressed_RangeBtn() {
        dialog_All_btn.setSelected(false);
        dialog_Memo_btn.setSelected(false);
    }

}