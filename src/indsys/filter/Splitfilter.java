package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.BufferedPipe;
import indsys.pipes.BufferedPipeExtended;
import indsys.pipes.Pipe;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by mod on 10/30/15.
 */
public class Splitfilter<T> extends AbstractFilter<T>{
    private T pipe;
    private T _out;
   private int index = 0;
    LinkedList<String> buffer = new LinkedList<>();
    public Splitfilter(T pipe){
        this.pipe = pipe;
    }

    @Override
    public T read(){


            if (buffer.size() > 0) {
                return (T)new PackageLine(index,buffer.removeFirst());
            } else {
                Package pair = (Package)((Pipe)pipe).getNext();
                if(pair.getIndex() == -2) {
                    return (T)new PackageEndFile();
                }

                String[] arrtemp = ((String) pair.getValue()).split("\\s+");
                index = pair.getIndex();
                for(String st:arrtemp) {
                    buffer.add(st);
                }

                return (T)new PackageLine(index,buffer.removeFirst());
            }
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

    public static void main(String[] args) {
        System.out.println("dsad");
        SourceFileLine sourceFileLine = new SourceFileLine(new File("aliceInWonderland.txt"));
        FileReadFilterLine fileReadFilterLine = new FileReadFilterLine(sourceFileLine);

        BufferedPipeExtended pipe = new BufferedPipeExtended(fileReadFilterLine);

        Splitfilter splitfilter = new Splitfilter(pipe);
        Package pack = (Package) splitfilter.read();
        System.out.println(pack.toString());
        while (pack.getIndex() != -2) {
            pack = (Package) splitfilter.read();
            System.out.println(pack.toString());

        }
        }
}
