package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;

import java.io.File;

/**
 * Created by mod on 11/6/15.
 */
public class FileReadFilterChar<T> extends AbstractFilter {

    private T _in;

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