package cz.zcu.luk.mwes.pdt;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 5.2.14
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class TagMParsed {
    public String form;
    public String lemma;
    public String tag;

    public TagMParsed(String form, String lemma, String tag) {
        this.form = form;
        this.lemma = lemma;
        this.tag = tag;
    }
}

