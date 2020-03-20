package test.exceprions;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Main {
    /*
    * Реализуйте метод readContent(String url), который отображает на экран
    * содержимое сайта, ссылка на который задаётся параметром url.
    * Напишите программу, считывающую из консоли строку (URL ресурса) и вызывающую
    * метод readContent. В том случае, если введённый URL неправильного формата
    * или нет доступа до указанного ресурса, пользователю предлагается повторить ввод.
    */
    public static void readContent(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        try(Scanner scan =new Scanner(url.openStream())) {
            while (scan.hasNextLine()) {
                System.out.println("     "+scan.nextLine());
            }
        }
    }
    public static void main(String[] args)  {
        String str="";
        Scanner scan = new Scanner(System.in))
        while(true) {
            try {
                System.out.print(" Enter the URL (or enter EXIT if you want exit): ");
                str = scan.nextLine();
                if (str.equalsIgnoreCase("exit")) {
                    break;
                }
                readContent(str);
            }
            catch(MalformedURLException e){
                System.out.println("URL is not correct.\n" +"Please enter URL again");
            }
            catch(IOException e){
                System.out.println("Reading content from "+str+"failed"+"Please enter other URL.");
            }
        }
    }
}
