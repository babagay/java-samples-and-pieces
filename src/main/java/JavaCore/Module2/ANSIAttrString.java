package JavaCore.Module2;

import java.awt.Color;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.*;

public class ANSIAttrString implements Iterable<ANSIAttrString> {

    //////////////////////////////////////////////////////////////////////

    public static class TermState {
        public static final TermState DEFAULT = new TermState(Color.WHITE, Color.BLACK);

        public final Color fg, bg;

        public TermState(Color fg, Color bg) {
            this.fg = fg;
            this.bg = bg;
        }

        public TermState(TermState init, String[] attrs) {
            Color fg = init.fg;
            Color bg = init.bg;
            for (String attr : attrs) {
                switch (attr) {
                    case  "0": fg = DEFAULT.fg;
                        bg = DEFAULT.bg;
                        break;

                    case "30": fg = Color.BLACK;          break;
                    case "31": fg = Color.RED;            break;
                    case "32": fg = Color.GREEN;          break;
                    case "33": fg = Color.YELLOW;         break;
                    case "34": fg = Color.BLUE;           break;
                    case "35": fg = Color.MAGENTA;        break;
                    case "36": fg = Color.CYAN;           break;
                    case "37": fg = Color.WHITE;          break;
                    case "39": fg = DEFAULT.fg;           break;

                    case "40": bg = Color.BLACK;          break;
                    case "41": bg = Color.RED;            break;
                    case "42": bg = Color.GREEN;          break;
                    case "43": bg = Color.YELLOW;         break;
                    case "44": bg = Color.BLUE;           break;
                    case "45": bg = Color.MAGENTA;        break;
                    case "46": bg = Color.CYAN;           break;
                    case "47": bg = Color.WHITE;          break;
                    case "49": bg = DEFAULT.bg;           break;
                }
            }
            this.fg = fg;
            this.bg = bg;
        }

        public String toString() {
            return fg + " on " + bg;
        }
    }

    //////////////////////////////////////////////////////////////////////

    private static class Iter implements Iterator<ANSIAttrString> {
        private final String s;
        private Matcher m;
        private TermState state;
        private int index;

        Iter(String s, TermState init, Matcher m) {
            this.s = s;
            this.state = init;
            this.m = m;
        }

        public boolean hasNext() {
            return this.m != null;
        }

        public ANSIAttrString next() {
            if (this.m == null) {
                throw new NoSuchElementException();
            }

            int thisEnd = m.find() ? m.start() : m.regionEnd();
            try {
                return new ANSIAttrString(s.substring(index, thisEnd), this.state);
            } finally {
                if (m.hitEnd()) {
                    this.m = null;
                } else {
                    String[] attrs = m.group(1).split(";");
                    state = new TermState(state, attrs);
                    this.index = m.end();
                }
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    //////////////////////////////////////////////////////////////////////

    public final TermState state;
    public final String string;

    private static final Pattern SGR_PATTERN = Pattern.compile(
            "\\e\\[(\\d+(?:;\\d+)*)m"
    );

    public ANSIAttrString(String string) {
        this(string, TermState.DEFAULT);
    }

    public ANSIAttrString(String string, TermState state) {
        this.state = state;
        this.string = string;
    }

    public Iterator<ANSIAttrString> iterator() {
        return new Iter(this.string, this.state, SGR_PATTERN.matcher(this.string));
    }

}