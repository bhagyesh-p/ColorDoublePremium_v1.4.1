package dogboy602k.ColorDouble.Util;

import dogboy602k.ColorDouble.Main.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dogboy on 7/19/2016.
 */
public class Manager implements Listener {
    private Economy economy = null;
    private Main plugin;
    private FileManager f;
    private HashMap<UUID, Double> playerBetRED;
    private HashMap<UUID, Double> playerBetBLUE;
    private HashMap<UUID, Double> playerBetGREEN;
    private HashMap<UUID, String> RED;
    private HashMap<UUID, String> BLUE;
    private HashMap<UUID, String> GREEN;
    private List<UUID> names = new ArrayList<>();
    private String color1;
    private String color2;
    private String color3;
    private Player GPlayer;
    private Double bet1 = 0.0;
    private Double bet2 = 0.0;
    private Double bet3 = 0.0;
    private Inventory pickColor1;
    private Inventory pickColor2;
    private Inventory pickColor3;
    private String[] temp = new String[7];

    public Manager(Main plugin) {
        this.plugin = plugin;
        this.playerBetRED = new HashMap<>();
        this.playerBetBLUE = new HashMap<>();
        this.playerBetGREEN = new HashMap<>();
        this.RED = new HashMap<>();
        this.BLUE = new HashMap<>();
        this.GREEN = new HashMap<>();

        color1 = plugin.getFileManager().getColor1();
        color2 = plugin.getFileManager().getColor2();
        color3 = plugin.getFileManager().getColor3();

    }

    public void SendBetRED(Player player, String color, double betAmount) {
        UUID playerUUID = player.getUniqueId();
        String Player = player.getName();
        if (hasEnoughMoney(player, betAmount)) {
            if (playerBetRED.containsKey(playerUUID)) {
                double bet = getPlayerBetRED(playerUUID);

                Util.sendMsg(player, ChatColor.RED + "[ERROR] You have betted already Use " + ChatColor.GREEN + "/cd retract " + player.getName() + " <COLOR>" + ChatColor.RED + " to remove your bet Your previous  bet amount was: " + ChatColor.GREEN + "$" + bet);
                return;
            } else if (!playerBetRED.containsKey(playerUUID)) {
                if (betAmount < 1) {
                    Util.sendMsg(player, ChatColor.RED + "[ERROR] You must bet more then 1 dollar");
                    return;
                }
                if (betAmount > 1000000000) {
                    Util.sendMsg(player, ChatColor.RED + "[ERROR] You must bet less then 1000000000");
                    return;
                }
                names.add(player.getUniqueId());
                this.addPlayerToPotRED(playerUUID, betAmount);
                this.RED.put(playerUUID, color);
                Util.sendMsg(player, "You have been added to " + getColorOfChat(color1) + color1 + ChatColor.GOLD + " team");
                Util.sendMsg(player, "The " + getColorOfChat(color1) + color1 + ChatColor.GOLD + " Pot has a total of: $" + getTotalPotAmountRED());
                plugin.getEconomy().withdrawPlayer(Player, betAmount);
                Util.sendMsg(player, "Good Luck");

                return;
            }
        } else {
            Util.sendMsg(player, ChatColor.RED + "[ERROR] You have do not have enough to bet the specified amount.");
            return;
        }
    }
    public void SendBetBLUE(Player player, String color, double betAmount) {
        UUID playerUUID = player.getUniqueId();
        String Player = player.getName();
        if (hasEnoughMoney(player, betAmount)) {
            if (playerBetBLUE.containsKey(playerUUID)) {
                double bet = getPlayerBetBLUE(playerUUID);

                Util.sendMsg(player, ChatColor.RED + "[ERROR] You have betted already Use " + ChatColor.GREEN + "/mass retract " + player.getName() + ChatColor.RED + " to remove your bet Your previous  bet amount was: " + ChatColor.GREEN + "$" + bet);
                return;
            } else if (!playerBetBLUE.containsKey(playerUUID)) {
                if (betAmount < 1) {
                    Util.sendMsg(player, ChatColor.RED + "[ERROR] You must bet more then 1 dollar");
                    return;
                }
                if (betAmount > 1000000000) {
                    Util.sendMsg(player, ChatColor.RED + "[ERROR] You must bet less then 1000000000");
                    return;
                }
                names.add(player.getUniqueId());
                this.addPlayerToPotBLUE(playerUUID, betAmount);
                this.BLUE.put(playerUUID, color);
                Util.sendMsg(player, "You have been added to " + getColorOfChat(color2) + color2 + ChatColor.GOLD + " team");
                Util.sendMsg(player, "The " + getColorOfChat(color2) + color2 + ChatColor.GOLD + " Pot has a total of: $" + getTotalPotAmountBLUE());
                plugin.getEconomy().withdrawPlayer(Player, betAmount);
                Util.sendMsg(player, "Good Luck");

                return;
            }
        } else {
            Util.sendMsg(player, ChatColor.RED + "[ERROR] You have do not have enough to bet the specified amount.");
            return;
        }

    }
    public void SendBetGREEN(Player player, String color, double betAmount) {
        UUID playerUUID = player.getUniqueId();
        String Player = player.getName();
        if (hasEnoughMoney(player, betAmount)) {
            if (playerBetGREEN.containsKey(playerUUID)) {

                double bet = getPlayerBetGREEN(playerUUID);

                Util.sendMsg(player, ChatColor.RED + "[ERROR] You have betted already Use " + ChatColor.GREEN + "/mass retract " + player.getName() + ChatColor.RED + " to remove your bet Your previous  bet amount was: " + ChatColor.GREEN + "$" + bet);
                return;
            } else if (!playerBetGREEN.containsKey(playerUUID)) {

                if (betAmount < 1) {

                    Util.sendMsg(player, ChatColor.RED + "[ERROR] You must bet more then 1 dollar");
                    return;
                }
                if (betAmount > 1000000000) {

                    Util.sendMsg(player, ChatColor.RED + "[ERROR] You must bet less then 1000000000");
                    return;
                }
                names.add(player.getUniqueId());
                this.addPlayerToPotGREEN(playerUUID, betAmount);
                this.GREEN.put(playerUUID, color);
                Util.sendMsg(player, "You have been added to " + getColorOfChat(color3) + color3 + ChatColor.GOLD + " team");
                Util.sendMsg(player, "The " + getColorOfChat(color3) + color3 + ChatColor.GOLD + " Pot has a total of: $" + getTotalPotAmountGREEN());
                plugin.getEconomy().withdrawPlayer(Player, betAmount);
                Util.sendMsg(player, "Good Luck");

                return;
            }
        } else {
            Util.sendMsg(player, ChatColor.RED + "[ERROR] You have do not have enough to bet the specified amount.");
            return;
        }

    }

    public void addPlayerToPotRED(UUID playerUUID, double amount) {

        this.playerBetRED.put(playerUUID, amount);
    }
    public void addPlayerToPotBLUE(UUID playerUUID, double amount) {
        this.playerBetBLUE.put(playerUUID, amount);
    }
    public void addPlayerToPotGREEN(UUID playerUUID, double amount) {
        this.playerBetGREEN.put(playerUUID, amount);
    }

    public Double getPlayerBetRED(UUID playerUUID) {
        return this.playerBetRED.get(playerUUID);
    }
    public Double getPlayerBetBLUE(UUID playerUUID) {
        return this.playerBetBLUE.get(playerUUID);
    }
    public Double getPlayerBetGREEN(UUID playerUUID) {
        return this.playerBetGREEN.get(playerUUID);
    }

    public double getTotalPotAmountRED() {
        double amount = 0;
        for (Map.Entry<UUID, Double> values : playerBetRED.entrySet()) {
            amount = values.getValue() + amount;
        }
        return amount;
    }
    public double getTotalPotAmountBLUE() {
        double amount = 0;
        for (Map.Entry<UUID, Double> values : playerBetBLUE.entrySet()) {
            amount = values.getValue() + amount;
        }
        return amount;
    }
    public double getTotalPotAmountGREEN() {
        double amount = 0;
        for (Map.Entry<UUID, Double> values : playerBetGREEN.entrySet()) {
            amount = values.getValue() + amount;
        }
        return amount;
    }

    public boolean hasEnoughMoney(Player player, double amount) {
        if (plugin.getEconomy().getBalance(player.getName()) >= amount) {
            return true;
        }
        return false;
    }
    public void removePlayersAfterWinnerChosen() {
        playerBetGREEN.clear();
        playerBetBLUE.clear();
        ;
        playerBetRED.clear();
        RED.clear();
        BLUE.clear();
        GREEN.clear();
    }
    public void retractPlayersBet(Player player, String color) {
        if (color.equalsIgnoreCase(color1)) {
            for (Map.Entry<UUID, Double> entry : playerBetRED.entrySet()) {
                UUID playerUUID = entry.getKey();
                double bet = entry.getValue();
                if (player.getUniqueId().equals(playerUUID)) {
                    plugin.getEconomy().depositPlayer(player, bet);
                    playerBetRED.remove(playerUUID, bet);
                    RED.remove(playerUUID);
                    Util.sendMsg(player, "Your bet has been removed");
                }
            }
        }
        if (color.equalsIgnoreCase(color2)) {
            for (Map.Entry<UUID, Double> entry : playerBetBLUE.entrySet()) {
                UUID playerUUID = entry.getKey();
                double bet = entry.getValue();
                if (player.getUniqueId().equals(playerUUID)) {
                    plugin.getEconomy().depositPlayer(player, bet);
                    playerBetBLUE.remove(playerUUID, bet);
                    BLUE.remove(playerUUID);
                    Util.sendMsg(player, "Your bet has been removed");
                }
            }
        }
        if (color.equalsIgnoreCase(color3)) {
            for (Map.Entry<UUID, Double> entry : playerBetGREEN.entrySet()) {
                UUID playerUUID = entry.getKey();
                double bet = entry.getValue();
                if (player.getUniqueId().equals(playerUUID)) {
                    plugin.getEconomy().depositPlayer(player, bet);
                    playerBetGREEN.remove(playerUUID, bet);
                    GREEN.remove(playerUUID);
                    Util.sendMsg(player, "Your bet has been removed");
                }
            }
        }

    }
    private ArrayList<String> whatColorForPlayer(Player player) {
        ArrayList<String> listOfColors = new ArrayList<>();
        for (Map.Entry<UUID, Double> entry : playerBetRED.entrySet()) {
            UUID playerUUID = entry.getKey();
            double bet = entry.getValue();
            if (player.getUniqueId().equals(playerUUID)) {
                listOfColors.add(color1);
            }
        }
        for (Map.Entry<UUID, Double> entry : playerBetBLUE.entrySet()) {
            UUID playerUUID = entry.getKey();
            double bet = entry.getValue();
            if (player.getUniqueId().equals(playerUUID)) {
                listOfColors.add(color2);

            }
        }

        for (Map.Entry<UUID, Double> entry : playerBetGREEN.entrySet()) {
            UUID playerUUID = entry.getKey();
            double bet = entry.getValue();
            if (player.getUniqueId().equals(playerUUID)) {
                listOfColors.add(color3);

            }
        }
        return listOfColors;
    }
    public void gameInfo(Player player) {
        Util.sendMsg(player, ChatColor.AQUA + "The amount of bets is: " + this.getamountofPlayers());

        if (whatColorForPlayer(player).size() == 0) {
            Util.sendMsg(player, ChatColor.AQUA + "You havent chosen a color ");
        } else {
            for (int i = whatColorForPlayer(player).size() - 1; i >= 0; i--) {
                Util.sendMsg(player, ChatColor.AQUA + "Your chosen color is: " + getColorOfChat(whatColorForPlayer(player).get(i)) + whatColorForPlayer(player).get(i));

            }
        }


    }
    public void listOfColors(String color){
        String[] colors ;
        colors = temp;

        for(int i = 0 ; i< colors.length;i++){
            if(colors[i] == null)
            colors[i] = "not picked yet ,";
        }

        int c = 0;
        for(String b :colors){
            c++;
        }

        for(int k = 0 ; k< colors.length-1;k++){
            colors[k] = colors[k+1];

        }
        colors [colors.length -1] = color;

        temp = colors;
    }
    public String listOfColors() {
        String loc = "COLORS THAT HAVE BEEN PICKED: " ;

        for(String ColorSample :temp){
            if(ColorSample.equalsIgnoreCase("not picked yet ,")){
                loc += " not picked yet,";

            }
            else
            loc += " ,"+ColorSample;

        }
        return loc;

    }

    public int getamountofPlayers() {
        int total = this.playerBetRED.size() + this.playerBetBLUE.size() + this.playerBetGREEN.size();
        return total;
    }

    public void REDwinner() {
        for (Map.Entry<UUID, Double> entry : playerBetRED.entrySet()) {
            UUID key = entry.getKey();
            Double value = entry.getValue();
            Double winnings = value * this.plugin.getFileManager().getMultiplier1() ;
            String Swinner = Bukkit.getPlayer(key).getName();
            Player winner = Bukkit.getPlayer(Swinner);

            Util.sendMsg(winner, "You have won you have won : $" + winnings);
        }
    }
    public void BLUEwinner() {
        for (Map.Entry<UUID, Double> entry : playerBetBLUE.entrySet()) {
            UUID key = entry.getKey();
            Double value = entry.getValue();
            Double winnings = value * this.plugin.getFileManager().getMultiplier2() ;
            String Swinner = Bukkit.getPlayer(key).getName();
            Player winner = Bukkit.getPlayer(Swinner);

            Util.sendMsg(winner, "You have won you have won : $" + winnings);
        }
    }
    public void GREENwinner() {
        for (Map.Entry<UUID, Double> entry : playerBetGREEN.entrySet()) {
            UUID key = entry.getKey();
            Double value = entry.getValue();
            Double winnings = value * this.plugin.getFileManager().getMultiplier3() ;
            String Swinner = Bukkit.getPlayer(key).getName();
            Player winner = Bukkit.getPlayer(Swinner);

            Util.sendMsg(winner, "You have won you have won : $" + winnings);
        }

    }

    public void announce(String color) {

        if (color.equalsIgnoreCase(color1)) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[ColorDouble] " + getColorOfChat(color1) + color1.toUpperCase() + " HAS WON");
            return;
        } else if (color.equalsIgnoreCase(color2)) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[ColorDouble] " + getColorOfChat(color2) + color2.toUpperCase() + " HAS WON");

        } else if (color.equalsIgnoreCase(color3)) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[ColorDouble] " + getColorOfChat(color2) + color3.toUpperCase() + " HAS WON");

        }

    }
    public ItemStack randomColorGen() {
        ItemStack bPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color2));
        ItemStack rPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color1));
        ItemStack gPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color3));

        Random random = new Random();
        int number = random.nextInt(3 - 1 + 1) + 1;
        switch (number) {
            case 2:
                return bPane;
            case 3:
                return rPane;
            default:
                return gPane;
        }
    }

    public void testRed(Player player) {

        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color1));
        ItemStack mid = new ItemStack(Material.BEACON);
        ItemStack blank = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0);

        Inventory loanInv = Bukkit.createInventory(null, 27, (ChatColor.GOLD + "Color Double"));
        loanInv.setItem(0, blank);
        loanInv.setItem(1, blank);
        loanInv.setItem(2, blank);
        loanInv.setItem(3, blank);
        loanInv.setItem(5, blank);
        loanInv.setItem(6, blank);
        loanInv.setItem(7, blank);
        loanInv.setItem(8, blank);
        loanInv.setItem(18, blank);
        loanInv.setItem(19, blank);
        loanInv.setItem(20, blank);
        loanInv.setItem(21, blank);
        loanInv.setItem(23, blank);
        loanInv.setItem(24, blank);
        loanInv.setItem(25, blank);
        loanInv.setItem(26, blank);
        loanInv.setItem(4, mid);
        loanInv.setItem(22, mid);
        ItemStack[] colors = new ItemStack[54];
        for (int i = 0; i < 54; i++) {
            colors[i] = randomColorGen();
        }
        colors[48] = pane;
        for (int i = 9; i < 18; i++) {
            loanInv.setItem(i, colors[i]);
        }
        new BukkitRunnable() {
            int c = 0;

            @Override
            public void run() {
                loanInv.setItem(9, colors[(c + 1)]);
                loanInv.setItem(10, colors[(c + 2)]);
                loanInv.setItem(11, colors[(c + 3)]);
                loanInv.setItem(12, colors[(c + 4)]);
                loanInv.setItem(13, colors[(c + 5)]);
                loanInv.setItem(14, colors[(c + 6)]);
                loanInv.setItem(14, colors[(c + 7)]);
                loanInv.setItem(15, colors[(c + 8)]);
                loanInv.setItem(16, colors[(c + 9)]);
                loanInv.setItem(17, colors[(c + 10)]);
                c++;
                if (c == 44) {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 20, 5);
        player.openInventory(loanInv);


    }
    public void testBlue(Player player) {

        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color2));
        ItemStack mid = new ItemStack(Material.BEACON);
        ItemStack blank = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0);

        Inventory loanInv = Bukkit.createInventory(null, 27, (ChatColor.GOLD + "Color Double"));
        loanInv.setItem(0, blank);
        loanInv.setItem(1, blank);
        loanInv.setItem(2, blank);
        loanInv.setItem(3, blank);
        loanInv.setItem(5, blank);
        loanInv.setItem(6, blank);
        loanInv.setItem(7, blank);
        loanInv.setItem(8, blank);
        loanInv.setItem(18, blank);
        loanInv.setItem(19, blank);
        loanInv.setItem(20, blank);
        loanInv.setItem(21, blank);
        loanInv.setItem(23, blank);
        loanInv.setItem(24, blank);
        loanInv.setItem(25, blank);
        loanInv.setItem(26, blank);
        loanInv.setItem(4, mid);
        loanInv.setItem(22, mid);
        ItemStack[] colors = new ItemStack[54];
        for (int i = 0; i < 54; i++) {
            colors[i] = randomColorGen();
        }
        colors[48] = pane;
        for (int i = 9; i < 18; i++) {
            loanInv.setItem(i, colors[i]);
        }
        new BukkitRunnable() {
            int c = 0;

            @Override
            public void run() {
                loanInv.setItem(9, colors[(c + 1)]);
                loanInv.setItem(10, colors[(c + 2)]);
                loanInv.setItem(11, colors[(c + 3)]);
                loanInv.setItem(12, colors[(c + 4)]);
                loanInv.setItem(13, colors[(c + 5)]);
                loanInv.setItem(14, colors[(c + 6)]);
                loanInv.setItem(14, colors[(c + 7)]);
                loanInv.setItem(15, colors[(c + 8)]);
                loanInv.setItem(16, colors[(c + 9)]);
                loanInv.setItem(17, colors[(c + 10)]);
                c++;
                if (c == 44) {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 20, 5);
        player.openInventory(loanInv);


    }
    public void testGreen(Player player) {

        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color3));
        ItemStack mid = new ItemStack(Material.BEACON);
        ItemStack blank = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0);

        Inventory loanInv = Bukkit.createInventory(null, 27, (ChatColor.GOLD + "Color Double"));
        loanInv.setItem(0, blank);
        loanInv.setItem(1, blank);
        loanInv.setItem(2, blank);
        loanInv.setItem(3, blank);
        loanInv.setItem(5, blank);
        loanInv.setItem(6, blank);
        loanInv.setItem(7, blank);
        loanInv.setItem(8, blank);
        loanInv.setItem(18, blank);
        loanInv.setItem(19, blank);
        loanInv.setItem(20, blank);
        loanInv.setItem(21, blank);
        loanInv.setItem(23, blank);
        loanInv.setItem(24, blank);
        loanInv.setItem(25, blank);
        loanInv.setItem(26, blank);
        loanInv.setItem(4, mid);
        loanInv.setItem(22, mid);
        ItemStack[] colors = new ItemStack[54];
        for (int i = 0; i < 54; i++) {
            colors[i] = randomColorGen();
        }
        colors[48] = pane;
        for (int i = 9; i < 18; i++) {
            loanInv.setItem(i, colors[i]);
        }
        new BukkitRunnable() {
            int c = 0;

            @Override
            public void run() {
                loanInv.setItem(9, colors[(c + 1)]);
                loanInv.setItem(10, colors[(c + 2)]);
                loanInv.setItem(11, colors[(c + 3)]);
                loanInv.setItem(12, colors[(c + 4)]);
                loanInv.setItem(13, colors[(c + 5)]);
                loanInv.setItem(14, colors[(c + 6)]);
                loanInv.setItem(14, colors[(c + 7)]);
                loanInv.setItem(15, colors[(c + 8)]);
                loanInv.setItem(16, colors[(c + 9)]);
                loanInv.setItem(17, colors[(c + 10)]);
                c++;
                if (c == 44) {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 20, 5);
        player.openInventory(loanInv);


    }

    public String pickColor() {
        Random random = new Random();
        int number = random.nextInt(100 - 1 + 1) + 1;
        if (number >= 1 && number <= 48) {
            REDwinner();

            return color1;
        }
        if (number >= 49 && number <= 96) {
            BLUEwinner();

            return color2;
        } else {
            GREENwinner();

            return color3;
        }
    }
    public void sendGameWinInfo(Player player, String color) {
        Random random = new Random();
        int num = random.nextInt(4 - 1 + 1) + 1;

        if (color.equalsIgnoreCase(color1)) {
            testRed(player);

        }


        if (color.equalsIgnoreCase(color2)) {
            testBlue(player);

        }


        if (color.equalsIgnoreCase(color3)) {
            testGreen(player);

        }
    }

    public int getColorOfPane(String color) {
        if (color.equalsIgnoreCase("white")) {
            return 0;
        } else if (color.equalsIgnoreCase("orange")) {
            return 1;
        } else if (color.equalsIgnoreCase("magenta")) {
            return 2;
        } else if (color.equalsIgnoreCase("aqua")) {
            return 3;
        } else if (color.equalsIgnoreCase("yellow")) {
            return 4;
        } else if (color.equalsIgnoreCase("lime")) {
            return 5;
        } else if (color.equalsIgnoreCase("pink")) {
            return 6;
        } else if (color.equalsIgnoreCase("dark gray")) {
            return 7;
        } else if (color.equalsIgnoreCase("gray")) {
            return 8;
        } else if (color.equalsIgnoreCase("cyan")) {
            return 9;
        } else if (color.equalsIgnoreCase("purple")) {
            return 10;
        } else if (color.equalsIgnoreCase("blue")) {
            return 11;
        } else if (color.equalsIgnoreCase("brown")) {
            return 12;
        } else if (color.equalsIgnoreCase("green")) {
            return 13;
        } else if (color.equalsIgnoreCase("red")) {
            return 14;
        } else {
            return 15;
        }

    }
    public ChatColor getColorOfChat(String color) {
        if (color.equalsIgnoreCase("white")) {
            return ChatColor.WHITE;
        } else if (color.equalsIgnoreCase("orange")) {
            return ChatColor.GOLD;
        } else if (color.equalsIgnoreCase("magenta")) {
            return ChatColor.LIGHT_PURPLE;
        } else if (color.equalsIgnoreCase("aqua")) {
            return ChatColor.AQUA;
        } else if (color.equalsIgnoreCase("yellow")) {
            return ChatColor.YELLOW;
        } else if (color.equalsIgnoreCase("lime")) {
            return ChatColor.GREEN;
        } else if (color.equalsIgnoreCase("dark gray")) {
            return ChatColor.DARK_GRAY;
        } else if (color.equalsIgnoreCase("gray")) {
            return ChatColor.GRAY;
        } else if (color.equalsIgnoreCase("cyan")) {
            return ChatColor.DARK_AQUA;
        } else if (color.equalsIgnoreCase("purple")) {
            return ChatColor.DARK_PURPLE;
        } else if (color.equalsIgnoreCase("pink")) {
            return ChatColor.LIGHT_PURPLE;
        } else if (color.equalsIgnoreCase("blue")) {
            return ChatColor.BLUE;
        } else if (color.equalsIgnoreCase("brown")) {
            return ChatColor.GOLD;
        } else if (color.equalsIgnoreCase("green")) {
            return ChatColor.DARK_GREEN;
        } else if (color.equalsIgnoreCase("red")) {
            return ChatColor.RED;
        } else {
            return ChatColor.BLACK;
        }

    }

    public void retractGUI(Player player){
        GPlayer = player;
        UUID playerUUID = player.getUniqueId();

        ItemStack colorItemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color1));
        ItemMeta colormeta = colorItemStack.getItemMeta();
        colormeta.setDisplayName(ChatColor.RED+ "Retract bet on " +getColorOfChat(color1) + color1);
        colorItemStack.setItemMeta(colormeta);

        ItemStack colorItemStack2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color2));
        ItemMeta colormeta2 = colorItemStack2.getItemMeta();
        colormeta2.setDisplayName(ChatColor.RED+ "Retract bet on " +getColorOfChat(color2) + color2);
        colorItemStack2.setItemMeta(colormeta2);

        ItemStack colorItemStack3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color3));
        ItemMeta colormeta3 = colorItemStack3.getItemMeta();
        colormeta3.setDisplayName(ChatColor.RED+ "Retract bet on " +getColorOfChat(color3) + color3);
        colorItemStack3.setItemMeta(colormeta3);

        ItemStack retractBet = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color3));
        ItemMeta retractBetmeta = retractBet.getItemMeta();
        retractBetmeta.setDisplayName(ChatColor.RED+"Retract a bet");
        retractBet.setItemMeta(retractBetmeta);

        Inventory retract = Bukkit.createInventory(null, 9, (ChatColor.GOLD + "BET RETRACT"));
        retract.setItem(3, colorItemStack);
        retract.setItem(4, colorItemStack2);
        retract.setItem(5, colorItemStack3);

        player.openInventory(retract);
    }
    public void GUI(Player player) {
        GPlayer = player;
        UUID playerUUID = player.getUniqueId();

        ItemStack colorItemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color1));
        ItemMeta colormeta = colorItemStack.getItemMeta();
        colormeta.setDisplayName(getColorOfChat(color1) + "Bet on " + color1);
        colorItemStack.setItemMeta(colormeta);

        ItemStack colorItemStack2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color2));
        ItemMeta colormeta2 = colorItemStack2.getItemMeta();
        colormeta2.setDisplayName(getColorOfChat(color2) + "Bet on " + color2);
        colorItemStack2.setItemMeta(colormeta2);

        ItemStack colorItemStack3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color3));
        ItemMeta colormeta3 = colorItemStack3.getItemMeta();
        colormeta3.setDisplayName(getColorOfChat(color3) + "Bet on " + color3);
        colorItemStack3.setItemMeta(colormeta3);

        ItemStack retractBet = new ItemStack(Material.BARRIER, 1, (byte) getColorOfPane(color3));
        ItemMeta retractBetmeta = retractBet.getItemMeta();
        retractBetmeta.setDisplayName(ChatColor.RED+"Retract a bet");
        retractBet.setItemMeta(retractBetmeta);

        Inventory pickInv = Bukkit.createInventory(null, 9, (ChatColor.GOLD + "COLOR PICKER"));
        pickInv.setItem(3, colorItemStack);
        pickInv.setItem(4, colorItemStack2);
        pickInv.setItem(5, colorItemStack3);
        pickInv.setItem(6, retractBet);

        player.openInventory(pickInv);


    }
    public void Color1GUI(Player player) {
        UUID playerUUID = player.getUniqueId();
        double ButtonAdd1 = this.plugin.getFileManager().getButtonAdd1();
        double ButtonAdd2 = this.plugin.getFileManager().getButtonAdd2();
        double ButtonSubtract1 = this.plugin.getFileManager().getButtonSubtract1();
        double ButtonSubtract2 = this.plugin.getFileManager().getButtonSubtract2();

        ItemStack add1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
        ItemMeta add1meta = add1.getItemMeta();
        add1meta.setDisplayName(ChatColor.GREEN + "ADD " + ButtonAdd1 + " dollars");
        add1.setItemMeta(add1meta);

        ItemStack add2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
        ItemMeta add2meta = add2.getItemMeta();
        add2meta.setDisplayName(ChatColor.GREEN + "ADD " + ButtonAdd2 + " dollars");
        add2.setItemMeta(add2meta);

        ItemStack sub1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta sub1meta = sub1.getItemMeta();
        sub1meta.setDisplayName(ChatColor.RED + "Subtract " + ButtonSubtract1 + " dollars");
        sub1.setItemMeta(sub1meta);

        ItemStack sub2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta sub2meta = sub2.getItemMeta();
        sub2meta.setDisplayName(ChatColor.RED + "Subtract " + ButtonSubtract2 + " dollars");
        sub2.setItemMeta(sub2meta);

        ItemStack cancel = new ItemStack(Material.BARRIER);
        ItemMeta cancelmeta = cancel.getItemMeta();
        cancelmeta.setDisplayName(ChatColor.RED + "Cancel");
        cancel.setItemMeta(cancelmeta);

        ItemStack confirm = new ItemStack(Material.SIGN);
        ItemMeta confirmmeta = confirm.getItemMeta();
        confirmmeta.setDisplayName(ChatColor.GREEN + "Confirm");
        confirm.setItemMeta(confirmmeta);

        pickColor1 = Bukkit.createInventory(null, 45, getColorOfChat(color1) + "BET FOR " + color1.toUpperCase() );
        pickColor1.setItem(9, add1);
        pickColor1.setItem(18, add2);
        pickColor1.setItem(17, sub1);
        pickColor1.setItem(26, sub2);
        pickColor1.setItem(36, cancel);
        pickColor1.setItem(22, confirm);
        player.openInventory(pickColor1);

    }
    public void Color2GUI(Player player) {
        UUID playerUUID = player.getUniqueId();
        double ButtonAdd1 = this.plugin.getFileManager().getButtonAdd1();
        double ButtonAdd2 = this.plugin.getFileManager().getButtonAdd2();
        double ButtonSubtract1 = this.plugin.getFileManager().getButtonSubtract1();
        double ButtonSubtract2 = this.plugin.getFileManager().getButtonSubtract2();

        ItemStack add1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
        ItemMeta add1meta = add1.getItemMeta();
        add1meta.setDisplayName(ChatColor.GREEN + "ADD " + ButtonAdd1 + " dollars");
        add1.setItemMeta(add1meta);

        ItemStack add2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
        ItemMeta add2meta = add2.getItemMeta();
        add2meta.setDisplayName(ChatColor.GREEN + "ADD " + ButtonAdd2 + " dollars");
        add2.setItemMeta(add2meta);

        ItemStack sub1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta sub1meta = sub1.getItemMeta();
        sub1meta.setDisplayName(ChatColor.RED + "Subtract " + ButtonSubtract1 + " dollars");
        sub1.setItemMeta(sub1meta);

        ItemStack sub2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta sub2meta = sub2.getItemMeta();
        sub2meta.setDisplayName(ChatColor.RED + "Subtract " + ButtonSubtract2 + " dollars");
        sub2.setItemMeta(sub2meta);

        ItemStack cancel = new ItemStack(Material.BARRIER);
        ItemMeta cancelmeta = cancel.getItemMeta();
        cancelmeta.setDisplayName(ChatColor.RED + "Cancel");
        cancel.setItemMeta(cancelmeta);

        ItemStack confirm = new ItemStack(Material.SIGN);
        ItemMeta confirmmeta = confirm.getItemMeta();
        confirmmeta.setDisplayName(ChatColor.GREEN + "Confirm");
        confirm.setItemMeta(confirmmeta);

        pickColor2 = Bukkit.createInventory(null, 45, getColorOfChat(color2) + "BET FOR " + color2.toUpperCase() );
        pickColor2.setItem(9, add1);
        pickColor2.setItem(18, add2);
        pickColor2.setItem(17, sub1);
        pickColor2.setItem(26, sub2);
        pickColor2.setItem(36, cancel);
        pickColor2.setItem(22, confirm);
        player.openInventory(pickColor2);


    }
    public void Color3GUI(Player player) {
        UUID playerUUID = player.getUniqueId();
        double ButtonAdd1 = this.plugin.getFileManager().getButtonAdd1();
        double ButtonAdd2 = this.plugin.getFileManager().getButtonAdd2();
        double ButtonSubtract1 = this.plugin.getFileManager().getButtonSubtract1();
        double ButtonSubtract2 = this.plugin.getFileManager().getButtonSubtract2();

        ItemStack add1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
        ItemMeta add1meta = add1.getItemMeta();
        add1meta.setDisplayName(ChatColor.GREEN + "ADD " + ButtonAdd1 + " dollars");
        add1.setItemMeta(add1meta);

        ItemStack add2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
        ItemMeta add2meta = add2.getItemMeta();
        add2meta.setDisplayName(ChatColor.GREEN + "ADD " + ButtonAdd2 + " dollars");
        add2.setItemMeta(add2meta);

        ItemStack sub1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta sub1meta = sub1.getItemMeta();
        sub1meta.setDisplayName(ChatColor.RED + "Subtract " + ButtonSubtract1 + " dollars");
        sub1.setItemMeta(sub1meta);

        ItemStack sub2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta sub2meta = sub2.getItemMeta();
        sub2meta.setDisplayName(ChatColor.RED + "Subtract " + ButtonSubtract2 + " dollars");
        sub2.setItemMeta(sub2meta);

        ItemStack cancel = new ItemStack(Material.BARRIER);
        ItemMeta cancelmeta = cancel.getItemMeta();
        cancelmeta.setDisplayName(ChatColor.RED + "Cancel");
        cancel.setItemMeta(cancelmeta);

        ItemStack confirm = new ItemStack(Material.SIGN);
        ItemMeta confirmmeta = confirm.getItemMeta();
        confirmmeta.setDisplayName(ChatColor.GREEN + "Confirm");
        confirm.setItemMeta(confirmmeta);

        pickColor3 = Bukkit.createInventory(null, 45, getColorOfChat(color3) + "BET FOR " + color3.toUpperCase() );

        pickColor3.setItem(9, add1);
        pickColor3.setItem(18, add2);
        pickColor3.setItem(17, sub1);
        pickColor3.setItem(26, sub2);
        pickColor3.setItem(36, cancel);
        pickColor3.setItem(22, confirm);
        player.openInventory(pickColor3);


    }
    public void openHistoryGUI(Player player){

        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(color3));

        ItemStack blank = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0);
        ItemMeta blankmeta = blank.getItemMeta();
        blankmeta.setDisplayName(ChatColor.GREEN + "HEY THIS IS THE BORDER ");
        blank.setItemMeta(blankmeta);



        Inventory loanInv = Bukkit.createInventory(null, 27, (ChatColor.GOLD + "ColorDouble History"));
        loanInv.setItem(0, blank);
        loanInv.setItem(1, blank);
        loanInv.setItem(2, blank);
        loanInv.setItem(3, blank);
        loanInv.setItem(5, blank);
        loanInv.setItem(6, blank);
        loanInv.setItem(7, blank);
        loanInv.setItem(8, blank);
        loanInv.setItem(9, blank);
        loanInv.setItem(17, blank);
        loanInv.setItem(18, blank);
        loanInv.setItem(19, blank);
        loanInv.setItem(20, blank);
        loanInv.setItem(21, blank);
        loanInv.setItem(23, blank);
        loanInv.setItem(24, blank);
        loanInv.setItem(25, blank);
        loanInv.setItem(26, blank);
        loanInv.setItem(4, blank);
        loanInv.setItem(22, blank);
        ItemStack[] colors = new ItemStack[7];

        if(temp[temp.length-1] == (null)) {
            for (int i = 0; i < colors.length; i++) {
                colors[i] = new ItemStack(Material.BARRIER);
            }
            loanInv.setItem(10, colors[0]);
            loanInv.setItem(11, colors[1]);
            loanInv.setItem(12, colors[2]);
            loanInv.setItem(13, colors[3]);
            loanInv.setItem(14, colors[4]);
            loanInv.setItem(15, colors[5]);
            loanInv.setItem(16, colors[6]);
        }
        else if(!(temp[temp.length-1].equalsIgnoreCase("not picked yet ,"))) {
            for (int i = 0; i < colors.length; i++) {
                if(temp[i].equalsIgnoreCase("not picked yet ,")){
                    colors[i] = new ItemStack(Material.BARRIER);
                }
                else
                    colors[i] = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getColorOfPane(temp[i]));
            }
            loanInv.setItem(10, colors[0]);
            loanInv.setItem(11, colors[1]);
            loanInv.setItem(12, colors[2]);
            loanInv.setItem(13, colors[3]);
            loanInv.setItem(14, colors[4]);
            loanInv.setItem(15, colors[5]);
            loanInv.setItem(16, colors[6]);
        }


        player.openInventory(loanInv);
    }

    public boolean CompareStrings(String string1, String string2){

        if(string1 == null || string2 == null){
            return false;
        }

        if(string1.equalsIgnoreCase(string2) ){
            return true;
        }
        else {
            return false;
        }
    }

    public void pickWinner() {
        final long gCD = 200L; //greatestCommonDenominator
        AtomicLong time = new AtomicLong();
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            switch ((int) ((time.getAndAdd(gCD) % 800) / gCD)) { //800 is our total "execution loop" time, divided by our denominator
                case 0: // 0/200 == 0
                    break;
                case 3: // 600/200 == 3
                    if (getamountofPlayers() > 1) {
                        String color = pickColor();

                        for (Player player : Bukkit.getOnlinePlayers()) {

                            if (names.contains(player.getUniqueId())) {
                                sendGameWinInfo(player, color);
                            }

                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                listOfColors(color);
                                announce(color);
                                this.cancel();
                            }
                        }.runTaskTimer(this.plugin, 200, 0);
                        removePlayersAfterWinnerChosen();
                        names.clear();
                    }
                    break;
                default:
                    break;
            }
        }, 0L, gCD);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        double add2;
        double subtract1;
        double subtract2;
        double add1;
        pickColor1 = Bukkit.createInventory(null, 45, getColorOfChat(color1) + "BET FOR " + color1.toUpperCase());
        pickColor2 = Bukkit.createInventory(null, 45, getColorOfChat(color2) + "BET FOR " + color2.toUpperCase());
        pickColor3 = Bukkit.createInventory(null, 45, getColorOfChat(color3) + "BET FOR " + color3.toUpperCase());

        if(e != null) {

            if (e.getInventory().getTitle().equals(ChatColor.GOLD + "Color Double")) {
                for (int i = 0; i < 54; i++) {
                    if (e.getRawSlot() == i) {
                        e.setCancelled(true);
                    }
                }

            }
            if (e.getInventory().getTitle().equals(ChatColor.GOLD + "COLOR PICKER")) {
                if (e.getRawSlot() == 3) {
                    e.setCancelled(true);



                    e.getWhoClicked().closeInventory();
                    Color1GUI(GPlayer);
                }
                if (e.getRawSlot() == 4) {
                    e.setCancelled(true);



                    e.getWhoClicked().closeInventory();
                    Color2GUI(GPlayer);
                }
                if (e.getRawSlot() == 5) {
                    e.setCancelled(true);



                    e.getWhoClicked().closeInventory();
                    Color3GUI(GPlayer);
                }
                if (e.getRawSlot() == 6) {
                    e.setCancelled(true);



                    e.getWhoClicked().closeInventory();
                    retractGUI(GPlayer);
                }
            }


            if (e.getInventory().getTitle().equals(ChatColor.GOLD + "BET RETRACT")) {
                int a = 0;

                if (e.getRawSlot() == 3) {
                    e.setCancelled(true);
                    for (Map.Entry<UUID, Double> entry : playerBetRED.entrySet()) {
                        UUID key = entry.getKey();
                        if (GPlayer.getUniqueId() == key) {
                            a++;

                        }
                    }
                    if (a > 0) {
                        retractPlayersBet(GPlayer, color1);
                    } else {
                        Util.sendMsg(GPlayer, ChatColor.RED + "[ERROR] You must bet on color " + getColorOfChat(color1) + color1);
                    }
                    e.getWhoClicked().closeInventory();
                }
                if (e.getRawSlot() == 4) {
                    e.setCancelled(true);

                    for (Map.Entry<UUID, Double> entry : playerBetBLUE.entrySet()) {
                        UUID key = entry.getKey();
                        if (GPlayer.getUniqueId() == key) {
                            a++;

                        }
                    }
                    if (a > 0) {
                        retractPlayersBet(GPlayer, color2);
                    } else {
                        Util.sendMsg(GPlayer, ChatColor.RED + "[ERROR] You must bet on color " + getColorOfChat(color2) + color2);
                    }
                    e.getWhoClicked().closeInventory();
                }
                if (e.getRawSlot() == 5) {
                    e.setCancelled(true);
                    for (Map.Entry<UUID, Double> entry : playerBetGREEN.entrySet()) {
                        UUID key = entry.getKey();
                        if (GPlayer.getUniqueId() == key) {
                            a++;
                        }
                    }
                    if (a > 0) {
                        retractPlayersBet(GPlayer, color3);
                    } else {
                        Util.sendMsg(GPlayer, ChatColor.RED + "[ERROR] You must bet on color " + getColorOfChat(color3) + color3);
                    }
                    e.getWhoClicked().closeInventory();
                }
                a = 0;
            }





            if (CompareStrings(e.getInventory().getName(), pickColor1.getName())) {
                add1 = this.plugin.getFileManager().getButtonAdd1();
                add2 = this.plugin.getFileManager().getButtonAdd2();
                subtract1 = this.plugin.getFileManager().getButtonSubtract1();
                subtract2 = this.plugin.getFileManager().getButtonSubtract2();
                if (e.getRawSlot() == 9) {
                    e.setCancelled(true);
                    bet1 = bet1 + add1;
                }
                if (e.getRawSlot() == 18) {
                    e.setCancelled(true);
                    bet1 = bet1 + add2;
                }
                if (e.getRawSlot() == 17) {
                    e.setCancelled(true);
                    bet1 = bet1 - subtract1;
                }
                if (e.getRawSlot() == 26) {
                    e.setCancelled(true);
                    bet1 = bet1 - subtract2;
                }
                if (e.getRawSlot() == 36) {
                    e.setCancelled(true);
                    Util.sendMsg((Player) e.getWhoClicked(), ChatColor.GOLD + " You have closed the GUI menu");
                    ////TODO: WORK ON THE CLOSE INVO
                    e.getWhoClicked().closeInventory();
                }
                if (e.getRawSlot() == 22) {
                    e.setCancelled(true);
                    if (bet1 > plugin.getEconomy().getBalance(GPlayer)) {
                        Util.sendMsg(GPlayer, ChatColor.RED + "You do not have " + bet1 + " in your account.");
                        e.getWhoClicked().closeInventory();
                        this.bet1 = 0.0;
                        return;
                    } else {
                        SendBetRED(GPlayer, color1, bet1);

                    }
                }
            }
            if (CompareStrings(e.getInventory().getName(), pickColor2.getName())) {

                add1 = this.plugin.getFileManager().getButtonAdd1();
                add2 = this.plugin.getFileManager().getButtonAdd2();
                subtract1 = this.plugin.getFileManager().getButtonSubtract1();
                subtract2 = this.plugin.getFileManager().getButtonSubtract2();
                if (e.getRawSlot() == 9) {
                    e.setCancelled(true);
                    bet2 = bet2 + add1;
                }
                if (e.getRawSlot() == 18) {
                    e.setCancelled(true);
                    bet2 = bet2 + add2;
                }
                if (e.getRawSlot() == 17) {
                    e.setCancelled(true);
                    bet2 = bet2 - subtract1;
                }
                if (e.getRawSlot() == 26) {
                    e.setCancelled(true);
                    bet2 = bet2 - subtract2;
                }
                if (e.getRawSlot() == 36) {
                    e.setCancelled(true);
                    Util.sendMsg((Player) e.getWhoClicked(), ChatColor.GOLD + " You have closed the GUI menu");
                    ////TODO: WORK ON THE CLOSE INVO
                    e.getWhoClicked().closeInventory();
                }
                if (e.getRawSlot() == 22) {
                    e.setCancelled(true);
                    if (bet2 > plugin.getEconomy().getBalance(GPlayer)) {
                        Util.sendMsg(GPlayer, ChatColor.RED + "You do not have " + bet2 + " in your account.");
                        e.getWhoClicked().closeInventory();
                        this.bet2 = 0.0;
                        return;
                    } else {
                        SendBetBLUE(GPlayer, color2, bet2);

                    }
                }
            }
            if (CompareStrings(e.getInventory().getName(), pickColor3.getName())) {

                add1 = this.plugin.getFileManager().getButtonAdd1();
                add2 = this.plugin.getFileManager().getButtonAdd2();
                subtract1 = this.plugin.getFileManager().getButtonSubtract1();
                subtract2 = this.plugin.getFileManager().getButtonSubtract2();
                if (e.getRawSlot() == 9) {
                    e.setCancelled(true);
                    bet3 = bet3 + add1;
                }
                if (e.getRawSlot() == 18) {
                    e.setCancelled(true);
                    bet3 = bet3 + add2;
                }
                if (e.getRawSlot() == 17) {
                    e.setCancelled(true);
                    bet3 = bet3 - subtract1;
                }
                if (e.getRawSlot() == 26) {
                    e.setCancelled(true);
                    bet3 = bet3 - subtract2;
                }
                if (e.getRawSlot() == 36) {
                    e.setCancelled(true);
                    Util.sendMsg((Player) e.getWhoClicked(), ChatColor.GOLD + "You have closed the GUI menu");
                    ////TODO: WORK ON THE CLOSE INVO
                    e.getWhoClicked().closeInventory();
                }
                if (e.getRawSlot() == 22) {
                    e.setCancelled(true);
                    if (bet3 > plugin.getEconomy().getBalance(GPlayer)) {
                        Util.sendMsg(GPlayer, ChatColor.RED + "You do not have " + bet3 + " in your account.");
                        e.getWhoClicked().closeInventory();
                        this.bet3 = 0.0;
                        return;
                    } else {
                        SendBetGREEN(GPlayer, color3, bet3);

                    }
                }
            }
        }
        if (CompareStrings(e.getInventory().getName(),ChatColor.GOLD + "ColorDouble History" )) {
            for (int i = 0; i < 27; i++) {
                if (e.getRawSlot() == i) {
                    e.setCancelled(true);
                }
            }
        }
        else
            return;

    }
    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e) {
        bet1= 0.0;
        bet2 = 0.0;
        bet3 = 0.0;    }

}