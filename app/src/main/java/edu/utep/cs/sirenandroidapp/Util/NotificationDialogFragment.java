package edu.utep.cs.sirenandroidapp.Util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import edu.utep.cs.sirenandroidapp.R;

public class NotificationDialogFragment extends DialogFragment {
    private NotificationDialogListener listener;


    public static NotificationDialogFragment newInstance(int msg) {
        NotificationDialogFragment frag = new NotificationDialogFragment();
        Bundle args = new Bundle();
        args.putInt("msg", msg);
        frag.setArguments(args);
        return frag;
    }

    public interface NotificationDialogListener {
        void onDeleteDialogPositiveClick();
    }

    public void setNotificationDialogListener(NotificationDialogListener listener){
        this.listener=listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int msg=getArguments().getInt("msg");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(msg==R.string.delete_question){
                            listener.onDeleteDialogPositiveClick();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NotificationDialogFragment.this.getDialog().cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

