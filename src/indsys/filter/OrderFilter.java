package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.BufferedPipe;
import indsys.pipes.Pipe;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by mod on 10/30/15.
 */
public class OrderFilter<T> extends AbstractFilter<T> {
    private T _in;
    private T _out;
    boolean endFile = false;
    boolean endfound = false;

    public OrderFilter(T in,T out){
        _in = in;
        _out = out;
    }

    @Override
    public T read() {
        if(((Pipe)_out).isEmpty()) {
            while (!endfound && !endFile) {
                Package pair = (Package) ((Pipe) _in).getNext();
                HashMap<Integer,LinkedList<String>> map = new HashMap<>();
                endfound = ((Pipe) _out).isFull();
                if (endfound == false) {
                    if(pair != null) {
                        if (pair.getIndex() != -2) {
                            if (pair.getIndex() != -1) {
                                for (String s : (LinkedList<String>) pair.getValue()) {
                                    int key = s.toUpperCase().charAt(0)-65;
                                    if (map.containsKey(key)) {
                                        LinkedList<String> temp = map.get(key);
                                        temp.add(s + " " + pair.getIndex());
                                    } else {
                                        map.put(key,new LinkedList<>());
                                        LinkedList<String> temp = map.get(key);
                                        temp.add(s + " " + pair.getIndex());

                                    }
                                    ((Pipe) _out).put(new PackageOrderEnrichment(map));
                                }
                            } else {
                                endfound = true;
                            }
                        } else {
                            ((Pipe) _out).put(pair);
                            endFile = true;
                        }
                    }else{
                        endfound = true;
                    }
                }
            }
        }
        endfound = false;
        return _out;
    }

    /**
     * TODO: implement
     * @param input
     * @return
     */
    private LinkedList<String> order(LinkedList<String> input){
        LinkedList<String> result = new LinkedList<>();


        return input;
    }
    @Override
    public void write(T value) {
/*        LinkedList<PackageAlphabetic> temp = new LinkedList<>();
        while (((PipeImpl) value).hasNext()) {
            PackagePAir pair = (PackagePAir) ((PipeImpl) _in).read();
            for (int i = 0; i < pair.getValue().size(); i++) {
                temp.add(new PackageAlphabetic(pair.getIndex(),(String)pair.getValue().get(i)));
            }
          }
        for(int k = 0; k < 26;k++){
            for(int y = 0; y < temp.size();y++){
                if(temp.get(y).getFirstElement() == k){
                    ((PipeImpl)_out).write(temp.get(y));
                }
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
        Pipe pipe = new BufferedPipe<>(4);
        Pipe pipe2 = new BufferedPipe<>(4);
        Pipe pipe3 = new BufferedPipe<>(4);
        Pipe pipe4 = new BufferedPipe<>(4);
        FileReadFilterLine frf = new FileReadFilterLine(new File("aliceInWonderland.txt"),pipe);
        AbstractFilter splitFilter = new Splitfilter<>(pipe,pipe2);
        RotateFilter rotateFilter = new RotateFilter(pipe2,pipe3);
        OrderFilter orderFilter = new OrderFilter(pipe3,pipe4);
        System.out.println("done");
        while(!orderFilter.isEndFile()) {
            frf.read();
            splitFilter.read();
            rotateFilter.read();
            System.out.println(orderFilter.read());
            pipe.clean();
            pipe2.clean();
            pipe3.clean();
            pipe4.clean();
            orderFilter.setEndFound();
        }
    }
}
