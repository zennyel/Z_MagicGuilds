package com.zennyel.guilds.listener;

import com.zennyel.guilds.manager.AdventurerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private AdventurerManager adventurerManager;

    public void onInventoryClick(InventoryClickEvent event){
        handleQuestGUI(event);
    }

    public void handleQuestGUI(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        String title = "";

        if(!event.getView().getTitle().equalsIgnoreCase(title)){
            return;
        }


        switch (event.getRawSlot()){
            case 10:
                break;
            case 12:
                break;
            case 14:
                break;
            case 16:
                break;
        }



    }


}
