package uebung1.warnsdorf;

import java.util.List;

import javax.swing.SwingWorker;

public class Worker extends SwingWorker<String, String>{

    @Override
    protected String doInBackground() throws Exception {
        //This is what's called in the .execute method
        for(int i = 0; i < 10; i++){
            //This sends the results to the .process method
            publish(String.valueOf(i));
            Thread.sleep(1000);
        }
        return null;
    }

    protected void process(List<String> item) {
        //This updates the UI
        //textArea.append(item + "\n");
    }
}