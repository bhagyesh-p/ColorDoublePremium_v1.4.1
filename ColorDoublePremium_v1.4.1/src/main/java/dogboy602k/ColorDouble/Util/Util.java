package dogboy602k.ColorDouble.Util;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Created by dogboy on 6/18/2016.
 */
public class Util {
    private static Player sender;


    public static List<Player> getOnlinePlayers() {
        List<Player> list = Lists.newArrayList();
        for (World world : Bukkit.getWorlds()) {
            list.addAll(world.getPlayers());
        }
        return Collections.unmodifiableList(list);
    }

    public static void sendMsg(Player sender, String message) {
        sender.sendMessage(ChatColor.GOLD + "[ColorDouble] " + message);
    }


    public static void sendEmptyMsg(CommandSender sender, String message) {
        sender.sendMessage(message);
    }
}
