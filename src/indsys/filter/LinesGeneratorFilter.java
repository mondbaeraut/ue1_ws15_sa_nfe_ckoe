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
    public LinesGeneratorFilter(Pipe pipe, int lenghtOfLine){
        this.pipe = pipe;
        this.lengthOfLine = lenghtOfLine;
    }


    @Override
    public T read() {
        boolean linefull = false;
        StringBuilder builder = new StringBuilder();
        while(!linefull){
            String word = "";
           if(buffer.size() == 0) {
               word = (String)((Package) ((Pipe) pipe).getNext()).getValue();
           }else{
                word = buffer.removeFirst();
           }
            if(lengthOfLine >= actualLenght + word.length()){
                actualLenght += word.length();
                builder.append(word);
                builder.append(" ");
            }else{
                linefull = true;
                buffer.add(word);
            }
        }


        return (T)new PackageLine(0,builder.toString());
    }

    @Override
    public void write(T value) {

    }

    public static void main(String[] args) {
        SourceFileChar charFile = new SourceFileChar(new File("aliceInWonderland.txt"));
        FileReadFilterChar  fileReadFilterChar = new FileReadFilterChar(charFile);
        BufferedPipeExtended bufferedPipeExtended = new BufferedPipeExtended(fileReadFilterChar);
        WordBuilderFilter wordBuilderFilter = new WordBuilderFilter(bufferedPipeExtended);
        BufferedPipeExtended bufferedPipeExtended2  =new BufferedPipeExtended(wordBuilderFilter);
        LinesGeneratorFilter linesGeneratorFilter = new LinesGeneratorFilter(bufferedPipeExtended2,30);
        Package pack = (Package) linesGeneratorFilter.read();
        while(pack.getIndex() != -2){
            System.out.println(pack.toString());
            pack = (Package) wordBuilderFilter.read();

        }
    }
}
