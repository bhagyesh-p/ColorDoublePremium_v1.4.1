package dogboy602k.ColorDouble.Commands;

import dogboy602k.ColorDouble.Main.Main;
import dogboy602k.ColorDouble.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by dogboy on 7/19/2016.
 */
public class ColorDoubleCommands implements CommandExecutor {

    private Main plugin;
    private String color1 ;
    private String color2 ;
    private String color3 ;

    public ColorDoubleCommands(Main plugin) {
        this.plugin = plugin;
          color1 = plugin.getFileManager().getColor1();
          color2 = plugin.getFileManager().getColor2();
          color3 = plugin.getFileManager().getColor3();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (args.length == 0 || args.length > 4) {
            Util.sendMsg((Player) sender, ChatColor.AQUA + "========================================");
            Util.sendMsg((Player) sender, ChatColor.AQUA + "Usage /cd bet "+ ChatColor.AQUA+"<username> "+ ChatColor.RED + " <" + ChatColor.GOLD + "C" + ChatColor.YELLOW + "O" + ChatColor.GREEN + "L" + ChatColor.AQUA + "O" + ChatColor.BLUE + "R" + ChatColor.DARK_PURPLE + "> "+ ChatColor.GREEN+" <amount>");
            Util.sendMsg((Player) sender, ChatColor.AQUA + "Usage /cd retract "+ ChatColor.AQUA+"<username> "+ ChatColor.RED + " <" + ChatColor.GOLD + "C" + ChatColor.YELLOW + "O" + ChatColor.GREEN + "L" + ChatColor.AQUA + "O" + ChatColor.BLUE + "R" + ChatColor.DARK_PURPLE + "> ");
            Util.sendMsg((Player) sender, ChatColor.AQUA + "Usage /cd info"  );
            Util.sendMsg((Player) sender, ChatColor.AQUA + "Usage /cd history"  );
            Util.sendMsg((Player) sender, ChatColor.AQUA + "Usage /cd rules" );
            Util.sendMsg((Player) sender, ChatColor.AQUA + "Usage /cd gui" );
            Util.sendMsg((Player) sender, ChatColor.AQUA + "========================================");
            return true;
        }
        else if(args.length == 4) {
            if (args[0].equalsIgnoreCase("bet")) {
                String playerName = args[1];
                String color = args [2];
                Player player = Bukkit.getPlayer(playerName);
                if (sender.equals(player)) {
                    double betAmount = 0;

                    if (!color.equalsIgnoreCase(color1) && !color.equalsIgnoreCase(color2) && !color.equalsIgnoreCase(color3)) {
                        Util.sendMsg((Player)sender, ChatColor.RED + "Please enter in "+color1+", "+color2+", or "+color3+" as your color");
                        return true;
                    }
                    else {
                        try {
                            betAmount = Double.valueOf(args[3]);
                        } catch (NumberFormatException e) {
                            Util.sendMsg((Player)sender, ChatColor.RED + "[ERROR] You must enter a number for this value");
                            return true;
                        }
                        if (betAmount <= 0) {
                            Util.sendMsg((Player)sender, ChatColor.RED + ("[ERROR] You must input a bet amount greater than 0"));
                            return true;
                        }

                        if (player != null) {
                            if (color.equalsIgnoreCase(color1)){

                                plugin.getManager().SendBetRED(player,color,betAmount);
                            }
                            else if(color.equalsIgnoreCase(color2)){

                                plugin.getManager().SendBetBLUE(player,color,betAmount);
                            }
                            else if(color.equalsIgnoreCase(color3)){
                                plugin.getManager().SendBetGREEN(player,color,betAmount);
                            }

                        } else {
                            Util.sendMsg((Player) sender, ChatColor.RED + "The player " + ChatColor.GOLD + playerName + ChatColor.RED + " is not online!");
                        }
                    }
                }
                else if (!sender.equals(player)){
                    Util.sendMsg((Player)sender, ChatColor.RED+"[ERROR] You are not that player hence you can not bet");
                }
            }
        }
        if(args.length ==3 && args[0].equalsIgnoreCase("retract")) {
            String playerName = args[1];
            String color = args[2];
            Player player = Bukkit.getPlayer(playerName);
            if (sender.equals(player)) {
                {
                    ////  retract
                    plugin.getManager().retractPlayersBet(player,color);

                }
            }
        }
        if(args.length ==1 && args[0].equalsIgnoreCase("gui")) {
            Player player = Bukkit.getPlayer(sender.getName());
            if (sender.equals(player)) {
                {
                    ////  GUI
                    plugin.getManager().GUI(player);
                }
            }
        }
        else if(args.length ==1 && args[0].equalsIgnoreCase("info")){

            Player player = Bukkit.getPlayer(sender.getName());
            if (sender.equals(player)) {
                {
                    ////  info on current pot
                    plugin.getManager().gameInfo(player);
                    Util.sendMsg((Player)sender, ChatColor.AQUA+ "=========[Info]=========");
                }

            }
        }
        else if(args.length ==1 && args[0].equalsIgnoreCase("history")){

            Player player = Bukkit.getPlayer(sender.getName());
            if (sender.equals(player)) {
                {
                    ////  info on history of pots
                    try{
                        plugin.getManager().listOfColors();
                    }catch (NullPointerException e){
                        Util.sendMsg((Player)sender, ChatColor.AQUA+ "No colors picked yet");
                        plugin.getManager().openHistoryGUI(player);
                        return true;
                    }
                    plugin.getManager().openHistoryGUI(player);
                    Util.sendMsg((Player)sender, ChatColor.AQUA+ "New color will be put at the end of the list");
                    Util.sendMsg((Player)sender, ChatColor.AQUA+ plugin.getManager().listOfColors());
                    Util.sendMsg((Player)sender, ChatColor.AQUA+ "=========[HISTORY]=========");
                }

            }
        }
        else if(args.length ==1 && args[0].equalsIgnoreCase("rules")){
            ////ChatColor.GOLD+"[ColorDouble]"
            Util.sendMsg((Player)sender, ChatColor.AQUA+ " ▁ ▂ ▃ ▄ ▅ ▆ ▇ " +ChatColor.GOLD+"[ColorDouble]" +ChatColor.AQUA+ "▇ ▆ ▅ ▄ ▃ ▂ ▁ ");
            Util.sendMsg((Player)sender, ChatColor.AQUA+ "Rules of ColorDouble: ");
            Util.sendMsg((Player)sender, ChatColor.AQUA+ "1) Pick a color either "+ plugin.getManager().getColorOfChat(color1) +color1 +ChatColor.AQUA +" or " + plugin.getManager().getColorOfChat(color2) + color2 + ChatColor.AQUA +" or " + plugin.getManager().getColorOfChat(color3) +color3);
            Util.sendMsg((Player)sender, ChatColor.AQUA+ "2) Bet a specific amount");
            Util.sendMsg((Player)sender, ChatColor.AQUA+ "3) Wait for the color to be drawn ");
            Util.sendMsg((Player)sender, ChatColor.AQUA+ "If  "+ plugin.getManager().getColorOfChat(color1) + color1  +ChatColor.AQUA +" is chosen you win "+(int) this.plugin.getFileManager().getMultiplier1()+"x your bet");
            Util.sendMsg((Player)sender, ChatColor.AQUA+ "If  "+ plugin.getManager().getColorOfChat(color2) + color2 +ChatColor.AQUA +" is chosen you win "+(int) this.plugin.getFileManager().getMultiplier2()+"x your bet");
            Util.sendMsg((Player)sender, ChatColor.AQUA+ "If "+ plugin.getManager().getColorOfChat(color3) +color3 +ChatColor.AQUA+ " is chosen your winnings are "+(int) this.plugin.getFileManager().getMultiplier3()+"x your bet");
            Util.sendMsg((Player)sender, plugin.getManager().getColorOfChat(color1) +color1+" = "+ChatColor.BOLD+ + (int) this.plugin.getFileManager().getMultiplier1()+"x");
            Util.sendMsg((Player)sender, plugin.getManager().getColorOfChat(color2) +color2+" = "+ChatColor.BOLD+ + (int) this.plugin.getFileManager().getMultiplier2()+"x");
            Util.sendMsg((Player)sender, plugin.getManager().getColorOfChat(color3) + color3+ " = "+ChatColor.BOLD+ + (int) this.plugin.getFileManager().getMultiplier3()+"x");


        }
        return true;
    }
}
