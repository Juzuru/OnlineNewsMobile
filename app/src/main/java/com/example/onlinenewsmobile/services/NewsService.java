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
        content.add("<aut>" + eliminateDoubleQuote(author.text()));
        content.add("<aut>Theo 24h.com.vn");

        return content;
    }

    public ArrayList<Object> readDanTri(Document document) {
        Elements section = document.getElementsByClass("ctl00_IDContent_Tin_Chi_Tiet");

        ArrayList<Object> content = new ArrayList<>();
        Element element = section.get(0).child(0).child(0);

        //Time
        content.add("<tim>" + eliminateDoubleQuote(element.child(1).ownText()));

        //Tttle
        element = element.nextElementSibling();
        content.add("<hea>" + eliminateDoubleQuote(element.ownText()));

        //Desctiption;
        element = element.nextElementSibling().nextElementSibling().nextElementSibling();
        content.add("<des>" + eliminateDoubleQuote(element.ownText()));

        //content
        element = element.nextElementSibling().nextElementSibling().child(0);
        do {
            if (element.getElementsByTag("strong").size() > 0) {
                content.add("<str>" + eliminateDoubleQuote(element.ownText()));
            } else {
                Elements eles = element.getElementsByTag("img");
                if (eles .size() > 0) {
                    content.add(HttpRequestService.getImageBitmap(eles.get(0).attr("src")));
                } else {
                    content.add(eliminateDoubleQuote(element.ownText()));
                }
            }
            element = element.nextElementSibling();
        } while (!element.attr("class").equals("news-tag"));
        content.add(content.size() - 1, "<aut>" + content.get(content.size() - 1));
        content.add("<aut>Theo Dân Trí");

        return content;
    }

    private String eliminateDoubleQuote(String s) {
        s = s.trim();
        if (s.startsWith("\"")) {
            s.substring(1);
        }
        if (s.endsWith("\"")) {
            s.substring(0, s.length() - 1);
        }

        return s;
    }


}
