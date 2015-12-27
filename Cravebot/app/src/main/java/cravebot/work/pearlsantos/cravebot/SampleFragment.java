/**
 * Created by Pearl Santos on 12/16/2015.
 */
package cravebot.work.pearlsantos.cravebot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cravebot.R;

public class SampleFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_one, container,
                false);

        return view;
    }
}
