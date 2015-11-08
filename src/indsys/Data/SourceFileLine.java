package indsys.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by mod on 11/8/15.
 */
public class SourceFileLine {
    private static LinkedList<PackageLine> packageLineLinkedList = new LinkedList<>();
    private File file;

    public SourceFileLine(File file){
        this.file = file;
        readFile();
    }
    public Package getNextLine(){
        if(packageLineLinkedList.size() > 0){
            return packageLineLinkedList.removeFirst();
        }
        return new PackageEndFile();
    }
    private void readFile(){
        BufferedReader br = null;
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(file));
            int index = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                if (!sCurrentLine.equals("")) {
                    packageLineLinkedList.add(new PackageLine(index, sCurrentLine));
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
