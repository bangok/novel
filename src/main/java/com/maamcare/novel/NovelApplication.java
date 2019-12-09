package com.maamcare.novel;


public class NovelApplication {

    public static String baseUrl = "https://www.biquguan.com/bqg403614/";
    public static String firstChapterUrl = "2274516.html";
    public static String fileUrl = "C:\\少女骑士零.txt";




    public static void main(String[] args) {
        System.out.println("爬虫开始");
        while (true){
            String nextChapterUrl = NovelUtil.getOneChapter(baseUrl,fileUrl,firstChapterUrl);
            if(nextChapterUrl.equals("./")){
                System.out.println("爬虫结束");
                break;
            }
            firstChapterUrl = nextChapterUrl;
        }
    }

}
