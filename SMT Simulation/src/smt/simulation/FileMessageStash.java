/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smt.simulation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.nio.file.StandardOpenOption.*;

/**
 *
 * @author Tim
 */
public class FileMessageStash extends MessageStash {

    Path directoryPath = Paths.get("C:\\SMTSimulation");
    int count = 0;

    @Override
    boolean isEmpty() {
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath);
            return (!directoryStream.iterator().hasNext());
        } catch (IOException e) {
            return true;
        }
    }

    @Override
    String poll() {
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath);
            Path filePath = directoryStream.iterator().next();
            List<String> message = Files.readAllLines(filePath, Charset.defaultCharset());
            Files.delete(filePath);
            return message.toString();
        } catch (IOException ex) {
            return "File not found!!!";
        }
    }

    @Override
    void add(String message) {
        List <String> messageList = new ArrayList<String>();
        messageList.add(message);
        try {
            Files.write(directoryPath.resolve("file"+count++), messageList, Charset.defaultCharset(), CREATE_NEW);
        } catch (IOException ex) {
        }
    }
}
