package com.example.onlinenewsmobile.services;

import android.os.AsyncTask;

import com.example.onlinenewsmobile.adapters.NewsCustomArrayAdapter;
import com.example.onlinenewsmobile.models.CategoryDTO;
import com.example.onlinenewsmobile.models.NewsDTO;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class RssService {
    private CategoryDTO categoryDTO;

    public void addNews(NewsCustomArrayAdapter newsCustomArrayAdapter, CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
        new RssReader(newsCustomArrayAdapter).execute(categoryDTO.getRssLink());
    }

    private class RssReader extends AsyncTask<String, Void, Void> {

        private NewsCustomArrayAdapter adapter;
        private ArrayList<NewsDTO> list;

        public RssReader(NewsCustomArrayAdapter newsCustomArrayAdapter) {
            this.adapter = newsCustomArrayAdapter;
            this.list = list;
        }

        @Override
        protected Void doInBackground(String... strings) {
            //String content = HttpRequestService.getContent(strings[0]);
            String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<rss version=\"2.0\">" +
                    "<channel>" +
                    "<title>Du lịch - 24H RSS</title>" +
                    "<description>Du lich - Hình ảnh các địa điểm du lịch giá rẻ nổi tiếng Việt Nam &amp; thế giới. Tư vấn các tour du lịch khám phá cảnh đẹp, những món ngon… cực hấp dẫn.</description>" +
                    "<image><url>https://static.24h.com.vn/images/logo24h-128x85.gif</url>" +
                    "<title>Tin tuc 24h | tin nhanh bong da | the thao | thoi trang, giai tri vn | báo online</title>" +
                    "<link> https://www.24h.com.vn </link></image>" +
                    " <pubDate>Tue, 15 Oct 2019 19:14:02 +0700</pubDate>" +
                    "<language>vi-vn</language>" +
                    "<generator>Tin tức 24h</generator>" +
                    "<copyright>(c)24h.com.vn</copyright>" +
                    "<ttl>5</ttl>" +
                    "<item>" +
                    "<title>Những điểm đến khiến du khách lạnh sống lưng</title>" +
                    "<description>" +
                    "<![CDATA[" +
                    " <a href='https://www.24h.com.vn/du-lich-24h/nhung-diem-den-khien-du-khach-lanh-song-lung-c76a1091536.html' title='Những điểm đến khiến du khách lạnh sống lưng'><img width='130' height='100' src='https://image.24h.com.vn/upload/4-2019/images/2019-10-15/1571114663-210-thumbnail.jpg' alt='Những điểm đến khiến du khách lạnh sống lưng' title='Những điểm đến khiến du khách lạnh sống lưng' /></a><br />Mặc dù những điểm đến này ẩn chứa bao điều bí mật, những câu chuyện được thêu dệt về ma quỷ cùng nhiều truyền thuyết đáng sợ khác, nhưng hiện chúng đang trở thành những khu du lịch thu hút nhiều du khách ưa mạo hiểm và thích khám phá những điều rùng rợn, bí ẩn." +
                    "]]>" +
                    "</description>" +
                    "<pubDate>Tue, 15 Oct 2019 19:00:00 +0700</pubDate>" +
                    "<link>https://www.24h.com.vn/du-lich-24h/nhung-diem-den-khien-du-khach-lanh-song-lung-c76a1091536.html</link>" +
                    "<guid>https://www.24h.com.vn/du-lich-24h/nhung-diem-den-khien-du-khach-lanh-song-lung-c76a1091536.html</guid>" +
                    "</item>" +
                    "<item>" +
                    "<title>Mách bạn kinh nghiệm du lịch bụi Campuchia tự túc 3 ngày 4 đêm</title>" +
                    "<description>" +
                    "<![CDATA[" +
                    " <a href='https://www.24h.com.vn/du-lich-24h/mach-ban-kinh-nghiem-du-lich-bui-campuchia-tu-tuc-3-ngay-4-dem-c76a1091509.html' title='Mách bạn kinh nghiệm du lịch bụi Campuchia tự túc 3 ngày 4 đêm'><img width='130' height='100' src='https://image.24h.com.vn/upload/4-2019/images/2019-10-15/1571112308-265-thumbnail.jpg' alt='Mách bạn kinh nghiệm du lịch bụi Campuchia tự túc 3 ngày 4 đêm' title='Mách bạn kinh nghiệm du lịch bụi Campuchia tự túc 3 ngày 4 đêm' /></a><br />&amp;#34;Bọn mình chọn đi bụi như vậy để tự trải nghiệm và gặp được nhiều người thú vị hơn. Quan trọng là tinh thần đi đây đi đó. Qua những kinh nghiệm của tụi mình. Mong rằng các bạn có được chuyến đi bụi đến Campuchia tuyệt vời nhất có thể&amp;#34;, Trọng Nhân chia sẻ." +
                    "]]>" +
                    "</description>" +
                    "<pubDate>Tue, 15 Oct 2019 12:00:00 +0700</pubDate>" +
                    "<link>https://www.24h.com.vn/du-lich-24h/mach-ban-kinh-nghiem-du-lich-bui-campuchia-tu-tuc-3-ngay-4-dem-c76a1091509.html</link>" +
                    "<guid>https://www.24h.com.vn/du-lich-24h/mach-ban-kinh-nghiem-du-lich-bui-campuchia-tu-tuc-3-ngay-4-dem-c76a1091509.html</guid>" +
                    "</item>" +
                    "<item>" +
                    "<title>Đẹp mê hồn với dãy núi cầu vồng tại Cam Túc, Trung Quốc</title>" +
                    "<description>" +
                    "<![CDATA[" +
                    " <a href='https://www.24h.com.vn/du-lich-24h/dep-me-hon-voi-day-nui-cau-vong-tai-cam-tuc-trung-quoc-c76a1091475.html' title='Đẹp mê hồn với dãy núi cầu vồng tại Cam Túc, Trung Quốc'><img width='130' height='100' src='https://image.24h.com.vn/upload/4-2019/images/2019-10-15//1571107023-72f2165595c167788438a37e55a8063e.jpg' alt='Đẹp mê hồn với dãy núi cầu vồng tại Cam Túc, Trung Quốc' title='Đẹp mê hồn với dãy núi cầu vồng tại Cam Túc, Trung Quốc' /></a><br />Nói tới du lịch Trung Quốc, nhiều người thường nghĩ tới Thiên Môn Sơn - Trương Gia Giới, Cửu Trại Câu, Phượng Hoàng cổ trấn... Tuy nhiên, với những người ưa mạo hiểm, thích phong lưu và bụi bặm thì những địa danh như Đôn Hoàng hay Công viên Trương Dịch Đan Hà là lựa chọn không tồi." +
                    "]]>" +
                    "</description>" +
                    "<pubDate>Tue, 15 Oct 2019 10:00:00 +0700</pubDate>" +
                    "<link>https://www.24h.com.vn/du-lich-24h/dep-me-hon-voi-day-nui-cau-vong-tai-cam-tuc-trung-quoc-c76a1091475.html</link>" +
                    "<guid>https://www.24h.com.vn/du-lich-24h/dep-me-hon-voi-day-nui-cau-vong-tai-cam-tuc-trung-quoc-c76a1091475.html</guid>" +
                    "</item>" +
                    "<link>https://www.24h.com.vn/upload/rss/dulich24h.rss</link>" +
                    "</channel>" +
                    "</rss>";
            Document document = DocumentService.parseXml(content);

            list = new ArrayList<>();
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

                dto.setCategoryName(categoryDTO.getName());
                dto.setNewspaper(categoryDTO.getNewspaper());

                list.add(dto);
            }

            for (int i = 0; i < list.size(); i++) {
                //list.get(i).setImageBitmap(HttpRequestService.getImageBitmap(list.get(i).getImageLink()));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.addAll(list);
        }
    }
}
