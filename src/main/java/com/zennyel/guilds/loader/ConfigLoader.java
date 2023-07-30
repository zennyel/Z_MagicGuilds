package com.zennyel.guilds.loader;

import com.zennyel.guilds.utils.ItemStackUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {
    private FileConfiguration fileConfiguration;

    public ConfigLoader(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public List<Integer> getIntList(String path) {
        if (fileConfiguration.contains(path) && fileConfiguration.isList(path)) {
            List<Integer> intList = new ArrayList<>();
            List<?> rawList = fileConfiguration.getList(path);

            for (Object element : rawList) {
                if (element instanceof Integer) {
                    intList.add((Integer) element);
                } else if (element instanceof String) {
                    try {
                        intList.add(Integer.parseInt((String) element));
                    } catch (NumberFormatException ignored) {
                        // If the element is not a valid integer, it will be ignored.
                    }
                }
            }

            return intList;
        }

        return new ArrayList<>(); // Return an empty list if the path doesn't exist or is not a list.
    }

    public List<String> getStringList(String path) {
        if (fileConfiguration.contains(path) && fileConfiguration.isList(path)) {
            return fileConfiguration.getStringList(path);
        }

        return new ArrayList<>(); // Return an empty list if the path doesn't exist or is not a list.
    }

    public Integer getInt(String path) {
        if (fileConfiguration.contains(path) && fileConfiguration.isInt(path)) {
            return fileConfiguration.getInt(path);
        }

        return null; // Return null if the path doesn't exist or is not an integer.
    }

    public String getString(String path) {
        if (fileConfiguration.contains(path) && fileConfiguration.isString(path)) {
            return fileConfiguration.getString(path);
        }

        return null; // Return null if the path doesn't exist or is not a string.
    }
    public ItemStack getItemStack(String path){
        ItemStack is = ItemStackUtils.getItemFromConfiguration(path, fileConfiguration);
        ItemMeta itemMeta = is.getItemMeta();
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
        is.setItemMeta(itemMeta);
        return is;
    }



}
