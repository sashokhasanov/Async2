package ru.sashok.study.async2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class RetainFragment extends Fragment {

    public static final String RETAIN_FRAGMENT_TAG = "RetainFragment";

    private List<String> operationResult;

    private LoadListener loadListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof  MainActivity)
        {
            loadListener = (LoadListener) context;
            if (operationResult != null)
            {
                loadListener.onResult(operationResult);
                operationResult = null;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        loadListener = null;
        super.onDetach();
    }

    public void setSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onResult(data);
        }
        else
        {
            if (this.operationResult == null)
            {
                this.operationResult = new ArrayList<>();
            }
            this.operationResult.add(data);
        }
    }

    public interface LoadListener
    {
        void onResult(List<String> data);

        void onResult(String data);
    }
}
