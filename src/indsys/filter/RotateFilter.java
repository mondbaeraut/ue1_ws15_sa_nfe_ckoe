package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.BufferedPipe;
import indsys.pipes.Pipe;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by mod on 10/30/15.
 */
public class RotateFilter<T> extends AbstractFilter<T> {
    private T _in;
    private T _out;
    boolean endFile = false;
    boolean endfound = false;
    public RotateFilter(T in,T out){
        _in = in;
        _out = out;
    }

    /**
     * TODO: Implement it
     * @return
     */
    @Override
    public T read() {
        if(((Pipe)_out).isEmpty()) {
            while (!endfound && !endFile) {
                Package pair = (Package) ((Pipe) _in).getNext();
                endfound = ((Pipe) _out).isFull();
                if (endfound == false) {
                    if (pair.getIndex() != -2) {
                        if (pair.getIndex() != -1) {
                            LinkedList<String> packagesplit = ((LinkedList<String>) pair.getValue());
                            LinkedList<String> resultList = new LinkedList<>();
                            if(packagesplit.size() >1) {
                                for (int i = 0; i < packagesplit.size(); i++) {
                                    StringBuilder srotate = new StringBuilder();
                                    srotate.append(packagesplit.get(i));
                                    srotate.append(" ");
                                    for (int k = 0; k < packagesplit.size(); k++) {
                                        if (k == i) {

                                        } else {
                                            srotate.append(packagesplit.get(k));
                                            srotate.append(" ");
                                        }
                                    }
                                    resultList.add(srotate.toString());

                                }

                            }else{
                                resultList.add(pair.getValue().toString());
                            }
                            ((Pipe) _out).put(new PackageRotate(pair.getIndex(), resultList));
                        } else {
                            endfound = true;
                            //((Pipe) _out).put(pair);
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


    /**
     * TODO: Optimieren mit lambda expressions, export rotate Methode in own one
     * @param value
     */
    @Override
    public void write(T value) {

        while (!(((Pipe)value).getNext() instanceof PackageEnd)) {
            LinkedList<String> result = new LinkedList<>();
           /* Package pair = (Package) ((PipeImpl)_in).getNext();
            if(pair.getValue() != -1) {
                    if (pair.getValue().getFirst() != "") {
                    for (int i = 0; i < pair.getValue().size(); i++) {
                        StringBuilder srotate = new StringBuilder();
                        srotate.append(pair.getValue().get(i));
                        srotate.append(" ");
                        for (int k = 0; k < pair.getValue().size(); k++) {
                            if (k == i) {

                            } else {
                                srotate.append(pair.getValue().get(k));
                                srotate.append(" ");
                            }
                        }
                        result.add(srotate.toString());
                    }
                    ((PipeImpl) _out).put(new PackagePAir(pair.getIndex(), result));
                }
            }*/
        }
    }
    public boolean isEndFile(){
        return endFile;
    }
    public void setEndFound(){
        endfound = false;
    }
    public static void main(String[] args) {
        /*Pipe pipe = new BufferedPipe<>(4);
        Pipe pipe2 = new BufferedPipe<>(4);
        Pipe pipe3 = new BufferedPipe<>(4);
        FileReadFilterLine frf = new FileReadFilterLine(new File("aliceInWonderland.txt"),pipe);
        AbstractFilter splitFilter = new Splitfilter<>(pipe,pipe2);
        RotateFilter rotateFilter = new RotateFilter(pipe2,pipe3);
        while(!rotateFilter.isEndFile()) {
            frf.read();
            splitFilter.read();
            System.out.println(rotateFilter.read());
            pipe.clean();
            pipe2.clean();
            pipe3.clean();
        }
        System.out.println("done");*/
    }
}
