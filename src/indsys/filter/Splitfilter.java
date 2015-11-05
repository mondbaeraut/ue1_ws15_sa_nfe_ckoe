package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.BufferedPipe;
import indsys.pipes.Pipe;
import indsys.pipes.PipeImpl;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by mod on 10/30/15.
 */
public class Splitfilter<T> extends AbstractFilter<T>{
    private T _in;
    private T _out;
    boolean endFile = false;
    boolean endfound = false;
    public Splitfilter(T in,T out){
        _in = in;
        _out = out;
    }
    @Override
    public T read(){
        if(((Pipe)_out).isEmpty()) {
            while (!endfound && !endFile) {
                LinkedList<String> split = new LinkedList<>();
                Package pair = (Package) ((Pipe) _in).getNext();
                endfound = ((Pipe) _out).isFull();
                if (endfound == false) {
                    if (pair.getIndex() != -2) {
                        if (pair.getIndex() != -1) {
                            String[] arrtemp = ((String) pair.getValue()).split("\\s+");
                            for (String t : arrtemp) {
                                if (!t.equals("\uFEFF") && t.length() != 0) {
                                    split.add(t);
                                }
                            }
                            ((Pipe) _out).put(new PackageSplit(pair.getIndex(), split));
                        } else {
                            //((Pipe) _out).put(pair);
                            endfound = true;
                        }
                    } else {
                        ((Pipe)_out).put(pair);
                        endFile = true;
                    }
                }
            }
        }
        endfound = false;
        return _out;
    }

    /**
     * Skip all whitespaces :)
     * TODO: export in own method
     * @param value
     */

    @Override
    public void write(T value){

     /*   while(((PipeImpl)_in).getNext() instanceof PackageEnd){
            LinkedList<String> split = new LinkedList<>();
                PackagePAir pair = (PackagePAir) ((PipeImpl)_in).read();
                if (pair.getValue().getFirst() != null) {
                    String svalue = (String) pair.getValue().getFirst();
                    String[] arrtemp = svalue.split("\\s+");
                    for (String t : arrtemp) {
                        if(!t.equals("\uFEFF") && t.length() != 0) {
                            split.add(t);
                        }
                    }
                    ((PipeImpl)_out).write(new PackagePAir(pair.getIndex(), split));
                } else {
                    System.out.println("Kein erstes element");
                }
            }*/

    }
    public boolean isEndFile(){
        return endFile;
    }
    public void setEndFound(){
        endfound = false;
    }
    public static void main(String[] args) {
        BufferedPipe pipe = new BufferedPipe<>(4);
        BufferedPipe pipe2 = new BufferedPipe<>(4);
        FileReadFilter frf = new FileReadFilter(new File("aliceInWonderland.txt"), pipe);
        Splitfilter splitfilter = new Splitfilter(pipe, pipe2);
        while(!splitfilter.isEndFile()) {
            frf.read();
            System.out.println(splitfilter.read().toString());
            pipe.clean();
            pipe2.clean();
            splitfilter.setEndFound();
        }
        }
}
