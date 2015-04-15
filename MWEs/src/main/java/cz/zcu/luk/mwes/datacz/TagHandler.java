package cz.zcu.luk.mwes.datacz;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 5.2.14
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class TagHandler {
    public static String extractShortTag(String tag) {
        StringBuilder tagShort = new StringBuilder();
        return tagShort.append(tag.charAt(0)).append(tag.charAt(9)).append(tag.charAt(10)).toString();
    }

    public static String extractShortTagWithNumberAndCase(String tag) {
        StringBuilder tagShort = new StringBuilder();
        return tagShort.append(tag.charAt(0)).append(tag.charAt(3)).append(tag.charAt(4)).append(tag.charAt(9)).append(tag.charAt(10)).toString();
    }
}
