package com.binlee.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/16
 */
public final class Statistics {

    private static final Logger sLogger = Logger.get(Statistics.class);
    private static final Pattern PATTERN_BUG = Pattern.compile("Bug#\\d{6}");

    private Statistics() {
        //no instance
    }

    public static void run(String[] args) {
        try {
            calcBugs(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void calcBugs(String path) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        Matcher matcher;
        final List<String> list = new ArrayList<String>() {
            @Override
            public boolean add(String s) {
                if (contains(s)) {
                    return false;
                }
                return super.add(s);
            }
        };
        int greps = 0;
        final Object[] records = new Object[6];
        int value = 0, index = -1;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("--->next")) {
                index++;
                records[index * 2] = line.substring(8);
                records[(index * 2) + 1] = (value = 0);
                continue;
            }
            matcher = PATTERN_BUG.matcher(line);
            if (matcher.find()) {
                final String bug = matcher.group();
                if (!list.contains(bug)) {
                    list.add(bug);
                    records[(index * 2) + 1] = ++value;
                }
                greps++;
            }
        }
        sLogger.i("all bugs: " + greps + ", final hit: " + list.size()
                + " fix rate: " + (list.size() * 1F / greps) * 100 + "%, "
                + " monthly: " + Arrays.toString(records) + "\n" + list);
    }
}
