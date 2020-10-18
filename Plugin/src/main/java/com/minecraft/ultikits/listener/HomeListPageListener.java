package com.minecraft.ultikits.listener;

import com.minecraft.ultikits.inventoryapi.InventoryManager;
import com.minecraft.ultikits.inventoryapi.PagesListener;
import com.minecraft.ultikits.ultitools.UltiTools;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class HomeListPageListener extends PagesListener {
    @Override
    public void onItemClick(InventoryClickEvent event, Player player, InventoryManager inventoryManager, ItemStack clickedItem) {
        if (inventoryManager.getTitle().contains(player.getName()+ UltiTools.languageUtils.getWords("home_'s_home_list"))) {
            String homeName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            player.performCommand("home " + homeName);
        }
    }
}
