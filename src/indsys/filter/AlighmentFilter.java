package indsys.filter;

import indsys.Data.*;
import indsys.Data.Package;
import indsys.pipes.BufferedPipeExtended;
import indsys.pipes.Pipe;

import java.io.File;

/**
 * Created by mod on 11/8/15.
 */
public class AlighmentFilter<T> extends AbstractFilter<T> {

    private T pipe;
    private AlignmentEnum alignmentEnum;
    public AlighmentFilter(AlignmentEnum alignmentEnum, T pipe){
        this.alignmentEnum = alignmentEnum;
        this.pipe = pipe;
    }

    @Override
    public T read() {
        indsys.Data.Package pack = (Package) ((Pipe) pipe).getNext();
        StringBuilder builder = new StringBuilder();
        if(pack.getIndex() != -2){
            switch (alignmentEnum){
                case LEFT:
                    int stringLengthL = ((String)pack.getValue()).length();
                    int numberofWhiteSpacesL = pack.getIndex()-stringLengthL;
                    if(numberofWhiteSpacesL >= 0){
                        for(int i = 0; i < numberofWhiteSpacesL-1; i++){
                            builder.append(" ");
                        }
                        builder.append((String)pack.getValue());
                    }
                    break;
                case RIGHT:
                    int stringLengthR = ((String)pack.getValue()).length();
                    int numberofWhiteSpacesR = pack.getIndex()-stringLengthR;
                    if(numberofWhiteSpacesR >= 0){
                        builder.append((String)pack.getValue());
                        for(int i = 0; i < numberofWhiteSpacesR-1; i++){
                            builder.append(" ");
                        }
                    }
                    break;
                case CENTER:
                    int stringLengthC = ((String)pack.getValue()).length();
                    int whitespaces = pack.getIndex()-stringLengthC;
                    int numberofWhiteSpacesRC = whitespaces /2;
                    int numberofWhiteSpactesLC = whitespaces - numberofWhiteSpacesRC;

                    for(int i = 0; i < numberofWhiteSpacesRC; i++){
                        builder.append(" ");
                    }
                    builder.append((String)pack.getValue());
                    for(int i = 0; i < numberofWhiteSpactesLC-1; i++){
                        builder.append(" ");
                    }
                    break;
            }
        }
        return (T)new PackageLine(pack.getIndex(),builder.toString());
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
        LinesGeneratorFilter linesGeneratorFilter = new LinesGeneratorFilter(bufferedPipeExtended2,10);
        BufferedPipeExtended bufferedPipeExtended3 = new BufferedPipeExtended(linesGeneratorFilter);
        AlighmentFilter alighmentFilter = new AlighmentFilter(AlignmentEnum.CENTER,bufferedPipeExtended3);
        Package pack = (Package) alighmentFilter.read();
        while(pack.getIndex() != -2){
            System.out.println(pack.toString());
            pack = (Package) alighmentFilter.read();

        }
    }
}
