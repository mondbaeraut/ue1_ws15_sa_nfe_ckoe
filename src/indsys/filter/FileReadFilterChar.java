package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.PipeImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by mod on 11/6/15.
 */
public class FileReadFilterChar<T> extends AbstractFilter {
    private int _currentPos = 0;
    private T _in;
    private T _out;

    public FileReadFilterChar(T in, T out) {
        _in = in;
        _out = out;
    }
    public FileReadFilterChar(T in){
        _in = in;
    }
    @Override
    public Object read() {
        Package pack = (Package)((SourceFileChar)_in).getNextChar();
        if(pack.getIndex() != -2) {
            return pack;
        }else{
            return new PackageEndFile();
        }
    }

    @Override
    public void write(Object value) {

    }

    public static void main(String[] args) {
        SourceFileChar charFile = new SourceFileChar(new File("aliceInWonderland.txt"));
        FileReadFilterChar fileReadFilterChar = new FileReadFilterChar(charFile);
        Package pack = (Package) fileReadFilterChar.read();
        System.out.println(pack.toString());
        while(pack.getIndex() != -2){
            pack = (Package) fileReadFilterChar.read();
            System.out.println(pack.toString());
        }

        System.out.println("done");
    }
}
