package ai.arcblroth.fabric.hewo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

// ported from https://github.com/zuzak/owo/blob/master/owo.js
public class Owo {

    private static final List<String> PREFIXES = Lists.newArrayList(
            "<3 ",
            "0w0 ",
            "H-hewwo?? ",
            "HIIII! ",
            "Haiiii! ",
            "Huohhhh. ",
            "OWO ",
            "OwO ",
            "UwU "
    );

    private static final List<String> SUFFIXES = Lists.newArrayList(
            " ( ͡° ᴥ ͡°)",
            " (இωஇ )",
            " (๑•́ ₃ •̀๑)",
            " (• o •)",
            " (●´ω｀●)",
            " (◠‿◠✿)",
            " (✿ ♡‿♡)",
            " (❁´◡`❁)",
            " (　\"◟ \")",
            " (人◕ω◕)",
            " (；ω；)",
            " (｀へ´)",
            " ._.",
            " :3",
            " :D",
            " :P",
            " ;-;",
            " ;3",
            " ;_;",
            " <{^v^}>",
            " >_<",
            " >_>",
            " UwU",
            " XDDD",
            " ^-^",
            " ^_^",
            " x3",
            " x3",
            " xD",
            " ÙωÙ",
            " ʕʘ‿ʘʔ",
            " ㅇㅅㅇ",
            ", fwendo",
            "（＾ｖ＾）"
    );

    private static final Map<String, String> SUBSTITUTIONS = ImmutableMap.<String, String>builder()
            .put("r", "w")
            .put("l", "w")
            .put("R", "W")
            .put("L", "W")
   //       .put("ow", "OwO")
            .put("no", "nu")
            .put("has", "haz")
            .put("have", "haz")
            .put("you", "uu")
            .put("the ", "da ")
            .put("The ", "Da ")
            .build();

    private static <T> T randomItem(List<T> in) {
        return in.get(ThreadLocalRandom.current().nextInt(in.size()));
    }

    public static String addAffixes(String str) {
        return randomItem(PREFIXES) + str + randomItem(SUFFIXES);
    }

    public static String substitute(String str) {
        for(Map.Entry<String, String> entry : SUBSTITUTIONS.entrySet()) {
            str = str.replace(entry.getKey(), entry.getValue());
        }
        return str;
    }

    public static String owo(String str) {
        return addAffixes(substitute(str));
    }

}
