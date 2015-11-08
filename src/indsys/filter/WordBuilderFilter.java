package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.BufferedPipeExtended;
import indsys.pipes.Pipe;

import java.io.File;

/**
 * Created by mod on 11/6/15.
 */
public class WordBuilderFilter<T> extends AbstractFilter<T> {
    public T pipe;

    public WordBuilderFilter(T pipe) {
        this.pipe = pipe;
    }

    @Override
    public T read() {
        boolean wholewordfound = false;
        boolean wordstartfound = false;
        String word = "";
        while (!wholewordfound) {
            Package pack = (Package) ((Pipe) pipe).getNext();
            if (pack.getIndex() != -2) {
                char ctemp = ((char) pack.getValue());

                if (ctemp != '\n' && ctemp != '\r') {
                    if (ctemp != 65279 && ctemp != 32) {
                        word += ((char) pack.getValue());
                        wordstartfound = true;
                    } else {
                        if (wordstartfound) {
                            wholewordfound = true;
                        }
                    }
                } else {
                    if (wordstartfound) {
                        wholewordfound = true;
                    }
                }
            } else {
                return (T) pack;
            }
        }
        return (T) new PackageLine(0, word);
    }

    @Override
    public void write(T value) {

    }


    public static void main(String[] args) {
        SourceFileChar charFile = new SourceFileChar(new File("aliceInWonderland2.txt"));
        FileReadFilterChar fileReadFilterChar = new FileReadFilterChar(charFile);
        BufferedPipeExtended bufferedPipeExtended = new BufferedPipeExtended(fileReadFilterChar);
        WordBuilderFilter wordBuilderFilter = new WordBuilderFilter(bufferedPipeExtended);

        Package pack = (Package) wordBuilderFilter.read();
        System.out.println(pack.toString());
        while (pack.getIndex() != -2) {
            pack = (Package) wordBuilderFilter.read();
            System.out.println(pack.toString());

        }
    }
}
