package ru.sashok.study.async2;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;


public class GetDataTask extends AsyncTask<Void, String, Void>
{
    private WeakReference<RetainFragment> retainFragment;

    public GetDataTask(RetainFragment retainFragment) {
        this.retainFragment = new WeakReference<>(retainFragment);
    }

    @Override
    protected Void doInBackground(Void... voids) {


        try {
            Thread.sleep(2000);
            String firstResult = "aaa";
            publishProgress(firstResult);

            Thread.sleep(5000);
            String secondResult = "bbb";
            publishProgress(secondResult);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {

        if (!isCancelled()) {

            super.onProgressUpdate(values);

            RetainFragment retainFragment = this.retainFragment.get();
            retainFragment.setSuccess(values[0]);
        }
    }
}



//
//    @Override
//    protected Void doInBackground(Void... voids) {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        String[] result = {"aaa", "bbb", "ccc"};
//
//        return result;
//    }
//
//    @Override
//    protected void onPostExecute(Void) {
//        super.onPostExecute(strings);
//
//        RetainFragment retainFragment = this.retainFragment.get();
//        retainFragment.setSuccess(strings);
//    }
//}
