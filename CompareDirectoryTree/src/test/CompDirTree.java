package test;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class CompDirTree {

	private String leftDirPath;
	private String rightDirPath;

	public CompDirTree() {
	}

	public void compareDir(String leftDirPath, String rightDirPath) {
		this.leftDirPath = leftDirPath;
		this.rightDirPath = rightDirPath;
		File leftDir = new File(leftDirPath);
		File rightDir = new File(rightDirPath);
		Map<File, String> leftDirMap = new HashMap<File, String>();
		Map<File, String> rightDirMap = new HashMap<File, String>();
		Collection<File> leftDirFiles = FileUtils.listFilesAndDirs(leftDir,
				TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		Collection<File> rightDirFiles = FileUtils.listFilesAndDirs(rightDir,
				TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		System.out
				.printf("%s: Comparing files/directories on the LEFT: %d, and on the RIGHT: %d\n",
						this.getClass().getName(), leftDirFiles.size(),
						rightDirFiles.size());
		// Load the left map and set all entries as "u" - unmatched
		for (File leftDirFile : leftDirFiles) {
			leftDirMap.put(leftDirFile, "u");
		}
		// Load the right map and set all entries as "u" - unmatched
		for (File rightDirFile : rightDirFiles) {
			rightDirMap.put(rightDirFile, "u");
		}
		Set<File> leftFiles = leftDirMap.keySet();
		Set<File> rightFiles = rightDirMap.keySet();
		for (File leftFile : leftFiles) {
			for (File rightFile : rightFiles) {
				if (compareFile(leftFile, rightFile)) {
					leftDirMap.put(leftFile, "m");
					rightDirMap.put(rightFile, "m");
					break;
				}
			}
		}
		boolean match = true;
		for (File leftDirFile : leftDirFiles) {
			if (leftDirMap.get(leftDirFile).equals("u")) {
				System.out.printf(
						"%s: File/directory on the LEFT has no match: %s\n",
						this.getClass().getName(), leftDirFile.getName());
				match = false;
			}
		}
		for (File rightDirFile : rightDirFiles) {
			if (rightDirMap.get(rightDirFile).equals("u")) {
				System.out.printf(
						"%s: File/directory on the RIGHT has no match: %s\n",
						this.getClass().getName(), rightDirFile.getName());
				match = false;
			}
		}
		if (match) {
			System.out.printf("%s: Directories match\n", this.getClass()
					.getName());
		} else {
			System.out.printf("%s: Directories don't match\n", this.getClass()
					.getName());
		}
	}

	private boolean compareFile(File left, File right) {
		// Compare types, file or directory
		if (left.isFile() ^ right.isFile()) {
			return false;
		}
		// Compare file lengths
		if (left.isFile() & right.isFile()) {
			if (left.length() != right.length()) {
				return false;
			}
		}
		// Make the parent directories' path names the same so we're just
		// comparing everything underneath that
		String path2 = right.getAbsolutePath();
		path2 = path2.replace(
				rightDirPath.subSequence(0, rightDirPath.length()),
				leftDirPath.subSequence(0, leftDirPath.length()));
		// Compare path names
		if (!left.getAbsolutePath().equals(path2)) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		CompDirTree cmp = new CompDirTree();
		if (args.length != 2) {
			System.out
					.printf("%s: Usage: CompDirTree [left dir] [right dir]\n");
			System.exit(1);
		}
		cmp.compareDir(args[0], args[1]);
	}

}
