package cz.zcu.luk.mwes.mains;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 14.11.13
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
public class PDTEncodingConverter {

    private static void changeEncoding(File oneFile, String dirOut) throws Exception {
        String outName = dirOut + oneFile.getName();
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outName), "UTF-8"));
        BufferedReader documentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile.getAbsolutePath()), "ISO-8859-2"));
        String nextLine;
        while((nextLine = documentsReader.readLine()) != null) {
            pw.println(nextLine);
        }

        documentsReader.close();
        pw.close();
    }


    public static void main(String[] args) throws Exception {
        //String inputDir = "C:\\tmttt\\data";
        String inputDir = "/storage/brno2/home/lkrcmar/MWE/pdt/iso";

        //String outputDir = "C:\\tmttt\\data\\utf8\\";
        String outputDir = "/storage/brno2/home/lkrcmar/MWE/pdt/utf8/";


        Collection files;
//        Collection files = FileUtils.listFiles(new File(inputDir), new String[]{"m"}, true);
//        for (Object oneFile : files) {
//            System.out.println(((File)oneFile).toString());
//        }
//
//        for (Object oneFile : files) {
//            String fileInString = parseFile((File)oneFile);
//            pw.println(fileInString);
//        }

        files = FileUtils.listFiles(new File(inputDir), new String[]{"csts"}, true);

        for (Object oneFile : files) {
            System.out.println(((File)oneFile).toString());
            changeEncoding((File) oneFile, outputDir);
        }

    }
}
