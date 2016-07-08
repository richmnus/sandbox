package test;

import java.text.MessageFormat;

public class Main {

	public Main() {
		String msg = VieNLSResources.getInstance().get(
				"DiskBasedRepositoryService_overwriteMissingStubs");
		System.out.println(msg);
	}

	public static void main(String[] args) {
		new Main();
	}

}
