package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.BufferedPipe;
import indsys.pipes.BufferedPipeExtended;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by mod on 10/29/15.
 */
public class FileReadFilterLine<T> extends AbstractFilter<Package> {
    private T _in;


    public FileReadFilterLine(T in) {
        _in = in;
    }

    @Override
    public Package read() {
        Package pack = (Package)((SourceFileLine)_in).getNextLine();
        if(pack.getIndex() != -2) {
            return pack;
        }else{
            return new PackageEndFile();
        }
    }

    @Override
    public void write(Package value) {
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

    public static void main(String[] args) {
        SourceFileLine sourceFileLine = new SourceFileLine(new File("aliceInWonderland.txt"));
        FileReadFilterLine fileReadFilterLine = new FileReadFilterLine(sourceFileLine);

        Package pack = (Package) fileReadFilterLine.read();
        System.out.println(pack.toString());
        while (pack.getIndex() != -2) {
            pack = (Package) fileReadFilterLine.read();
            System.out.println(pack.toString());

        }
    }
}
