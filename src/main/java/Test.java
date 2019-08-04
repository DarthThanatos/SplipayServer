public class Test {



    public static void main(String[] argv){
        Class a = null;
        Yo yo = new Yo();
        System.out.println(yo.getClass().getCanonicalName());
    }
}


class Yo extends  Test{

}