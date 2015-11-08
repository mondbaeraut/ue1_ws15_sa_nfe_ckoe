package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.BufferedPipe;
import indsys.pipes.BufferedPipeExtended;
import indsys.pipes.Pipe;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by mod on 11/7/15.
 */
public class LinesGeneratorFilter<T> extends AbstractFilter<T> {
    private Pipe pipe;
    private int lengthOfLine = 0;
    private int actualLenght = 0;
    private LinkedList<String> buffer = new LinkedList<>();
    boolean endFile = false;

    public LinesGeneratorFilter(Pipe pipe, int lenghtOfLine) {
        this.pipe = pipe;
        this.lengthOfLine = lenghtOfLine;
    }


    @Override
    public T read() {
        boolean linefull = false;
        StringBuilder builder = new StringBuilder();
        if (!endFile) {
            while (!linefull) {

                Package pack = null;
                if (buffer.isEmpty()) {
                    pack = (Package) ((Pipe) pipe).getNext();
                } else {
                    pack = new PackageLine(0, buffer.removeFirst());
                }
                if (pack.getIndex() == -2) {
                    endFile = true;
                    linefull = true;
                } else {
                    String word = "";
                    if (buffer.size() == 0) {
                        word = (String) pack.getValue();
                    }
                    //+1 cause of whitespace
                    if (lengthOfLine - 1 > actualLenght + word.length()) {
                        actualLenght += word.length();
                        actualLenght += 1;
                        builder.append(word);
                        builder.append(" ");
                    } else {
                        actualLenght = 0;
                        linefull = true;
                        buffer.add(word);
                    }
                }
            } return (T) new PackageLine(lengthOfLine, builder.toString());

        }
        return(T)new PackageEndFile();
    }

    @Override
    public void write(T value) {

    }

    public static void main(String[] args) {
        SourceFileChar charFile = new SourceFileChar(new File("aliceInWonderland.txt"));
        FileReadFilterChar fileReadFilterChar = new FileReadFilterChar(charFile);
        BufferedPipeExtended bufferedPipeExtended = new BufferedPipeExtended(fileReadFilterChar);
        WordBuilderFilter wordBuilderFilter = new WordBuilderFilter(bufferedPipeExtended);
        BufferedPipeExtended bufferedPipeExtended2 = new BufferedPipeExtended(wordBuilderFilter);
        LinesGeneratorFilter linesGeneratorFilter = new LinesGeneratorFilter(bufferedPipeExtended2, 9);
        Package pack = (Package) linesGeneratorFilter.read();
        System.out.println(pack.toString());
        while (pack.getIndex() != -2) {
            pack = (Package) linesGeneratorFilter.read();
            System.out.println(pack.toString());

        }
    }
}
