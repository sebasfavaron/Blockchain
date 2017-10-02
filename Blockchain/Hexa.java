/**
 * Created by navi on 23/09/17.
 */
public class Hexa {
    public String hexaNumber;
    public Integer intNumber;
    public Long aLong;

    public Hexa(Integer a) {
        intNumber = a;
        hexaNumber = Integer.toHexString(a);
    }
    public Hexa(String a) {
        hexaNumber = a;
        //intNumber=Integer.parseInt(a, 16) ;
    }
    public void inc() {
        intNumber++;
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
