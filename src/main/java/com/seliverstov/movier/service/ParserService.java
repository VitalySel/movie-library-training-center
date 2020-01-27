package com.seliverstov.movier.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParserService {

    public ParserService() {
    }

    public static String parsePage(int id) throws IOException {
        String res = "";
        Document document = Jsoup.connect("https://ru.kinorium.com/"+id+"/").get();
        res += (document.getElementsByClass("film-page__title-text film-page__itemprop ").text()+"|");
        Element table = document.select("table").first();
        Elements rows = table.select("tr");
        int counter = 0;
        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            if(cols.get(0).text().equals("год") || cols.get(0).text().equals("бюджет") || cols.get(0).text().equals("длительность")) {
                counter++;
                res += (cols.get(1).text() + "|");
            }
        }
        for (int i = counter; i < 3; i++) {
            return "null";
        }
        try {
            res += document.getElementsByAttributeValueContaining("href","/movies/premier/?order=premier_dat").first().text()+"|";
        }
        catch (Exception e){
            return "null";
        }
        res += document.getElementsByClass("movie_poster__wrapper ").select("img").attr("src") + "|";
        Elements elements = document.getElementsByAttributeValueContaining("href","/R2D2/?genres");
        for (int i = 0; i < elements.size(); i++) {
            res += (elements.get(i).text()+",");
        }
        res += "|";
        res += (document.getElementsByAttributeValue("itemprop","description").first().text())+"|";


        Elements part = document.getElementsByClass("gallery slider-container-film actor-slider film-page__cast");

        elements = document.getElementsByAttributeValue("itemprop","actor");
        for (int i = 0; i < elements.size(); i++) {
            if(!elements.get(i).attr("itemprop").isEmpty()) {
                res += (elements.get(i).select("a").attr("href")+",");
            }
        }
        res += ("|");
        res += (document.getElementsByAttributeValue("itemprop","director").first().text()) + "|";
        res += (document.getElementsByAttributeValue("itemprop","director").select("a").attr("href"));

        return res;
    }

    public static String parsDirector(String link) throws IOException {
        String res = "";
        Document document = Jsoup.connect("https://ru.kinorium.com/"+link).get();
        res += (document.getElementsByClass("person-page__title ").text()+"|");
        try {
            res += (document.getElementsByAttributeValue("itemprop", "birthDate").first().text()) + "|";
        }
        catch (NullPointerException e){
            res += "N/A|";
        }

        Element table = document.select("table").first();
        Elements rows = table.select("tr");
        boolean havePlace = false;
        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            if(cols.get(0).text().equals("место рождения")) {
                havePlace = true;
                res += (cols.get(1).text() + "|");
            }
        }
        if(!havePlace) {
            res+= "N/A|";
        }
        res += document.getElementsByClass("main_poster person_portrait ").select("img").attr("src") + "|";

        return res;
    }
}
