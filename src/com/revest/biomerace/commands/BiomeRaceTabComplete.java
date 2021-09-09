package com.revest.biomerace.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class BiomeRaceTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String string, String[] strings) {
        if (cmd.getName() == "updatedelay") {
            if (strings.length == 1) {
                ArrayList<String> values = new ArrayList<String>();


                values.add("ab");
                values.add("rc");

                Collections.sort(values);

                return values;
            }

        }
        return null;
    }
}







