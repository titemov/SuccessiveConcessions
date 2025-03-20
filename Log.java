import java.io.RandomAccessFile;
import java.util.Date;

public class Log {
    public static void initialEntry(){
        try{
            RandomAccessFile raf = new RandomAccessFile("log.txt","rw");
            Date date = new Date();
            String s = date.toString();
            int len = (int) raf.length();
            raf.seek(len);
            raf.write(("\n\n----- "+s+" -----").getBytes());
            raf.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void writeLog(String s, boolean newLine) {
        try{
            RandomAccessFile raf = new RandomAccessFile("log.txt","rw");
            int len = (int) raf.length();
            raf.seek(len);
            if (newLine) {
                raf.write(("\r\n" + s).getBytes());
            }else{
                raf.write((s).getBytes());
            }

            raf.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static char[] getText(){
        try{
            RandomAccessFile raf = new RandomAccessFile("log.txt","r");
            raf.seek(0);
            char[] text= new char[(int) raf.length()];
            for(int i=0;i<text.length;i++){
                text[i]=raf.readChar();
            }
            raf.close();
            return text;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
