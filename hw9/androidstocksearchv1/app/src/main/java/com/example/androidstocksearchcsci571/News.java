package com.example.androidstocksearchcsci571;

/*
    Sample:
    {
        "source": {
            "id": "the-next-web",
            "name": "The Next Web"
        },
        "author": "David Canellis",
        "title": "Xilinx stock goes up, AMD stock goes down after $35B buyout",
        "description": "Advanced Micro Devices (AMD) has officially acquired San Jose chipmaker Xilinx for a moustache-twirling $35 billion. The deal is this year’s second largest tech disclosed acquisition, behind NVIDIA’s $40 billion buyout of mobile phone chipmaker Arm in Septemb…",
        "url": "https://thenextweb.com/hardfork/2020/10/27/amd-xilinx-stock-buyout-35-billion-chipmaker-semiconductor-acquisition/",
        "urlToImage": "https://img-cdn.tnwcdn.com/image/hardfork?filter_last=1&fit=1280%2C640&url=https%3A%2F%2Fcdn0.tnwcdn.com%2Fwp-content%2Fblogs.dir%2F1%2Ffiles%2F2020%2F07%2Fintelamd.jpg&signature=76490c0534aae03896fe9f2dc8491cb3",
        "publishedAt": "2020-10-27T17:27:41Z",
        "content": "Advanced Micro Devices (AMD) has officially acquired San Jose chipmaker Xilinx for a moustache-twirling $35 billion.\r\nThe deal is this years second largest tech disclosed acquisition, behind NVIDIAs … [+1944 chars]"
    }
*/
public class News {
    private String sourceId, sourceName, author, title, description, url, urlToImage, publishedAt, content;

    public News(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "News{" +
                "sourceId='" + sourceId + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
