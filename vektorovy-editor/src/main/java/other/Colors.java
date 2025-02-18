package other;

import javafx.scene.paint.Color;

public class Colors {

    //opatří hexadecimální barvy z .NET
    public static String getHtmlColor(Color c){return "#" + c.toString().substring(2, c.toString().length() - 2);}

    //zjistí jestli je ve stringu číslice
    public static boolean isFloat(String s){
        return s.matches("[+-]?([0-9]*[.])?[0-9]+");
    }
}
