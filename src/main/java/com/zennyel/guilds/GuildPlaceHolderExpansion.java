package com.zennyel.guilds;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GuildPlaceHolderExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
       return  "zmagicguilds";
    }

    @Override
    public @NotNull String getAuthor() {
        return "zennyel";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        return  "";
    }
}
