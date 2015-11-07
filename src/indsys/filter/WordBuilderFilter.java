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
    public WordBuilderFilter(T pipe){
        this.pipe = pipe;
    }
    @Override
    public T read() {
        boolean wholewordfound = false;
        boolean wordstartfound = false;
        String word = "";
        while(!wholewordfound) {
            Package pack = (Package) ((Pipe) pipe).getNext();
            char ctemp = ((char)pack.getValue());
            if(pack.getIndex() != -2) {
               if(ctemp != '\n') {
                   if (ctemp != 65279 && ctemp != 32) {
                       word += ((char) pack.getValue());
                       wordstartfound = true;
                   } else {
                       if (wordstartfound) {
                           wholewordfound = true;
                       }
                   }
               }else{
                   if(wordstartfound){
                       wholewordfound = true;
                   }
               }
            }else{
                return (T)pack;
            }
        }
        return (T)new PackageLine(0,word);
    }

    @Override
    public void write(T value) {

    }

    private Package buildWord(Package pack){
        boolean wordcomplete = false;
        String word = "";
        while(!wordcomplete){
            if(((char)pack.getValue()) != 65279){
                word += (char)pack.getValue();

            }else{
                System.out.println((int)'\uFEFF');
                if(word.length() > 0){
                    wordcomplete = true;
                }
            }
        }
        return new PackageLine(1,word);
    }

    public static void main(String[] args) {
        SourceFileChar charFile = new SourceFileChar(new File("aliceInWonderland.txt"));
        FileReadFilterChar fileReadFilterChar = new FileReadFilterChar(charFile);
        BufferedPipeExtended bufferedPipeExtended = new BufferedPipeExtended(fileReadFilterChar);
        WordBuilderFilter wordBuilderFilter = new WordBuilderFilter(bufferedPipeExtended);

        Package pack = (Package) wordBuilderFilter.read();
        while(pack.getIndex() != -2){
            System.out.println(pack.toString());
            pack = (Package) wordBuilderFilter.read();

        }
    }
}
