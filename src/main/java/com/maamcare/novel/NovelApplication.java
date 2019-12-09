package com.maamcare.novel;


import java.util.Scanner;

public class NovelApplication {

    public static String baseUrl = "https://www.biquguan.com/bqg403614/";
    public static String firstChapterUrl = "2274516.html";
    public static String fileUrl = "C:\\少女骑士零.txt";




    public static void main(String[] args) {
        //提示输入

        Scanner scanner = new Scanner(System.in);
        System.out.println("爬取网站地址为：https://www.biquguan.com/");
        System.out.println("请输入要爬取小说的头地址，示例：https://www.biquguan.com/bqg403614/");
        String myBaseUrl = scanner.nextLine();
        baseUrl = myBaseUrl;
        System.out.println("请输入要爬取小说的第一张地址，示例：2274516.html");
        String myFirstChapterUrl = scanner.nextLine();
        firstChapterUrl = myFirstChapterUrl;
        System.out.println("请输入要存到本机的文件地址，示例：C:\\少女骑士零.txt");
        String myFileUrl = scanner.nextLine();
        fileUrl = myFileUrl;

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
