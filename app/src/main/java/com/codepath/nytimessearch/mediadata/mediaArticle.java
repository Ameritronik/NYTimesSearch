package com.codepath.nytimessearch.mediadata;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hkanekal on 3/17/2017.
 */

public class mediaArticle {

    public final static String IMAGE = "image";
    public final static String NYT_URL = "http://www.nytimes.com/";

    @SerializedName("web_url")
    @Expose
    public String webUrl;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("lead_paragraph")
    @Expose
    private String leadParagraph;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("print_page")
    @Expose
    private String printPage;
    @SerializedName("blog")
    @Expose
    private List<Object> blog = new ArrayList<Object>();
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("multimedia")
    @Expose
    private List<multiMedia> multimedia = new ArrayList<multiMedia>();
    @SerializedName("headline")
    @Expose
    public mediaHeadline headline;
    @SerializedName("keywords")
    @Expose
    private List<mediaKeywords> keywords = new ArrayList<mediaKeywords>();
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("news_desk")
    @Expose
    private String newsDesk;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("subsection_name")
    @Expose
    private String subsectionName;
    @SerializedName("byline")
    @Expose
    private mediaByline byline;
    @SerializedName("type_of_material")
    @Expose
    private String typeOfMaterial;
    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("word_count")
    @Expose
    private String wordCount;
    @SerializedName("slideshow_credits")
    @Expose
    private Object slideshowCredits;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }


    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public void setLeadParagraph(String leadParagraph) {
        this.leadParagraph = leadParagraph;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPrintPage() {
        return printPage;
    }

    public void setPrintPage(String printPage) {
        this.printPage = printPage;
    }

    public List<Object> getBlog() {
        return blog;
    }

    public void setBlog(List<Object> blog) {
        this.blog = blog;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<multiMedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<multiMedia> multiMedia) {
        this.multimedia = multiMedia;
    }

    public mediaHeadline getHeadline() {
        return headline;
    }

    public void setHeadline(mediaHeadline headline) {
        this.headline = headline;
    }

    public List<mediaKeywords> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<mediaKeywords> keywords) {
        this.keywords = keywords;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSubsectionName() {
        return subsectionName;
    }

    public void setSubsectionName(String subsectionName) {
        this.subsectionName = subsectionName;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public Object getSlideshowCredits() {
        return slideshowCredits;
    }

    public void setSlideshowCredits(Object slideshowCredits) {
        this.slideshowCredits = slideshowCredits;
    }

    public String getArticleImageUrl() {
        String imageUrl = "";

        for (multiMedia m : this.getMultimedia()) {
            if (m.getType().equals(IMAGE) && m.getSubtype().equals("xlarge")) {
                Log.d("DEBUG"," Got to URL");
                imageUrl = NYT_URL + m.getUrl();
                Log.d("DEBUG"," Got imageURL "+imageUrl);
                break;
            }
        }
        return imageUrl;
    }

}
