package com.acc.group4.Navigators.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;


public class Crawler {
        private static Crawler NavigatorsInstance_1 = null;
        private WebCrwaler webCrawler = WebCrwaler.getInstance();
        public static Crawler getInstance() {
                NavigatorsInstance_1 = NavigatorsInstance_1 == null ? new Crawler(10) : NavigatorsInstance_1;
                return NavigatorsInstance_1;
        }
         private final Set<String> Navigators_visitedUrls = new HashSet<>();
            private final int navigators_mUrls;


            public Crawler(int maxUrls) {
                this.navigators_mUrls = maxUrls;
            }


            public void crawlWebsite(String NAV_url, String navigators_opDir) throws IOException {
                Path Nav_d1 = Paths.get(navigators_opDir);
                if (!Files.exists(Nav_d1)) {
                    Files.createDirectories(Nav_d1);
                    System.out.println("Directory created: " + Nav_d1);
                } else {
                    System.out.println("Directory already exists: " + Nav_d1);
                }


                navigators_dfsCrawl(NAV_url, 0, navigators_opDir);
                System.out.println("\n[*] Total Crawled URLs: " + Navigators_visitedUrls.size());
            }


            private void navigators_dfsCrawl(String navigators_url, int navigators_depth, String navigators_outputDirectory) throws IOException {
                if (navigators_depth >= navigators_mUrls || Navigators_visitedUrls.contains(navigators_url)) {
                    return;
                }


                Navigators_visitedUrls.add(navigators_url);
                System.out.println("\n[*] Crawling: " + navigators_url);


                Document document;
                try {
                    document = Jsoup.connect(navigators_url).get();
                } catch (IOException e) {
                    System.err.println("Error connecting to " + navigators_url + ": " + e.getMessage());
                    return;  // Skip to the next URL if an error occurs
                }


                Elements navigators_links = document.select("a[href]");


                String navigators_fileName = URLEncoder.encode(navigators_url, StandardCharsets.UTF_8.toString()) + ".txt";
                String navigators_filePath = navigators_outputDirectory + "/" + navigators_fileName;


                try (BufferedWriter navigators_writer = new BufferedWriter(new FileWriter(navigators_filePath))) {
                        navigators_writer.write(document.text());
                    System.out.println("Content saved to: " + navigators_filePath);
                } catch (IOException e) {
                    System.err.println("Error writing content to " + navigators_filePath + ": " + e.getMessage());
                }


                for (Element link : navigators_links) {
                    String navigators_nextUrl = link.absUrl("href");
                    navigators_dfsCrawl(navigators_nextUrl, navigators_depth + 1, navigators_outputDirectory);


                    // Check if the maximum limit has been reached after crawling each link
                    if (Navigators_visitedUrls.size() >= navigators_mUrls) {
                        return;
                    }
                }
            }


}