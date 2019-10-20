package com.example.onlinenewsmobile.services;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class NewsService {
    public ArrayList<Object> read24h(Document document) {
        Elements section = document.getElementsByTag("section");

        ArrayList<Object> content = new ArrayList<>();

        //header
        Element header = section.get(1).child(0).child(0);
        content.add("<hea>" + eliminateDoubleQuote(header.text()));
        //time
        Element time = header.nextElementSibling();
        content.add("<tim>" + eliminateDoubleQuote(time.text()));
        //Description
        Element element = time.nextElementSibling().nextElementSibling();
        content.add("<des>" + eliminateDoubleQuote(element.text()));

        //content
        element = element.nextElementSibling().nextElementSibling();
        Elements eles;
        do {
            if (element.attr("id").endsWith("zone_banner_sponser_product")) break;

            element = element.nextElementSibling();
            if ((eles = element.getElementsByTag("strong")).size() != 0) {
                content.add("<str>" + eliminateDoubleQuote(eles.text()));
            } else {
                if ((eles = element.getElementsByTag("img")).size() != 0) {
                    String src = eles.attr("src");
                    if (!src.startsWith("http")) {
                        src = eles.attr("data-original");
                    }
                        content.add(HttpRequestService.getImageBitmap(src));

                } else {
                    content.add(eliminateDoubleQuote(element.text()));
                }
            }
        } while (true);

        //Author
        Element author = section.get(1).nextElementSibling().nextElementSibling().child(1);
        content.add(eliminateDoubleQuote(author.text()));

        return content;
    }

    private String eliminateDoubleQuote(String s) {
        if (s.startsWith("\"")) {
            s.substring(1);
        }
        if (s.endsWith("\"")) {
            s.substring(0, s.length() - 1);
        }

        return s;
    }
}
