package org.techtown.northkorean_memorization;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

public class Test_Dialog_Saved extends Dialog {
    private static Test_Dialog_Saved testDialogSaved;
    Context context;

    private Test_Dialog_Saved(Context context)  {
        super(context);
        this.context = context;
    }

    public static Test_Dialog_Saved getInstance(Context context) {
        testDialogSaved = new Test_Dialog_Saved(context);

        return testDialogSaved;
    }

    public void showDefaultDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customDialogView = inflater.inflate(R.layout.main_dialog_saved, null);
        builder.setView(customDialogView);
        builder.setPositiveButton("Yes", yesButtonClickListener);
        builder.setPositiveButton("Yes", noButtonClickListener);

        testDialogSaved.show();
    }

    private DialogInterface.OnClickListener yesButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
    };

    private DialogInterface.OnClickListener noButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Test_Dialog.getInstance(getContext()).showDefaultDialog();

        }
    };

}
