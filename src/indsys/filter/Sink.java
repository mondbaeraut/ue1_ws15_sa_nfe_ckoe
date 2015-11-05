package indsys.filter;

import indsys.Data.Package;
import indsys.pipes.BufferedPipe;
import indsys.pipes.Pipe;


import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by mod on 10/30/15.
 */
public class Sink<T>{
    private T _in;
    private T _out;
    boolean endFile = false;
    boolean endfound = false;
    private HashMap<Integer,LinkedList<String>> result = new HashMap<>();
    public Sink(T in, T out){
        _in = in;
        _out = out;
    }

    public T read() {
        while (!endfound && !endFile) {
            indsys.Data.Package pair = (Package) ((Pipe)_in).getNext();
            endfound = ((Pipe) _in).isEmpty();
            if (endfound == false) {
                if (pair.getIndex() != -1) {


                    result.putAll((HashMap<Integer, LinkedList<String>>) pair.getValue());
                    HashMap<Integer, LinkedList<String>>temp = (HashMap<Integer, LinkedList<String>>) pair.getValue();
                    for(Map.Entry e : temp.entrySet()){
                    if (result.containsKey(temp)) {
                        LinkedList<String> temp2 = result.get(key);
                        temp2.add(s + " " + pair.getIndex());
                    } else {
                        result.put(key,new LinkedList<>());
                        LinkedList<String> temp2 = result.get(key);
                        temp.add(s + " " + pair.getIndex());

                    }}
                }

            }
                if(pair.getIndex() != -2){

                }
                else {
                    System.out.println("DONE!!!!");
                    endFile = true;
                }
        }

        endfound = false;
        return _out;
    }
    private void writeFile() throws IOException {
        File file = new File("result.txt");
        // creates the file
        file.createNewFile();
        // creates a FileWriter Object
        FileWriter writer = new FileWriter(file);
        // Writes the content to the file
        writer.write("Fuck you alice!");
        writer.write("\n");
        for(Map.Entry e : result.entrySet()){
            for(String p:((LinkedList<String>)e.getValue())){
                writer.write((String)p);
                writer.write("\n");
            }
        }
        writer.flush();
        writer.close();

        //Creates a FileReader Object
        FileReader fr = new FileReader(file);
        char [] a = new char[50];
        fr.read(a); // reads the content to the array
        for(char c : a)
            System.out.print(c); //prints the characters one by one
        fr.close();
    }
    public boolean isEndFile(){
        return endFile;
    }
    public void setEndFound(){
        endfound = false;
    }
    public static void main(String[] args) {
        Pipe pipe = new BufferedPipe<>(4);
        Pipe pipe2 = new BufferedPipe<>(4);
        Pipe pipe3 = new BufferedPipe<>(4);
        Pipe pipe4 = new BufferedPipe<>(4);
        Pipe pipe5 = new BufferedPipe<>(4);
        Pipe pipe6 = new BufferedPipe<>(4);

        FileReadFilter frf = new FileReadFilter(new File("aliceInWonderland.txt"),pipe);
        AbstractFilter splitFilter = new Splitfilter<>(pipe,pipe2);
        RotateFilter rotateFilter = new RotateFilter(pipe2,pipe3);
        UslessWordsFilter uslessWordsFilter = new UslessWordsFilter(pipe3,pipe4);
        OrderFilter orderFilter = new OrderFilter(pipe4,pipe5);
        Sink sinkFilter = new Sink(pipe5,pipe6);

        while(!sinkFilter.isEndFile()) {
            frf.read();
            splitFilter.read();
            rotateFilter.read();
            uslessWordsFilter.read();
            orderFilter.read();
            sinkFilter.read();
            pipe.clean();
            pipe2.clean();
            pipe3.clean();
            pipe4.clean();
            pipe5.clean();
        }
        try {
            sinkFilter.writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}
