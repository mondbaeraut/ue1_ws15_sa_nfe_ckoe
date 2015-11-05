package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.BufferedPipe;
import indsys.pipes.Pipe;
import indsys.pipes.PipeImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by mod on 10/29/15.
 */
public class FileReadFilter<T> extends AbstractFilter<T> {
    private int _currentPos = 0;
    private T _in;
    private T _out;
    private LinkedList<PackageLine> packageLineLinkedList = new LinkedList<>();

    private boolean endFile = false;
    private boolean endfound = false;

    public FileReadFilter(T in, T out) {
        _in = in;
        _out = out;
        readFile();
    }

    private void readFile() {
        BufferedReader br = null;
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader((File) _in));
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

    @Override
    public T read() {
        if (((Pipe) _out).isEmpty()) {
            while (!endfound && !endFile) {
                //indsys.Data.Package pair = (Package) ((PipeImpl) _in).getNext();
                if (endfound == false) {
                    if (packageLineLinkedList.size() > 0) {
                        endfound = ((Pipe) _out).isFull();
                        if (endfound == false) {
                            Package pack = packageLineLinkedList.removeFirst();
                            ((Pipe) _out).put(pack);
                        }

                    } else {
                        endFile = true;
                        ((Pipe) _out).put(new PackageEndFile());
                        endfound = true;
                    }
                }

            }
        }

        //   ((Pipe) _out).put(new PackageEnd());
        System.out.println("--------------------------------------------------");
        endfound = false;
        return _out;
    }

    @Override
    public void write(T value) {
        BufferedReader br = null;
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader((File) value));
            int index = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                if (!sCurrentLine.equals("")) {
                    LinkedList<String> lltemp = new LinkedList<>();
                    lltemp.add(sCurrentLine);
                    //((PipeImpl) _out).put(new PackagePAir(index, lltemp));
                }
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isEndFile() {
        return endFile;
    }

    public void setEndFound() {
        endfound = false;
    }

    public static void main(String[] args) {
        BufferedPipe pipe1 = new BufferedPipe(4);
        FileReadFilter frf = new FileReadFilter(new File("aliceInWonderland.txt"), pipe1);
        while (!frf.isEndFile()) {
            pipe1 = (BufferedPipe) frf.read();
            System.out.println(pipe1.toString());
            pipe1.clean();
        }
    }
}
