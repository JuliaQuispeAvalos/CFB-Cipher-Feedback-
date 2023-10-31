import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class CFBDemo {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su clave (16 caracteres): ");
        String key = scanner.nextLine();
        System.out.print("Ingrese su IV (16 caracteres): ");
        String iv = scanner.nextLine();

        System.out.print("Ingrese el texto a cifrar: ");
        String plaintext = scanner.nextLine();

        try {
            // Convert the key and IV to byte arrays
            byte[] keyBytes = key.getBytes("UTF-8");
            byte[] ivBytes = iv.getBytes("UTF-8");

            // Create a SecretKeySpec for the key
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

            // Create an AES Cipher instance in CFB mode
            Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");

            // Initialize the cipher with encryption mode, the key, and IV
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));

            // Encrypt the plaintext
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));

            // Encode the encrypted data as a Base64 string
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("Texto cifrado: " + encryptedText);

            // Initialize the cipher for decryption
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));

            // Decrypt the data
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // Convert the decrypted bytes to a string
            String decryptedText = new String(decryptedBytes, "UTF-8");
            System.out.println("Texto descifrado: " + decryptedText);
        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        }
    }
}