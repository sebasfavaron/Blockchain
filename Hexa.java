/**
 * Created by navi on 23/09/17.
 */
public class Hexa {
    public String hexaNumber;
    public Integer intNumber;
    public Hexa(Integer a){
        intNumber=a;
        hexaNumber=Integer.toHexString(a);
    }
    public void inc(){
        intNumber++;
        hexaNumber=Integer.toHexString(intNumber);
    }
    public boolean check(){
        char[]numero= hexaNumber.toCharArray();
        if (numero[0]==0&&numero[1]==0&&numero[2]==0&&numero[3]==0){
            return true;
        }
        return false;
    }
}
