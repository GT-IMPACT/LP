package logisticspipes.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.guihook.GuiContainerManager;
import cpw.mods.fml.common.Mod;
import logisticspipes.LogisticsPipes;
import logisticspipes.config.Configs;
import logisticspipes.gui.GuiLogisticsCraftingTable;
import logisticspipes.gui.GuiSolderingStation;
import logisticspipes.gui.orderer.GuiRequestTable;

public class NEILogisticsPipesConfig implements IConfigureNEI {
    public static boolean added = false;

    public NEILogisticsPipesConfig() {
    }

    public void loadConfig() {
        if (Configs.TOOLTIP_INFO && !added) {
            GuiContainerManager.addTooltipHandler(new DebugHelper());
            added = true;
        }

        GuiContainerManager.addDrawHandler(new DrawHandler());
        API.registerGuiOverlayHandler(GuiLogisticsCraftingTable.class, new LogisticsCraftingOverlayHandler(), "crafting");
        API.registerGuiOverlayHandler(GuiRequestTable.class, new LogisticsCraftingOverlayHandler(), "crafting");
    }

    public String getName() {
        return ((Mod)LogisticsPipes.class.getAnnotation(Mod.class)).name();
    }

    public String getVersion() {
        return ((Mod)LogisticsPipes.class.getAnnotation(Mod.class)).version();
    }
}