import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        ArrayList<GameProgress> progresses = new ArrayList<>();
        ArrayList<String> linkProgress = new ArrayList<>();
        progresses.add(new GameProgress(100, 2, 18, 563.1));
        progresses.add(new GameProgress(87, 3, 25, 613.5));
        progresses.add(new GameProgress(45, 8, 29, 754.2));
        for (int i = 0; i < progresses.size(); i++) {
            String link = "/D:/Games/savegames" + (i + 1) + ".dat";
            saveGame(progresses.get(i), link);
            linkProgress.add(link);
        }
        zipFiles(linkProgress, "D:/Games/savegames/zip.zip");
    }

    public static void saveGame(GameProgress progress, String path) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }

    public static void zipFiles(ArrayList<String> path, String zipPath) {
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));
            for (String s : path) {
                File file = new File(s);
                FileInputStream fis = new FileInputStream(s);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);
                byte[] paragraph = new byte[fis.available()];
                fis.read(paragraph);
                zos.write(paragraph);
                zos.closeEntry();
                System.out.println("Файл " + s + " архивирован");
                fis.close();
            }
            zos.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        for (String s : path) {
            File file = new File(s);
            if (file.delete()) {
                System.out.println("Файл " + file.getName() + " удален");
            }
        }
    }
}


