/**
 * Created by navi on 23/09/17.
 */
public class Hexa {
    private String hexaNumber;
    private Integer intNumber;
    private int nonce;
    private Long aLong;

    public Hexa(Integer a) {
        nonce = 1;
        intNumber = a;
        hexaNumber = Integer.toHexString(a);
    }
    public Hexa(String a) {
        nonce = 1;
        hexaNumber = a;
        //intNumber=Integer.parseInt(a, 16) ;
    }
    public void inc() {
        nonce++;
        intNumber *= 31; // igual a hacer intNumber = intNumber/(pow(31,nonce-1)*pow(31,nonce)
        hexaNumber = Integer.toHexString(intNumber);
    }
    public boolean check(int zeros) {
        char[] number = hexaNumber.toCharArray();
        for (int i=0; i<zeros; i++){
            if (number[i]!='0'){
                return false;
            }
        }
        return true;
    }

    public int getNonce() {
        return nonce;
    }

    public String toString(){
        return "HASH = " + hexaNumber.toString();
    }

    public void add(String otro){
        Integer aux;
        aux = Integer.parseInt(otro, 16);
        intNumber += aux;
        hexaNumber = Integer.toHexString(intNumber);

    }
    public void add(Integer otro){
        intNumber += otro;
        hexaNumber = Integer.toHexString(intNumber);
    }
    public String getHexaNumber(){
        return this.hexaNumber;
    }
    public Integer getIntNumber(){
        return this.intNumber;
    }
}
