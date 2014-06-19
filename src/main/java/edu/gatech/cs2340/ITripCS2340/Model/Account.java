public class Account {
	private static final int FNV_32_INIT = 0x811c9dc5;
	private static final int FNV_32_PRIME = 0x01000193;
	private String userName;
	private String password;

	public Account(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	private static int fnvHash(String unhashedString) {
		int rv = FNV_32_INIT;
		final int len = unhashedString.length();
		for (int i = 0; i < len; i++) {
			rv ^= unhashedString.charAt(i);
			rv *= FNV_32_PRIME;
		}
		return rv;
	}

	public int getHashedUserName() {
		return fnvHash(userName);
	}

	public int getHashedPassword() {
		return fnvHash(password);
	}
}