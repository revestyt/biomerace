package com.revest.biomerace.commands;

import com.revest.biomerace.BiomeRace;
import com.revest.biomerace.BiomeRaceActionBar;
import com.revest.biomerace.checks.BiomeRaceCheck;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;


public class BiomeRaceCommands implements CommandExecutor {
    private final BiomeRace plugin;
    public static String randombiome = "";
    public int actionbartickdelay = 5;
    public int racechecktickdelay = 100;
    private BukkitTask ab_task;
    private BukkitTask rc_task;


    public BiomeRaceCommands(BiomeRace plugin) {
        getServer().getConsoleSender().sendMessage("Creating BiomeRace Commands Instance");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player Sender = (Player) sender;
        String[] biomes = {"jungle", "desert", "plains", "basalt_deltas", "savanna", "swamp", "taiga",
                "mountains", "forest", "warped_forest", "crimson_forest", "nether_wastes"};
        String currentbiome = Sender.getLocation().getBlock().getBiome().toString().toLowerCase(Locale.ROOT);

        if (cmd.getName().equalsIgnoreCase("biome")) {
            Sender.sendMessage("§bYou are currently in a " + currentbiome + "§b biome.");
        }

        if (cmd.getName().equalsIgnoreCase("pplonline")) {
            List<String> playersonline = new ArrayList<>();
            for (Player player : getServer().getOnlinePlayers()) {
                String playername = player.getName();
                playersonline.add(playername);
            }
            String playersonlinestring = String.join(", ", playersonline);
            Sender.sendMessage(ChatColor.AQUA + "Players online: " + playersonlinestring);
        }

        if (cmd.getName().equalsIgnoreCase("startrace")) {
            randombiome = "";
            int randomidx = new Random().nextInt(biomes.length);
            randombiome = biomes[randomidx];
            for (Player player : getServer().getOnlinePlayers()) {
                player.sendTitle("§bFind a " + randombiome + " biome!", ChatColor.DARK_AQUA + "§3Find the biome before your opponent!", 10, 100, 20);

            }
            rc_task = new BiomeRaceCheck(Sender, randombiome).runTaskTimer(this.plugin, 0, racechecktickdelay);
            ab_task = new BiomeRaceActionBar(randombiome).runTaskTimer(this.plugin, 0, actionbartickdelay);
        }

        if (cmd.getName().equalsIgnoreCase("stoprace")) {
            if (rc_task != null) {
                rc_task.cancel();
                ab_task.cancel();
            }

            for (Player player : getServer().getOnlinePlayers()) {
                player.sendTitle("§bThe race has been cancelled.", "§3", 10, 100, 20);
                randombiome = "";
            }
        }

        if (cmd.getName().equalsIgnoreCase("racestatus")) {
            Sender.sendMessage("§bLooking for a " + randombiome + " biome currently.");
        }

        if (cmd.getName().equalsIgnoreCase("updatedelay")) {
            if (args.length > 1) {
                if (args[0].startsWith("ab")) {
                    Sender.sendMessage(s);
                    actionbartickdelay = Integer.parseInt(args[1]);
                    Sender.sendMessage("§bThe tick delay for updating the action bar has been set to " + actionbartickdelay + " ticks. (A tick is a 20th of a second.)");
                }
                else {
                    if (args[0].startsWith("rc")) {
                        racechecktickdelay = Integer.parseInt(args[1]);
                        Sender.sendMessage("§bThe tick delay for checking Winner has been set to " + racechecktickdelay + " ticks. (A tick is a 20th of a second.)");
                    }
                    else {
                            Sender.sendMessage("§4Sorry But you need so Specify What Type you want to Change and set an amount.");
                        }
                    }

                }
            if (args[0].startsWith("help")) {
                Sender.sendMessage("§bUse §e/updatedelay§b ab §c<ticks>§b for changing the action bar tick delay and use §e/updatedelay§b rc §c<ticks>§b for changing the Biome Race Biome Update Check tick delay, this means how fast it updates.");
            }
            else {
                Sender.sendMessage("§4Sorry But you need so Specify What Type you want to Change and set an amount.");
            }

            }












         return true;
    }


}
