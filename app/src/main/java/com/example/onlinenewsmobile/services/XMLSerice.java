package com.example.onlinenewsmobile.services;

import com.example.onlinenewsmobile.models.NewsDTO;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLSerice {

    public static ArrayList<NewsDTO> getNewsItem(String content) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(content));
            is.setEncoding("UTF-8");
            Document document = builder.parse(is);

            ArrayList<NewsDTO> list = new ArrayList<>();
            NewsDTO dto;

            NodeList items = document.getElementsByTagName("item");
            NodeList details;
            Node node;
            for (int i = 0; i < items.getLength(); i++) {
                dto = new NewsDTO();
                details = items.item(i).getChildNodes();

                for (int j = 0; j < details.getLength(); j++) {
                    node = details.item(j);

                    switch (node.getNodeName()){
                        case "title":
                            dto.setTitle(node.getTextContent());
                            break;
                        case "description":
                            String description = node.getTextContent();
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
                                dto.setDescription(description.substring(start).replace("&amp;#34;", ""));
                            }
                            break;
                        case "link":
                            dto.setLink(node.getTextContent());
                            break;
                    }
                }

                list.add(dto);
            }

            return list;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
