package com.example.onlinenewsmobile.services;

import android.os.AsyncTask;

import com.example.onlinenewsmobile.adapters.NewsCustomArrayAdapter;
import com.example.onlinenewsmobile.models.CategoryDTO;
import com.example.onlinenewsmobile.models.NewsDTO;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

public class RssService {
    private CategoryDTO categoryDTO;

    public void addNews(NewsCustomArrayAdapter newsCustomArrayAdapter, CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
        new RssReader(newsCustomArrayAdapter).execute(categoryDTO.getRssLink(), categoryDTO.getNewspaper(), categoryDTO.getName());
    }

    private class RssReader extends AsyncTask<String, Void, ArrayList<NewsDTO>> {

        private NewsCustomArrayAdapter adapter;

        public RssReader(NewsCustomArrayAdapter newsCustomArrayAdapter) {
            this.adapter = newsCustomArrayAdapter;
        }

        @Override
        protected ArrayList<NewsDTO> doInBackground(String... strings) {
            try {
                switch (strings[1]) {
                    case "24h":
                        return getNewsItemFrom24h(strings[0], strings[1]);
                    case "Dân Trí":
                        return getNewsItemFromDanTri(strings[0], strings[1], strings[2]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsDTO> list) {
            adapter.addAll(list);
        }
    }

    private ArrayList<NewsDTO> getNewsItemFrom24h(String rssLink, String newspaper) throws Exception{
        ArrayList<NewsDTO> list = new ArrayList<>();
        NewsDTO dto;

        Document document = DocumentService.parseHtml("https://" + rssLink);
        Elements elements = document.getElementsByTag("item");
        Element element;

        for (int i = 0; i < elements.size(); i++) {
            dto = new NewsDTO();
            element = elements.get(i).child(0);
            do {
                switch (element.tagName()) {
                    case "title":
                        dto.setTitle(eliminateDoubleQuote(element.text()));
                        break;
                    case "description":
                        String description = element.text();
                        if (description.indexOf("href=") > 0) {
                            int start = description.indexOf("src='");
                            int end;
                            if (start >= 0) {
                                start += 5;
                                end = description.indexOf(" ", start + 10) - 1;
                                dto.setImageLink(description.substring(start, end));
                            }
                            start = description.lastIndexOf("/>");
                            if (start > 0) {
                                start += 2;
                                dto.setDescription(description.substring(start).replace("&amp;#34;", "").replace("&nbsp;", ""));
                            }
                        } else {
                            dto.setDescription(eliminateDoubleQuote(description));
                            try {
                                dto.setImageLink(element.getElementsByTag("img").get(0).attr("src"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "link":
                        dto.setLink(element.text());
                        break;
                }
            } while ((element = element.nextElementSibling()) != null);
            dto.setNewspaper(newspaper);
            list.add(dto);
        }

        for (int i = 0; i < list.size(); i++) {
            try {
                list.get(i).setImageBitmap(HttpRequestService.getImageBitmap(list.get(i).getImageLink()));
            } catch (Exception e) {
                list.get(i).setImageBitmap(null);
                e.printStackTrace();
            }
        }

        return list;
    }

    private ArrayList<NewsDTO> getNewsItemFromDanTri(String rssLink, String newspaper, String category) throws Exception{
        ArrayList<NewsDTO> list = new ArrayList<>();
        NewsDTO dto;

        String rssContent = HttpRequestService.executeGet(new URL((rssLink)));
        rssContent = rssContent.substring(rssContent.indexOf("<link>") + 6, rssContent.indexOf("</link>"));

        Document document = DocumentService.parseHtml(rssContent);
        Elements elements;
        Element element;

        if (category.equals("Trang chủ")) {
            elements = document.getElementsByClass("noibattrangchu");
            element = elements.get(0).child(0);
            dto = new NewsDTO();
            dto.setTitle(eliminateDoubleQuote(element.attr("title")));
            dto.setLink("https://dantri.com.vn" + element.attr("href"));
            dto.setImageLink(element.child(0).attr("src"));
            element = element.nextElementSibling().nextElementSibling().nextElementSibling();
            dto.setDescription(eliminateDoubleQuote(element.ownText()));
            dto.setNewspaper(newspaper);
            list.add(dto);

            elements = document.getElementsByClass("wid330");
            for (int i = 0; i < elements.size(); i++) {
                dto = new NewsDTO();
                dto.setImageLink(elements.get(i).previousElementSibling().child(0).attr("src"));
                element = elements.get(i).child(0);
                dto.setLink("https://dantri.com.vn" + element.child(0).attr("href"));
                dto.setTitle(eliminateDoubleQuote(element.text()));
                dto.setDescription(eliminateDoubleQuote(element.nextElementSibling().ownText()));
                dto.setNewspaper(newspaper);
                list.add(dto);
            }
        } else {
            elements = document.getElementsByClass("mt3");
            for (int i = 0; i < elements.size(); i++) {
                dto = new NewsDTO();
                element = elements.get(i).child(0);
                dto.setLink("https://dantri.com.vn" + element.attr("href"));
                dto.setImageLink(element.child(0).attr("src"));
                dto.setTitle(eliminateDoubleQuote(element.child(0).attr("title")));
                dto.setDescription(eliminateDoubleQuote(element.nextElementSibling().child(0).nextElementSibling().ownText()));
                dto.setNewspaper(newspaper);
                list.add(dto);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            try {
                list.get(i).setImageBitmap(HttpRequestService.getImageBitmap(list.get(i).getImageLink()));
            } catch (Exception e) {
                list.get(i).setImageBitmap(null);
                e.printStackTrace();
            }
        }

        return list;
    }

    private String eliminateDoubleQuote(String s) {
        s = s.trim().replace("&amp;#34;", "").replace("&nbsp;", "")
                    .replace("&#34;", "").replace("#34;", "");
        if (s.startsWith("\"")) {
            s.substring(1);
        }
        if (s.endsWith("\"")) {
            s.substring(0, s.length() - 1);
        }

        return s;
    }
}
