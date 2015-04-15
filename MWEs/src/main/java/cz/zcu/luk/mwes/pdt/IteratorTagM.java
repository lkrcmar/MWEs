package cz.zcu.luk.mwes.pdt;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 5.2.14
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class IteratorTagM implements Iterator<TagMParsed> {
    private final BufferedReader br;
    private TagMParsed next;

    public IteratorTagM(BufferedReader br) {
        this.br = br;
        advance();
    }

    private void advance() {
        try {
            String nextLine;
            String nextLineTrimmed;

            String form;
            String lemma;
            String tag;

            while((nextLine = br.readLine()) != null) {
                nextLineTrimmed = nextLine.trim();
                if (nextLineTrimmed.startsWith("<form>")) {
                    form = nextLineTrimmed.substring(6, nextLineTrimmed.length() - 7);
                    if ((nextLine = br.readLine()) == null) {
                        throw new IllegalStateException("1 next line should be line containing <form_change> or <lemma>");
                    }
                    nextLineTrimmed = nextLine.trim();
                    if (nextLineTrimmed.startsWith("<form_change>")) {
                        if ((nextLine = br.readLine()) == null) {
                            throw new IllegalStateException("2 next line should be line containing <lemma>");
                        }
                        nextLineTrimmed = nextLine.trim();
                    }
                    if (nextLineTrimmed.startsWith("<lemma>")) {
                        lemma = nextLineTrimmed.substring(7, nextLineTrimmed.length() - 8);
                    }
                    else {
                        throw new IllegalStateException("3 next line should be line containing <lemma>");
                    }
                    if ((nextLine = br.readLine()) == null) {
                        throw new IllegalStateException("4 next line should be line containing <tag>");
                    }
                    nextLineTrimmed = nextLine.trim();
                    if (nextLineTrimmed.startsWith("<tag>")) {
                        tag = nextLineTrimmed.substring(5, nextLineTrimmed.length() - 6);
                    }
                    else {
                        throw new IllegalStateException("6 next line should be line containing <tag>");
                    }

                    next = new TagMParsed(form, lemma, tag);
                    return;
                }
            }
            br.close();
            next = null;
        }
        catch (IOException ioe) {
            throw new IOError(ioe);
        }
    }

    public boolean hasNext() {
        return next != null;
    }

    public TagMParsed next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        TagMParsed tagMParsed = next;
        advance();
        return tagMParsed;
    }

    public void remove() {
        throw new UnsupportedOperationException("remove is not supported");
    }
}
