package com.maamcare.novel;


import lombok.AllArgsConstructor;
import lombok.Data;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Data
public class NovelUtil {
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符


    /**
     * 爬取一章的内容，并且写入到文件最后
     * url：网页地址
     * */
    public static String getOneChapter(String baseUrl,String fileUrl,String chapterUrl){
        String url = baseUrl + chapterUrl;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);

        String body = null;
        try {
            //获取数据
            Response response = call.execute();
            body = new String(response.body().bytes());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        //格式化数据
        Document doc = Jsoup.parse(body);
        //获取下一章url
        String nextUrl = getNextChapterUrl(doc);
        //爬取章节标题
        Elements titleElement =doc.getElementsByClass("bookname");
        String title = titleElement.select("h1").html();
        //开始爬取内容
        Element content =doc.getElementById("content");
        String str = content.html();
        //去除script标签所有内容
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(str);
        str = m_script.replaceAll("");
        //去除所有的br标签
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(str);
        str=m_html.replaceAll(""); //过滤html标签
        //剔除nbsp
        str = str.replaceAll("&nbsp;","");
        //拼装完整内容
        str = title + "\n" +str+"\n\n";
        //写入文件
        FileOperater fileOperater = new FileOperater();
        fileOperater.writefileinfo(str,fileUrl);
        //返回下一章url
        System.out.println("正在爬取内容，当前章节："+title);
        return nextUrl;
    }

    /**
     * 返回下一章的url
     * */
    public static String getNextChapterUrl(Document doc){
        String nextUrl = doc.getElementById("A3").attr("href");
        if(nextUrl.equals("")||nextUrl==null){
            throw new RuntimeException("over");
        }
        return nextUrl;
    }

}
