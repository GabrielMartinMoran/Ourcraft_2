package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.filters;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ManaTurretTargetsFilter {

    protected ArrayList<String> whitelist;
    protected ArrayList<String> blacklist;

    private final static String WHITELIST_PREFIX = "whitelist:";
    private final static String BLACKLIST_PREFIX = "blacklist:";

    public ManaTurretTargetsFilter(ArrayList<String> whitelist, ArrayList<String> blacklist) {
        this.whitelist = whitelist;
        this.blacklist = blacklist;
    }

    public static ManaTurretTargetsFilter fromBookMeta(BookMeta meta) {
        ArrayList<String> whitelist = new ArrayList<>();
        ArrayList<String> blacklist = new ArrayList<>();
        for (int i = 0; i < meta.getPageCount(); i++) {
            String page = meta.getPage(i + 1);
            String[] lines = Arrays.stream(page.split("\n")).filter(x -> x.length() > 0).toArray(String[]::new);
            for (String line : lines) {
                ArrayList<String> listToAdd = null;
                String[] names = null;
                if (line.trim().toLowerCase().startsWith(WHITELIST_PREFIX)) {
                    listToAdd = whitelist;
                    names = getNames(WHITELIST_PREFIX, line);
                }
                if (line.trim().toLowerCase().startsWith(BLACKLIST_PREFIX)) {
                    listToAdd = blacklist;
                    names = getNames(BLACKLIST_PREFIX, line);
                }
                if (listToAdd != null && names != null) {
                    for (String name : names) {
                        listToAdd.add(name);
                    }
                }
            }
        }
        return new ManaTurretTargetsFilter(whitelist, blacklist);
    }

    private static String[] getNames(String prefix, String line) {
        return Arrays.stream(line.trim().substring(prefix.length()).replace(" ", "").split(","))
                .filter(x -> x.length() > 0).toArray(String[]::new);
    }

    public boolean shouldTarget(LivingEntity livingEntity) {
        String name = livingEntity.getCustomName();
        if (name == null) name = livingEntity.getName();
        if (!this.blacklist.isEmpty()) {
            return this.blacklist.contains(name);
        }
        if (!this.whitelist.isEmpty()) {
            return !this.whitelist.contains(name);
        }
        return false;
    }
}
