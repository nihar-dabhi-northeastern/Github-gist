
public class PolkadotScore {

    public static long computePolkadotScore(String art) {
        String[] lines = art.split("\n", -1);

        // 1) Find the eyes line: first line with two '•' close together
        //    and no letters between them (rules out signature decorations
        //    like "•Å(V)åö•").
        int eyesLine = -1, eyesStart = -1, eyesEnd = -1;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            int b1 = -1, b2 = -1;
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '\u2022') {
                    if (b1 < 0) b1 = x;
                    else { b2 = x; break; }
                }
            }
            if (b1 >= 0 && b2 > b1 && b2 - b1 <= 6) {
                boolean clean = true;
                for (int x = b1 + 1; x < b2; x++) {
                    if (Character.isLetter(line.charAt(x))) { clean = false; break; }
                }
                if (clean) {
                    eyesLine = i; eyesStart = b1; eyesEnd = b2;
                    break;
                }
            }
        }
        if (eyesLine < 0)
            throw new IllegalArgumentException("Could not locate eyes.");

        // Pupil character count = entire eye span "• ... •" inclusive.
        int pupilCharCount = (eyesEnd - eyesStart) + 1;

        // 2) Find the lips: the first "()" pair on a line below the eyes.
       
        int lipStart = -1, lipEnd = -1;
        double eyeMid = (eyesStart + eyesEnd) / 2.0;
        outer:
        for (int i = eyesLine + 1; i < lines.length; i++) {
            String line = lines[i];
            int bestX = -1;
            double bestDist = Double.MAX_VALUE;
            for (int x = 0; x + 1 < line.length(); x++) {
                if (line.charAt(x) == '(' && line.charAt(x + 1) == ')') {
                    double d = Math.abs((x + 0.5) - eyeMid);
                    if (d < bestDist) { bestDist = d; bestX = x; }
                }
            }
            if (bestX >= 0) {
                lipStart = bestX;
                lipEnd   = bestX + 1;
                break outer;
            }
        }
        if (lipStart < 0)
            throw new IllegalArgumentException("Could not locate lips.");

        // 3) Bucket the polkadots.
        long inside = 0, outside = 0;
        for (String line : lines) {
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '0' || c == 'o' || c == 'O') {
                    if (x >= lipStart && x <= lipEnd) inside++;
                    else                              outside++;
                }
            }
        }

        return outside + inside * (long) pupilCharCount;
    }

    public static void main(String[] args) {
        String art = String.join("\n",
                "              ,   ,-',",
                "        ,', ,'  ','  ,'   \u00C5\u00D1G\u00CB\u00A3\u00CF\u00C7\u00C4 \u2020(\u2013)\u00CB \u00DFR\u00C4\u2020",
                "      '-',  '      ,'",
                "          ' -,    ',",
                "              ' -, ',                                         , - - -,",
                "             ('''''' \u00AE'''''''')                        ,,,,,    ,-' -,''''''''',",
                "              ` ~\u201E''`\u201E~ '                          ',  ,', -' , -,'' ''''''''',",
                "                  \"\u201E  \" - \u201E                   \u201E - \",\u00AE,-'     `~~' '''''''",
                "                 \u201E\"         \" \u201E         \u201E - \"      ,',,,',",
                "               \u201E-\" \" \" \" \" \" \" ~~~~~~\" - \u201E       ,'",
                "            \u201E\" \u2013,'' ~ ,       \u2022 ; \u2022          \"      \"\u201E",
                "            \"\"\"\";      ' - , ,  ; , , , - '' ' ' -,_ ', ',",
                "           , -' ' ',           ,'    ,'             ',~', ',",
                "         ,'         ' - , ,()' /\\    ',          (),'\u00AF ,'   `\u00B8`;",
                "         ',                            ` ` ` ` ` `      ,-,,,-'",
                "           '-,                                  ,\u00AC  ,-'",
                "              ' -, ~            ~~~~~~' ' ` ,-'",
                "                  `~-,,,,,,,      ,,,,,,,,,,-~'",
                "      ('('('(,,,              ;    ;                \u2022\u00C5(V)\u00E5\u00F6\u2022",
                "       '-, '-,'''      ,-';`,`'\u02C6\u02C6\u02C6\u02C6\u02C6 ,' ;' ' -,           \u202297\u2022",
                "         ;\u00AF ;      ;  ;  ', ; ; ,'  ;  \u201A\u00B8  ' -,        \u2022\u2022\u2022",
                "         ;   ;     ;       '''''''''''    ``'-,',  ,'",
                "         ;   ;, -\u00AC;    O   O   O  O   '-',,,,,,,,,,,,",
                "         ;        ;  O   O     O O    O  ,'     O     ,'",
                "          ' - - ' `;    O        O  O    O   O    ,'",
                "               ,-'   O    O    O  O      O    O   ,'",
                "            ,-' O O  O   O        O        O ,'",
                "         ,-'   O      O   O   O     O  O     ,'",
                "      ,-'  O    O    O  O   O   O O O   O,-'-,",
                "       ``\u00AC -,,,,,,,-\u00AC~,~~~~~~~~~--',)  (' -,",
                "                    ',   (',                     ' -,    '-,",
                "                     ',)  (',                        `-,)  ' -,",
                "                      ',    ',                           `-,  ,',-----,",
                "                       ',)   ;                              `\\,- ---'",
                "                   \u00B8,,,,'\u2021  (;",
                "                  (\u00B8,,,,,';_'\\ \u00DFy \u00A7(V)\u00F2\u00F3\u2020(\u2013)775 \u2122"
        );

        System.out.println("Polkadot score: " + computePolkadotScore(art));
    }
}