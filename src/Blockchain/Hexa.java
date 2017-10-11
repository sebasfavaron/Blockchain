package src.Blockchain;

/**
 * Created by navi on 23/09/17.
 */
public class Hexa {
    private String concatData;
    private String hexaNumber;
    private int nonce;

    public Hexa(String concatData) {
        nonce = 1;
        this.concatData = concatData;
        hexaNumber = sha256(concatData);
    }

    //For the load operation
    public Hexa(String hexaNumber, Integer nonce) {
        this.hexaNumber = hexaNumber;
        this.nonce = nonce;
    }

    public void inc() {
        nonce++;
        // To replace nonce just replace what's after the '.' with the new nonce
        concatData = concatData.substring(0, concatData.lastIndexOf(".") + 1) + nonce;
        hexaNumber = sha256(concatData);
    }

    public boolean check(int zeros) {
        char[] number = hexaNumber.toCharArray();
        for (int i=0; i<zeros; i++){
            if (number[i] != '0'){
                return false;
            }
        }
        return true;
    }

    public String sha256(String base) {
        try{
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public int getNonce() {
        return nonce;
    }

    public String toString(){
        return "HEXA = " + hexaNumber;
    }

    public String getHexaNumber(){
        return this.hexaNumber;
    }

    public String getConcatData() {
        return this.concatData;
    }

}
