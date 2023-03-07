package org.example;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        if(args.length < 2){
            System.out.println("<link file (full path/file)> <output directory (full path/)>");
            System.exit(0);
        }
        File linkFile = new File(args[0]);
        String dst = args[1];
        File htmlOutput = new File(dst+"index.html");
        //move script and style files to dst directory
        //URL styleResource = Main.class.getClassLoader().getResource("style.css");
       // URL scriptResource = Main.class.getClassLoader().getResource("script.js");
        InputStream in1 = Main.class.getClassLoader().getResourceAsStream("style.css");
        InputStream in2 = Main.class.getClassLoader().getResourceAsStream("script.js");
        Files.copy(in1,
                new File(dst+"style.css").toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(in2,
                new File(dst+"script.js").toPath(), StandardCopyOption.REPLACE_EXISTING);


        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("template.mustache");
        mustache.execute(new PrintWriter(htmlOutput), new Context(linkFile)).flush();
    }

}