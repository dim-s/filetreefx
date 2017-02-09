package sample.component;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

abstract public class AbstractDirectoryTreeSource {
    abstract public boolean isEmpty(String path);
    abstract DirectoryTreeValue createValue(String path);
    abstract List<DirectoryTreeValue> list(String path);

    abstract public DirectoryTreeListener listener(String path);

    abstract public void shutdown();
}
