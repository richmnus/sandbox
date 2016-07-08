package com.ibm.rational.rit.g11n.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * For the Eclipse core plugins that have HTML content, invert the nl/**
 * sub-directory to html/** and then delete the old nl/** sub-directory
 */
public class PluginsHtml {

    private List<String> skipList;

    public PluginsHtml(String dirPath, String skip) {
        String[] skips = skip.split(",");
        this.skipList = Arrays.asList(skips);
        processPluginsHtml(dirPath, dirPath);
    }

    public void processPluginsHtml(String sourceDirPath, String destDirPath) {
        File sourceDir = new File(sourceDirPath);
        Collection<File> plugins = listPlugins(sourceDir);
        if (plugins.size() > 0) {
            System.out.printf("%s: Processing %d plugins\n", this.getClass()
                    .getName(), plugins.size());
        } else {
            System.out.printf("%s: No plugins to process\n", this.getClass()
                    .getName());
        }
        int pluginsWithHtml = 0;
        int skippedPlugins = 0;
        for (File plugin : plugins) {
            if (skipList.contains(plugin.getName())) {
                // This plugin is on the skip list
                System.out.printf("%s: Skipped plugin %s\n", this.getClass()
                        .getName(), plugin.getName());
                skippedPlugins++;
                continue;
            }
            if (hasHtml(plugin)) {
                pluginsWithHtml++;
                String sourcePluginDirPath = sourceDirPath + File.separator
                        + plugin.getName() + File.separator + "nl";
                String destPluginDirPath = destDirPath + File.separator
                        + plugin.getName() + File.separator;
                // Process the plugin's HTML directory tree
                processPluginHtml(sourcePluginDirPath, destPluginDirPath);
                // Now remove the plugin's old nl/ tree
                boolean result = FileUtils.deleteQuietly(new File(
                        sourcePluginDirPath));
                if (!result) {
                    System.out
                            .printf("%s: Couldn't delete the nl/ subdirectory in plugin %s\n",
                                    this.getClass().getName(), plugin.getName());
                }
            }
        }
        System.out
                .printf("%s: Processed %d plugin(s) with HTML content, skipped %d plugin(s)\n",
                        this.getClass().getName(), pluginsWithHtml,
                        skippedPlugins);
    }

    /**
     * Re-organises all the HTML files in a plugin as follows:
     * com.ghc.ghTester\nl\fr\html\com\ghc\ghTester\gui\resourceviewer\stubs\
     * behaviour\* becomes
     * com.ghc.ghTester\html\com\ghc\ghTester\gui\resourceviewer
     * \stubs\behaviour\fr\*
     * 
     * @param sourceDirPath
     *            e.g. C:\\Work\\G11n\\returns_plugins\\in\\com.ghc.a3\\nl
     * @param destDirPath
     *            e.g. C:\\Work\\G11n\\returns_plugins\\out\\com.ghc.a3\\
     */
    public void processPluginHtml(String sourceDirPath, String destDirPath) {
        File sourceDir = new File(sourceDirPath);
        if (!sourceDir.canRead()) {
            System.out.printf(
                    "%s: Error - source directory %s cannot be read\n", this
                            .getClass().getName(), sourceDirPath);
            System.exit(1);
        }
        Collection<File> srcFiles = FileUtils.listFiles(sourceDir,
                TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File srcFile : srcFiles) {
            String fileName = srcFile.getName();
            // If this isn't an HTML file...
            if (!fileName.contains(".html")) {
                // We'll ignore it
                System.out.printf("%s: Ignoring file %s\n", this.getClass()
                        .getName(), fileName);
                continue;
            }
            String path = srcFile.getPath();
            path = path.replace(sourceDirPath, "");
            int indexOf = path.indexOf(File.separator + "html");
            String lang = path.substring(0, indexOf);
            lang = lang.replace(File.separator, "_");
            if (lang.startsWith("_")) {
                lang = lang.substring("_".length(),
                        lang.length() + 1 - "_".length());
            }
            if (lang.equals("zh")) {
                lang = "zh_CN";
            }
            if (lang.equals("zh_HK")) {
                continue;
            }
            path = path.substring(indexOf + 1);
            path = path.substring(0, path.indexOf(fileName)) + lang
                    + File.separator + fileName;
            File destDir = new File(destDirPath + path);
            try {
                FileUtils.copyFile(srcFile, destDir);
                System.out.printf("%s: Lang=%s, copied %s to %s\n", this
                        .getClass().getName(), lang, fileName, destDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Answers a collection of plugins in the specified directory. A plugin is
     * assumed to be a directory whose name begins with 'com.'.
     * 
     * @param parentDir
     *            Parent directory
     * @return Collection of plugins
     */
    private Collection<File> listPlugins(File parentDir) {
        Collection<File> files = FileUtils.listFilesAndDirs(parentDir,
                TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        Collection<File> results = new ArrayList<File>();
        for (File file : files) {
            if (file.isDirectory() && file.getName().startsWith("com.")) {
                results.add(file);
            }
        }
        return results;
    }

    /**
     * True if the specified plugin contains HTML content.
     * 
     * @param pluginDir
     *            Plugin directory
     * @return boolean
     */
    private boolean hasHtml(File pluginDir) {
        Collection<File> files = FileUtils.listFilesAndDirs(pluginDir,
                TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        int htmlDirs = 0;
        int htmlFiles = 0;
        for (File file : files) {
            if (file.isDirectory() && file.getName().startsWith("html")) {
                htmlDirs++;
            }
            if (file.isFile() && file.getName().endsWith(".html")) {
                htmlFiles++;
            }
        }
        // If this plugin has HTML content
        if (htmlDirs > 0 || htmlFiles > 0) {
            System.out.printf(
                    "%s: Plugin %s contains %d HTML dirs and %d HTML files\n",
                    this.getClass().getName(), pluginDir.getName(), htmlDirs,
                    htmlFiles);
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        switch (args.length) {
        case 0:
            System.out
                    .printf("Usage: java -jar PluginsHtml <folder name> <skip list>\n");
            System.out
                    .printf("e.g. java -jar PluginsHtml /tmp/myfolder com.ghc.ghTester.homescreen.content,com.ghc.a3\n");
            break;
        case 1:
            new PluginsHtml(args[0], "");
            break;
        default:
            new PluginsHtml(args[0], args[1]);
            break;
        }
    }
}
