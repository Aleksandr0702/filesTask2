import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress game1 = new GameProgress(100, 3, 5, 152.5);
        GameProgress game2 = new GameProgress(80, 2, 3, 100.0);
        GameProgress game3 = new GameProgress(50, 1, 1, 50.0);


        saveGame("C:\\Games\\savegames/save1.dat", game1);
        saveGame("C:\\Games\\savegames/save2.dat", game2);
        saveGame("C:\\Games\\savegames/save3.dat", game3);


        zipFiles("C:\\Games\\savegames/zip.zip",
                new String[]{
                        "C:\\Games\\savegames/save1.dat",
                        "C:\\Games\\savegames/save2.dat",
                        "C:\\Games\\savegames/save3.dat"
                });


        deleteNonZippedFiles("C:\\Games\\savegames");
    }
    public static void saveGame(String filePath, GameProgress game) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zipFilePath, String[] filesToZip) {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String file : filesToZip) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(new File(file).getName());
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteNonZippedFiles(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.getName().endsWith(".zip")) {
                    file.delete();
                }
            }
        }
    }
}