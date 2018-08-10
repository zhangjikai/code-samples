package com.zhangjikai.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * NIO2 基本文件操作
 * Created by zhangjk on 2016/1/17.
 */
public class NioOp {

    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-DD hh:MM:ss");

    /**
     * 检查文件是否存在
     */
    public static void checkFileExists() {
        Path path = Paths.get("E:\\program_new\\java\\jdk1.7\\include");
        if (Files.exists(path)) {
            System.out.println("file exists");
        }

        if (Files.notExists(path)) {
            System.out.println("file not exists");
        }

        // 不能访问
        if (!Files.exists(path) && !Files.notExists(path)) {
            System.out.println("file can't access");
        }
    }

    /**
     * 获得文件属性
     */
    public static void getFileAttributes() {
        Path path = Paths.get("F:\\test");
        BasicFileAttributes basicAttr = null;
        DosFileAttributes dosAttr = null;
        PosixFileAttributes posixAttr = null;
        try {
            basicAttr = Files.readAttributes(path, BasicFileAttributes.class);

            // 文件创建时间 attr.creationTime() 类型为 FileTime， 无法直接格式化， 需要用下面的方式
            System.out.println("creationTime: " + sFormat.format(basicAttr.creationTime().toMillis()));

            // 文件最后访问时间
            System.out.println("lastAccessTime: " + sFormat.format(basicAttr.lastAccessTime().toMillis()));

            // 文件最后修改时间
            System.out.println("lastModifiedTime: " + sFormat.format(basicAttr.lastModifiedTime().toMillis()));

            // 是否为文件夹
            System.out.println("isDirectory: " + basicAttr.isDirectory());
            System.out.println("isOther: " + basicAttr.isOther());

            // 是否为常规文件
            System.out.println("isRegularFile: " + basicAttr.isRegularFile());

            // 是否为链接
            System.out.println("isSymbolicLink: " + basicAttr.isSymbolicLink());

            // 文件大小
            System.out.println("size: " + basicAttr.size());


            dosAttr =  Files.readAttributes(path, DosFileAttributes.class);

            // 是否只读
            System.out.println("isReadOnly is " + dosAttr.isReadOnly());

            // 是否隐藏
            System.out.println("isHidden is " + dosAttr.isHidden());

            // 是否压缩文件
            System.out.println("isArchive is " + dosAttr.isArchive());

            // 是否系统文件
            System.out.println("isSystem is " + dosAttr.isSystem());


            System.out.println("isWriteAble is " + Files.isWritable(path));
            System.out.println("isExecutable is " + Files.isExecutable(path));
            // windows 下不支持
            /*posixAttr =
                    Files.readAttributes(path, PosixFileAttributes.class);
            System.out.format("%s %s %s%n",
                    posixAttr.owner().getName(),
                    posixAttr.group().getName(),
                    PosixFilePermissions.toString(posixAttr.permissions()));*/



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 遍历文件夹
    public static void traversalDirectory() {
        Path path = Paths.get("F:\\test\\");

        final List<File> files = new ArrayList<File>();
        SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files.add(file.toFile());
                return super.visitFile(file, attrs);
            }
        };

        try {
            Files.walkFileTree(path, finder);
        } catch (IOException e) {
            e.printStackTrace();
        }

            /*for(File file : files) {
                System.out.println(figetName());
            }*/


        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                System.out.println(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static final long K = 1024 * 1024;

    /**
     *  打印文件夹内存占用
     * @param store
     * @throws IOException
     */
    static void printFileStore(FileStore store) throws IOException {
        long total = store.getTotalSpace() / K;
        long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / K;
        long avail = store.getUsableSpace() / K;

        String s = store.toString();
        if (s.length() > 20) {
            System.out.println(s);
            s = "";
        }
        System.out.format("%-20s %12d %12d %12d\n", s, total, used, avail);
    }

    /**
     * 重命名文件
     */
    static void renameFile() throws IOException {
        Path path = Paths.get("F:\\pp\\python-2.7.11.amd64.msi");
        //Path newPath = Paths.get("FileTransfer.imlt");

        Files.move(path, path.resolveSibling("README.md"), StandardCopyOption.REPLACE_EXISTING);
        System.out.println(111);

    }

    // 创建文件夹 : Files.createDirectories(sourcePath);
    // 删除文件: Files.delete(sourcePath);

    public static void main(String[] args) throws IOException {
        renameFile();
    }
}
