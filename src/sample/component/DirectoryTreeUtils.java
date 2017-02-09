package sample.component;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DirectoryTreeUtils {
    private final static Map<String, Image> fileIcons = new HashMap<>();

    private final static Image folderIcon = makeIcon("folder.png");
    private final static Image folderOpenIcon = makeIcon("folder-open.png");

    private final static Image fileAnyIcon = makeIcon("file.png");
    private final static Image fileImageIcon = makeIcon("image-file.png");
    private final static Image fileAudioIcon = makeIcon("audio-file.png");
    private final static Image fileVideoIcon = makeIcon("video-file.png");
    private final static Image fileTextIcon = makeIcon("text-file.png");
    private final static Image fileSourceIcon = makeIcon("source-file.png");
    private final static Image fileArchiveIcon = makeIcon("archive-file.png");
    private final static Image fileExecutableIcon = makeIcon("executable-file.png");

    static {
        // images
        fileIcons.put("jpg", fileImageIcon);
        fileIcons.put("jpeg", fileImageIcon);
        fileIcons.put("png", fileImageIcon);
        fileIcons.put("gif", fileImageIcon);
        fileIcons.put("ico", fileImageIcon);
        fileIcons.put("bmp", fileImageIcon);
        fileIcons.put("tiff", fileImageIcon);
        fileIcons.put("tif", fileImageIcon);

        // audio
        fileIcons.put("mp3", fileAudioIcon);
        fileIcons.put("wav", fileAudioIcon);
        fileIcons.put("wave", fileAudioIcon);
        fileIcons.put("aif", fileAudioIcon);
        fileIcons.put("aiff", fileAudioIcon);
        fileIcons.put("ogg", fileAudioIcon);

        // video
        fileIcons.put("mp4", fileVideoIcon);
        fileIcons.put("avi", fileVideoIcon);
        fileIcons.put("flv", fileVideoIcon);
        fileIcons.put("3gp", fileVideoIcon);
        fileIcons.put("webm", fileVideoIcon);

        // text
        fileIcons.put("txt", fileTextIcon);
        fileIcons.put("log", fileTextIcon);
        fileIcons.put("conf", fileTextIcon);
        fileIcons.put("ini", fileTextIcon);
        fileIcons.put("svg", fileTextIcon);
        fileIcons.put("xml", fileTextIcon);
        fileIcons.put("json", fileTextIcon);
        fileIcons.put("fxml", fileTextIcon);

        // source
        fileIcons.put("php", makeIcon("php-file.png"));
        fileIcons.put("java", fileSourceIcon);
        fileIcons.put("js", fileSourceIcon);
        fileIcons.put("html", fileSourceIcon);
        fileIcons.put("htm", fileSourceIcon);
        fileIcons.put("css", fileSourceIcon);

        // archive
        fileIcons.put("zip", fileArchiveIcon);
        fileIcons.put("jar", fileArchiveIcon);
        fileIcons.put("rar", fileArchiveIcon);
        fileIcons.put("7z", fileArchiveIcon);
        fileIcons.put("tar", fileArchiveIcon);
        fileIcons.put("gz", fileArchiveIcon);
        fileIcons.put("bz2", fileArchiveIcon);
        fileIcons.put("war", fileArchiveIcon);
        fileIcons.put("iso", fileArchiveIcon);

        // executable
        fileIcons.put("exe", fileExecutableIcon);
        fileIcons.put("appx", fileExecutableIcon);
        fileIcons.put("sh", fileExecutableIcon);
        fileIcons.put("bat", fileExecutableIcon);
    }

    public static Node getIconOfFile(File file, boolean expanded) {
        if (file.isDirectory()) {
            return new ImageView(!expanded ? folderIcon : folderOpenIcon);
        }

        if (expanded) {
            return null;
        }

        String name = file.getName();

        int index = name.lastIndexOf('.');

        if (index == -1) {
            return new ImageView(fileAnyIcon);
        }

        String ext = name.substring(index + 1).toLowerCase();

        if (fileIcons.containsKey(ext)) {
            return new ImageView(fileIcons.get(ext));
        }

        return new ImageView(fileAnyIcon);
    }

    private static Image makeIcon(String fileName) {
        return new Image("/sample/component/img/" + fileName);
    }
}
