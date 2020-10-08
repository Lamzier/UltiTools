package com.minecraft.ultikits.v1_12_R1;

import com.minecraft.ultikits.api.VersionWrapper;
import com.minecraft.ultikits.enums.Sounds;
import com.minecraft.ultikits.enums.Colors;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Wrapper1_12_R1 implements VersionWrapper {

    public ItemStack getColoredPlaneGlass(Colors plane) {
        return new ItemStack(Material.STAINED_GLASS_PANE, 1, plane.getCode());
    }

    public ItemStack getSign() {
        return new ItemStack(Material.SIGN, 1);
    }

    public ItemStack getEndEye() {
        return new ItemStack(Material.EYE_OF_ENDER, 1);
    }

    public ItemStack getEmailMaterial(boolean isRead) {
        if (isRead){
            return new ItemStack(Material.INK_SACK, 1, (short) 15);
        }else {
            return new ItemStack(Material.PAPER, 1);
        }
    }

    public ItemStack getHead(OfflinePlayer player) {
        if (player.isOp()) {
            return new ItemStack(Material.SKULL_ITEM, 1 , (short) 5);
        } else {
            return new ItemStack(Material.SKULL_ITEM, 1 , (short) 3);
        }
    }

    public ItemStack getGrassBlock() {
        return new ItemStack(Material.GRASS);
    }

    public Objective registerNewObjective(Scoreboard scoreboard, String name, String criteria, String displayName) {
        return scoreboard.registerNewObjective(ChatColor.DARK_AQUA + displayName, "dummy");
    }

    public Sound getSound(Sounds sound) {
        switch (sound){
            case UI_TOAST_OUT:
                return Sound.UI_TOAST_OUT;
            case BLOCK_CHEST_OPEN:
                return Sound.BLOCK_CHEST_OPEN;
            case BLOCK_CHEST_LOCKED:
                return Sound.BLOCK_CHEST_LOCKED;
            case BLOCK_CHEST_CLOSE:
                return Sound.BLOCK_CHEST_CLOSE;
            case ITEM_BOOK_PAGE_TURN:
                return Sound.ITEM_BOTTLE_FILL;
            case BLOCK_NOTE_BLOCK_HAT:
                return Sound.BLOCK_NOTE_HAT;
            case BLOCK_NOTE_BLOCK_BELL:
                return Sound.BLOCK_NOTE_BELL;
            case BLOCK_WET_GRASS_BREAK:
                return Sound.BLOCK_GRASS_BREAK;
            case BLOCK_NOTE_BLOCK_CHIME:
                return Sound.BLOCK_NOTE_CHIME;
            case ENTITY_ENDERMAN_TELEPORT:
                return Sound.ENTITY_ENDERMEN_TELEPORT;
            default:
                return null;
        }
    }

    public ItemStack getBed(Colors bedColor) {
        return new ItemStack(Material.BED, 1, bedColor.getCode());
    }

    public int getItemDurability(ItemStack itemStack) {
        return itemStack.getType().getMaxDurability() - itemStack.getDurability();
    }

    public ItemStack getItemInHand(Player player, boolean isMainHand) {
        if (isMainHand){
            return player.getInventory().getItemInMainHand();
        }else {
            return player.getInventory().getItemInOffHand();
        }
    }
}
