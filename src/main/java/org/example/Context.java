package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// COMMENT is only the initial state
enum Section{
    URL,
    DESCRIPTION,
    TAG,
    COMMENT
}
public class Context {

    List<Link> listOfLinks;
    File linkFile;
    Context(File linkFile){
        this.linkFile = linkFile;
        this.listOfLinks = new ArrayList<>();
        buildListOfLinks();

    }

    void buildListOfLinks(){
        try (BufferedReader br = new BufferedReader(new FileReader(linkFile))) {
            String line;
            Link link = new Link(new ArrayList<>(), "", new ArrayList<>());;
            Section section = Section.COMMENT;
            while ((line = br.readLine()) != null) {
                // when a new url section is encountered, the link constructed from
                // the previous section (unless we're on the first one is added to the
                // list, and a new link object is created.
                if (line.startsWith("@")){
                    section = Section.URL;
                    if (!link.urls.isEmpty()){
                        listOfLinks.add(link);
                    }
                    link = new Link(new ArrayList<>(), "", new ArrayList<>());
                    continue;
                }else if (line.startsWith("?")){
                    section = Section.DESCRIPTION;
                    continue;
                }else if (line.startsWith("+")){
                    section = Section.TAG;
                    continue;
                }else if (line.startsWith("#")){
                    continue;
                } else if (line.isBlank()) {
                    continue;
                }
                switch (section) {
                    case URL -> link.urls.add(new Url(line));
                    case DESCRIPTION -> link.description = line;
                    case TAG -> link.tags.add(new Tag(line));
                }

            }
        } catch (IOException e){
            System.out.println(e);

        }
    }
    List<Link> links() {
        return this.listOfLinks;
    }
    static class Link {
        List<Url> urls;
        String description;
        List<Tag> tags;

        Link(List<Url> urls, String description, List<Tag> tags) {
            this.urls = urls;
            this.description = description;
            this.tags = tags;
        }
    }

    static class Tag {
        String tag;

        Tag(String tag) {
            this.tag = tag;
        }
    }
    static class Url {
        String url;

        Url(String url) {
            this.url = url;
        }
    }
}
