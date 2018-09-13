package com.example.wry_springboot.Io;

import java.io.*;

public class readline {
    public static void main(String arg[]) throws IOException {
//        FileReader fileReader =new FileReader("/springFile/io/test");
//        BufferedReader bufferedReader =new BufferedReader(fileReader);
        //reader();
        writer();
    }
    public static void reader() throws IOException{
        File file =new File("/springFile/io/test");
        InputStream inputStream = new FileInputStream(file);
        byte b[] =new byte[(int)file.length()];
        int str =0;
        inputStream.read(b);
        inputStream.close();
        System.out.println(new String(b));

    }
    public static void writer() throws IOException {
        File file =new File("/springFile/io/test_one");
        OutputStream outputStream =new FileOutputStream(file);
        String test = "hello";
        outputStream.write(test.getBytes());
        outputStream.close();
    }
}
