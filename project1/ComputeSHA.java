import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class ComputeSHA
{
	public static void main(String[] args)throws Exception
	{
		String filename = args[0];
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		MessageDigest checksum = MessageDigest.getInstance("SHA-1");
		checksum.update(data);
		byte[] cs_bytes = checksum.digest();

		StringBuffer cs_hex = new StringBuffer();
		for (int i = 0; i < cs_bytes.length; i++) {
			cs_hex.append(Integer.toHexString(0xFF & cs_bytes[i]));
		}

		System.out.println(cs_hex.toString());
	}
}
