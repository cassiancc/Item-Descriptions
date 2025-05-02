package cc.cassian.item_descriptions.client.compat;
//? if <1.21.2 {
/*import dev.emi.emi.EmiUtil;
*///?}
public class EmiCompat {

    public static String getModName(String mod) {
        //? if <1.21.2 {
        /*return EmiUtil.getModName(mod);
        *///?} else {
        return mod;
        //?}
    }


}