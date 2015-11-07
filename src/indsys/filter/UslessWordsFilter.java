package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.BufferedPipe;
import indsys.pipes.Pipe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by mod on 11/2/15.
 */
public class UslessWordsFilter<T> extends AbstractFilter<T> {
    private T _in;
    private T _out;
    boolean endFile = false;
    boolean endfound = false;
    public UslessWordsFilter(T _in, T _out) {
        this._in = _in;
        this._out = _out;
    }

    @Override
    public T read() {
        ArrayList<String> stopword = readStopWords();
        if(((Pipe)_out).isEmpty()) {
            while (!endfound && !endFile) {
                LinkedList<String> split = new LinkedList<>();
                Package pair = (Package) ((Pipe) _in).getNext();
                endfound = ((Pipe) _out).isFull();
                if (endfound == false) {
                    if (pair.getIndex() != -2) {
                        if (pair.getIndex() != -1) {
                            for(String s:(LinkedList<String>)pair.getValue()) {
                                String[] arrtemp = s.split("\\s+");
                                if (stopword.contains(arrtemp[0].toLowerCase())) {

                                } else {
                                    split.add(s);
                                }
                            }
                            LinkedList<String>temp = split;
                            ((Pipe) _out).put(new PackageRotate<>(pair.getIndex(), temp));

                        } else {
                            //((Pipe) _out).put(pair);
                            endfound = true;
                        }
                    } else {
                        ((Pipe) _out).put(pair);
                        endFile = true;
                    }
                }
            }
        }
        endfound = false;
    return _out;
            }

    private ArrayList<String> readStopWords(){
        ArrayList<String>result = new ArrayList<>();
        BufferedReader br = null;
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(new File("stop-word-list.txt")));
            while ((sCurrentLine = br.readLine()) != null) {
                if(!sCurrentLine.equals("")) {
                   result.add(sCurrentLine);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public void write(T value) {

    }
    public boolean isEndFile(){
        return endFile;
    }
    public void setEndFound(){
        endfound = false;
    }
    public boolean getEndPackage(){
        return endfound;
    }
    public static void main(String[] args) {
        Pipe pipe = new BufferedPipe<>(4);
        Pipe pipe2 = new BufferedPipe<>(4);
        Pipe pipe3 = new BufferedPipe<>(4);
        Pipe pipe4 = new BufferedPipe<>(4);
        FileReadFilterLine frf = new FileReadFilterLine(new File("aliceInWonderland.txt"),pipe);


        AbstractFilter splitFilter = new Splitfilter<>(pipe,pipe2);


        RotateFilter rotateFilter = new RotateFilter(pipe2,pipe3);


        UslessWordsFilter uslessWordsFilter = new UslessWordsFilter(pipe3,pipe4);
        while(!uslessWordsFilter.isEndFile()) {
            frf.read();
            splitFilter.read();
            rotateFilter.read();
            System.out.println(uslessWordsFilter.read());
            pipe.clean();
            pipe2.clean();
            pipe3.clean();
            pipe4.clean();
            uslessWordsFilter.setEndFound();
        }
        System.out.println("done");
    }
}
