package com.ut.rpg.core.guis;

import com.ut.rpg.core.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public abstract class GuiBase implements Listener {

    private static HashMap<UUID, GuiBase> uuidGui;
    protected Inventory inv;
    protected UUID uuid;
    public Player p;

    private GuiBase()
    {
        uuidGui = new HashMap<>();
    }

    public static GuiBase initGuiBase()
    {
        GuiBase guiBase = new GuiBase() {

            @Override
            protected void onInvClick(InventoryClickEvent e) {
                GuiBase.uuidGui.get(e.getWhoClicked().getUniqueId()).onInvClick(e);
            }

            @Override
            protected void onInvClose(InventoryCloseEvent e) {
                GuiBase.uuidGui.get(e.getPlayer().getUniqueId()).onInvClose(e);
                GuiBase.uuidGui.remove(e.getPlayer().getUniqueId());
            }

            @Override
            protected void onInit() {

            }
        };
        Main.getPlugin().getServer().getPluginManager().registerEvents(guiBase, Main.getPlugin());
        return guiBase;
    }

    public GuiBase(Player p, int size, String title)
    {
        this.inv = Main.getPlugin().getServer().createInventory(null, 9 * size, title);
        this.uuid = p.getUniqueId();
        this.p = p;
        onInit();
        p.openInventory(this.inv);
        uuidGui.put(p.getUniqueId(), this);
    }

    protected void refresh() {
        this.inv.clear();
        onInit();
    }

    protected abstract void onInvClick(InventoryClickEvent e);
    protected abstract void onInvClose(InventoryCloseEvent e);
    protected abstract void onInit();

    @EventHandler
    public void exeucteClick(InventoryClickEvent e)
    {
        if(uuidGui.containsKey(e.getWhoClicked().getUniqueId()))
            onInvClick(e);
    }
    @EventHandler
    public void executeClose(InventoryCloseEvent e)
    {
        if(uuidGui.containsKey(e.getPlayer().getUniqueId()))
            onInvClose(e);
    }

}
