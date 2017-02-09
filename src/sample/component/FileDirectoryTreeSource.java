package sample.component;

import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FileDirectoryTreeSource extends AbstractDirectoryTreeSource {
    private final File directory;
    private Map<String, DirectoryTreeListener> watchers = new HashMap<>();

    public FileDirectoryTreeSource(File directory) {
        this.directory = directory;
    }

    @Override
    public void shutdown() {
        Collection<DirectoryTreeListener> values = new ArrayList<>(watchers.values());

        for (DirectoryTreeListener listener : values) {
            listener.shutdown();
        }
    }

    public File getDirectory() {
        return directory;
    }

    public boolean isEmpty(String path) {
        Path checkIfEmpty = Paths.get(new File(directory, "/" + path).getPath());
        try {
            DirectoryStream<Path> ds = Files.newDirectoryStream(checkIfEmpty);
            Iterator _files = ds.iterator();
            if (_files.hasNext()) {
                return false;
            }
        } catch (IOException e) {
            // nop.
        }

        return true;
    }

    public List<DirectoryTreeValue> list(String path) {
        File[] files = new File(directory, path).listFiles();

        if (files == null) {
            return Collections.emptyList();
        } else {
            ArrayList<DirectoryTreeValue> list = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    list.add(createValue(path + "/" + file.getName()));
                }
            }

            for (File file : files) {
                if (!file.isDirectory()) {
                    list.add(createValue(path + "/" + file.getName()));
                }
            }

            return list;
        }
    }

    @Override
    public DirectoryTreeListener listener(String path) {
        if (watchers.containsKey(path)) {
            return watchers.get(path);
        }

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path p = Paths.get(new File(directory, "/" + path).getPath());
            p.register(
                    watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE
            );

            Listener listener = new Listener(path, watchService);
            watchers.put(path, listener);
            return listener;
        } catch (IOException e) {
            return null;
        }
    }

    public DirectoryTreeValue createValue(String path) {
        File file = new File(directory, "/" + path);

        if (file.getPath().endsWith(File.separator)) {
            file = new File(file.getPath().substring(0, file.getPath().length() - 1));
        }

        String text = file.getName();
        String code = file.getName();

        Node icon = DirectoryTreeUtils.getIconOfFile(file, false);
        Node expandIcon = DirectoryTreeUtils.getIconOfFile(file, true);

        return new DirectoryTreeValue(path, code, text, icon, expandIcon, file.isDirectory());
    }

    private class Listener extends DirectoryTreeListener {
        private boolean shutdown = false;

        public Listener(String path, WatchService watcher) {
            super(path);

            new Thread(() -> {
                while (true) {
                    WatchKey poll = null;
                    try {
                        poll = watcher.poll(1, TimeUnit.SECONDS);

                        if (shutdown) {
                            watcher.close();
                            break;
                        }

                        if (poll != null) {
                            poll.pollEvents();
                            trigger();

                            poll.reset();
                        }
                    } catch (InterruptedException | IOException e) {
                        //watchers.remove(getPath());
                        break;
                    }
                }
            }).start();
        }

        @Override
        public void shutdown() {
            super.shutdown();

            watchers.remove(getPath());
            shutdown = true;
        }
    }
}
